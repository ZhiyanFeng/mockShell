package test;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import a2.*;

public class DirectoryStackTest {

	FileSystem f;
	Directory root;
	Directory workingDirectory;
	DirectoryStack dirStack;
	Directory testDir;
	Directory testDir2;

	@Before
	public void setUp() {
		f = FileSystem.createFileSystemInstance();
		root = f.getRoot();
		dirStack = new DirectoryStack();
		testDir = new Directory(root, "testDir");
		testDir2 = new Directory(root, "testDir2");
		root.addDirectory(testDir);
		workingDirectory = root;
	}

	@After
	public void tearDown() {
		// Since each test relies on the filesystem, and a new empty
		// filesystem needs to be created between tests in order for each test
		// to be independent, the file system should be cleared right after a
		// test has finished running.
		root.getDirectoryListing().clear();
		root.getFileListing().clear();
		while (!dirStack.isEmpty())
			dirStack.popDirectory();
	}

	@Test
	public void testPushOntoEmptyStack() {
		dirStack.pushDirectory(testDir);
		assertEquals(testDir, dirStack.popDirectory());
	}

	@Test
	public void testPushOntoNonEmptyStack() {
		dirStack.pushDirectory(testDir2);
		dirStack.pushDirectory(testDir);
		assertEquals(testDir, dirStack.popDirectory());
	}
	
	@Test
	public void testPopStackUntilEmpty() {
	    dirStack.pushDirectory(testDir);
	    dirStack.pushDirectory(testDir2);
	    dirStack.popDirectory();
	    dirStack.popDirectory();
	    assertTrue(dirStack.isEmpty());
	}
	
	@Test
    public void testPopRemovedDir() {
        dirStack.pushDirectory(testDir);
        dirStack.pushDirectory(testDir2);
        dirStack.popDirectory();
        
        root.getDirectoryListing().remove(testDir);
        OutputStream os = new ByteArrayOutputStream();
        System.setOut(new PrintStream(os));
        dirStack.popDirectory();
        String ls = System.lineSeparator();
        assertEquals("The topmost directory in the stack is no longer valid "
                + "(due to mv or rm)" + ls, os.toString());
        System.setOut(System.out);
        assertTrue(dirStack.isEmpty());
    }
}
