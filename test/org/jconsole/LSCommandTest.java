package org.jconsole;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jconsole.CommandFailedException;
import org.jconsole.JConsole;
import org.jconsole.LSCommand;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class LSCommandTest {

	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();

	static LSCommand LSCom = new LSCommand();
	static String curDir = System.getProperty("user.dir");

	@Before
	public void setUpStreams() {
		System.setOut(new PrintStream(outContent));
		System.setErr(new PrintStream(errContent));
		JConsole jcon = JConsole.instance();
		jcon.setCurrentDir(curDir);
		LSCom.setConsole(jcon);
	}

	@Test
	// test the ls command when the wild card is used at the end
	public void test_wild_card_at_end() {
		try {
			String[] args1 = new String[1];
			args1[0] = "J*";
			LSCom.execute(args1);
			boolean file_found = true;
			String[] expected = { "Jconsole.xml", "JconsoleDoc.xml" };
			String output = outContent.toString();
			for (String s : expected) {
				if (!output.contains(s)) {
					file_found = false;
					break;
				}
			}
			if (file_found == false) {
				assertTrue(false);
			}
		} catch (CommandFailedException e) {
			e.printStackTrace();
			fail("Exception was thrown");
		}
		outContent.reset();
	}

	@Test
	// test the ls command when the wild card is used at the middle
	public void test_wild_card_at_middle() {
		try {
			String[] args1 = new String[1];
			args1[0] = "Jcon*xml";
			LSCom.execute(args1);
			boolean file_found = true;
			String[] expected = { "Jconsole.xml", "JconsoleDoc.xml" };
			String output = outContent.toString();
			for (String s : expected) {
				if (!output.contains(s)) {
					file_found = false;
					break;
				}
			}
			if (file_found == false) {
				assertTrue(false);
			}
		} catch (CommandFailedException e) {
			e.printStackTrace();
			fail("Exception was thrown");
		}
		outContent.reset();
	}

	@Test
	// test the ls command when the wild card is used at the beginning
	public void test_wild_card_at_beginning() {
		try {
			String[] args1 = new String[1];
			args1[0] = "*xml";
			LSCom.execute(args1);
			boolean file_found = true;
			String[] expected = { "Jconsole.xml", "JconsoleDoc.xml",
					"build.xml", "buildDoc.xml", "pom.xml" };
			String output = outContent.toString();
			for (String s : expected) {
				if (!output.contains(s)) {
					file_found = false;
					break;
				}
			}
			if (file_found == false) {
				assertTrue(false);
			}
		} catch (CommandFailedException e) {
			e.printStackTrace();
			fail("Exception was thrown");
		}
		outContent.reset();
	}

	@Test
	// test when ls is used without any arguments
	public void test_with_no_args() {
		try {

			List<String> expected_files = get_files_in_directory();

			String[] args1 = new String[0];
			LSCom.execute(args1);
			boolean file_found = true;

			String output = outContent.toString();
			for (String s : expected_files) {
				if (!output.contains(s)) {
					file_found = false;
					break;
				}
			}

			if (file_found == false) {
				assertTrue(false);
			}

		} catch (CommandFailedException e) {
			e.printStackTrace();
			fail("Exception was thrown");
		}
		outContent.reset();
	}

	@Test
	// test when unexpected arguments are used
	public void test_with_bad_args() {

		String[] args1 = new String[1];
		args1[0] = "_*";
		String regex = "^[a-zA-Z0-9_]*$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(args1[0]);
		if (!matcher.find()) {
			// System.out.println("Pattern not found");
			assertTrue(true);
		} else {
			assertTrue(false);
		}
	}

	private List<String> get_files_in_directory() {
		File directory = new File(curDir);
		File[] fList = directory.listFiles();
		List<String> expected_files = new ArrayList<String>();
		for (File file : fList) {
			expected_files.add(file.getName());
		}
		return expected_files;
	}
}
