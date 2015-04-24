package a2;

/**
 * Class to print the path of the current working directory
 */
public class DirectoryPrinter extends Command {

	// documentation for pwd command
	private static String documentation = "Enter pwd to print the current "
			+ "working directory path in the shell. "
			+ "\n Add > OUTFILE or >> OUTFILE to capture output to outfile.";

	public static String getDocumentation() {
		return documentation;
	}

	/**
	 * Returns the path of the working directory
	 * 
	 * @param current
	 *            The current (working) directory
	 * @return The path of the given directory
	 */
	public static String printWorkingDir(Directory current) {
		return current.getPath();
	}
}
