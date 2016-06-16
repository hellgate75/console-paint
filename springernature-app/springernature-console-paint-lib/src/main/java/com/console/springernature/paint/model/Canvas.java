package com.console.springernature.paint.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implementation of the ICanvas API structure containing the shapes drawn on it.
 * 
 * @author Fabrizio Torelli
 * see ICanvas
 */
public class Canvas implements ICanvas {
	private static final Logger logger = LoggerFactory.getLogger("com.console.springernature.paint.model");
	
	public static final char PAINT_BRUSH = 'x';
	public static final String HORIZONTHAL_FRAME_BRUSH = "-";
	public static final String VERTICAL_FRAME_BRUSH = "|";
	
	public int MAX_CONSOLE_WIDTH = 40;
	public int MAX_CONSOLE_HEIGHT = 10;
	public boolean MAX_CONSOLE_BOUNDED = false;

	private char[][] canvasMap = null;
	private int width = 0;
	private int height = 0;
	private Device currentDevice = null;
	
	/**
	 * Constructor of the ICanvas API instance
	 */
	public Canvas() {
		super();
		
	}
	
	private void executeCommand(String[] parsedCommand) {
		if (currentDevice!=null && parsedCommand!=null && parsedCommand.length>0) {
			if (!this.isCreated() && !("C".equalsIgnoreCase(parsedCommand[0])) && !("Q".equalsIgnoreCase(parsedCommand[0]))) {
				logger.error("(General Excaption) Canvas not ready ...");
				currentDevice.writeMessage(new DeviceMessage(ErrorCodeEnum.CANVAS_UNAVAILABLE, DeviceMessageTypeEnum.ERROR, "Canvas not ready to draw. Please create a canavas initially."));
				currentDevice.unlockRequest();
				return;
			}
			Class<? extends ICommand> commandClass = CommandsManager.getInstance().getCommandsMap().get(parsedCommand[0].toUpperCase());
			if (commandClass!=null) {
				try {
					ICommand command = commandClass.newInstance();
					String[] parameters = new String[0];
					if (parsedCommand.length>1) {
						parameters = Arrays.copyOfRange(parsedCommand, 1, parsedCommand.length);
					}
					if (command.acceptParameters(this, parameters)) {
						if (command.isComposedCommand()) {
							command.executeGeometry(this);
						}
						else {
							command.execute(this);
						}
						if (this.isCreated()) {
							currentDevice.drawCanvas(this);
						}
						currentDevice.unlockRequest();
					}
					else {
						currentDevice.writeMessage(command.getLastMessage());
						if (this.isCreated()) {
							currentDevice.drawCanvas(this);
						}
						currentDevice.unlockRequest();
					}
				} catch (InstantiationException e) {
					logger.error("(InstantiationException) Unable to instanziate the class : " + commandClass.getCanonicalName(), e);
					currentDevice.writeMessage(new DeviceMessage(ErrorCodeEnum.COMMAND_NOT_VALID, DeviceMessageTypeEnum.ERROR, "Unable to instanziate the command : '"+parsedCommand[0].toUpperCase()+"'."));
					if (this.isCreated()) {
						currentDevice.drawCanvas(this);
					}
					currentDevice.unlockRequest();
				} catch (IllegalAccessException e) {
					logger.error("(IllegalAccessException) Unable to instanziate the class : " + commandClass.getCanonicalName(), e);
					currentDevice.writeMessage(new DeviceMessage(ErrorCodeEnum.COMMAND_NOT_VALID, DeviceMessageTypeEnum.ERROR, "Unable to instanziate the command : '"+parsedCommand[0].toUpperCase()+"'."));
					if (this.isCreated()) {
						currentDevice.drawCanvas(this);
					}
					currentDevice.unlockRequest();
				} catch (Throwable e) {
					logger.error("(General Exception) Unable to instanziate the class : " + commandClass.getCanonicalName(), e);
					currentDevice.writeMessage(new DeviceMessage(ErrorCodeEnum.COMMAND_NOT_VALID, DeviceMessageTypeEnum.ERROR, "Unable to instanziate the command : '"+parsedCommand[0].toUpperCase()+"'."));
					if (this.isCreated()) {
						currentDevice.drawCanvas(this);
					}
					currentDevice.unlockRequest();
				}
			}
			else {
				currentDevice.writeMessage(new DeviceMessage(ErrorCodeEnum.COMMAND_NOT_VALID, DeviceMessageTypeEnum.ERROR, "Unable to locate the command : '"+parsedCommand[0].toUpperCase()+"'."));
				if (this.isCreated()) {
					currentDevice.drawCanvas(this);
				}
				currentDevice.unlockRequest();
			}
		}
		else {
			currentDevice.writeMessage(new DeviceMessage(ErrorCodeEnum.COMMAND_NOT_FOUND, DeviceMessageTypeEnum.ERROR, "Unable to locate the from the input : '"+Arrays.toString(parsedCommand)+"'."));
			if (this.isCreated()) {
				currentDevice.drawCanvas(this);
			}
			currentDevice.unlockRequest();
		}
	}

	/* (non-Javadoc)
	 * @see com.console.springernature.paint.model.ICommandListener#dispatchCommand(com.console.springernature.paint.model.IDevice, java.lang.String)
	 */
	@Override
	public void dispatchCommand(IDevice device, String command) {
		if (currentDevice!=null) {
			if (currentDevice == device) {
				//currentDevice.unlockRequest();
				logger.info("Full Command : " + command);
				List<String> commandAndArgs = new ArrayList<String>(0);
				for(String value: command.split(" ")) {
					if (!value.equals("\n") && value.length()>0) {
						commandAndArgs.add(value);
					}
				}
				String[] parsedCommand =  new String[commandAndArgs.size()];
				parsedCommand = commandAndArgs.toArray(parsedCommand);
				executeCommand(parsedCommand);
			}
			else {
				device.writeMessage(new DeviceMessage(ErrorCodeEnum.INVALID_CANVAS, DeviceMessageTypeEnum.WARNING, "This canvas has not registered the device."));
				device.unlockRequest();
			}
		}
	}

	/* (non-Javadoc)
	 * @see com.console.springernature.paint.model.ICommandListener#dispatchCommand(com.console.springernature.paint.model.IDevice, java.lang.String[])
	 */
	@Override
	public void dispatchCommand(IDevice device, String[] parsedCommand) {
		if (currentDevice!=null) {
			if (currentDevice == device) {
				//currentDevice.unlockRequest();
				executeCommand(parsedCommand);
			}
			else {
				device.writeMessage(new DeviceMessage(ErrorCodeEnum.UNOWNED_CANVAS, DeviceMessageTypeEnum.WARNING, "This canvas has not registered the device."));
				device.unlockRequest();
			}
		}
	}

	/* (non-Javadoc)
	 * @see com.console.springernature.paint.model.ICanvas#create(int, int)
	 */
	@Override
	public void create(int width, int height) {
		if (width >0 && height > 0) {
			this.height=height;
			this.width=width;
			canvasMap = new char[height][];
			for (int i=0; i<height; i++) {
				canvasMap[i] = (new String(new char[width]).replace("\0", " ")).toCharArray();
			}
		}
		else {
			if (currentDevice!=null) {
				currentDevice.writeMessage(new DeviceMessage(ErrorCodeEnum.NULLED_CANVAS, DeviceMessageTypeEnum.WARNING, "The canvas of dimension " + width +" rows and " + height + " columns is not enought dimensioned!!"));
				currentDevice.unlockRequest();
			}
			else {
				//logger.
			}
		}
	}

	/* (non-Javadoc)
	 * @see com.console.springernature.paint.model.ICanvas#getWidth()
	 */
	@Override
	public int getWidth() {
		return width;
	}

	/* (non-Javadoc)
	 * @see com.console.springernature.paint.model.ICanvas#getHeight()
	 */
	@Override
	public int getHeight() {
		return height;
	}

	/* (non-Javadoc)
	 * @see com.console.springernature.paint.model.ICanvas#applyCommand(com.console.springernature.paint.model.ICommand)
	 */
	@Override
	public boolean applyCommand(ICommand command) {
		return false;
	}

	/* (non-Javadoc)
	 * @see com.console.springernature.paint.model.ICanvas#assignDevice(com.console.springernature.paint.model.IDevice)
	 */
	@Override
	public void assignDevice(IDevice device) {
		try {
			currentDevice = (Device)device;
		} catch (ClassCastException e) {
		}
		if (currentDevice!=null) {
			MAX_CONSOLE_WIDTH = currentDevice.MAX_CONSOLE_WIDTH-2;
			MAX_CONSOLE_HEIGHT = currentDevice.MAX_CONSOLE_HEIGHT-4;
			MAX_CONSOLE_BOUNDED = currentDevice.MAX_CONSOLE_BOUNDED;
		}
	}

	/* (non-Javadoc)
	 * @see com.console.springernature.paint.model.ICanvas#hasADeviceAssigned()
	 */
	@Override
	public boolean hasADeviceAssigned() {
		return currentDevice!=null;
	}

	/* (non-Javadoc)
	 * @see com.console.springernature.paint.model.ICanvas#isCreated()
	 */
	@Override
	public boolean isCreated() {
		return canvasMap!=null;
	}

	/* (non-Javadoc)
	 * @see com.console.springernature.paint.model.ICanvas#lookUpCommand(java.lang.String, java.lang.String[])
	 */
	@Override
	public CommandResponse lookUpCommand(String command, String[] arguments) {
		return null;
	}

	/**
	 * Return the Canvas matrix as rows / column bi-dimensional array containing the Canvas cell's characters
	 * presents in each point of the Canvas
	 * @return the matrix of the canvas cell's characters
	 */
	public char[][] getCanvasMatrix() {
		return canvasMap;
	}

	/**
	 * Set the Canvas matrix as rows / column bi-dimensional array containing the Canvas cell's characters
	 * @param map the matrix of the canvas cell's characters
	 */
	public void setCanvasMatrix(char[][] map) {
		canvasMap = map;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#finalize()
	 */
	@Override
	protected void finalize() throws Throwable {
		/*
		 * Force to empty the local Device reference to avoid weak references still in active if the main thread continue
		 */
		if (currentDevice!=null) {
			currentDevice.removeCommandListener(this);
			currentDevice = null;
		}
		/*
		 * Destroy the raw types (not necessary but useful)
		 */
		canvasMap = null;
		System.gc();
		super.finalize();
	}
	
}
