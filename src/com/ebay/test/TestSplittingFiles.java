package com.ebay.test;

import org.junit.Test;

import com.ebay.main.UnzipUtilityMainRunner;

import junit.framework.Assert;

public class TestSplittingFiles {

	
	@Test
	public void testSplittingFile() {
		String mergeAllInputFilesIntoSingleFile="output.txt";
		String splitBigFileIntoSmallFileFolder="splitted";
		boolean output=true;
		try {
			new UnzipUtilityMainRunner(). splitBigFileIntoSmallFiles(mergeAllInputFilesIntoSingleFile,splitBigFileIntoSmallFileFolder);
		} catch (Exception e) {
			e.printStackTrace();
			output=false;
		}
		Assert.assertEquals(true, output);
	}
	
	
}
