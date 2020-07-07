package sample;

import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.PDFTextStripperByArea;
import org.apache.pdfbox.text.TextPosition;
import java.io.IOException;
import java.util.ArrayList;


public class OurPDFTextStripper extends PDFTextStripperByArea {

    ArrayList<Float> yPos = new ArrayList<>();
    public int counter = 0;

    public OurPDFTextStripper() throws IOException {
        super();
    }

    // right leaning: (75.189, 60.1) first character
    // left leaning: (39.4413, 59.99237) first character, to 80.76459 for Figure Index,
    @Override
    protected void processTextPosition(TextPosition pos){
        super.processTextPosition(pos);
        if (pos.getX() > 37 && pos.getX() < 88 && pos.getY() > 88 && pos.getY() < 718) {
            if (!yPos.contains(pos.getY())){
                yPos.add(pos.getY());
            }
        }
    }
}
