package ru.example.store;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import java.util.stream.Collectors;

@Service
public class MainService {

    List<Product> products;

    @PostConstruct
    void postConstruct() {
        try {
            products = new ObjectMapper().readValue(Files.readString(Path.of("./resources/products.json")), new TypeReference<>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Map<String, TreeSet<Product>> getAllProducts() {

        return products.stream().collect(Collectors.groupingBy(
                Product::cat,
                Collectors.toCollection(() ->
                        new TreeSet<>(Comparator.comparing(Product::id))
                )
        ));
    }

    public List<Product> getProducts() {

        return getAllProducts().values().stream().map(TreeSet::first).toList();
    }
}
