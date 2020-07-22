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
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.text.PDFTextStripperByArea;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;
import org.apache.pdfbox.text.PDFTextStripper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
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
        TOParser readTO = new TOParser(true, 3);
        readTO.parse();

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

            try {
                PDAcroForm pDAcroForm = document2.getDocumentCatalog().getAcroForm();

                for (PDField field : pDAcroForm.getFields()) {
                    if (field.getFieldType().equals("Tx")) {
                        field.setValue("");
                    }
                }

                //Part
                PDField field = pDAcroForm.getField("JCN");
                field.setValue(PartInfo.getJCN().trim());
                field = pDAcroForm.getField("Quantity");
                field.setValue(PartInfo.getQuantity().trim());
                field = pDAcroForm.getField("Stock");
                field.setValue(PartInfo.getCurrentNSN().getNsn().trim());

                //TOParser
                field = pDAcroForm.getField("Fig");
                field.setValue(TOParser.getFigureNumber().trim());
                field = pDAcroForm.getField("Vol");
                field.setValue(TOParser.getVolume().trim());
                field = pDAcroForm.getField("TO");
                field.setValue(TOParser.getTechnicalOrderNum().trim());

                //Part
                field = pDAcroForm.getField("Index");
                field.setValue(PartInfo.getCurrentPart().getIndex().replaceAll("-", "").trim());
                field = pDAcroForm.getField("Part");
                field.setValue(PartInfo.getCurrentPart().getPartNum().trim());
                field = pDAcroForm.getField("Nomenclature");
                field.setValue(PartInfo.getCurrentPart().getDescription().replaceAll("\\.", "").trim());

                //Default
                field = pDAcroForm.getField("AccessKey");
                field.setValue("*12");
                field = pDAcroForm.getField("Doc");
                field.setValue("*A123BC01554001");
                field = pDAcroForm.getField("WUC");
                field.setValue("*1234AA567");
                field = pDAcroForm.getField("SerID");
                field.setValue("*12345678");
                field = pDAcroForm.getField("Org");
                field.setValue("*123BC");

                field = pDAcroForm.getField("CreateDate");
                GregorianCalendar now = new GregorianCalendar();
                now.setTimeInMillis(System.currentTimeMillis());
                String date = String.format("%1$tb %1$te %1$tY", now);
                field.setValue(date);

                field = pDAcroForm.getField("Emp");
                field.setValue("*12345");
                field = pDAcroForm.getField("Shop");
                field.setValue("*A1BCD");
                field = pDAcroForm.getField("Base");
                field.setValue("*ABCD");
                field = pDAcroForm.getField("SRAN");
                field.setValue("*AB1234");
                field = pDAcroForm.getField("Priority");
                field.setValue("*04");
                field = pDAcroForm.getField("Dest");
                field.setValue("*ABC");
                field = pDAcroForm.getField("Requester");
                field.setValue("*Bernard");
                field = pDAcroForm.getField("Verify");
                field.setValue("*Sonali");
                field = pDAcroForm.getField("Remarks");
                field.setValue("*NSIN X-Force");
                field = pDAcroForm.getField("Date");
                field.setValue(date);
                String time = String.format("%1$tH %1$tM", now);
                field = pDAcroForm.getField("NeedTime");
                field.setValue(time);

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
