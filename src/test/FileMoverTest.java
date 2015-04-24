package test;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import a2.*;

public class FileMoverTest {

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
		// to be independent, the file system should be reset right after a test
		// has finished running.
		root.getDirectoryListing().clear();
		root.getFileListing().clear();
	}

	@Test
	public void testRenamingFile() {
		FileMover.move(root, "testFile.txt", "testFile2.txt");
		assertEquals("testFile2.txt", root.getFileListing()
				.toArray(new File[0])[0].getName());
		assertEquals("...", root.getFileListing()
                .toArray(new File[0])[0].getContent());
		assertTrue(!root.getFileListing().contains(testFile));
	}

	@Test
	public void testMoveFileIntoAnotherDirectory() {
		FileMover.move(root, "testFile.txt", "testDirA");
		assertTrue(testDirA.getFileListing().toArray(new File[0])[0]
				.equals(testFile));
		assertTrue(!root.getFileListing().contains(testFile));
	}

	@Test
	public void testMoveEmptyDirectoryIntoAnotherDirectory() {
		FileMover.move(root, "testDirA", "testDirB");
		Directory copyOfTestDirA = testDirB.getDirectoryListing().toArray(
				new Directory[0])[0];
		assertEquals("testDirA", copyOfTestDirA.getName());
		assertEquals("testDirB", copyOfTestDirA.getParent().getName());
		assertEquals("/testDirB/testDirA/", copyOfTestDirA.getPath());
		// Tests to see if testDirB's testDirA directory is empty or not
		assertTrue(copyOfTestDirA.getDirectoryListing().isEmpty());
		assertTrue(copyOfTestDirA.getFileListing().isEmpty());
		// The root directory should no longer contain testDirA
		assertTrue(!root.getDirectoryListing().contains(testDirA));
	}

	@Test
	public void testMoveNonEmptyDirectoryIntoAnotherDirectory() {
		testDirA.addFile(new File("testFile2", "....."));
		// Move testDirA into testDirB
		FileMover.move(root, "./testDirA", "./testDirB");
		// The new testDirA directory should be the first and only directory
		// that can be found within testDirB's set of directories.
		Directory newTestDirA = testDirB.getDirectoryListing().toArray(
				new Directory[0])[0];
		assertEquals("testDirA", newTestDirA.getName());
		assertEquals("testDirB", newTestDirA.getParent().getName());
		assertEquals("/testDirB/testDirA/", newTestDirA.getPath());
		assertTrue(newTestDirA.getDirectoryListing().isEmpty());
		assertTrue(newTestDirA.getFileListing().toArray(new File[0])[0]
				.equals(new File("testFile2", ".....")));
		assertTrue(!root.getDirectoryListing().contains(testDirA));
	}

	@Test
	public void testMoveNonEmptyDirectoryIntoAnotherDirectory2() {
		testDirA.addFile(new File("testFile2", "....."));
		// Move testDirA into testDirB
		FileMover.move(testDirB, "../testDirA", "./");
		// The new testDirA directory should be the first and only directory
		// that can be found within testDirB's set of directories.
		Directory newTestDirA = testDirB.getDirectoryListing().toArray(
				new Directory[0])[0];
		assertEquals("testDirA", newTestDirA.getName());
		assertEquals("testDirB", newTestDirA.getParent().getName());
		assertEquals("/testDirB/testDirA/", newTestDirA.getPath());
		assertTrue(newTestDirA.getDirectoryListing().isEmpty());
		assertTrue(newTestDirA.getFileListing().toArray(new File[0])[0]
				.equals(new File("testFile2", ".....")));
		assertTrue(!root.getDirectoryListing().contains(testDirA));
	}

	@Test
	public void testMoveNonEmptyDirectoryIntoAnotherDirectory3() {
		testDirA.addFile(new File("testFile2", "....."));
		// Move testDirA into testDirB
		FileMover.move(testDirA, "./", "../testDirB");
		// The new testDirA directory should be the first and only directory
		// that can be found within testDirB's set of directories.
		Directory newTestDirA = testDirB.getDirectoryListing().toArray(
				new Directory[0])[0];
		assertEquals("testDirA", newTestDirA.getName());
		assertEquals("testDirB", newTestDirA.getParent().getName());
		assertEquals("/testDirB/testDirA/", newTestDirA.getPath());
		assertTrue(newTestDirA.getDirectoryListing().isEmpty());
		assertTrue(newTestDirA.getFileListing().toArray(new File[0])[0]
				.equals(new File("testFile2", ".....")));
		assertTrue(!root.getDirectoryListing().contains(testDirA));
	}

	@Test
	public void testMoveDirectoryWithSubDirectoryIntoAnotherDirectory() {
		testDirA.addFile(new File("testFile2", "....."));
		testDirA.addDirectory(new Directory(testDirA, "testDirC"));
		FileMover.move(root, "testDirA", "testDirB");
		// The new testDirA directory should be the first and only directory
		// that can be found within testDirB's set of directories.
		Directory newTestDirA = testDirB.getDirectoryListing().toArray(
				new Directory[0])[0];
		assertEquals("testDirA", newTestDirA.getName());
		assertEquals("testDirB", newTestDirA.getParent().getName());
		assertEquals("/testDirB/testDirA/", newTestDirA.getPath());
		assertEquals("testDirC",
				newTestDirA.getDirectoryListing().toArray(new Directory[0])[0]
						.getName());
		assertTrue(newTestDirA.getFileListing().toArray(new File[0])[0]
				.equals(new File("testFile2", ".....")));
		assertTrue(!root.getDirectoryListing().contains(testDirA));
	}

	@Test
	public void testMoveDirectoryIntoFile() {
		// The output stream will have to be changed, before any messages
		// given by FileCopier can be recorded
		OutputStream os = new ByteArrayOutputStream();
		System.setOut(new PrintStream(os));
		FileCopier.copy(root, "testDirA", "randomFile.txt");
		String ls = System.lineSeparator();
		assertEquals("Invalid command, please try again" + ls, os.toString());
		System.setOut(System.out);
	}

	@Test
	public void testMoveNonExistantDirectoryIntoAnotherDirectory() {
		// The output stream will have to be changed, before any messages
		// given by FileCopier can be recorded
		OutputStream os = new ByteArrayOutputStream();
		System.setOut(new PrintStream(os));
		FileMover.move(root, "testDirABC", "testDirA");
		String ls = System.lineSeparator();
		assertEquals("testDirABC does not exist." + ls, os.toString());
		System.setOut(System.out);
	}

	public void testMoveNonExistantFileIntoADirectory() {
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
