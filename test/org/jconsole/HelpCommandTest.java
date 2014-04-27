package org.jconsole;

import static org.junit.Assert.fail;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Random;

import org.jconsole.CommandFailedException;
import org.jconsole.HelpCommand;
import org.jconsole.JConsole;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class HelpCommandTest {

	static HelpCommand hcom = new HelpCommand();
	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
	private static String myDir = System.getProperty("user.dir");
	// contains commands that whose details are
	// known using the help keyword
	private String commandList[]; 

	@Before
	public void setup() {
		System.setOut(new PrintStream(outContent));
		System.setErr(new PrintStream(errContent));
		JConsole jcon = JConsole.instance();
		jcon.setCurrentDir(myDir);
		hcom.setConsole(jcon);
		createCommandList();
	}

	public void createCommandList() {
		commandList = new String[] { "ls", "help", "cls", "cd", "cp", "exit",
				"quit", "pwd", "set", "show" };
	}

	@Test
	// test the output of help command without any arguments
	public void helpDisplay() {
		try {
			String[] args1 = null;
			hcom.execute(args1);
			String output = outContent.toString();
			Assert.assertTrue(output.contains("help"));
		} catch (CommandFailedException e) {
			e.printStackTrace();
			fail("Exception was thrown");
		}
		outContent.reset();
	}

	@Test
	// test the help command for a random command from the commandList object
	public void testRandomCommand() {
		try {
			int index = new Random().nextInt(commandList.length);
			String random_value = commandList[index];
			String[] args = new String[1];
			args[0] = random_value;
			hcom.execute(args);
			String output = outContent.toString();
			Assert.assertTrue(output.contains("Help topic for"));
		} catch (CommandFailedException e) {
			e.printStackTrace();
			fail("Exception was thrown");
		}
		outContent.reset();

	}
}