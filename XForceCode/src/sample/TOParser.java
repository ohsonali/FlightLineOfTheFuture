// File: TOParser.java
// TOParser class with get and set methods

package sample;
import org.apache.pdfbox.text.PDFTextStripperByArea;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

/**
 * This class holds the logic to parse the Technical Order PDF
 *
 * @author Bernard Chan, Sonali Loomba
 *
*/
public class TOParser {
    /** Stores current PDF document */
    private final PDDocument technicalOrder;
    /** Stores page number entered by program arguments */
    private final int page;

    /** Stores parsed figure number*/
    private static String figureNumber;
    /** Stores parsed figure description*/
    private static String figureDescription;

    /** Stores parsed technical order number*/
    private static String technicalOrderNum;
    /** Stores parsed volume*/
    private static String volume;

    /** Stores all parsed parts from technical order table*/
    private static ArrayList<String[]> parts = new ArrayList<>();

    /**
     * TOParser constructor
     *
     * @param page page number of technical order
     * @throws IOException in case of error opening PDDocument
     *
     */
    public TOParser(int page) throws IOException {
        this.page = page;
        technicalOrder = PDDocument.load(new File(Utils.technicalOrderFile));
    }

    /**
     * Reads technical order table
     *
     * Method:
     * Calculate rectangle dimensions based on X coordinates from <code>columnWidths()</code> and
     * Y coordinates from <code>PDFDimensionFinder.rowYCoordinates</code>
     * For every rectangle put the text information into <code>parts</code> ArrayList as a String[]
     *
     * @throws IOException if <code>TOPage</code> does not exist
     *
     */
    public void parse() throws IOException {
        PDFDimensionFinder leanFinder = findLean();
        ArrayList<Integer> columnWidths = columnWidths();
        PDFTextStripperByArea pdfStripperArea = new PDFTextStripperByArea();
        PDPage TOPage = technicalOrder.getPage(page - 1);
        pdfStripperArea.setSortByPosition(true);

        for (int i = 0; i < leanFinder.getRowYCoordinates().size()-1; i++) {
            int height = leanFinder.getRowYCoordinates().get(i+1)-leanFinder.getRowYCoordinates().get(i);
            int currentColumn = Utils.leftStart + leanFinder.getPageOffset();
            String[] currentRow = new String[columnWidths.size()];
            TOParser.getParts().add(currentRow);
            for (int j = 0; j < columnWidths.size(); j++){
                String name = Integer.toString(i) + j;
                Rectangle rectangle = new Rectangle(currentColumn,leanFinder.getRowYCoordinates().get(i), columnWidths.get(j), height);
                pdfStripperArea.addRegion(name, rectangle);
                pdfStripperArea.extractRegions(TOPage);
                currentRow[j]= pdfStripperArea.getTextForRegion(name);
                pdfStripperArea.removeRegion(name);
                currentColumn += columnWidths.get(j);
            }
        }
        findTOHeader(TOPage, pdfStripperArea);
        technicalOrder.close();
    }


    /**
     * Finds technical order number and volume by creating a header rectangle that only reads above the table
     *
     * @param TOPage technical order page that is being parsed
     * @param pdfStripperArea instance of object <code>PDFTextStripperByArea</code> that parses the page
     * @throws IOException if TOPage does not exist
     */
    private void findTOHeader(PDPage TOPage, PDFTextStripperByArea pdfStripperArea) throws IOException {
        Rectangle headerRect = new Rectangle(0, 0, Utils.pageWidth, Utils.headerHeight);
        pdfStripperArea.addRegion("header", headerRect);
        pdfStripperArea.extractRegions(TOPage);
        TOParser.setTechnicalOrderNum(pdfStripperArea.getTextForRegion("header").replaceAll("TO", "").trim());
        String[] split = TOParser.technicalOrderNum.split("-");
        TOParser.setVolume(split[split.length - 1]);
    }


    /**
     * Creates a PDFDimensionFinder object that finds page offset and row Y-coordinates
     * @return object of type PDFDimensionFinder
     * @throws IOException if page does not exist
     *
     */
    private PDFDimensionFinder findLean() throws IOException {
        PDFDimensionFinder leanFinder = new PDFDimensionFinder();
        leanFinder.setSortByPosition(true);
        leanFinder.setStartPage(page);
        leanFinder.setEndPage(page + 1);
        leanFinder.getText(technicalOrder);
        leanFinder.getRowYCoordinates().add(Utils.startHeight + Utils.tableHeight);
        Collections.sort(leanFinder.getRowYCoordinates());
        return leanFinder;
    }

    /**
     * Creates an ArrayList of table column widths from pre-measured dimensions in <code>Utils</code>
     * @return ArrayList containing table column width dimensions
     *
     */
    private ArrayList<Integer> columnWidths() {
        ArrayList<Integer> columnPos = new ArrayList<>();
        columnPos.add(Utils.indexWidth);
        columnPos.add(Utils.partWidth);
        columnPos.add(Utils.cageWidth);
        columnPos.add(Utils.descWidth);
        columnPos.add(Utils.unitWidth);
        columnPos.add(Utils.usableWidth);
        columnPos.add(Utils.smrWidth);
        return columnPos;
    }

    /**
     * Gets the figure number
     * @return a String specifying the figure number
     */
    public static String getFigureNumber() {
        return figureNumber;
    }

    /**
     * Sets the figure number
     * @param figureNumber the figure number
     */
    public static void setFigureNumber(String figureNumber) {
        TOParser.figureNumber = figureNumber;
    }

    /**
     * Gets the figure description
     * @return a String specifying the figure description
     */
    public static String getFigureDescription() {
        return figureDescription;
    }

    /**
     * Sets the figure description
     * @param figureDescription the figure description
     */
    public static void setFigureDescription(String figureDescription) {
        TOParser.figureDescription = figureDescription;
    }

    /**
     * Gets the technical order number
     * @return a String specifying the technical order number
     */
    public static String getTechnicalOrderNum() {
        return technicalOrderNum;
    }

    /**
     * Sets the technical order number
     * @param technicalOrderNum the technical order number
     */
    public static void setTechnicalOrderNum(String technicalOrderNum) {
        TOParser.technicalOrderNum = technicalOrderNum;
    }

    /**
     * Gets the volume
     * @return a String specifying the volume
     */
    public static String getVolume() {
        return volume;
    }

    /**
     * Sets the volume
     * @param volume the volume
     */
    public static void setVolume(String volume) {
        TOParser.volume = volume;
    }

    /**
     * Get the parts list
     * @return ArrayList of parts from TO
     */
    public static ArrayList<String[]> getParts() {
        return parts;
    }

}
