package tech.chazwarp923.MorrowWarpManager.config;

import java.io.File;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;

public class ConfigHandler {

	public static String getConfigLocation() {
	    return System.getProperty("user.home") + File.separator + ".MorrowWarpManager/MorrowWarpManager.xml";
	}
	
	public static XMLConfiguration loadConfig() throws ConfigurationException {
		File configFile = new File(getConfigLocation());
		if(!configFile.exists()) {
			configFile.mkdirs();
		}
		XMLConfiguration config = new XMLConfiguration();
		config.setFile(configFile);
		config.load();
		config.setAutoSave(true);
		config.setReloadingStrategy(new FileChangedReloadingStrategy());
		return config;
	}
	
}
