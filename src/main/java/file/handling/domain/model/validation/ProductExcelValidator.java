package file.handling.domain.model.validation;

import file.handling.domain.model.product.ProductExcelFile;
import file.handling.presentaion._error.Errors;
import org.apache.poi.ss.usermodel.*;

import java.io.IOException;

public class ProductExcelValidator {
    Workbook workbook;
    Sheet sheet;

    Cell ヘッダー_商品名;
    Cell ヘッダー_価格;

    public ProductExcelValidator(ProductExcelFile excelFile) throws IOException {
        // Excelファイルへアクセス
        Workbook workbook = WorkbookFactory.create(excelFile.excelFile().getInputStream());
        Sheet sheet = workbook.getSheetAt(0); // 1つ目のシートを取得
        this.workbook = workbook;
        this.sheet = sheet;

        Row header = sheet.getRow(0); // 1行目を取得
        Cell ヘッダー_商品名 = header.getCell(0); // 1列目のセルの値を取得
        Cell ヘッダー_価格 = header.getCell(1); // 2列目のセルの値を取得

        this.ヘッダー_商品名 = ヘッダー_商品名;
        this.ヘッダー_価格 = ヘッダー_価格;
    }

    public boolean isInvalidContent() {
        return !valid商品名() || !valid価格();
    }

    private boolean valid商品名() {
        return "商品名".equals(ヘッダー_商品名.getStringCellValue());
    }
    private boolean valid価格() {
        return "価格".equals(ヘッダー_価格.getStringCellValue());
    }

    public Errors errors() {
        Errors errors = new Errors();
        if (!valid商品名()) errors.addLine("ヘッダーの1列目は「商品名」である必要があります。");
        if (!valid価格()) errors.addLine("ヘッダーの2列目は「価格」である必要があります。");
        return errors;
    }

}
