/**
 * 
 */
package com.console.springernature.paint.model.test;

import com.console.springernature.paint.model.CommandResponse;
import com.console.springernature.paint.model.ICanvas;
import com.console.springernature.paint.model.ICommand;
import com.console.springernature.paint.model.IDevice;

/**
 * Fake Canvas for testing reasons.
 * @author Fabrizio
 *
 */
public class FakeCanvas implements ICanvas {

	/* (non-Javadoc)
	 * @see com.console.springernature.paint.model.ICommandListener#dispatchCommand(com.console.springernature.paint.model.IDevice, java.lang.String)
	 */
	@Override
	public void dispatchCommand(IDevice device, String command) {
	}

	/* (non-Javadoc)
	 * @see com.console.springernature.paint.model.ICommandListener#dispatchCommand(com.console.springernature.paint.model.IDevice, java.lang.String[])
	 */
	@Override
	public void dispatchCommand(IDevice device, String[] parsedCommand) {
	}

	/* (non-Javadoc)
	 * @see com.console.springernature.paint.model.ICanvas#create(int, int)
	 */
	@Override
	public void create(int width, int height) {
	}

	/* (non-Javadoc)
	 * @see com.console.springernature.paint.model.ICanvas#getWidth()
	 */
	@Override
	public int getWidth() {
		return 0;
	}

	/* (non-Javadoc)
	 * @see com.console.springernature.paint.model.ICanvas#getHeight()
	 */
	@Override
	public int getHeight() {
		return 0;
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
	}

	/* (non-Javadoc)
	 * @see com.console.springernature.paint.model.ICanvas#hasADeviceAssigned()
	 */
	@Override
	public boolean hasADeviceAssigned() {
		return false;
	}

	/* (non-Javadoc)
	 * @see com.console.springernature.paint.model.ICanvas#isCreated()
	 */
	@Override
	public boolean isCreated() {
		return false;
	}

	/* (non-Javadoc)
	 * @see com.console.springernature.paint.model.ICanvas#lookUpCommand(java.lang.String, java.lang.String[])
	 */
	@Override
	public CommandResponse lookUpCommand(String command, String[] arguments) {
		// TODO Auto-generated method stub
		return null;
	}

}
