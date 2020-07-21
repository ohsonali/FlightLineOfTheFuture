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
        for (int i = 0; i<ParsedInfo.nsn.size(); i++){
            for(int j = 0; j<ParsedInfo.nsn.get(i).length; j++){
                System.out.print(ParsedInfo.nsn.get(i)[j]+ "         ");
            }
            System.out.println();
        }
        */
        File csv = new File(Utils.bernardcsv);

        File file = new File(Utils.bernardFile);
        PDDocument document = PDDocument.load(file);

        File file2 = new File(Utils.bernard9006File);
        PDDocument document2 = PDDocument.load(file2);

        BufferedReader csvReader = new BufferedReader(new FileReader(csv));
        String row;
        while ((row = csvReader.readLine()) != null) {
            String[] data = row.split(",");
            if ("4730015544191".replaceAll("-", "").trim().equals(data[1])) {
                for (int i = 0; i < 13; i++) {
                    System.out.println(data[i]);
                }
            }
        }

        System.exit(0);

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
        PDPage page = document.getPage(2);
        pdfStripperArea.setSortByPosition(true);
        leanFinder.setSortByPosition(true);
        leanFinder.setStartPage(3);
        leanFinder.setEndPage(4);
        leanFinder.getText(document);
        // System.out.println(leanFinder.left);

        Collections.sort(leanFinder.yPos);

        leanFinder.yPos.add(Utils.startHeight+Utils.tableHeight);

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
                pdfStripperArea.extractRegions(page);
                currentRow[j]= pdfStripperArea.getTextForRegion(name);
                //System.out.println(pdf.getTextForRegion(name));
                currentColumn += xPos.get(j);
                pdfStripperArea.removeRegion(name);
            }
        }

        Rectangle headerRect = new Rectangle(0, 0, Utils.pageWidth, Utils.headerHeight);
        pdfStripperArea.addRegion("header", headerRect);
        pdfStripperArea.extractRegions(page);
        ParsedInfo.technicalOrder = pdfStripperArea.getTextForRegion("header").replaceAll("TO", "").trim();
        String[] split = ParsedInfo.technicalOrder.split("-");
        ParsedInfo.volume = split[split.length - 1];


        for (int i = 0; i <ParsedInfo.parts.size(); i++) {
            String figure = ParsedInfo.parts.get(i)[0];
            String partNumber = ParsedInfo.parts.get(i)[1];
            String cage = ParsedInfo.parts.get(i)[2];
            String description = ParsedInfo.parts.get(i)[3];
            String units = ParsedInfo.parts.get(i)[4];
            String usable = ParsedInfo.parts.get(i)[5];
            String smr = ParsedInfo.parts.get(i)[6];
            String[] partLines = stringLines(partNumber);
            if (figure.trim().length() == 0 && partNumber.trim().length() == 0 && cage.trim().length() == 0 && description.trim().length() == 0 &&
                    units.trim().length() == 0 && usable.trim().length() == 0 && smr.trim().length() == 0) {
                ParsedInfo.parts.remove(i);
            }

            if (partLines.length > 1) {
                String[] cageLines = stringLines(cage);
                String[] smrLines = stringLines(smr);
                for (int j = 0; j < partLines.length; j++) {
                    String[] newRow = new String[xPos.size()];
                    newRow[0] = figure;
                    newRow[3] = description;
                    newRow[4] = units;
                    newRow[5] = usable;

                    newRow[1] = partLines[j].replace(" 4", " =");
                    newRow[2] = cageLines[j];
                    newRow[6] = smrLines[j];
                    ParsedInfo.parts.add(i + j + 1, newRow);
                }
                ParsedInfo.parts.remove(i);
            }
        }


        /*
        //Testing TO Parser
        for (int i = 0; i<ParsedInfo.parts.size(); i++){
            for(int j = 0; j<7; j++){
                System.out.print(ParsedInfo.parts.get(i)[j]+ "         ");
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

        document.close();

        launch(args);


        if (Part.getJCN() != null && Part.getQuantity() != null && Part.getCurrentPart() != null) {

            try {
                PDAcroForm pDAcroForm = document2.getDocumentCatalog().getAcroForm();

                for (PDField field : pDAcroForm.getFields()) {
                    if (field.getFieldType().equals("Tx")) {
                        field.setValue("");
                    }
                }

                //Part
                PDField field = pDAcroForm.getField("JCN");
                field.setValue(Part.getJCN().trim());
                field = pDAcroForm.getField("Quantity");
                field.setValue(Part.getQuantity().trim());
                field = pDAcroForm.getField("Stock");
                field.setValue(Part.getCurrentNSN().getNsn().trim());

                //ParsedInfo
                field = pDAcroForm.getField("Fig");
                field.setValue(ParsedInfo.figureNumber.trim());
                field = pDAcroForm.getField("Vol");
                field.setValue(ParsedInfo.volume.trim());
                field = pDAcroForm.getField("TO");
                field.setValue(ParsedInfo.technicalOrder.trim());

                //PartInfo
                field = pDAcroForm.getField("Index");
                field.setValue(Part.getCurrentPart().getFigure().replaceAll("-", "").trim());
                field = pDAcroForm.getField("Part");
                field.setValue(Part.getCurrentPart().getPart().trim());
                field = pDAcroForm.getField("Nomenclature");
                field.setValue(Part.getCurrentPart().getDescription().replaceAll("\\.", "").trim());

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

    public static String[] stringLines(String str){
        return str.split("\r\n|\r|\n");
    }


}
