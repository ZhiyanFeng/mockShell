package test;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import a2.Directory;
import a2.DirectoryMaker;
import a2.File;
import a2.FileSystem;
import a2.SyntaxChecker;

public class SyntaxCheckerTest {

	OutputStream os;
	FileSystem f;
	Directory current;
	Directory dir2;
	File file3;
	String[] s1, s2, s3, s4, s5, s6, s7;

	@Before
	public void setUp() {

		os = new ByteArrayOutputStream();
		f = FileSystem.createFileSystemInstance();
		System.setOut(new PrintStream(os));
		current = f.getRoot();
		dir2 = new Directory(current, "dir2");
		file3 = new File("file3");
		current.getDirectoryListing().add(dir2);
		current.getFileListing().add(file3);
	}

	@Test
	public void testValidFileName() {

		assertTrue(SyntaxChecker.validFileName("filename", current));
		assertTrue(SyntaxChecker.validFileName("file1", current));
		assertTrue(SyntaxChecker.validFileName("filename.txt", current));
		assertFalse(SyntaxChecker.validFileName("file_name", current));
		// an invalid filename prints a message to the console
		assertEquals("Invalid filename\n", os.toString());
		assertFalse(SyntaxChecker.validFileName("file&name", current));
		assertFalse(SyntaxChecker.validFileName("filename.txt.pdf", current));
		// directory and file in the same parent directory cannot have the same
		// name
		assertFalse(SyntaxChecker.validFileName("dir2", current));

	}

	@Test
	public void testValidDirName() {

		assertTrue(SyntaxChecker.validDirName("dira", current));
		assertTrue(SyntaxChecker.validDirName("dir1", current));
		assertFalse(SyntaxChecker.validDirName("dir/a", current));
		// an invalid directory name prints a message to the console
		assertEquals("Invalid directory name\n", os.toString());
		// directory and file in the same parent directory cannot have the same
		// name
		assertFalse(SyntaxChecker.validDirName("file3", current));
		// two directories in the same directory cannot have the same name
		assertFalse(SyntaxChecker.validDirName("dir2", current));

	}

	@Test
	public void testValidString() {

		assertTrue(SyntaxChecker.validString("abc"));
		assertTrue(SyntaxChecker.validString("abc def ghi"));
		assertTrue(SyntaxChecker.validString("abc 123 &%*"));
		assertFalse(SyntaxChecker.validString("abc \"ghi"));
		// an invalid string prints a message to the console
		assertEquals("Invalid String\n", os.toString());
	}

	@Test
	public void testValidEcho() {
		s1 = ("echo \"abc\" > file1").split(" +");
		s2 = ("echo \"abc\" >> file1").split(" +");
		s3 = ("cat file1 > file2").split(" +");
		s4 = ("pwd >> file3").split(" +");
		s5 = ("pwd a file3").split(" +");
		s6 = ("echo \"abc\" >> file1 file2").split(" +");

		assertTrue(SyntaxChecker.validEcho(s1, current));
		assertTrue(SyntaxChecker.validEcho(s2, current));
		assertTrue(SyntaxChecker.validEcho(s3, current));
		assertTrue(SyntaxChecker.validEcho(s4, current));
		assertFalse(SyntaxChecker.validEcho(s5, current));
		assertFalse(SyntaxChecker.validEcho(s6, current));
	}

	@AfterClass
	public static void tearDown() {

		System.setOut(System.out);
	}
}
