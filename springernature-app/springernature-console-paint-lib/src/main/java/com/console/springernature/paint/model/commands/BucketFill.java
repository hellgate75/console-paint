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
 * Command that realize the Bucket Filler operation on the Canvas matching in a point
 * and repace with the given color (character) not similar to the frame.
 * @author Fabrizio Torelli
 *
 */
public class BucketFill implements ICommand {
	private DeviceMessage lastMessage = null;
	private int x1 = 0;
	private int y1 = 0;
	private char brush;

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
		if (parameters.length>2) {
			try {
				x1 = Integer.parseInt(parameters[0]);
				y1 = Integer.parseInt(parameters[1]);
				brush = parameters[2].charAt(0);
				int maxWidth = canvas.getWidth()-1;
				int maxHeight = canvas.getHeight()-1;
				if (x1> maxWidth || y1>maxHeight) {
					lastMessage = new DeviceMessage(ErrorCodeEnum.COMMAND_OUT_OF_BOUNDS, DeviceMessageTypeEnum.ERROR, "Required rectangle exceed the terminal limits [max: columns=0.."+maxWidth+" rows=0.."+maxHeight+"].");
					return false;
				}
				if (brush == Canvas.HORIZONTHAL_FRAME_BRUSH.charAt(0) || brush == Canvas.VERTICAL_FRAME_BRUSH.charAt(0)) {
					lastMessage = new DeviceMessage(ErrorCodeEnum.COMMAND_NOT_VALID, DeviceMessageTypeEnum.ERROR, "The color cannot be the same of the frame containing the canvas. Not allowed colors : '"+Canvas.HORIZONTHAL_FRAME_BRUSH.charAt(0)+"' and '"+Canvas.VERTICAL_FRAME_BRUSH.charAt(0)+"'.");
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
		return new String[] {"B x y c", "Fill the entire area connected to (x,y) with colour c represented by a letter as the bucket fill operation do..", "The Canvas's frame colors are not allowed."};
	}

	/* (non-Javadoc)
	 * @see com.console.springernature.paint.model.ICommand#getCommandSymbol()
	 */
	@Override
	public String getCommandSymbol() {
		return "B";
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
			c.setCanvasMatrix(GeometryHelper.getInstance().bucketFill(matrix, x1, y1, brush));
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
