package test;

import static org.junit.Assert.*;
import org.junit.*;
import a2.Directory;
import a2.File;

public class DirectoryTest {

	private Directory a, b, c, d;
	private File file1, file2;

	@Before
	public void setUp() {
		a = new Directory("root");
		b = new Directory(a, "b");
		c = new Directory(a, "c");
		d = new Directory("d");
		file1 = new File("stuff");
		file2 = new File("stuff2");
		a.addFile(file1);
	}

	@Test
	public void testGetName() {
		assertTrue(a.getName().equals("root"));
		assertTrue(b.getName().equals("b"));
		assertTrue(c.getName().equals("c"));
	}

	@Test
	public void testGetDirectoryListing() {
		for (Directory dir : a.getDirectoryListing()) {
			assertTrue(dir == b || dir == c);
		}
	}

	@Test
	public void testAddDirectory() {
		a.addDirectory(d);
		assertTrue(a.getDirectoryListing().contains(d));
	}

	@Test
	public void testDeleteDirectory() {
		a.deleteDirectory("b");
		assertFalse(a.getDirectoryListing().contains(b));
	}

	@Test
	public void testGetFileListing() {
		for (File file : a.getFileListing()) {
			assertTrue(file == file1);
		}
	}

	@Test
	public void testAddFile() {
		a.addFile(file2);
		assertTrue(a.getFileListing().contains(file2));
	}

	@Test
	public void testDeleteFile() {
		a.deleteFile("stuff");
		assertFalse(a.getFileListing().contains(file1));
	}

	@Test
	public void testSetParent() {
		d.setParent(a);
		assertEquals(d.getParent(), a);
	}

	@Test
	public void testGetParent() {
		assertEquals(b.getParent(), a);
	}

	@Test
	public void testSetPath() {
		a.addDirectory(d);
		assertTrue(d.getPath().equals("root/d/"));
	}

	@Test
	public void testGetPath() {
		assertTrue(b.getPath().equals("root/b/"));
	}
}
