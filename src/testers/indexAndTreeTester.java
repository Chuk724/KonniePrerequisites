package testers;
import userClasses.Index;
import userClasses.Tree;
import userClasses.Blob;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class indexAndTreeTester {
	private static String fileName = "test.txt";
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		File newFile = new File(fileName);
		newFile.createNewFile();
		PrintWriter pw = new PrintWriter(newFile);
		pw.write("Test Content.");
		pw.close();
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		/**File testerFile = new File(fileName);
		testerFile.delete();
		File indexFile = new File("./index");
		indexFile.delete();**/
	}

	@Test
	void test() throws Exception{
		Index idx = new Index();
		File idxFile = new File("./index");
		Path indexFile = Path.of("./index");
		assertTrue(idxFile.exists());
		
		
		idx.add(fileName);
		Path p1=Paths.get(fileName);
		String contents = Files.readString(p1);
		String sha1 = Index.encryptThisString(contents);
		
		//System.out.println(idx.blobs.get(fileName));
		System.out.println(fileName + " : " + sha1);
		assertTrue(Files.readString(indexFile).contains(fileName + " : " + sha1));
		
		
		Tree tree1 = new Tree(null);
		File treeFile = new File("./objects/" + tree1.getSha());
		assertTrue(treeFile.exists());
		
		idx.remove(fileName);
		
	}

}
