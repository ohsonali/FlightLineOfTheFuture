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
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;
import org.apache.pdfbox.text.PDFTextStripper;

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
        String bernardFile = "C:/Users/berna_000/Desktop/my9006.pdf";
        String sonaliFile = "/Users/ohsonali/Documents/X-Force/Pages from 1C-17A-4-33.pdf";
        String sonaliConfig = "--module-path /Applications/javafx/javafx-sdk-11.0.2/lib --add-modules javafx.controls,javafx.fxml";


        launch(args);

        File file = new File(bernardFile);
        PDDocument document9006 = PDDocument.load(file);
        try {
            PDAcroForm pDAcroForm = document9006.getDocumentCatalog().getAcroForm();
            PDField field = pDAcroForm.getField("JCN");
            field.setValue("testing testing");
            field = pDAcroForm.getField("Quantity");
            field.setValue("Testing 2");
            document9006.save(file.getPath());
        } catch (IOException e) {
            e.printStackTrace();
        }

        /*
        PDFTextStripper pdfStripper = new PDFTextStripper();

        String text = pdfStripper.getText(document);

        System.out.println(text);

        document.close();
        */
    }


}
