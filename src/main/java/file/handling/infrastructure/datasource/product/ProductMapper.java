package file.handling.infrastructure.datasource.product;

import file.handling.domain.model.product.ImageFileMetadata;
import file.handling.domain.model.product.Product;
import file.handling.domain.model.product.ProductId;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
interface ProductMapper {
    List<Product> list();
    Product productOf(@Param("productId") ProductId productId);

    ImageFileMetadata fileMetadataOf(@Param("filename") String filename);

    boolean productExistsOf(@Param("filename") String filename);

    void registerNew(@Param("productId") ProductId productId,
                     @Param("product") Product product);

    void registerFileMetadata(@Param("productId") ProductId productId,
                              @Param("metadata") ImageFileMetadata fileMetadata);

    void register(@Param("productId") ProductId productId,
                  @Param("product") Product product);

    void delete(@Param("productId") ProductId productId);
}
