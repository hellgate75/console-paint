package com.console.springernature.paint.model.commands;


import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.junit.runners.MethodSorters;
import org.mockito.Spy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.console.springernature.paint.model.Canvas;
import com.console.springernature.paint.model.DeviceManager;

/**
 * This test automate class gives evidence of the effective availability of the CreateLine command features.
 * @author Fabrizio Torelli
 *
 */
@RunWith(JUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CreateLineTest {
	private static final Logger logger = LoggerFactory.getLogger("com.console.springernature.paint.model");
	private static final String commandSigature = "L";
	
	private static @Spy CreateLine commandUnderTest = new CreateLine();
	
	@Test
	/*
	 * Here we test the effective availability of the CreateLine instance
	 */
	public void test0CreateLineInstance() {
		logger.info("Testing the CreateLine command instance availability ...");
		assertNotNull(commandUnderTest);
	}

	@Test
	/*
	 * Here we test the effective symbol of the CreateLine instance
	 */
	public void test1CreateLineSymbol() {
		logger.info("Testing the CreateLine command is available as singleton ...");
		assertEquals(commandSigature, commandUnderTest.getCommandSymbol());
	}

	@Test
	/*
	 * Here we test the effective composite nature of the CreateLine instance
	 */
	public void test2CreateLineSymbol() {
		logger.info("Testing the CreateLine command is not a composite command ...");
		assertFalse(commandUnderTest.isComposedCommand());
	}
	
	@Test
	/*
	 * Here we test the CreateLine instance is able to run the command as simple or composed
	 */
	public void test3CreateLineExecuteCommand() {
		logger.info("Testing the CreateLine command executes the command ...");
		
		Canvas canvas = (Canvas)DeviceManager.getInstance().getDefaultCanvas();
		canvas.create(30, 10);
		String[] parameters = new String[] {"0", "0", "0", "9"};
		assertTrue(commandUnderTest.acceptParameters(canvas, parameters));
		if (commandUnderTest.isComposedCommand()) {
			assertNotNull(commandUnderTest.executeGeometry(canvas));
		}
		else {
			assertNotNull(commandUnderTest.execute(canvas));
		}
		/*
		 * Here we create the expected blank canvas matrix
		 */
		char[][] expectedMatrix = new char[10][];
		for(int i=0;i<expectedMatrix.length;i++) {
			expectedMatrix[i] = new char[30];
			Arrays.fill(expectedMatrix[i], ' ');
			expectedMatrix[i][0] = Canvas.PAINT_BRUSH;
		}
		/* Here we verify the expected matrix with the current one row by row ... */
		char[][] currentMatrix = canvas.getCanvasMatrix();
		for(int i=0;i<expectedMatrix.length;i++) {
			assertArrayEquals(expectedMatrix[i], currentMatrix[i]);
		}

	}

	
	@Test
	/*
	 * Here we test the CreateLine instance is able to provide any syntax
	 */
	public void test4CreateLineProvideSyntax() {
		logger.info("Testing the CreateLine command provides the syntax ...");
		assertNotNull(commandUnderTest.getSyntax());
		assertTrue(commandUnderTest.getSyntax().length>1);
	}

	@Test
	/*
	 * Here we test the CreateLine instance do not provide any message in case of correct parameters
	 */
	public void test5CreateLineHasNotAnyMessageIfAcceptsTheParameters() {
		logger.info("Testing the CreateLine command does not provide any message in case of correct parameters ...");
		Canvas canvas = (Canvas)DeviceManager.getInstance().getDefaultCanvas();
		canvas.create(30, 10);
		canvas.MAX_CONSOLE_WIDTH = 30;
		canvas.MAX_CONSOLE_HEIGHT = 10;
		String[] parameters = new String[] {"0", "0", "0", "9"};
		assertTrue(commandUnderTest.acceptParameters(canvas, parameters));
		assertNull(commandUnderTest.getLastMessage());
	}


	/*
	 * Here we test the CreateLine instance provide any message in case of wrong parameters
	 */
	public void test6CreateLineHasAMessageIfNotAcceptsTheParameters() {
		logger.info("Testing the CreateLine command provides a message in case of wrong parameters ...");
		Canvas canvas = (Canvas)DeviceManager.getInstance().getDefaultCanvas();
		canvas.create(30, 10);
		canvas.MAX_CONSOLE_WIDTH = 30;
		canvas.MAX_CONSOLE_HEIGHT = 10;
		String[] parameters = new String[] {"0", "0"};
		assertFalse(commandUnderTest.acceptParameters(canvas, parameters));
		assertNotNull(commandUnderTest.getLastMessage());
	}

	@Test
	/*
	 * Here we test the CreateLine instance accepts not correct parameters
	 */
	public void test7CreateLineAcceptsCorrectParameters() {
		logger.info("Testing the CreateLine command accepts correct parameters ...");
		Canvas canvas = (Canvas)DeviceManager.getInstance().getDefaultCanvas();
		canvas.create(30, 10);
		canvas.MAX_CONSOLE_WIDTH = 30;
		canvas.MAX_CONSOLE_HEIGHT = 10;
		String[] parameters = new String[] {"0", "0", "0", "9"};
		assertTrue(commandUnderTest.acceptParameters(canvas, parameters));
	}

	@Test
	/*
	 * Here we test the CreateLine instance does not accept not correct parameters
	 */
	public void test8CreateLineDoesNotAcceptWrongParameters() {
		logger.info("Testing the CreateLine command does not accept wrong parameters ...");
		Canvas canvas = (Canvas)DeviceManager.getInstance().getDefaultCanvas();
		canvas.create(30, 10);
		canvas.MAX_CONSOLE_WIDTH = 30;
		canvas.MAX_CONSOLE_HEIGHT = 10;
		String[] parameters = new String[] {"0", "0", "0"};
		assertFalse(commandUnderTest.acceptParameters(canvas, parameters));
	}

	@Test
	/*
	 * Here we test the CreateLine instance do not accepts unbounded parameters
	 */
	public void test9CreateLineDoesNotAcceptUnboundedParameters() {
		logger.info("Testing the CreateLine command does not accept unbounded parameters ...");
		Canvas canvas = (Canvas)DeviceManager.getInstance().getDefaultCanvas();
		canvas.create(30, 10);
		canvas.MAX_CONSOLE_WIDTH = 30;
		canvas.MAX_CONSOLE_HEIGHT = 10;
		String[] parameters = new String[] {"0", "0", "0", "30"};
		assertFalse(commandUnderTest.acceptParameters(canvas, parameters));
	}

}