package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import a2.Directory;
import a2.DirectoryChanger;
import a2.FileSystem;
import Exceptions.NoSuchDirectoryException;

public class DirectoryChangerTest {
	private Directory root;
	private Directory current;
	private Directory dir1 = null, dir2 = null, dir12 = null, dir13 = null;
	private Directory test1, test2;

	@Before
	public void setUp() throws Exception {
		root = FileSystem.createFileSystemInstance().getRoot();
		dir1 = new Directory(root, "dir1");
		dir2 = new Directory(root, "dir2");
		dir12 = new Directory(dir1, "dir12");
		dir13 = new Directory(dir12, "dir13");
		root.getDirectoryListing().add(dir1);
		root.getDirectoryListing().add(dir2);
		dir1.getDirectoryListing().add(dir12);
		dir12.getDirectoryListing().add(dir13);
	}

	// @Test
	// public void testChangeDir() {
	// current = root;
	// try {
	// test1 = DirectoryChanger.changeDir(current, "dir1");
	// } catch (NoSuchDirectoryException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }finally{
	// assertTrue(test1.equals(dir1));
	// }
	// }

	@Test
	public void testChangeDir2() {
		current = root;
		try {
			test2 = DirectoryChanger.changeDir(current, "dir1/dir12/dir13");
		} catch (NoSuchDirectoryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			assertTrue(test2.equals(dir13));
		}
	}
}
