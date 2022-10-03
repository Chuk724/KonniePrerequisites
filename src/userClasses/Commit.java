package userClasses;
import java.io.File;
import java.io.IOException;
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

public class Commit {
	private Commit nextCommit;
	private Commit parentCommit;
	private Tree pTree;
	private String summary;
	private String author;
	private String date;
	public Commit (String summary, String author, Commit parent) throws Exception {
		this.summary  = summary;
		this.author = author;
		parentCommit = parent;
		
		File objects = new File ("objects");
	    if (objects.exists()==false) {
	        	objects.mkdir();
	    }
	    SimpleDateFormat formatter = new SimpleDateFormat("MMMM dd, yyyy");
		Date date = new Date();
		this.date = formatter.format(date);
	    
		
		if (parentCommit!=null) {
			parentCommit.setNext(this);
			parentCommit.writeFile();
			createTree(parentCommit.getPrevTree());
		} else {
			createTree(null);
		}
		File index = new File("./index");
	    index.delete();
	    writeFile();
	    
	}
	
	public void createTree(Tree previousTree) throws Exception {
		pTree = new Tree(previousTree);
	}
	
	public Tree getPrevTree() throws Exception {
		return pTree;
	}
	
	public void setNext(Commit next) {
		
		nextCommit = next;
		
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
		if (parentCommit ==null) {

		} else{
			contents=contents+ "objects/" + parentCommit.generateSha1String() ;
		}
		return contents;
	}
	public void writeFile() throws IOException {
		String contents = new String ("");
		contents = contents+"objects/"+pTree.getSha() + "\n";
		if (parentCommit ==null) {
			contents=contents+ "\n";
		} else{
			contents=contents+ "objects/" + parentCommit.generateSha1String() + "\n";
		}
		if (nextCommit ==null) {
			contents=contents+ "\n";
		} else{
			contents=contents+"objects/"+nextCommit.generateSha1String() + "\n";
		}
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