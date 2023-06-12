package file.handling.domain.model.product;

import java.util.UUID;

/**
 * 商品ID
 */
public class ProductId {
    UUID value;

    ProductId() {
    }

    public ProductId(UUID value) {
        this.value = value;
    }

    public ProductId(String value) {
        this.value = UUID.fromString(value);
    }

    public static ProductId newOne() {
        return new ProductId(UUID.randomUUID());
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
