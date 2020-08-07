//File: UserEntryController.java
//UserEntryController class controls UserEntryController.fxml window

package sample;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * This class controls the window that takes in user entries for Job Control Number and Quantity that needs to be ordered
 * @author Bernard Chan, Sonali Loomba
 */

public class UserEntryController implements Initializable {
    /** fx id of TextField for user to enter Job Control Number */
    @FXML TextField jcn;
    /** fx id of TextField for user to enter quantity that needs to be ordered */
    @FXML TextField quantity;
    /** fx id of Button for user to order part and fill F9006 */
    @FXML Button order;
    /** fx id of Button for user to cancel order and return to display of parsed parts
     * from technical order MainController.fxml window*/
    @FXML Button cancel;
    /** fx id of Text displaying chosen part's number */
    @FXML Text partNumber;
    /** fx id of Text displaying chosen part's matching stock number*/
    @FXML Text nsn;
    /** fx id of Text displaying chosen part's quantity currently in stock*/
    @FXML Text stock;
    /** fx id of Text displaying chosen part's description*/
    @FXML Text description;

    /**
     * Initializes UserEntryController.fxml window
     * Sets part number, stock number, description, and quantity in stock from chosen part's relevant fields
     * @param url
     * @param rb
     */
    @Override
    public void initialize (URL url, ResourceBundle rb){
        partNumber.setText("Part Number: " + PartInfo.getCurrentPart().getPartNum().replaceAll("[#=]", "").trim());
        nsn.setText("NSN: " + PartInfo.getCurrentNSN().getNsn());
        description.setText("Description: " + PartInfo.getCurrentPart().getDescription());
        stock.setText("Quantity in Stock: " + Supply.checkInventory(PartInfo.getCurrentNSN().getNsn()));
    }

    /**
     * Stores job control number and quantity that user has entered
     * Closes program if both fields are entered
     * @param e button clicked
     *
     */
    public void order (ActionEvent e) {
        String JCN, Quantity;
        JCN = jcn.getText();
        Quantity = quantity.getText();
        PartInfo.addUserData(JCN, Quantity);

        if (!JCN.trim().isEmpty() && !Quantity.trim().isEmpty()) {
            jcn.clear();
            quantity.clear();

            Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
            stage.close();
        }
    }

    /**
     * Clears part chosen information
     * Returns to MainController.fxml window with TO part table and clears chosen part information
     * @param e button clicked
     * @throws Exception if MainController.fxml is unable to load
     */
    public void cancel (ActionEvent e) throws Exception {
        PartInfo.removePart();
        NSNScrape.clearNSNList();
        Parent root = FXMLLoader.load(getClass().getResource("MainController.fxml"));
        Scene scene = new Scene(root);
        Stage window = (Stage) ((Node) e.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

   /* Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Close Window, Are you sure?");
        alert.showAndWait();
    ButtonType result = alert.getResult();
    Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow() ;
        if(result == ButtonType.OK){
        if(stage != null){
            stage.close();
        }
    }*/
}
