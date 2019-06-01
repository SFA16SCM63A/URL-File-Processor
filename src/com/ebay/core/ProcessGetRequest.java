package com.ebay.core;

import java.awt.FlowLayout;
import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.swing.JFrame;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;

public class ProcessGetRequest {
	
	public volatile int failedCount=0;
	public volatile int passedCount=0;
	JProgressBar jProgressBar;
	int completedPercentage;
	
	public void process(String splittedFileFolderName) {
		
		jProgressBar=loadUI();
		completedPercentage=0;
		
		ExecutorService executor = Executors.newFixedThreadPool(4);
		long startTime=System.currentTimeMillis();
		File outputFolder=new File(splittedFileFolderName);
		if(!outputFolder.exists())throw new IllegalArgumentException();
		int count=0;
		jProgressBar.setMaximum(outputFolder.listFiles().length);
		for (File fileEntry : outputFolder.listFiles()) {
	        if (!fileEntry.isDirectory()) {
	        	ProcessRequest processRequests=new ProcessRequest(fileEntry,this);
	        	executor.execute(processRequests);
	        }
		}
		executor.shutdown();
		try {
			executor.awaitTermination(100,TimeUnit.HOURS);
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
	
	public JProgressBar loadUI() {
		final int MAX = 100;
        final JFrame frame = new JFrame("Processing...");
 
        // creates progress bar
        final JProgressBar pb = new JProgressBar();
        pb.setMinimum(0);
        pb.setMaximum(MAX);
        pb.setStringPainted(true);
 
        // add progress bar
        frame.setLayout(new FlowLayout());
        frame.getContentPane().add(pb);
 
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 200);
        frame.setVisible(true);
        return pb;
	}
	
	
	public synchronized void updateProgressBar() {
		try {
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                   jProgressBar.setValue(++completedPercentage);
                }
            });
            java.lang.Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
	}
}
