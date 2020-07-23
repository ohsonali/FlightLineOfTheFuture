package sample;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Supply {
    public static String checkInventory(boolean bernard) {
        File csv;
        if (bernard) {
            csv = new File(Utils.bernardcsv);
        } else {
            csv = new File(Utils.sonalicsv);
        }
        String quantityInStock = "Not Found";
        try {
            BufferedReader csvReader = new BufferedReader(new FileReader(csv));
            String row;
            while ((row = csvReader.readLine()) != null) {
                String[] data = row.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
                if (PartInfo.getCurrentNSN().getNsn().replaceAll("-", "").trim().equals(data[1])) {
                    quantityInStock = data[9];
                }
            }
        }
        catch (IOException io){
            io.printStackTrace();
        }
        return quantityInStock;
    }
}
