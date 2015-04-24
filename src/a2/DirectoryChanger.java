package a2;

import java.util.*;

import Exceptions.NoSuchDirectoryException;

public class DirectoryChanger extends Command {

	// documentation for cd DIR command
	private static String documentation = "Enter cd DIR in the shell (where "
			+ "DIR represents a directory which may \n be relative to the "
			+ "current directory or may be a full path) to change \n "
			+ "from the current directory to directory DIR.";

	public DirectoryChanger() {
	}

	public static String getDocumentation() {
		return documentation;
	}

	// get the working directory if given a file name
	public static Directory changeDir(Directory currentDir, String fileName)
			throws NoSuchDirectoryException {
		List<String> dirList = DirectoryGetter.splitDir(fileName);
		return DirectoryGetter.getDir(currentDir, dirList);
	}
}
