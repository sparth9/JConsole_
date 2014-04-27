package org.jconsole;

import static org.junit.Assert.*;

import java.io.File;

import org.jconsole.CPCommand;
import org.jconsole.CommandFailedException;
import org.jconsole.JConsole;
import org.junit.Before;
import org.junit.Test;

public class CPCommandTest {

	static CPCommand CpCom = new CPCommand();
	static String curDir = System.getProperty("user.dir");

	@Before
	public void setup() {
		JConsole jcon = JConsole.instance();
		jcon.setCurrentDir(curDir);
		CpCom.setConsole(jcon);
	}

	@Test
	//copy a file to the current directory
	public void CopytoCurrentDir() {
		try {
			String srcDir = curDir + "/testResource/testDoc.txt";
			String[] args1 = { srcDir, "." };
			CpCom.execute(args1);
			String destfile = args1[0].substring(args1[0].lastIndexOf("/"));
			String destPath = curDir + destfile;
			File f = new File(destPath);
			if (!(f.exists())) {
				fail("File not found");
			}
		} catch (CommandFailedException e) {
			e.printStackTrace();
			fail("Exception was thrown");
		}
	}

	@Test
	//test when wild-card argument is used
	public void CopyMaskedFile() {
		try {
			File fd = new File("./testResource/testDoc2.txt");
			if (fd.exists()) {
				fd.delete();
			}
			String[] args1 = { "*stDoc2.txt", "./testResource" };
			CpCom.execute(args1);
			String destPath = curDir + "/testResource/testDoc2.txt";
			File f = new File(destPath);
			if (!(f.exists())) {
				fail("File not found");
			}
		} catch (CommandFailedException e) {
			e.printStackTrace();
			fail("Exception was thrown");
		}
	}

	@Test
	//test when only one argument is given
	public void bad_input_one_arg() {
		try {
			String[] args1 = new String[1];
			args1[0] = "file1";
			CpCom.execute(args1);
			fail("Exception not thrown");
		} catch (CommandFailedException e) {
			e.printStackTrace();
		}
	}

	@Test
	//Test when the argument to the cp command is null
	public void NullArg() {
		try {
			String[] args1 = new String[0];
			CpCom.execute(args1);
			fail("Exception not thrown");
		} catch (CommandFailedException e) {
			e.printStackTrace();
		}
	}
}
