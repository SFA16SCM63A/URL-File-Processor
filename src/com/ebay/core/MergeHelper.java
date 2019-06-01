package com.ebay.core;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.Scanner;

public class MergeHelper extends CommonHelper {

	public void readAndMergeAllFiles(String folderName,String mergeFileName) throws Exception {
		File folder=new File(folderName);
		if(!folder.isDirectory()) throw new IllegalArgumentException();
		
		File outputFile=new File(mergeFileName);
		deleteDir(outputFile);
		outputFile.createNewFile();
		try(FileOutputStream fileOutputStream=new FileOutputStream(outputFile)){
			BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(fileOutputStream));
			for (File fileEntry : folder.listFiles()) {
				if(fileEntry.getAbsolutePath().contains(".gz"))throw new IllegalArgumentException("Invalid input file "+fileEntry.getAbsolutePath());
		        if (!fileEntry.isDirectory()) {
		        	try(Scanner sc=new Scanner(fileEntry)){
		        			while(sc.hasNextLine()) {
		        				bufferedWriter.write(sc.nextLine()+"\n");
		        			}
		        	}
		        	System.out.println("Merged: "+fileEntry.getName());
		        }
		    }
			bufferedWriter.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
}
