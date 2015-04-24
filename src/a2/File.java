package a2;

/**
 * Emulates a file in a file system.
 * 
 * Emulates a file in a file system. Has a name and contents.
 * 
 * @author Ken Lee
 */
public class File {
	private String name = null;
	private String content = "";

	/**
	 * Default constructor
	 */
	public File() {
	}

	/**
	 * Constructor to create a file with a name but no content.
	 * 
	 * @param n
	 *            the name of the file
	 */
	public File(String n) {
		name = n;
	}

	/**
	 * Constructor to create a file with a name and content.
	 * 
	 * @param n
	 *            the name of the file
	 * @param c
	 *            the content of the file
	 */
	public File(String n, String c) {
		name = n;
		content = c;
	}

	/**
	 * Sets the name of a file
	 * 
	 * @param newName
	 *            the new name which the File will have
	 */
	public void setName(String newName) {
		this.name = newName;
	}

	/**
	 * Returns the name of the File.
	 * 
	 * @return the String containing the name of the File
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns the content of the File.
	 * 
	 * @return the String containing the content of the File
	 */
	public String getContent() {
		return content;
	}

	/**
	 * Overwrites the content of the File.
	 * 
	 * @param c
	 *            the String which will overwrite the content of the File
	 */
	public void overwriteContent(String c) {
		content = c;
	}

	/**
	 * Adds to the existing content of the File.
	 * 
	 * @param c
	 *            the String which will be added to the content of the File
	 */
	public void addContent(String c) {
		content = content + c;
	}

	/**
	 * Compares the File to another File by name and content.
	 * 
	 * @param o
	 *            the File to be compared to
	 */
	public boolean equals(File o) {
		return (this.name.equals(o.getName()))
				&& (this.content.equals(o.getContent()));
	}
}
