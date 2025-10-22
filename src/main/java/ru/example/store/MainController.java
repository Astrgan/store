package ru.example.store;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Comparator;
import java.util.TreeSet;
import java.util.stream.Collectors;

@Controller
public class MainController {
    private final MainService mainService;

    public MainController(MainService mainService) {
        this.mainService = mainService;
    }

    @GetMapping
    String index(Model model, HttpSession session){
        System.out.println(session.getId());



        model.addAttribute("products", mainService.getProducts());
        mainService.getProducts().stream().forEach(product -> System.out.println(product.images()));
        return "index";
    }
}
