package file.handling.presentaion.controller.product;

import file.handling.application.service.product.ProductService;
import file.handling.domain.model.product.Product;
import file.handling.domain.model.product.ProductId;
import file.handling.domain.model.product.ProductImageFile;
import file.handling.presentaion._error.Errors;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

@Controller
@RequestMapping("products")
class ProductRegisterController {

    ProductService productService;

    ProductRegisterController(ProductService productService) {
        this.productService = productService;
    }

    @InitBinder("product")
    public void productInitBinder(WebDataBinder binder) {
        binder.setAllowedFields(
//                "productId.value",
                "number",
                "name",
                "fileName"
        );
    }

    @InitBinder("imageFile")
    public void imageFileInitBinder(WebDataBinder binder) {
        binder.setAllowedFields("imageFile");
    }

    @GetMapping("/add")
    String newOne(Model model) {
        model.addAttribute("product", new Product());
        return "product/add";
    }

    @PostMapping("/add")
    String register(@ModelAttribute("product") Product product,
                    @Validated @ModelAttribute("imageFile") ProductImageFile imageFile,
                    BindingResult result,
                    Model model,
                    RedirectAttributes redirectAttributes) throws IOException, NoSuchAlgorithmException {
        model.addAttribute("product", product);

        if (result.hasErrors()) {
            Errors errors = new Errors();
            result.getAllErrors().forEach(objectError ->
                    errors.addLine(objectError.getDefaultMessage())
            );
            model.addAttribute("errorMessages", errors.message());
            return "product/add";
        }

        productService.register(product, imageFile);
        redirectAttributes.addFlashAttribute("message", "商品情報を登録しました。");
        return "redirect:/products";
    }

//    @PostMapping(path = "/{productId}", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    @PostMapping("/{productId}")
    String update(@PathVariable("productId") ProductId productId,
                  @ModelAttribute("product") Product product,
                  @ModelAttribute("imageFile") ProductImageFile imageFile,
                  RedirectAttributes redirectAttributes) {
        productService.update(productId, product, imageFile);
        redirectAttributes.addFlashAttribute("message", "商品情報を更新しました。");
        return "redirect:/products/" + productId.toString();
    }

}
