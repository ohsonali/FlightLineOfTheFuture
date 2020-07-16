package sample;

public class Part {
    private static PartInfo currentPart = null;
    private static String JCN = null;
    private static String Quantity = null;

    private static NSN currentNSN = null;

    public static void addNSN(NSN nsn) {
        currentNSN = nsn;
    }

    public static void addPart(PartInfo part) {
        currentPart = part;
    }

    public static void addUserData(String jcn, String quantity) {
        JCN = jcn;
        Quantity = quantity;
    }

    public static void removePart() {
        currentPart = null;
        JCN = null;
        Quantity = null;
    }

    public static NSN getCurrentNSN() {return currentNSN;}

    public static PartInfo getCurrentPart() {
        return currentPart;
    }

    public static String getJCN() {
        return JCN;
    }

    public static String getQuantity() {
        return Quantity;
    }
}
