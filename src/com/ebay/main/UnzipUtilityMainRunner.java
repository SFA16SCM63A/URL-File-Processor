package com.ebay.main;
import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileSystemView;

import com.ebay.core.CommonHelper;
import com.ebay.core.MergeHelper;
import com.ebay.core.ProcessGetRequest;
import com.ebay.core.SplitHelper;
import com.ebay.implementation.GunZipHelperImpl;
import com.ebay.implementation.ZipHelperImpl;
import com.ebay.intrface.GunZipHelper;

public class UnzipUtilityMainRunner extends CommonHelper{


	
	public static void main(String[] args) throws Exception {
		
		JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
		int returnValue = jfc.showOpenDialog(null);
		if (returnValue == JFileChooser.APPROVE_OPTION) {
			File selectedFile = jfc.getSelectedFile();
			String inputZipFile=selectedFile.getAbsolutePath();
			new UnzipUtilityMainRunner().run(inputZipFile);
		}else {
			System.err.println("Failed to process. Select valid ZIP file");
		}
		
		 
	 }

	public void run(String inputZipFile) throws Exception {
		if(inputZipFile==null||inputZipFile.length()==0)throw new IllegalArgumentException();
		
		String extractFilesIntoFolder="extractedFiles";
		String mergeAllInputFilesIntoSingleFile="output.txt";
		String splitBigFileIntoSmallFileFolder="splitted";
		
		/*
		 * This function will delete folder named 'extractedFiles'
		 */
	    refreshOutputFolder(extractFilesIntoFolder);
	    
	    /*
	     * This function will Unzip all input files inside ZIP file
	     */
	    unZipInputFile(inputZipFile);
	    
	    /*
	     * This function will UnZip for .gz files, and put it into 'extractedFiles' folder
	     */
	    extractAllGunZipFiles(inputZipFile,extractFilesIntoFolder);
	    
	    /*
	     * This function will merge all input files into one Single input file
	     */
	    mergeAllInputFilesIntoSingleFile(extractFilesIntoFolder,mergeAllInputFilesIntoSingleFile);
	    
	    
	    /*
	     * This function will split Single file into small files with 50000 urls each
	     */
	    splitBigFileIntoSmallFiles(mergeAllInputFilesIntoSingleFile,splitBigFileIntoSmallFileFolder);
		
	   
	    processGetRequest(splitBigFileIntoSmallFileFolder);	
		
		deleteDir(new File(splitBigFileIntoSmallFileFolder));
		deleteDir(new File(extractFilesIntoFolder));
	}
	
	

	
	public void mergeAllInputFilesIntoSingleFile(String extractedInputFilesFolderName,String mergeFileName) throws Exception {
		new MergeHelper().readAndMergeAllFiles(extractedInputFilesFolderName,mergeFileName);
	}
	
	public void splitBigFileIntoSmallFiles(String fileName,String splittedFilesFolderName) throws IOException {
		new SplitHelper().createNewFiles(fileName,splittedFilesFolderName);
	}
	

	public void processGetRequest(String splitBigFileIntoSmallFileFolder) {
		new ProcessGetRequest().process(splitBigFileIntoSmallFileFolder);	
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