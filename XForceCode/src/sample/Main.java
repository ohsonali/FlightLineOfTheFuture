package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.awt.*;
import java.io.*;
import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("MainController.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    public static void main(String[] args) throws IOException {
        /*
        //Testing nsn scraper
        for (int i = 0; i<NSNScrape.getScrapedNSNs().size(); i++){
            for(int j = 0; j<NSNScrape.getScrapedNSNs().get(i).length; j++){
                System.out.print(NSNScrape.getScrapedNSNs().get(i)[j]+ "         ");
            }
            System.out.println();
        }
        */
        Utils.bernard = Boolean.parseBoolean(args[0]);

        TOParser readTO = new TOParser(Utils.bernard, Integer.parseInt(args[1]));
        readTO.parse();

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
   character into an array (ypos) -> p
4. Calculate rectangle dimensions (next y-coordinate position - current y-coordinate position).
   First one could be the first line under figure and index no. Note: make sure to grab
   the top of the letter, might be very sensitive.
5. For every row, find the column entries. [outer for loop is row, inner for loop is column].
   Look at every specific box and put that information into the array.
6. Create Part objects using information in the ArrayList of Arrays.

Base assumptions: Pages are either left or right leaning and for each respective type, the first
character has the same x-coordinate as the rest of the same type.
The height of the table is always the same, and the table always starts at the same pixel height.
The width of the columns are always the same.
First entry will always be the figure number.
ut in new class. Put all the figure and index numbers into an
   ArrayList of String[7] Arrays.
*/

    /*
    for (int i = 0; i < leanFinder.yPos.size(); i ++) {
                System.out.println(leanFinder.yPos.get(i));
            }
    */


//        Rectangle rect = new Rectangle( Utils.leftStart + leanFinder.x, Utils.startHeight, Utils.figureWidth, Utils.tableHeight);
//        pdfStripperArea.addRegion("Figure and Index", rect);
//        PDPage docPage = document.getPage(3);
//        pdfStripperArea.extractRegions(docPage);
//        String regionText = pdfStripperArea.getTextForRegion("Figure and Index");
//        System.out.println(regionText);

        /*
        //Testing TO Parser
        for (int i = 0; i<TOParser.parts.size(); i++){
            for(int j = 0; j<7; j++){
                System.out.print(TOParser.parts.get(i)[j]+ "         ");
            }
            System.out.println();
        }
        */

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

        launch(args);

        if (PartInfo.getJCN() != null && PartInfo.getQuantity() != null && PartInfo.getCurrentPart() != null) {
            if (!PartInfo.getJCN().trim().isEmpty() && !PartInfo.getQuantity().trim().isEmpty()) {
                Autofiller f9006filler = new Autofiller(Utils.bernard);
                f9006filler.fill9006();

                if (Utils.bernard) {
                    if (!Desktop.isDesktopSupported()) {
                        System.out.println("not supported");
                        System.exit(0);
                    }
                    Desktop desktop = Desktop.getDesktop();
                    desktop.open(new File(Utils.bernard9006File));
                } else {
                /*
                if (!Desktop.isDesktopSupported()) {
                    System.out.println("not supported");
                    System.exit(0);
                }
                Desktop desktop = Desktop.getDesktop();
                desktop.open(new File(Utils.sonali9006File));
                */
                }
            }
        }
    }
}
