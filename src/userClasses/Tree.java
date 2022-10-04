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
	private int numDeleted;
	HashMap<String, String> newIndex;
	
	public Tree (Tree prevTree) throws IOException {
		numDeleted = 0;
		previousTree = prevTree;
		File objects = new File ("objects");
	    if (objects.exists()==false) {
	        	objects.mkdir();
	    }
		String rawText = "";
		//Files.createFile(newFilePath);
		
		//HashMap of fileNames and sha1s in index for folder - <sha1, fileName>
		newIndex = new HashMap<String, String>();
		
		//need to get data going in Tree file to make the sha for the tree file name
		
		readIndex();
		
		for (String s : newIndex.keySet()) {
			rawText+= "blob :" + s + " " + newIndex.get(s);
		}
		
		sha = encryptThisString(rawText);
		String name = "./objects/"+sha;
		File tree = new File(name);
		
		/**for (String s : a) {
			String inputSha = s.substring(s.indexOf(":") + 1);
			String ogFileName = newIndex.get(inputSha);
			pw.println(s + " " + ogFileName);
		}**/
		
		PrintWriter pw = new PrintWriter(tree);
		for (String s : newIndex.keySet()) {
			pw.println("blob : " + s + " " + newIndex.get(s));
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
	}
	 * @throws FileNotFoundException **/
	
	private void readIndex() throws FileNotFoundException {
		File index = new File ("./index");
		
		Scanner reader = new Scanner (index);
		while (reader.hasNextLine()) {
				String line = reader.nextLine();
				if (!line.contains("*deleted*") && !line.contains("*edited*")) {
					String fileName = line.substring(0, line.indexOf(" "));
					String sha1 = line.substring(line.indexOf(":") + 2);
					newIndex.put(sha1, fileName);
				} else {
					String state = line.substring(0, line.indexOf(" "));
					numDeleted++;
					line = line.substring(line.indexOf(" ")+1);
					String fileName = line.substring(0);
					//System.out.println(fileName);
					Tree pTree = findGoodBlobs(fileName, previousTree);
					System.out.println("most recent clean tree: " + previousTree.getSha());
					//newIndex.put(fileName, state);
				}
				
		}
		reader.close();
	}
	
	private Tree findGoodBlobs(String fileName, Tree previous) throws FileNotFoundException {
		Tree returnTree = null;
		boolean deleted = false;
		if (previous != null) {
			File prevTree = new File("./objects/" + previous.getSha());
			//Tree currentTree = currentTree.getPreviousTree();
			//System.out.println(previous.getSha());
			//Tree currentTree = getPreviousTree();
			Scanner reader = new Scanner(prevTree);
			while (reader.hasNextLine()) {
				String line = reader.nextLine();
				System.out.println(deleted);
				if (returnTree != null) {
					System.out.println("current sha of return tree " + returnTree.getSha());
				} else {
					System.out.println("returnTree is null right now");
				}
				if (!line.contains(fileName)) {
					String type = line.substring(0, line.indexOf(" "));
					System.out.println(type);
					if (type.equals("blob")) {
						line = line.substring(line.indexOf(":") + 2);
						String sha = line.substring(0, line.indexOf(" "));
						line = line.substring(line.indexOf(" ") + 1);
						String file = line.substring(0);
						newIndex.put(sha, file);
					} else if (type.equals("tree") && deleted == false) {
						line = line.substring(line.indexOf(":") + 2);
						String sha = line.substring(0);
						System.out.println(sha);
						Tree nextTree = previous.getPreviousTree();
						System.out.println("About to go into this tree: " + nextTree.getSha());
						findGoodBlobs(fileName, nextTree);
					}
				} else {
					returnTree = previous.getPreviousTree();
					System.out.println("Sha of the last tree: " + returnTree.getSha());
					deleted = true;
					previousTree = returnTree;
				}
			}
			//System.out.println("Sha of final tree: " + returnTree.getSha());
			return returnTree;
		}
		//System.out.println("Sha of the last tree: " + returnTree.getSha());
		return returnTree;
		
	}
	
	private Tree getPreviousTree() {
		return previousTree;
	}
	
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
