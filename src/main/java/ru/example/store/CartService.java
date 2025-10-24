package ru.example.store;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class CartService {
    Map<String, Cart> carts;
    final private MainService mainService;

    public CartService(MainService mainService) {
        this.mainService = mainService;
    }

    @PostConstruct
    public void init() {
        carts = new HashMap<>();
    }

    Cart getCart(String sessionID) {
        return carts.computeIfAbsent(sessionID, Cart::new);
    }

    public void addProduct(String sessionID, String productID) {
        var cart = carts.computeIfAbsent(sessionID, Cart::new);
//        var cart = carts.computeIfAbsent(sessionID, id -> new Cart(id));
        cart.addProduct(mainService.getProduct(productID));
    }
}
