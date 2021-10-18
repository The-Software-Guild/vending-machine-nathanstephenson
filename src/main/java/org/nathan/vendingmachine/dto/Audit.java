package org.nathan.vendingmachine.dto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Audit {
    private LocalDate date;
    private String operation;

    public Audit(LocalDate date, String operation){
        this.date = date;
        this.operation = operation;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String toString(){
        return date.format(DateTimeFormatter.ofPattern("dd MM yyyy")) + ": " + operation;
    }
}
