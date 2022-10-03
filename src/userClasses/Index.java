package userClasses;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Objects;
import java.util.Map.Entry;
import java.util.Scanner;

public class Index {
	
	//public HashMap <String, String> blobs;
	public Index () throws Exception{
		//blobs=new HashMap <String,String>();
		init();
	}
	public void init() throws IOException {
        
        File objects = new File ("objects");
        objects.mkdir();
        for (File file: Objects.requireNonNull(objects.listFiles())) {
        	if (!file.isDirectory()) {
        		file.delete();
        	}
        }
        File myObj = new File("index"); 
        if (myObj.exists()) {
        myObj.delete();
        }
        File f2=new File ("index");
        f2.createNewFile();

	}
	public void add (String filename) throws IOException {
		Blob create = new Blob (filename);
		
		String Sha1 = create.getSha1();
		//blobs.put (filename, create.getSha1());
        File file = new File("index");
        
		BufferedWriter bf = null;
		PrintWriter out = null;
		  
	        try {
	  
	            // create new BufferedWriter for the output file
	            //bf = new BufferedWriter(new FileWriter(file));
	            
	        	
	        	out = new PrintWriter(new BufferedWriter(new FileWriter(file, true)));
	            // iterate map entries
	            
	        	out.println(filename + " : " + Sha1);
	        	/**for (Entry<String, String> entry :
	                 blobs.entrySet()) {
	  
	                // put key and value separated by a colon
	                bf.write(entry.getKey() + " : "
	                         + entry.getValue());
	  
	                // new line
	                bf.newLine();
	            }**/
	  
	            out.flush();
	        }
	            catch (IOException e) {
	                e.printStackTrace();
	            }
	            finally {
	      
	                try {
	      
	                    // always close the writer
	                    out.close();
	                }
	                catch (Exception e) {
	                }
	                
	            }
	}
	
	public void delete (String filename) throws Exception {
		File file = new File ("index");
		
		BufferedWriter bf = null;
		PrintWriter out = null;
		  
	        try {
	  
	            // create new BufferedWriter for the output file
	            //bf = new BufferedWriter(new FileWriter(file));
	            
	        	
	        	out = new PrintWriter(new BufferedWriter(new FileWriter(file, true)));
	            // iterate map entries
	            
	        	out.println("*deleted* " + filename);
	        	/**for (Entry<String, String> entry :
	                 blobs.entrySet()) {
	  
	                // put key and value separated by a colon
	                bf.write(entry.getKey() + " : "
	                         + entry.getValue());
	  
	                // new line
	                bf.newLine();
	            }**/
	  
	            out.flush();
	        }
	            catch (IOException e) {
	                e.printStackTrace();
	            }
	            finally {
	      
	                try {
	      
	                    // always close the writer
	                    out.close();
	                }
	                catch (Exception e) {
	                }
	                
	            }
	}
	
	public void edit (String filename) throws Exception {
		File file = new File ("index");
		
		BufferedWriter bf = null;
		PrintWriter out = null;
		  
	        try {
	  
	            // create new BufferedWriter for the output file
	            //bf = new BufferedWriter(new FileWriter(file));
	            
	        	
	        	out = new PrintWriter(new BufferedWriter(new FileWriter(file, true)));
	            // iterate map entries
	            
	        	out.println("*edited* " + filename);
	        	/**for (Entry<String, String> entry :
	                 blobs.entrySet()) {
	  
	                // put key and value separated by a colon
	                bf.write(entry.getKey() + " : "
	                         + entry.getValue());
	  
	                // new line
	                bf.newLine();
	            }**/
	  
	            out.flush();
	        }
	            catch (IOException e) {
	                e.printStackTrace();
	            }
	            finally {
	      
	                try {
	      
	                    // always close the writer
	                    out.close();
	                }
	                catch (Exception e) {
	                }
	                
	            }
	}
	
	
	public void remove (String filename) throws IOException {
		Path p1=Paths.get(filename);
		String contents = Files.readString(p1);
		String Sha1 = encryptThisString(contents);
		
		File myObj = new File ("objects/"+Sha1);
		myObj.delete();
		File file = new File("index");
		//BufferedWriter bf = null;
		BufferedReader reader = null;
		BufferedWriter writer = null;
				
	        	File tempFile = new File("myTempFile.txt");

	        	reader = new BufferedReader(new FileReader(file));
	        	writer = new BufferedWriter(new FileWriter(tempFile));

	        	String lineToRemove = filename + " : " + Sha1;
	        	String currentLine;

	        	while((currentLine = reader.readLine()) != null) {
	        	    // trim newline when comparing with lineToRemove
	        	    //String trimmedLine = currentLine.trim();
	        	    if(currentLine.equals(lineToRemove)) continue;
	        	    writer.write(currentLine + System.getProperty("line.separator"));
	        	}
	        	writer.close(); 
	        	reader.close(); 
	        	tempFile.renameTo(file);
	        	
	        	
	        	
	        	
	            
	        
        System.out.println ("Deleted the file: "+ Sha1);
	}
	
	public static String encryptThisString(String input)
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

}
