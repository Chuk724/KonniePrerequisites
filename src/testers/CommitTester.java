package testers;
import userClasses.Index;
import userClasses.Commit;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.PrintWriter;

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
		Index indy = new Index();
		indy.add(fileName);
		Commit c1 = new Commit("first commit test", "Charlie Seymour", null);
	}

}
