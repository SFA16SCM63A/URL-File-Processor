package com.ebay.core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.ebay.main.UnzipUtilityMainRunner;

public class ProcessRequests implements Runnable {

	File file;
	UnzipUtilityMainRunner unzipUtility;
	public ProcessRequests(File file,UnzipUtilityMainRunner unzipUtility) {
		this.file=file;
		this.unzipUtility=unzipUtility;
	}
	
	public void run() {
		try {
			process(file);
		} catch (FileNotFoundException e) {
			System.err.println("Failed to process thread for file "+file.getAbsolutePath());
		}
	}
	
	private void process(File file) throws FileNotFoundException {
		if(file==null)throw new IllegalArgumentException();
		Scanner sc=new Scanner(new FileInputStream(file));
		int failed=0;
		int passed=0;
		while(sc.hasNextLine()) {
			try {
				getHTML(sc.nextLine());	   
				passed++;
				//System.out.println("Yeah..!!");
			}catch(Exception e) {
				failed++;
				//System.err.println("Failed: ");
			}	
		}
		sc.close();
		unzipUtility.passedCount+=passed;
		unzipUtility.failedCount+=failed;
		System.out.println("Finished processing passedCount: "+unzipUtility.passedCount);
		System.out.println("Finished processing failedCount: "+unzipUtility.failedCount);
	}
	
	
	public static String getHTML(String urlToRead) throws Exception {
	      StringBuilder result = new StringBuilder();
	      URL url = new URL(urlToRead);
	      HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	      conn.setRequestMethod("GET");
	      BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	      String line;
	      while ((line = rd.readLine()) != null) {
	         result.append(line);
	      }
	      rd.close();
	      return result.toString();
	   }

	   
	
}
