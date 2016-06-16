package com.console.springernature.paint.model.commands;


import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

import java.util.Arrays;

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
import com.console.springernature.paint.model.Device;
import com.console.springernature.paint.model.DeviceManager;

/**
 * This test automate class gives evidence of the effective availability of the BucketFill command features.
 * @author Fabrizio Torelli
 *
 */
@RunWith(JUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BucketFillTest {
	private static final Logger logger = LoggerFactory.getLogger("com.console.springernature.paint.model");
	private static final String commandSigature = "B";
	private static @Spy Device mockedDevice = Mockito.mock(Device.class);
	private static final char BUCKETFILL_CHAR='o';
	
	private static @Spy BucketFill commandUnderTest = new BucketFill();
	
	@Test
	/*
	 * Here we test the effective availability of the BucketFill instance
	 */
	public void test0BucketFillInstance() {
		logger.info("Testing the BucketFill command instance availability ...");
		assertNotNull(commandUnderTest);
	}

	@Test
	/*
	 * Here we test the effective symbol of the BucketFill instance
	 */
	public void test1BucketFillSymbol() {
		logger.info("Testing the BucketFill command is available as singleton ...");
		assertEquals(commandSigature, commandUnderTest.getCommandSymbol());
	}

	@Test
	/*
	 * Here we test the effective composite nature of the BucketFill instance
	 */
	public void test2BucketFillSymbol() {
		logger.info("Testing the BucketFill command is not a composite command ...");
		assertFalse(commandUnderTest.isComposedCommand());
	}
	
	@Test
	/*
	 * Here we test the BucketFill instance is able to run the command as simple or composed
	 */
	public void test3BucketFillExecuteCommand() {
		logger.info("Testing the BucketFill command executes the command ...");
		
		Canvas canvas = (Canvas)DeviceManager.getInstance().getDefaultCanvas();
		canvas.create(30, 10);
		doNothing().when(mockedDevice).drawCanvas(any(Canvas.class));
		canvas.assignDevice(mockedDevice);
		canvas.dispatchCommand(mockedDevice, "L 5 0 5 5");
		canvas.dispatchCommand(mockedDevice, "L 0 5 5 5");
		String[] parameters = new String[] {"0", "0", ""+BUCKETFILL_CHAR};
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
			if (i==5) {
				for (int y=0; y<6; y++) {
					expectedMatrix[i][y] = Canvas.PAINT_BRUSH;
				}
			}
			else if (i>=0 && i<5){
				expectedMatrix[i][0] = BUCKETFILL_CHAR;
				expectedMatrix[i][1] = BUCKETFILL_CHAR;
				expectedMatrix[i][2] = BUCKETFILL_CHAR;
				expectedMatrix[i][3] = BUCKETFILL_CHAR;
				expectedMatrix[i][4] = BUCKETFILL_CHAR;
				expectedMatrix[i][5] = Canvas.PAINT_BRUSH;
			}
		}
		/* Here we verify the expected matrix with the current one row by row ... */
		char[][] currentMatrix = canvas.getCanvasMatrix();
		for(int i=0;i<expectedMatrix.length;i++) {
			assertArrayEquals(expectedMatrix[i], currentMatrix[i]);
		}

	}

	@Test
	/*
	 * Here we test the BucketFill instance is able to run the command as simple or composed
	 */
	public void test4BucketFillExecuteDisjunctedLinesCommand() {
		logger.info("Testing the BucketFill command executes the command ...");
		
		Canvas canvas = (Canvas)DeviceManager.getInstance().getDefaultCanvas();
		canvas.create(30, 10);
		doNothing().when(mockedDevice).drawCanvas(any(Canvas.class));
		canvas.assignDevice(mockedDevice);
		canvas.dispatchCommand(mockedDevice, "L 5 0 5 4");
		canvas.dispatchCommand(mockedDevice, "L 0 5 4 5");
		String[] parameters = new String[] {"0", "0", ""+BUCKETFILL_CHAR};
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
			Arrays.fill(expectedMatrix[i], BUCKETFILL_CHAR);
			if (i==5) {
				for (int y=0; y<5; y++) {
					expectedMatrix[i][y] = Canvas.PAINT_BRUSH;
				}
			}
			else if (i>=0 && i<5){
				expectedMatrix[i][0] = BUCKETFILL_CHAR;
				expectedMatrix[i][1] = BUCKETFILL_CHAR;
				expectedMatrix[i][2] = BUCKETFILL_CHAR;
				expectedMatrix[i][3] = BUCKETFILL_CHAR;
				expectedMatrix[i][4] = BUCKETFILL_CHAR;
				expectedMatrix[i][5] = Canvas.PAINT_BRUSH;
			}
		}
		/* Here we verify the expected matrix with the current one row by row ... */
		char[][] currentMatrix = canvas.getCanvasMatrix();
		for(int i=0;i<expectedMatrix.length;i++) {
			assertArrayEquals(expectedMatrix[i], currentMatrix[i]);
		}

	}

	@Test
	/*
	 * Here we test the BucketFill instance is able to provide any syntax
	 */
	public void test4BucketFillProvideSyntax() {
		logger.info("Testing the BucketFill command provides the syntax ...");
		assertNotNull(commandUnderTest.getSyntax());
		assertTrue(commandUnderTest.getSyntax().length>1);
	}

	@Test
	/*
	 * Here we test the BucketFill instance do not provide any message in case of correct parameters
	 */
	public void test5BucketFillHasNotAnyMessageIfAcceptsTheParameters() {
		logger.info("Testing the BucketFill command does not provide any message in case of correct parameters ...");
		Canvas canvas = (Canvas)DeviceManager.getInstance().getDefaultCanvas();
		canvas.create(30, 10);
		canvas.MAX_CONSOLE_WIDTH = 30;
		canvas.MAX_CONSOLE_HEIGHT = 10;
		String[] parameters = new String[] {"0", "0", "o"};
		assertTrue(commandUnderTest.acceptParameters(canvas, parameters));
		assertNull(commandUnderTest.getLastMessage());
	}


	/*
	 * Here we test the BucketFill instance provide any message in case of wrong parameters
	 */
	public void test6BucketFillHasAMessageIfNotAcceptsTheParameters() {
		logger.info("Testing the BucketFill command provides a message in case of wrong parameters ...");
		Canvas canvas = (Canvas)DeviceManager.getInstance().getDefaultCanvas();
		canvas.create(30, 10);
		canvas.MAX_CONSOLE_WIDTH = 30;
		canvas.MAX_CONSOLE_HEIGHT = 10;
		String[] parameters = new String[] {"0"};
		assertFalse(commandUnderTest.acceptParameters(canvas, parameters));
		assertNotNull(commandUnderTest.getLastMessage());
	}

	@Test
	/*
	 * Here we test the BucketFill instance accepts not correct parameters
	 */
	public void test7BucketFillAcceptsCorrectParameters() {
		logger.info("Testing the BucketFill command accepts correct parameters ...");
		Canvas canvas = (Canvas)DeviceManager.getInstance().getDefaultCanvas();
		canvas.create(30, 10);
		canvas.MAX_CONSOLE_WIDTH = 30;
		canvas.MAX_CONSOLE_HEIGHT = 10;
		String[] parameters = new String[] {"0", "0", "o"};
		assertTrue(commandUnderTest.acceptParameters(canvas, parameters));
	}

	@Test
	/*
	 * Here we test the BucketFill instance does not accept not correct parameters
	 */
	public void test8BucketFillDoesNotAcceptWrongParameters() {
		logger.info("Testing the BucketFill command does not accept wrong parameters ...");
		Canvas canvas = (Canvas)DeviceManager.getInstance().getDefaultCanvas();
		canvas.create(30, 10);
		canvas.MAX_CONSOLE_WIDTH = 30;
		canvas.MAX_CONSOLE_HEIGHT = 10;
		String[] parameters = new String[] {"0", "0"};
		assertFalse(commandUnderTest.acceptParameters(canvas, parameters));
	}

	@Test
	/*
	 * Here we test the BucketFill instance do not accepts unbounded parameters
	 */
	public void test9BucketFillDoesNotAcceptUnboundedParameters() {
		logger.info("Testing the BucketFill command does not accept unbounded parameters ...");
		Canvas canvas = (Canvas)DeviceManager.getInstance().getDefaultCanvas();
		canvas.create(30, 10);
		canvas.MAX_CONSOLE_WIDTH = 30;
		canvas.MAX_CONSOLE_HEIGHT = 10;
		String[] parameters = new String[] {"0", "30", "o"};
		assertFalse(commandUnderTest.acceptParameters(canvas, parameters));
	}

}