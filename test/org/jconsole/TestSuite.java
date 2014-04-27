package org.jconsole;

import org.jconsole.CLSCommand;
import org.jconsole.HistoryCommand;
import org.jconsole.QuitCommand;
import org.junit.runner.JUnitCore;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	LSCommandTest.class,
	PWDCommandTest.class,
	HelpCommandTest.class,
	CDCommandTest.class,
	CPCommandTest.class,
	MKDIRCommandTest.class,
	RMDIRCommandTest.class,
	PingCommandTest.class,
	QuitCommand.class,
	HistoryCommand.class
})

public class TestSuite {

}