package sample;


import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

public class UserWindowController {

    //private Stage stage =(Stage) ((Node) e.getSource()).getScene().getWindow() ;


    public void goTo9006 (ActionEvent e) throws Exception{

        /*
        Parent root = FXMLLoader.load(getClass().getResource("F9006.fxml"));
        Scene scene = new Scene(root);
        Stage window = (Stage) ((Node) e.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
        */
    }

    public void cancel (ActionEvent e) throws Exception{
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Close Window, Are you sure?");
        alert.showAndWait();
        ButtonType result = alert.getResult();
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow() ;
        if(result == ButtonType.OK){
            if(stage != null){
                stage.close();
            }
        }
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        Scene scene = new Scene(root);
        Stage window = (Stage) ((Node) e.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }
}
