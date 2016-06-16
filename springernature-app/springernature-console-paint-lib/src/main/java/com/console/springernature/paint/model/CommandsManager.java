/**
 * 
 */
package com.console.springernature.paint.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Singleton class that lookup the commands and provide the information to the environment.
 * @author Fabrizio Torelli
 *
 */
public class CommandsManager {

	private static final Logger logger = LoggerFactory.getLogger("com.console.springernature.paint.model");
	private static CommandsManager instance = null;
	
	private Map<String, Class<? extends ICommand>> commandsMap = new HashMap<String, Class<? extends ICommand>>(0);
	private Map<String, String[]> commandsSyntaxMap = new HashMap<String, String[]>(0);
	
	/*
	 * The DeviceManager is a singleton and the Constructor has private visibility. No instance is allowed.
	 */
	private CommandsManager() {
		super();
		commandsMap = lookupInPackage("com.console.springernature.paint.model.commands");
		commandsSyntaxMap = lookupSyntaxInPackage("com.console.springernature.paint.model.commands");
	}
	
	/*
	 * Use reflections to instantiate and run a lookup of the command in the reference package
	 */
	private static Map<String, Class<? extends ICommand>> lookupInPackage(String packageName) {
		HashMap<String, Class<? extends ICommand>> mapOfCommands = new HashMap<String, Class<? extends ICommand>>(0);
		try {
			Reflections reflections = new Reflections(packageName);
			Set<Class<? extends ICommand>> classes = reflections.getSubTypesOf(ICommand.class);
			
			for(Class<? extends ICommand> clazz: classes) {
				try {
					ICommand c = clazz.newInstance();
					String signum = c.getCommandSymbol();
					mapOfCommands.put(signum, clazz);
				} catch (InstantiationException e) {
					logger.error("(InstantiationException) Unable to instanziate the class : " + clazz.getCanonicalName(), e);
				} catch (IllegalAccessException e) {
					logger.error("(IllegalAccessException) Unable to instanziate the class : " + clazz.getCanonicalName(), e);
				} catch (Throwable e) {
					logger.error("(Generic Exception) Unable to instanziate the class : " + clazz.getCanonicalName(), e);
				}
			}
		} catch (Throwable e) {
			logger.error("(Generic Exception) Unable to locate package classes in : " + packageName, e);
		}
		return mapOfCommands;
	}
	
	/*
	 * Use reflections to instantiate and run a lookup of the command syntax in the reference package
	 */
	private static Map<String, String[]> lookupSyntaxInPackage(String packageName) {
		Map<String, String[]> mapOfCommandsSyntax = new HashMap<String, String[]>(0);
		try {
			Reflections reflections = new Reflections(packageName);
			Set<Class<? extends ICommand>> classes = reflections.getSubTypesOf(ICommand.class);
			
			for(Class<? extends ICommand> clazz: classes) {
				try {
					ICommand c = clazz.newInstance();
					String signum = c.getCommandSymbol();
					String[] syntax = c.getSyntax();
					mapOfCommandsSyntax.put(signum, syntax);
				} catch (InstantiationException e) {
					logger.error("(InstantiationException) Unable to instanziate the class : " + clazz.getCanonicalName(), e);
				} catch (IllegalAccessException e) {
					logger.error("(IllegalAccessException) Unable to instanziate the class : " + clazz.getCanonicalName(), e);
				} catch (Throwable e) {
					logger.error("(Generic Exception) Unable to instanziate the class : " + clazz.getCanonicalName(), e);
				}
			}
		} catch (Throwable e) {
			logger.error("(Generic Exception) Unable to locate package classes in : " + packageName, e);
		}
		return mapOfCommandsSyntax;
	}
	
	/**
	 * Returns the map containing operation symbols and related classes
	 * @return the Commands Class Map map of commands
	 */
	public Map<String, Class<? extends ICommand>> getCommandsMap() {
		return commandsMap;
	}

	/**
	 * Returns the map containing operation symbols and related classes
	 * @return the Commands Syntax Map map of commands
	 */
	public Map<String, String[]> getCommandsSyntaxMap() {
		return commandsSyntaxMap;
	}

	/**
	 * The CommandsManager Singleton instance static getter method.
	 * @return The Singleton instance of the CommandsManager
	 */
	public static CommandsManager getInstance() {
		if (instance == null) {
			instance = new CommandsManager();
		}
		return instance;
	}
	
}
