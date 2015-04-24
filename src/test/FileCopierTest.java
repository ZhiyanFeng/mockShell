package test;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import org.junit.Before;
import org.junit.After;
import org.junit.Test;

import a2.*;

public class FileCopierTest {

	FileSystem f;
	Directory root;
	Directory testDirA;
	Directory testDirB;
	File testFile;

	@Before
	public void setUp() {
		// Setup the file system first
		f = FileSystem.createFileSystemInstance();
		root = f.getRoot();

		testDirA = new Directory(root, "testDirA");
		root.addDirectory(testDirA);
		testDirB = new Directory(root, "testDirB");
		root.addDirectory(testDirB);
		testFile = new File("testFile.txt", "...");
		root.addFile(testFile);
	}

	@After
	public void tearDown() {
		// Since each test relies on the filesystem, and a new empty
		// filesystem needs to be created between tests in order for each test
		// to be independent, the file system should be cleared right after a
		// test has finished running.
		root.getDirectoryListing().clear();
		root.getFileListing().clear();
	}

	@Test
	public void testMakeCopyOfFileInCurrentDirectory() {
		FileCopier.copy(root, "testFile.txt", "testFile2.txt");
		assertTrue(root.getFileListing().contains(testFile));
	}

	@Test
	public void testCopyFileIntoAnotherDirectory() {
		FileCopier.copy(root, "testFile.txt", "testDirA");
		assertEquals(testFile.getName(),
				testDirA.getFileListing().toArray(new File[0])[0].getName());
		assertEquals(testFile.getContent(),
                testDirA.getFileListing().toArray(new File[0])[0].getContent());
	}

	@Test
	public void testCopyEmptyDirectoryIntoAnotherDirectory() {
		FileCopier.copy(root, "testDirA", "testDirB");
		Directory copyOfTestDirA = testDirB.getDirectoryListing().toArray(
				new Directory[0])[0];
		assertEquals("testDirA", copyOfTestDirA.getName());
		assertEquals("testDirB", copyOfTestDirA.getParent().getName());
		assertEquals("/testDirB/testDirA/", copyOfTestDirA.getPath());
		assertTrue(copyOfTestDirA.getDirectoryListing().isEmpty());
		assertTrue(copyOfTestDirA.getFileListing().isEmpty());
	}

	@Test
	public void testCopyNonEmptyDirectoryIntoAnotherDirectory() {
		testDirA.addFile(new File("testFile2", "....."));
		// Make a copy of testDirA in testDirB
		FileCopier.copy(root, "./testDirA", "./testDirB");
		// The new testDirA directory should be the first and only directory
		// that can be found within testDirB's set of directories.
		Directory copyOfTestDirA = testDirB.getDirectoryListing().toArray(
				new Directory[0])[0];
		assertEquals("testDirA", copyOfTestDirA.getName());
		assertEquals("testDirB", copyOfTestDirA.getParent().getName());
		assertEquals("/testDirB/testDirA/", copyOfTestDirA.getPath());
		assertTrue(copyOfTestDirA.getDirectoryListing().isEmpty());
		assertTrue(copyOfTestDirA.getFileListing().toArray(new File[0])[0]
				.equals(new File("testFile2", ".....")));
	}

	@Test
	public void testCopyNonEmptyDirectoryIntoAnotherDirectory2() {
		testDirA.addFile(new File("testFile2", "....."));
		// Make a copy of testDirA in testDirB
		FileCopier.copy(testDirB, "../testDirA", "./");
		// The new testDirA directory should be the first and only directory
		// that can be found within testDirB's set of directories.
		Directory copyOfTestDirA = testDirB.getDirectoryListing().toArray(
				new Directory[0])[0];
		assertEquals("testDirA", copyOfTestDirA.getName());
		assertEquals("testDirB", copyOfTestDirA.getParent().getName());
		assertEquals("/testDirB/testDirA/", copyOfTestDirA.getPath());
		assertTrue(copyOfTestDirA.getDirectoryListing().isEmpty());
		assertTrue(copyOfTestDirA.getFileListing().toArray(new File[0])[0]
				.equals(new File("testFile2", ".....")));
	}

	@Test
	public void testCopyNonEmptyDirectoryIntoAnotherDirectory3() {
		testDirA.addFile(new File("testFile2", "....."));
		// Make a copy of testDirA in testDirB
		FileCopier.copy(testDirA, "./", "../testDirB");
		// The new testDirA directory should be the first and only directory
		// that can be found within testDirB's set of directories.
		Directory copyOfTestDirA = testDirB.getDirectoryListing().toArray(
				new Directory[0])[0];
		assertEquals("testDirA", copyOfTestDirA.getName());
		assertEquals("testDirB", copyOfTestDirA.getParent().getName());
		assertEquals("/testDirB/testDirA/", copyOfTestDirA.getPath());
		assertTrue(copyOfTestDirA.getDirectoryListing().isEmpty());
		assertTrue(copyOfTestDirA.getFileListing().toArray(new File[0])[0]
				.equals(new File("testFile2", ".....")));
	}

	@Test
	public void testCopyDirectoryWithSubDirectoryIntoAnotherDirectory() {
		testDirA.addFile(new File("testFile2", "....."));
		testDirA.addDirectory(new Directory(testDirA, "testDirC"));
		FileCopier.copy(root, "testDirA", "testDirB");
		// The new testDirA directory should be the first and only directory
		// that can be found within testDirB's set of directories.
		Directory copyOfTestDirA = testDirB.getDirectoryListing().toArray(
				new Directory[0])[0];
		assertEquals("testDirA", copyOfTestDirA.getName());
		assertEquals("testDirB", copyOfTestDirA.getParent().getName());
		assertEquals("/testDirB/testDirA/", copyOfTestDirA.getPath());
		assertEquals(
				"testDirC",
				copyOfTestDirA.getDirectoryListing().
				toArray(new Directory[0])[0].getName());
		assertTrue(copyOfTestDirA.getFileListing().toArray(new File[0])[0]
				.equals(new File("testFile2", ".....")));
	}

	@Test
	public void testCopyDirectoryIntoFile() {
		// The output stream will have to be changed, before any messages
		// given by FileCopier can be recorded
		OutputStream os = new ByteArrayOutputStream();
		System.setOut(new PrintStream(os));
		FileCopier.copy(root, "testDirA", "randomFile.txt");
		// Different line separators are used by java for different operating
		// systems, so line separator being used by the current OS is
		// retrieved here before any assertions are performed.
		String ls = System.lineSeparator();
		assertEquals("Invalid command, please try again" + ls, os.toString());
		System.setOut(System.out);
	}

	@Test
	public void testCopyNonExistantDirectoryIntoAnotherDirectory() {
		// The output stream will have to be changed, before any messages
		// given by FileCopier can be recorded
		OutputStream os = new ByteArrayOutputStream();
		System.setOut(new PrintStream(os));
		FileCopier.copy(root, "testDirABC", "testDirA");
		String ls = System.lineSeparator();
		assertEquals("testDirABC does not exist." + ls, os.toString());
		System.setOut(System.out);
	}

	@Test
	public void testCopyNonExistantFileIntoADirectory() {
		// The output stream will have to be changed, before any messages
		// given by FileCopier can be recorded
		OutputStream os = new ByteArrayOutputStream();
		System.setOut(new PrintStream(os));
		FileCopier.copy(root, "testDirABC.txt", "testDirA");
		String ls = System.lineSeparator();
		assertEquals("testDirABC.txt does not exist." + ls, os.toString());
		System.setOut(System.out);
	}
}
