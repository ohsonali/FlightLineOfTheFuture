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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

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
First entry will always be the figure number.

*/


        PDFTextStripperByArea pdfStripperArea = new PDFTextStripperByArea();
        PDFLeanFinder leanFinder = new PDFLeanFinder();
        pdfStripperArea.setSortByPosition(true);
        leanFinder.setSortByPosition(true);
        leanFinder.setStartPage(3);
        leanFinder.setEndPage(4);
        leanFinder.getText(document);
        System.out.println(leanFinder.left);

        Collections.sort(leanFinder.yPos);
        leanFinder.yPos.add(Utils.startHeight+Utils.tableHeight);
        for (int i = 0; i < leanFinder.yPos.size(); i ++) {
            System.out.println(leanFinder.yPos.get(i));
        }



//        Rectangle rect = new Rectangle( Utils.leftStart + leanFinder.x, Utils.startHeight, Utils.figureWidth, Utils.tableHeight);
//        pdfStripperArea.addRegion("Figure and Index", rect);
//        PDPage docPage = document.getPage(3);
//        pdfStripperArea.extractRegions(docPage);
//        String regionText = pdfStripperArea.getTextForRegion("Figure and Index");
//        System.out.println(regionText);

        ArrayList<Integer> xPos = new ArrayList<>();
        xPos.add(Utils.figureWidth);
        xPos.add(Utils.partWidth);
        xPos.add(Utils.cageWidth);
        xPos.add(Utils.descWidth);
        xPos.add(Utils.unitWidth);
        xPos.add(Utils.usableWidth);
        xPos.add(Utils.smrWidth);

        for (int i = 0; i < leanFinder.yPos.size()-1; i++) {
            int height = leanFinder.yPos.get(i+1)-leanFinder.yPos.get(i);
            int currentColumn = Utils.leftStart + leanFinder.x;
            String[] currentRow = new String[xPos.size()];
            ParsedInfo.parts.add(currentRow);

            for (int j = 0; j<xPos.size(); j++){
                String name = Integer.toString(i) + Integer.toString(j);
                Rectangle rectangle = new Rectangle(currentColumn,leanFinder.yPos.get(i), xPos.get(j), height);
                //PDFTextStripperByArea pdf = new PDFTextStripperByArea();
                pdfStripperArea.addRegion(name, rectangle);
                PDPage page = document.getPage(2);
                pdfStripperArea.extractRegions(page);
                currentRow[j]= pdfStripperArea.getTextForRegion(name);
                //System.out.println(pdf.getTextForRegion(name));
                currentColumn += xPos.get(j);
            }
        }

        for (int i = 0; i <ParsedInfo.parts.size(); i++) {
            String[] partLines = stringLines(ParsedInfo.parts.get(i)[1]);
            if (partLines.length > 1) {
                String[] cageLines = stringLines(ParsedInfo.parts.get(i)[2]);
                String[] smrLines = stringLines(ParsedInfo.parts.get(i)[6]);
                for (int j = 0; j < partLines.length; j++) {
                    String[] newRow = new String[xPos.size()];
                    if (j == 0) {
                        newRow[0] = ParsedInfo.parts.get(i)[0];
                        newRow[3] = ParsedInfo.parts.get(i)[3];
                        newRow[4] = ParsedInfo.parts.get(i)[4];
                        newRow[5] = ParsedInfo.parts.get(i)[5];
                    } else {
                        newRow[0] = "";
                        newRow[3] = "";
                        newRow[4] = "";
                        newRow[5] = "";
                    }
                    newRow[1] = partLines[j];
                    newRow[2] = cageLines[j];
                    newRow[6] = smrLines[j];
                    ParsedInfo.parts.add(i + j + 1, newRow);
                }
                ParsedInfo.parts.remove(i);
            }
        }



        for (int i = 0; i<ParsedInfo.parts.size(); i++){
            for(int j = 0; j<7; j++){
                System.out.print(ParsedInfo.parts.get(i)[j]+ "         ");
            }
            System.out.println();
        }
        System.out.println("hi");
        System.out.println(stringLines(ParsedInfo.parts.get(16)[3]).length);

//
//        for every entry in ypos - the last entry = y:
//            for every column:
//                create new rectangle using info from row column
//                create string name
//                add string to arraylist of arrays
//                pdfstripperArea.addregion(, rect);
//
//         extract regions doc page
//         for every name






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
    public static String[] stringLines(String str){
        return str.split("\r\n|\r|\n");
    }
}
