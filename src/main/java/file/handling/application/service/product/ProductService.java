package file.handling.application.service.product;

import file.handling.domain.model.product.*;
import file.handling.infrastructure.transfer.ProductImageHandler;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

@Service
public class ProductService {

    ProductRepository productRepository;
    ProductImageHandler productImageHandler;

    public ProductService(ProductRepository productRepository, ProductImageHandler productImageHandler) {
        this.productRepository = productRepository;
        this.productImageHandler = productImageHandler;
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
        productImageHandler.uploadImage(imageFile);
    }

    public void update(ProductId productId, Product product, ProductImageFile imageFile) {
        Product withFileName = product.widthFileName(imageFile);
        productRepository.update(productId, withFileName);
        productImageHandler.uploadImage(imageFile);
    }

    public void bulkRegister(ProductExcelFile file) throws IOException {
        productRepository.bulkRegister(file);
    }
}
