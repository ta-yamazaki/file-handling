package file.handling.domain.model.product;

import java.util.List;

/**
 * 商品一覧
 */
public class Products{
    List<Product> list;

    public Products() {
    }

    public Products(List<Product> list) {
        this.list = list;
    }

    public List<Product> list() {
        return list;
    }

    @Override
    public String toString() {
        return "Products{" +
                "list=" + list +
                '}';
    }
}
