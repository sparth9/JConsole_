package org.jconsole;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Random;

import org.jconsole.CommandFailedException;
import org.jconsole.JConsole;
import org.jconsole.commands.pingCommand;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PingCommandTest {

	static pingCommand pingCom = new pingCommand();
	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();

	@Before
	public void setup() {
		System.setOut(new PrintStream(outContent));
		System.setErr(new PrintStream(errContent));
		JConsole jcon = JConsole.instance();
	}

	/** Tests the IP address of the local host **/
	@Test
	public void testLocalHost() {
		try {
			String[] args1 = new String[1];
			args1[0] = "localhost";
			pingCom.execute(args1);
			String output = outContent.toString();
			Assert.assertTrue(output.contains("127.0.0.1"));
		} catch (CommandFailedException e) {
			e.printStackTrace();
			fail("Exception was thrown");
		}
	}

	@Test
	// test invalid host
	public void testInvalidHost() {
		try {
			String[] args1 = new String[1];
			args1[0] = "abc";
			pingCom.execute(args1);
			fail("Exception was not thrown as anticipated");
		} catch (CommandFailedException e) {
			e.printStackTrace();
		}
	}

}
