package a2;

import java.nio.file.NoSuchFileException;
import java.util.List;

import Exceptions.NoSuchDirectoryException;

/**
 * Command class for the "ls" command.
 * 
 * @author Ken Lee
 */
public class DirectoryLister extends Command {

	// documentation for ls DIR command
	private static String documentation = "Enter ls to display the contents"
			+ " (files and/or directories) of the \n current directory"
			+ " in the shell."
			+ "\n Enter ls PATH (where PATH represents a path or a list of"
			+ " paths) to \n print the contents of the specified path(s)."
			+ "\n Enter ls -R or ls -R PATH to recursively list all "
			+ "subdirectories."
			+ "\n Add > OUTFILE or >> OUTFILE to capture output to outfile.";

	/**
	 * Return the names of the contents of a Directory in a String.
	 * 
	 * Can recursively list all subdirectories in the starting directory.
	 * 
	 * @param start
	 *            the Directory that is to have its contents listed
	 * @param recursive
	 * 				boolean denoting whether the subdirectories are to be
	 * 				listed recursively
	 * @return the names of the Directory's contents in a String
	 */
	public static String listDir(Directory start, boolean recursive) {
		String output = start.getName() + ":\n";
		for (Directory dir : start.getDirectoryListing()) {
			output = output + dir.getName() + "\n";
		}
		for (File file : start.getFileListing()) {
			output = output + file.getName() + "\n";
		}
		if (recursive) {
			for (Directory dir : start.getDirectoryListing()) {
				output = output + "\n" + listDir(dir, recursive);
			}
		}
		return output;
	}

	/**
	 * Return the names of the contents of perhaps multiple Directories.
	 * 
	 * Can take in multiple input Strings that represent paths to Directories.
	 * Those paths may be absolute or relative to a specific Directory.
	 * Assembles a String representation of the contents of the various
	 * Directories and returns it.  Can recursively list all subdirectories in
	 * a starting directory.
	 * 
	 * @param start
	 *            	the Directory input paths may be relative to
	 * @param input
	 *            	the array of Strings of paths to Directories to be listed
	 * @param recursive
	 * 				boolean denoting whether the subdirectories are to be
	 * 				listed recursively
	 * @return the names of the Directories' contents in a String
	 */
	public static String listDir(Directory start, String[] input,
			boolean recursive) {
		String output = "";
		for (String in : input) {
			List<String> parts = DirectoryGetter.splitDir(in);
			try {
				Directory currentDir = start;
				int size = parts.size();
				String file = parts.get(size - 1);
				if (size > 1) {
					currentDir = DirectoryGetter.getDir(currentDir, 
							parts.subList(0, size - 1));
				}
				output = FileGetter.getFile(currentDir, file).getName()+ "\n";
			} catch (NoSuchFileException e) {
				try {
					Directory target = DirectoryChanger.changeDir(start, in);
					output = output + target.getName() + ":\n";
					for (Directory dir : target.getDirectoryListing()) {
						output = output + dir.getName() + "\n";
					}
					for (File f : target.getFileListing()) {
						output = output + f.getName() + "\n";
					}
					output = output + "\n";
					if (recursive) {
						for (Directory dir : target.getDirectoryListing()) {
							output = output + listDir(dir, true);
						}
					}
				} catch (NoSuchDirectoryException f) {
					output = output + f.getMessage() + ": " + in + "\n";
				}
			} catch (NoSuchDirectoryException f) {
				output = output + f.getMessage() + ": " + in + "\n";
			}
		}
		return output;
	}

	/**
	 * Return the documentation of the ls command.
	 * 
	 * @return the String containing the documentation of the command
	 */
	public static String getDocumentation() {
		return documentation;
	}

}
