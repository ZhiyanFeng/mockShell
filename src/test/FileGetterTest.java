package test;

import static org.junit.Assert.*;

import java.nio.file.NoSuchFileException;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import a2.Directory;
import a2.File;
import a2.FileGetter;
import a2.FileSystem;

public class FileGetterTest {
	private Directory root;
	private Directory current;
	private Directory dir1 = null, dir2 = null, dir12 = null, dir13 = null;
	private File f1, f2, file;

	@Before
	public void setUp() throws Exception {
		root = FileSystem.createFileSystemInstance().getRoot();
		dir1 = new Directory(root, "dir1");
		dir2 = new Directory(root, "dir2");
		dir12 = new Directory(dir1, "dir12");
		dir13 = new Directory(dir12, "dir13");
		f1 = new File("f1", "abc");
		f2 = new File("f2", "cat");
		root.getDirectoryListing().add(dir1);
		root.getDirectoryListing().add(dir2);
		root.getFileListing().add(f1);
		dir1.getDirectoryListing().add(dir12);
		dir12.getDirectoryListing().add(dir13);
		dir12.getFileListing().add(f2);
	}

	@Test
	public void testCheckfile() {
		current = root;
		boolean b = FileGetter.checkfile(root, "f1");
		assertEquals(b, true);
	}

	@Test
	public void testGetFile() {
		current = dir12;
		try {
			file = FileGetter.getFile(current, "f2");
		} catch (NoSuchFileException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
			;
		} finally {
			assertEquals(f2, file);
		}

	}

}
