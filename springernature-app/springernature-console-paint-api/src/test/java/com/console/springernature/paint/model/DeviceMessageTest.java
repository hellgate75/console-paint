package com.console.springernature.paint.model;

import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.junit.runners.MethodSorters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.*;

/**
 * This test automate class gives evidence of the effective availability of the messaging bean of the device.
 * We are not yet ready to operte and test the implementation library, however we create fake elements.
 * @author Fabrizio Torelli
 *
 */
@RunWith(JUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DeviceMessageTest {
	private static final Logger logger = LoggerFactory.getLogger("com.console.springernature.paint.model");

	private static DeviceMessage messageUnderTest1 = null;
	private static DeviceMessage messageUnderTest2 = null;
	private static DeviceMessage messageUnderTest3 = null;
	private static DeviceMessage messageUnderTest4 = null;
	private static DeviceMessage messageUnderTest5 = null;
	
	private static String[] syntax = new String[]{"P x1 x2", "Draw a point of coordinates (x1, x2)"};
	private static String message1 = "Unable to find the Canvas";
	private static String message2 = "This is a message";
	private static String message3 = "Unable to find the Device";
	private static String message4 = "Command has overcome the Canvas boundaries";
	private static String message5 = "Another Information";
	
	@BeforeClass
	public static void initResponse() {
		messageUnderTest1 = new DeviceMessage(ErrorCodeEnum.CANVAS_UNAVAILABLE, DeviceMessageTypeEnum.ERROR, message1);
		messageUnderTest2 = new DeviceMessage(null, DeviceMessageTypeEnum.INFO, message2, syntax);
		messageUnderTest3 = new DeviceMessage(ErrorCodeEnum.DEVICE_UNAVAILABLE, DeviceMessageTypeEnum.ERROR, message3, syntax);
		messageUnderTest4 = new DeviceMessage(ErrorCodeEnum.COMMAND_OUT_OF_BOUNDS, DeviceMessageTypeEnum.WARNING, message4, syntax);
		messageUnderTest5 = new DeviceMessage(null, DeviceMessageTypeEnum.INFO, message5, syntax);
	}
	
	@Test
	/*
	 * Here we test availability of the DeviceMessage without syntax message. It should have an empty message syntax, but not nulled.
	 */
	public void test0DeviceMessageWithoutSyntax() {
		logger.info("Testing the Device Message without syntax ...");
		assertNotNull(messageUnderTest1);
		assertEquals(ErrorCodeEnum.CANVAS_UNAVAILABLE, messageUnderTest1.getErrorCode());
		assertEquals(DeviceMessageTypeEnum.ERROR, messageUnderTest1.getType());
		assertEquals(message1, messageUnderTest1.getMessage());
		assertNotNull(messageUnderTest1.getSyntax());
		assertEquals(0, messageUnderTest1.getSyntax().length);
	}

	@Test
	/*
	 * Here we test availability of the DeviceMessage without error code (Information messafe type). It should have a a nulled message error code.
	 */
	public void test1DeviceMessageWithoutErrorCode() {
		logger.info("Testing the Device Message without error code ...");
		assertNotNull(messageUnderTest2);
		assertNull(messageUnderTest2.getErrorCode());
		assertEquals(DeviceMessageTypeEnum.INFO, messageUnderTest2.getType());
		assertEquals(message2, messageUnderTest2.getMessage());
		assertNotNull(messageUnderTest2.getSyntax());
		assertEquals(2, messageUnderTest2.getSyntax().length);
		assertEquals(messageUnderTest2.getSyntax()[0], syntax[0]);
		assertEquals(messageUnderTest2.getSyntax()[1], syntax[1]);
	}

	@Test
	/*
	 * Here we test availability of the DeviceMessage in a kind of Error Message template.
	 */
	public void test2DeviceMessageErrorType() {
		logger.info("Testing the Device Message : Error Type ...");
		assertNotNull(messageUnderTest3);
		assertEquals(ErrorCodeEnum.DEVICE_UNAVAILABLE, messageUnderTest3.getErrorCode());
		assertEquals(DeviceMessageTypeEnum.ERROR, messageUnderTest3.getType());
		assertEquals(message3, messageUnderTest3.getMessage());
		assertNotNull(messageUnderTest3.getSyntax());
		assertEquals(2, messageUnderTest3.getSyntax().length);
		assertEquals(messageUnderTest3.getSyntax()[0], syntax[0]);
		assertEquals(messageUnderTest3.getSyntax()[1], syntax[1]);
	}

	@Test
	/*
	 * Here we test availability of the DeviceMessage in a kind of Warning Message template.
	 */
	public void test3DeviceMessageWarningType() {
		logger.info("Testing the Device Message : Warning Type ...");
		assertNotNull(messageUnderTest4);
		assertEquals(ErrorCodeEnum.COMMAND_OUT_OF_BOUNDS, messageUnderTest4.getErrorCode());
		assertEquals(DeviceMessageTypeEnum.WARNING, messageUnderTest4.getType());
		assertEquals(message4, messageUnderTest4.getMessage());
		assertNotNull(messageUnderTest4.getSyntax());
		assertEquals(2, messageUnderTest4.getSyntax().length);
		assertEquals(messageUnderTest4.getSyntax()[0], syntax[0]);
		assertEquals(messageUnderTest4.getSyntax()[1], syntax[1]);
	}


	@Test
	/*
	 * Here we test availability of the DeviceMessage in a kind of Error Informative template. It should have a a nulled message error code.
	 */
	public void test4DeviceMessageInformationType() {
		logger.info("Testing the Device Message : Information Type ...");
		assertNotNull(messageUnderTest5);
		assertNull(messageUnderTest5.getErrorCode());
		assertEquals(DeviceMessageTypeEnum.INFO, messageUnderTest5.getType());
		assertEquals(message5, messageUnderTest5.getMessage());
		assertNotNull(messageUnderTest5.getSyntax());
		assertEquals(2, messageUnderTest5.getSyntax().length);
		assertEquals(messageUnderTest5.getSyntax()[0], syntax[0]);
		assertEquals(messageUnderTest5.getSyntax()[1], syntax[1]);
	}

}
