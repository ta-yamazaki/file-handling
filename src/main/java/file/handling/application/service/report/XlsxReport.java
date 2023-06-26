package file.handling.application.service.report;

import file.handling.domain.model.product.Product;
import file.handling.domain.model.product.Products;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.*;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class XlsxReport extends AbstractXlsxView {

    private Workbook workbook;
    private Sheet sheet;

    @Override
    protected void buildExcelDocument(Map<String, Object> model,
                                      Workbook workbook,
                                      HttpServletRequest request,
                                      HttpServletResponse response) {
        this.workbook = workbook;

        String fileName = "商品一覧.xlsx";
        this.sheet = workbook.createSheet(fileName);

        // 行固定
        sheet.createFreezePane(0, 1);

        response.setHeader("Content-Disposition", "attachment; filename*=UTF-8''" + URLEncoder.encode(fileName, StandardCharsets.UTF_8));

        setHeader();

        Products products = (Products) model.get("products");
        setBody(products);

        // 枠線の非表示
//        sheet.setDisplayGridlines(false);

        setColumnsWidth();
    }

    private void setHeader() {
        Row headerRow = this.sheet.createRow(0);

        writeHeaderCell(headerRow, "商品番号");
        writeHeaderCell(headerRow, "商品名");
        writeHeaderCell(headerRow, "商品画像ファイル名");
    }

    private void setBody(Products products) {
        for (Product product : products.list()) {
            int lastRow = this.sheet.getLastRowNum();
            Row row = this.sheet.createRow( lastRow + 1);

            writeBodyCell(row, product.number()); // 商品番号
            writeBodyCell(row, product.name()); // 商品名
            writeBodyCell(row, product.fileName()); // 商品画像ファイル名
        }
    }

    private void writeHeaderCell(Row row, String value) {
        int lastCellNum = row.getLastCellNum();
        Cell cell = row.createCell(lastCellNum == -1 ? 0 : lastCellNum);
        cell.setCellValue(value);
        cell.setCellStyle(headerCellStyle());
    }

    private void writeBodyCell(Row row, String value) {
        int lastCellNum = row.getLastCellNum();
        Cell cell = row.createCell(lastCellNum == -1 ? 0 : lastCellNum);
        cell.setCellValue(value);
        cell.setCellStyle(bodyCellStyle());
    }

    private CellStyle headerCellStyle() {
        // 日本語での列幅の自動調整のため、フォントを明示的に指定する。
        CellStyle cellStyle = this.workbook.createCellStyle();
        Font font = this.workbook.createFont();
        font.setFontName("游ゴシック");
        font.setBold(true);
        cellStyle.setFont(font);
        return cellStyle;
    }

    private CellStyle bodyCellStyle() {
        // 日本語での列幅の自動調整のため、フォントを明示的に指定する。
        CellStyle cellStyle = this.workbook.createCellStyle();
        Font font = this.workbook.createFont();
        font.setFontName("游ゴシック");
        cellStyle.setFont(font);
        return cellStyle;
    }

    private void setColumnsWidth() {
        this.sheet.autoSizeColumn(0);  // 商品番号
        this.sheet.autoSizeColumn(1);  // 商品名
        this.sheet.autoSizeColumn(2);  // 商品画像ファイル名
    }
}
