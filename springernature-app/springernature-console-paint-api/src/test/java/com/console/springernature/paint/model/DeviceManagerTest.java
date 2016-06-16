package com.console.springernature.paint.model;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.junit.runners.MethodSorters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.*;

/**
 * This test automate class gives evidence of the effective availability of the layout messaging of the device.
 * We now test the most used message types.
 * @author Fabrizio Torelli
 *
 */
@RunWith(JUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DeviceManagerTest {
	private static final Logger logger = LoggerFactory.getLogger("com.console.springernature.paint.model");

	
	@Test
	/*
	 * Here we test the effective availability of the DeviceManager instance
	 */
	public void test0DeviceManageSingletonIsAvailable() {
		logger.info("Testing the Device Manager instance is available ...");
		assertNotNull(DeviceManager.getInstance());
	}

	@Test
	/*
	 * Here we test the effective unavailability of the implementation during the request of the default device
	 */
	public void test1DeviceManageCannotFindTheImplementationDevice() {
		logger.info("Testing the Device Manager that cannot find the default implementation device ...");
		logger.warn("This test will log an exception due to the missing of the implmentation library!!");
		assertNotNull(DeviceManager.getInstance());
		assertNull(DeviceManager.getInstance().getDefaultDevice());
	}

	@Test
	/*
	 * Here we test the effective unavailability of the implementation during the request of the default canvas
	 */
	public void test2DeviceManageCannotFindTheImplementationCanvas() {
		logger.info("Testing the Device Manager that cannot find the default implementation canvas ...");
		logger.warn("This test will log an exception due to the missing of the implmentation library!!");
		assertNotNull(DeviceManager.getInstance());
		assertNull(DeviceManager.getInstance().getDefaultCanvas());
	}

	@Test
	/*
	 * Here we test the effective availability of a fake implementation during the request of a named device
	 */
	public void test3DeviceManageCanFindAFakeImplementationDevice() {
		logger.info("Testing the Device Manager that cannot find a fake default implementation device ...");
		assertNotNull(DeviceManager.getInstance());
		assertNotNull(DeviceManager.getInstance().getNamedDevice("test.FakeDevice"));
	}

	@Test
	/*
	 * Here we test the effective availability of a fake implementation during the request of a named canvas
	 */
	public void test4DeviceManageCanFindAFakeImplementationCanvas() {
		logger.info("Testing the Device Manager that cannot find a fake default implementation canvas ...");
		assertNotNull(DeviceManager.getInstance());
		assertNotNull(DeviceManager.getInstance().getNamedCanvas("test.FakeCanvas", DeviceManager.getInstance().getNamedDevice("test.FakeDevice")));
	}
	
}
