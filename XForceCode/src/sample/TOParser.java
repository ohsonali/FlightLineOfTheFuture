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

public class TOParser {
    private final PDDocument technicalOrder;
    private final int page;

    private static String figureNumber;
    private static String figureDescription;

    private static String technicalOrderNum;
    private static String volume;

    private static ArrayList<String[]> parts = new ArrayList<>();

    public TOParser(boolean Bernard, int page) throws IOException {
        this.page = page;
        if (Bernard) {
            technicalOrder = PDDocument.load(new File(Utils.bernardFile));
        } else {
            technicalOrder = PDDocument.load(new File(Utils.sonaliFile));
        }
    }

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

    private void findTOHeader(PDPage TOPage, PDFTextStripperByArea pdfStripperArea) throws IOException {
        Rectangle headerRect = new Rectangle(0, 0, Utils.pageWidth, Utils.headerHeight);
        pdfStripperArea.addRegion("header", headerRect);
        pdfStripperArea.extractRegions(TOPage);
        TOParser.setTechnicalOrderNum(pdfStripperArea.getTextForRegion("header").replaceAll("TO", "").trim());
        String[] split = TOParser.technicalOrderNum.split("-");
        TOParser.setVolume(split[split.length - 1]);
    }


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

    public static String getFigureNumber() {
        return figureNumber;
    }

    public static void setFigureNumber(String figureNumber) {
        TOParser.figureNumber = figureNumber;
    }

    public static String getFigureDescription() {
        return figureDescription;
    }

    public static void setFigureDescription(String figureDescription) {
        TOParser.figureDescription = figureDescription;
    }

    public static String getTechnicalOrderNum() {
        return technicalOrderNum;
    }

    public static void setTechnicalOrderNum(String technicalOrderNum) {
        TOParser.technicalOrderNum = technicalOrderNum;
    }

    public static String getVolume() {
        return volume;
    }

    public static ArrayList<String[]> getParts() {
        return parts;
    }

    public static void setVolume(String volume) {
        TOParser.volume = volume;
    }
}
