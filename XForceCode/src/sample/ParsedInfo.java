package sample;

import java.util.ArrayList;

public class ParsedInfo {
    public static ArrayList<String[]> parts = new ArrayList<>();
    public static String figureNumber;
    public static String figureDescription;

    public static String technicalOrder;
    public static String volume;

    public static ArrayList<String[]> nsn = new ArrayList<>();

    public static void clearNSNList() {
        nsn = new ArrayList<>();
    }
}
