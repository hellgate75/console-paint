package com.console.springernature.paint.model;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jline.Terminal;
import jline.TerminalFactory;
import jline.TerminalFactory.Type;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implementation of the IDevice API structure containing the canvas and owner of the command input control process.
 * 
 * @author Fabrizio Torelli
 * see com.console.springernature.paint.model.IDevice, <a href="CommandsManager.html">CommandsManager</a>
 */
public class Device implements IDevice {
	private static final Logger logger = LoggerFactory.getLogger("com.console.springernature.paint.model");

	private List<ICommandListener> commandListenerList = new ArrayList<ICommandListener>(0);
	private int requestCounter = 0;
	public int MAX_CONSOLE_WIDTH = 82;

	private String codeTitle = "SpringerNature Code Problem";

	public int MAX_CONSOLE_HEIGHT = 19;
	public boolean MAX_CONSOLE_BOUNDED = false;
	private Map<String, String[]> deviceCommandMap = new HashMap<String, String[]>(0);

	/**
	 * Constructor of the IDevice API instance
	 */
	public Device() {
		super();
	}
	
	/**
	 * Initialize the map of the commands symbols associated to the Command classes
	 * using the CommandsManager to recover the information
	 */
	public void initDeviceCommandMap() {
		deviceCommandMap =  CommandsManager.getInstance().getCommandsSyntaxMap();
		
	}
	
	/**
	 * Initialize Device, setup the screen and prints the welcome information
	 */
	public void initDevice() {
		System.out.println(codeTitle);
		System.out.println("---------------------------");
		System.out.println("Author: Fabrizio Torelli");
		try {
			TerminalFactory.configure(Type.AUTO);
			Terminal t = TerminalFactory.get();
			t.init();
			if (t.getWidth()>1 && t.getHeight()>1) {
				MAX_CONSOLE_WIDTH = t.getWidth();
				MAX_CONSOLE_HEIGHT = t.getHeight();
				MAX_CONSOLE_BOUNDED = true;
			}
		} catch (Throwable e) {
		}
		if (MAX_CONSOLE_BOUNDED) {
			System.out.println("Terminal size : cols="+MAX_CONSOLE_WIDTH+ " rows="+MAX_CONSOLE_HEIGHT);
		}
		else {
			System.out.println("Terminal size : UNKNOWN (presuming cols="+MAX_CONSOLE_WIDTH+ " rows="+MAX_CONSOLE_HEIGHT+")");
		}
		System.out.println();
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println();
				System.out.println("Thank you for have used the "+codeTitle+"!!");
			}
		}));

	}

	/**
	 * This method allows the listening for new commands from the System.in stream
	 */
	public void listenForANewRequest() {
		requestCounter = 0;
		System.out.print("enter command: ");
		
		String[] commandPath = new String[0];
		
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			String line = "";
			while(null == (line = br.readLine())) {
				try {
					Thread.sleep(500);
				} catch (Throwable e) {
				}
			}
			List<String> commandAndArgs = new ArrayList<String>(0);
			for(String value: line.split(" ")) {
				if (!value.equals("\n") && value.length()>0) {
					commandAndArgs.add(value);
				}
			}
			commandPath = new String[commandAndArgs.size()];
			commandPath = commandAndArgs.toArray(commandPath);
			br=null;
		} catch (Throwable e) {
			logger.error("Unable to complete the input.", e);
		}
		dispatchCommand(commandPath);
	}
	
	/**
	 * This method dispatch a parsed command to the Command Listeners associated to the Device
	 * @param commandPath the array of string containing the command
	 */
	public void dispatchCommand(String[] commandPath) {
		if (commandPath.length==0) {
			System.out.println("ERROR: None command found please try again.");
			System.out.println();
			requestCounter = 1;
			this.listenForANewRequest();
			unlockRequest();
		}
		else {
			try {
				//Request the command
				if (commandListenerList.size()>0) {
					for(ICommandListener listener: commandListenerList) {
						listener.dispatchCommand(this, commandPath);
						requestCounter++;
					}
				}
				else {
					logger.error("No dispatch listener connected.");
					requestCounter = 1;
					writeMessage(new DeviceMessage(ErrorCodeEnum.CANVAS_UNAVAILABLE, DeviceMessageTypeEnum.ERROR, "Unable to complete the command execution"));
					unlockRequest();
				}
			} catch (Throwable e) {
				logger.error("Unable to complete the command dispatch.", e);
				requestCounter = 1;
				writeMessage(new DeviceMessage(ErrorCodeEnum.ERROR_IN_COMMAND_DISPATCHING, DeviceMessageTypeEnum.ERROR, "Unable to complete the command execution"));
				unlockRequest();
			}
		}
	}

	/* (non-Javadoc)
	 * @see com.console.springernature.paint.model.IDevice#drawCanvas(com.console.springernature.paint.model.ICanvas)
	 */
	@Override
	public void drawCanvas(ICanvas canvas) {
		if (canvas!=null && canvas.isCreated() && Canvas.class.isAssignableFrom(canvas.getClass())) {
			int canvasWidth = canvas.getWidth();
			Canvas c = (Canvas)canvas;
			char[][] map = c.getCanvasMatrix();
			System.out.println(new String(new char[canvasWidth+2]).replace("\0", Canvas.HORIZONTHAL_FRAME_BRUSH));
			for(char[] line: map) {
				System.out.println(Canvas.VERTICAL_FRAME_BRUSH + String.valueOf(line) + Canvas.VERTICAL_FRAME_BRUSH);
			}
			System.out.println(new String(new char[canvasWidth+2]).replace("\0", Canvas.HORIZONTHAL_FRAME_BRUSH));
		}
		else {
			this.writeMessage(new DeviceMessage(ErrorCodeEnum.INVALID_CANVAS, DeviceMessageTypeEnum.WARNING, "This canvas is not valid to have been drawn in the device."));
		}
	}

	/* (non-Javadoc)
	 * @see com.console.springernature.paint.model.IDevice#addCommandListener(com.console.springernature.paint.model.ICommandListener)
	 */
	@Override
	public void addCommandListener(ICommandListener listener) {
		if (listener!=null && !commandListenerList.contains(listener)) {
			commandListenerList.add(listener);
		}
	}

	/* (non-Javadoc)
	 * @see com.console.springernature.paint.model.IDevice#removeCommandListener(com.console.springernature.paint.model.ICommandListener)
	 */
	@Override
	public void removeCommandListener(ICommandListener listener) {
		if (listener!=null && commandListenerList.contains(listener)) {
			commandListenerList.remove(listener);
		}
	}

	/**
	 * This method reset andclear the associated Command Listeners
	 */
	public void resetCommandListeners() {
		commandListenerList = new ArrayList<ICommandListener>(0);
	}
	
	
	/* (non-Javadoc)
	 * @see com.console.springernature.paint.model.IDevice#writeMessage(com.console.springernature.paint.model.DeviceMessage)
	 */
	@Override
	public void writeMessage(DeviceMessage message) {
		if (message!=null) {
			String kindOf = message.getType().name();
			System.out.println(kindOf + ": " + message.getMessage());
			if (message.getSyntax()!=null && message.getSyntax().length>0) {
				System.out.println("Syntax : ");
				for(String sintaxLine: message.getSyntax()) {
					System.out.println(sintaxLine);
				}
			}
		}
	}

	/* (non-Javadoc)
	 * @see com.console.springernature.paint.model.IDevice#lookUpCommands()
	 */
	@Override
	public Map<String, String[]> lookUpCommands() {
		return deviceCommandMap;
	}

	/* (non-Javadoc)
	 * @see com.console.springernature.paint.model.IDevice#unlockRequest()
	 */
	@Override
	public void unlockRequest() {
		requestCounter--;
		if (requestCounter<=0) {
			System.out.println();
			this.listenForANewRequest();
		}
	}

	/* (non-Javadoc)
	 * @see com.console.springernature.paint.model.IDevice#start()
	 */
	@Override
	public void start() {
		Thread t = new Thread(new Runnable() {
			
			@Override
			public void run() {
				Device.this.initDeviceCommandMap();
				Device.this.initDevice();
				if (commandListenerList.size()==0) {
					DeviceManager.getInstance().getDefaultCanvas();
				}
				Device.this.listenForANewRequest();
			}
		});
		t.setDaemon(true);
		t.start();
	}

	/* (non-Javadoc)
	 * @see com.console.springernature.paint.model.IDevice#waitForExit()
	 */
	@Override
	public void waitForExit() {
		try {
			Thread.currentThread().join();
		} catch (InterruptedException e) {
		}
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#finalize()
	 */
	@Override
	protected void finalize() throws Throwable {
		/*
		 * Force to empty the list and remove any reference of the Canvas to avoid weak references still in active if the main thread continue
		 */
		if (commandListenerList!=null) {
			commandListenerList.clear();
			commandListenerList = null;
		}
		System.gc();
		super.finalize();
	}

}
