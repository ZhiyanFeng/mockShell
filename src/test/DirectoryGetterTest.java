/**
 * 
 */
package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import Exceptions.NoSuchDirectoryException;
import a2.Directory;
import a2.DirectoryGetter;
import a2.FileSystem;

import java.util.*;

/**
 * @author zhiyan
 *
 */
public class DirectoryGetterTest {
	private Directory root;
	private Directory dir1 = null, dir2 = null, dir12 = null, dir13 = null;
	private List<String> L, L2, L3, L4, L5, L6, L7;

	/**
	 * @throws java.lang.Exception
	 */
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
		String test = dir13.getPath();
		String test2 = "../dir12/dir13/";
		String test3 = "./dir13/";
		String test4 = "../../dir2";
		String test5 = "/dir2";
		String test6 = "/";
		String test7 = "../../../";
		L = DirectoryGetter.splitDir(test);
		L2 = DirectoryGetter.splitDir(test2);
		L3 = DirectoryGetter.splitDir(test3);
		L4 = DirectoryGetter.splitDir(test4);
		L5 = DirectoryGetter.splitDir(test5);
		L6 = DirectoryGetter.splitDir(test6);
		L7 = DirectoryGetter.splitDir(test7);

	}

	@Test
	public void testSplitDir() {
		List<String> path = new ArrayList<String>();
		path.add("/");
		path.add("dir1");
		path.add("dir12");
		path.add("dir13");
		assertTrue(path.equals(L));
	}

	@Test
	public void testSplitDir2() {
		List<String> path = new ArrayList<String>();
		path.add("/");
		assertTrue(path.equals(L6));
	}

	@Test
	public void testGetDir() {
		Directory testDir;
		try {
			testDir = DirectoryGetter.getDir(root, L);
			assertEquals(testDir, dir13);
		} catch (NoSuchDirectoryException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
	}

	@Test
	public void testGetDir2() {
		Directory testDir;
		try {
			testDir = DirectoryGetter.getDir(dir12, L2);
			assertEquals(testDir, dir13);
		} catch (NoSuchDirectoryException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
	}

	@Test
	public void testGetDir3() {
		Directory testDir;
		try {
			testDir = DirectoryGetter.getDir(dir12, L3);
			assertEquals(testDir, dir13);
		} catch (NoSuchDirectoryException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
	}

	@Test
	public void testGetDir4() {
		Directory testDir;
		try {
			testDir = DirectoryGetter.getDir(dir12, L4);
			assertEquals(testDir, dir2);
		} catch (NoSuchDirectoryException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
	}

	@Test
	public void testGetDir5() {
		Directory testDir;
		try {
			testDir = DirectoryGetter.getDir(dir12, L5);
			assertEquals(testDir, dir2);
		} catch (NoSuchDirectoryException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
	}

	@Test
	public void testGetDir6() {
		Directory testDir;
		try {
			testDir = DirectoryGetter.getDir(dir1, L7);
			assertEquals(testDir, root);
		} catch (NoSuchDirectoryException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
	}
}
