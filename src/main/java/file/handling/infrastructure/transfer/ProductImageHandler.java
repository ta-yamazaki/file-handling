package file.handling.infrastructure.transfer;

import file.handling.domain.model.product.Product;
import file.handling.domain.model.product.ProductImageFile;

public interface ProductImageHandler {
    void uploadImage(ProductImageFile imageFile);
    ProductImageFile downloadImage(Product product);
}
