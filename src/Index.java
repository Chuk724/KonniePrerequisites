import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Objects;
import java.util.Map.Entry;
import java.util.Scanner;

public class Index {
	
	private HashMap <String, String> blobs;
	
	public Index () {
		blobs=new HashMap <String,String>();
	}
	public void init() throws IOException {
        
        File objects = new File ("objects");
        objects.mkdir();
        for (File file: Objects.requireNonNull(objects.listFiles())) {
        	if (!file.isDirectory()) {
        		file.delete();
        	}
        }
        File myObj = new File("index.txt"); 
        if (myObj.exists()) {
        myObj.delete();
        }
        File f2=new File ("index.txt");
        f2.createNewFile();

	}
	public void add (String filename) throws IOException {
		Blob create = new Blob (filename);
		blobs.put (filename, create.getSha1());
        File file = new File("index.txt");
		 BufferedWriter bf = null;
		  
	        try {
	  
	            // create new BufferedWriter for the output file
	            bf = new BufferedWriter(new FileWriter(file));
	  
	            // iterate map entries
	            for (Entry<String, String> entry :
	                 blobs.entrySet()) {
	  
	                // put key and value separated by a colon
	                bf.write(entry.getKey() + " : "
	                         + entry.getValue());
	  
	                // new line
	                bf.newLine();
	            }
	  
	            bf.flush();
	        }
	            catch (IOException e) {
	                e.printStackTrace();
	            }
	            finally {
	      
	                try {
	      
	                    // always close the writer
	                    bf.close();
	                }
	                catch (Exception e) {
	                }
	                
	            }
	}
	public void remove (String filename) throws IOException {
		String Sha1 = blobs.remove(filename);
		File myObj = new File ("objects/"+Sha1+".txt");
		myObj.delete();
		File file = new File("index.txt");
		 BufferedWriter bf = null;
		  
	        try {
	  
	            // create new BufferedWriter for the output file
	            bf = new BufferedWriter(new FileWriter(file));
	  
	            // iterate map entries
	            for (Entry<String, String> entry :
	                 blobs.entrySet()) {
	  
	                // put key and value separated by a colon
	                bf.write(entry.getKey() + " : "
	                         + entry.getValue());
	  
	                // new line
	                bf.newLine();
	            }
	  
	            bf.flush();
	        }
	            catch (IOException e) {
	                e.printStackTrace();
	            }
	            finally {
	      
	                try {
	      
	                    // always close the writer
	                    bf.close();
	                }
	                catch (Exception e) {
	                }
	                
	            }
        System.out.println ("Deleted the file: "+ Sha1);
	}

}
