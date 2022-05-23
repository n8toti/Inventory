/**
 * The modifyProductController allows the user to modify the product that they select from the mainscreen. They can
 * associate parts with the product and remove associated parts from the product. When the user saves they are
 * redirected back to the mainscreen with the product modified. If they add associated parts to the product they will
 * have to remove them before deleting the product.
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

public class modifyProductController implements Initializable {
    Product prod;
    int index = 0;
    public void setModProduct(Product prod, int index)
    {
        this.index = index;
        this.prod = prod;
        modifyProductIDText.setText(Integer.toString(prod.getId()));
        modProdInvText.setText(Integer.toString(prod.getStock()));
        modProdNameText.setText(prod.getName());
        modProdMinText.setText(Integer.toString(prod.getMin()));
        modProdMaxText.setText(Integer.toString(prod.getMax()));
        modProdPriceText.setText(Double.toString(prod.getPrice()));
        associatedPartsTable();

    }
    private void associatedPartsTable() {
        if(!prod.getAllAssociatedParts().isEmpty())
        {
            partID1.setCellValueFactory(new PropertyValueFactory<Part, Integer>("id"));
            partName1.setCellValueFactory(new PropertyValueFactory<Part, String>("name"));
            partInventory1.setCellValueFactory(new PropertyValueFactory<Part, Integer>("stock"));
            partPrice1.setCellValueFactory(new PropertyValueFactory<Part, Double>("price"));
            modPartsTable1.refresh();
            modPartsTable1.setItems(prod.getAllAssociatedParts());
        }
    }

    @FXML
    private Button modAddProductButton;

    @FXML
    private Button modCancelProductButton;

    @FXML
    private TableView<Part> modPartsTable;

    @FXML
    private TableView<Part> modPartsTable1;

    @FXML
    private TextField modProdInvText;

    @FXML
    private TextField modProdMaxText;

    @FXML
    private TextField modProdMinText;

    @FXML
    private TextField modProdNameText;

    @FXML
    private TextField modProdPriceText;

    @FXML
    private TextField modProdSearch;

    @FXML
    private Button modRemovePartButton;

    @FXML
    private TextField modifyProductIDText;

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
    private Button saveModProduct;

    @FXML
    void modAddProductButton(MouseEvent event) {
        Part add = modPartsTable.getSelectionModel().getSelectedItem();
        prod.addAssociatedParts(add);
        associatedPartsTable();
    }

    @FXML
    void modCancelProducButton(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("sb.fxml")));
        Stage stage = (Stage) modCancelProductButton.getScene().getWindow();
        stage.setScene(new Scene(root, 1100, 450));
        stage.show();
    }

    @FXML
    void modProdSearch(KeyEvent event) {
        try
        {
            int id = Integer.parseInt(modProdSearch.getText());
            modPartsTable.getSelectionModel().select(Inventory.lookupPart(id));
        }
        catch(NumberFormatException ex) {
            String searched = modProdSearch.getText();
            for (Part e : Inventory.lookupPart(searched))
            {
                modPartsTable.getSelectionModel().select(e);
            }
        }
        if(modProdSearch.getText().isBlank())
        {
            modPartsTable.getSelectionModel().clearSelection();
        }

    }

    @FXML
    void modRemovePartButton(MouseEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Action");
        alert.setHeaderText("Remove part?");
        alert.setContentText("Remove associated part from Product?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK)
        {
            Part delete = modPartsTable1.getSelectionModel().getSelectedItem();
            prod.deleteAssociatedPart(delete);
            associatedPartsTable();
        }
    }

    @FXML
    void saveModProduct(MouseEvent event) throws IOException{
        String name = modProdNameText.getText();
        double price = 0;
        int inv = 0;
        int min = 0;
        int max = 0;
        boolean errorSent = false;
        try {
            price = Double.parseDouble(modProdPriceText.getText());
            inv = Integer.parseInt(modProdInvText.getText());
            min = Integer.parseInt(modProdMinText.getText());
            max = Integer.parseInt(modProdMaxText.getText());
        } catch (NumberFormatException ex) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Invalid Action");
            alert.setHeaderText("Invalid Data");
            alert.setContentText("The data entered is Invalid");


            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {}
            errorSent = true;
        }
        if(!errorSent) {
            if (max > min && inv <= max) {
                prod.setName(name);
                prod.setPrice(price);
                prod.setStock(inv);
                prod.setMin(min);
                prod.setMax(max);

                Inventory.updateProduct(index, prod);
                Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("sb.fxml")));
                Stage stage = (Stage) saveModProduct.getScene().getWindow();
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

    }
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //specifying each colum in parts table to add data

        partID.setCellValueFactory(new PropertyValueFactory<Part, Integer>("id"));
        partName.setCellValueFactory(new PropertyValueFactory<Part, String>("name"));
        partInventory.setCellValueFactory(new PropertyValueFactory<Part, Integer>("stock"));
        partPrice.setCellValueFactory(new PropertyValueFactory<Part, Double>("price"));
        modPartsTable.refresh();
        //set data for parts table
        modPartsTable.setItems(Inventory.allParts);
    }
}
