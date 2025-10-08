package com.chrisV.InventorySystem.ui;

import com.chrisV.InventorySystem.model.Product;
import com.chrisV.InventorySystem.repo.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.command.annotation.Command;
import org.springframework.shell.command.annotation.Option;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.table.*;
import java.util.*;

@Command(group = "Demo Commands")
@ShellComponent
public class TestingUI {


//    public void PersonTableCommands() {
//        // initialize random data once
//        Random random = new Random();
//        String[] names = {"Alice", "Bob", "Charlie", "Diana", "Ethan", "Fiona"};
//        String[] cities = {"New York", "Chicago", "Miami", "Los Angeles", "Houston"};
//
//        for (int i = 0; i < 5; i++) {
//            String name = names[random.nextInt(names.length)];
//            int age = 18 + random.nextInt(40);
//            String city = cities[random.nextInt(cities.length)];
//            people.add(new Person(i + 1, name, age, city));
//        }
//    }

    @Autowired
    private ProductRepo repo;


    public static String showTable(List<Product> products) {
        var fields = Product.class.getDeclaredFields();
        int columns = fields.length;

        // Header row with all field names
        Object[][] data = new Object[products.size() + 1][columns];
        for (int i = 0; i < columns; i++) {
            data[0][i] = fields[i].getName();
        }

        // Data rows with all field values
        for (int i = 0; i < products.size(); i++) {
            Product p = products.get(i);
            Object[] row = new Object[columns];
            for (int j = 0; j < columns; j++) {
                fields[j].setAccessible(true);
                try {
                    row[j] = fields[j].get(p);
                } catch (IllegalAccessException e) {
                    row[j] = "N/A";
                }
            }
            data[i + 1] = row;
        }

        TableModel model = new ArrayTableModel(data);
        TableBuilder tableBuilder = new TableBuilder(model);
        tableBuilder.addFullBorder(BorderStyle.fancy_heavy);
        tableBuilder.on(CellMatchers.table()).addAligner(SimpleHorizontalAligner.center);

        return tableBuilder.build().render(350);
    }


//    // Simple data class
//    static class Person {
//        private final int id;
//        private final String name;
//        private final int age;
//        private final String city;
//
//        public Person(int id, String name, int age, String city) {
//            this.id = id;
//            this.name = name;
//            this.age = age;
//            this.city = city;
//        }
//        public int getId() { return id; }
//        public String getName() { return name; }
//        public int getAge() { return age; }
//        public String getCity() { return city; }
//    }
}
