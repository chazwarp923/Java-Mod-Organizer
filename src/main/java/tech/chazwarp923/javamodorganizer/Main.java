/**
* @author Chaz Kerby ©2016
*/
package tech.chazwarp923.javamodorganizer;

import java.io.IOException;

import tech.chazwarp923.javamodorganizer.config.ConfigHandler;
import tech.chazwarp923.javamodorganizer.downloads.DownloadManager;

public class Main {
	
    public static void main(String[] args) {
		
    	ConfigHandler.loadConfig();
        
    	if(args.length > 0) {
            if(args[0].contains("nxm://")) {
                try {
					DownloadManager.handleNexusLink(args[0]);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
            }
        }
        else {
        	
        }
    }
}
