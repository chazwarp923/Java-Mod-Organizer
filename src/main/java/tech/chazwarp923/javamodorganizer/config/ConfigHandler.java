package tech.chazwarp923.javamodorganizer.config;

import java.io.File;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;

public class ConfigHandler {

	protected static XMLConfiguration config;
	
	private static String getConfigLocation() {
	    return System.getProperty("user.home") + File.separator + ".JavaModOrganizer/";
	}
	
	private static void setConfigDefaults() {
		config.setProperty("username", " ");
		config.setProperty("password", " ");
		try {
			config.save();
		} catch (ConfigurationException e) {
			e.printStackTrace();
		}
	}
	
	public static void loadConfig() {
		File configFile = new File(getConfigLocation());
		if(!configFile.exists())
			configFile.mkdirs();
		configFile = new File(getConfigLocation() + "JavaModOrganizer.xml");
		config = new XMLConfiguration();
		config.setFile(configFile);
		if(!configFile.exists()) {
			try {
				config.save();
				setConfigDefaults();
			} catch (ConfigurationException e) {
				e.printStackTrace();
			}
		}
		try {
			config.load();
		} catch (ConfigurationException e) {
			e.printStackTrace();
		}
		config.setAutoSave(true);
		config.setReloadingStrategy(new FileChangedReloadingStrategy());
	}
	
	public static void setConfigValue(String property, Object value) {
		config.setProperty(property, value);
	}
	
	public static Object getConfigValue(String property) {
		return config.getProperty(property);
	}
	
}
