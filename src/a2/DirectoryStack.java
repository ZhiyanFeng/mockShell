package a2;

import java.util.Stack;

public class DirectoryStack extends Command {
	private Stack<Directory> directoryStack;

	private static String documentationPushd = "Enter pushd DIR to save the current"
			+ " working directory onto stack and then \n"
			+ "change the current working directory to DIR. The old current"
			+ " directory \n be returned later via the popd command";

	private static String documentationPopd = "Enter popd to remove the top most "
			+ "directory from the stack and make it the \n the current working "
			+ "directory. If the stack is empty, an error message will be "
			+ "displayed.";

	/**
	 * Returns string documentation for pushd command.
	 * 
	 * @return documentation string
	 */
	public static String getDocumentationPushd() {
		return documentationPushd;
	}

	/**
	 * Returns string documentation for popd command.
	 * 
	 * @return documentation string
	 */
	public static String getDocumentationPopd() {
		return documentationPopd;
	}

	/**
	 * Generates a new DirectoryStack and returns it to the caller.
	 * 
	 * @return directoryStack An empty stack for directories
	 */
	public DirectoryStack() {
		directoryStack = new Stack<Directory>();
	}

	/**
	 * Pushes the given dir onto the DirectoryStack
	 * 
	 * @param dir
	 *            The current working directory
	 */
	public void pushDirectory(Directory dir) {
		directoryStack.push(dir);
	}

	/**
	 * Pops the topmost dir from the DirectoryStack
	 * 
	 * @return topDir The topmost directory DirectoryStack (if the stack is not
	 *         empty and the topmost directory hasn't been removed/moved), 
	 *         null otherwise.
	 */
	public Directory popDirectory() {
		Directory topDir = null;
		if (!directoryStack.isEmpty()) {
			topDir = directoryStack.pop();
			// Check to see if the directory that got popped has been moved
			// or removed
			if (!topDir.getParent().getDirectoryListing().contains(topDir)) {
			    System.out.println ("The topmost directory in the stack " +
			            "is no longer valid (due to mv or rm)");
			    return null;
			}
		} else {
			System.out.println("There are no more directories on the stack.");
		}

		return topDir;
	}

	/**
	 * Checks to see if the DirectoryStack is empty or not.
	 * 
	 * @return TRUE if the DirectoryStack is empty, FALSE otherwise.
	 */
	public boolean isEmpty() {
		return directoryStack.isEmpty();
	}
}
