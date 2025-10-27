package com.chrisV.InventorySystem;

import com.chrisV.InventorySystem.model.Category;
import com.chrisV.InventorySystem.model.Product;
import com.chrisV.InventorySystem.repo.ProductRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class InventoryRepositoryIntegretionTest {

    private List<Product> products = new ArrayList<>();

    @Autowired
    private ProductRepo productRepo;

    @BeforeEach
    void loadProducts() {
        Product p1 = new Product("Product1", 10, 5.0, null, Category.BEVERAGE);
        Product p2 = new Product("Product2", 12, 4.0, null, Category.DYE);
        products.addAll(List.of(p1, p2));
        System.out.println(products);
        productRepo.saveAll(products);
    }

    @Test
    void repoSaveAndFindsByName() {
        List<Product> saved = productRepo.findAll();
        assertThat(saved).isNotEmpty();
        assertThat(saved.get(0).getName()).isEqualTo("Product1");
    }

    @Test
    void repoFindAllByCategory() {
        productRepo.deleteByName("Product1");
        assertThat(productRepo.findByName("Product1")).isNull();

    }
}
