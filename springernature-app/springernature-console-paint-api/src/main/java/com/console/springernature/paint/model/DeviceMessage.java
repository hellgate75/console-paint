/**
 * 
 */
package com.console.springernature.paint.model;

import java.util.Arrays;

/**
 * Realize a return message from the command request or command execution.
 * 
 * @author Fabrizio
 * @see <a href="DeviceMessageTypeEnum.html">DeviceMessageTypeEnum</a>, <a href="ErrorCodeEnum.html">ErrorCodeEnum</a>
 */
public class DeviceMessage {
	private ErrorCodeEnum errorCode;
	private DeviceMessageTypeEnum type;
	private String message;
	private String[] syntax = new String[0];
	
	/**
	 * Construct the Device message.
	 * See  <a href="DeviceMessageTypeEnum.html">DeviceMessageTypeEnum</a> and <a href="ErrorCodeEnum.html">ErrorCodeEnum</a>
	 * @param errorCode Error code assigned to the message ot null
	 * @param type The severity type message
	 * @param message The textual message
	 */
	public DeviceMessage(ErrorCodeEnum errorCode, DeviceMessageTypeEnum type, String message) {
		super();
		this.errorCode = errorCode;
		this.type = type;
		this.message = message;
	}
	/**
	 * Construct the Device message.
	 * See  <a href="DeviceMessageTypeEnum.html">DeviceMessageTypeEnum</a> and <a href="ErrorCodeEnum.html">ErrorCodeEnum</a>
	 * @param errorCode Error code assigned to the message ot null
	 * @param type The severity type message
	 * @param message The textual message
	 * @param syntax The syntax message
	 */
	public DeviceMessage(ErrorCodeEnum errorCode, DeviceMessageTypeEnum type, String message,
			String[] syntax) {
		super();
		this.errorCode = errorCode;
		this.type = type;
		this.message = message;
		this.syntax = syntax;
	}
	/**
	 * Message severity type related to the current message.
	 * See  <a href="DeviceMessageTypeEnum.html">DeviceMessageTypeEnum</a>
	 * @return the message severity type
	 */
	public DeviceMessageTypeEnum getType() {
		return type;
	}
	/**
	 * The error code related to the message. It can be nullable in case the error message is not applicable.
	 * See  <a href="ErrorCodeEnum.html">ErrorCodeEnum</a>
	 * @return the error code enumeration member or null
	 */
	public ErrorCodeEnum getErrorCode() {
		return errorCode;
	}
	/**
	 * The message coming from the command or the command or any other entity.
	 * @return the message to be displayed in the device.
	 */
	public String getMessage() {
		return message;
	}
	/**
	 * The command correct syntax message.
	 * @return The array of dtring representing the command correct syntax.
	 * @see ICommand#getSyntax()
	 */
	public String[] getSyntax() {
		return syntax;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "DeviceMessage [type='" + type + "', message='" + message
				+ "', syntax='" + Arrays.toString(syntax) + "']";
	}
	
}
