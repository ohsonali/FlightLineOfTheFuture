// File: Autofiller.java
// Autofiller class with autofilling logic

package sample;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;

import java.io.File;
import java.io.IOException;
import java.util.GregorianCalendar;
/**
 * Auto fills the F9006 pdf form based on the information stored in <code>PartInfo</code> and <code>TOParser</code>
 * @author Bernard Chan, Sonali Loomba
 * */
public class Autofiller {
    /** The F9006 pdf document as a PDDocument */
    private final PDDocument f9006;
    /** The F9006 pdf document as a File */
    private final File f9006path;
    /**
     * Autofiller constructor
     *
     * @throws IOException in case of error loading PDDocument or opening File
     *
     */
    public Autofiller() throws IOException {
        f9006path = new File(Utils.f9006File);
        f9006 = PDDocument.load(f9006path);
    }

    /**
     * Fills out the F9006 form
     */
    public void fill9006() {
        try {
            PDAcroForm pDAcroForm = f9006.getDocumentCatalog().getAcroForm();

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
            field.setValue(PartInfo.getCurrentPart().getPartNum().replaceAll("[#=]", "").trim());
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

            f9006.save(f9006path.getPath());
            f9006.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
