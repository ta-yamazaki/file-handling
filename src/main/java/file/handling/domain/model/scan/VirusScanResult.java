package file.handling.domain.model.scan;

/**
 * ウイルススキャン結果
 */
public class VirusScanResult {
    VirusScanStatus status;
    String resultMessage;
    String signature;

    public VirusScanResult(VirusScanStatus status, String resultMessage) {
        this.status = status;
        this.resultMessage = resultMessage;
    }

    public VirusScanResult(VirusScanStatus status, String resultMessage, String signature) {
        super();
        this.status = status;
        this.resultMessage = resultMessage;
        this.signature = signature;
    }

    public String status() {
        return "ステータス: " + status.name();
    }

    public String statusMessage() {
        return status.message();
    }

    public String resultMessage() {
        return resultMessage;
    }

    public String signature() {
        return signature;
    }

    @Override
    public String toString() {
        return "VirusScanResult{" +
                "status=" + status +
                ", resultMessage='" + resultMessage + '\'' +
                ", signature='" + signature + '\'' +
                '}';
    }
}
