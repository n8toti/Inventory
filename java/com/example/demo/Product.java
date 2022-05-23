/***
 * used to create a list of products to add to the inventory
 */

package com.example.demo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Product {


    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    //Product Constructor alt+insert
    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    //product set methods
    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public void setMax(int max) {
        this.max = max;
    }
    //product get methods

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }

    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }
    public void addAssociatedParts(Part part){
        associatedParts.add(part);
    }
    public boolean deleteAssociatedPart(Part selectedAssociatedPart)
    {
        associatedParts.remove(selectedAssociatedPart);
        return true;
    }
    public ObservableList<Part> getAllAssociatedParts(){
        return associatedParts;
    }
}