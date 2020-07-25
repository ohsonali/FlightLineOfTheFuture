// File: PartInfo.java
// TOParser PartInfo with get and set methods
package sample;

import java.io.IOException;
import java.util.ArrayList;
/**
 * This class stores the user-entered and parsed/scraped information about the part
 *
 * @author Bernard Chan, Sonali Loomba
 *
 */
public class PartInfo {
    /** The current part as a <code>Part</code> object */
    private static Part currentPart = null;
    /** User-entered JCN */
    private static String JCN = null;
    /** User-entered quantity */
    private static String quantity = null;
    /** The current nsn as a <code>NSN</code> object */
    private static NSN currentNSN = null;
    /**
     * Stores the user-entered data in <code>JCN</code> and <code>quantity</code>
     *
     * @param jcn the jcn <code>String</code> to be added
     * @param quantity the quantity <code>String</code> to be added
     */
    public static void addUserData(String jcn, String quantity) {
        setJCN(jcn);
        setQuantity(quantity);
    }
    /**
     * Clears the fields of <code>PartInfo</code>
     */
    public static void removePart() {
        setCurrentPart(null);
        setCurrentNSN(null);
        setJCN(null);
        setQuantity(null);
    }
    /**
     * Gets the current NSN
     * @return an <code>NSN</code> object specifying the current NSN
     */
    public static NSN getCurrentNSN() {return currentNSN;}
    /**
     * Gets the current part
     * @return a <code>Part</code> object specifying the current part
     */
    public static Part getCurrentPart() {
        return currentPart;
    }
    /**
     * Gets the user-entered JCN
     * @return a <code>String</code> specifying the user-entered JCN
     */
    public static String getJCN() {
        return JCN;
    }
    /**
     * Gets the user-entered quantity
     * @return a <code>String</code> specifying the user-entered quantity
     */
    public static String getQuantity() {
        return quantity;
    }
    /**
     * Sets the current part
     * @param currentPart the current part
     */
    public static void setCurrentPart(Part currentPart) {
        PartInfo.currentPart = currentPart;
    }
    /**
     * Sets the current JCN
     * @param JCN the current JCN
     */
    public static void setJCN(String JCN) {
        PartInfo.JCN = JCN;
    }
    /**
     * Sets the current quantity
     * @param quantity the current quantity
     */
    public static void setQuantity(String quantity) {
        PartInfo.quantity = quantity;
    }
    /**
     * Sets the current NSN
     * @param currentNSN the current NSN
     */
    public static void setCurrentNSN(NSN currentNSN) {
        PartInfo.currentNSN = currentNSN;
    }
}