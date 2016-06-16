/**
 * 
 */
package com.console.springernature.paint.model;

/**
 * Presents the result for any request of a command from the execution engine
 * 
 * @author Fabrizio
 * @see <a href="DeviceMessage.html">DeviceMessage</a>, <a href="ICommand.html">ICommand</a>
 */
public class CommandResponse {
	private DeviceMessage message = null;
	private ICommand commandInstance = null;
	
	/**
	 * Constructor for a not successful command instance. It reports the message to be shown in the device.
	 * <br/>See <a href="DeviceMessage.html">DeviceMessage</a>
	 * @param message - Message explaining the not success in bounding the command
	 */
	public CommandResponse(DeviceMessage message) {
		super();
		this.message = message;
	}
	/**
	 * Constructor for a successful command instance. It means that the command is found, the syntax is correct and the parameters are accepted.
	 * <br/>See <a href="ICommand.html">ICommand</a>
	 * @param command - The bounded command.
	 */
	public CommandResponse(ICommand command) {
		super();
		this.commandInstance = command;
	}
	/**
	 * This method return the last INFO, WARNING or ERROR message related to the last execution.
	 * <br/>See <a href="DeviceMessage.html">DeviceMessage</a>
	 * @return The last message on the command execution

	 */
	public DeviceMessage getMessage() {
		return message;
	}
	/**
	 * Retrieve the command instance if it has been found and it was correct.
	 * <br/>See <a href="ICommand.html">ICommand</a>
	 * @return The command instance.
	 * @see ICommand
	 */
	public ICommand getCommand() {
		return commandInstance;
	}
	/**
	 * Explain if the message has been fond and instanced correctly.
	 * @return The correct status of the retrieve of the command
	 */
	public boolean isCorrect() {
		return commandInstance!=null;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "CommandResponse [message=" + message + ", commandInstance="
				+ commandInstance + "]";
	}
	
}
