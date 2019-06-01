/*
 * package com.ebay.core; import java.io.BufferedOutputStream; import
 * java.io.BufferedWriter; import java.io.File; import java.io.FileInputStream;
 * import java.io.FileOutputStream; import java.io.IOException; import
 * java.io.OutputStreamWriter; import java.util.LinkedList; import
 * java.util.Queue; import java.util.Scanner; import java.util.Set; import
 * java.util.TreeSet;
 * 
 * import com.ebay.core.CommonHelper; import
 * com.ebay.implementation.GunZipHelperImpl; import
 * com.ebay.implementation.ZipHelperImpl; import com.ebay.intrface.GunZipHelper;
 * 
 * public class BackUp extends CommonHelper{
 * 
 * public static void main(String[] args) throws IOException { //String
 * inputZipFile=
 * "/home/sachin/Downloads/URLFileProcessorAssignment/URLFileProcessorAssignment/inputData.zip";
 * String inputZipFile="/home/sachin/Documents/inputData.zip"; String
 * outputFolder="extractedFiles";
 * 
 * new BackUp().run(inputZipFile,outputFolder); }
 * 
 * public void run(String inputZipFile,String outputFolder) throws IOException {
 * if(inputZipFile==null||outputFolder==null||inputZipFile.length()==0||
 * outputFolder.length()==0)throw new IllegalArgumentException();
 * 
 * boolean deleteZipFolder=true; refreshOutputFolder(outputFolder);
 * unZipInputFile(inputZipFile);
 * extractAllGunZipFiles(inputZipFile,deleteZipFolder);
 * 
 * readAndMergeAllFiles(outputFolder);
 * 
 * //createNewFiles("output.txt");
 * 
 * File fp=mergeFiles("splitted");
 * System.out.println("Merged final : "+fp.getAbsolutePath()); }
 * 
 * 
 * public File mergeFiles(String folderName) throws IOException { File
 * folder=new File(folderName); if(!folder.isDirectory()) throw new
 * IllegalArgumentException(); Queue<File> queue=new LinkedList<>(); for (File
 * fileEntry : folder.listFiles()) { if (!fileEntry.isDirectory()) {
 * queue.add(fileEntry); } } int index=0; while(queue.size()!=1) { File
 * fp1=queue.remove(); File fp2=queue.remove(); File newFile=new
 * File("merged"+(index++)); if(!newFile.exists())newFile.createNewFile();
 * 
 * FileOutputStream fileOutputStream=new FileOutputStream(newFile);
 * BufferedWriter bufferedWriter=new BufferedWriter(new
 * OutputStreamWriter(fileOutputStream)); Scanner sc1=new Scanner(new
 * FileInputStream(fp1)); Scanner sc2=new Scanner(new FileInputStream(fp2));
 * String val1=null; String val2=null;
 * 
 * if(sc1.hasNextLine())val1=sc1.nextLine();
 * if(sc2.hasNextLine())val2=sc2.nextLine();
 * 
 * while(val1!=null&&val2!=null) { if(val1.equalsIgnoreCase(val2)){
 * if(sc1.hasNextLine())val1=sc1.nextLine(); else val1=null; }else
 * if(val1.compareTo(val2)>0) { bufferedWriter.write(val1);
 * if(sc1.hasNextLine())val1=sc1.nextLine(); else val1=null; }else {
 * bufferedWriter.write(val2); if(sc2.hasNextLine())val2=sc2.nextLine(); else
 * val2=null; } } while(val1!=null) { bufferedWriter.write(val1);
 * if(sc1.hasNextLine())val1=sc1.nextLine(); else val1=null; } while(val2!=null)
 * { bufferedWriter.write(val2); if(sc2.hasNextLine())val2=sc2.nextLine(); else
 * val2=null; } queue.add(newFile);
 * System.out.println("Merged: "+newFile.getAbsolutePath()); }
 * 
 * return queue.remove(); }
 * 
 * 
 * public void readAndMergeAllFiles(String folderName) throws IOException { File
 * folder=new File(folderName); if(!folder.isDirectory()) throw new
 * IllegalArgumentException();
 * 
 * File outputFile=new File("output.txt"); deleteDir(outputFile);
 * outputFile.createNewFile(); try(FileOutputStream fileOutputStream=new
 * FileOutputStream(outputFile)){ BufferedWriter bufferedWriter=new
 * BufferedWriter(new OutputStreamWriter(fileOutputStream)); for (File fileEntry
 * : folder.listFiles()) { if (!fileEntry.isDirectory()) { try(Scanner sc=new
 * Scanner(fileEntry)){ while(sc.hasNextLine()) {
 * bufferedWriter.write(sc.nextLine()+"\n"); } }
 * System.out.println(fileEntry.getName()); } } bufferedWriter.close();
 * }catch(Exception e) { e.printStackTrace(); } }
 * 
 * public void createNewFiles(String fileName) throws IOException {
 * 
 * File outputFile=new File("splitted"); deleteDir(outputFile);
 * outputFile.mkdir();
 * 
 * Scanner sc=new Scanner(new FileInputStream(new File(fileName))); int
 * counter=0; int threshold=50000; int index=0; String
 * opFileName="splitted/op"+(index++)+".txt"; FileOutputStream
 * fileOutputStream=new FileOutputStream(opFileName); BufferedWriter
 * bufferedWriter=new BufferedWriter(new OutputStreamWriter(fileOutputStream));
 * Set<String> set=new TreeSet<>(); while(sc.hasNextLine()) {
 * set.add(sc.nextLine()); counter++; if(counter==threshold) { for(String
 * str:set) { bufferedWriter.write(str+"\n"); } set=new TreeSet<>();
 * bufferedWriter.close(); fileOutputStream.close();
 * opFileName="splitted/op"+(index++)+".txt"; fileOutputStream=new
 * FileOutputStream(opFileName); bufferedWriter=new BufferedWriter(new
 * OutputStreamWriter(fileOutputStream)); counter=0; } }
 * 
 * for(String str:set) { bufferedWriter.write(str+"\n"); } set=new TreeSet<>();
 * bufferedWriter.close(); fileOutputStream.close();
 * 
 * 
 * }
 * 
 * 
 * public void extractAllGunZipFiles(String inputZipFile,boolean
 * deleteZipFolder) {
 * if(inputZipFile==null||inputZipFile.trim().length()==0)throw new
 * IllegalArgumentException(); GunZipHelper gunZipHelper=new GunZipHelperImpl();
 * gunZipHelper.extractAllGunZipFiles(inputZipFile,deleteZipFolder);
 * 
 * }
 * 
 * private void unZipInputFile(String inputFileName) throws IOException {
 * if(inputFileName==null||inputFileName.trim().length()==0)throw new
 * IllegalArgumentException(); new ZipHelperImpl().unzip(inputFileName); }
 * 
 * private void refreshOutputFolder(String outputFolder) { File fp=new
 * File(outputFolder); deleteDir(fp); if(!fp.exists())fp.mkdir(); }
 * 
 * }
 */