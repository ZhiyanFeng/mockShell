package test;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import a2.Manual;

public class ManualTester {

	String mkdirDocumentation;
	String pwdDocumentation;

	@Before
	public void setUp() {
		mkdirDocumentation = "Enter mkdir DIR in the shell "
				+ "(where DIR represents a directory that may \n be relative "
				+ "to the current directory or a full path) to create the \n"
				+ "directory DIR.";

		pwdDocumentation = "Enter pwd to print the current "
				+ "working directory path in the shell. "
				+ "\n Add > OUTFILE or >> OUTFILE to capture output to "
				+ "outfile.";
	}

	@Test
	public void testmanmkdir() {
		// test man mkdir
		assertEquals(mkdirDocumentation, Manual.printDocumentation("mkdir"));
	}

	@Test
	public void testmanpwd() {
		// test man pwd
		assertEquals(pwdDocumentation, Manual.printDocumentation("pwd"));
	}

	@Test
	public void testNoSuchCommand() {
		// test a command that does not exist (ie man abc)
		assertEquals("No such command.", Manual.printDocumentation("abc"));
	}
}
