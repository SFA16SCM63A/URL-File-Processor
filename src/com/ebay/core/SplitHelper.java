package com.ebay.core;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

public class SplitHelper extends CommonHelper {

	public void createNewFiles(String fileName,String splittedFolderName) throws IOException {
		File outputFile=new File(splittedFolderName);
		deleteDir(outputFile);
		outputFile.mkdir();
		
		Scanner sc=new Scanner(new FileInputStream(new File(fileName)));
		int counter=0;
		int threshold=50000;
		int index=0;
		String opFileName=splittedFolderName+"/op"+(index++)+".txt";
		FileOutputStream fileOutputStream=new FileOutputStream(opFileName);
		BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(fileOutputStream));
		Set<String> set=new TreeSet<>();
		while(sc.hasNextLine()) {
			set.add(sc.nextLine());
			counter++;
			if(counter==threshold) {
				for(String str:set) {
					bufferedWriter.write(str+"\n");	
				}
				set=new TreeSet<>();
				bufferedWriter.close();
				fileOutputStream.close();
				opFileName=splittedFolderName+"/op"+(index++)+".txt";
				fileOutputStream=new FileOutputStream(opFileName);
				bufferedWriter=new BufferedWriter(new OutputStreamWriter(fileOutputStream));
				counter=0;
			}
		}
		
		for(String str:set) {
			bufferedWriter.write(str+"\n");	
		}
		set=new TreeSet<>();
		bufferedWriter.close();
		fileOutputStream.close();
		
		
	}

}
