package com.chrisV.InventorySystem.service;

import org.springframework.shell.command.annotation.Command;
import org.springframework.shell.command.annotation.Option;
import org.springframework.stereotype.Service;

@Service
@Command(group = "stock-treshold")
public class StockTresholdService {

    private int maxStockTreshold;

    @Command(command = "max", description = "set max stock threshold")
    public void setMaxStockTreshold(@Option(required = true) int treshold) {
        if(treshold < 0) System.out.println("Treshold cannot be negative");
        this.maxStockTreshold = treshold;
        System.out.println("Max stock treshold set to: " + this.maxStockTreshold);
    }

    public int getMaxStockTreshold() {
        return maxStockTreshold;
    }


}
