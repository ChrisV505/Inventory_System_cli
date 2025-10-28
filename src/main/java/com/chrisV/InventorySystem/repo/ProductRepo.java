package com.chrisV.InventorySystem.repo;

import com.chrisV.InventorySystem.model.Category;
import com.chrisV.InventorySystem.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepo extends JpaRepository<Product, Integer> {

    List<Product> findAllByCategory(List<Category> categoryNameList);

    Product findByName(String name);
    Product deleteByName(String name);
}
