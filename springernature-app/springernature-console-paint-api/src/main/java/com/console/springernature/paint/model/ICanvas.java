package com.console.springernature.paint.model;

/**
 * Canvas business interface. This class expose the common canvas features.
 * 
 * @author Fabrizio Torelli
 * @see <a href="ICommand.html">ICommand</a>, <a href="IDevice.html">IDevice</a>
 *
 */

public interface ICanvas extends ICommandListener {

	/**
	 * Create a new empty canvas.
	 * @param width - Width of the canvas in unit
	 * @param height - Height of the canvas in unit
	 */
	void create(int width, int height);
	
	/**
	 * Returns the current canvas width or 0 if the canvas has not been created.
	 * @return the width in units or 0
	 */
	int getWidth();
	
	/**
	 * Returns the current canvas height or 0 if the canvas has not been created.
	 * @return the height in units or 0
	 */
	int getHeight();
	
	/**
	 * Apply a command to the current canvas if the command is valid.
	 * <br/>See <a href="ICommand.html">ICommand</a>
	 * @param command - The command to have been executed.
	 * @return the command application status.
	 */
	boolean applyCommand(ICommand command);
	
	/**
	 * The device to be assigned to the canvas to print the changes for any command accepted.
	 * <br/>See <a href="IDevice.html">IDevice</a>
	 * @param device - Device to be applied to the canvas.
	 */
	void assignDevice(IDevice device);
	/**
	 * Return the status of a device assigned to the canvas.
	 * @return The status of a valid device assigned to the canvas.
	 */
	boolean hasADeviceAssigned();
	/**
	 * Return the status of creation for the canvas.
	 * @return The status of a creation for the canvas.
	 */
	boolean isCreated();
	/**
	 * Return a discovered command response with the related command or the message explaining the not success.
	 * @return The command response for the look up.
	 */
	CommandResponse lookUpCommand(String command, String[] arguments);
}
