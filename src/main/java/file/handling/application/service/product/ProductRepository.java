package file.handling.application.service.product;

import file.handling.domain.model.product.*;

import java.io.IOException;

public interface ProductRepository {
    Products list();
    Product productOf(ProductId productId);

    ImageFileMetadata fileMetadataOf(String filename);

    boolean productExistsOf(String filename);

    void register(Product product, ImageFileMetadata fileMetadata);

    void update(ProductId productId, Product product);

    void bulkRegister(ProductExcelFile file) throws IOException;
}
