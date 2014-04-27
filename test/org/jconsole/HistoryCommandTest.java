package org.jconsole;

import static org.junit.Assert.fail;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.jconsole.CommandFailedException;
import org.jconsole.HelpCommand;
import org.jconsole.HistoryCommand;
import org.jconsole.JConsole;
import org.jconsole.LSCommand;
import org.jconsole.PWDCommand;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class HistoryCommandTest {

	static HistoryCommand hcom = new HistoryCommand();
	JConsole jcon = JConsole.instance();
	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();

	@Before
	public void setup() {
		hcom.setConsole(jcon);
		runSomeCommands();
	}

	private void runSomeCommands() {
		LSCommand lsCom = new LSCommand();
		HelpCommand hCom = new HelpCommand();
		PWDCommand pwdCom = new PWDCommand();
		hCom.setConsole(jcon);
		pwdCom.setConsole(jcon);
		jcon.processLine("pwd");
		jcon.processLine("help");
		String[] args = null;
		try {
			pwdCom.execute(args);
		} catch (CommandFailedException e) {
			e.printStackTrace();
		}
		try {
			hCom.execute(args);

		} catch (CommandFailedException e) {
			e.printStackTrace();
		}

	}

	@Test
	// run a few commands and then check if the history command runs as expected
	public void testHistoryCommand() {
		try {
			String[] args = new String[1];
			args[0] = "2";
			hcom.execute(args);
			List h = jcon.getCommandHistory();
			List<String> commandList = new ArrayList<String>();
			for (int i = 0; i < h.size(); i++) {
				commandList.add(h.get(i).toString());
			}
			String top = commandList.get(0);
			Assert.assertEquals(top, "pwd");
		} catch (CommandFailedException e) {
			e.printStackTrace();
			fail("Exception was thrown");
		}
	}
}