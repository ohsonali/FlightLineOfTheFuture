package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.text.PDFTextStripper;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }


    public static void main(String[] args) throws IOException{
        // hi 
        String bernardConfig = "--module-path C:/Users/berna_000/Desktop/javafx-sdk-12/javafx-sdk-11.0.2/lib --add-modules javafx.controls,javafx.fxml";
        String bernardFile = "C:/Users/berna_000/Desktop/Pages from 1C-17A-4-33.pdf";
        String sonaliFile = "/Users/ohsonali/Documents/X-Force/Pages from 1C-17A-4-33.pdf";


        launch(args);
        System.out.println("hello");
        File file = new File(bernardFile);
        PDDocument document = PDDocument.load(file);

        PDFTextStripper pdfStripper = new PDFTextStripper();

        String text = pdfStripper.getText(document);

        System.out.println(text);

        document.close();
    }


}
