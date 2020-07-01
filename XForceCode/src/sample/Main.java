package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.io.*;
import java.io.IOException;

import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.text.PDFTextStripperByArea;
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

        int x = Utils.rightStart - Utils.leftStart;


        File file = new File(Utils.bernardFile);
        PDDocument document = PDDocument.load(file);

        File file2 = new File(Utils.bernard9006File);
        PDDocument document2 = PDDocument.load(file2);

        //OurPDFTextStripper pdfStripper = new OurPDFTextStripper();
        //pdfStripper.setStartPage(3);
        //pdfStripper.setEndPage(4);
        //String text = pdfStripper.getText(document);
/*
1. Find column widths, table height, and table starting height (which should be the same
   between both pages), and find the starting x coordinate for the two types of pages
   (left leaning and right leaning). Stores these as unchanging variables.
2. Find out whether the page is right or left leaning by looking at the x-coordinate
   of the first character on the page (which should be F in Figure)
3. Look at the Figure and Index No. rectangle. Put all the y-coordinate positions of the top of the
   character into an array (ypos) -> put in new class. Put all the figure and index numbers into an
   ArrayList of String[7] Arrays.
4. Calculate rectangle dimensions (next y-coordinate position - current y-coordinate position).
   First one could be the first line under figure and index no. Note: make sure to grab
   the top of the letter, might be very sensitive.
5. For every row, find the column entries. [outer for loop is row, inner for loop is column].
   Look at every specific box and put that information into the array.
6. Create PartInfo objects using information in the ArrayList of Arrays.

Base assumptions: Pages are either left or right leaning and for each respective type, the first
character has the same x-coordinate as the rest of the same type.
The height of the table is always the same, and the table always starts at the same pixel height.
The width of the columns are always the same.

*/


        PDFTextStripperByArea pdfStripperArea = new PDFTextStripperByArea();
        Rectangle rect = new Rectangle( Utils.leftStart, Utils.startHeight ,Utils.leftStart + Utils.figureWidth, 29);
        pdfStripperArea.addRegion("Figure and Index", rect);
        PDPage docPage = document.getPage(2);
        pdfStripperArea.extractRegions(docPage);
        String regionText = pdfStripperArea.getTextForRegion("Figure and Index");
        System.out.println(regionText);


        PDFLeanFinder leanFinder = new PDFLeanFinder();
        leanFinder.setStartPage(3);
        leanFinder.setEndPage(4);
        leanFinder.getText(document);
        System.out.println(leanFinder.left);
        for (int i = 0; i < leanFinder.yPos.size(); i ++) {
            System.out.println(leanFinder.yPos.get(i));
        }


        //System.out.println(text);

        document.close();

        launch(args);




        if (Part.getJCN() != null && Part.getQuantity() != null && Part.getCurrentPart() != null) {

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
            if (!Desktop.isDesktopSupported()) {
                System.out.println("not supported");
                System.exit(0);
            }
            Desktop desktop = Desktop.getDesktop();
            desktop.open(file2);
        }
    }


}
