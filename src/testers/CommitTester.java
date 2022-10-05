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
	private static String fileName2 = "foo.txt";
	private static String fileName3 = "bar.txt";
	private static String fileName4 = "foobar.txt";
	private static String fileName5 = "test2.txt";
	private static String fileName6 = "test3.txt";
	private static String fileName7 = "test4.txt";
	private static String fileName8 = "test5.txt";
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		File newFile = new File(fileName);
		newFile.createNewFile();
		PrintWriter pw = new PrintWriter(newFile);
		pw.write("Test Content.");
		pw.close();
		
		File newFile2 = new File(fileName2);
		newFile2.createNewFile();
		PrintWriter pw2 = new PrintWriter(newFile2);
		pw2.write("This is foo.");
		pw2.close();
		
		File newFile3 = new File(fileName3);
		newFile3.createNewFile();
		PrintWriter pw3 = new PrintWriter(newFile3);
		pw3.write("This is bar.");
		pw3.close();
		
		File newFile4 = new File(fileName4);
		newFile4.createNewFile();
		PrintWriter pw4 = new PrintWriter(newFile4);
		pw4.write("This is foobar.");
		pw4.close();
		
		File newFile5 = new File(fileName5);
		newFile5.createNewFile();
		PrintWriter pw5 = new PrintWriter(newFile5);
		pw5.write("Test2 content.");
		pw5.close();
		
		File newFile6 = new File(fileName6);
		newFile6.createNewFile();
		PrintWriter pw6 = new PrintWriter(newFile6);
		pw6.write("Test3 Content.");
		pw6.close();
		
		File newFile7 = new File(fileName7);
		newFile7.createNewFile();
		PrintWriter pw7 = new PrintWriter(newFile7);
		pw7.write("Test4 Content.");
		pw7.close();
		
		File newFile8 = new File(fileName8);
		newFile8.createNewFile();
		PrintWriter pw8 = new PrintWriter(newFile8);
		pw8.write("Test5 Content.");
		pw8.close();
		
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		File testerFile = new File(fileName);
		testerFile.delete();
	}

	@Test
	void test() throws Exception {
		
		Index indy = new Index();
		File idxFile = new File("./index");
		Path indexFile = Path.of("./index");
		assertTrue(idxFile.exists());
		
		//First commit with 2 blob
		indy.add(fileName);
		indy.add(fileName2);
		Commit c1 = new Commit("first commit test", "Charlie Seymour");
		File c1File = new File("./objects/" + c1.generateSha1String());
		assertTrue(c1File.exists());
		
		//Second commit with 2 more blobs
		indy.add(fileName3);
		indy.add(fileName4);
		Commit c2 = new Commit("second commit test", "Charlie Seymour");
		File c2File = new File("./objects/" + c2.generateSha1String());
		assertTrue(c2File.exists());
		
		//Third commit with 2 more blobs
		indy.add(fileName5);
		indy.add(fileName6);
		Commit c3 = new Commit("third commit test", "Charlie Seymour");
		File c3File = new File("./objects/" + c3.generateSha1String());
		assertTrue(c3File.exists());
		
		//Fourth commit with 2 more blobs
		indy.add(fileName7);
		indy.add(fileName8);
		Commit c4 = new Commit("fourth commit test", "Charlie Seymour");
		File c4File = new File("./objects/" + c4.generateSha1String());
		assertTrue(c4File.exists());
		
	}

}
