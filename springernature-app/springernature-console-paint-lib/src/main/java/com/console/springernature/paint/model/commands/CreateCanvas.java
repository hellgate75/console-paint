/**
 * 
 */
package com.console.springernature.paint.model.commands;

import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import com.console.springernature.paint.model.Canvas;
import com.console.springernature.paint.model.DeviceMessage;
import com.console.springernature.paint.model.DeviceMessageTypeEnum;
import com.console.springernature.paint.model.ErrorCodeEnum;
import com.console.springernature.paint.model.ICanvas;
import com.console.springernature.paint.model.ICommand;

/**
  * Command that realize the Canvas creation according to the user required dimensions
* @author Fabrizio Torelli
 *
 */
public class CreateCanvas implements ICommand {
	private DeviceMessage lastMessage = null;
	private int columns = 0;
	private int rows = 0;

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
		lastMessage = null;
		if(canvas==null || !Canvas.class.isAssignableFrom(canvas.getClass())) {
			lastMessage = new DeviceMessage(ErrorCodeEnum.INVALID_CANVAS, DeviceMessageTypeEnum.ERROR, "Current cavas is not valid.");
			return false;
		}
		if (parameters.length>1) {
			try {
				columns = Integer.parseInt(parameters[0]);
				rows = Integer.parseInt(parameters[1]);
				int maxWidth = ((Canvas)canvas).MAX_CONSOLE_WIDTH;
				int maxHeight = ((Canvas)canvas).MAX_CONSOLE_HEIGHT;
				if (columns> maxWidth|| rows>maxHeight) {
					lastMessage = new DeviceMessage(ErrorCodeEnum.COMMAND_OUT_OF_BOUNDS, DeviceMessageTypeEnum.ERROR, "Required canvas size exceed the terminal limits [max: columns="+maxWidth+" rows="+maxHeight+"].");
					return false;
				}
				return true;
			} catch (NumberFormatException e) {
			}
		}
		lastMessage = new DeviceMessage(ErrorCodeEnum.INVALID_PARAMETERS, DeviceMessageTypeEnum.ERROR, "Invalid number or type of parameters.", this.getSyntax());
		return false;
	}

	/* (non-Javadoc)
	 * @see com.console.springernature.paint.model.ICommand#getSyntax()
	 */
	@Override
	public String[] getSyntax() {
		return new String[] {"C w h", "Create a new Canvas with w columns and h rows"};
	}

	/* (non-Javadoc)
	 * @see com.console.springernature.paint.model.ICommand#getCommandSymbol()
	 */
	@Override
	public String getCommandSymbol() {
		return "C";
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
		if (lastMessage==null) {
			canvas.create(columns, rows);
		}
		return new ArrayList<Point2D>(0);
	}

	/* (non-Javadoc)
	 * @see com.console.springernature.paint.model.ICommand#executeGeometry(com.console.springernature.paint.model.ICanvas)
	 */
	@Override
	public GeneralPath executeGeometry(ICanvas canvas) {
		return null;
	}

}
