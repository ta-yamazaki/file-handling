package file.handling.domain.model.product;

import org.springframework.web.multipart.MultipartFile;

/**
 * 商品
 */
public class Product {

    ProductId productId;

    String number;
    String name;

    String fileName;

    public Product() {
    }

    public Product(ProductId productId, String number, String name, String fileName) {
        this.productId = productId;
        this.number = number;
        this.name = name;
        this.fileName = fileName;
    }

    public Product widthFileName(ProductImageFile imageFile) {
        return new Product(
                productId,
                number,
                name,
                imageFile.getOriginalFilename()
        );
    }

//    public Product generateImageFile(MultipartFile imageFile) {
//        String fileName = String.format("%s_%s.%s", name, productId, imageFile.extension());
//        return new Product(
//                productId,
//                number,
//                name,
//                productImage.generateImageFileName(name, productId)
//        );
//    }

    public ProductId productId() {
        return productId;
    }

    public String idAsString() {
        return productId.toString();
    }

    public String number() {
        return number;
    }

    public String name() {
        return name;
    }

    public String fileName() {
        return fileName;
    }

    //
    public String imagePath() {
        return "/images/" + fileName;
    }

//    public String imagePath() {
////        String tmpdir = System.getProperty( "java.io.tmpdir" );  //. システムの一時ディレクトリ
//        String userDir = System.getProperty("user.dir");
////        return "/images/product/" + fileName;
//        return "file:///" + userDir + "/src/main/resources/static/images/product/" + fileName;
//    }

    @Override
    public String toString() {
        return "Product{" +
                "productId=" + productId +
                ", number='" + number + '\'' +
                ", name='" + name + '\'' +
                ", fileName='" + fileName + '\'' +
                '}';
    }
}
