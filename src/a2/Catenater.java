package a2;

import java.nio.file.NoSuchFileException;
import java.util.*;

import Exceptions.NoSuchDirectoryException;

/**
 * implement the class Cat
 * 
 * @author Zhiyan Feng
 *
 */
public class Catenater extends Command {
	private static String documentation = "Enter cat FILE (where FILE "
			+ "represents the name of a file) in the shell to \n display "
			+ "the contents of a specified file in the shell."
			+ "\n Add > OUTFILE or >> OUTFILE to capture output to outfile.";

	public Catenater() {
	}

	/**
	 * print out the content of the file if it exists, other throw
	 * NoSuchDirectoryException, NoSuchFileException
	 * 
	 * @param CurrentDir
	 * @param name
	 *            is the file name
	 * @throws NoSuchDirectoryException
	 * @throws NoSuchFileException
	 */

	public static String printContent(Directory currentDir, String name)
			throws NoSuchDirectoryException, NoSuchFileException {
		List<String> L = new ArrayList<String>();
		L = DirectoryGetter.splitDir(name);
		int size = L.size();
		String file = L.get(size - 1);
		if (size > 1) {
			currentDir = DirectoryGetter.getDir(currentDir,
					L.subList(0, size - 1));
		}
		return FileGetter.getFile(currentDir, file).getContent();
	}

	/**
	 * get the documentation of the method
	 */
	public static String getDocumentation() {
		return documentation;
	}

}
