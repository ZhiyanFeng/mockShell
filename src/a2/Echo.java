package a2;

import java.nio.file.NoSuchFileException;
import java.util.*;

import Exceptions.NoSuchDirectoryException;

/**
 * Class to handle the JShell echo command
 * 
 * @author Laura
 *
 */
public class Echo extends Command {
	/**
	 * string documentation for echo command.
	 */
	private static String documentation = "Enter echo STRING in the shell "
			+ "(where STRING represents a string of \n characters surrounded "
			+ "by double quotation marks) to print STRING in the \n shell. "
			+ "\n Enter echo STRING > OUTFILE (where OUTFILE represents "
			+ "the name of a \n file) to add STRING to a new file (OUTFILE) "
			+ "OR to add STRING to an \n existing file (OUTFILE) and "
			+ "simultaneously overwrite the file's previous \n contents into "
			+ "the shell to display the contents of a specified \n file in the"
			+ " shell. "
			+ "\n Enter STRING >> OUTFILE to append STRING to an existing"
			+ " file instead \n of overwriting.";

	/**
	 * Method for echo command. Print an input string to the console if 
	 * output file is not provided. If output file is provided, call
	 * on AppendorOverwrite method.
	 * 
	 * @param currentDirectory
	 * @param command
	 * @throws NoSuchDirectoryException
	 * @throws NoSuchFileException
	 */
	public static void echo(Directory currentDirectory, String[] command)
			throws NoSuchDirectoryException, NoSuchFileException {

		String newString = stringCreater(command);
		if (command[command.length - 2].equals(">")
				|| command[command.length - 2].equals(">>")) {
			if ((command[1].charAt(0) == '"')
					&& (command[command.length - 3].charAt(
							command[command.length - 3].length() - 1) == '"')
							&& newString != null) {
				AppendorOverwrite(currentDirectory, newString, command);
			} else {
				System.out.println("Invalid comamnd, please try again");
			}
		} else if (newString != null){
			System.out.print(newString + "\n");
		}
	}

	/**
	 * Depending on the input, check to see whether a file exists, if it does,
	 * append a string to contents or overwrite contents (depending on > or >>),
	 * if it doesn't create new file and add string to that file. 
	 * 
	 * @throws NoSuchDirectoryException
	 * @param currentDirectory
	 *            - the current directory of JShell
	 * @param string
	 *            - the input string
	 * @param path
	 *            - the filename or file path
	 */
	public static void AppendorOverwrite(Directory currentDirectory,
			String string, String[] command) throws NoSuchDirectoryException,
			NoSuchFileException {

		// check that filename doesn't end with /
		if (command[command.length - 1].charAt(command[command.length - 1]
				.length() - 1) != '/') {

			String path = command[command.length - 1].toString();
			List<String> fullPathList = DirectoryGetter.splitDir(path);
			Directory current;
			// check if path or just filename
			if (fullPathList.size() > 1) {
				List<String> dirpath = fullPathList.subList(0,
						fullPathList.size() - 1);
				current = DirectoryGetter.getDir(currentDirectory, dirpath);
			} else {
				current = currentDirectory;
			}
			String filename = fullPathList.get(fullPathList.size() - 1);
			Boolean fileExists = FileGetter.checkfile(current, filename);
			if (fileExists) {
				File f = FileGetter.getFile(current, filename);
				if (command[command.length - 2].equals(">")) {
					f.overwriteContent(string);
				} else if (command[command.length - 2].equals(">>")) {
					f.addContent("\n" + string);
				}
			} else if (SyntaxChecker.validFileName(filename, current)) {
				File f1 = new File(filename, string);
				current.addFile(f1);
			} else {
				System.out.print("Invalid command, please try again\n");
			}
		}
	}

	/**
	 * Return a string created from user input.
	 * 
	 * @param command
	 *            - the user input from JShell (split by " ")
	 * @return string - the user input string
	 * @throws NoSuchDirectoryException
	 */
	public static String stringCreater(String[] command)
			throws NoSuchDirectoryException {
		// check for correct input format
		try {
			if ((command[command.length - 2].equals(">") 
					|| command[command.length - 2].equals(">>"))
					&& (command[1].charAt(0) == '"')
					&& (command[command.length - 3].charAt(
							command[command.length - 3].length() - 1) == '"')) {
				// recreate input string
				String newString = "";
				for (int i = 1; i < (command.length - 2); i++) {
					newString += (command[i] + " ");
				}
				if (SyntaxChecker.validString(newString)) {
					return newString.substring(1, newString.length() - 2);
				} else {
					return null;
				}
			}
			// if command only contains echo STRING, check for correct input
			// format
			// return input string in console
			else if ((command[1].charAt(0) == '"')
					&& (command[command.length - 1].charAt(
							command[command.length - 1].length() - 1) == '"')) {
				String newString = "";
				for (int i = 1; i < (command.length); i++) {
					newString += (command[i] + " ");
				}
				if (SyntaxChecker.validString(newString)) {
					return newString.substring(1, newString.length() - 2);
				} else {
					return null;
				}
			} else {
				System.out.println("Invalid command, please try again");
				return null;
			}
		} catch (Exception e) {
			return "Invalid comamnd, please try again";
		}
	}

	/**
	 * Returns string documentation for echo command.
	 * 
	 * @return documentation string
	 */
	public static String getDocumentation() {
		return documentation;
	}
}
