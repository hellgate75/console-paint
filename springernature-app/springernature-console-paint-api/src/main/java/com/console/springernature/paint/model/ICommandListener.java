/**
 * 
 */
package com.console.springernature.paint.model;

/**
 * Message Listener that dispatch the message request.
 * @author Fabrizio Torelli
 * @see <a href="IDevice.html">IDevice</a>
 */
public interface ICommandListener {
	/**
	 * Method that dispatch to the listener the new command request
	 * <br/>See <a href="IDevice.html">IDevice</a>
	 * @param device Device calling the command change.
	 * @param command Command string to be executed.
	 */
	void dispatchCommand(IDevice device, String command);

	/**
	 * Method that dispatch to the listener the new command request
	 * <br/>See <a href="IDevice.html">IDevice</a>
	 * @param device Device calling the command change.
	 * @param parsedCommand Command string parsed array to be executed.
	 */
	void dispatchCommand(IDevice device, String[] parsedCommand);
}
