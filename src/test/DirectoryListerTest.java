package test;

import static org.junit.Assert.*;

import org.junit.*;

import a2.Directory;
import a2.DirectoryLister;
import a2.File;
import a2.FileSystem;

public class DirectoryListerTest {

	private Directory a, b, c, d, e;
	private File f;
	private FileSystem fs;

	@Before
	public void setUp() {
		fs = FileSystem.createFileSystemInstance();
		a = new Directory("a");
		b = new Directory("b");
		c = new Directory("c");
		d = new Directory("d");
		e = new Directory("e");
		fs.getRoot().addDirectory(a);
		a.addDirectory(b);
		a.addDirectory(c);
		b.addDirectory(d);
		c.addDirectory(e);
		f = new File("1");
		b.addFile(f);
	}

	@After
	public void cleanUp() {
		fs.getRoot().getDirectoryListing().clear();
	}

	@Test
	public void testListDir_noString() {
		String actual = DirectoryLister.listDir(a, false);
		String expected = "a:\nb\nc\n";
		String expected2 = "a:\nc\nb\n";
		assertTrue(actual.equals(expected) || actual.equals(expected2));
		actual = DirectoryLister.listDir(b, false);
		expected = "b:\nd\n1\n";
		assertTrue(actual.equals(expected));
	}

	@Test
	public void testListDir_String() {
		String[] input = { ".." };
		String actual = DirectoryLister.listDir(a, input, false);
		String expected = ":\na\n\n";
		assertTrue(actual.equals(expected));
		actual = DirectoryLister.listDir(b, input, false);
		expected = "a:\nb\nc\n\n";
		String expected2 = "a:\nc\nb\n\n";
		assertTrue(actual.equals(expected) || actual.equals(expected2));
		String[] input2 = { "a/b", "/a/b", "b/d", "../../../" };
		actual = DirectoryLister.listDir(a, input2, false);
		expected = "There is no such Directory: "
				+ "a/b\nb:\nd\n1\n\nd:\n\n:\na\n\n";
		assertTrue(actual.equals(expected));
	}
	
	@Test
	public void testListDir_Recursive() {
		a.deleteDirectory("c");
		String actual = DirectoryLister.listDir(a, true);
		String expected = "a:\nb\n\nb:\nd\n1\n\nd:\n";
		assertTrue(actual.equals(expected));
		String[] input = {"b/1"};
		String actual2 = DirectoryLister.listDir(a, input , true);
		String expected2 = "1\n";
		assertTrue(actual2.equals(expected2));
	}

	@Test
	public void testGetDocumentation() {
		String documentation = "Enter ls to display the contents"
				+ " (files and/or directories) of the \n current directory"
				+ " in the shell."
				+ "\n Enter ls PATH (where PATH represents a path or a list of"
				+ " paths) to \n print the contents of the specified path(s)."
				+ "\n Enter ls -R or ls -R PATH to recursively list all "
				+ "subdirectories."
				+ "\n Add > OUTFILE or >> OUTFILE to capture output to outfile.";
		assertTrue(DirectoryLister.getDocumentation().equals(documentation));
	}
}
