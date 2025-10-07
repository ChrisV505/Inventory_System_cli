package com.chrisV.InventorySystem.service;

import com.chrisV.InventorySystem.model.Category;
import com.chrisV.InventorySystem.model.Product;
import com.chrisV.InventorySystem.repo.ProductRepo;
import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.command.annotation.Command;
import org.springframework.shell.command.annotation.Option;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

@Service
@Command(command = "product-cli")
public class ProductService {

    @Autowired
    private ProductRepo repo;

    @Command(command = "list", description = "List all products in the inventory")
    public List<Product> listProducts(@Option(required = false)
                                          @UniqueElements  List<String> category) {

        if (category == null) return repo.findAll();

        try{
            List<Category> categoryList = category.stream()
                    .map(Category::valueOf)
                    .toList();
            return repo.findAllByCategory(categoryList);

        }catch(IllegalArgumentException e){
            System.out.println("Invalid category. Available categories are: " + Arrays.toString(Category.values()));
            return List.of();
        }

    }

    @Command(command = "add", description = "Add a new product to the inventory")
    public Product addProduct(@Option (required = true) String name,
                              Integer stock, Double price,
                              @Option(required = false)
                                  String code,
                              @Option(required = false, arityMin = 0, arityMax = 3)  List<String> categoryList) {

        //initialize category in case user doesn't provide any
        List<Category> category = null;

        //convert list of string to list of enum
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

    @Command(command = "delete", description = "Delete a product from the inventory")
    public void deleteProduct() {
        String temp = "test product";

        Product product = repo.findByName(temp);
        if (product != null) {
            repo.delete(product);
            System.out.println("Deleted product: " + product);


        }


    }

}
