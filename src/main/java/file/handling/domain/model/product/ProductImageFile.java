package file.handling.domain.model.product;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * 商品画像ファイル
 */
public class ProductImageFile {
    String fileName;
    MultipartFile imageFile;

    public ProductImageFile(String fileName, MultipartFile imageFile) {
        this.fileName = fileName;
        this.imageFile = imageFile;
    }

    public String getOriginalFilename() {
        return imageFile.getOriginalFilename();
    }

    public Path uploadPath() {
        String userDir = System.getProperty("user.dir");
        return Paths.get(userDir + "/static/images/", getOriginalFilename());
    }


    public void transferTo(Path savePath) throws IOException {
        imageFile.transferTo(savePath);
    }
}
