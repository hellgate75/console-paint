package com.console.springernature.paint.model;
/**
 * Realize the different known error messages relted to any process executed in the console.
 * <br/>
 * The members are :
 * <br/><b>COMMAND_NOT_FOUND</b> - A command has not be found in the command set
 * <br/><b>INVALID_PARAMETERS</b> - The parameter to make an instance of  command or another entity are invalid
 * <br/><b>COMMAND_OUT_OF_BOUNDS</b> - The command draw a kind of shape the is extended outside the canvas and it has been trunked.
 * <br/><b>COMMAND_NOT_VALID</b> - The command is not applicable to the canvas due to the missing of the prerequisites to be drawn.
 * <br/><b>INVALID_CANVAS</b> - The canvas is not applicable to the device.
 * <br/><b>CANVAS_UNAVAILABLE</b> - The canvas is not instanced.
 * <br/><b>DEVICE_UNAVAILABLE</b> - The canvas has not any device assigned.
 * <br/><b>UNKNOWN_ERROR</b> - Any error not identified in the current code list.
 * <br/>
 * @author Fabrizio Torelli
 *
 */

public enum ErrorCodeEnum {
	COMMAND_NOT_FOUND, INVALID_PARAMETERS, COMMAND_OUT_OF_BOUNDS, ERROR_IN_COMMAND_DISPATCHING, COMMAND_NOT_VALID, INVALID_CANVAS, UNOWNED_CANVAS, NULLED_CANVAS, CANVAS_UNAVAILABLE, DEVICE_UNAVAILABLE, UNKNOW_ERROR
}
