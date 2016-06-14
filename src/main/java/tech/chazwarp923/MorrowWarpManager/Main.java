package tech.chazwarp923.MorrowWarpManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

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
    	String url = nxm;
        url = url.replace("nxm://", "http://www.nexusmods.com/");
        System.out.println(url);
        URL nexusLink = new URL(url);
		
        //Begin code borrowed from http://www.codejava.net/java-se/networking/use-httpurlconnection-to-download-file-from-an-http-url
        HttpURLConnection httpConn = (HttpURLConnection) nexusLink.openConnection();
        int responseCode = httpConn.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            String fileName = "";
            String disposition = httpConn.getHeaderField("Content-Disposition");
            String contentType = httpConn.getContentType();
            int contentLength = httpConn.getContentLength();
 
            if (disposition != null) {
                // extracts file name from header field
                int index = disposition.indexOf("filename=");
                if (index > 0) {
                    fileName = disposition.substring(index + 10,
                            disposition.length() - 1);
                }
            } else {
                // extracts file name from URL
                fileName = url.substring(url.lastIndexOf("/") + 1,
                		url.length());
            }
 
            System.out.println("Content-Type = " + contentType);
            System.out.println("Content-Disposition = " + disposition);
            System.out.println("Content-Length = " + contentLength);
            System.out.println("fileName = " + fileName);
 
            // opens input stream from the HTTP connection
            InputStream inputStream = httpConn.getInputStream();
            String saveDir = System.getProperty("java.io.tmpdir") + "/MorrowWarpManager";
            String saveFilePath = saveDir + File.separator + fileName;
             
            // opens an output stream to save into file
            File tmpFile = new File(saveDir);
            tmpFile.mkdirs();
            FileOutputStream outputStream = new FileOutputStream(saveFilePath);
 
            int bytesRead = -1;
            byte[] buffer = new byte[4096];
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
 
            outputStream.close();
            inputStream.close();
 
            System.out.println("File downloaded");
        } else {
            System.out.println("No file to download. Server replied HTTP code: " + responseCode);
        }
        httpConn.disconnect();
    }
    //End borrowed code
}
