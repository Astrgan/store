package ru.example.store;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class Cart {
    private String id;
    private Map<Product, Integer> products = new HashMap<>();
    private LocalDateTime update = LocalDateTime.now();

    public Map<Product, Integer> getProducts() {
        return products;
    }

    public Cart(String id) {
        this.id = id;
        this.update = LocalDateTime.now();
    }

    public void addProduct(Product productID){
//        products.put(productID, products.getOrDefault(productID, 0) + 1);
        products.put(productID, 1);
        this.update = LocalDateTime.now();
    }
}
