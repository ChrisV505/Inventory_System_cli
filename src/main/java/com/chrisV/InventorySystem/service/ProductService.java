package com.chrisV.InventorySystem.service;

import com.chrisV.InventorySystem.model.Category;
import com.chrisV.InventorySystem.model.Product;
import com.chrisV.InventorySystem.repo.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.command.annotation.Command;
import org.springframework.shell.command.annotation.Option;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@Command(command = "product-cli")
public class ProductService {

    @Autowired
    private ProductRepo repo;

    @Command(command = "list-products", description = "List all products in the inventory")
    public List<Product> listProducts() {

        return null;
    }

    @Command(command = "add", description = "Add a new product to the inventory")
    public Product addProduct(@Option (required = true) String name,
                              Integer stock, Double price,
                              @Option(required = false)
                                  String code,
                              @Option(required = false) List<String> categoryList) {

        List<Category> category = null;

        if(categoryList != null) {
            category = categoryList.stream()
                    .map(Category::valueOf)
                    .toList();
        }

        Product product = Product.builder().name(name)
                .stock(stock)
                .price(BigDecimal.valueOf(price))
                .total(BigDecimal.valueOf(stock * price))
                .code(code)
                .category(category)
                .build();

        repo.save(product);
        return product;
    }
}
