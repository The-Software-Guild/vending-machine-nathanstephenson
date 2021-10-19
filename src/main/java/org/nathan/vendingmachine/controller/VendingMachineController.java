package org.nathan.vendingmachine.controller;

import org.nathan.vendingmachine.dao.AuditDaoException;
import org.nathan.vendingmachine.dao.VendingMachineDaoException;
import org.nathan.vendingmachine.service.Currency;
import org.nathan.vendingmachine.service.VendingMachineServiceLayer;
import org.nathan.vendingmachine.ui.VendingMachineView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class VendingMachineController {
    private VendingMachineServiceLayer sl;
    private VendingMachineView view;
    private List<Currency> coins;

    public VendingMachineController(VendingMachineServiceLayer service, VendingMachineView view){
        this.sl = service;
        this.view = view;
        coins = new ArrayList<>();
    }

    public void run() throws VendingMachineDaoException, AuditDaoException {
        boolean cont = true;
        File f = new File("vendingmachine.txt");
        if (f.exists()) {
            sl.loadStock("vendingmachine");
        }
        int menuOption = view.menu();
        while (cont) {
            switch (menuOption) {
                case 0:
                    cont = false;
                    break;
                case 1:
                    coins = view.insertCoins();
                    break;
                case 2:
                    sl.purchaseSnack(view.selectSnack(sl.getSnacks()));
                    break;
                case 3:
                    sl.loadStock(view.loadStock());
                    break;
                case 4:
                    sl.saveStock(view.saveStock());
                    break;
            }
        }
    }
}
