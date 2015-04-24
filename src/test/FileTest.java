package test;

import static org.junit.Assert.*;
import org.junit.*;
import a2.File;

public class FileTest {

	private File file1, file2, file3;

	@Before
	public void setUp() {
		file1 = new File("file1", "Yes this is dog");
		file2 = new File("file2");
		file3 = new File();
	}

	@Test
	public void testSetName() {
		file3.setName("file3");
		assertTrue(file3.getName().equals("file3"));
	}

	@Test
	public void testGetName() {
		assertTrue(file2.getName().equals("file2"));
	}

	@Test
	public void testGetContent() {
		assertTrue(file1.getContent().equals("Yes this is dog"));
	}

	@Test
	public void testOverwriteContent() {
		file1.overwriteContent("Freak out");
		assertTrue(file1.getContent().equals("Freak out"));
	}

	@Test
	public void testAddContent() {
		file1.addContent("!");
		assertTrue(file1.getContent().equals("Yes this is dog!"));
	}
}