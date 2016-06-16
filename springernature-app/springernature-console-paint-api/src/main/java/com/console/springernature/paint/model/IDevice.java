package com.console.springernature.paint.model;

import java.util.Map;

/**
 * Device business interface. This class expose the common output device features.
 * A device can be the screen, a web page applet, a custom paint drawer device and so on ...
 * 
 * @author Fabrizio Torelli
 * @see <a href="ICanvas.html">ICanvas</a>, <a href="DeviceMessage.html">DeviceMessage</a>, <a href="ICommandListener.html">ICommandListener</a>, java.util.Map
 *
 */
public interface IDevice {
	
	/**
	 * Redraws the canvas or generate an output message in case of error or canvas incompatibility
	 * <br/>See <a href="ICanvas.html">ICanvas</a>
	 * @param canvas to be updated
	 */
	void drawCanvas(ICanvas canvas);

	/**
	 * Add a command listener to the device
	 * <br/>See <a href="ICommandListener.html">ICommandListener</a>
	 * @param listener Listener to be added to the device
	 */
	void addCommandListener(ICommandListener listener);

	/**
	 * Remove a command listener to the device
	 * <br/>See <a href="ICommandListener.html">ICommandListener</a>
	 * @param listener Listener to be removed from the device
	 */
	void removeCommandListener(ICommandListener listener);

	/**
	 * Provide the feature to display a message on the device
	 * <br/>See <a href="DeviceMessage.html">DeviceMessage</a>
	 * @param message Message to have been displayed in the device
	 */
	void writeMessage(DeviceMessage message);

	/**
	 * Provide the feature to unlock the request wait and process the next command in the queue or simply provide the next green semaphore to allow a new command. 
	 */
	void unlockRequest();

	/**
	 * Provide the startup of the Device operations. 
	 */
	void start();

	/**
	 * Provide the synchronization of the device with the current thread. 
	 */
	void waitForExit();
	
	/**
	 * Provide the map of the available commands and the relative syntax messages
	 * <br/>See java.util.Map
	 * @return The available commands on the device.
	 */
	Map<String,String[]> lookUpCommands();
	
	
}
