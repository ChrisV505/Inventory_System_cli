package com.chrisV.InventorySystem.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;
    private Integer stock;
    private BigDecimal price;
    private BigDecimal total;
    private String code;
    private List<Category> category;

    // Convenience constructor taking primitive/double and a single Category (useful in tests)
    public Product(String name, int stock, double price, String code, Category singleCategory) {
        this.name = name;
        this.stock = stock;
        this.price = BigDecimal.valueOf(price);
        this.code = code;
        if (singleCategory == null) {
            this.category = new ArrayList<>();
        } else {
            this.category = List.of(singleCategory);
        }
    }



    @Override
    public String toString() {
        return "\nProduct{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", stock=" + stock +
                ", price=" + price +
                ", total=" + total +
                ", code='" + code + '\'' +
                ", category=" + category +
                '}';
    }




}
