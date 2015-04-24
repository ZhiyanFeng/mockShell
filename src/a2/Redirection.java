package a2;

import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.util.Arrays;
import java.util.Scanner;

import Exceptions.NoSuchDirectoryException;

/**
 * Class to handle redirection commands for interpreter.
 * 
 * @author Laura
 *
 */
public class Redirection {

	static Directory current = Interpreter.current;
	
	/**
	 * Method to interpret redirection commands for interpreter. Call the
	 * appropriate command or print the appropriate error message. 
	 * 
	 * @param command
	 * 				- The user input split by spaces.
	 * @param console 
	 * 				- The scanner console from JShell.
	 */
	public static void redirect(String[] command,Scanner console) {
		
		try {
			if (command[0].equals("man") && command.length == 4) {
				String doc = Manual.printDocumentation(command[1]);
				Echo.AppendorOverwrite(current, doc, command);
			} else if (command[0].equals("mkdir")) {
				DirectoryMaker.makeDir(current,
						Arrays.copyOfRange(command, 1, command.length - 2));
			} else if (command[0].equals("cd") && command.length == 4) {
				Interpreter.current = DirectoryChanger.changeDir(
						Interpreter.current, command[1]);
			} else if (command[0].equals("pushd") && command.length == 4) {	
				Interpreter.dirStack.pushDirectory(Interpreter.current);
				Interpreter.current = DirectoryChanger.changeDir(
						Interpreter.current,command[1]);
			} else if (command[0].equals("popd") && command.length == 3) {
			    Directory topDir = Interpreter.dirStack.popDirectory();
				Interpreter.current = (topDir 
						== null) ? Interpreter.current : topDir;
			} else if (command[0].equals("ls")) {
				boolean recursive = 
						(command[1].equals("-r") || command[1].equals("-R"));
				if (command.length == 3) {
					String list = DirectoryLister.listDir(current, recursive);
					Echo.AppendorOverwrite(current, list, command);
				} else if ((command.length == 4) && recursive) {
					String list2 = DirectoryLister.listDir(current, recursive);
					Echo.AppendorOverwrite(current, list2, command);
				} else {
					if (recursive) {
						String list2 = DirectoryLister.listDir(current, Arrays
								.copyOfRange(command, 2, command.length - 2),
								recursive);
						Echo.AppendorOverwrite(current, list2, command);
					} else {
						String list2 = DirectoryLister.listDir(current, Arrays
								.copyOfRange(command, 1, command.length - 2),
								recursive);
						Echo.AppendorOverwrite(current, list2, command);
					}
				}
			} else if (command[0].equals("pwd") && command.length == 3) {
				String pwdpath = DirectoryPrinter.printWorkingDir(current);
				Echo.AppendorOverwrite(current, pwdpath, command);
			} else if (command[0].equals("mv") && command.length == 5) {
				FileMover.move(current, command[1], command[2]);
			} else if (command[0].equals("cp") && command.length == 5) {
				FileCopier.copy(current, command[1], command[2]);
			} else if (command[0].equals("cat") && command.length == 4) {
				String content = Catenater.printContent(current, command[1]);
				Echo.AppendorOverwrite(current, content, command);
			} else if (command[0].equals("echo")) {
				Echo.echo(current, command);
			} else if (command[0].equals("get") && command.length == 4) {
				try {
					GetFileFromURL.getter(command[1], current);
				} catch (IOException e) {
					System.out.println("Unable to find file at that URL, "
							+ "please try again.");
				}
			} else if (command[0].equals("rm") && (command.length > 1)) {
				Remover.rm(current, command[1], console);
			} else {
				System.out.println("Invalid command, please try again");
			}
		} catch (NoSuchFileException | NoSuchDirectoryException e) {
			System.out.println(e.getMessage());
		}
	}
}
