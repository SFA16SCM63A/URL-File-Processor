package com.ebay.implementation;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;

import com.ebay.core.CommonHelper;
import com.ebay.intrface.GunZipHelper;

public class GunZipHelperImpl extends CommonHelper implements GunZipHelper{
	
    int uniqueIdentifier;
    public GunZipHelperImpl() {
    	uniqueIdentifier=0;
    }

	public void extractAllGunZipFiles(String inputFilePath,String extractFilesIntoFolder) throws IOException {
		
		File fileExtracted=new File(extractFilesIntoFolder);
		if(!fileExtracted.exists())fileExtracted.mkdirs();
		
	     inputFilePath=getOutputFileName(inputFilePath);
	     System.out.println("InputFilePath: "+inputFilePath);
		  File file =new File(inputFilePath); 
		  if(file.isDirectory()) { 
			  listFilesForFolder(file,extractFilesIntoFolder); 
		  }else { 
			 unGunzipFile(new File(inputFilePath),"/"+inputFilePath+"/url");
		  }
	}
	
	 private String getOutputFileName(String fileName) {
		 String[] strs=fileName.split("/");
		 int index=0;
		 while(index<strs[strs.length-1].length()&&strs[strs.length-1].charAt(index)!='.')index++;
		 return strs[strs.length-1].substring(0,index);
	 }
	
	private void listFilesForFolder(File folder,String extractFilesIntoFolder) throws IOException {
	    for (File fileEntry : folder.listFiles()) {
	        if (fileEntry.isDirectory()) {
	            listFilesForFolder(fileEntry,extractFilesIntoFolder);
	        } else {
	         new GunZipHelperImpl().unGunzipFile(fileEntry,extractFilesIntoFolder+"/"+uniqueIdentifier++);	
	        }
	    }
	}
	
	
	 private void unGunzipFile(File compressedFile, String decompressedFile) throws IOException {
	        byte[] buffer = new byte[1024];
	        FileInputStream fileIn = new FileInputStream(compressedFile);

            GZIPInputStream gZIPInputStream = new GZIPInputStream(fileIn);
            FileOutputStream fileOutputStream = new FileOutputStream(decompressedFile);
            int bytes_read;
            while ((bytes_read = gZIPInputStream.read(buffer)) > 0) {
                fileOutputStream.write(buffer, 0, bytes_read);
            }
            gZIPInputStream.close();
            fileOutputStream.close();
            System.out.println("The file was decompressed successfully!");
	    }
	 

	 
	 
	 
	
}
