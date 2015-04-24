package a2;

import java.util.List;

import Exceptions.NoSuchDirectoryException;

/**
 * Command class for the "mkdir" command.
 * 
 * @author Ken Lee
 */
public class DirectoryMaker extends Command {

	// documentation for mkdir DIR command
	private static String documentation = "Enter mkdir DIR in the shell "
			+ "(where DIR represents a directory that may \n be relative "
			+ "to the current directory or a full path) to create the \n"
			+ "directory DIR.";

	/**
	 * Creates one or more Directories in specific paths.
	 * 
	 * Can take in multiple input Strings that represent paths to Directories to
	 * be created. Paths may be absolute or relative to a specific Directory.
	 * Creates the Directory if the path and name is valid.
	 * 
	 * @param start
	 *            the Directory input paths may be relative to
	 * @param input
	 *            the array of Strings of paths to Directories to be created
	 */
	public static void makeDir(Directory start, String[] input) {
		for (String in : input) {
			List<String> parts = DirectoryGetter.splitDir(in);
			if (parts.size() > 1) {
				String name = parts.get(parts.size() - 1);
				parts.remove(parts.size() - 1);
				try {
					Directory target = DirectoryGetter.getDir(start, parts);
					if (SyntaxChecker.validDirName(name, target)) {
						target.addDirectory(new Directory(target, name));
					}
				} catch (NoSuchDirectoryException e) {
					System.out.println(e.getMessage());
					break;
				}
			} else {
				if (SyntaxChecker.validDirName(parts.get(0), start)) {
					start.addDirectory(new Directory(start, parts.get(0)));
				}
			}
		}
	}

	/**
	 * Return the documentation of the mkdir command.
	 * 
	 * @return the String containing the documentation of the command
	 */
	public static String getDocumentation() {
		return documentation;
	}
}