package test;

import static org.junit.Assert.*;

import java.nio.file.NoSuchFileException;

import org.junit.Before;
import org.junit.Test;

import a2.Catenater;
import a2.Directory;
import a2.File;
import a2.FileSystem;
import Exceptions.NoSuchDirectoryException;

public class CatenaterTest {
	private Directory root;
	private Directory current;
	private Directory dir1 = null, dir2 = null, dir12 = null, dir13 = null;
	private File f1, f2, file;
	private String s;

	@Before
	public void setUp() throws Exception {
		root = FileSystem.createFileSystemInstance().getRoot();
		dir1 = new Directory(root, "dir1");
		dir2 = new Directory(root, "dir2");
		dir12 = new Directory(dir1, "dir12");
		dir13 = new Directory(dir12, "dir13");
		f1 = new File("f1", "dog");
		f2 = new File("f2", "cat");
		root.getDirectoryListing().add(dir1);
		root.getDirectoryListing().add(dir2);
		root.getFileListing().add(f1);
		dir1.getDirectoryListing().add(dir12);
		dir12.getDirectoryListing().add(dir13);
		dir12.getFileListing().add(f2);
	}

	@Test
	public void testPrintContent() {
		current = root;
		try {
			s = Catenater.printContent(current, "f1");
		} catch (NoSuchFileException | NoSuchDirectoryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			assertEquals(s, "dog");
		}
	}

	@Test
	public void testPrintContent2() {
		current = root;
		try {
			s = Catenater.printContent(current, "/dir1/dir12/f2");
		} catch (NoSuchFileException | NoSuchDirectoryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			assertEquals(s, "cat");
		}
	}

}
