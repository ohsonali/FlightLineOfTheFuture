// File: Main.java
// Main class with program logic
package FLOTF;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.awt.*;
import java.io.*;
import java.io.IOException;
/**
 * Organizes main logic of program
 * @author Bernard Chan, Sonali Loomba
 * */
public class Main extends Application {
    public static void main(String[] args) throws IOException {
        TOParser readTO = new TOParser(Integer.parseInt(args[0]));
        readTO.parse();
        launch(args);
        if (PartInfo.getJCN() != null && PartInfo.getQuantity() != null && PartInfo.getCurrentPart() != null) {
            if (!PartInfo.getJCN().trim().isEmpty() && !PartInfo.getQuantity().trim().isEmpty()) {
                Autofiller f9006filler = new Autofiller();
                f9006filler.fill9006();
                if (!Desktop.isDesktopSupported()) {
                    System.out.println("Desktop Not Supported");
                    System.exit(0);
                } else {
                    Desktop.getDesktop().open(new File(Autofiller.getF9006path()));
                }
            }
        }
    }
    @Override
    public void start(Stage stage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("MainController.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}