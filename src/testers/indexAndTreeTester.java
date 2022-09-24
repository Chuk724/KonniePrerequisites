package testers;
import userClasses.Index;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
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
		File testerFile = new File(fileName);
		testerFile.delete();
	}

	@Test
	void test() throws Exception{
		Index idx = new Index();
		File idxFile = new File("./index");
		Path indexFile = Path.of("./index");
		assertTrue(idxFile.exists());
		
		idx.add(fileName);
		System.out.println(idx.blobs.get(fileName));
		assertTrue(Files.readString(indexFile).contains(fileName + " : "+idx.blobs.get(fileName)));
		
	}

}
