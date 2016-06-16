package com.console.springernature.paint.model;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.doNothing;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.junit.runners.MethodSorters;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This test automate class gives evidence of the effective availability of the Canavas features.
 * This unit test marks the availability of the Canvas, the recovery of the default instance and the
 * states implemented in this library.
 * @author Fabrizio Torelli
 *
 */
@RunWith(JUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CanvasTest {
	private static final Logger logger = LoggerFactory.getLogger("com.console.springernature.paint.model");

	private static @Spy Canvas canvasUnderTest = null;
	private static @Spy Device mockedDevice = new Device();
	private static  @Mock PrintStream ps = new PrintStream(new ByteArrayOutputStream());
	static {
		/* Here we assign a mocked PrintStream to the system in order to prevent to print to the out */
		System.setOut(ps);
	}
	
	@BeforeClass
	/*
	 * Here we make an instance of the Canvas behalf the DeviceManager who reads the implementation package
	 */
	public static void initResponse() {
		DeviceManager.getInstance().getDefaultDevice();
		/*
		 * We do not start the device so it doesn't create the canvas we crate it ourself
		 */
		canvasUnderTest = (Canvas)DeviceManager.getInstance().getDefaultCanvas();
	}
	
	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	/*
	 * Here we test the effective availability of the Canvas instance
	 */
	public void test0CanvasInstance() {
		logger.info("Testing the Canvas is created ...");
		assertNotNull(canvasUnderTest);
	}

	@Test
	/*
	 * Here we test the default state of the Canvas after the creation
	 */
	public void test1CanvasAfterTheCreation() {
		logger.info("Testing the Canvas initially is created with no data ...");
		/* When the canvas is created is not initialized on any size and his data */
		assertFalse(canvasUnderTest.isCreated());
		assertNull(canvasUnderTest.getCanvasMatrix());
	}

	@Test
	/*
	 * Here we test the initialization of the Canvas after the creation
	 */
	public void test2CanvasInitialization() {
		logger.info("Testing the Canvas initially is initalized properly ...");
		canvasUnderTest.create(30, 10);
		assertTrue(canvasUnderTest.isCreated());
		assertNotNull(canvasUnderTest.getCanvasMatrix());
		assertEquals(30, canvasUnderTest.getWidth());
		assertEquals(10, canvasUnderTest.getHeight());
		assertEquals(10, canvasUnderTest.getCanvasMatrix().length);
		assertEquals(30, canvasUnderTest.getCanvasMatrix()[0].length);
	}

	@Test
	/*
	 * Here we test the Canvas dash-board's initialization on the device canvas create command execution.
	 */
	public void test3CanvasDashBoardByDeviceCommand() {
		logger.info("Testing the Canvas is initalized by the device under the right command ...");
		Canvas fakeCanvas = new Canvas();
		doCallRealMethod().when(mockedDevice).dispatchCommand(any(String[].class));
		doCallRealMethod().when(mockedDevice).addCommandListener(any(ICommandListener.class));
		doNothing().when(mockedDevice).drawCanvas(any(ICanvas.class));
		doNothing().when(mockedDevice).writeMessage(any(DeviceMessage.class));
		doNothing().when(mockedDevice).initDevice();
		doNothing().when(mockedDevice).start();
		doNothing().when(mockedDevice).waitForExit();
		doNothing().when(mockedDevice).initDeviceCommandMap();
		doNothing().when(mockedDevice).listenForANewRequest();
		String command = "C 30 10";
		/* The canvas' device assign usually is automatic as the assign 
		 * of the canvas as listener to the device, however in this case 
		 * we have created fake elements and we need to assign it automatically*/
		assertFalse(fakeCanvas.hasADeviceAssigned());
		mockedDevice.addCommandListener(fakeCanvas);
		fakeCanvas.assignDevice(mockedDevice);
		mockedDevice.start();
		assertTrue(fakeCanvas.hasADeviceAssigned());
		assertFalse(fakeCanvas.isCreated());
		assertNull(fakeCanvas.getCanvasMatrix());
		mockedDevice.dispatchCommand(command.split(" "));
		assertTrue(fakeCanvas.isCreated());
		assertNotNull(fakeCanvas.getCanvasMatrix());
		assertEquals(30, fakeCanvas.getWidth());
		assertEquals(10, fakeCanvas.getHeight());
		assertEquals(10, fakeCanvas.getCanvasMatrix().length);
		assertEquals(30, fakeCanvas.getCanvasMatrix()[0].length);
	}
	@Test
	/*
	 * Here we test the Canvas dash-board's initialization on direct dispatch call.
	 */
	public void test4CanvasDashBoardDirectDispatchCall() {
		logger.info("Testing the Canvas is initalized by the direct dispatch call ...");
		Canvas fakeCanvas = new Canvas();
		doNothing().when(mockedDevice).drawCanvas(any(ICanvas.class));
		doNothing().when(mockedDevice).writeMessage(any(DeviceMessage.class));
		doNothing().when(mockedDevice).writeMessage(any(DeviceMessage.class));
		doNothing().when(mockedDevice).initDevice();
		doNothing().when(mockedDevice).start();
		doNothing().when(mockedDevice).waitForExit();
		doNothing().when(mockedDevice).initDeviceCommandMap();
		doNothing().when(mockedDevice).listenForANewRequest();
		String command = "C 30 10";
		/* The canvas' device assign usually is automatic as the assign 
		 * of the canvas as listener to the device, however in this case 
		 * we have created fake elements and we need to assign it automatically*/
		assertFalse(fakeCanvas.hasADeviceAssigned());
		fakeCanvas.assignDevice(mockedDevice);
		assertTrue(fakeCanvas.hasADeviceAssigned());
		assertFalse(fakeCanvas.isCreated());
		assertNull(fakeCanvas.getCanvasMatrix());
		/* Here we call the ispatch command signature accepting a device and a string*/
		fakeCanvas.dispatchCommand(mockedDevice, command);
		assertTrue(fakeCanvas.isCreated());
		assertNotNull(fakeCanvas.getCanvasMatrix());
		assertEquals(30, fakeCanvas.getWidth());
		assertEquals(10, fakeCanvas.getHeight());
		assertEquals(10, fakeCanvas.getCanvasMatrix().length);
		assertEquals(30, fakeCanvas.getCanvasMatrix()[0].length);
		command = "C 40 10";
		/* Here we call the ispatch command signature accepting a device and a string array*/
		fakeCanvas.dispatchCommand(mockedDevice, command.split(" "));
		assertTrue(fakeCanvas.isCreated());
		assertNotNull(fakeCanvas.getCanvasMatrix());
		assertEquals(40, fakeCanvas.getWidth());
		assertEquals(10, fakeCanvas.getHeight());
		assertEquals(10, fakeCanvas.getCanvasMatrix().length);
		assertEquals(40, fakeCanvas.getCanvasMatrix()[0].length);
	}
	@Test
	/*
	 * Here we test the Canvas dash-board's initialization on direct create call.
	 */
	public void test5CanvasDashBoardDirectCreateCall() {
		logger.info("Testing the Canvas is initalized by the direct dispatch call ...");
		Canvas fakeCanvas = new Canvas();
		assertFalse(fakeCanvas.isCreated());
		assertNull(fakeCanvas.getCanvasMatrix());
		/* Here we call the canvas create method*/
		fakeCanvas.create(30, 10);
		assertTrue(fakeCanvas.isCreated());
		assertNotNull(fakeCanvas.getCanvasMatrix());
		assertEquals(30, fakeCanvas.getWidth());
		assertEquals(10, fakeCanvas.getHeight());
		assertEquals(10, fakeCanvas.getCanvasMatrix().length);
		assertEquals(30, fakeCanvas.getCanvasMatrix()[0].length);
	}

	@Test
	/*
	 * Here we test the Canvas unimplemented methods.
	 */
	public void test6CanvasUnimplementedMethods() {
		logger.info("Testing the Canvas has some unimplemented features we test here ...");
		Canvas fakeCanvas = new Canvas();
		assertFalse(fakeCanvas.applyCommand(null));
	}

}