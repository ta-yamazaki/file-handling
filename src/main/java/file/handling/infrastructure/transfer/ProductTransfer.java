package file.handling.infrastructure.transfer;

import file.handling.domain.model.product.Product;
import file.handling.domain.model.product.ProductImageFile;
import org.springframework.web.multipart.MultipartFile;

public interface ProductTransfer {
    void uploadImage(ProductImageFile imageFile);
    ProductImageFile downloadImage(Product product);
}
