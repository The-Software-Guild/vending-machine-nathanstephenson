package org.nathan.vendingmachine.service;

import org.nathan.vendingmachine.dao.AuditDao;
import org.nathan.vendingmachine.dao.AuditDaoException;
import org.nathan.vendingmachine.dao.VendingMachineDao;
import org.nathan.vendingmachine.dao.VendingMachineDaoException;
import org.nathan.vendingmachine.dto.Snack;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class VendingMachineServiceImpl implements VendingMachineServiceLayer {
    private VendingMachineDao vendingDao;
    private AuditDao auditDao;

    public VendingMachineServiceImpl(VendingMachineDao vendingDao, AuditDao auditDao) {
        this.vendingDao = vendingDao;
        this.auditDao = auditDao;
    }

    //CRUD operations
    @Override
    public Snack getSnack(int i) throws VendingMachineDaoException {
        return vendingDao.getSnack(i);
    }

    @Override
    public Map<Integer, Snack> getSnacks() throws VendingMachineDaoException {
        return vendingDao.getMachineStock();
    }

    @Override
    public void addSnack(String name, int count, BigDecimal price) throws VendingMachineDaoException, AuditDaoException {
        vendingDao.addSnack(name, count, price);
        logAudit("Added snack '" + name + "'");
    }

    @Override
    public void removeSnack(int i) throws VendingMachineDaoException, AuditDaoException {
        if (validateSnackExists(i)) {
            String name = vendingDao.getSnack(i).getName();
            vendingDao.removeSnack(i);
            logAudit("Removed snack '" + name + "'");
        } else {
            System.err.println("Snack does not exist.");
        }
    }

    @Override
    public void editSnack(int i, String newName, int newCount, BigDecimal newPrice) throws VendingMachineDaoException, AuditDaoException {
        if (validateSnackExists(i)) {
            System.err.println("Not yet implemented editSnack");
            logAudit("Edited snack '" + newName + "'");
        } else {
            System.err.println("Snack does not exist.");
        }
    }

    //service-specific functions
    @Override
    public void purchaseSnack(int i) throws VendingMachineDaoException, AuditDaoException {
        Snack s = vendingDao.getSnack(i);
        vendingDao.editSnack(i, s.getName(), s.getCount() - 1, s.getPrice());
        logAudit(s.getName() + " purchased.");
    }

    @Override
    public void loadStock(String filename) throws VendingMachineDaoException {
        try {
            vendingDao.loadMachineStock(filename);
        } catch (Exception e) {
            throw new VendingMachineDaoException("Error loading stock");
        }
    }

    @Override
    public void saveStock(String filename) throws VendingMachineDaoException {
        try {
            vendingDao.saveMachineStock(filename);
        } catch (Exception e) {
            throw new VendingMachineDaoException("Error saving stock");
        }
    }

    @Override
    public void logAudit(String operation) throws AuditDaoException {
        auditDao.logAudit(operation);
    }

    @Override
    public boolean validateSnackExists(int i) throws VendingMachineDaoException {
        return vendingDao.getMachineStock().containsKey(i);
    }

    @Override
    public boolean validateSnackSelection(int choice) throws VendingMachineDaoException {
        return vendingDao.getMachineStock().size() >= choice;
    }

    @Override
    public boolean validateFilename(String filename) {
        return !filename.contains(".");
    }

    @Override
    public BigDecimal fundsValue(List<Currency> coins) {
        BigDecimal total = new BigDecimal("0.00");
        for (Currency c : coins) {
            total = total.add(c.getValue());
        }
        return total;
    }

    @Override
    public boolean sufficientFunds(List<Currency> coins, BigDecimal price) {
        return fundsValue(coins).compareTo(price) >= 0;
    }


}
