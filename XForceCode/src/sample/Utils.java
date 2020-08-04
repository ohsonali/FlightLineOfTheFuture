// File: Utils.java
// Utils class with stored pixel dimensions
package sample;

/**
 * This class holds the pixel dimensions of the Technical Order PDF table, which we pre-measured and confirmed was
 * standardized throughout Technical Orders
 *
 * @author Bernard Chan, Sonali Loomba
 */
public class Utils {

    /** Pre-measured X-Coordinate of left side of table for left-leaning pages */
    static int leftStart = 37;
    /** Pre-measured X-Coordinate of left side of table for right-leaning pages */
    static int rightStart = 72;
    /** Pre-measured X-Coordinate of first character for left-leaning pages */
    static int firstLeft = 39;
    /** Pre-measured X-Coordinate of first character for right-leaning pages */
    static int firstRight = 75;
    /** Pre-measured width of the index column of the table */
    static int indexWidth = 50;
    /** Pre-measured width of the part column of the table */
    static int partWidth = 94;
    /** Pre-measured width of the cage column of the table */
    static int cageWidth = 35;
    /** Pre-measured width of the description column of the table */
    static int descWidth = 200;
    /** Pre-measured width of the unit column of the table */
    static int unitWidth = 44;
    /** Pre-measured width of the usable column of the table */
    static int usableWidth = 38;
    /** Pre-measured width of the SMR column of the table */
    static int smrWidth = 40;
    /** Pre-measured Y-Coordinate position of the top of the first row */
    static int startHeight = 86;
    /** Pre-measured height of the entire table */
    static int tableHeight = 630;
    /** Pre-measured width of the entire page */
    static int pageWidth = 611;
    /** Pre-measured height of the header containing the Technical Order number and Volume */
    static int headerHeight = 44;
    /** File path to Technical Order PDF */
    static String technicalOrderFile = System.getProperty("user.dir") + "/Pages from TO 1C-17A-4-33.pdf";
    /** File path to Blank F9006 Form PDF */
    static String f9006File = System.getProperty("user.dir") + "/Blank F9006.pdf";
    /** File path to Mock Supply Database CSV */
    static String supplyCSV = System.getProperty("user.dir") + "/Mock Warehouse.csv";
}
