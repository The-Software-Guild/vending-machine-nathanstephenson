package org.nathan.vendingmachine.dao;

import org.nathan.vendingmachine.dto.Snack;

import java.io.*;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class VendingMachineDaoImpl implements VendingMachineDao {
    private static final String DELIMITER = "::";
    private Map<String, Snack> snacks = new HashMap<>();

    public VendingMachineDaoImpl() {

    }

    @Override
    public void loadMachineStock(String filename) throws VendingMachineDaoException {
        Map<String, Snack> loadedSnacks = new HashMap<>();
        try {
            Scanner in = new Scanner(new BufferedReader(new FileReader(filename + ".txt")));
            while (in.hasNextLine()) {
                Snack s = unmarshallSnack(in.nextLine());
                loadedSnacks.put(s.getName(), s);
            }
        } catch (Exception e) {
            throw new VendingMachineDaoException("Error loading from persistent storage.");
        }
        snacks = loadedSnacks;
    }

    @Override
    public void saveMachineStock(String filename) throws VendingMachineDaoException {
        try {
            PrintWriter out = new PrintWriter(new FileWriter(filename + ".txt"));
            for (Snack s : snacks.values()) {
                out.println(marshallSnack(s));
                out.flush();
            }
            out.close();
        } catch (Exception e) {
            throw new VendingMachineDaoException("Error saving data.");
        }
    }

    @Override
    public Map<String, Snack> getMachineStock() throws VendingMachineDaoException {
        try {
            return snacks;
        } catch (Exception e) {
            throw new VendingMachineDaoException("Error accessing data.");
        }
    }

    @Override
    public Snack getSnack(String name) throws VendingMachineDaoException {
        try {
            return snacks.get(name);
        } catch (Exception e) {
            throw new VendingMachineDaoException("Error retrieving data.");
        }
    }

    @Override
    public void addSnack(String name, int count, BigDecimal price) throws VendingMachineDaoException {
        try {
            snacks.put(name, new Snack(name, count, price));
        } catch (Exception e) {
            throw new VendingMachineDaoException("Error adding snack to collection.");
        }
    }

    @Override
    public boolean removeSnack(String name) {
        if (snacks.containsKey(name)) {
            snacks.remove(name);
            return true;
        }
        System.err.println("Snack does not exist to be removed.");
        return false;
    }

    @Override
    public boolean editSnack(String originalName, String newName, int newCount, BigDecimal newPrice) throws VendingMachineDaoException {
        if (snacks.containsKey(originalName)) {
            snacks.remove(originalName);
            try {
                snacks.put(newName, new Snack(newName, newCount, newPrice));
            } catch (Exception e) {
                throw new VendingMachineDaoException("Error updating snack.");
            }
            return true;
        }
        System.err.println("Snack does not exist to be edited.");
        return false;
    }

    private String marshallSnack(Snack snack) {
        return snack.getName() + DELIMITER + snack.getCount() + DELIMITER + snack.getPrice();
    }

    private Snack unmarshallSnack(String str) {
        String[] data = str.split(DELIMITER);
        return new Snack(data[0], Integer.parseInt(data[1]), new BigDecimal(data[2]));
    }
}
