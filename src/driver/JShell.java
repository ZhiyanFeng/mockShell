//**********************************************************
// Assignment2:
// CDF user_name1: c4songyb
// CDF user_name2: c3leeken
// CDF user_name3: c3garret
// CDF user_name4: c4fengzi
//
// Author1: Steven Song
// Author2: Ken Wei Lee
// Author3: Laura Garrett
// Author4: zhiyan feng
//
//
// Honor Code: I pledge that this program represents my own
//   program code and that I have coded on my own. I received 
//   help from no one in designing and debugging my program.
//   I have also read the plagiarism section in the course info
//   sheet of CSC 207 and understand the consequences.  
//*********************************************************
package driver;

import java.util.Scanner;

import a2.*;

/**
 * Class to interact with user. Take in user input and display appropriate
 * output.
 * 
 * @author Laura
 */
public class JShell {

	/**
	 * Main program.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		String[] command;
		
		Scanner console = new Scanner(System.in);
		do {

			// print current path
			System.out.print(Interpreter.current.getPath() + "# ");
			// Take user input into the console
			String input = console.nextLine();
			command = input.trim().split(" +");
			// Interpret command and take appropriate action
			Interpreter.interpret(command,console);
		}
		while (!Interpreter.getExit());

		console.close();
	}
}
