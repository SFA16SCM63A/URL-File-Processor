package com.ebay.test;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import com.ebay.main.UnzipUtilityMainRunner;

import junit.framework.Assert;

public class TestExtractingFiles {

	@Test
	public void testValidExtractingZipFile() {
		String inputZipFile="/home/sachin/Documents/inputData.zip";
		boolean output=true;
	    try {
			new UnzipUtilityMainRunner().unZipInputFile(inputZipFile);
		} catch (IOException e) {
			e.printStackTrace();
			output=false;
		}
	    Assert.assertEquals(true, output);
	}

	@Test
	public void testInValidExtractingZipFile() {
		String inputZipFile="/home/sachin/Documents/inputData2.zip";
		boolean output=false;
	    try {
			new UnzipUtilityMainRunner().unZipInputFile(inputZipFile);
		} catch (IOException e) {
			e.printStackTrace();
			output=true;
		}
	    Assert.assertEquals(true, output);
	}
	
	@Test
	public void testValidExtractingGunZipFile() {
		String inputZipFile="/home/sachin/Documents/inputData.zip";
		String extractFilesIntoFolder="extractedFiles";
		boolean output=true;
	    try {
			new UnzipUtilityMainRunner().extractAllGunZipFiles(inputZipFile,extractFilesIntoFolder);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			output=false;
			e.printStackTrace();
		}
	    Assert.assertEquals(true, output);
	}

	
	@Test
	public void testInValidExtractingGunZipFile() {
		String inputZipFile="/home/sachin/Documents/inputDatas.zip";
		String extractFilesIntoFolder="extractedFiles";
		boolean output=false;
	    try {
			new UnzipUtilityMainRunner().extractAllGunZipFiles(inputZipFile,extractFilesIntoFolder);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			output=true;
			e.printStackTrace();
		}
	    Assert.assertEquals(true, output);
	}

}
