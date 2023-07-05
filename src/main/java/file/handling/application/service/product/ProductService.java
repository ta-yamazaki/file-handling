package file.handling.application.service.product;

import file.handling.domain.model.product.*;
import file.handling.infrastructure.transfer.ProductImageTransfer;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

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
        Product widthId = product.widthNewId();
        Product withFileName = widthId.widthFileName(imageFile);
        ImageFileMetadata fileMetadata = imageFile.getMetadata();
        productRepository.register(withFileName, fileMetadata);
        productImageTransfer.uploadImage(imageFile);
    }

    public void update(ProductId productId, Product product, ProductImageFile imageFile) {
        Product withFileName = product.widthFileName(imageFile);
        productRepository.update(productId, withFileName);
        productImageTransfer.uploadImage(imageFile);
    }

    public void bulkRegister(ProductExcelFile file) throws IOException {
        productRepository.bulkRegister(file);
    }
}
