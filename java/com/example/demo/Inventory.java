/**
 * Inventory class is used to create a list of parts and  products
 */

package com.example.demo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Inventory {
    public static  ObservableList<Part> allParts = FXCollections.observableArrayList();
    public static  ObservableList<Product> allProducts = FXCollections.observableArrayList();

    public static void addPart(Part newPart) {
        allParts.add(newPart);
    }
    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }
    public static Part lookupPart(int partID) {
        for (Part e : allParts)
        {
            if (e.getId() == partID)
            {
                return e;
            }
        }
        return null;
    }
    public static Product lookupProduct(int productID) {
        for (Product e : allProducts)
        {
            if (e.getId() == productID) {
                return e;
            }
        }
        return null;
    }
    public static ObservableList<Part> lookupPart(String partName){
        ObservableList<Part> part = FXCollections.observableArrayList();
        for (Part allPart : allParts) {
            if (allPart.getName().toUpperCase().contains(partName.toUpperCase())) {
                part.add(allPart);
            }
        }
        return part;
    }
    public static ObservableList<Product> lookupProduct(String productName) {
        ObservableList<Product> prod = FXCollections.observableArrayList();
        for (Product allProduct : allProducts) {
            if (allProduct.getName().toUpperCase().contains(productName.toUpperCase())) {
                prod.add(allProduct);
            }
        }
        return prod;
    }
    public static void updatePart(int index, Part selectedPart) {
        allParts.set(index, selectedPart);
    }
    public static void updateProduct(int index, Product selectedProduct){
        allProducts.set(index, selectedProduct);
    }

    public static boolean deletePart(Part selectedPart) {
        return allParts.remove(selectedPart);
    }
    public static boolean deleteProduct(Product selectedProduct) {
        return allProducts.remove(selectedProduct);
    }

    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }
}
