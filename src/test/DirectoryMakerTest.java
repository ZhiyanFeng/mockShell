package test;

import static org.junit.Assert.*;

import org.junit.*;

import Exceptions.NoSuchDirectoryException;
import a2.Directory;
import a2.DirectoryGetter;
import a2.DirectoryLister;
import a2.DirectoryMaker;
import a2.FileSystem;

public class DirectoryMakerTest {

	private Directory a;
	private FileSystem fs;

	@Before
	public void setUp() {
		fs = FileSystem.createFileSystemInstance();
		a = new Directory("a");
		fs.getRoot().addDirectory(a);
	}

	@After
	public void cleanUp() {
		fs.getRoot().getDirectoryListing().clear();
	}

	@Test
	public void testMakeDir() throws NoSuchDirectoryException {
		String[] input = { "b", "/a/b/d", "a/b/c", "/a/b/..", "^%" };
		DirectoryMaker.makeDir(a, input);
		assertTrue(DirectoryGetter.getDir(a, "/a/b").getName().equals("b"));
		assertTrue(DirectoryGetter.getDir(a, "/a/b/d").getName().equals("d"));
		String bList = "";
		for (Directory dir : DirectoryGetter.getDir(a, "/a/b")
				.getDirectoryListing()) {
			bList = bList + dir.getName();
		}
		assertFalse(bList.contains("c"));
		assertFalse(bList.contains(".."));
		assertFalse(bList.contains("^%"));
	}

	@Test
	public void testGetDocumentation() {
		String documentation = "Enter mkdir DIR in the shell "
				+ "(where DIR represents a directory that may \n be relative "
				+ "to the current directory or a full path) to create the \n"
				+ "directory DIR.";
		assertTrue(DirectoryMaker.getDocumentation().equals(documentation));
	}
}
