package file.handling.domain.model.product;

import file.handling.presentaion.controller.product.validate.*;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;

/**
 * 商品画像ファイル
 */
public class ProductImageFile {
    @NotEmptyFile(message = "ファイルを選択してください。")
    @AllowedMediaType(
            message = "JPEGファイルまたはPNGファイルのみアップロード可能です。",
            mediaTypes = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE}
    )
    @AllowedFileExtension(
            message = "ファイルの拡張子は「.jpeg」、「.jpg」または「.png」のみ選択可能です。",
            extensions = {"jpeg", "jpg", "png"}
    )
    @DisallowedFilenameCharacters(
            message = "ファイル名には次の文字は使えません: \\ / : ; * ? \" < > |",
            characters = {"\\", "/", ":", ";", "*", "?", "\"", "<", ">", "|"}
    )
    @MaxFilenameLength(message = "ファイル名の最大文字数は100文字です。", max = 100)
    @DisallowedSameFilename(message = "すでに使用されているファイル名です。")
    @DisallowedSameFile(message = "すでに存在するファイルです。")
    MultipartFile imageFile;

    public ProductImageFile(MultipartFile imageFile) {
        this.imageFile = imageFile;
    }

    public String getOriginalFilename() {
        return imageFile.getOriginalFilename();
    }

    public Path uploadPath() {
        String userDir = System.getProperty("user.dir");
        return Paths.get(userDir + "/static/images/", getOriginalFilename());
    }

    public ImageFileMetadata getMetadata() throws IOException, NoSuchAlgorithmException {
        return ImageFileMetadata.from(imageFile);
    }

    public void transferTo(Path savePath) throws IOException {
        imageFile.transferTo(savePath);
    }

    public boolean isEmpty() {
        return imageFile.isEmpty();
    }

}
