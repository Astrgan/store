package ru.example.store;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainController {
    private final MainService mainService;

    public MainController(MainService mainService) {
        this.mainService = mainService;
    }

    @GetMapping
    String index(Model model, HttpSession session, @RequestParam(defaultValue = "none") String cat) {
        System.out.println(session.getId());
        if (cat.equals("none")) {
            model.addAttribute("products", mainService.getProducts());
        } else {
            model.addAttribute("products", mainService.getAllProducts().get(cat));
        }
        return "index";
    }

    @GetMapping("/product/{id}")
    String product(Model model, HttpSession session, @PathVariable String id) {
        model.addAttribute("product", mainService.getProduct(id));
        return "product";
    }
}
