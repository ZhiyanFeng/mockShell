package test;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.file.NoSuchFileException;
import java.util.HashSet;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import Exceptions.NoSuchDirectoryException;
import a2.*;

public class EchoTest {

	OutputStream os;
	FileSystem f;
	Directory current;
	Directory dir1, dir2, dir3;
	File f1, f2, f3, f4;
	HashSet<File> allFiles, allFiles2, allFiles3;
	public String[] s1, s2, s3, s4, s5, s6, s7, s8, s9, s10, s11, s12, s13, s14;

	@Before
	public void setUp() {

		os = new ByteArrayOutputStream();
		System.setOut(new PrintStream(os));

		f = FileSystem.createFileSystemInstance();
		current = f.getRoot();
		dir1 = new Directory(current, "dir1");
		dir2 = new Directory(dir1, "dir2");
		dir3 = new Directory(dir2, "dir3");
		f1 = new File();
		f2 = new File();
		f3 = new File();
		f4 = new File();
		current.getDirectoryListing().add(dir1);
		dir1.getDirectoryListing().add(dir2);
		dir2.getDirectoryListing().add(dir3);
		allFiles = current.getFileListing();
		allFiles2 = dir1.getFileListing();
		allFiles3 = dir3.getFileListing();

		s1 = "echo \"abc def\" ".trim().split(" +");
		s2 = "echo abc".trim().split(" +");
		s3 = "echo \"abc\" > file1".trim().split(" +");
		s4 = "echo \"def\" > file1".trim().split(" +");
		s5 = "echo \"ghi\" >> file1".trim().split(" +");
		s6 = "echo \"123\" >> file2".trim().split(" +");
		s7 = "echo \"abc\" >> file2".trim().split(" +");
		s8 = "echo \"new string\" > file2".trim().split(" +");
		s9 = "echo \"abc\" > /dir1/file3".trim().split(" +");
		s10 = "echo \"abc\" > /dir1/dir2/dir3/file4".trim().split(" +");
		s11 = "echo \"def\" >> /dir1/dir2/dir3/file4".trim().split(" +");
		s12 = "echo \"ab\"c\"".trim().split(" +");
		s13 = "echo \"abc\" > file&1".trim().split(" +");
		s14 = "echo \"\"".trim().split(" +");
	}

	@Test
	public void testValidString() throws NoSuchDirectoryException,
			NoSuchFileException {
		Echo.echo(current, s1);
		// should print line "abc def"
		assertEquals("abc def\n", os.toString());
	}

	@Test
	public void testInvalidString1() throws NoSuchDirectoryException,
			NoSuchFileException {
		Echo.echo(current, s14);
		// should print line "Invalid String" 
		
		assertEquals("Invalid String\n", os.toString());
	}
	
	@Test
	public void testInvalidString3() throws NoSuchDirectoryException,
			NoSuchFileException {
		Echo.echo(current, s12);
		// should print line "Invalid String" 
		
		assertEquals("Invalid String\n", os.toString());
	}

	@Test
	public void testInvalidFileName() throws NoSuchDirectoryException,
			NoSuchFileException {
		Echo.echo(current, s13);
		// should print line "Invalid filename"
		assertEquals("Invalid filename\nInvalid command, please try again\n",
				os.toString());
	}

	@Test
	public void testCreateFileinRootDirectory()
			throws NoSuchDirectoryException, NoSuchFileException {
		// uses > to create file in current directory
		Echo.echo(current, s3);
		f1.addContent("abc");
		for (File file : allFiles) {
			if (file.getName().equals("file1")) {
				assertEquals(f1.getContent(), ((file.getContent())));
			}
		}
	}

	@Test
	public void testOverwriteFileinRootDirectory()
			throws NoSuchDirectoryException, NoSuchFileException {
		// overwrite file in current directory
		Echo.echo(current, s3);
		Echo.echo(current, s4);
		f1.addContent("abc");
		f1.overwriteContent("def");
		for (File file : allFiles) {
			if (file.getName().equals("file1")) {
				assertEquals(f1.getContent(), file.getContent());
			}
		}
	}

	@Test
	public void testAppendToFileinRootDirectory()
			throws NoSuchDirectoryException, NoSuchFileException {
		// append to file in current directory
		Echo.echo(current, s3);
		Echo.echo(current, s4);
		Echo.echo(current, s5);
		f1.addContent("abc");
		f1.overwriteContent("def");
		f1.addContent("\nghi");
		for (File file : allFiles) {
			if (file.getName().equals("file1")) {
				assertEquals(f1.getContent(), file.getContent());
			}
		}
	}

	@Test
	public void testCreateAndAppendFile2inRootDirectory()
			throws NoSuchDirectoryException, NoSuchFileException {
		// uses >> to create file in current directory
		Echo.echo(current, s6);
		Echo.echo(current, s7);
		f2.addContent("123\n");
		f2.addContent("abc");
		for (File file : allFiles) {
			if (file.getName().equals("file2")) {
				assertEquals(f2.getContent(), file.getContent());
			}
		}
	}

	@Test
	public void testOverwriteFile2inRootDirectory()
			throws NoSuchDirectoryException, NoSuchFileException {
		// overwrite second file in current directory with string with spaces
		Echo.echo(current, s6);
		Echo.echo(current, s7);
		Echo.echo(current, s8);
		f2.addContent("123");
		f2.addContent("abc");
		f2.overwriteContent("new string");
		for (File file : allFiles) {
			if (file.getName().equals("file2")) {
				assertEquals(f2.getContent(), file.getContent());
			}
		}
	}

	@Test
	public void testCreateFileInDir1() throws NoSuchDirectoryException,
			NoSuchFileException {
		// check that echo works with a path
		Directory dir1 = new Directory(current, "dir1");
		HashSet<File> allFiles2 = dir1.getFileListing();
		f3.addContent("\"abc\" ");
		Echo.echo(current, s9);
		for (File file : allFiles2) {
			if (file.getName().equals("file3")) {
				assertEquals(f3.getContent(), file.getContent());
			}
		}
	}

	@Test
	public void testCreateAndAppendFileInDir3()
			throws NoSuchDirectoryException, NoSuchFileException {
		// check that echo works with a path
		f4.addContent("\"abc\" ");
		f4.addContent("\"def\" ");
		Echo.echo(current, s10);
		Echo.echo(current, s11);
		for (File file : allFiles3) {
			if (file.getName().equals("file4")) {
				assertEquals(f4.getContent(), file.getContent());
			}
		}
	}

	@Test
	public void testCreateAndAppendFileInDir2()
			throws NoSuchDirectoryException, NoSuchFileException {
		// check that echo works from dir2 (a directory other than
		// root directory)
		f4.addContent("\"abc\" ");
		f4.addContent("\"def\" ");
		Echo.echo(dir2, s3);
		Echo.echo(dir2, s4);
		for (File file : allFiles3) {
			if (file.getName().equals("file1")) {
				assertEquals(f4.getContent(), file.getContent());
			}
		}
	}

	@AfterClass
	public static void tearDown() {

		System.setOut(System.out);
	}
}
