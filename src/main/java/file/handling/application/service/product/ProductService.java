package file.handling.application.service.product;

import file.handling.domain.model.product.*;
import file.handling.infrastructure.transfer.ProductImageTransfer;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.UserPrincipal;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.Map;

@Service
public class ProductService {

    ProductRepository productRepository;
    ProductImageTransfer productImageTransfer;

    public ProductService(ProductRepository productRepository, ProductImageTransfer productImageTransfer) {
        this.productRepository = productRepository;
        this.productImageTransfer = productImageTransfer;
    }

    public Products list() {
        return productRepository.list();
    }

    public Product productOf(ProductId productId) {
        return productRepository.productOf(productId);
    }

    public void register(Product product, ProductImageFile imageFile) throws IOException, NoSuchAlgorithmException {
        Product withFileName = product.widthFileName(imageFile);
        ImageFileMetadata fileMetadata = imageFile.getMetadata();
        productRepository.register(withFileName, fileMetadata);
        productImageTransfer.uploadImage(imageFile);
    }

    public void update(ProductId productId, Product product, ProductImageFile imageFile) {
        Product withFileName = product.widthFileName(imageFile);
        productRepository.update(productId, withFileName);
        productImageTransfer.uploadImage(imageFile);
    }

}
