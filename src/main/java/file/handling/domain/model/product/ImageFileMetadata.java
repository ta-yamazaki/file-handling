package file.handling.domain.model.product;

import file.handling.domain.model.file.FileHash;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.FileTime;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Map;

public class ImageFileMetadata {
    String filename;
    long size;
    LocalDateTime lastAccessTime;
    LocalDateTime lastModifiedTime;
    LocalDateTime creationTime;
    String fileOwner;
    String hash;

    // for mybatis
    public ImageFileMetadata() {
    }

    public ImageFileMetadata(String filename, long size, FileTime lastAccessTime, FileTime lastModifiedTime, FileTime creationTime, String fileOwner, String hash) {
        this.filename = filename;
        this.size = size;
        this.lastAccessTime = LocalDateTime.ofInstant(lastAccessTime.toInstant(), ZoneId.systemDefault());
        this.lastModifiedTime = LocalDateTime.ofInstant(lastModifiedTime.toInstant(), ZoneId.systemDefault());
        this.creationTime = LocalDateTime.ofInstant(creationTime.toInstant(), ZoneId.systemDefault());
        this.fileOwner = fileOwner;
        this.hash = hash;
    }

    public static ImageFileMetadata from(MultipartFile file) throws IOException, NoSuchAlgorithmException {
        Path path = new File(file.getOriginalFilename()).toPath();
        file.transferTo(path);
        Map<String, Object> attributes = Files.readAttributes(path, "*");

        ImageFileMetadata fileMetadata = new ImageFileMetadata(
                file.getOriginalFilename(),
                (long) attributes.get("size"),
                (FileTime) attributes.get("lastAccessTime"),
                (FileTime) attributes.get("lastModifiedTime"),
                (FileTime) attributes.get("creationTime"),
                Files.getOwner(path).getName(),
                FileHash.getfileHash(path)
        );
        Files.deleteIfExists(path);
        return fileMetadata;
    }

    public long size() {
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
