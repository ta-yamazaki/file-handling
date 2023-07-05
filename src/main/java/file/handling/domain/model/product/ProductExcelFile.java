package file.handling.domain.model.product;

import file.handling.presentaion._error.Errors;
import file.handling.presentaion.controller.product.validate.*;
import org.apache.poi.ss.usermodel.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * 商品CSVファイル
 */
public class ProductExcelFile {
    @NotEmptyFile(message = "ファイルを選択してください。")
    @MaxFilenameLength(message = "ファイル名の最大文字数は100文字です。", max = 100)
    @DisallowedFilenameCharacters(
            message = "ファイル名には次の文字は使えません: \\ / : ; * ? \" < > |",
            characters = {"\\", "/", ":", ";", "*", "?", "\"", "<", ">", "|"}
    )
    @AllowedFileExtension(
            message = "ファイルの拡張子は「.xsl」、「.xslx」または「.csv」のみ選択可能です。",
            extensions = {"xsl", "xlsx", "csv"}
    )
    @AllowedMediaType(
            message = "ファイルのメディアタイプがエクセルまたはCSV形式ではありません。",
            mediaTypes = {"application/vnd.ms-excel", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", "text/csv"}
    )
//    @AllowedFileFormatBinary(
//            message = "ファイルのフォーマットがCSV形式ではありません。",
//            formats = {"csv"}
//    )
    MultipartFile excelFile;

    public ProductExcelFile() {
    }

    public ProductExcelFile(MultipartFile excelFile) {
        this.excelFile = excelFile;
    }

    public MultipartFile excelFile() {
        return excelFile;
    }

    public boolean isEmpty() {
        return excelFile.isEmpty();
    }

}
