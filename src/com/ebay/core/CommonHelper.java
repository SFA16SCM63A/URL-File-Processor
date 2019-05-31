package com.ebay.core;
import java.io.File;

public class CommonHelper {
	
	public void deleteDir(File file) {
		    File[] contents = file.listFiles();
		    if (contents != null) {
		        for (File f : contents) {
		            deleteDir(f);
		        }
		    }
		    file.delete();
	 }
}
