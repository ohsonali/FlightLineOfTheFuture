package sample;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UserWindowController implements Initializable {

    //private Stage stage =(Stage) ((Node) e.getSource()).getScene().getWindow() ;
    @FXML TextField jcn, quantity;
    @FXML Button order, cancel;
    @FXML Text partNumber, nsn, stock, description;
    String quantityInStock = "Not Found";

    @Override
    public void initialize (URL url, ResourceBundle rb){
        File csv = new File(Utils.bernardcsv);
        try {
            BufferedReader csvReader = new BufferedReader(new FileReader(csv));
            String row;
            while ((row = csvReader.readLine()) != null) {
                String[] data = row.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
                if (PartInfo.getCurrentNSN().getNsn().replaceAll("-", "").trim().equals(data[1])) {
                    quantityInStock = data[9];
                }
            }

        }
        catch (IOException io){
            io.printStackTrace();
        }
        partNumber.setText("Part Number: " + PartInfo.getCurrentPart().getPartNum());
        nsn.setText("NSN: " + PartInfo.getCurrentNSN().getNsn());
        description.setText("Description: " + PartInfo.getCurrentPart().getDescription());
        stock.setText("Quantity in Stock: " + quantityInStock);
    }

    public void order (ActionEvent e) throws Exception{
        String JCN, Quantity;
        JCN = jcn.getText();
        Quantity = quantity.getText();
        PartInfo.addUserData(JCN, Quantity);

        jcn.clear();
        quantity.clear();
        System.out.println(PartInfo.getJCN());
        System.out.println(PartInfo.getQuantity());

        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow() ;
        stage.close();

    }

    public void cancel (ActionEvent e) throws Exception{
        PartInfo.removePart();
        NSNScrape.clearNSNList();
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
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
