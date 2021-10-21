package org.nathan.vendingmachine;

import org.nathan.vendingmachine.controller.VendingMachineController;
import org.nathan.vendingmachine.dao.AuditDaoException;
import org.nathan.vendingmachine.dao.AuditDaoImpl;
import org.nathan.vendingmachine.dao.VendingMachineDaoException;
import org.nathan.vendingmachine.dao.VendingMachineDaoImpl;
import org.nathan.vendingmachine.service.InsufficientFundsException;
import org.nathan.vendingmachine.service.VendingMachineServiceImpl;
import org.nathan.vendingmachine.ui.UserIOConsoleImpl;
import org.nathan.vendingmachine.ui.VendingMachineView;

public class App {
    public static void main(String[] args) throws AuditDaoException, VendingMachineDaoException, InsufficientFundsException {
        VendingMachineController controller = new VendingMachineController(
                new VendingMachineServiceImpl(new VendingMachineDaoImpl(), new AuditDaoImpl("AuditLog")), new VendingMachineView(new UserIOConsoleImpl()));
        controller.run();
    }
}
