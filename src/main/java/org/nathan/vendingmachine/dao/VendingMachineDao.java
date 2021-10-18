package org.nathan.vendingmachine.dao;

import org.nathan.vendingmachine.dto.Snack;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;

public interface VendingMachineDao {
    void loadMachineStock(String filename) throws FileNotFoundException, VendingMachineDaoException;

    void saveMachineStock(String filename) throws IOException, VendingMachineDaoException;

    Map<String, Snack> getMachineStock() throws VendingMachineDaoException;

    Snack getSnack(String name) throws VendingMachineDaoException;

    void addSnack(String name, int count, BigDecimal price) throws VendingMachineDaoException;

    boolean removeSnack(String name);

    boolean editSnack(String originalName, String newName, int newCount, BigDecimal newPrice) throws VendingMachineDaoException;
}
