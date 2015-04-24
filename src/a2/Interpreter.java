package a2;

import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.util.Arrays;
import java.util.Scanner;

import driver.JShell;
import Exceptions.NoSuchDirectoryException;

/**
 * Class to interpret user input for JShell.
 * 
 * @author Laura
 */
public class Interpreter {

	private static boolean exit = false;
	
	/**
	 * Initializes File System.
	 */
	public static FileSystem f = FileSystem.createFileSystemInstance();
	/**
	 * Initializes current directory at File System Root.
	 */
	public static Directory current = f.getRoot();
	/**
	 * Initializes Directory Stack.
	 */
	public static DirectoryStack dirStack = new DirectoryStack();
	
	/**
	 * Getter method for boolean exit.
	 * @return boolean exit 
	 * 			- false until the user enters "exit"
	 */
	public static boolean getExit() {
		return exit;
	}
	/**
	 * Setter method for boolean exit.
	 * @param boolean b
	 * 				- Set Exit to true when user enters "exit"
	 */
	public static void setExit(Boolean b) {
		exit = b;
	}
	
	/**
	 * Method to interpret user input and call the appropriate command or print
	 * the appropriate error message. 
	 * 
	 * @param command
	 *            - the user input, split by spaces
	 * @param console
	 *            - the Scanner console from JShell
	 */
	public static void interpret(String[] command,Scanner console) {

		try {
			if ((command.length >= 3)
					&& SyntaxChecker.validEcho(command, current)) {
				Redirection.redirect(command, console);
			} else if ((command[0].equals("man")) && (command.length == 2)) {
				System.out.println(Manual.printDocumentation(command[1]));
			} else if ((command[0].equals("mkdir")) && (command.length != 1)) {
				DirectoryMaker.makeDir(current,
						Arrays.copyOfRange(command, 1, command.length));
			} else if (command[0].equals("cd") && (command.length == 2)) {
				current = DirectoryChanger.changeDir(current, command[1]);
			} else if ((command[0].equals("pushd") && (command.length == 2))) {
				dirStack.pushDirectory(current);
				current = DirectoryChanger.changeDir(current,command[1]);
			} else if (command[0].equals("popd") && command.length == 1) {
			  	Directory topDir = dirStack.popDirectory();
				current = (topDir == null) ? current : topDir;
			} else if (command[0].equals("ls")) {
				if (command.length == 1) {
					System.out.println(DirectoryLister.listDir
							(current, false));
				} else {
					boolean recursive = (command[1].equals("-r") 
							|| command[1].equals("-R"));
					if (recursive && command.length != 2) {
						System.out.println(DirectoryLister.listDir(current,
								Arrays.copyOfRange(command, 2, 
										command.length), recursive));
					} else if (recursive && command.length == 2){
						System.out.println(DirectoryLister.listDir
								(current, recursive));
					} else {
						System.out.print(DirectoryLister.listDir(current,
								Arrays.copyOfRange(command, 1, command.length),
								recursive));
					}
				}
			} else if ((command[0].equals("pwd")) && (command.length == 1)) {
				System.out.println(DirectoryPrinter.printWorkingDir(current));
			} else if ((command[0].equals("mv")) && (command.length == 3)) {
				FileMover.move(current, command[1], command[2]);
			} else if ((command[0].equals("cp")) && (command.length == 3)) {
				FileCopier.copy(current, command[1], command[2]);
			} else if ((command[0].equals("cat")) && (command.length == 2)) {
				System.out.println(Catenater.printContent(current, command[1]));
			} else if (command[0].equals("echo") && (command.length > 1)) {
				Echo.echo(current, command);
			} else if (command[0].equals("get") && (command.length == 2)) {
				try {
					GetFileFromURL.getter(command[1], current);
				} catch (IOException e) {
					System.out.println("Unable to find file at that URL, "
							+ "please try again.");
				}
			} else if (command[0].equals("rm") && (command.length > 1)) {
				if(command[1].equals("-f")) {
					ForceRemover.rm(current, command[2]);
				}else {
					Remover.rm(current, command[1],console);
				}
			} else if ((command[0].equals("")) && (command.length == 1)) {

			} else {
				if (command[0].equals("exit")) {
					setExit(true);
				} else {
					System.out.println("Invalid command, please try again");
				}
			}
		} catch (NoSuchFileException | NoSuchDirectoryException e) {
			System.out.println(e.getMessage());
		}	
	}
}
