package a2;

import Exceptions.NoSuchDirectoryException;

/**
 * This class contains various methods for copying files, as well as various
 * methods locating and identifying files within a given file system
 * 
 * @author Steven
 *
 */
public class FileCopier extends Command {

	// documentation for cp OLDPATH NEWPATH command
	private static String documentation = "Enter cp OLDPATH NEWPATH in the "
			+ "shell to copy item OLDPATH to NEWPATH. \n Both OLDPATH and "
			+ "NEWPATH may be relative to the current directory or may \n "
			+ "be full paths. If NEWPATH is a directory, OLDPATH will be "
			+ "copied into the \n directory. If OLDPATH is a directory, "
			+ "all of its comtents will be copied. \n If OLDPATH and NEWPATH "
			+ "are both files, the OLDPATH file will \n be copied and the "
			+ "copy will be named NEWPATH.";

	public static String getDocumentation() {
		return documentation;
	}

	/**
	 * Copies the file at oldPath to newPath. If oldPath is a directory, then
	 * all of oldPath's contents are copied into newPath. (Assumes that the
	 * arguments passed on to the method are valid)
	 * 
	 * @param workingDirectory
	 *            The current working directory
	 * @param oldPath
	 *            The path for the item that gets copied
	 * @param newPath
	 *            The path for the directory that the item gets copied to
	 */
	public static void copy(Directory workingDirectory, String oldPath,
			String newPath) {
		try {
			String oldFileName, newFileName;
			// The given paths are always separated into two parts:
			// 1) ...FileName, which contains the name of the file
			// denoted by the path (if the path points to a file)
			// 2) ...DirPath, which can contain either the path of the directory
			// that the file resides in (if the path points to a file) or the
			// original path that is passed to the method (if the original path
			// points to a directory).
			String oldDirPath = "";
			String newDirPath = "";
			String newFolderName = "";

			oldFileName = getNameFromPath(oldPath);
			oldDirPath = oldPath.replaceAll(oldFileName, "");
			newFileName = getNameFromPath(newPath);
			newDirPath = newPath.replaceAll(newFileName, "");
			// If only the name of the file to be copied is given, then the path
			// is assumed to be the current directory.
			oldDirPath = (oldDirPath.equals("")) ? "./" : oldDirPath;
			newDirPath = (newDirPath.equals("")) ? "./" : newDirPath;
			if (oldFileName == "" && newFileName != "") {
				System.out.println("Invalid command, please try again");
				return;
			}
			if (!dirExists(oldDirPath, workingDirectory)) {
				System.out.println(oldDirPath + " does not exist.");
				return;
			}

			// Get the Directory objects denoted by the given paths
			Directory oldDir = DirectoryGetter.getDir(workingDirectory,
					oldDirPath);
			Directory newDir = DirectoryGetter.getDir(workingDirectory,
					newDirPath);

			// If oldPath points to a file, then just copy the file to newPath
			if (oldFileName != "") {
				if (findFile(oldDir, oldFileName) == null) {
					System.out.println(oldPath + " does not exist.");
					return;
				}
				if (newFileName == "")
					newFileName = oldFileName;
				if (!SyntaxChecker.validFileName(newFileName, newDir))
					return;
				copyFile(oldFileName, newFileName, oldDir, newDir);
			}
			// In case oldPath is a directory, then newPath is also assumed to
			// be a directory. Therefore, a copy of the oldDir will need to be
			// made in newDir.
			else {
				if (!SyntaxChecker.validDirName(oldDir.getName(), newDir))
					return;

				Directory copyOfOldDir = new Directory(newDir, oldDir.getName());
				newDir.addDirectory(copyOfOldDir);

				// Copy all of the contents of the old directory into the new
				// directory
				for (File f : oldDir.getFileListing().toArray(new File[0])) {
					copyFile(f.getName(), f.getName(), oldDir, copyOfOldDir);
				}
				for (Directory d : oldDir.getDirectoryListing().toArray(
						new Directory[0])) {
					copy(workingDirectory, d.getPath(), copyOfOldDir.getPath());
				}
			}
		} catch (NoSuchDirectoryException e) {

		}
	}

	/**
	 * Copies a given file from oldDir to a newDir, with the new file having the
	 * name newFileName.
	 * 
	 * @param oldFileName
	 *            The name of the file that gets copied
	 * @param newFileName
	 *            The name of the new file that gets created
	 * @param oldDir
	 *            The directory that the old file resides in
	 * @param newDir
	 *            The directory that the file gets copied to
	 */
	public static void copyFile(String oldFileName, String newFileName,
			Directory oldDir, Directory newDir) {
		File fileToCopy = findFile(oldDir, oldFileName);
		File copyOfFile = new File(newFileName, fileToCopy.getContent());
		newDir.addFile(copyOfFile);
	}

	/**
	 * Retrieves a file if it resides in the given directory.
	 * 
	 * @param d
	 *            The directory that the given file is supposed to reside in
	 * @param fileName
	 *            The name of the file that is to be found
	 * @return The actual File with the fileName if it exists, null otherwise.
	 */
	public static File findFile(Directory d, String fileName) {

		File[] listOfFiles = d.getFileListing().toArray(new File[0]);
		for (File f : listOfFiles) {
			if (f.getName().equals(fileName))
				return f;
		}
		return null;
	}

	/**
	 * Checks to see if a given directory with a path (relative to the current
	 * directory) exists or not
	 * 
	 * @param path
	 *            The path of the given directory (relative to the current
	 *            directory)
	 * @param currentDir
	 *            The Directory object that represents the current directory
	 * @return TRUE if the given directory exists, FALSE otherwise
	 */
	public static boolean dirExists(String path, Directory currentDir) {

		if (!(path.endsWith("/")))
			path = path + "/";
		try {
			DirectoryGetter.getDir(currentDir, path);
		} catch (NoSuchDirectoryException e) {
			return false;
		}
		return true;
	}

	/**
	 * Gets the file name that is found at the end of a given path
	 * 
	 * @param path
	 *            The path for either a directory or a file
	 * @return An empty string if path denotes a director, otherwise the name of
	 *         the file denoted by path is returned.
	 */
	public static String getNameFromPath(String path) {
		String fileName;
		String[] pathParts = path.split("/");
		int txtindex = pathParts[pathParts.length - 1].indexOf(".txt");
		if (txtindex != -1)
			fileName = pathParts[pathParts.length - 1];
		else
			fileName = "";
		return fileName;
	}
}
