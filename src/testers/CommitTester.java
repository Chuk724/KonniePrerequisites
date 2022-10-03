package testers;
import userClasses.Index;
import userClasses.Commit;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.PrintWriter;
import java.nio.file.Path;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class CommitTester {

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
	void test() throws Exception {
		//First commit with 1 blob
		Index indy = new Index();
		indy.add(fileName);
		File idxFile = new File("./index");
		Path indexFile = Path.of("./index");
		assertTrue(idxFile.exists());
		
		
		Commit c1 = new Commit("first commit test", "Charlie Seymour", null);
		File c1File = new File("./objects/" + c1.generateSha1String());
		assertTrue(c1File.exists());
		
		//Second commit with 2 more blobs
		indy.add("foo.txt");
		indy.add("bar.txt");
		
		Commit c2 = new Commit("second commit test", "Charlie Seymour", c1);
		
	}

}
