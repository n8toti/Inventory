/**
 * addPartController adds parts to the part table and then directs user back to the main .fxml controller page with the
 * choice of inhouse or outsourced part. When the user saves the part they are redirected to the mainscreen with the new
 * part added to the parts table. IF the user cancels no part is added and they are redirected back to the mainscreen.
 */

package com.example.demo;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;


public class addPartController {

    Inventory inventory;

    @FXML
    private TextField addNameId;

    @FXML
    private Button addPartCancel;

    @FXML
    private TextField addPartId;

    @FXML
    private TextField addPartInv;

    @FXML
    private TextField addPartMax;

    @FXML
    private TextField addPartMin;

    @FXML
    private TextField addPartPrice;

    @FXML
    private Button addPartSave;

    @FXML
    private TextField addPartSwitch;

    @FXML
    private RadioButton inHouse;

    @FXML
    private Text inOrOut;

    @FXML
    private RadioButton outSourced;

    //when cancel is pressed it directs user back to the main screen
    @FXML
    void addPartCancel(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("sb.fxml")));
        Stage stage = (Stage) addPartCancel.getScene().getWindow();
        stage.setScene(new Scene(root, 1100, 450));
        stage.show();
    }

    @FXML
    void addPartSave(MouseEvent event) throws IOException {
        int newID = Inventory.allParts.size() + 1;
        int machineID = 0;
        int inv = 0;
        int min = 0;
        int max = 0;
        double price = 0.0;
        boolean errorSent = false;

        try
        {
            price = Double.parseDouble(addPartPrice.getText());
            inv = Integer.parseInt(addPartInv.getText());
            min = Integer.parseInt(addPartMin.getText());
            max = Integer.parseInt(addPartMax.getText());
        }
        catch(NumberFormatException ex) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Invalid Action");
            alert.setHeaderText("Invalid Data");
            alert.setContentText("The data entered is Invalid");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {}
            errorSent = true;
        }

        if (inHouse.isSelected())
        {
            InHouse addPart = new InHouse(0, "",0.0,0,0,0,0);

            try
            {
                machineID = Integer.parseInt(addPartSwitch.getText());
            }
            catch(NumberFormatException ex) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Invalid Action");
                alert.setHeaderText("Invalid Data");
                alert.setContentText("The data entered is Invalid");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {}
                errorSent = true;

            }


            String name = addNameId.getText();
            if(!errorSent) {
                if (min > max || name.equals("") || inv < 1 || price == 0 || machineID < 1 || inv < min || inv > max) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Invalid Action");
                    alert.setHeaderText("Invalid Data");
                    alert.setContentText("The data entered is Invalid");

                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == ButtonType.OK) {
                    }
                } else if (!errorSent) {
                    addPart.setId(newID);
                    addPart.setMachineId(machineID);
                    addPart.setName(name);
                    addPart.setMin(min);
                    addPart.setMax(max);
                    addPart.setPrice(price);
                    addPart.setStock(inv);

                    inventory.addPart(addPart);

                    Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("sb.fxml")));
                    Stage stage = (Stage) addPartCancel.getScene().getWindow();
                    stage.setScene(new Scene(root, 1100, 450));
                    stage.show();
                }
            }
        }
        else {
            String companyName = addPartSwitch.getText();
            Outsourced addPart = new Outsourced(0, "", 0.0, 0, 0, 0, "");
            String name = addNameId.getText();
            if (!errorSent) {
                if (min > max || name.equals("") || inv < 1 || price == 0 || companyName.equals("") || inv > max) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Invalid Action");
                    alert.setHeaderText("Invalid Data");
                    alert.setContentText("The data entered is Invalid");

                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == ButtonType.OK) {
                    }
                } else if (!errorSent) {

                    addPart.setId(newID);
                    addPart.setCompanyName(companyName);
                    addPart.setName(name);
                    addPart.setMin(min);
                    addPart.setMax(max);
                    addPart.setPrice(price);
                    addPart.setStock(inv);
                    inventory.addPart(addPart);

                    Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("sb.fxml")));
                    Stage stage = (Stage) addPartCancel.getScene().getWindow();
                    stage.setScene(new Scene(root, 1100, 450));
                    stage.show();
                }
            }
        }

    }

    @FXML
    void inHouseClick(MouseEvent event) {
        if (inHouse.isSelected())
        {
            outSourced.setSelected(false);
            inOrOut.setText("Machine Id");
        }

    }

    @FXML
    void outSourcedClick(MouseEvent event) {
        if (outSourced.isSelected())
        {
            inHouse.setSelected(false);
            inOrOut.setText("Company Name");
        }

    }

}
