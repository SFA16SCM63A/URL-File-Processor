package com.ebay.core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
public class ProcessRequest implements Runnable {

	File file;
	ProcessGetRequest processGetRequest;
	public ProcessRequest(File file,ProcessGetRequest processGetRequest) {
		this.file=file;
		this.processGetRequest=processGetRequest;
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
		processGetRequest.passedCount+=passed;
		processGetRequest.failedCount+=failed;
		System.out.println("Finished processing passedCount: "+processGetRequest.passedCount);
		System.out.println("Finished processing failedCount: "+processGetRequest.failedCount);
		processGetRequest.updateProgressBar();
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
