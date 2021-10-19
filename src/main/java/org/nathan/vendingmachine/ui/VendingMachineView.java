package org.nathan.vendingmachine.ui;

import org.nathan.vendingmachine.dto.Snack;
import org.nathan.vendingmachine.service.Currency;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class VendingMachineView {
    private UserIO io;

    public VendingMachineView(UserIO userIO) {
        this.io = userIO;
    }

    public int menu() {
        io.print("---Vending Machine---");
        io.print("1 - Insert coins");
        io.print("2 - Select Snack");
        io.print("3 - Load Stock");
        io.print("4 - Save Stock");
        io.print("0 - Exit");
        return io.readInt("Please choose the operation to perform:", 0, 4);
    }

    public List<Currency> insertCoins() {
        List<Currency> coins = new ArrayList<>();
        boolean cont;
        do {
            coins.add(io.readCurrency("Please enter the name of the coin you would like to insert:"));
            cont = io.readBool("Add another coin?");
        } while (cont);
        return coins;
    }

    public int selectSnack(Map<Integer, Snack> snacks) {
        for (Map.Entry<Integer, Snack> s : snacks.entrySet()) {
            io.print(s.getKey() + " - " + s.getValue().getName() + " (" + s.getValue().getCount() + " remaining) - " + s.getValue().getPrice().doubleValue());
        }
        int choice;
        boolean validChoice = false;
        do {
            choice = io.readInt("Choice: ");
            for (int key : snacks.keySet()) {
                validChoice = choice == key || validChoice;
            }
            System.out.println(validChoice ? "" : "Please select a valid snack.");
        } while (!validChoice);
        return choice;
    }

    public String loadStock() {
        return io.readString("What is the name of the file to load from?");
    }

    public String saveStock() {
        return io.readString("What is the name of the file to save to?");
    }
}
