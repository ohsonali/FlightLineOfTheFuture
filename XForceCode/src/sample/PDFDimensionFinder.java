package sample;

import org.apache.pdfbox.text.PDFTextStripperByArea;
import org.apache.pdfbox.text.TextPosition;
import java.io.IOException;
import java.util.ArrayList;


public class PDFDimensionFinder extends PDFTextStripperByArea {
    public ArrayList<Integer> rowYCoordinates = new ArrayList<>();
    private boolean firstChar = true;
    public int pageOffset;

    public PDFDimensionFinder() throws IOException {
        super();
    }

    // right leaning: (75.189, 60.1) first character
    // left leaning: (39.4413, 59.99237) first character, to 80.76459 for Figure Index,
    @Override
    protected void processTextPosition(TextPosition pos) {
        super.processTextPosition(pos);
        if (firstChar) {
            findOffset(pos);
            firstChar = false;
        }
        findRowYCoordinates(pos);
    }

    protected void findOffset(TextPosition pos) {
        //System.out.println("First Y: " + pos.getY());
        //System.out.println("First X: " + pos.getX());
        if (pos.getX() > Utils.firstLeft - 2 && pos.getX() < Utils.firstLeft + 2) {
            pageOffset = 0;
        } else if (pos.getX() > Utils.firstRight - 2 && pos.getX() < Utils.firstRight + 2) {
            pageOffset = Utils.rightStart - Utils.leftStart;
        } else {
            throw new IllegalArgumentException("Neither left or right start");
        }
    }


    protected void findRowYCoordinates(TextPosition pos) {
        if (pos.getX() > Utils.leftStart + pageOffset && pos.getX() < Utils.leftStart + pageOffset + Utils.figureWidth && pos.getY()
                > Utils.startHeight && pos.getY() < Utils.tableHeight + Utils.startHeight) {
            int currentY = (int) (pos.getY() - pos.getHeight());
            if (!rowYCoordinates.contains(currentY)) {
                rowYCoordinates.add(currentY);
            }
        }
    }
}
