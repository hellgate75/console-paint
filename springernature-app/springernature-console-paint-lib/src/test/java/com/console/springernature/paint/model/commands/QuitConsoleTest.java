package com.console.springernature.paint.model.commands;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.doReturn;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.junit.runners.MethodSorters;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.console.springernature.paint.model.Canvas;
import com.console.springernature.paint.model.DeviceManager;

/**
 * This test automate class gives evidence of the effective availability of the CommandManager command features.
 * @author Fabrizio Torelli
 *
 */
@RunWith(JUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class QuitConsoleTest {
	private static final Logger logger = LoggerFactory.getLogger("com.console.springernature.paint.model");
	private static final String commandSigature = "Q";
	
	private static @Spy QuitConsole commandUnderTest = new QuitConsole();
	private static @Spy QuitConsole commandMocked = Mockito.mock(QuitConsole.class);
	
	@Test
	/*
	 * Here we test the effective availability of the QuitConsole instance
	 */
	public void test0QuitConsoleInstance() {
		logger.info("Testing the QuitConsole command instance availability ...");
		assertNotNull(commandUnderTest);
	}

	@Test
	/*
	 * Here we test the effective symbol of the QuitConsole instance
	 */
	public void test1QuitConsoleSymbol() {
		logger.info("Testing the QuitConsole command is available as singleton ...");
		assertEquals(commandSigature, commandUnderTest.getCommandSymbol());
	}

	@Test
	/*
	 * Here we test the effective composite nature of the QuitConsole instance
	 */
	public void test2QuitConsoleSymbol() {
		logger.info("Testing the QuitConsole command is not a composite command ...");
		assertFalse(commandUnderTest.isComposedCommand());
	}
	
	@Test
	/*
	 * Here we test the QuitConsole instance accepts correct parameters
	 */
	public void test3QuitConsoleAcceptsCorrectParameters() {
		logger.info("Testing the QuitConsole command accepts correct parameters ...");
		Canvas canvas = (Canvas)DeviceManager.getInstance().getDefaultCanvas();
		String[] parameters = new String[] {};
		assertTrue(commandUnderTest.acceptParameters(canvas, parameters));
	}
	
	@Test
	/*
	 * Here we test the QuitConsole instance is able to run the command as simple or composed
	 */
	public void test4QuitConsoleExecutesTheCommand() {
		logger.info("Testing the QuitConsole command executes the command (not operating) ...");
		
		doReturn(null).when(commandMocked).execute(any(Canvas.class));
		doReturn(null).when(commandMocked).executeGeometry(any(Canvas.class));
		doCallRealMethod().when(commandMocked).acceptParameters(any(Canvas.class), any(String[].class));
		/* We cannot operate on the execution because it kills the console and stop the tests so we mock and we 
		 * operate on the non working version */
		Canvas canvas = (Canvas)DeviceManager.getInstance().getDefaultCanvas();
		String[] parameters = new String[] {};
		assertTrue(commandMocked.acceptParameters(canvas, parameters));
		/* Here we prevent the compile and test console quits*/
		if (commandMocked.isComposedCommand()) {
			assertNull(commandMocked.executeGeometry(canvas));
		}
		else {
			assertNull(commandMocked.execute(canvas));
		}
		
	}

	
	@Test
	/*
	 * Here we test the QuitConsole instance is able to provide any syntax
	 */
	public void test5QuitConsoleProvidesTheSyntax() {
		logger.info("Testing the QuitConsole command provides the syntax ...");
		assertNotNull(commandUnderTest.getSyntax());
		assertTrue(commandUnderTest.getSyntax().length>1);
	}

	@Test
	/*
	 * Here we test the QuitConsole instance do not provide any message in case of correct parameters
	 */
	public void test6QuitConsoleHasNotAnyMessageIfAcceptsTheParameters() {
		logger.info("Testing the QuitConsole command does not provide any message in case of correct parameters ...");
		Canvas canvas = (Canvas)DeviceManager.getInstance().getDefaultCanvas();
		String[] parameters = new String[] {};
		assertTrue(commandUnderTest.acceptParameters(canvas, parameters));
		assertNull(commandUnderTest.getLastMessage());
	}

	/*
	 * Here we test the QuitConsole instance provide any message in case of wrong parameters
	 */
	public void test7QuitConsoleHasAMessageIfNotAcceptsTheParameters() {
		logger.info("Testing the QuitConsole command provides a message in case of wrong parameters ...");
		String[] parameters = new String[] {};
		/* Quit Console command has not parameters to make it not acceptable we need to pass a nulled
		 * Canvas as parameter */
		assertFalse(commandUnderTest.acceptParameters(null, parameters));
		assertNotNull(commandUnderTest.getLastMessage());
	}

}