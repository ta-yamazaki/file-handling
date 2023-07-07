package file.handling.infrastructure.scan;

import file.handling.domain.model.scan.VirusScanResult;

import java.io.IOException;
import java.io.InputStream;

public interface VirusScanner {
    boolean scannerIsRunning();
    VirusScanResult scan(final InputStream inputStream);
}
