/**
* @author Chaz Kerby ©2016
*/
package tech.chazwarp923.javamodorganizer.downloads;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlPasswordInput;
import com.gargoylesoftware.htmlunit.html.HtmlScript;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;

import tech.chazwarp923.javamodorganizer.config.ConfigHandler;

public class DownloadManager {
	
	public static void handleNexusLink (String nxm) throws IOException {
    	String game = nxm.substring(6, nxm.indexOf("/mods")).toLowerCase();
    	String modId = nxm.substring(6 + game.length() + 6, nxm.indexOf("/files"));
    	String fileId = nxm.substring(6 + game.length() + 6 + modId.length() + 7, nxm.length());
    	if(game.equals("skyrimse")) {
    		game = "skyrimspecialedition";
    	}
    	String url = "http://www.nexusmods.com/" + game + "/ajax/downloadfile?id=" + fileId + "&skipdonate";
    	
    	final WebClient webClient = new WebClient(BrowserVersion.BEST_SUPPORTED);
        webClient.getOptions().setJavaScriptEnabled(false);
        final HtmlPage page1 = webClient.getPage("http://www.nexusmods.com/games");
        final HtmlForm form = (HtmlForm) page1.getElementById("userlogin");

        final HtmlSubmitInput button = (HtmlSubmitInput) form.getInputsByValue("Login").get(0);
        final HtmlTextInput textField = form.getInputByName("username");
        textField.setValueAttribute((String) ConfigHandler.getConfigValue("username"));
        final HtmlPasswordInput textField2 = form.getInputByName("password");
        textField2.setValueAttribute((String) ConfigHandler.getConfigValue("password"));
        button.click();
        
        final HtmlPage downloadPage = webClient.getPage(url);
        final HtmlScript script = (HtmlScript)downloadPage.getFirstByXPath("//script");
        String downloadUrl = script.toString().substring(script.toString().indexOf("href") + 8, script.toString().length() - 6);
        final HtmlAnchor link = downloadPage.getAnchorByText("EU - Kent Premium");
        link.removeAttribute("href");
        link.setAttribute("href", downloadUrl);
    	
    	System.out.println("Nexus Link: " + nxm);
    	System.out.println("Game: " + game);
    	System.out.println("GameID: " + modId);
    	System.out.println("FileID: " + fileId);
    	System.out.println("Download Page: " + url);
    	System.out.println("Download Link: " + downloadUrl); 
        
        String saveDir = System.getProperty("java.io.tmpdir") + "/Java Mod Organizer";
        String saveFilePath = saveDir + File.separator + downloadUrl.toString().substring(downloadUrl.toString().lastIndexOf("/") + 1, downloadUrl.toString().lastIndexOf("?"));;
        startDownload(link.click().getWebResponse().getContentAsStream(), new File(saveFilePath));
        webClient.close();
    }
	
	private static void startDownload(InputStream is, File output) {
		Download d = new Download();
		d.setDownload(is, output);
		Thread t = new Thread(d, "Download Thread");
		t.start();
	}
}
