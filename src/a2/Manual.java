package a2;

/**
 * Class to handle JShell man command
 * 
 * @author Laura
 *
 */
public class Manual extends Command {

	/**
	 * string documentation for man command.
	 */
	private static String documentation = "Print documentation for CMD."
			+ "\n Add > OUTFILE or >> OUTFILE to capture output to outfile.";

	/**
	 * Get documentation string for man command.
	 * 
	 * @return documentation string
	 */
	public static String getDocumentation() {
		return documentation;
	}

	/**
	 * Method to return documentation for a given command, if it exists.
	 * Otherwise return that the command does not exist
	 * 
	 * @param command
	 * @return String of documentation
	 */
	public static String printDocumentation(String cmd) {
		if (cmd.equals("mkdir")) {
			return DirectoryMaker.getDocumentation();
		} else if (cmd.equals("ls")) {
			return DirectoryLister.getDocumentation();
		} else if (cmd.equals("cd")) {
			return DirectoryChanger.getDocumentation();
		} else if (cmd.equals("pwd")) {
			return DirectoryPrinter.getDocumentation();
		} else if (cmd.equals("mv")) {
			return FileMover.getDocumentation();
		} else if (cmd.equals("cp")) {
			return FileCopier.getDocumentation();
		} else if (cmd.equals("cat")) {
			return Catenater.getDocumentation();
		} else if (cmd.equals("echo")) {
			return Echo.getDocumentation();
		} else if (cmd.equals("get")) {
			return GetFileFromURL.getDocumentation();
		} else if (cmd.equals("pushd")) {
			return DirectoryStack.getDocumentationPushd();
		} else if (cmd.equals("popd")) {
			return DirectoryStack.getDocumentationPopd();
		} else if (cmd.equals("rm")) {
			return Remover.getDocumentation();
		} else if (cmd.equals("man")) {
			return Manual.getDocumentation();
		} else if (cmd.equals("exit")) {
			return ("Exit the program.");
		} else {
			return ("No such command.");
		}
	}
}
