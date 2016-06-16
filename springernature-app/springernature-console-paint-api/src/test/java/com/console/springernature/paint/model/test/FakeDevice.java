/**
 * 
 */
package com.console.springernature.paint.model.test;

import java.util.Map;

import com.console.springernature.paint.model.DeviceMessage;
import com.console.springernature.paint.model.ICanvas;
import com.console.springernature.paint.model.ICommandListener;
import com.console.springernature.paint.model.IDevice;

/**
 * Fake device for testing reasons.
 * @author Fabrizio Torelli
 *
 */
public class FakeDevice implements IDevice {

	/* (non-Javadoc)
	 * @see com.console.springernature.paint.model.IDevice#drawCanvas(com.console.springernature.paint.model.ICanvas)
	 */
	@Override
	public void drawCanvas(ICanvas canvas) {
	}

	/* (non-Javadoc)
	 * @see com.console.springernature.paint.model.IDevice#addCommandListener(com.console.springernature.paint.model.ICommandListener)
	 */
	@Override
	public void addCommandListener(ICommandListener listener) {
	}

	/* (non-Javadoc)
	 * @see com.console.springernature.paint.model.IDevice#removeCommandListener(com.console.springernature.paint.model.ICommandListener)
	 */
	@Override
	public void removeCommandListener(ICommandListener listener) {
	}

	/* (non-Javadoc)
	 * @see com.console.springernature.paint.model.IDevice#writeMessage(com.console.springernature.paint.model.DeviceMessage)
	 */
	@Override
	public void writeMessage(DeviceMessage message) {
	}

	/* (non-Javadoc)
	 * @see com.console.springernature.paint.model.IDevice#lookUpCommands()
	 */
	@Override
	public Map<String, String[]> lookUpCommands() {
		return null;
	}

	/* (non-Javadoc)
	 * @see com.console.springernature.paint.model.IDevice#unlockRequest()
	 */
	@Override
	public void unlockRequest() {
	}

	/* (non-Javadoc)
	 * @see com.console.springernature.paint.model.IDevice#start()
	 */
	@Override
	public void start() {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see com.console.springernature.paint.model.IDevice#waitForExit()
	 */
	@Override
	public void waitForExit() {
		// TODO Auto-generated method stub
		
	}

}
