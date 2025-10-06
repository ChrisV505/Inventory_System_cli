package com.chrisV.InventorySystem.service;

import com.chrisV.InventorySystem.model.Product;
import org.springframework.shell.command.annotation.Command;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Command(command = "product-cli")
public class ProductService {

    @Command(command = "list-products", description = "List all products in the inventory")
    public List<Product> listProducts() {

        return null;
    }

    @Command(command = "add", description = "Add a new product to the inventory")
    public Product addProduct(String name, String description, double price, int quantity) {
        return null;
    }




}
