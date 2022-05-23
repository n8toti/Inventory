/**
 * addProduct Controller is used to add products to thea ddProduct.fxml file. The user can add Products with associated
 * parts. With the ability to remove associated parts to the product that the user is adding. The save button adds the
 * product to the product list and redirects the user back to the main screen with the new product added. The associated
 * parts are stored in an objective list. The cancel button returns the user back the mainscreen without adding a part.
 * The user can search the part list and the part will be highlighted if found.
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

public class addProductController implements Initializable {
    Product addProduct = new Product(0,"",0.0,0,0,0);

    @FXML
    private TextField addProdInvText;

    @FXML
    private TextField addProdMaxText;

    @FXML
    private TextField addProdMinText;

    @FXML
    private TextField addProdNameText;

    @FXML
    private TextField addProdPriceText;

    @FXML
    private Button addProductButton;

    @FXML
    private TextField addProductSearch;

    @FXML
    private Button cancelProductButton;

    @FXML
    private TableColumn<Part, Integer> partID;

    @FXML
    private TableColumn<Part, Integer> partID1;

    @FXML
    private TableColumn<Part, Integer> partInventory;

    @FXML
    private TableColumn<Part, Integer> partInventory1;

    @FXML
    private TableColumn<Part, String> partName;

    @FXML
    private TableColumn<Part, String> partName1;

    @FXML
    private TableColumn<Part, Double> partPrice;

    @FXML
    private TableColumn<Part, Double> partPrice1;

    @FXML
    private TableView<Part> partsTable;

    @FXML
    private TableView<Part> partsTable1;

    @FXML
    private Button removePartButton;

    @FXML
    private Button saveAddProduct;

    @FXML
    void addProductButton(MouseEvent event) {
        Part add = partsTable.getSelectionModel().getSelectedItem();
        addProduct.addAssociatedParts(add);

    }

    @FXML
    void addProductSearch(KeyEvent event) {
        try
        {
            int id = Integer.parseInt(addProductSearch.getText());
            partsTable.getSelectionModel().select(Inventory.lookupPart(id));
        }
        catch(NumberFormatException ex) {
            String searched = addProductSearch.getText();
            for (Part e : Inventory.lookupPart(searched))
            {
                partsTable.getSelectionModel().select(e);
            }
        }
        if(addProductSearch.getText().isBlank())
        {
            partsTable.getSelectionModel().clearSelection();
        }

    }

    @FXML
    void cancelProducButton(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("sb.fxml")));
        Stage stage = (Stage) cancelProductButton.getScene().getWindow();
        stage.setScene(new Scene(root, 1100, 450));
        stage.show();
    }
    @FXML
    void removePartButton(MouseEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Action");
        alert.setHeaderText("Remove part?");
        alert.setContentText("Remove associated part from Product?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            Part delete = partsTable1.getSelectionModel().getSelectedItem();
            addProduct.deleteAssociatedPart(delete);
        }
    }

    @FXML
    void saveAddProduct(MouseEvent event) throws IOException {
        int newID = Inventory.allProducts.size() + 1000;
        String name = addProdNameText.getText();
        double price = 0;
        int inv = 0;
        int min = 0;
        int max = 0;
        boolean errorSent = false;

        try {
            price = Double.parseDouble(addProdPriceText.getText());
            inv = Integer.parseInt(addProdInvText.getText());
            min = Integer.parseInt(addProdMinText.getText());
            max = Integer.parseInt(addProdMaxText.getText());
        } catch (NumberFormatException ex) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Invalid Action");
            alert.setHeaderText("Invalid Data");
            alert.setContentText("The data entered is Invalid");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {}
            errorSent = true;
        }
        if (!errorSent)
            if (max > min && inv <= max) {
                addProduct.setId(newID);
                addProduct.setName(name);
                addProduct.setPrice(price);
                addProduct.setStock(inv);
                addProduct.setMin(min);
                addProduct.setMax(max);

                Inventory.addProduct(addProduct);
                Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("sb.fxml")));
                Stage stage = (Stage) cancelProductButton.getScene().getWindow();
                stage.setScene(new Scene(root, 1100, 450));
                stage.show();
            } else {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Invalid Action");
                alert.setHeaderText("Invalid Data");
                alert.setContentText("The data entered is Invalid");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                }
            }
    }


    public void initialize(URL url, ResourceBundle resourceBundle) {
        //specifying each colum in parts table to add data

        partID.setCellValueFactory(new PropertyValueFactory<Part, Integer>("id"));
        partName.setCellValueFactory(new PropertyValueFactory<Part, String>("name"));
        partInventory.setCellValueFactory(new PropertyValueFactory<Part, Integer>("stock"));
        partPrice.setCellValueFactory(new PropertyValueFactory<Part, Double>("price"));
        partsTable.refresh();
        //set data for parts table
        partsTable.setItems(Inventory.allParts);

        partID1.setCellValueFactory(new PropertyValueFactory<Part, Integer>("id"));
        partName1.setCellValueFactory(new PropertyValueFactory<Part, String>("name"));
        partInventory1.setCellValueFactory(new PropertyValueFactory<Part, Integer>("stock"));
        partPrice1.setCellValueFactory(new PropertyValueFactory<Part, Double>("price"));
        partsTable1.refresh();
        partsTable1.setItems(addProduct.getAllAssociatedParts());

    }

}