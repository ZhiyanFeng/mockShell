package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import a2.*;

public class DirectoryPrinterTest {

	FileSystem f;
	Directory current;

	@Before
	public void setUp() {
		f = FileSystem.createFileSystemInstance();
		current = f.getRoot();
	}

	@Test
	public void testEmptyFileSystemWorkingDirectory() {
		// / is expected since the working directory of a new file system should
		// be the root.
		assertEquals("/", DirectoryPrinter.printWorkingDir(current));
	}

	@Test
	public void testChangeToNewDirectory() {
		current.addDirectory(new Directory(current, "TestDir"));
		// Set the current working directory to be the TestDir directory that
		// just got created
		current = current.getDirectoryListing().toArray(new Directory[0])[0];
		assertEquals("/TestDir/", DirectoryPrinter.printWorkingDir(current));
	}

	@Test
	public void testChangeBackToParentDirectory() {
		current.addDirectory(new Directory(current, "TestDir"));
		// Set the current working directory to be the TestDir directory that
		// just got created
		current = current.getDirectoryListing().toArray(new Directory[0])[0];
		// Now go back to its parent directory (i.e the root directory)
		current = current.getParent();
		assertEquals("/", DirectoryPrinter.printWorkingDir(current));
	}

}
