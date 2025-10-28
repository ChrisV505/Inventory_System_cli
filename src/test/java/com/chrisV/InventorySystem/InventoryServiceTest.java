package com.chrisV.InventorySystem;

import com.chrisV.InventorySystem.model.Category;
import com.chrisV.InventorySystem.model.Product;
import com.chrisV.InventorySystem.repo.ProductRepo;
import com.chrisV.InventorySystem.service.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@ExtendWith(MockitoExtension.class)
public class InventoryServiceTest {

    private List<Double> totalCalculateTest(List<Product> products) {
        List<Double> totals = new ArrayList<>(2);

        for(Product p : products) {
            double total = (Double) (p.getStock() * (Double.valueOf(String.valueOf(p.getPrice()))));
            totals.add(total);
        }
        return totals;
    }

    @Test
    void calculateTotalProductValue_ShouldReturnCorrectTotal() {
        // Test implementation goes here
        Product product1 = new Product("Product1", 10, 5.0, null, Category.BEVERAGE);
        Product product2 = new Product("Product2", 20, 3.0, null, Category.BEVERAGE);

        int total = 10;

        //act
        List<Double> totals = totalCalculateTest(List.of(product1, product2));
        List<Double> correctTotals = List.of(50.0, 60.0);

        assertEquals(correctTotals, totals);
    }


}
