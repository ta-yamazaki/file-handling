package file.handling.presentaion.controller.product;

import file.handling.application.service.product.ProductService;
import file.handling.domain.model.product.ProductId;
import file.handling.domain.model.product.Product;
import file.handling.domain.model.product.Products;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("products")
class ProductReferenceController {

    ProductService productService;

    ProductReferenceController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping()
    String list(Model model) {
        Products products = productService.list();
        model.addAttribute("products", products);
        return "product/list";
    }

    @GetMapping("/{productId}")
    String find(@PathVariable("productId") ProductId productId,
                   Model model) {
        Product product = productService.productOf(productId);
        model.addAttribute("product", product);
        return "product/detail";
    }

}
