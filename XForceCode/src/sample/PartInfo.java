package sample;

import java.util.ArrayList;

public class PartInfo {
    private static Part currentPart = null;
    private static String JCN = null;
    private static String quantity = null;
    private static NSN currentNSN = null;

    public static void addUserData(String jcn, String quantity) {
        setJCN(jcn);
        setQuantity(quantity);
    }

    public static void removePart() {
        setCurrentPart(null);
        setCurrentNSN(null);
        setJCN(null);
        setQuantity(null);
    }

    public static NSN getCurrentNSN() {return currentNSN;}

    public static Part getCurrentPart() {
        return currentPart;
    }
    public static String getJCN() {
        return JCN;
    }
    public static String getQuantity() {
        return quantity;
    }

    public static void setCurrentPart(Part currentPart) {
        PartInfo.currentPart = currentPart;
    }

    public static void setJCN(String JCN) {
        PartInfo.JCN = JCN;
    }

    public static void setQuantity(String quantity) {
        PartInfo.quantity = quantity;
    }

    public static void setCurrentNSN(NSN currentNSN) {
        PartInfo.currentNSN = currentNSN;
    }
}