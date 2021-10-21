package org.nathan.vendingmachine.dao;

import org.nathan.vendingmachine.dto.Audit;
import org.nathan.vendingmachine.dto.Snack;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AuditDaoImpl implements AuditDao{
    private String filename;

    public AuditDaoImpl(String filename){
        this.filename = filename;
    }

    @Override
    public void setFilename(String filename) {
        this.filename = filename;
    }

    @Override
    public void logAudit(String operation) throws AuditDaoException {
        try {
            PrintWriter out = new PrintWriter(new FileWriter(filename + ".txt", true));
            out.println(new Audit(LocalDateTime.now(), operation));
            out.flush();
            out.close();
        }catch (Exception e){
            throw new AuditDaoException("Failed to write to audit log.");
        }
    }
}
