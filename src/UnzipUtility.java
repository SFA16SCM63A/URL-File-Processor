import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Scanner;

import com.ebay.core.CommonHelper;
import com.ebay.implementation.GunZipHelperImpl;
import com.ebay.implementation.ZipHelperImpl;
import com.ebay.intrface.GunZipHelper;

public class UnzipUtility extends CommonHelper{

	public static void main(String[] args) throws IOException {
  		 //String inputZipFile="/home/sachin/Downloads/URLFileProcessorAssignment/URLFileProcessorAssignment/inputData.zip";
		 String inputZipFile="/home/sachin/Documents/inputData.zip";
		 String outputFolder="extractedFiles";
		 
		 new UnzipUtility().run(inputZipFile,outputFolder);
	 }

	public void run(String inputZipFile,String outputFolder) throws IOException {
		if(inputZipFile==null||outputFolder==null||inputZipFile.length()==0||outputFolder.length()==0)throw new IllegalArgumentException();

	/*	boolean deleteZipFolder=true;
	    refreshOutputFolder(outputFolder);
	    unZipInputFile(inputZipFile);
	    extractAllGunZipFiles(inputZipFile,deleteZipFolder);
	  */  
	    //readAllFiles(outputFolder);
		
		createNewFiles("output.txt");
	}
	
	
	public void readAllFiles(String folderName) throws IOException {
		File folder=new File(folderName);
		if(!folder.isDirectory()) throw new IllegalArgumentException();
		
		File outputFile=new File("output.txt");
		deleteDir(outputFile);
		outputFile.createNewFile();
		try(FileOutputStream fileOutputStream=new FileOutputStream(outputFile)){
			BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(fileOutputStream));
			for (File fileEntry : folder.listFiles()) {
		        if (!fileEntry.isDirectory()) {
		        	try(Scanner sc=new Scanner(fileEntry)){
		        			while(sc.hasNextLine()) {
		        				bufferedWriter.write(sc.nextLine()+"\n");
		        			}
		        	}
		        	System.out.println(fileEntry.getName());
		        }
		    }
			bufferedWriter.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void createNewFiles(String fileName) throws IOException {
		
		Scanner sc=new Scanner(new FileInputStream(new File(fileName)));
		int counter=0;
		int threshold=50000;
		int index=0;
		String opFileName="op"+(index++)+".txt";
		FileOutputStream fileOutputStream=new FileOutputStream(opFileName);
		BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(fileOutputStream));
		
		while(sc.hasNextLine()) {
			bufferedWriter.write(sc.nextLine()+"\n");
			counter++;
			if(counter==threshold) {
				bufferedWriter.close();
				fileOutputStream.close();
				opFileName="op"+(index++)+".txt";
				fileOutputStream=new FileOutputStream(opFileName);
				bufferedWriter=new BufferedWriter(new OutputStreamWriter(fileOutputStream));
				counter=0;
			}
		}
		
		
	}

	
	public void extractAllGunZipFiles(String inputZipFile,boolean deleteZipFolder) {
		if(inputZipFile==null||inputZipFile.trim().length()==0)throw new IllegalArgumentException();
		GunZipHelper gunZipHelper=new GunZipHelperImpl();
		gunZipHelper.extractAllGunZipFiles(inputZipFile,deleteZipFolder);
    			
	}
	
	private void unZipInputFile(String inputFileName) throws IOException {
		if(inputFileName==null||inputFileName.trim().length()==0)throw new IllegalArgumentException();
		new ZipHelperImpl().unzip(inputFileName);
	}
	
	private void refreshOutputFolder(String outputFolder) {
	     File fp=new File(outputFolder);
	     deleteDir(fp);
	     if(!fp.exists())fp.mkdir();			
	}
    
}