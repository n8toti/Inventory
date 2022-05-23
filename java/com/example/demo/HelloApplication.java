/**  JavaDoc Index directory has the generated JavaDoc files-- is located in the main file
 *
 *   RUNTIME ERROR ENCOUNTERED
 *   During the production of the code I had encountered an error when trying to load a new scene. There were a few
 *   problems. One of which was that not all the controller code was correctly inputted. Another Problem was that the
 *   fx controller wasn't properly sourced in the .fxml page. I was wondering how it knew which to use and where it would
 *   be linked I compared my projects code with a default rendition that is created when you choose a java fx project and
 *   noticed that in the default .fxml file the controller code was specified. When importing a scene builder .fxml file
 *   the controller is not specified. Another Error was when I went to modify data. I was wondering how I could transfer
 *   data from one scene into another scene without the access to the text fields. After reading up on controllers I was
 *   able to load the controller of the next scene into the current scene which gave me access to the proper controller.
 *   I created a method to take the selected data and index of the selected table row and transfer it into the next
 *   controller.
 *
 *  FUTURE ENHANCEMENT
 *  When a part is associated with a Product I would decrement the available inventory for that part number after the
 *  product is added to the product list. If the part is directly tied to the product then they shouldn't count the part
 *  as in stock if the product is considered in stock and dependent on that part. There could also be more information
 *  added to the Part list about outsourced parts. More columns could be added to the table view to show if a part is
 *  outsourced or inhouse at a glance rather than having to modify the part to see the information. Each Individual part
 *  and product could be given an individual code that associates to the part and product. Information about where the
 *   item is stored could also be added to the inventory list.
 */


package com.example.demo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Parent;

import java.io.IOException;
import java.util.Objects;


public class HelloApplication extends Application {
    Inventory inv = new Inventory();
    @Override
    public void start(Stage stage) throws IOException {

        defaultData(inv);
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("sb.fxml")));
        stage.setScene(new Scene(root, 1100, 450));
        stage.show();
    }
public void defaultData(Inventory inv){
    //Create data for Parts in/out
    InHouse brakes = new InHouse(1, "Brakes", 15.99, 10, 1, 20,100);
    InHouse Wheel = new InHouse(2, "Wheel", 11.99, 16, 1, 20,101);
    InHouse Seat = new InHouse(3, "Seat", 15.99, 10, 1, 20,102);

    inv.addPart(brakes);
    inv.addPart(Wheel);
    inv.addPart(Seat);

    Outsourced rakes = new Outsourced(4, "rakes", 15.99, 10, 1, 20,"StpQk");
    Outsourced heel = new Outsourced(5, "heel", 11.99, 16, 1, 20,"TurnFst");
    Outsourced eat = new Outsourced(6, "eat", 15.99, 10, 1, 20,"SitDown");
    inv.addPart(rakes);
    inv.addPart(heel);
    inv.addPart(eat);
    //Create data for products

    Product GB = new Product(1000, "Giant Bike", 299.99, 5,1,20);
    Product Tri = new Product(1001,"Tricycle",99.99,3,1,20);
    inv.addProduct(GB);
    inv.addProduct(Tri);
    }
    public static void main(String[] args) {
        launch();
    }
}