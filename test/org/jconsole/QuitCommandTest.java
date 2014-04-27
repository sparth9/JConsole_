package org.jconsole;

import java.security.Permission;

import org.jconsole.CommandFailedException;
import org.jconsole.JConsole;
import org.jconsole.QuitCommand;
import org.junit.Test;

import junit.framework.TestCase;

public class QuitCommandTest extends TestCase {
	static QuitCommand quitCom = new QuitCommand();

	protected static class ExitException extends SecurityException {
		public final int status;

		public ExitException(int status) {
			super("There is no escape!");
			this.status = status;
		}
	}

	private static class NoExitSecurityManager extends SecurityManager {
		@Override
		public void checkPermission(Permission perm) {
			// allow anything.
		}

		@Override
		public void checkPermission(Permission perm, Object context) {
			// allow anything.
		}

		@Override
		public void checkExit(int status) {
			super.checkExit(status);
			throw new ExitException(status);
		}
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		System.setSecurityManager(new NoExitSecurityManager());
		JConsole jcon = JConsole.instance();
		quitCom.setConsole(jcon);
	}

	@Override
	protected void tearDown() throws Exception {
		System.setSecurityManager(null); // or save and restore original
		super.tearDown();
	}

	@Test
	// test the quit command by overriding the java SecurityManager
	public void test_quit() {
		try {
			quitCom.execute(null);

		} catch (CommandFailedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExitException e) {
			assertEquals("Exit Status", 0, e.status);
		}
	}

	@Test
	public void test_no_quit() {
		assert (true);
	}
}