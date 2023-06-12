package file.handling.application.service.product;

import file.handling.domain.model.product.Product;
import file.handling.domain.model.product.ProductId;
import file.handling.domain.model.product.ProductImageFile;
import file.handling.domain.model.product.Products;
import file.handling.infrastructure.transfer.ProductImageTransfer;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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

    public void register(Product product, ProductImageFile imageFile) {
        Product withFileName = product.widthFileName(imageFile);
        productRepository.register(withFileName);
        productImageTransfer.uploadImage(imageFile);
    }

    public void update(ProductId productId, Product product, ProductImageFile imageFile) {
        Product withFileName = product.widthFileName(imageFile);
        productRepository.update(productId, withFileName);
        productImageTransfer.uploadImage(imageFile);
    }

}
