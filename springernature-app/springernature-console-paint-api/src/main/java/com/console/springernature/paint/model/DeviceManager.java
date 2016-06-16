/**
 * 
 */
package com.console.springernature.paint.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Device Manager is owner of the runtime load of Canvas and Device from the implementation package following the rule :
 * <br/><ul>
 * <li>Any implementation has found into the package: <b>com.console.springernature.paint.model</b></li>
 * <li>Any request made as an extension of the default package contains the sub-packages: for the example <b>com.console.springernature.paint.model.mycanvas.Canvas1</b> is <b>mycanvas.Canvas1</b></li>
 * </ul>
 * @author Fabrizio Torelli
 * @see <a href="ICanvas.html">ICanvas</a>, <a href="IDevice.html">IDevice</a>
 */
public class DeviceManager {

	private static final Logger logger = LoggerFactory.getLogger("com.console.springernature.paint.model");

	private static DeviceManager instance = null;

	private IDevice defaultDevice = null;
	private ICanvas defaultCanvas = null;
	
	/*
	 * The DeviceManager is a singleton and the Constructor has private visibility. No instance is allowed.
	 */
	private DeviceManager() {
		super();
		// TODO Auto-generated constructor stub
	}

	/*
	 * This method use the Class loader to find and instance Classes as required by the full class name.
	 * Accepts: className full class name (e.g.: com.console.springernature.paint.model.Device)
	 */
	private static Object getObjectInstance(String className) {
		try {
			return Class.forName(className).newInstance();
		} catch (InstantiationException e) {
			logger.error("(InstantiationException) Unable to instanziate the class : " + className, e);
		} catch (IllegalAccessException e) {
			logger.error("(IllegalAccessException) Unable to instanziate the class : " + className, e);
		} catch (ClassNotFoundException e) {
			logger.error("(ClassNotFoundException) Unable to instanziate the class : " + className, e);
		} catch (Throwable e) {
			logger.error("(Generic Exception) Unable to instanziate the class : " + className, e);
		}
		return null;
	}
	
	/**
	 * Returns the default Device in the implementation package if is loaded or null.
	 * <br/>See <a href="IDevice.html">IDevice</a>
	 * @return The default Device or null
	 */
	public IDevice getDefaultDevice() {
		if (defaultDevice==null) {
			try {
				defaultDevice = (IDevice) getObjectInstance("com.console.springernature.paint.model.Device");
			} catch (ClassCastException e) {
				logger.error("(ClassCastException) Unable to instanziate the default device ...", e);
			} catch (Throwable e) {
				logger.error("(Generic Exception) Unable to instanziate the default device ...", e);
			}
		}
		return defaultDevice;
	}

	/**
	 * Returns a named Device in the implementation package if is loaded or null.
	 * <br/>See <a href="IDevice.html">IDevice</a>
	 * @param simpleName Simple name of the Device (e.g. for <b>com.console.springernature.paint.model.MyDevice</b> it is correct to use <b>MyDevice</b>)
	 * @return The named Device or null
	 */
	public IDevice getNamedDevice(String simpleName) {
		try {
			return (IDevice) getObjectInstance("com.console.springernature.paint.model."+simpleName);
		} catch (ClassCastException e) {
			logger.error("(ClassCastException) Unable to instanziate the named device : "+simpleName+" ...", e);
		} catch (Throwable e) {
			logger.error("(Generic Exception) Unable to instanziate the named device : "+simpleName+" ...", e);
		}
		return null;
	}

	/**
	 * Returns the default Canvas (and link it to the default Device) in the implementation package if is loaded or null.
	 * <br/>See <a href="ICanvas.html">ICanvas</a>
	 * @return The default Canvas or null
	 */
	public ICanvas getDefaultCanvas() {
		if (defaultCanvas==null) {
			try {
				defaultCanvas = (ICanvas) getObjectInstance("com.console.springernature.paint.model.Canvas");
				if (defaultCanvas!=null) {
					if (defaultDevice==null) {
						this.getDefaultDevice();
					}
					if (defaultDevice!=null) {
						this.getDefaultDevice();
						defaultCanvas.assignDevice(defaultDevice);
						defaultDevice.addCommandListener(defaultCanvas);
					}
					else {
						logger.warn("(Custom Event) Unable to associate the default device ...");
						defaultCanvas = null;
					}
				}
			} catch (ClassCastException e) {
				logger.error("(ClassCastException) Unable to instanziate the default canvas ...", e);
			} catch (Throwable e) {
				logger.error("(Generic Exception) Unable to instanziate the default canvas ...", e);
			}
		}
		return defaultCanvas;
	}
	
	/**
	 * Returns a named Canvas (and link it to the used Device) in the implementation package if is loaded or null.
	 * <br/>See <a href="ICanvas.html">ICanvas</a>, <a href="IDevice.html">IDevice</a>
	 * @param simpleName Simple name of the Canvas (e.g. for <b>com.console.springernature.paint.model.MyCanvas</b> it is correct to use <b>MyCanvas</b>)
	 * @param device The Device Object to be linked the device. If it is nulled the Canvas is not returned.
	 * @return The named canvas or null
	 */
	public ICanvas getNamedCanvas(String simpleName, IDevice device) {
		try {
			ICanvas canvas = (ICanvas) getObjectInstance("com.console.springernature.paint.model."+simpleName);
			if (device!=null) {
				canvas.assignDevice(device);
				device.addCommandListener(canvas);
				return canvas;
			}
			else {
				logger.warn("(Custom Event) Unable to associate the given device to the canvas : "+simpleName+" ...");
				return null;
			}
		} catch (ClassCastException e) {
			logger.error("(ClassCastException) Unable to instanziate the named canvas : "+simpleName+" ...", e);
		} catch (Throwable e) {
			logger.error("(Generic Exception) Unable to instanziate the named canvas : "+simpleName+" ...", e);
		}
		return null;
	}
	
	/**
	 * The DeviceManager Singleton instance static getter method.
	 * @return The Singleton instance of the Device Manager
	 */
	public static DeviceManager getInstance() {
		if (instance == null) {
			instance = new DeviceManager();
		}
		return instance;
	}
}
