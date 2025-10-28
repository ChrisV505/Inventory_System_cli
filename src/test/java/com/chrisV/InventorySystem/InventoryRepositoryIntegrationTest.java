package com.chrisV.InventorySystem;

import com.chrisV.InventorySystem.model.Category;
import com.chrisV.InventorySystem.model.Product;
import com.chrisV.InventorySystem.repo.ProductRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class InventoryRepositoryIntegrationTest {

    @Autowired
    private ProductRepo productRepo;

    @BeforeEach
    void loadProducts() {
        Product p1 = new Product("Product1", 10, 5.0, null, Category.BEVERAGE);
        Product p2 = new Product("Product2", 12, 4.0, null, Category.DYE);
        productRepo.saveAll(List.of(p1, p2));
    }

    @Test
    void repoSaveAndFindsByName() {
        List<Product> saved = productRepo.findAll();
        assertThat(saved).isNotEmpty();
        assertThat(saved).extracting(Product::getName)
                .containsExactlyInAnyOrder("Product1", "Product2");
    }

    @Test
    void repoDeleteProduct() {
        productRepo.deleteByName("Product1");
        assertThat(productRepo.findByName("Product1")).isNull();
    }

    @Test
    void repoUpdateProduct() {
        Product product = productRepo.findByName("Product2");
        assertThat(product).isNotNull();

        product.setPrice(BigDecimal.valueOf(221.02));
        productRepo.saveAndFlush(product);

        assertThat(productRepo.findByName("Product2").getPrice()).isEqualByComparingTo(BigDecimal.valueOf(221.02));
    }

}
