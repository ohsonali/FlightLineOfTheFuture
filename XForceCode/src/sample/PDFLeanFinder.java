package sample;

import org.apache.pdfbox.text.PDFTextStripperByArea;
import org.apache.pdfbox.text.TextPosition;
import java.io.IOException;
import java.util.ArrayList;


public class PDFLeanFinder extends PDFTextStripperByArea {
    public ArrayList<Integer> yPos = new ArrayList<>();
    private boolean first = true;
    public boolean left;
    public int x;

    public PDFLeanFinder() throws IOException {
        super();
    }

    // right leaning: (75.189, 60.1) first character
    // left leaning: (39.4413, 59.99237) first character, to 80.76459 for Figure Index,
    @Override
    protected void processTextPosition(TextPosition pos) {
        super.processTextPosition(pos);
        if (first) {
            System.out.println("First Y: " + pos.getY());
            System.out.println("First X: " + pos.getX());
            if (pos.getX() > Utils.leftStart - 2 && pos.getX() < Utils.leftStart + 2) {
                left = true;
                x = 0;
            } else if (pos.getX() > Utils.rightStart - 2 && pos.getX() < Utils.rightStart + 2) {
                left = false;
                x = Utils.rightStart - Utils.leftStart;
            } else {
                throw new IllegalArgumentException("Neither left or right start");
            }
            first = false;
        }
        if (pos.getX() > Utils.leftStart + x && pos.getX() < Utils.leftStart + x + Utils.figureWidth && pos.getY()
                > Utils.startHeight && pos.getY() < Utils.tableHeight - Utils.startHeight) {
            int currentY = (int) (pos.getY() - pos.getHeight());
            if (!yPos.contains(currentY)) {
                yPos.add(currentY);
            }
        }
    }
}
