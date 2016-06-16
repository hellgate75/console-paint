package com.console.springernature.paint.app;

import com.console.springernature.paint.model.DeviceManager;
import com.console.springernature.paint.model.IDevice;

public class Main {
	static {
		if (System.getProperty("log4j.configurationFile")==null)
			System.setProperty("log4j.configurationFile", "log4j2.xml");
	}
	public static void main(String[] args) {
		IDevice device = DeviceManager.getInstance().getDefaultDevice();
		if (device!=null) {
				device.start();
				device.waitForExit();
		}
		else {
			System.out.println("Unable to start the device ...");
		}
	}

}
