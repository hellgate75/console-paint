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
 * This unit test marks the availability of the Device, the recovery of the default instance and the
 * states implemented in this library.
 * @author Fabrizio Torelli
 *
 */
@RunWith(JUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DeviceTest {
	private static final Logger logger = LoggerFactory.getLogger("com.console.springernature.paint.model");

	private static @Spy Device deviceUnderTest = new Device();
	private static @Spy Canvas mockedCanvas = new Canvas();
	private static  @Mock PrintStream ps = new PrintStream(new ByteArrayOutputStream());
	static {
		/* Here we assign a mocked PrintStream to the system in order to prevent to print to the out */
		System.setOut(ps);
	}
	
	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	/*
	 * Here we test the effective availability of the Device instance
	 */
	public void test0DeviceInstance() {
		logger.info("Testing the Device is created ...");
		assertNotNull(deviceUnderTest);
	}

	@Test
	/*
	 * Here we test the Device create a Canvas dash-board using the command dispatch
	 */
	public void test1DeviceCreatingACanvasDashBoard() {
		logger.info("Testing the Device initially is created with no data ...");
		/* When the Device is created is not initialized on any size and his data */
		deviceUnderTest.addCommandListener(mockedCanvas);
		doCallRealMethod().when(mockedCanvas).assignDevice(any(Device.class));
		mockedCanvas.assignDevice(deviceUnderTest);
		doCallRealMethod().when(mockedCanvas).create(any(Integer.class), any(Integer.class));
		doCallRealMethod().when(mockedCanvas).dispatchCommand(any(Device.class), any(String.class));
		doCallRealMethod().when(mockedCanvas).isCreated();
		doCallRealMethod().when(mockedCanvas).hasADeviceAssigned();
		doCallRealMethod().when(mockedCanvas).getCanvasMatrix();
		doCallRealMethod().when(mockedCanvas).getWidth();
		doCallRealMethod().when(mockedCanvas).getHeight();
		doNothing().when(deviceUnderTest).drawCanvas(any(Canvas.class));
		doNothing().when(deviceUnderTest).listenForANewRequest();
		String command = "C 30 10";
		assertFalse(mockedCanvas.isCreated());
		assertNull(mockedCanvas.getCanvasMatrix());
		deviceUnderTest.dispatchCommand(command.split(" "));
		assertTrue(mockedCanvas.isCreated());
		assertNotNull(mockedCanvas.getCanvasMatrix());
		assertEquals(30, mockedCanvas.getWidth());
		assertEquals(10, mockedCanvas.getHeight());
		assertEquals(10, mockedCanvas.getCanvasMatrix().length);
		assertEquals(30, mockedCanvas.getCanvasMatrix()[0].length);
	}

	@Test
	/*
	 * Here we test the Device draw a Canvas
	 */
	public void test2DeviceDrawACanvas() throws Throwable {
		logger.info("Testing the Device initially is created with no data ...");
		/* When the Device is created is not initialized on any size and his data */
		deviceUnderTest.addCommandListener(mockedCanvas);
		doCallRealMethod().when(mockedCanvas).assignDevice(any(Device.class));
		mockedCanvas.assignDevice(deviceUnderTest);
		doCallRealMethod().when(mockedCanvas).create(any(Integer.class), any(Integer.class));
		doCallRealMethod().when(mockedCanvas).dispatchCommand(any(Device.class), any(String.class));
		doCallRealMethod().when(mockedCanvas).isCreated();
		doCallRealMethod().when(mockedCanvas).hasADeviceAssigned();
		doCallRealMethod().when(mockedCanvas).getCanvasMatrix();
		doCallRealMethod().when(mockedCanvas).getWidth();
		doCallRealMethod().when(mockedCanvas).getHeight();
		doCallRealMethod().when(deviceUnderTest).drawCanvas(any(Canvas.class));
		doNothing().when(deviceUnderTest).listenForANewRequest();
		doNothing().when(ps).println(any(String.class));
		doNothing().when(ps).print(any(String.class));
		String command = "C 50 10";
		deviceUnderTest.dispatchCommand(command.split(" "));
		assertTrue(mockedCanvas.isCreated());
		assertNotNull(mockedCanvas.getCanvasMatrix());
		assertEquals(50, mockedCanvas.getWidth());
		assertEquals(10, mockedCanvas.getHeight());
		assertEquals(10, mockedCanvas.getCanvasMatrix().length);
		assertEquals(50, mockedCanvas.getCanvasMatrix()[0].length);
	}

	
}