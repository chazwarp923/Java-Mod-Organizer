/**
* @author Chaz Kerby ©2016
*/
package tech.chazwarp923.javamodorganizer.downloads;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.FileUtils;

public class Download implements Runnable {
	
	private InputStream is;
	private File output;
	
	public void run() {
		try {
			FileUtils.copyInputStreamToFile(is, output);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void setDownload(InputStream is, File output) {
		this.is = is;
		this.output = output;
	}
}
