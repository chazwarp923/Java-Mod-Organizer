package tech.chazwarp923.MorrowWarpManager;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/**
 * Created by Chazwarp923 on 6/12/16.
 */
public class Main {
    public static void main(String[] args) {
        if(args.length > 0) {
            if(args[0].contains("nxm://")) {
                try {
					handleNexusLink(args[0]);
				} catch (IOException e) {
					e.printStackTrace();
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
        Element links = doc.select("script[language][type]").first();
        URL downloadLink = new URL(links.html().substring(links.html().indexOf('"') + 1, links.html().length() - 2));
    	
    	System.out.println("Nexus Link: " + nxm);
    	System.out.println("Game: " + game);
    	System.out.println("ModID: " + modId);
    	System.out.println("FileID: " + fileId);
    	System.out.println("Download Page: " + url);      
        System.out.println("Download Link: " + downloadLink);
        
        String saveDir = System.getProperty("java.io.tmpdir") + "/MorrowWarpManager";
        String saveFilePath = saveDir + File.separator + downloadLink.toString().substring(downloadLink.toString().lastIndexOf("/") + 1, downloadLink.toString().lastIndexOf("?"));;
        FileUtils.copyURLToFile(downloadLink, new File(saveFilePath));	
    }
}
