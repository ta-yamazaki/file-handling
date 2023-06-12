package file.handling.infrastructure.datasource.product;

import file.handling.application.service.product.ProductRepository;
import file.handling.domain.model.product.Product;
import file.handling.domain.model.product.ProductId;
import file.handling.domain.model.product.Products;
import org.springframework.stereotype.Repository;

@Repository
public class ProductDataSource implements ProductRepository {

    ProductMapper productMapper;

    ProductDataSource(ProductMapper productMapper) {
        this.productMapper = productMapper;
    }

    @Override
    public Products list() {
        return new Products(productMapper.list());
    }

    @Override
    public Product productOf(ProductId productId) {
        return productMapper.productOf(productId);
    }

    @Override
    public void register(Product product) {
        ProductId productId = ProductId.newOne();
        productMapper.registerNew(productId, product);
    }

    @Override
    public void update(ProductId productId, Product product) {
        productMapper.delete(productId);
        productMapper.register(productId, product);
    }
}
