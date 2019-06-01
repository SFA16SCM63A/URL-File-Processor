package com.ebay.main;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.ebay.core.CommonHelper;
import com.ebay.core.MergeHelper;
import com.ebay.core.ProcessRequests;
import com.ebay.core.SplitHelper;
import com.ebay.implementation.GunZipHelperImpl;
import com.ebay.implementation.ZipHelperImpl;
import com.ebay.intrface.GunZipHelper;

public class UnzipUtilityMainRunner extends CommonHelper{

	public volatile int failedCount=0;
	public volatile int passedCount=0;
	
	public static void main(String[] args) throws Exception {
		 String inputZipFile="/home/sachin/Documents/inputData.zip";
		 new UnzipUtilityMainRunner().run(inputZipFile);
	 }

	public void run(String inputZipFile) throws Exception {
		if(inputZipFile==null||inputZipFile.length()==0)throw new IllegalArgumentException();
		
		String extractFilesIntoFolder="extractedFiles";
		String mergeAllInputFilesIntoSingleFile="output.txt";
		String splitBigFileIntoSmallFileFolder="splitted";
		
	    refreshOutputFolder(extractFilesIntoFolder);
	    unZipInputFile(inputZipFile);
	    extractAllGunZipFiles(inputZipFile,extractFilesIntoFolder);
	    
	    mergeAllInputFilesIntoSingleFile(extractFilesIntoFolder,mergeAllInputFilesIntoSingleFile);
	    splitBigFileIntoSmallFiles(mergeAllInputFilesIntoSingleFile,splitBigFileIntoSmallFileFolder);
		
		process(splitBigFileIntoSmallFileFolder);
		deleteDir(new File(splitBigFileIntoSmallFileFolder));
		deleteDir(new File(extractFilesIntoFolder));
	}
	
	
	public void mergeAllInputFilesIntoSingleFile(String extractedInputFilesFolderName,String mergeFileName) throws Exception {
		new MergeHelper().readAndMergeAllFiles(extractedInputFilesFolderName,mergeFileName);
	}
	
	public void splitBigFileIntoSmallFiles(String fileName,String splittedFilesFolderName) throws IOException {
		new SplitHelper().createNewFiles(fileName,splittedFilesFolderName);
	}
	
	public void process(String splittedFileFolderName) {
		ExecutorService executor = Executors.newFixedThreadPool(4);
		long startTime=System.currentTimeMillis();
		File outputFolder=new File(splittedFileFolderName);
		if(!outputFolder.exists())throw new IllegalArgumentException();
		int count=0;
		for (File fileEntry : outputFolder.listFiles()) {
	        if (!fileEntry.isDirectory()) {
	        	ProcessRequests processRequests=new ProcessRequests(fileEntry,this);
	        	executor.execute(processRequests);
	        }
		}
		executor.shutdown();
		try {
			executor.awaitTermination(1000,TimeUnit.HOURS);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Executed: "+(count++));
	    System.out.println("Total passed: "+passedCount);
	    System.out.println("Failed count: "+failedCount);
		
		long endTime=System.currentTimeMillis();
		System.out.println("Time took: "+(endTime-startTime));
		System.out.println("Failed: "+failedCount+", Total: "+(passedCount+failedCount));
	}
	
	
	
	
	public void extractAllGunZipFiles(String inputZipFile,String extractFilesIntoFolder) throws IOException {
		if(inputZipFile==null||inputZipFile.trim().length()==0)throw new IllegalArgumentException();
		GunZipHelper gunZipHelper=new GunZipHelperImpl();
		gunZipHelper.extractAllGunZipFiles(inputZipFile,extractFilesIntoFolder);
    			
	}
	
	public void unZipInputFile(String inputFileName) throws IOException {
		if(inputFileName==null||inputFileName.trim().length()==0)throw new IllegalArgumentException();
		new ZipHelperImpl().unzip(inputFileName);
	}
	
	private void refreshOutputFolder(String outputFolder) {
	     File fp=new File(outputFolder);
	     deleteDir(fp);
	     if(!fp.exists())fp.mkdir();			
	}
    
}