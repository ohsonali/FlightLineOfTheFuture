package sample;

public class Part {
    private static PartInfo currentPart;
    private static String JCN;
    private static String Quantity;

    public static void addPart(PartInfo part) {
        currentPart = part;
    }

    public static void addUserData(String jcn, String quantity) {
        JCN = jcn;
        Quantity = quantity;
    }

    public static void removePart(PartInfo part) {
        currentPart = null;
        JCN = null;
        Quantity = null;
    }

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
