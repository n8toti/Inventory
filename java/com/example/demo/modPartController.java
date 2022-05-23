/**
 * The modPartController is the controller for modifyPart.fxml file. The user is able to select a part from the table
 * on the mainscreen and edit the part. They can change the inhouse or outsourced. When the save button is clicked they
 * are redirected back to the mainscreen with where the new values are displayed on the table.
 */
package com.example.demo;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;


public class modPartController {
    Part part;
    int index = 0;

    public void setModPart(Part part, int index)
    {

        this.index = index;
        this.part = part;
        modifyPartID.setText(Integer.toString(part.getId()));
        modPartInv.setText(Integer.toString(part.getStock()));
        modPartName.setText(part.getName());
        modPartMin.setText(Integer.toString(part.getMin()));
        modPartMax.setText(Integer.toString(part.getMax()));
        modPartPrice.setText(Double.toString(part.getPrice()));
        if(part instanceof InHouse)
        {
            //modOutSourced.setDisable(true);
            modInHouse.setSelected(true);
            modOutSourced.setSelected(false);
            modPartSwitch.setText(String.valueOf(((InHouse) part).getMachineId()));
        }
        else
        {
            inOrOut.setText("Company Name");
            modOutSourced.setSelected(true);
            modInHouse.setSelected(false);
            modPartSwitch.setText(String.valueOf(((Outsourced) part).getCompanyName()));
        }

    }
    @FXML
    private Text inOrOut;

    @FXML
    private RadioButton modInHouse;

    @FXML
    private RadioButton modOutSourced;

    @FXML
    private Button modPartCancel;

    @FXML
    private TextField modPartInv;

    @FXML
    private TextField modPartMax;

    @FXML
    private TextField modPartMin;

    @FXML
    private TextField modPartName;

    @FXML
    private TextField modPartPrice;

    @FXML
    private Button modPartSave;

    @FXML
    private TextField modPartSwitch;

    @FXML
    private TextField modifyPartID;



    @FXML
    void modPartCancel(MouseEvent event) throws IOException {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("sb.fxml")));
            Stage stage = (Stage) modPartCancel.getScene().getWindow();
            stage.setScene(new Scene(root, 1100, 450));
            stage.show();
    }


    @FXML
    void modPartSave(MouseEvent event) throws IOException {
        int id = part.getId();
        String modName = modPartName.getText();
        double modPrice = 0.0;
        int modStock = 0;
        int min = 0;
        int max = 0;
        boolean errorSent = false;
        try {
            modPrice = Double.parseDouble(modPartPrice.getText());
            modStock = Integer.parseInt(modPartInv.getText());
            min = Integer.parseInt(modPartMin.getText());
            max = Integer.parseInt(modPartMax.getText());
        } catch (NumberFormatException ex) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Invalid Action");
            alert.setHeaderText("Invalid Data");
            alert.setContentText("The data entered is Invalid");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
            }
            errorSent = true;
        }
        if (!errorSent) {
            if (min < max && modStock <= max) {
                if (modInHouse.isSelected()) {
                    InHouse modPart = new InHouse(0, "", 0.0, 0, 0, 0, 0);
                    modPart.setId(id);
                    modPart.setName(modName);
                    modPart.setPrice(modPrice);
                    modPart.setStock(modStock);
                    modPart.setMin(min);
                    modPart.setMax(max);

                    try {
                        modPart.setMachineId(Integer.parseInt(modPartSwitch.getText()));
                    } catch (NumberFormatException ex) {
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setTitle("Invalid Action");
                        alert.setHeaderText("Invalid Data");
                        alert.setContentText("machine ID must be an integer");
                        Optional<ButtonType> result = alert.showAndWait();
                        if (result.get() == ButtonType.OK) {
                        }
                    }
                    Inventory.updatePart(index, modPart);
                }
            else {
                Outsourced modPart = new Outsourced(0, "", 0.0, 0, 0, 0, "");
                modPart.setId(id);
                modPart.setName(modName);
                modPart.setPrice(modPrice);
                modPart.setStock(modStock);
                modPart.setMin(min);
                modPart.setMax(max);
                modPart.setCompanyName(modPartSwitch.getText());
                Inventory.updatePart(index, modPart);

            }
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("sb.fxml")));
            Stage stage = (Stage) modPartSave.getScene().getWindow();
            stage.setScene(new Scene(root, 1100, 450));
            stage.show();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Invalid Action");
            alert.setHeaderText("Invalid Data");
            alert.setContentText("The data entered is Invalid");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {}
            }
        }
    }


    @FXML
    void outSourcedClick(MouseEvent event) {
        if (modOutSourced.isSelected())
        {
            modInHouse.setSelected(false);
            inOrOut.setText("Company Name");
        }

    }

    @FXML
    void inHouseClick(MouseEvent event) {
        if (modInHouse.isSelected())
        {
            modOutSourced.setSelected(false);
            inOrOut.setText("Machine Id");
        }

    }

}
