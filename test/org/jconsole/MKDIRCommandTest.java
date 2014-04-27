package org.jconsole;

import static org.junit.Assert.*;

import java.io.File;

import org.jconsole.CommandFailedException;
import org.jconsole.JConsole;
import org.jconsole.MKDIRCommand;
import org.junit.Before;
import org.junit.Test;

public class MKDIRCommandTest {

	static String myDir = System.getProperty("user.dir");
	MKDIRCommand mkCom = new MKDIRCommand();
	JConsole jcon = JConsole.instance();

	@Before
	public void setup() {
		jcon.setCurrentDir(myDir);
		mkCom.setConsole(jcon);
	}

	@Test
	public void TestExecuteNullInput() // mkdir given with null argument
	{
		String[] args1 = null;
		try {
			mkCom.execute(args1);
			fail("Exception was not thrown");
		} catch (CommandFailedException e) {
			e.printStackTrace();
		}
	}

	@Test
	// create a new directory
	public void TestExecuteValidInput() {
		File f = new File(myDir + "/testResource2");
		String[] args2 = { "testResource2" };
		try {
			if (f.exists())
				f.delete();
			mkCom.execute(args2);
			if (!f.exists())
				fail("File not created");
		} catch (CommandFailedException e) {
			fail("Exception was thrown");
			e.printStackTrace();
		}
	}

}
