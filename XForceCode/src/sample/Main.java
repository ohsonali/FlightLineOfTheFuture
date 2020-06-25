package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.*;
import java.io.IOException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;
import org.apache.pdfbox.text.PDFTextStripper;
import java.awt.Desktop;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    public static void main(String[] args) throws IOException {
        String bernardConfig = "--module-path C:/Users/berna_000/Desktop/javafx-sdk-12/javafx-sdk-11.0.2/lib --add-modules javafx.controls,javafx.fxml";
        String bernardFile = "C:/Users/berna_000/Desktop/Pages from 1C-17A-4-33.pdf";
        String sonaliFile = "/Users/ohsonali/Documents/X-Force/Pages from 1C-17A-4-33.pdf";
        String sonaliConfig = "--module-path /Applications/javafx/javafx-sdk-11.0.2/lib --add-modules javafx.controls,javafx.fxml";
        String sonali9006File = "/Users/ohsonali/Documents/X-Force/my9006.pdf";


        launch(args);

        if (Part.getJCN() != null && Part.getQuantity() != null && Part.getCurrentPart() != null) {
            File file = new File(sonaliFile);
            PDDocument document = PDDocument.load(file);

            File file2 = new File(sonali9006File);
            PDDocument document2 = PDDocument.load(file2);

            PDFTextStripper pdfStripper = new PDFTextStripper();

            String text = pdfStripper.getText(document);

            System.out.println(text);

            document.close();

            try {
                PDAcroForm pDAcroForm = document2.getDocumentCatalog().getAcroForm();
                PDField field = pDAcroForm.getField("JCN");
                field.setValue(Part.getJCN());
                field = pDAcroForm.getField("Quantity");
                field.setValue(Part.getQuantity());
                document2.save(file2.getPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
            /*if (!Desktop.isDesktopSupported()) {
                System.out.println("not supported");
                System.exit(0);
            }
            Desktop desktop = Desktop.getDesktop();
            desktop.open(file2);*/
        }
    }


}
