package file.handling.infrastructure.datasource.product;

import file.handling.application.service.product.ProductRepository;
import file.handling.domain.model.product.*;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.stereotype.Repository;

import java.io.IOException;

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
    public ImageFileMetadata fileMetadataOf(String filename) {
        return productMapper.fileMetadataOf(filename);
    }

    @Override
    public boolean productExistsOf(String filename) {
        return productMapper.productExistsOf(filename);
    }

    @Override
    public void register(Product product, ImageFileMetadata fileMetadata) {
        ProductId productId = product.productId();
        productMapper.registerNew(productId, product);
        productMapper.registerFileMetadata(productId, fileMetadata);
    }

    @Override
    public void update(ProductId productId, Product product) {
        productMapper.delete(productId);
        productMapper.register(productId, product);
    }

    @Override
    public void bulkRegister(ProductExcelFile file) throws IOException {
        Workbook workbook = WorkbookFactory.create(file.excelFile().getInputStream());
        Sheet sheet = workbook.getSheetAt(0);
        int lastRow = sheet.getLastRowNum();
        for (int i=0; i<lastRow; i++) {
            Row row = sheet.getRow(i+1);
            Product newProduct = new Product(ProductId.newOne(),
                    null,
                    row.getCell(0).getStringCellValue(),
                    (int) row.getCell(1).getNumericCellValue(),
                    "");
            productMapper.registerNew(newProduct.productId(), newProduct);
        }
    }
}
