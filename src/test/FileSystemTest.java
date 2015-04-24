package test;

import static org.junit.Assert.*;
import org.junit.*;
import a2.Directory;
import a2.FileSystem;

public class FileSystemTest {

	private FileSystem a, b;
	private Directory d;

	@Before
	public void setUp() {
		a = FileSystem.createFileSystemInstance();
	}

	@Test
	public void testCreateFileSystemInstance() {
		b = FileSystem.createFileSystemInstance();
		assertEquals(a, b);
	}

	@Test
	public void testGetRoot() {
		d = a.getRoot();
		assertEquals(a.getRoot(), d);
	}
}