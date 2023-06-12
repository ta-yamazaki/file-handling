package file.handling.infrastructure.transfer;

import file.handling.domain.model.product.Product;
import file.handling.domain.model.product.ProductImageFile;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ServerErrorException;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

@Component
public class ProductImageTransfer implements ProductTransfer {
    @Override
    public void uploadImage(ProductImageFile productImageFile) {
        try {
            Path uploadPath = productImageFile.uploadPath();
            productImageFile.transferTo(uploadPath);
        } catch (IOException e) {
            e.printStackTrace();
            throw new ServerErrorException(e.toString(), e.getCause());
        }
    }

    @Override
    public ProductImageFile downloadImage(Product product) {
        return null;
    }
}
