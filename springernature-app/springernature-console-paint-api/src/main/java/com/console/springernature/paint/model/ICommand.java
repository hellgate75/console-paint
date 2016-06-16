/**
 * 
 */
package com.console.springernature.paint.model;

import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.util.List;


/**
 * Command interface that realize a Canvas transformation.
 * Only horizontal and vertical lines or orthogonal shapes are allowed in this version.
 * Overlapping of shapes is allowed.
 * 
 * @author Fabrizio Torelli
 * @see <a href="DeviceMessage.html">DeviceMessage</a>, <a href="ICanvas.html">ICanvas</a>, java.awt.geom.Point2D, java.util.List, java.awt.geom.GeneralPath
 */
public interface ICommand {

	/**
	 * Returns the last execution message if available or null if it's not applicable.
	 * <br/>See <a href="DeviceMessage.html">DeviceMessage</a>
	 * @return The message to be exposed to the device or null
	 */
	DeviceMessage getLastMessage();
	
	/**
	 * Accept the parameters related to a Canvas and compliant to the inner checks.
	 * <br/>See <a href="ICanvas.html">ICanvas</a>
	 * @param canvas - Canvas to check the acceptance of the parameters
	 * @param parameters - parameters to be applied to the canvas.
	 * @return The acceptance status for the command related to the internal check and the canvas appliance.
	 * @see ICanvas
	 */
	boolean acceptParameters(ICanvas canvas, String[] parameters);
	
	/**
	 * Return the command syntax message on multiple lines matrix.
	 * @return The command syntax row array
	 */
	String[] getSyntax();
	
	/**
	 * Get the command unique symbol that distinguish the beginning of the request parsed by the engine.
	 * @return The unique command symbol
	 */
	String getCommandSymbol();
	
	/**
	 * Returns the composition status of the command and it means it's used the executeGeometry instead of the execute to draw the canvas.
	 * @return the flag of a multiple shapes' command
	 */
	boolean isComposedCommand();
	
	/**
	 * Transforms the canvas according to the command instruments.
	 * Execute the canvas transformation and return the points to be applied.
	 * <br/>See java.awt.geom.Point2D, java.util.List
	 * @param canvas Canvas to be transformed.
	 * @return The point list written on the canvas.
	 */
	List<Point2D> execute(ICanvas canvas);

	/**
	 * Transforms the canvas according to the command instruments.
	 * Execute the canvas transformation and return the general path containing the drawn shapes.
	 * <br/>See java.awt.geom.GeneralPath
	 * @param canvas Canvas to be transformed.
	 * @return The general path containing the drawn shapes
	 */
	GeneralPath executeGeometry(ICanvas canvas);
}
