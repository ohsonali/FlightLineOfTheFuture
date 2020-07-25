// File: PDFDimensionFinder.java
// PDFDimensionFinder class with get methods
package sample;

import org.apache.pdfbox.text.PDFTextStripperByArea;
import org.apache.pdfbox.text.TextPosition;
import java.io.IOException;
import java.util.ArrayList;
/**
 * This class extends PDFTextStripperByArea
 * @see <a href="https://pdfbox.apache.org/docs/2.0.1/javadocs/org/apache/pdfbox/text/PDFTextStripperByArea.html">https://pdfbox.apache.org/docs/2.0.1/javadocs/org/apache/pdfbox/text/PDFTextStripperByArea.html</a>
 * to determine the pixel locations of the rows on the table
 *
 * @author Bernard Chan, Sonali Loomba
 *
 */
public class PDFDimensionFinder extends PDFTextStripperByArea {
    /** Stores the Y-Coordinate positions of the rows in the table */
    private ArrayList<Integer> rowYCoordinates = new ArrayList<>();
    /** Determines if <code>processTextPosition</code> is on the first character */
    private boolean firstChar = true;
    /** Number of pixels that the page is offset to the right from a left-leaning page */
    private int pageOffset;
    /**
     * PDFDimensionFinder constructor
     * @throws IOException if PDFTextStripperByArea does not initialize properly
     */
    public PDFDimensionFinder() throws IOException {
        super();
    }

    /**
     * Processes all the characters in the PDF character by character, determining the page's lean based on the first
     * character and also looking at all the characters to determine what Y-Coordinates to add to
     * <code>rowYCoordinates</code>
     *
     * @param pos the TextPosition object representing the current character
     */
    @Override
    protected void processTextPosition(TextPosition pos) {
        super.processTextPosition(pos);
        if (firstChar) {
            findOffset(pos);
            firstChar = false;
        }
        findRowYCoordinates(pos);
    }
    /**
     * Looks at a specific TextPosition character and determines the <code>pageOffset</code> based on the position of
     * the character
     *
     * @param pos the TextPosition object representing the current character
     */
    protected void findOffset(TextPosition pos) {
        if (pos.getX() > Utils.firstLeft - 2 && pos.getX() < Utils.firstLeft + 2) {
            pageOffset = 0;
        } else if (pos.getX() > Utils.firstRight - 2 && pos.getX() < Utils.firstRight + 2) {
            pageOffset = Utils.rightStart - Utils.leftStart;
        } else {
            throw new IllegalArgumentException("Neither left or right start");
        }
    }

    /**
     * Determines the <code>rowYCoordinates</code> based on the Y-Coordinate positions of the first column of entries
     *
     * @param pos the TextPosition object representing the current character
     */
    protected void findRowYCoordinates(TextPosition pos) {
        if (pos.getX() > Utils.leftStart + pageOffset && pos.getX() < Utils.leftStart + pageOffset + Utils.indexWidth && pos.getY()
                > Utils.startHeight && pos.getY() < Utils.tableHeight + Utils.startHeight) {
            int currentY = (int) (pos.getY() - pos.getHeight());
            if (!rowYCoordinates.contains(currentY)) {
                rowYCoordinates.add(currentY);
            }
        }
    }

    /**
     * Gets the rowYCoordinates
     * @return an ArrayList specifying the Y-Coordinates of the rows in the table
     */
    public ArrayList<Integer> getRowYCoordinates() {
        return rowYCoordinates;
    }

    /**
     * Gets the pageOffset
     * @return an Integer specifying the pageOffset
     */
    public int getPageOffset() {
        return pageOffset;
    }
}
