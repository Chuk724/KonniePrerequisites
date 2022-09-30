package otherTests;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import userClasses.Tree;

class TreeTest {

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@Test
	void test() throws IOException {
		ArrayList <String> input = new ArrayList();
		input.add("blob : 81e0268c84067377a0a1fdfb5cc996c93f6dcf9f");
		input.add("blob : 01d82591292494afd1602d175e165f94992f6f5f");
		input.add("blob : f1d82236ab908c86ed095023b1d2e6ddf78a6d83");
		input.add("tree : bd1ccec139dead5ee0d8c3a0499b42a7d43ac44b");
		input.add("tree : e7d79898d3342fd15daf6ec36f4cb095b52fd976");
		Tree test = new Tree (input);
		File Obj3 = new File ("dd4840f48a74c1f97437b515101c66834b59b1be.txt");
		assertTrue (Obj3.exists());
		Path filePath = Path.of("dd4840f48a74c1f97437b515101c66834b59b1be.txt");
		assertTrue (Files.readString(filePath).equals("blob : 81e0268c84067377a0a1fdfb5cc996c93f6dcf9f\n"
				+ "blob : 01d82591292494afd1602d175e165f94992f6f5f\n"
				+ "blob : f1d82236ab908c86ed095023b1d2e6ddf78a6d83\n"
				+ "tree : bd1ccec139dead5ee0d8c3a0499b42a7d43ac44b\n"
				+ "tree : e7d79898d3342fd15daf6ec36f4cb095b52fd976"));
		
	}

}
