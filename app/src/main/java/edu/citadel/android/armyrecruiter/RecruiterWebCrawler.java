package edu.citadel.android.armyrecruiter;


import org.jsoup.Jsoup;
import org.jsoup.examples.HtmlToPlainText;
import org.jsoup.nodes.Document;


import java.io.Serializable;
import java.util.Scanner;


public class RecruiterWebCrawler implements Serializable {
    private int zipCode;
    private String address;
    private String phoneNumber;

    public int getZipCode() {
        return zipCode;
    }

    public void setZipCode(int zipCode) {
        this.zipCode = zipCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public static RecruiterWebCrawler WebCrawler(int zip){
            Document doc = null;
        try {
            doc = Jsoup.connect("https://www.goarmy.com/locate-a-recruiter.E-R-NA-"
                    + zip + ".result.html#locate-a-recruiter").get();

            if (Integer.toString(zip).length() > 5 || Integer.toString(zip).length() < 5) {
                return new RecruiterWebCrawler();
            } else{
                return new RecruiterWebCrawler(doc);
            }
        }catch(Exception e) {
            return new RecruiterWebCrawler();
        }
    }

    public RecruiterWebCrawler(){
        zipCode = 0;
        address = "Improper Zip Code or Timeout";
        phoneNumber = "";
    }

    private RecruiterWebCrawler(Document doc) {

        try {
            HtmlToPlainText temp = new HtmlToPlainText();
            String webPage = temp.getPlainText
                    (doc.getElementsByClass("station-detail paginated").get(0));
            Scanner alfred = new Scanner(webPage);
            String info = "";
            alfred.nextLine();


            for(int i = 0;i < 5;i++){
                    info = info + alfred.nextLine() + "\n";
            }

            this.address = info;

        }catch (Exception e){
            new RecruiterWebCrawler();
        }
    }
}
