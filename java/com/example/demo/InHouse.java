package com.example.demo;

/**
 * Inherits from the part class with a new private variable machineId specific to InHouse class
 */

public class InHouse extends Part{

    private int machineId;


    public InHouse(int id, String name, Double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }

    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }

    public int getMachineId() {
        return machineId;
    }
}
