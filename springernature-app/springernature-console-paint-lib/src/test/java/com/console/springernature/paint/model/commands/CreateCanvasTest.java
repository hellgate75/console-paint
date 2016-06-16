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
 * This test automate class gives evidence of the effective availability of the CreateCanvas command features.
 * @author Fabrizio Torelli
 *
 */
@RunWith(JUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CreateCanvasTest {
	private static final Logger logger = LoggerFactory.getLogger("com.console.springernature.paint.model");
	private static final String commandSigature = "C";
	
	private static @Spy CreateCanvas commandUnderTest = new CreateCanvas();
	
	@Test
	/*
	 * Here we test the effective availability of the CreateCanvas instance
	 */
	public void test0CreateCanvasInstance() {
		logger.info("Testing the CreateCanvas command instance availability ...");
		assertNotNull(commandUnderTest);
	}

	@Test
	/*
	 * Here we test the effective symbol of the CreateCanvas instance
	 */
	public void test1CreateCanvasSymbol() {
		logger.info("Testing the CreateCanvas command is available as singleton ...");
		assertEquals(commandSigature, commandUnderTest.getCommandSymbol());
	}

	@Test
	/*
	 * Here we test the effective composite nature of the CreateCanvas instance
	 */
	public void test2CreateCanvasSymbol() {
		logger.info("Testing the CreateCanvas command is not a composite command ...");
		assertFalse(commandUnderTest.isComposedCommand());
	}
	
	@Test
	/*
	 * Here we test the CreateCanvas instance is able to run the command as simple or composed
	 */
	public void test3CreateCanvasExecuteCommand() {
		logger.info("Testing the CreateCanvas command executes the command ...");
		
		Canvas canvas = (Canvas)DeviceManager.getInstance().getDefaultCanvas();
		String[] parameters = new String[] {""+(canvas.MAX_CONSOLE_WIDTH-2), ""+(canvas.MAX_CONSOLE_HEIGHT-4)};
		assertTrue(commandUnderTest.acceptParameters(canvas, parameters));
		if (commandUnderTest.isComposedCommand()) {
			assertNotNull(commandUnderTest.executeGeometry(canvas));
		}
		else {
			assertNotNull(commandUnderTest.execute(canvas));
		}
		assertTrue(canvas.isCreated());
		assertNotNull(canvas.getCanvasMatrix());
		assertEquals(canvas.MAX_CONSOLE_WIDTH-2, canvas.getWidth());
		assertEquals(canvas.MAX_CONSOLE_HEIGHT-4, canvas.getHeight());
		/*
		 * Here we create the expected blank canvas matrix
		 */
		char[][] expectedMatrix = new char[canvas.MAX_CONSOLE_HEIGHT-4][];
		for(int i=0;i<expectedMatrix.length;i++) {
			expectedMatrix[i] = new char[canvas.MAX_CONSOLE_WIDTH-2];
			Arrays.fill(expectedMatrix[i], ' ');
		}
		/* Here we verify the expected matrix with the current one row by row ... */
		char[][] currentMatrix = canvas.getCanvasMatrix();
		for(int i=0;i<expectedMatrix.length;i++) {
			assertArrayEquals(expectedMatrix[i], currentMatrix[i]);
		}

	}

	
	@Test
	/*
	 * Here we test the CreateCanvas instance is able to provide any syntax
	 */
	public void test4CreateCanvasProvideSyntax() {
		logger.info("Testing the CreateCanvas command provides the syntax ...");
		assertNotNull(commandUnderTest.getSyntax());
		assertTrue(commandUnderTest.getSyntax().length>1);
	}

	@Test
	/*
	 * Here we test the CreateCanvas instance do not provide any message in case of correct parameters
	 */
	public void test5CreateCanvasHasNotAnyMessageIfAcceptsTheParameters() {
		logger.info("Testing the CreateCanvas command does not provide any message in case of correct parameters ...");
		Canvas canvas = (Canvas)DeviceManager.getInstance().getDefaultCanvas();
		String[] parameters = new String[] {""+(canvas.MAX_CONSOLE_WIDTH-2), ""+(canvas.MAX_CONSOLE_HEIGHT-4)};
		assertTrue(commandUnderTest.acceptParameters(canvas, parameters));
		assertNull(commandUnderTest.getLastMessage());
	}


	/*
	 * Here we test the CreateCanvas instance provide any message in case of wrong parameters
	 */
	public void test6CreateCanvasHasAMessageIfNotAcceptsTheParameters() {
		logger.info("Testing the CreateCanvas command provides a message in case of wrong parameters ...");
		Canvas canvas = (Canvas)DeviceManager.getInstance().getDefaultCanvas();
		String[] parameters = new String[] {""+(canvas.MAX_CONSOLE_WIDTH-2)};
		assertFalse(commandUnderTest.acceptParameters(canvas, parameters));
		assertNotNull(commandUnderTest.getLastMessage());
	}

	@Test
	/*
	 * Here we test the CreateCanvas instance accepts not correct parameters
	 */
	public void test7CreateCanvasAcceptsCorrectParameters() {
		logger.info("Testing the CreateCanvas command accepts correct parameters ...");
		Canvas canvas = (Canvas)DeviceManager.getInstance().getDefaultCanvas();
		String[] parameters = new String[] {""+(canvas.MAX_CONSOLE_WIDTH-2), ""+(canvas.MAX_CONSOLE_HEIGHT-4)};
		assertTrue(commandUnderTest.acceptParameters(canvas, parameters));
	}

	@Test
	/*
	 * Here we test the CreateCanvas instance does not accept not correct parameters
	 */
	public void test8CreateCanvasDoesNotAcceptWrongParameters() {
		logger.info("Testing the CreateCanvas command does not accept wrong parameters ...");
		Canvas canvas = (Canvas)DeviceManager.getInstance().getDefaultCanvas();
		String[] parameters = new String[] {""+(canvas.MAX_CONSOLE_WIDTH-2)};
		assertFalse(commandUnderTest.acceptParameters(canvas, parameters));
	}

	@Test
	/*
	 * Here we test the CreateCanvas instance do not accepts unbounded parameters
	 */
	public void test9CreateCanvasDoesNotAcceptUnboundedParameters() {
		logger.info("Testing the CreateCanvas command does not accept unbounded parameters ...");
		Canvas canvas = (Canvas)DeviceManager.getInstance().getDefaultCanvas();
		String[] parameters = new String[] {""+(canvas.MAX_CONSOLE_WIDTH+1), ""+(canvas.MAX_CONSOLE_HEIGHT+1)};
		assertFalse(commandUnderTest.acceptParameters(canvas, parameters));
	}

}