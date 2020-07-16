package sample;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class UserWindowController {

    //private Stage stage =(Stage) ((Node) e.getSource()).getScene().getWindow() ;
    @FXML TextField jcn, quantity;
    @FXML Button order, cancel;


    public void order (ActionEvent e) throws Exception{
        String JCN, Quantity;
        JCN = jcn.getText();
        Quantity = quantity.getText();
        Part.addUserData(JCN, Quantity);

        jcn.clear();
        quantity.clear();
        System.out.println(Part.getJCN());
        System.out.println(Part.getQuantity());

        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow() ;
        stage.close();

    }

    public void cancel (ActionEvent e) throws Exception{
        Part.removePart();
        ParsedInfo.clearNSNList();
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
