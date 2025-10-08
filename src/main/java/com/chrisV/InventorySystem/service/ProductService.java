package com.chrisV.InventorySystem.service;

import com.chrisV.InventorySystem.mapper.CategoryMapper;
import com.chrisV.InventorySystem.model.Category;
import com.chrisV.InventorySystem.model.Product;
import com.chrisV.InventorySystem.repo.ProductRepo;
import com.chrisV.InventorySystem.ui.TableUI;
import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.shell.command.annotation.Command;
import org.springframework.shell.command.annotation.Option;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

@Service
@Command(group = "product-cli")
public class ProductService {

    @Autowired
    private ProductRepo repo;

    @Command(command = "list", description = "List all products in the inventory")
    public String listProducts(@Option(required = false) @UniqueElements  List<String> categories) {

        if (categories == null) return TableUI.showTable(repo.findAll()); // repo.findAll();

        try{
            List<Category> categoryList = CategoryMapper.mapToString(categories);
            return TableUI.showTable(repo.findAllByCategory(categoryList));

        }catch(IllegalArgumentException e){
            System.out.println("Invalid category. Available categories are: " + Arrays.toString(Category.values()));
            return List.of(String .valueOf(e)).toString();
        }
    }

    @Command(command = "usage-update", description = "Update stock of an existing product in the inventory")
    public String updateStock(@Option(required = true) String name, @Option(required = true) Integer stock) {
        Product existingProduct = repo.findByName(name);

        if(stock < 0) return "Stock cannot be negative";
        if(stock > existingProduct.getStock()) return "Stock to be reduced cannot be greater than existing stock";
        existingProduct.setStock((existingProduct.getStock() - stock));

        if(existingProduct.getPrice() != null){
            existingProduct.setTotal(BigDecimal.valueOf(stock * existingProduct.getPrice().doubleValue()));
        }
        repo.save(existingProduct);
        return "Updated stock for product: " + existingProduct.toString()   ;
    }

    @Command(command = "update", description = "Update an existing product in the inventory")
    public Product updateProduct(@Option(required = true) String name, Integer stock,
                                 @Option Double price,
                                 @Option String code,
                                 @Option(arityMin = 0, arityMax = 3) @UniqueElements List<String> categories) {

        Product existingProduct = repo.findByName(name);

        if (existingProduct != null) {
            if (stock != null) existingProduct.setStock(stock);
            if (price != null) existingProduct.setPrice(BigDecimal.valueOf(price));
            if (stock != null && price != null) existingProduct.setTotal(BigDecimal.valueOf(stock * price));
            if (code != null) existingProduct.setCode(code);
            if (categories != null) {
                try {
                    List<Category> category = CategoryMapper.mapToString(categories);
                    existingProduct.setCategory(category);
                } catch (IllegalArgumentException e) {
                    System.out.println("Invalid category. Available categories are: " + Arrays.toString(Category.values()));
                }
            }
            return repo.save(existingProduct);
        }
        return null;
    }

    @Command(command = "add", description = "Add a new product to the inventory")
    public Product addProduct(@Option (required = true) String name,
                              Integer stock, Double price,
                              @Option(arityMin = 0, arityMax = 3) @UniqueElements  List<String> categories,
                              @Option String code) {

        //initialize category in case user doesn't provide any
        List<Category> category = null;

        System.out.println(code);

        if(code == null) code = "nothing";

        System.out.println(code);
        //convert list of string to list of enum
        try{
            category = CategoryMapper.mapToString(categories);
            Product product = Product.builder()
                    .name(name)
                    .stock(stock)
                    .price(BigDecimal.valueOf(price))
                    .total(BigDecimal.valueOf(stock * price))
                    .category(category)
                    .code(code)
                    .build();

            repo.save(product);
            return product;
        }catch(IllegalArgumentException e){
            System.out.println("Invalid category. Available categories are: " + Arrays.toString(Category.values()));
        }
        return null;
    }

    @Command(command = "delete", description = "Delete a product from the inventory")
        public void deleteProduct(@Option(required = true) String name){
        Product product = repo.findByName(name);
        if (product != null) {
            repo.delete(product);
            System.out.println("Deleted product: " + product);
        } else {
            System.out.println("Product with name " + name + " not found");
        }
    }
}
