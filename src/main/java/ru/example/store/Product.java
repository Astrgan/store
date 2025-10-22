package ru.example.store;

import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

public record Product(
        String id,
        String cat,
        String name,
        String prise,
        List<String> images,
        String description,
        String miniDescription
) {
    public Product {
        // если JSON не содержит description → подгружаем из файла
        if (description == null || description.isBlank()) {
            description = loadDescriptionFromResources(id);
        }

        // если images не указаны → пустой список
        if (images == null) {
            images = loadImagesFromResources(id);
        }
    }

    private static List<String> loadImagesFromResources(String id){
        try {
            return Files.list(Path.of("resources/static/product/" + id)).filter(path -> path.toString().endsWith(".jpeg") || path.toString().endsWith(".jpg") || path.toString().endsWith(".webp")).map(path -> path.subpath(2,5).toString()).sorted().toList();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static String loadDescriptionFromResources(String id) {
        try {
            return new String(Files.readAllBytes(Path.of("resources/static/product/" + id + "/description.txt")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    @Override
    public String toString() {
        return "Product{" +
                "id='" + id + '\'' +
                '}';
    }
}