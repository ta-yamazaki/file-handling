package file.handling.presentaion.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
class TopController {
    @GetMapping("/")
    String top() {
        return "redirect:/products";
    }
}
