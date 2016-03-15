package com.kaancelen.charterv3.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public class Util {

	public static void showMessage(String title, String detail){
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(title, detail));
	}
	
	public static String getFileExtension(String filename){
		String extension = "";

		int i = filename.lastIndexOf('.');
		if (i > 0) {
		    extension = filename.substring(i+1);
		}
		
		return extension;
	}
	
	public static void createDirectories(String path){
		int index = path.lastIndexOf(File.separator);
		if(index == -1)
			return;
		String destinationPath = path.substring(0, index);
		createDirectories(destinationPath);
		File dir = new File(destinationPath);
		if(!dir.exists())
			dir.mkdir();
	}
	
	public static boolean copyFile(String destinationPath, InputStream inputStream){
		OutputStream outputStream = null;
		try{
			createDirectories(destinationPath);//create directories that not exist
			outputStream = new FileOutputStream(destinationPath);
			int read = 0;
			byte[] bytes = new byte[1024];
			while((read = inputStream.read(bytes)) != -1){
				outputStream.write(bytes, 0, read);
			}
		}catch(IOException e){
			System.err.println(e.getLocalizedMessage());
			return false;
		} finally {
			if(inputStream != null){ try {inputStream.close();} catch (IOException e) {} }
			
			if(outputStream != null){ try { outputStream.flush(); outputStream.close(); } catch (IOException e) {} }
		}
		return true;
	}
	
	public static String replaceTurkishChars(String inString) {
        inString = inString.replace("ı", "i");
        inString = inString.replace("ş", "s");
        inString = inString.replace("ğ", "g");
        inString = inString.replace("ü", "u");
        inString = inString.replace("ö", "o");
        inString = inString.replace("ç", "c");
        inString = inString.replace("İ", "I");
        inString = inString.replace("Ş", "S");
        inString = inString.replace("Ğ", "G");
        inString = inString.replace("Ü", "U");
        inString = inString.replace("Ö", "O");
        inString = inString.replace("Ç", "C");
        inString = inString.replace("\"", "'");
        return inString;
    }
}
