package com.example.demo;

/**
 * Inherits from the part class with a private variable companyName specific to Outsourced parts
 */
public class Outsourced extends Part{
    private String companyName;

    public Outsourced(int id, String name, Double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyName() {
        return companyName;
    }
}
