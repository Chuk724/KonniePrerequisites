package userClasses;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Map.Entry;

public class Tree {
//	private final int numberOfBranches = 100;
	private Tree previousTree;
	private String sha;
	
	public Tree (Tree prevTree) throws IOException {
		previousTree = prevTree;
		File objects = new File ("objects");
	    if (objects.exists()==false) {
	        	objects.mkdir();
	    }
		String rawText = "";
		//Files.createFile(newFilePath);
		
		File index = new File ("./index");
		
		//HashMap of fileNames and sha1s in index for folder - <sha1, fileName>
		HashMap<String, String> newIndex = new HashMap<String, String>();
		Scanner reader = new Scanner (index);
		while (reader.hasNextLine()) {
				String line = reader.nextLine();
				String fileName = line.substring(0, line.indexOf(" "));
				String sha1 = line.substring(line.indexOf(":") + 1);
				newIndex.put(sha1, fileName);
		}
		reader.close();
		
		//need to get data going in Tree file to make the sha for the tree file name
		
		for (String s : newIndex.keySet()) {
			rawText+= "blob :" + s + " " + newIndex.get(s);
		}
		
		sha = encryptThisString(rawText);
		String name = "./objects/"+sha+".txt";
		File tree = new File(name);
		
		/**for (String s : a) {
			String inputSha = s.substring(s.indexOf(":") + 1);
			String ogFileName = newIndex.get(inputSha);
			pw.println(s + " " + ogFileName);
		}**/
		
		PrintWriter pw = new PrintWriter(tree);
		for (String s : newIndex.keySet()) {
			pw.println("blob :" + s + " " + newIndex.get(s));
		}
		if (previousTree != null) {
			pw.println("tree : " + previousTree.getSha());
		}
		//pw.write(sb.toString());
		pw.close();		
	}
	
	/**private String readAR(ArrayList <String> as) {
		String str = "";
		for (String name: as) {
			str+=name+"/n";
		}
		return str;
	}**/
	
	 private static String encryptThisString(String input)
	    {
	        try {
	            // getInstance() method is called with algorithm SHA-1
	            MessageDigest md = MessageDigest.getInstance("SHA-1");
	 
	            // digest() method is called
	            // to calculate message digest of the input string
	            // returned as array of byte
	            byte[] messageDigest = md.digest(input.getBytes());
	 
	            // Convert byte array into signum representation
	            BigInteger no = new BigInteger(1, messageDigest);
	 
	            // Convert message digest into hex value
	            String hashtext = no.toString(16);
	 
	            // Add preceding 0s to make it 32 bit
	            while (hashtext.length() < 32) {
	                hashtext = "0" + hashtext;
	            }
	 
	            // return the HashText
	            return hashtext;
	        }
	 
	        // For specifying wrong message digest algorithms
	        catch (NoSuchAlgorithmException e) {
	            throw new RuntimeException(e);
	        }
	    }
	 
	 public String getSha() {
		 return sha;
	 }
}
