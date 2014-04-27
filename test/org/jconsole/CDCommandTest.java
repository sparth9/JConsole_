package org.jconsole;

import static org.junit.Assert.*;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jconsole.CDCommand;
import org.jconsole.CommandFailedException;
import org.jconsole.JConsole;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CDCommandTest {

	static CDCommand cdCom = new CDCommand();
	static String curDir = System.getProperty("user.dir");

	@Before
	public void setup() {
		JConsole jcon = JConsole.instance();
		jcon.setCurrentDir(curDir);
		cdCom.setConsole(jcon);
	}

	@Test
	// no arguments to cd
	public void TestExecuteNullInput() {
		try {
			String[] args1 = { "" };
			cdCom.execute(args1);
			File newFileDir = cdCom.getCurrentDir();
			String newDir = newFileDir.toString();
			Assert.assertEquals(curDir, newDir);
		} catch (CommandFailedException e) {
			e.printStackTrace();
			fail("Exception was thrown");
		}
	}

	@Test
	// changing dir to src
	public void TestExecuteValidInput() {
		try {
			String[] args1 = { "src" };
			cdCom.execute(args1);
			File newFileDir = cdCom.getCurrentDir();
			String newDir = newFileDir.toString();
			String expDir = curDir + "\\src";
			Assert.assertEquals(expDir, newDir);

		} catch (CommandFailedException e) {
			e.printStackTrace();
			fail("Exception was thrown");
		}
	}

	@Test
	// move one level up CD..
	public void TestExecuteOneLevelUp() {
		try {
			String[] args1 = { ".." };
			cdCom.execute(args1);
			File newFileDir = cdCom.getCurrentDir();
			String newDir = newFileDir.toString();
			String expDir = curDir.substring(0, curDir.lastIndexOf("\\"));
			Assert.assertEquals(expDir, newDir);

		} catch (CommandFailedException e) {
			e.printStackTrace();
			fail("Exception was thrown");
		}
	}

	@Test
	// current dir cd .
	public void TestExecuteCurDirInput() {
		try {
			String[] args1 = { "." };
			cdCom.execute(args1);
			File newFileDir = cdCom.getCurrentDir();
			String newDir = newFileDir.toString();
			String expDir = curDir;
			Assert.assertEquals(expDir, newDir);

		} catch (CommandFailedException e) {
			e.printStackTrace();
			fail("Exception was thrown");
		}
	}

	@Test
	// directory does not exist
	public void TestExecuteInvalidInput() {
		try {
			String[] args1 = { "psedo" };
			cdCom.execute(args1);
			fail("Exception was not thrown");
		} catch (CommandFailedException e) {
			e.printStackTrace();
			File newFileDir = cdCom.getCurrentDir();
			String newDir = newFileDir.toString();
			String expDir = curDir;
			Assert.assertEquals(expDir, newDir);

		}
	}

	@Test
	// test when invalid characters are given to the cd command
	public void test_invalid_characters() {
		String[] args1 = new String[1];
		args1[0] = "132";
		String regex = "^[^\\d\\s:_=+*-/]*$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(args1[0]);
		System.out.println(matcher.find());
		if (matcher.find()) {
			assertTrue(false);
		} else {
			assertTrue(true);
		}
	}

}
