package com.console.springernature.paint.model.commands;


import static org.junit.Assert.assertArrayEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.doNothing;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.junit.runners.MethodSorters;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.console.springernature.paint.model.Canvas;
import com.console.springernature.paint.model.Device;
import com.console.springernature.paint.model.DeviceMessage;
import com.console.springernature.paint.model.ICanvas;

/**
 * This test automate class gives evidence of the effective command execution and the expected response on the screen.
 * @author Fabrizio Torelli
 *
 */
@RunWith(JUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class GeometryHelperTest {
	private static final Logger logger = LoggerFactory.getLogger("com.console.springernature.paint.model");
	private static  @Mock PrintStream ps = new PrintStream(new ByteArrayOutputStream());
	
	static {
		/* Here we assign a mocked PrintStream to the system in order to prevent to print to the out */
		System.setOut(ps);
	}
	private static @Spy Device device = Mockito.mock(Device.class);
	private static Canvas canvas = null;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		doNothing().when(device).drawCanvas(any(ICanvas.class));
		doNothing().when(device).start();
		doNothing().when(device).writeMessage(any(DeviceMessage.class));
		doNothing().when(device).listenForANewRequest();
		doCallRealMethod().when(device).addCommandListener(any(Canvas.class));
		doCallRealMethod().when(device).resetCommandListeners();
		device.resetCommandListeners();
		canvas = new Canvas();
		canvas.assignDevice(device);
		canvas.MAX_CONSOLE_WIDTH=50;
		canvas.MAX_CONSOLE_HEIGHT=20;
		device.addCommandListener(canvas);
	}

	@Test
	/*
	 * Here we test the Canvas dash-board for the Canvas commands
	 */
	public void test0GeometryHelperGraphicalCanvasCommands() {
		logger.info("Testing the Canvas response for the Canvas Commmands ...");
		executeAFullTestForTheResource("complete_canvas.txt");
	}

	@Test
	/*
	 * Here we test the Canvas dash-board for the Line commands
	 */
	public void test1GeometryHelperGraphicalLineCommands() {
		logger.info("Testing the Canvas response for the Line Commmands ...");
		executeAFullTestForTheResource("complete_lines.txt");
	}

	@Test
	/*
	 * Here we test the Canvas dash-board for the Rectangle commands
	 */
	public void test2GeometryHelperGraphicalRectangleCommands() {
		logger.info("Testing the Canvas response for the Rectangle Commmands ...");
		executeAFullTestForTheResource("complete_rectangle.txt");
	}

	@Test
	/*
	 * Here we test the Canvas dash-board for the Bucket Fill commands
	 */
	public void test3GeometryHelperGraphicalBucketFillCommands() {
		logger.info("Testing the Canvas response for the Bucket Fill Commmands ...");
		executeAFullTestForTheResource("simple_bucketfill.txt");
	}
	
	@Test
	/*
	 * Here we test the Canvas dash-board for the Composite Bucket Fill commands
	 */
	public void test4GeometryHelperGraphicalCompositeBucketFillCommands() {
		logger.info("Testing the Canvas response for the Composite Bucket Fill Commmands ...");
		executeAFullTestForTheResource("composite_bucketfill.txt");
	}
	
	@Test
	/*
	 * Here we test the Canvas dash-board for the Complex Bucket Fill commands
	 */
	public void test5GeometryHelperGraphicalComplexBucketFillCommands() {
		logger.info("Testing the Canvas response for the Complex Bucket Fill Commmands ...");
		executeAFullTestForTheResource("complex_bucketfill.txt");
	}
	
	@Test
	/*
	 * Here we test the Canvas dash-board for the Unclosed Lines Bucket Fill commands
	 */
	public void test6GeometryHelperGraphicalUnclosedLinesBucketFillCommands() {
		logger.info("Testing the Canvas response for the Unclosed Lines Bucket Fill Commmands ...");
		executeAFullTestForTheResource("disjunction_bucketfill.txt");
	}
	
	private static void executeAFullTestForTheResource(String resourceName) {
		List<GeometryHelperTest.MockedTestCase> testCases = loadTestCases(resourceName);
		logger.info(resourceName + " : test cases : " + testCases.size());
		
		for(GeometryHelperTest.MockedTestCase singleCommandCase: testCases) {
			logger.info(resourceName + " : command : " + singleCommandCase.getCommand());
			canvas.dispatchCommand(device, singleCommandCase.getCommand());
			logger.info("");
			char[][] expectedMatrix = singleCommandCase.getMatrix();
			char[][] currentMatrix = canvas.getCanvasMatrix();
			for(int i=0; i<currentMatrix.length; i++) {
				logger.info("canvas line : " + Arrays.toString(currentMatrix[i]));
				assertArrayEquals(expectedMatrix[i], currentMatrix[i]);
			}
		}
	}
	
	private static final List<GeometryHelperTest.MockedTestCase> loadTestCases(String resorceName) {
		List<GeometryHelperTest.MockedTestCase> testCases = new ArrayList<GeometryHelperTest.MockedTestCase>(0);
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream("src/test/resources/"+resorceName)));
			String command = null;
			List<String> resultList = new ArrayList<String>(0);
			while(br.ready()) {
				String line = br.readLine();
				if (command == null) {
					command = line.trim();
				}
				else if (!line.equals("")){
					resultList.add(line.trim());
				}
				else {
					testCases.add(new GeometryHelperTest.MockedTestCase(command, resultList));
					command= null;
					resultList.clear();
				}
			}
		} catch (IOException e) {
			logger.error("I/O Operation: Unable to read the resource : " + resorceName, e);
		} catch (Throwable e) {
			logger.error("Generic Operation: Unable to read the resource : " + resorceName, e);
		}
		finally {
			try {
				br.close();
			} catch (Throwable e) {
			}
		}
		return testCases;
	}

	protected static class MockedTestCase {
		private String command;
		private char[][] matrix;
		public MockedTestCase(String command, char[][] matrix) {
			super();
			this.command = command;
			this.matrix = matrix;
		}
		public MockedTestCase(String command, List<String> matrixLines) {
			super();
			this.command = command;
			this.matrix = new char[matrixLines.size()-2][];
			for(int i=1; i<matrixLines.size()-1; i++) {
				String line = matrixLines.get(i);
				line = line.substring(1, line.length()-1);
				System.out.println(line);
				this.matrix[i-1] = line.toCharArray();
			}
		}
		/**
		 * @return the command
		 */
		public String getCommand() {
			return command;
		}
		/**
		 * @return the matrix
		 */
		public char[][] getMatrix() {
			return matrix;
		}
		
	}

}