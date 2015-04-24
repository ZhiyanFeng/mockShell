package a2;

import java.util.HashSet;

/**
 * Emulates a directory in a file system.
 * 
 * Emulates a directory in a file system. Has a name, a parent directory it
 * resides within and a path from the root. Contains sets of directories and
 * files.
 * 
 * @author Ken Lee
 */
public class Directory extends Object {
	private String name = null;
	private Directory parent = null;
	private String path = null;
	private HashSet<Directory> directoryListing = new HashSet<Directory>();
	private HashSet<File> fileListing = new HashSet<File>();

	/**
	 * Default constructor
	 */
	public Directory() {
	}

	/**
	 * Constructor for a directory that only has a name.
	 * 
	 * @param n
	 *            String containing the name for the directory
	 */
	public Directory(String n) {
		name = n;
		setPath();
	}

	/**
	 * Constructor for a directory with both a name and a parent directory.
	 * 
	 * @param n
	 *            String containing the name for the directory
	 * @param p
	 *            the Directory that the newly created Directory resides in
	 */
	public Directory(Directory p, String n) {
		name = n;
		parent = p;
		setPath();
	}

	/**
	 * Return the name of the Directory.
	 * 
	 * @return the name of the Directory
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Return the Directories contained within the Directory as a set.
	 * 
	 * @return the set of Directories
	 */
	public HashSet<Directory> getDirectoryListing() {
		return this.directoryListing;
	}

	/**
	 * Adds a Directory to a Directory.
	 * 
	 * Adds a Directory to a Directory. Sets the parent of the Directory being
	 * added as the Directory it's being added to. Updates the path of the
	 * Directory being added.
	 * 
	 * @param d
	 *            the Directory to be added
	 */
	public void addDirectory(Directory d) {
		this.directoryListing.add(d);
		d.setParent(this);
		d.setPath();
	}

	/**
	 * Deletes a Directory from the set containing it in its parent Directory.
	 * 
	 * @param n
	 *            the name String of the Directory to be deleted
	 */
	public void deleteDirectory(String n) {
		Directory dirToBeDeleted = null;
		for (Directory d : this.directoryListing) {
			if (d.getName() == n) {
				dirToBeDeleted = d;
			}
		}
		// Should only delete the directory outside of the loop, or else
		// a ConcurrentModificationException may get generated.
		this.directoryListing.remove(dirToBeDeleted);
	}

	/**
	 * Returns the Files contained within the Directory as a set.
	 * 
	 * @return the set of Files
	 */
	public HashSet<File> getFileListing() {
		return this.fileListing;
	}

	/**
	 * Adds a File to a Directory.
	 * 
	 * @param f
	 *            the File to be added
	 */
	public void addFile(File f) {
		this.fileListing.add(f);
	}

	/**
	 * Deletes a File from the set containing it in its parent Directory.
	 * 
	 * @param n
	 *            the name String of the File to be deleted
	 */
	public void deleteFile(String n) {
		File fileToBeDeleted = null;
		for (File f : this.fileListing) {
			if (f.getName().equals(n)) {
				fileToBeDeleted = f;
			}
		}
		// Should only delete the file outside of the loop, or else
		// a ConcurrentModificationException may get generated.
		this.fileListing.remove(fileToBeDeleted);
	}

	/**
	 * Sets the parent Directory.
	 * 
	 * @param par
	 *            the Directory that is to be the parent
	 */
	public void setParent(Directory par) {
		this.parent = par;
	}

	/**
	 * Returns the parent Directory.
	 * 
	 * @return the parent Directory
	 */
	public Directory getParent() {
		return parent;
	}

	/**
	 * Sets the path of the Directory.
	 * 
	 * Traces the Directory back to the root Directory. Contains the path in a
	 * String representation.
	 * 
	 * @param par
	 *            the Directory that is to be the parent
	 */
	public void setPath() {
		Directory d = this;
		path = this.name + "/";
		while (d.parent != null) {
			path = d.parent.name + "/" + path;
			d = d.parent;
		}
	}

	/**
	 * Returns the String representation of the path.
	 * 
	 * @return the String representation of the path
	 */
	public String getPath() {
		return path;
	}
}