package testers;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.PrintWriter;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import userClasses.Blob;

class BlobTester {
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		File newFile = new File("test.txt");
		newFile.createNewFile();
		PrintWriter pw = new PrintWriter(newFile);
		pw.write("Test Content.");
		pw.close();
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		File testerFile = new File("test.txt");
		testerFile.delete();
	}

	@Test
	void testBlobCreate() throws Exception{
		Blob b = new Blob("test.txt");
		File f = new File("./objects/" + b.getSha1());
		assertTrue(f.exists());
	}

}
