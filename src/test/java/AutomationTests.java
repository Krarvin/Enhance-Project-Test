import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.junit.Test;
import org.junit.Assert;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;
import java.util.List;


public class AutomationTests {
    @Test
    public void WebUITest() throws Exception, FailingHttpStatusCodeException{
        //This test is done using a specific link to a car listing. This was because I couldn't figure out how to navigate into a specific listing using htmlunit.
        WebClient client = new WebClient();
        client.getOptions().setCssEnabled(false);
        client.getOptions().setJavaScriptEnabled(false);
        try{
            String searchUrl = "https://www.tmsandbox.co.nz/motors/used-cars/bmw/auction-2149252463.htm";
            HtmlPage page = client.getPage(searchUrl);
            HtmlElement listingDetails =  page.getFirstByXPath(".//div[@class='attributes-box key-details-box']");
            String stringDetails = listingDetails.asText();
            List<String> lines = Arrays.asList(listingDetails.asText().split("\n"));
            for(int i = 0; i<lines.size(); i++){
                if(lines.get(i).contains("Number plate")){
                    System.out.println(lines.get(i+1));
                    assert(lines.get(i+1) != null);
                }else if(lines.get(i).contains("Kilometres")){
                    System.out.println(lines.get(i+1));
                    assert(lines.get(i+1) != null);
                }else if (lines.get(i).contains("Body")){
                    System.out.println(lines.get(i+1));
                    assert(lines.get(i+1) != null);
                }else if (lines.get(i).contains("Seats")){
                    System.out.println(lines.get(i+1));
                    assert(lines.get(i+1) != null);
                }
            }

        }catch(Exception e){
            e.printStackTrace();
        }

    }

    @Test
    public void apiTest1() throws IOException {
        URL url = new URL("https://api.trademe.co.nz/v1/Charities.JSON");
        URLConnection con = url.openConnection();
        con.setRequestProperty("accept","application/json");
        InputStream is = con.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String line = null;
        Boolean hasStJohn = false;
        while((line = br.readLine()) != null){
            if(line.contains("St John")){
                hasStJohn = true;
            }
        }
        Assert.assertTrue(hasStJohn);
    }

    @Test
    public void apiTest2() throws IOException{
        //this test does not pass because the request returns a 401. This is because I couldn't wrap my head around the trademe authentication for vehicles.
        URL url = new URL("https://api.trademe.co.nz/v1/Search/Motors/Used.JSON");
        URLConnection con = url.openConnection();
        con.setRequestProperty("accept","application/json");
        InputStream is = con.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String line = null;
        Boolean hasNoPlate = false;
        Boolean hasSeats = false;
        Boolean hasKM = false;
        Boolean hasBody = false;
        while((line = br.readLine()) != null){
            if(line.contains("Number plate")){
                hasNoPlate = true;
            }else if (line.contains("Seats")){
                hasSeats = true;
            }else if (line.contains("Kilometres")){
                hasKM = true;
            }else if (line.contains("Body")){
                hasBody = true;
            }
        }
        Assert.assertTrue(hasNoPlate);
        Assert.assertTrue(hasSeats);
        Assert.assertTrue(hasKM);
        Assert.assertTrue(hasBody);
    }

}


