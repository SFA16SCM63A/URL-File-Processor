package com.ebay.test;


import org.junit.Assert;
import org.junit.Test;

import com.ebay.main.UnzipUtilityMainRunner;


public class TestMergingFiles {

	
	
	
	@Test
	public void testInValidMergingFile() {
		
		String extractFilesIntoFolder="inputData";
		String mergeAllInputFilesIntoSingleFile="output.txt";
		boolean output=true;
		try {
			new UnzipUtilityMainRunner().mergeAllInputFilesIntoSingleFile(extractFilesIntoFolder,mergeAllInputFilesIntoSingleFile);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			output=true;
			System.out.println("kkkkkkkkkkkkkkkkkkkkkkkkkkkkkk");
		}

		Assert.assertEquals(true, output);
	}
	
	
	@Test
	public void testValidMergingFile() {
		
		String extractFilesIntoFolder="extractedFiles";
		String mergeAllInputFilesIntoSingleFile="output.txt";
		boolean output=true;
		try {
			new UnzipUtilityMainRunner().mergeAllInputFilesIntoSingleFile(extractFilesIntoFolder,mergeAllInputFilesIntoSingleFile);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			output=false;
		}

		Assert.assertEquals(true, output);
	}
}
