package tech.chazwarp923.MorrowWarpManager;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.apache.commons.configuration.XMLConfiguration;
import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/**
 * Created by Chazwarp923 on 6/12/16.
 */

public class Main {
	
	private static XMLConfiguration config;
	
    public static void main(String[] args) {
    	/*try {
			config = ConfigHandler.loadConfig();
		} catch (ConfigurationException e) {
			e.printStackTrace();
		}*/
        if(args.length > 0) {
            if(args[0].contains("nxm://")) {
                try {
					handleNexusLink(args[0]);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
            }
        }
        else {
        	
        }
    }
    
    private static void handleNexusLink (String nxm) throws IOException {
    	String game = nxm.substring(6, nxm.indexOf("/mods")).toLowerCase();
    	String modId = nxm.substring(6 + game.length() + 6, nxm.indexOf("/files"));
    	String fileId = nxm.substring(6 + game.length() + 6 + modId.length() + 7, nxm.length());
    	String url = "http://www.nexusmods.com/" + game + "/download/" + fileId; 
    	
    	Document doc = Jsoup.connect(url).get();
        Element link = doc.select("script[language][type]").first();
        System.out.println(link);
        String linkHtml = link.html();
        URL downloadLink = new URL(linkHtml.substring(link.html().indexOf('"') + 1, linkHtml.length() - 2));
    	
    	System.out.println("Nexus Link: " + nxm);
    	System.out.println("Game: " + game);
    	System.out.println("ModID: " + modId);
    	System.out.println("FileID: " + fileId);
    	System.out.println("Download Page: " + url);      
        System.out.println("Download Link: " + downloadLink);
        
        /*final WebClient webClient = new WebClient();
        final HtmlPage page1 = webClient.getPage("http://www.facebook.com");
        final HtmlForm form = (HtmlForm) page1.getElementById("login_form");

        final HtmlSubmitInput button = (HtmlSubmitInput) form.getInputsByValue("Log In").get(0);
        final HtmlTextInput textField = form.getInputByName("email");
        textField.setValueAttribute("jon@jon.com");
        final HtmlPasswordInput textField2 = form.getInputByName("pass");
        textField2.setValueAttribute("ahhhh");
        final HtmlPage page2 = button.click();
        webClient.close();*/
        
        String saveDir = System.getProperty("java.io.tmpdir") + "/MorrowWarpManager";
        String saveFilePath = saveDir + File.separator + downloadLink.toString().substring(downloadLink.toString().lastIndexOf("/") + 1, downloadLink.toString().lastIndexOf("?"));;
        FileUtils.copyURLToFile(downloadLink, new File(saveFilePath));	
    }
}
