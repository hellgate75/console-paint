/**
 * 
 */
package com.console.springernature.paint.model.commands;

import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import com.console.springernature.paint.model.DeviceMessage;
import com.console.springernature.paint.model.ICanvas;
import com.console.springernature.paint.model.ICommand;

/**
 * Command that realize the exit for the user experience
 * @author Fabrizio Torelli
 *
 */
public class QuitConsole implements ICommand {
	private DeviceMessage lastMessage = null;
	/* (non-Javadoc)
	 * @see com.console.springernature.paint.model.ICommand#getLastMessage()
	 */
	@Override
	public DeviceMessage getLastMessage() {
		return lastMessage;
	}

	/* (non-Javadoc)
	 * @see com.console.springernature.paint.model.ICommand#acceptParameters(com.console.springernature.paint.model.ICanvas, java.lang.String[])
	 */
	@Override
	public boolean acceptParameters(ICanvas canvas, String[] parameters) {
		return true;
	}

	/* (non-Javadoc)
	 * @see com.console.springernature.paint.model.ICommand#getSyntax()
	 */
	@Override
	public String[] getSyntax() {
		return new String[] {"Q", "Quit the console"};
	}

	/* (non-Javadoc)
	 * @see com.console.springernature.paint.model.ICommand#getCommandSymbol()
	 */
	@Override
	public String getCommandSymbol() {
		return "Q";
	}

	/* (non-Javadoc)
	 * @see com.console.springernature.paint.model.ICommand#isComposedCommand()
	 */
	@Override
	public boolean isComposedCommand() {
		return false;
	}

	/* (non-Javadoc)
	 * @see com.console.springernature.paint.model.ICommand#execute(com.console.springernature.paint.model.ICanvas)
	 */
	@Override
	public List<Point2D> execute(ICanvas canvas) {
		System.exit(0);
		return new ArrayList<Point2D>(0);
	}

	/* (non-Javadoc)
	 * @see com.console.springernature.paint.model.ICommand#executeGeometry(com.console.springernature.paint.model.ICanvas)
	 */
	@Override
	public GeneralPath executeGeometry(ICanvas canvas) {
		System.exit(0);
		return new GeneralPath();
	}

}
