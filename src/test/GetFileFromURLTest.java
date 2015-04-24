package test;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.HashSet;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import a2.Directory;
import a2.File;
import a2.FileGetter;
import a2.FileSystem;
import a2.GetFileFromURL;

public class GetFileFromURLTest {

	OutputStream os;
	FileSystem f;
	Directory current;
	HashSet<File> allFiles;

	@Before
	public void setUp() throws Exception {
		f = FileSystem.createFileSystemInstance();
		current = f.getRoot();
		allFiles = current.getFileListing();
	}

	@Test
	public void testFileFromURL() {
		// test that method does not crash with valid URL
		try {
			GetFileFromURL.getter(
					"http://www.cs.cmu.edu/~spok/grimmtmp/073.txt", current);
		} catch (IOException e) {
			System.out.println("Unable to find file at that URL, "
					+ "please try again.");
		} // check that file exists
		assertTrue(FileGetter.checkfile(current, "073.txt"));
	}

	@Test
	public void testFail() {
		// test that method does not crash with invalid URL
		// error message should be printed

		os = new ByteArrayOutputStream();
		System.setOut(new PrintStream(os));
		try {
			GetFileFromURL.getter("http://www.cs.cmu.edu/~spok/grimmtmp/0.txt",
					current);
		} catch (IOException e) {
			System.out.print("Unable to find file at that URL, "
					+ "please try again.\n");
		}
		assertEquals(os.toString(), "Unable to find file at that URL, "
				+ "please try again.\n");
		System.setOut(System.out);
		assertFalse(FileGetter.checkfile(current, "0.txt"));
	}
}
