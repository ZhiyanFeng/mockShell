package a2;

/**
 * Emulates a file system.
 * 
 * @author Ken Lee
 */
public class FileSystem {
	private static FileSystem singleReference = null;
	private Directory root = null;

	// Make sure we only ever have one instance of FileSystem
	private FileSystem() {
		root = new Directory("");
	}

	/**
	 * Make sure there's only ever a singleton instance of FileSystem.
	 * 
	 * If a FileSystem doesn't exist yet, create it and return it. Otherwise
	 * return the FileSystem that already exists.
	 * 
	 * @return the singleton FileSystem
	 */
	public static FileSystem createFileSystemInstance() {
		if (singleReference == null) {
			singleReference = new FileSystem();
		}
		return singleReference;
	}

	/**
	 * Returns the root Directory of the FileSystem.
	 * 
	 * @return the root Directory of the FileSystem
	 */
	public Directory getRoot() {
		return this.root;
	}
}