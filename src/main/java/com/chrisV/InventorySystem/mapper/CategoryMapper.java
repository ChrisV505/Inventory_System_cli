package com.chrisV.InventorySystem.mapper;

import com.chrisV.InventorySystem.model.Category;

import java.util.Collections;
import java.util.List;

public class CategoryMapper {

    public static List<Category> mapToString(List<String> categoryList) {
        if (categoryList != null) {
            return categoryList.stream()
                    .map(Category::valueOf)
                    .toList();
        }
        return Collections.emptyList();
    }
}
