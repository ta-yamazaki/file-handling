package file.handling.domain.model.scan;

import file.handling.presentaion.controller.product.validate.NotEmptyFile;
import org.springframework.web.multipart.MultipartFile;

public class VirusScanFile {
    @NotEmptyFile(message = "ファイルを選択してください。")
    MultipartFile file;

    public VirusScanFile() {
    }

    public VirusScanFile(MultipartFile file) {
        this.file = file;
    }

    public MultipartFile file() {
        return file;
    }

}
