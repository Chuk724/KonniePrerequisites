package testers;

import userClasses.Index;
import userClasses.Commit;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.PrintWriter;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import userClasses.Commit;
import userClasses.Index;

class HeadTester {

	private static String fileName = "test.txt";
	private static String fileName2 = "test2.txt";
	private static String fileName3 = "test3.txt";
	private static String fileName4 = "test4.txt";
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		//create a test .txt file
		File file = new File(fileName);
		file.createNewFile();
		PrintWriter pw = new PrintWriter(file);
		pw.write("Test Content.");
		pw.close();
		
		//create a test .txt file
		File file2 = new File(fileName2);
		file2.createNewFile();
		PrintWriter pw2 = new PrintWriter(file2);
		pw2.write("Test2 Content.");
		pw2.close();
		
		//create a test .txt file
		File file3 = new File(fileName3);
		file3.createNewFile();
		PrintWriter pw3 = new PrintWriter(file3);
		pw3.write("Test3 Content.");
		pw3.close();	
		
		//create a test .txt file
		File file4 = new File(fileName4);
		file4.createNewFile();
		PrintWriter pw4 = new PrintWriter(file4);
		pw4.write("Test4 Content.");
		pw4.close();
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		//File head = new File("HEAD");
		//head.delete();
	}

	@Test
	void test() throws Exception {
		Index idx = new Index();

		idx.add(fileName);
		Commit c1 = new Commit("first commit test", "Charlie Seymour");
		
		idx.add(fileName2);
		Commit c2 = new Commit("second commit test", "Charlie Seymour");
	}

}
