package file.handling.domain.model.scan;

/**
 * ウイルススキャン状況
 */
public enum VirusScanStatus {
    PASSED("ウイルスは検知されませんでした。"),
    FAILED("ウイルスが検知されました。"),
    ERROR("ウイルススキャン中にエラーが発生しました。"),
    CONNECTION_FAILED("ウイルススキャンサーバーとの接続に失敗しました。");

    String message;

    VirusScanStatus(String message) {
        this.message = message;
    }

    public String message() {
        return message;
    }
}