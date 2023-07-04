package file.handling.domain.model.product;

import file.handling.domain.model.file.FileHash;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.Map;

public class ImageFileMetadata {
    String filename;
    int size;
    LocalDateTime lastAccessTime;
    LocalDateTime lastModifiedTime;
    LocalDateTime creationTime;
    String fileOwner;
    String hash;

    // for mybatis
    public ImageFileMetadata() {
    }

    public ImageFileMetadata(String filename, int size, LocalDateTime lastAccessTime, LocalDateTime lastModifiedTime, LocalDateTime creationTime, String fileOwner, String hash) {
        this.filename = filename;
        this.size = size;
        this.lastAccessTime = lastAccessTime;
        this.lastModifiedTime = lastModifiedTime;
        this.creationTime = creationTime;
        this.fileOwner = fileOwner;
        this.hash = hash;
    }

    public static ImageFileMetadata from(MultipartFile file) throws IOException, NoSuchAlgorithmException {
        Path path = new File(file.getOriginalFilename()).toPath();
        file.transferTo(path);
        Map<String, Object> attributes = Files.readAttributes(path, "*");

        ImageFileMetadata fileMetadata = new ImageFileMetadata(
                file.getOriginalFilename(),
                (int) attributes.get("size"),
                (LocalDateTime) attributes.get("lastAccessTime"),
                (LocalDateTime) attributes.get("lastModifiedTime"),
                (LocalDateTime) attributes.get("creationTime"),
                Files.getOwner(path).getName(),
                FileHash.getfileHash(path)
        );
        Files.deleteIfExists(path);
        return fileMetadata;
    }

    public int size() {
        return size;
    }

    public LocalDateTime creationTime() {
        return creationTime;
    }

    public String hash() {
        return hash;
    }

    public boolean isSame(ImageFileMetadata metadata) {
        return hash.equals(metadata.hash()) &&
                size == metadata.size() &&
                creationTime.isEqual(metadata.creationTime());
    }
}
