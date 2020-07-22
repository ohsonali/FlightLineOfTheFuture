package sample;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NSNScrape {

    private static ArrayList<String[]> scrapedNSNs = new ArrayList<>();
    public static void clearNSNList() {
        scrapedNSNs = new ArrayList<>();
    }

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
                entry[0] = nsn.text();
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
                    entry[1] = description;
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
            entry[2] = cageStrings;
            if (entry[0] != null) {
                NSNScrape.getScrapedNSNs().add(entry);
            }
        }
    }


    public static ArrayList<String[]> getScrapedNSNs() {
        return scrapedNSNs;
    }
}
