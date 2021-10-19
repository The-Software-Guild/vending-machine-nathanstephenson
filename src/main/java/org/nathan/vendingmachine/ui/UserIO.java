package org.nathan.vendingmachine.ui;

import org.nathan.vendingmachine.service.Currency;

public interface UserIO {
    void print(String message);

    String readString(String prompt);

    int readInt(String prompt);

    int readInt(String prompt, int min, int max);

    Currency readCurrency(String prompt);

    boolean readBool(String prompt);
}
