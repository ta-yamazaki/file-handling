package file.handling.application.service.report;

import file.handling.domain.model.product.Product;
import file.handling.domain.model.product.Products;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class XlsxReport extends AbstractXlsxView {

    @Override
    protected void buildExcelDocument(Map<String, Object> model,
                                      Workbook workbook,
                                      HttpServletRequest request,
                                      HttpServletResponse response) {
        String fileName = "商品一覧.xlsx";
        response.setHeader("Content-Disposition", "attachment; filename*=UTF-8''" + URLEncoder.encode(fileName, StandardCharsets.UTF_8));

        Sheet sheet = workbook.createSheet(fileName);
        // 枠線の非表示
//        sheet.setDisplayGridlines(false);
        setHeader(sheet);

        Products products = (Products) model.get("products");
        setBody(sheet, products);
    }

    private void setHeader(Sheet sheet) {
        Row headerRow = sheet.createRow(0);

        writeCell(headerRow, "商品番号");
        writeCell(headerRow, "商品名");
        writeCell(headerRow, "商品画像ファイル名");
    }

    private void setBody(Sheet sheet, Products products) {
        for (Product product : products.list()) {
            Row row = sheet.createRow(sheet.getLastRowNum() + 1);

            writeCell(row, product.number()); // 商品番号
            writeCell(row, product.name()); // 商品名
            writeCell(row, product.fileName()); // 商品画像ファイル名
        }
    }

    private void writeCell(Row row, String value) {
        int lastCellNum = row.getLastCellNum();
        Cell cell = row.createCell(lastCellNum == -1 ? 0 : lastCellNum);
        cell.setCellValue(value);
    }
}
