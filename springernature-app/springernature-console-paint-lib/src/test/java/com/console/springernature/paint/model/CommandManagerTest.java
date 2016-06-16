package com.console.springernature.paint.model;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This test automate class gives evidence of the effective availability of the CommandManager features.
 * @author Fabrizio Torelli
 *
 */
@RunWith(JUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CommandManagerTest {
	private static final Logger logger = LoggerFactory.getLogger("com.console.springernature.paint.model");
	private static final String[] commandList = new String[]{"L", "Q", "B", "R", "C"};
	
	@Test
	/*
	 * Here we test the effective availability of the CommandManager singleton instance
	 */
	public void test0DeviceInstance() {
		logger.info("Testing the CommandManager is available as singleton ...");
		assertNotNull(CommandsManager.getInstance());
	}

	
	@Test
	/*
	 * Here we test the effective availability int the CommandManager singleton instance the commands map
	 */
	public void test1DeviceCorrectCommandsMap() {
		logger.info("Testing the CommandManager is available as singleton ...");
		assertNotNull(CommandsManager.getInstance().getCommandsMap());
		assertEquals(5, CommandsManager.getInstance().getCommandsMap().size());
		for(String command: commandList) {
			assertTrue(Arrays.asList(CommandsManager.getInstance().getCommandsMap().keySet().toArray()).contains(command));
		}
	}
	
	@Test

	/*
	 * Here we test the effective availability int the CommandManager singleton instance the syntax map
	 */
	public void test1DeviceCorrectSyntaxMap() {
		logger.info("Testing the CommandManager is available as singleton ...");
		assertNotNull(CommandsManager.getInstance().getCommandsSyntaxMap());
		assertEquals(5, CommandsManager.getInstance().getCommandsSyntaxMap().size());
		for(String command: commandList) {
			assertTrue(Arrays.asList(CommandsManager.getInstance().getCommandsMap().keySet().toArray()).contains(command));
		}
	}

}