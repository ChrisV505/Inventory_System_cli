package com.chrisV.InventorySystem.ui;

import com.chrisV.InventorySystem.model.Product;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

public class OutputFormatter {

    private static final ObjectMapper JSON = new ObjectMapper();
    private static final ObjectMapper XML = new ObjectMapper();

    public static String toTable(List<Product> products) {
        return TableUI.showTable(products);
    }

    public static String toJson(List<Product> products) {
        try {
            return JSON.writerWithDefaultPrettyPrinter().writeValueAsString(products);
        } catch(JsonProcessingException e) {
            return "Error converting to JSON: " + e.getMessage();
        }
    }

    public static String toXml(List<Product> products) {
        try {
            return XML.writerWithDefaultPrettyPrinter().writeValueAsString(products);
        } catch(JsonProcessingException e) {
            return "Error converting to XML: " + e.getMessage();
        }
    }

}
