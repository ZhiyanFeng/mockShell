/**
 * 
 */
package test;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Scanner;

import org.junit.Before;
import org.junit.Test;

import a2.Directory;
import a2.DirectoryGetter;
import a2.DirectoryStack;
import a2.File;
import a2.FileSystem;
import a2.Remover;

/**
 * @author yan
 *
 */
public class RemoverTest {
	private Directory root;
	private Directory dir1, dir2, dir12, dir131;
	private Directory dir132;
	private File f1,f2,f3,f4;
	private Scanner console = new Scanner(System.in);

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		root = FileSystem.createFileSystemInstance().getRoot();

		dir1 = new Directory(root, "dir1");
		dir2 = new Directory(root, "dir2");
		dir12 = new Directory(dir1, "dir12");
		f1 = new File("f1");
		f2 = new File("f2");
		f3 = new File("f3");
		f4 = new File("f4");
		dir1.getFileListing().add(f1);
		dir131 = new Directory(dir12, "dir131");
		dir132 = new Directory(dir12,"dir132");
		dir131.addFile(f2);
		dir131.addFile(f3);
		dir131.addFile(f4);
		root.getDirectoryListing().add(dir1);
		root.getDirectoryListing().add(dir2);
		dir1.getDirectoryListing().add(dir12);
		dir12.getDirectoryListing().add(dir131);
		dir12.getDirectoryListing().add(dir132);

	}

	@Test
	public void testRm() {
		Remover.rm(root, "/dir1/dir12", console);
		assertFalse(dir1.getDirectoryListing().contains(dir12));
	}
	
	@Test
	public void testRm2() {
		Remover.rm(root, "/dir2", console);
		assertFalse(root.getDirectoryListing().contains(dir2));
	}


}
