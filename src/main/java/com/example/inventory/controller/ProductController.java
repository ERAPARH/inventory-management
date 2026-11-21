package com.example.inventory.controller;

import com.example.inventory.Product;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ConcurrentHashMap<Long, Product> products =
            new ConcurrentHashMap<>();

    private final AtomicLong idGenerator = new AtomicLong();

    public ProductController() {
        addProduct(new Product(null, "Laptop", 10, 55000));
        addProduct(new Product(null, "Keyboard", 25, 1200));
        addProduct(new Product(null, "Monitor", 8, 12000));
    }

    private Product addProduct(Product product) {
        long id = idGenerator.incrementAndGet();
        product.setId(id);
        products.put(id, product);
        return product;
    }

    @GetMapping
    public List<Product> getProducts() {
        return products.values().stream()
                .sorted((a, b) -> a.getId().compareTo(b.getId()))
                .toList();
    }

    @PostMapping
    public Product createProduct(@RequestBody Product product) {
        return addProduct(product);
    }
}
