package file.handling.presentaion.controller.report;

import file.handling.application.service.product.ProductService;
import file.handling.application.service.report.XlsxReport;
import file.handling.domain.model.product.Product;
import file.handling.domain.model.product.ProductId;
import file.handling.domain.model.product.Products;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("report")
class ReportController {

    ProductService productService;

    ReportController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping()
    public ModelAndView list(Model model) {
        Products products = productService.list();
        model.addAttribute("products", products);
        return new ModelAndView(new XlsxReport());
    }

}
