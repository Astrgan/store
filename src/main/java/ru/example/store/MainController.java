package ru.example.store;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainController {
    private final MainService mainService;
    private final CartService cartService;

    public MainController(MainService mainService, CartService cartService) {
        this.mainService = mainService;
        this.cartService = cartService;
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

    @GetMapping("/cart")
    String index(Model model, HttpSession session) {
        model.addAttribute("cart", cartService.getCart(session.getId()));
        model.addAttribute("sum", cartService.getCart(session.getId()).getProducts().keySet().stream().mapToDouble(p->Double.parseDouble(p.prise().replace(" ", ""))).sum());
        return "cart";
    }

    @GetMapping("/add/{id}")
    String add(HttpServletRequest request, @PathVariable String id, HttpSession session){
        String referer = request.getHeader("Referer");
        System.out.println("Previous page: " + referer);
        cartService.addProduct(session.getId(), id);
        // например, вернуть обратно
        return "redirect:" + (referer != null ? referer : "/");
    }

    @GetMapping("/product/{id}")
    String product(Model model, HttpSession session, @PathVariable String id) {
        model.addAttribute("product", mainService.getProduct(id));
        return "product";
    }
}
