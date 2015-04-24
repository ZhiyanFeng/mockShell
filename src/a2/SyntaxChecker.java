package a2;

import java.util.HashSet;

/**
 * Helper class checks for valid file and directory names for classes which
 * create files or directories. Checks for valid strings as a helper class for
 * echo class
 * 
 * @author Laura
 *
 */
public class SyntaxChecker {

	/**
	 * Checks that the String filename is a valid filename.
	 * 
	 * @param filename
	 * @param current
	 *            directory
	 * @return true if valid filename
	 */
	public static boolean validFileName(String filename, Directory current) {
		int dotCounter = 0;
		boolean validName = true;
		int i = 0;
		while ((i < filename.length()) && validName) {
			if (filename.charAt(i) == '.') {
				dotCounter += 1;
			}
			if (filename.charAt(i) != '.'
					&& !Character.isLetterOrDigit(filename.charAt(i))) {
				validName = false;
			} 
			i += 1;
		}
		if (dotCounter > 1) {
			validName = false;
		}
		HashSet<Directory> allsubdirectories = current.getDirectoryListing();
		if (allsubdirectories.size() > 0) {
			for (Directory dir : allsubdirectories) {
				if (filename.equals(dir.getName())) {
					validName = false;
					break;
				}
			}
		}
		if (!validName) {
			System.out.print("Invalid filename\n");
		}
		return validName;
	}

	/**
	 * Checks that the String directory name is a valid directory name.
	 * 
	 * @param directory
	 *            name
	 * @param current
	 *            directory
	 * @return true if valid directory name
	 */
	public static boolean validDirName(String dirname, Directory current) {
		boolean validName = true;
		int i = 0;
		while ((i < dirname.length()) && validName) {
			if (!Character.isLetterOrDigit(dirname.charAt(i))) {
				validName = false;
			}
			i += 1;
		}
		HashSet<Directory> allsubdirectories = current.getDirectoryListing();
		if (allsubdirectories.size() > 0) {
			for (Directory dir : allsubdirectories) {
				if (dirname.equals(dir.getName())) {
					validName = false;
					break;
				}
			}
		}
		HashSet<File> allFiles = current.getFileListing();
		if (allFiles.size() > 0) {
			for (File f : allFiles) {
				if (dirname.equals(f.getName())) {
					validName = false;
					break;
				}
			}
		}
		if (!validName) {
			System.out.print("Invalid directory name\n");
		}
		return validName;
	}

	/**
	 * checks that the input string is a valid string.
	 * 
	 * @param input
	 *            string
	 * @return true if valid string
	 */
	public static boolean validString(String inputString) {
		boolean validString = true;
		int i = 1;
		while (i < inputString.length() - 2 && validString) {
			if (inputString.charAt(i) == '"') {
				validString = false;
			}
			i += 1;
		} if (inputString.charAt(1) == '"') {
			validString = false;
		}
		if (!validString) {
			System.out.print("Invalid String\n");
		}
		return validString;
	}

	/**
	 * Check for valid redirection syntax in a command. Follows echo format of >
	 * OUTFILE or >> OUTFILE at end of command.
	 * 
	 * @param command
	 * @param current
	 *            directory
	 * @return true if syntax is valid
	 */
	public static boolean validEcho(String[] command, Directory current) {
		boolean validEcho = false;
		if (command[command.length - 2].equals(">")
				|| command[command.length - 2].equals(">>")) {
			validEcho = true;
		}
		return validEcho;
	}
}
