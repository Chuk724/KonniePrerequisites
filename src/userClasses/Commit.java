package userClasses;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.util.Scanner;

public class Commit {
	
	private String parentSha;
	//private String nextSha;
	
	private Tree pTree;
	private String summary;
	private String author;
	private String date;
	public Commit (String summary, String author) throws Exception {
		this.summary  = summary;
		this.author = author;
		
		File objects = new File ("objects");
	    if (objects.exists()==false) {
	        	objects.mkdir();
	    }
	    
	    getHead();
	    
	    SimpleDateFormat formatter = new SimpleDateFormat("MMMM dd, yyyy");
		Date date = new Date();
		this.date = formatter.format(date);
	    
		File index = new File("./index");
	    
	    if(parentSha != null) {
	    	createTreeForChild(getPTree(parentSha));
	    } else {
	    	createTree(null);
	    }
	    
	    writeFile();
	    
	    if (parentSha != null) {
	    	setAsChild();
	    }
		
		replaceHead();
		
	    index.delete();
		System.out.println(parentSha);
	}
	
	public void createTree(String previousTree) throws Exception {
		pTree = new Tree(previousTree);
	}
	
	public void createTreeForChild(String previousTree) throws Exception {
		pTree = new Tree(previousTree);
	}
	
	public void getHead() throws Exception {
		File head = new File ("HEAD");
	    if (head.exists()==false) {
	    	head.createNewFile();
	    	return;
	    }
	    
	    Scanner scanner = new Scanner(head);
	    String currentHead = "";
	    if (scanner.hasNextLine()) {
	    	currentHead = scanner.nextLine();
	    }
	    if (!currentHead.equals("")) {
	    	parentSha = currentHead;
	    }
	    scanner.close();
	    
	}
	
	private void replaceHead() throws Exception {
		File head = new File ("HEAD");
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(head, false)));
	    out.println(generateSha1String());
	    out.close();
	}
	
	
	public String getPTree(String commitSha) throws Exception {
		File file = new File("./objects/" + commitSha);
		Scanner scanner = new Scanner(file);
		String tree = scanner.nextLine();
		tree = tree.substring(tree.indexOf("/") +1);
		return tree;
	}
	
	
	public void setAsChild() throws IOException {
		File parentFile = new File("./objects/" + parentSha);
		
		File tempFile = new File("myTempFile.next");
		
		BufferedReader reader = new BufferedReader(new FileReader(parentFile));
		PrintWriter writer = new PrintWriter(new FileWriter(tempFile));
		int counter = 0;
		String currentLine;
		while((currentLine = reader.readLine()) != null) {
			counter++;
			if (counter==3) {
				writer.println("objects/" + generateSha1String());
			} else {
				writer.write(currentLine + System.getProperty("line.separator"));
			}
		}
		writer.close();
		reader.close();
		tempFile.renameTo(parentFile);
	}
	
	
	public String generateSha1String(){
		String contents = getContents();
		try {
            // getInstance() method is called with algorithm SHA-1
            MessageDigest md = MessageDigest.getInstance("SHA-1");
 
            // digest() method is called
            // to calculate message digest of the input string
            // returned as array of byte
            byte[] messageDigest = md.digest(contents.getBytes());
 
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
	
	
	public String getDate() {
		return date;
	}
	public String getContents() {
		String contents = new String ("");
		contents = contents+summary+ "\n";
		contents = contents+date+ "\n";
		contents = contents + author+ "\n";
		if (parentSha == null) {

		} else{
			contents=contents+ "objects/" + parentSha ;
		}
		return contents;
	}
	public void writeFile() throws IOException {
		String contents = new String ("");
		contents = contents+"objects/"+pTree.getSha() + "\n";
		if (parentSha == null) {
			contents=contents+ "\n";
		} else{
			contents=contents+ "objects/" + parentSha + "\n";
		}
		contents=contents+ "\n";
		/**if (nextSha == null) {
			contents=contents+ "\n";
		} else{
			contents=contents+"objects/"+nextSha + "\n";
		}**/
		contents=contents+author + "\n";
		contents=contents+date + "\n";
		contents=contents+this.summary;
		String fileName = generateSha1String();
		File commit = new File ("objects/"+fileName);
		commit.createNewFile();
		 Path p = Paths.get("objects/"+fileName);
	        try {
	            Files.writeString(p, contents, StandardCharsets.ISO_8859_1);
	        } catch (IOException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }
	}
}