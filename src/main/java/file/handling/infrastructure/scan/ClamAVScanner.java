package file.handling.infrastructure.scan;

import file.handling.domain.model.scan.VirusScanResult;
import file.handling.domain.model.scan.VirusScanStatus;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

@Component
public class ClamAVScanner implements VirusScanner {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    private static final int CHUNK_SIZE = 2048;
    private static final String RESPONSE_OK = "stream: OK";
    private static final String PONG = "PONG";
    private static final String FOUND_SUFFIX = "FOUND";
    private static final String ERROR_SUFFIX = "ERROR";
    private static final String STREAM_PREFIX = "stream:";

    private static final int CONNECTION_TIMEOUT = 2000;
    private static final int READ_TIMEOUT = 20000;
    private static final String HOST = "localhost";
    private static final int PORT = 3310;

    @Override
    public boolean scannerIsRunning() {
        try {
            return processCommand("zPING\0".getBytes()).trim().equalsIgnoreCase(PONG);
        } catch (Exception e) {
            logger.warn("Error pinging to ClamAV, " + e.getMessage());
            return false;
        }
    }

    private String processCommand(final byte[] cmd) throws IOException {
        String response = "";

        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress(HOST, PORT), CONNECTION_TIMEOUT);
            socket.setSoTimeout(READ_TIMEOUT);

            try (DataOutputStream dos = new DataOutputStream(socket.getOutputStream())) {
                dos.write(cmd);
                dos.flush();

                InputStream is = socket.getInputStream();

                int read = CHUNK_SIZE;
                byte[] buffer = new byte[CHUNK_SIZE];

                while (read == CHUNK_SIZE) {
                    try {
                        read = is.read(buffer);
                    } catch (IOException e) {
                        System.out.println("Error reading result from socket, " + e.getMessage());
                        break;
                    }
                    response = new String(buffer, 0, read);
                }
            }
        }
        return response;
    }

    @Override
    public VirusScanResult scan(final InputStream inputStream) {
        logger.info("VirusScan: Start to scan.");

        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress(HOST, PORT), CONNECTION_TIMEOUT);
            socket.setSoTimeout(READ_TIMEOUT);

            try (OutputStream outStream = new BufferedOutputStream(socket.getOutputStream())) {
                outStream.write("zINSTREAM\0".getBytes(StandardCharsets.UTF_8));
                outStream.flush();

                byte[] buffer = new byte[CHUNK_SIZE];
                try (InputStream inStream = socket.getInputStream()) {
                    int read = inputStream.read(buffer);

                    while (read >= 0) {
                        byte[] chunkSize = ByteBuffer.allocate(4).putInt(read).array();
                        outStream.write(chunkSize);
                        outStream.write(buffer, 0, read);

                        if (inStream.available() > 0) {
                            byte[] reply = IOUtils.toByteArray(inStream);
                            throw new IOException("Reply from server: " + new String(reply, StandardCharsets.UTF_8));
                        }
                        read = inputStream.read(buffer);
                    }
                    outStream.write(new byte[]{0, 0, 0, 0});
                    outStream.flush();

                    VirusScanResult result = populateVirusScanResult(new String(IOUtils.toByteArray(inStream)).trim());
                    logger.info("VirusScan: Ends the scan.");
                    logger.info("VirusScan: results => " + result);
                    return result;
                }
            }
        } catch (IOException e) {
            logger.error("VirusScan: Errors occurred.");
            logger.error(e.getMessage());
            return new VirusScanResult(VirusScanStatus.FAILED, e.getMessage());
        }
    }

    private VirusScanResult populateVirusScanResult(final String result) {
        if (result == null || result.isEmpty())
            return new VirusScanResult(VirusScanStatus.ERROR, result);

        if (RESPONSE_OK.equals(result))
            return new VirusScanResult(VirusScanStatus.PASSED, result);

        if (result.endsWith(FOUND_SUFFIX)) {
            String signature = result.substring(STREAM_PREFIX.length(), result.lastIndexOf(FOUND_SUFFIX) - 1).trim();
            return new VirusScanResult(VirusScanStatus.FAILED, result, signature);
        }

        if (result.endsWith(ERROR_SUFFIX))
            return new VirusScanResult(VirusScanStatus.ERROR, result);

        return new VirusScanResult(VirusScanStatus.FAILED, result);
    }
}
