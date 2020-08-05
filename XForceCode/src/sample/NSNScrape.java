// File: NSNScrape.java
// NSNScrape class with webscraping logic
package sample;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * This class holds the logic to webscrape NSNCenter.com for National Stock Numbers
 *
 * @author Bernard Chan, Sonali Loomba
 *
 */
public class NSNScrape {
    /** Stores the scraped National Stock Number information */
    private static ArrayList<String[]> scrapedNSNs = new ArrayList<>();

    /**
     * Scrapes NSNCenter.com for a particular part and stores all the possible corresponding
     * National Stock Numbers in the scrapedNSNs
     *
     * @param part that is being looked for while webscraping
     * @throws IOException if connecting to the webpage fails
     *
     */
    public static void webScrape(Part part) throws IOException {
        String partNumber = part.getPartNum().trim();
        Document doc = Jsoup.connect("https://www.nsncenter.com/NSNSearch?q=" + partNumber).get();
        Elements rows = doc.select("tr");
        for (Element row : rows ) {
            String[] entry = new String[3];
            Elements nsns = row.select("a[href][onclick^='dataLayer.push({'event':'trackEvent','eventCategory':'Commerce','eventAction':'ProductClick','eventLabel':']");
            Elements cages = row.select("a[href^='https://www.cagecode.info/']");
            // code assumes we will only find one nsns and one cages per row
            for (Element nsn : nsns) {
                entry[0] = nsn.text().trim();
                Pattern descriptions = Pattern.compile(",'name':'(.*?)','category':'");
                Matcher matcher = descriptions.matcher(nsn.attr("onclick"));
                while (matcher.find()) {
                    String rawDescription = matcher.group(1);
                    String[] descList = rawDescription.split("(?=[,| ])");
                    String description = "";
                    int counter = 0;
                    for (String desc : descList) {
                        if (counter == 3) {
                            description += "\n" + desc;
                            counter = 1;
                        } else {
                            counter += 1;
                            description += desc;
                        }
                    }
                    entry[1] = description.trim();
                }
            }
            String cageStrings = "";
            int counter = 0;
            for (Element cage : cages) {
                if (counter == 5) {
                    cageStrings += "\n" + cage.text() + " ";
                    counter = 0;
                } else {
                    cageStrings += cage.text() + " ";
                }
                counter += 1;
            }
            entry[2] = cageStrings.trim();
            if (entry[0] != null) {
                NSNScrape.getScrapedNSNs().add(entry);
            }
        }
    }
    /**
     * Clears <code>scrapedNSNs</code> to enable cancel button functionality and the user to
     * navigate back and forth in the GUI without the logic breaking
     */
    public static void clearNSNList() {
        scrapedNSNs = new ArrayList<>();
    }
    /**
     * Gets <code>scrapedNSNs</code>
     * @return an Arraylist specifying the scraped NSNs
     */
    public static ArrayList<String[]> getScrapedNSNs() {
        return scrapedNSNs;
    }
}
