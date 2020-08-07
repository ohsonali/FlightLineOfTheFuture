// File: UnitTests.java
// UnitTests to test program logic
package sample;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class UnitTests {

    @Test
    public void testTOParserLeftStart() throws IOException {
        PDDocument technicalOrder = PDDocument.load(new File(Utils.technicalOrderFile));

        PDFDimensionFinder testFinder = new PDFDimensionFinder();
        testFinder.setSortByPosition(true);
        testFinder.setStartPage(3);
        testFinder.setEndPage(4);
        testFinder.getText(technicalOrder);
        assertEquals(0, testFinder.getPageOffset());

        technicalOrder.close();
    }

    @Test
    public void testTOParserRightStart() throws IOException {
        PDDocument technicalOrder = PDDocument.load(new File(Utils.technicalOrderFile));

        PDFDimensionFinder testFinder = new PDFDimensionFinder();
        testFinder.setSortByPosition(true);
        testFinder.setStartPage(4);
        testFinder.setEndPage(5);
        testFinder.getText(technicalOrder);
        assertEquals(Utils.rightStart - Utils.leftStart, testFinder.getPageOffset());

        technicalOrder.close();
    }

    @Test
    public void testTOParserYPos() throws IOException {
        PDDocument technicalOrder = PDDocument.load(new File(Utils.technicalOrderFile));

        PDFDimensionFinder testFinder = new PDFDimensionFinder();
        testFinder.setSortByPosition(true);
        testFinder.setStartPage(3);
        testFinder.setEndPage(4);
        testFinder.getText(technicalOrder);

        Integer[] expectedValues = new Integer[] {94,115,156,196,227,237,258,278,288,319,
                340,350,360,411,462,473,544,585,616,657,667,677,687,698,95};
        assertArrayEquals(expectedValues, testFinder.getRowYCoordinates().toArray());

        technicalOrder.close();
    }

    @Test
    public void testTOParserYPos2() throws IOException {
        PDDocument technicalOrder = PDDocument.load(new File(Utils.technicalOrderFile));

        PDFDimensionFinder testFinder = new PDFDimensionFinder();
        testFinder.setSortByPosition(true);
        testFinder.setStartPage(4);
        testFinder.setEndPage(5);
        testFinder.getText(technicalOrder);

        Integer[] expectedValues = new Integer[] {105,115,125,135,95};
        assertArrayEquals(expectedValues, testFinder.getRowYCoordinates().toArray());

        technicalOrder.close();
    }

    @Test
    public void testNSNScrape() throws IOException {
        NSNScrape.webScrape(new Part("", "4616331", "","","","",""));
        String[] nsnEntry1 = new String[] {"6220-01-419-6252", "RETAINER,LAMP", "72914"};
        String[] nsnEntry2 = new String[] {"5995-01-475-9957", "CABLE ASSEMBLY,RADIO FREQUENCY", "00752"};
        NSNScrape.getScrapedNSNs().get(1)[1] = NSNScrape.getScrapedNSNs().get(1)[1]
                .replaceAll("\\n", "");

        assertArrayEquals(nsnEntry1, NSNScrape.getScrapedNSNs().get(0));
        assertArrayEquals(nsnEntry2, NSNScrape.getScrapedNSNs().get(1));
    }

    @Test
    public void testSupplyCheck() throws IOException {
        String expectedQuantity = "36";
        assertEquals(expectedQuantity, Supply.checkInventory("5305000546652"));
    }

    @Test
    public void testSupplyCheck2() throws IOException {
        String expectedQuantity = "129";
        assertEquals(expectedQuantity, Supply.checkInventory("5340005980234SX\n"));
    }
}

