/**
 * The startController is the controller used on the mainscreen-- the sb.fxml file. It shows two populated table views
 * with parts and product. The user can add a part or product, modify parts or products, delete parts or products, and
 * exit the system.
 */

package com.example.demo;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

public class startController implements Initializable {

    @FXML
    private Button addPart;

    @FXML
    private Button deleteProduct;

    @FXML
    private Button partDelete;

    @FXML
    private TableColumn<Part, Integer> partID;

    @FXML
    private TableColumn<Part, Integer> partInventory;

    @FXML
    private Button partModify;

    @FXML
    private TableColumn<Part, String> partName;

    @FXML
    private TableColumn<Part, Double> partPrice;

    @FXML
    private TextField partSearch;

    @FXML
    private TableView<Part> partsTable;

    @FXML
    private Button productAdd;

    @FXML
    private TableColumn<Product, Integer> productID;

    @FXML
    private TableColumn<Product, Integer> productInventory;

    @FXML
    private Button productModify;

    @FXML
    private TableColumn<Product, String> productName;

    @FXML
    private TableColumn<Product, Double> productPrice;

    @FXML
    private TextField productSearch;

    @FXML
    private TableView<Product> productTable;
    @FXML
    private Button systemClose;

    @FXML
    void addProduct(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("addProduct.fxml")));
        Stage stage = (Stage) productAdd.getScene().getWindow();
        stage.setScene(new Scene(root, 940, 700));
        stage.show();

    }
    @FXML
    void closeSystem(MouseEvent event) {
    javafx.application.Platform.exit();
    }

    @FXML
    void deletePart(MouseEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Action");
        alert.setHeaderText("Delete Part");
        alert.setContentText("Delete part?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            Part partToDelete = partsTable.getSelectionModel().getSelectedItem();
            Inventory.deletePart(partToDelete);
            partDelete.requestFocus();
            partsTable.getSelectionModel().clearSelection();
        }
    }

    @FXML
    void deleteProduct(MouseEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Action");
        alert.setHeaderText("Delete Product");
        alert.setContentText("Delete Product? If there are Parts Associated with this product you cannot delete.");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            Product prodToDelete = productTable.getSelectionModel().getSelectedItem();
            if (prodToDelete.getAllAssociatedParts().isEmpty()) {
                Inventory.deleteProduct(prodToDelete);
                deleteProduct.requestFocus();
                productTable.getSelectionModel().clearSelection();
            }
        }

    }

    @FXML
    void modifyPart(MouseEvent event) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("modifyPart.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) partModify.getScene().getWindow();
        stage.setScene(new Scene(root, 700, 700));
        stage.show();
        Part updatePart = partsTable.getSelectionModel().getSelectedItem();
        modPartController controller = loader.getController();
        int index = partsTable.getSelectionModel().getSelectedIndex();
        controller.setModPart(updatePart, index);
    }

    @FXML
    void modifyProduct(MouseEvent event) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("modifyProduct.fxml"));
        Parent root = loader.load();;
        Stage stage = (Stage) productModify.getScene().getWindow();
        stage.setScene(new Scene(root, 950, 700));
        stage.show();
        Product modProd = productTable.getSelectionModel().getSelectedItem();
        modifyProductController controller = loader.getController();
        int index = productTable.getSelectionModel().getSelectedIndex();
        controller.setModProduct(modProd, index);
    }
    @FXML
    void partAdd(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("addPart.fxml")));
        Stage stage = (Stage) addPart.getScene().getWindow();
        stage.setScene(new Scene(root, 700, 700));
        stage.show();

    }

    @FXML
    void searchPart(KeyEvent event) {
        try
        {
            int id = Integer.parseInt(partSearch.getText());
            partsTable.getSelectionModel().select(Inventory.lookupPart(id));
        }
        catch(NumberFormatException ex) {
            String searched = partSearch.getText();
            for (Part e : Inventory.lookupPart(searched))
            {
                partsTable.getSelectionModel().select(e);
            }
        }
        if(partSearch.getText().isBlank())
        {
            partsTable.getSelectionModel().clearSelection();
        }

    }

    @FXML
    void searchProduct(KeyEvent event) {
        try
        {
            int id = Integer.parseInt(productSearch.getText());
            productTable.getSelectionModel().select(Inventory.lookupProduct(id));
        }
        catch(NumberFormatException ex) {
            String searched = productSearch.getText();
            for (Product e : Inventory.lookupProduct(searched))
            {
                productTable.getSelectionModel().select(e);
            }
        }
        if(productSearch.getText().isBlank())
        {
            productTable.getSelectionModel().clearSelection();
        }
    }
//this code populates the tables
   @Override
   public void initialize(URL url, ResourceBundle resourceBundle) {
        //specifying each colum in parts table to add data

       partID.setCellValueFactory(new PropertyValueFactory<Part, Integer>("id"));
       partName.setCellValueFactory(new PropertyValueFactory<Part, String>("name"));
       partInventory.setCellValueFactory(new PropertyValueFactory<Part, Integer>("stock"));
       partPrice.setCellValueFactory(new PropertyValueFactory<Part, Double>("price"));
       partsTable.refresh();
       //set data for parts table
       partsTable.setItems(Inventory.allParts);



        //specifying each column in product table to add data
        productID.setCellValueFactory(new PropertyValueFactory<Product, Integer>("id"));
        productName.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));
        productPrice.setCellValueFactory(new PropertyValueFactory<Product, Double>("price"));
        productInventory.setCellValueFactory(new PropertyValueFactory<Product, Integer>("stock"));
        productTable.refresh();
        //set data for product table
        productTable.setItems(Inventory.allProducts);
    }

}