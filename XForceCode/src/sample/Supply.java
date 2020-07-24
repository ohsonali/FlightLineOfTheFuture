package sample;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
/**
 * This class holds the logic of checking a part's inventory in the supply database
 * @author Bernard Chan, Sonali Loomba
 * */
public class Supply {
    /**
     * Finds the inventory of a part in the supply database based on the National Stock Number of the part
     * @param nsn the National Stock Number to check
     * @param bernard specifies the user
     * @return String number amount of the part that is in stock
     */
    public static String checkInventory(String nsn, boolean bernard) {
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
                if (nsn.replaceAll("-", "").trim().equals(data[1])) {
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
