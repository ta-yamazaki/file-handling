package file.handling.application.service.product;

import file.handling.domain.model.product.ImageFileMetadata;
import file.handling.domain.model.product.Product;
import file.handling.domain.model.product.ProductId;
import file.handling.domain.model.product.Products;

public interface ProductRepository {
    Products list();
    Product productOf(ProductId productId);

    ImageFileMetadata fileMetadataOf(String filename);

    boolean productExistsOf(String filename);

    void register(Product product, ImageFileMetadata fileMetadata);

    void update(ProductId productId, Product product);
}
