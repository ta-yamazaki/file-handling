package file.handling.application.service.product;

import file.handling.domain.model.product.Product;
import file.handling.domain.model.product.ProductId;
import file.handling.domain.model.product.Products;

public interface ProductRepository {
    Products list();
    Product productOf(ProductId productId);

    void register(Product product);

    void update(ProductId productId, Product product);
}
