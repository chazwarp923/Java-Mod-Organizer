/**
* @author Chaz Kerby ©2016
*/
package tech.chazwarp923.javamodorganizer;

import java.io.IOException;

import org.apache.commons.configuration.XMLConfiguration;

import tech.chazwarp923.javamodorganizer.downloads.DownloadManager;

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
