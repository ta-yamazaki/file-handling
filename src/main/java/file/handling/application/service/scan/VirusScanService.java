package file.handling.application.service.scan;

import file.handling.domain.model.scan.VirusScanFile;
import file.handling.domain.model.scan.VirusScanResult;
import file.handling.domain.model.scan.VirusScanStatus;
import file.handling.infrastructure.scan.VirusScanner;
import org.springframework.stereotype.Service;

import java.io.*;

/**
 * ウイルススキャンサービス
 */
@Service
public class VirusScanService {

    VirusScanner virusScanner;

    public VirusScanService(VirusScanner virusScanner) {
        this.virusScanner = virusScanner;
    }

    public VirusScanResult scan(VirusScanFile file) throws IOException {
        InputStream inputStream = file.file().getInputStream();
        return scan(inputStream);
    }

    public VirusScanResult scan(File file) throws FileNotFoundException {
        InputStream inputStream = new FileInputStream(file);
        return scan(inputStream);
    }

    public VirusScanResult scan(InputStream inputStream) {
        if (!virusScanner.scannerIsRunning())
            return new VirusScanResult(VirusScanStatus.CONNECTION_FAILED, "ClamAV did not respond to ping request!");

        return virusScanner.scan(inputStream);
    }

}
