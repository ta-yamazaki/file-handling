package file.handling.presentaion.controller.upload;

import file.handling.application.service.product.ProductService;
import file.handling.domain.model.product.ProductExcelFile;
import file.handling.domain.model.validation.ProductExcelValidator;
import file.handling.presentaion._error.Errors;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Controller
@RequestMapping("file-upload/products")
public class FileUploadController {

    ProductService productService;

    FileUploadController(ProductService productService) {
        this.productService = productService;
    }

    @InitBinder("uploadFile")
    public void InitBinder(WebDataBinder binder) {
        binder.setAllowedFields("excelFile");
    }

    @InitBinder("uploadFileAdmin")
    public void AdminInitBinder(WebDataBinder binder) {
        binder.setAllowedFields("excelFile");
    }

    @ModelAttribute("uploadFile")
    ProductExcelFile uploadFile() {
        return new ProductExcelFile();
    }
    @ModelAttribute("uploadFileAdmin")
    ProductExcelFile uploadFileAdmin() {
        return new ProductExcelFile();
    }

    @GetMapping
    String input() {
        return "product/fileUpload";
    }

    @PostMapping
    String fileUpload(@Validated @ModelAttribute("uploadFile") ProductExcelFile file,
                      BindingResult result,
                      Model model) throws IOException {
        if (result.hasErrors()) {
            Errors errors = new Errors();
            result.getAllErrors().forEach(objectError ->
                    errors.addLine(objectError.getDefaultMessage())
            );
            model.addAttribute("errorMessages", errors.message());
            return "product/fileUpload";
        }

        ProductExcelValidator validator = new ProductExcelValidator(file);

        if (validator.isInvalidContent()) {
            model.addAttribute("errorMessages", validator.errors().message());
            return "product/fileUpload";
        }

        productService.bulkRegister(file);
        model.addAttribute("message", "商品情報を一括登録しました。");
        return "product/fileUpload";
    }

    @PostMapping("/admin")
    @PreAuthorize("hasRole('admin')")
    String admin(@Validated @ModelAttribute("uploadFileAdmin") ProductExcelFile file,
                 BindingResult result,
                 Model model) {
        if (result.hasErrors()) {
            Errors errors = new Errors();
            result.getAllErrors().forEach(objectError ->
                    errors.addLine(objectError.getDefaultMessage())
            );
            model.addAttribute("errorMessages", errors.message());
            return "product/fileUpload";
        }

//        productService.upload(file);
        model.addAttribute("message", "商品情報を一括登録しました。");
        return "product/fileUpload";
    }
}
