package a2;

import Exceptions.NoSuchDirectoryException;

/**
 * This class contains a method for moving files and directories within a file
 * system.
 * 
 * @author Steven
 *
 */
public class FileMover extends FileCopier {

	// documentation for mv OLDPATH NEWPATH command
	private static String documentation = "Enter mv OLDPATH NEWPATH in the "
			+ "shell to move item OLDPATH to NEWPATH. \n Both OLDPATH and "
			+ "NEWPATH may be relative to the current directory or may \n "
			+ "be full paths. If NEWPATH is a directory, OLDPATH will be "
			+ "moved into the \n directory. If OLDPATH and NEWPATH are both "
			+ "files, the OLDPATH file will \n be renamed/moved to NEWPATH.";

	public static String getDocumentation() {
		return documentation;
	}

	/**
	 * Move item OLDPATH to NEWPATH. Both OLDPATH and NEWPATH may be relative to
	 * the current directory or may be full paths. If NEWPATH is a directory,
	 * move the item into the directory.
	 * 
	 * @param workingDirectory
	 *            The current working directory
	 * @param oldPath
	 *            The path for the item that is being moved
	 * @param newPath
	 *            The path for the directory that the item gets moved to
	 */
	public static void move(Directory workingDirectory, String oldPath,
			String newPath) {
		copy(workingDirectory, oldPath, newPath);

		String oldFileName = getNameFromPath(oldPath);
		String oldDirPath = oldPath.replaceAll(oldFileName, "");
		// If only the name of the to be copied is given, then the path is
		// assumed to be the current directory.
		oldDirPath = (oldDirPath.equals("")) ? "./" : oldDirPath;
		Directory oldDir = null;
		try {
			oldDir = DirectoryGetter.getDir(workingDirectory, oldDirPath);
		} catch (NoSuchDirectoryException e) {
			return;
		}
		// if directory points to a directory (i.e no file name is given)
		if (oldFileName == "") {
			if (!dirExists(oldDirPath, workingDirectory)) {
				System.out.println(oldDirPath + " does not exist.");
				return;
			}
			// Then delete the old directory
			oldDir.getParent().deleteDirectory(oldDir.getName());
		} else {
			if (findFile(oldDir, oldFileName) == null) {
				System.out.println(oldPath + " does not exist.");
				return;
			}
			oldDir.deleteFile(oldFileName);
		}
	}

}
