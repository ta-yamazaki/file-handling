package file.handling.presentaion.controller.product;

import file.handling.application.service.product.ProductService;
import file.handling.domain.model.product.Product;
import file.handling.domain.model.product.ProductId;
import file.handling.domain.model.product.ProductImageFile;
import file.handling.domain.model.product.Products;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
                    @ModelAttribute("imageFile") ProductImageFile imageFile,
                    Model model,
                    RedirectAttributes redirectAttributes) {
        productService.register(product, imageFile);
        model.addAttribute("product", product);
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
