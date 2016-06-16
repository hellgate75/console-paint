package com.console.springernature.paint.model;

/**
 * Realize the different message type to be written on the device.
 * <br/>
 * The members are :
 * <br/><b>INFO</b> - Advice communication type
 * <br/><b>WARNING</b> - Warning message related to any unbounded or illogical operation
 * <br/><b>ERROR</b> - Error in the execution of a command, a process or an operation.
 * <br/>
 * @author Fabrizio Torelli
 *
 */
public enum DeviceMessageTypeEnum {
	INFO, WARNING, ERROR;
}
