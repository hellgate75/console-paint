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
 * Command that realize the creation of a line on the Canvas the user required coordinates
 * @author Fabrizio Torelli
 *
 */
public class CreateLine implements ICommand {
	private DeviceMessage lastMessage = null;
	private int x1 = 0;
	private int y1 = 0;
	private int x2 = 0;
	private int y2 = 0;

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
		if (parameters.length>3) {
			try {
				x1 = Integer.parseInt(parameters[0]);
				y1 = Integer.parseInt(parameters[1]);
				x2 = Integer.parseInt(parameters[2]);
				y2 = Integer.parseInt(parameters[3]);
				int maxWidth = canvas.getWidth()-1;
				int maxHeight = canvas.getHeight()-1;
				if (x1> maxWidth || y1>maxHeight || x2> maxWidth|| y2>maxHeight) {
					lastMessage = new DeviceMessage(ErrorCodeEnum.COMMAND_OUT_OF_BOUNDS, DeviceMessageTypeEnum.ERROR, "Required line exceed the terminal limits [max: columns=0.."+maxWidth+" rows=0.."+maxHeight+"].");
					return false;
				}
				if (x1!=x2 && y1!=y2) {
					lastMessage = new DeviceMessage(ErrorCodeEnum.COMMAND_NOT_VALID, DeviceMessageTypeEnum.ERROR, "Currently only horizontal or vertical lines are supported.");
					return false;
				}
				if (!GeometryHelper.getInstance().verifyPoints(x1, y1, x2, y2)) {
					int tmpX = x1;
					int tmpY = y1;
					x1=x2;
					y1=y2;
					x2=tmpX;
					y2=tmpY;
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
		return new String[] {"L x1 y1 x2 y2", "Create a new line from (x1,y1) to (x2,y2).", "Currently only horizontal or vertical lines are supported."};
	}

	/* (non-Javadoc)
	 * @see com.console.springernature.paint.model.ICommand#getCommandSymbol()
	 */
	@Override
	public String getCommandSymbol() {
		return "L";
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
			Canvas c = (Canvas)canvas;
			char[][] matrix = c.getCanvasMatrix();
			c.setCanvasMatrix(GeometryHelper.getInstance().drawLine(matrix, x1, y1, x2, y2));
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
