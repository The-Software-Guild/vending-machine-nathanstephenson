package org.nathan.vendingmachine.dao;

import org.nathan.vendingmachine.dto.Audit;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.time.LocalDate;

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
            PrintWriter out = new PrintWriter(new FileWriter(filename + ".txt"));
            out.println(new Audit(LocalDate.now(), operation));
            out.flush();
            out.close();
        }catch (Exception e){
            throw new AuditDaoException("Failed to write to audit log.");
        }
    }
}
