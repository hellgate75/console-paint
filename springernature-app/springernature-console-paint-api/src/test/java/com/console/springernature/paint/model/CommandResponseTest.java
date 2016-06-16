package com.console.springernature.paint.model;

import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

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
public class CommandResponseTest {
	private static final Logger logger = LoggerFactory.getLogger("com.console.springernature.paint.model");

	private static CommandResponse responseUnderTest1 = null;
	private static CommandResponse responseUnderTest2 = null;
	private static CommandResponse responseUnderTest3 = null;
	
	@BeforeClass
	public static void initResponse() {
		DeviceMessage testMessage = new DeviceMessage(ErrorCodeEnum.CANVAS_UNAVAILABLE, DeviceMessageTypeEnum.ERROR, "Unable to find the canvas");
		responseUnderTest1 = new CommandResponse(testMessage);
		ICommand testCommandSimple = new ICommand() {
			
			@Override
			public boolean isComposedCommand() {
				return false;
			}
			
			@Override
			public String[] getSyntax() {
				return new String[0];
			}
			
			@Override
			public DeviceMessage getLastMessage() {
				return null;
			}
			
			@Override
			public String getCommandSymbol() {
				return "Simple";
			}
			
			@Override
			public GeneralPath executeGeometry(ICanvas canvas) {
				return null;
			}
			
			@Override
			public List<Point2D> execute(ICanvas canvas) {
				return new ArrayList<Point2D>(0);
			}
			
			@Override
			public boolean acceptParameters(ICanvas canvas, String[] parameters) {
				// TODO Auto-generated method stub
				return false;
			}
		};
		responseUnderTest2 = new CommandResponse(testCommandSimple);
		ICommand testCommandComplex = new ICommand() {
			
			@Override
			public boolean isComposedCommand() {
				return true;
			}
			
			@Override
			public String[] getSyntax() {
				return new String[0];
			}
			
			@Override
			public DeviceMessage getLastMessage() {
				return null;
			}
			
			@Override
			public String getCommandSymbol() {
				return "Complex";
			}
			
			@Override
			public GeneralPath executeGeometry(ICanvas canvas) {
				return new GeneralPath();
			}
			
			@Override
			public List<Point2D> execute(ICanvas canvas) {
				return null;
			}
			
			@Override
			public boolean acceptParameters(ICanvas canvas, String[] parameters) {
				// TODO Auto-generated method stub
				return false;
			}
		};
		responseUnderTest3 = new CommandResponse(testCommandComplex);
	}
	
	@Test
	/*
	 * Here we test the availability the CommandResponse as Error Message.
	 */
	public void test0ErrorMessageInstance() {
		logger.info("Testing the Error Device Message Response ...");
		assertNotNull(responseUnderTest1);
		assertNull(responseUnderTest1.getCommand());
		assertNotNull(responseUnderTest1.getMessage());
		assertFalse(responseUnderTest1.isCorrect());
	}

	/*
	 * Here we test the availability the CommandResponse as not composed command's one.
	 */
	@Test
	public void test1SimpleCommandMessageInstance() {
		logger.info("Testing the Simple Command Response ...");
		assertNotNull(responseUnderTest2);
		assertNull(responseUnderTest2.getMessage());
		assertNotNull(responseUnderTest2.getCommand());
		assertTrue(responseUnderTest2.isCorrect());
	}

	/*
	 * Here we test the availability the CommandResponse as composed command's one.
	 */
	@Test
	public void test2SimpleCommandMessageInstance() {
		logger.info("Testing the Complex Command Response ...");
		assertNotNull(responseUnderTest3);
		assertNull(responseUnderTest3.getMessage());
		assertNotNull(responseUnderTest3.getCommand());
		assertTrue(responseUnderTest3.isCorrect());
	}
}
