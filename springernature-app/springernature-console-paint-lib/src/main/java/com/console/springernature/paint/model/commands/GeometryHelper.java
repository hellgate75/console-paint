/**
 * 
 */
package com.console.springernature.paint.model.commands;

import com.console.springernature.paint.model.Canvas;

/**
 * Helper that provides aritmetical operations and algorithms to the Commands in order to realize their
 * owner features.
 * 
 * @author Fabrizio
 *
 */
public class GeometryHelper {

	private static GeometryHelper instance = null;
	
	/**
	 * Function that draw a line in the canvas according to the couple of 
	 * coordinates required in the user experience
	 * @param matrix - Canvas data matrix
	 * @param x1 - x coordinate of the first point
	 * @param y1 - y coordinate of the first point
	 * @param x2 - x coordinate of the second point
	 * @param y2 - y coordinate of the second point
	 * @return the transformed Canvas matrix
	 */
	public char[][] drawLine(char[][] matrix, int x1, int y1, int x2, int y2) {
		/*
		 * To draw a line we have the brush inherited by the Canvas and 
		 * 2 points x and y couple coordinates.
		 * We have alread those points sorted by position.
		 * We know that at least one couple of x or y coordionates are equals.
		 * 
		 * So we search if we need to draw and horizonthal or vertical line (the 
		 * only ones available just right now)
		 */
		
		if (x1==x2) {
			/*
			 * Vertical line
			 * We seek and change the array by row taking
			 * the x coordinate constant and varying the
			 * y coordinates along the interval determined by 
			 * the differences between y1 and y2  
			 */
			for(int i=y1; i<=y2; i++) {
				matrix[i][x1] = Canvas.PAINT_BRUSH;
				
			}
		}
		else {
			/*
			 * Horizontal line
			 * We seek and change the array by column taking
			 * the y coordinate constant and varying the
			 * x coordinates along the interval determined by 
			 * the differences between x1 and x2  
			 */
			for(int i=x1; i<=x2; i++) {
				matrix[y1][i] = Canvas.PAINT_BRUSH;
				
			}
		}
		return matrix;
	}

	/**
	 * Function that draw a rectangle or a line in the canvas according to the couple of 
	 * coordinates required in the user experience
	 * @param matrix - Canvas data matrix
	 * @param x1 - x coordinate of the first point
	 * @param y1 - y coordinate of the first point
	 * @param x2 - x coordinate of the second point
	 * @param y2 - y coordinate of the second point
	 * @return the transformed Canvas matrix
	 */
	public char[][] drawRectangle(char[][] matrix, int x1, int y1, int x2, int y2) {
		/*
		 * Given 2 points we evaluate the position of the other. We know that if the points have
		 *  at least one equal coordinate the rectangle become a line.
		 *  Elsewhere we have 4 scenarios
		 *  (P) = point (V) = virtual point (to be found)
		 *  
		 *  Scenario 1 :
		 *   (P1)               (V1)
		 *  
		 *   (V2)               (P2)
		 *  
		 *  Scenario 2 :
		 *   (P2)               (V1)
		 *  
		 *   (V2)               (P1)
		 *  
		 *  Scenario 3 :
		 *   (V1)               (P1)
		 *  
		 *   (P2)               (V2)
		 *  
		 *  Scenario 4 :
		 *   (V1)               (P2)
		 *  
		 *   (P1)               (V2)
		 *  
		 *  It means that here we have to check the major between the x and 
		 *  the y coordinates to determine which are the right couple of coordinates 
		 *  will be aasigned to the points (those coordinates extends the original ones)
		 *  
		 *  After that we need simple to mark the lines between :
		 *  P1 and V1, P1 and V2 
		 *  P2 and V1, P2 and V2
		 *  
		 *  In this way the rectangle is drawn ever correctly.
		 *  
		 * */
		if (x1!=x2 && y1!=y2) {
			/*
			 * Here we have a rectangle
			 * We prepare the variables for the maximum and minimum values for the x and y coordinates
			 */
			int xMin = x1 < x2 ? x1 : x2;
			int xMax = x1 > x2 ? x1 : x2;
			int yMin = y1 < y2 ? y1 : y2;
			int yMax = y1 > y2 ? y1 : y2;
			/*
			 * Here we prepare the variables for new 2 virtual points closing the rectangle
			 */
			int x3 = 0;
			int y3 = 0;
			int x4 = 0;
			int y4 = 0;
			/*
			 * Here we seek who is the minimum x variable and than the
			 * y minimum variable. We apply the 4 scenarios.
			 */
			if (xMin==x1) {
				if (yMin==y1) {
					x3 = xMax;
					y3 = yMin;
					x4=xMin;
					y4 = yMax;
					
				}
				else {
					x3 = xMin;
					y3 = yMin;
					x4=xMax;
					y4 = yMax;
				}
			}
			else {
				if (yMin==y2) {
					x3 = xMax;
					y3 = yMin;
					x4=xMin;
					y4 = yMax;
					
				}
				else {
					x3 = xMin;
					y3 = yMin;
					x4=xMax;
					y4 = yMax;
				}
			}
			/*
			 * Here we write the lines according the position for :
			 *  P1 and V1, P1 and V2 
			 */
			if (this.verifyPoints(x1, y1, x3, y3)) {
				matrix = this.drawLine(matrix, x1, y1, x3, y3);
			}
			else {
				matrix = this.drawLine(matrix, x3, y3, x1, y1);
			}
			if (this.verifyPoints(x1, y1, x4, y4)) {
				matrix = this.drawLine(matrix, x1, y1, x4, y4);
			}
			else {
				matrix = this.drawLine(matrix, x4, y4, x1, y1);
			}
			/*
			 * Here we write the lines according the position for :
			 *  P2 and V1, P2 and V2
			 */
			if (this.verifyPoints(x2, y2, x3, y3)) {
				matrix = this.drawLine(matrix, x2, y2, x3, y3);
			}
			else {
				matrix = this.drawLine(matrix, x3, y3, x2, y2);
			}
			if (this.verifyPoints(x2, y2, x4, y4)) {
				matrix = this.drawLine(matrix, x2, y2, x4, y4);
			}
			else {
				matrix = this.drawLine(matrix, x4, y4, x2, y2);
			}
		}
		else {
			/*
			 * Here we have a line
			 * We just draw the line according to the point position
			 */
			if (this.verifyPoints(x1, x2, y1, y2)) {
				matrix = this.drawLine(matrix, x1, x2, y1, y2);
			}
			else {
				matrix = this.drawLine(matrix, x2, y2, x1, y1);
			}
		}
		return matrix;
	}

	private void checkAndReplaceHorizontally(char[][] matrix, int y, int x1, char searchChar, char brush, int deepInVerticalLevel) {
		--deepInVerticalLevel;
		/*
		 * Search the x1 left-laying x coordinates for each y coordinate matching
		 */
		for(int x=x1; x>=0; x--) {
			if (matrix[y][x]==searchChar || matrix[y][x]==brush) {
				matrix[y][x] = brush;
				if (deepInVerticalLevel>0) {
					this.checkAndReplaceVertically(matrix, x, y, searchChar, brush, deepInVerticalLevel);
					this.checkAndReplaceOnTheDiagonals(matrix, x, y, searchChar, brush, deepInVerticalLevel);

				}
			}
			else {
				break;
			}
		}
		/*
		 * Search the x1 right-laying x coordinates for each y coordinate matching
		 */
		for(int x=x1+1; x<matrix[y].length; x++) {
			if (matrix[y][x]==searchChar || matrix[y][x]==brush) {
				matrix[y][x] = brush;
				if (deepInVerticalLevel>0) {
					this.checkAndReplaceVertically(matrix, x, y, searchChar, brush, deepInVerticalLevel);
					this.checkAndReplaceOnTheDiagonals(matrix, x, y, searchChar, brush, deepInVerticalLevel);
				}
			}
			else {
				break;
			}
		}
	}

	private void checkAndReplaceVertically(char[][] matrix, int x, int y1, char searchChar, char brush, int deepInHorizontalLevel) {
		--deepInHorizontalLevel;
		/*
		 * Search the y1 upper-laying y coordinates for each x matching
		 */
		for(int y=y1; y>=0; y--) {
			if (matrix[y][x]==searchChar || matrix[y][x]==brush) {
				matrix[y][x] = brush;
				if (deepInHorizontalLevel>0) {
					this.checkAndReplaceHorizontally(matrix, y, x, searchChar, brush, deepInHorizontalLevel);
					this.checkAndReplaceOnTheDiagonals(matrix, x, y, searchChar, brush, deepInHorizontalLevel);
				}
			}
			else {
				break;
			}
		}
		/*
		 * Search the y1 lower-laying y coordinates for each x matching
		 */
		for(int y=y1+1; y<matrix.length; y++) {
			if (matrix[y][x]==searchChar || matrix[y][x]==brush) {
				matrix[y][x] = brush;
				if (deepInHorizontalLevel>0) {
					this.checkAndReplaceHorizontally(matrix, y, x, searchChar, brush, deepInHorizontalLevel);
					this.checkAndReplaceOnTheDiagonals(matrix, x, y, searchChar, brush, deepInHorizontalLevel);
				}
			}
			else {
				break;
			}
		}
	}
	/*
	 * The diagonal search lookup along the diagonal directives and enlarge the search horizontally and vertically  :
	 * 
	 *       315           45
	 *          \        /
	 *               P           
	 *           /      \
	 *       225           135
	 *       
	 *   On each of those angular directions we extend the search horizontally and vertically as shown in following example :
	 *   
	 *   				⇑
	 *   			<- 045 ->
	 *   				⇓
	 *   				
	 */
	private void checkAndReplaceOnTheDiagonals(char[][] matrix, int x1, int y1, char searchChar, char brush, int deepInLevel) {
		/* 
		 * Start searching the x and y coordinates on the 315 degrees diagonal with x1 and y1 (excluded)
		 */
		for (int x=x1-1, y=y1+1; x>=0 && y<matrix.length;) {
			if (x>=0 && y<matrix.length && matrix[y][x]==searchChar || matrix[y][x]==brush) {
				matrix[y][x] = brush;
				this.checkAndReplaceHorizontally(matrix, y, x, searchChar, brush, deepInLevel);
				this.checkAndReplaceVertically(matrix, x, y, searchChar, brush, deepInLevel);
			}
			else {
				break;
			}
			x--; y++;
		}
		
		/* 
		 * Start searching the x and y coordinates on the 45 degrees diagonal with x1 and y1 (excluded)
		 */
		for (int x=x1+1, y=y1-1; x<matrix[y>0?y:0].length && y>=0; ) {
			if (x<matrix[y].length && y>=0 && matrix[y][x]==searchChar || matrix[y][x]==brush) {
				matrix[y][x] = brush;
				this.checkAndReplaceHorizontally(matrix, y, x, searchChar, brush, deepInLevel);
				this.checkAndReplaceVertically(matrix, x, y, searchChar, brush, deepInLevel);
			}
			else {
				break;
			}
			x++; y--;
		}

		/* 
		 * Start searching the x and y coordinates on the 135 degrees diagonal with x1 and y1 (excluded)
		 */
		for (int x=x1+1, y=y1+1; x<matrix[y<matrix.length ? y : matrix.length-1].length && y<matrix.length;) {
			if (x<matrix[y].length && y<matrix.length && matrix[y][x]==searchChar || matrix[y][x]==brush) {
				matrix[y][x] = brush;
				this.checkAndReplaceHorizontally(matrix, y, x, searchChar, brush, deepInLevel);
				this.checkAndReplaceVertically(matrix, x, y, searchChar, brush, deepInLevel);
			}
			else {
				break;
			}
			x++; y++;
		}
		
		/* 
		 * Start searching the x and y coordinates on the 225 degrees diagonal with x1 and y1 (excluded)
		 */
		for (int x=x1-1, y=y1-1; x>=0 && y>=0;) {
			if ( x>=0 && y>=0 && matrix[y][x]==searchChar || matrix[y][x]==brush) {
				matrix[y][x] = brush;
				this.checkAndReplaceHorizontally(matrix, y, x, searchChar, brush, deepInLevel);
				this.checkAndReplaceVertically(matrix, x, y, searchChar, brush, deepInLevel);
			}
			else {
				break;
			}
			x--; y--;
		}
	}
	
	/**
	 * Function that fill as the Bucket Fill function of the paint do the canvas starting from one 
	 * couple of coordinates required in the user experience
	 * @param matrix - Canvas data matrix
	 * @param x1 - x coordinate of the first point
	 * @param y1 - y coordinate of the first point
	 * @return the transformed Canvas matrix
	 * @param brush - Color (character) used to fill the matching area
	 * @return the transformed Canvas matrix
	 */
	public char[][] bucketFill(char[][] matrix, int x1, int y1, char brush) {
		/*
		 * To Consider the filling of a point we will drive a search for the 
		 * boundaries point around the selected one as :
		 *       315     0     45
		 *          \    |   /
		 *    270  --    P    --   90
		 *           /   |  \
		 *       225           135
		 *              180
		 * We enlarge the search vertically and horizontally for a global number 
		 * of 4 times including the point starts alternating one the each other.
		 * 
		 * We grant the coverage of most cases such as the fill of closed figure or the filling 
		 * of elements in every point of the screen end we look up to the diagonals in order to 
		 * find out spaces of disjuncted lines as the following example shows :
		 *  --------------------------------
		 *  |       x                      |
		 *  |       x                      |
		 *  |       x                      |
		 *  |       x                      |
		 *  |       x                      |
		 *  |       x                      |
		 *  |xxxxxxx                       |
		 *  |                              |
		 *  |                              |
		 *  |                              |
		 *  --------------------------------
		 *  
		 *  Probably some cases could not be got but the machine power, the complexity of the algorithm 
		 *  and the scope of the exercise do not get the needing to cross the current limitations.
		 *  
		 *  To improve better the process of the bucket filler probably is needed a specific analysis in the
		 *  graphic boundaries and the time to design and develop all the related test cases.
		 */

		
		/*
		 * Initially we select the search character by the point position in the canvas map
		 */
		char searchChar = matrix[y1][x1];

		/* 
		 * Start searching the x descending from the x1 (included)
		 */
		for (int x=x1; x>=0; x--) {
			if (matrix[y1][x]==searchChar || matrix[y1][x]==brush) {
				this.checkAndReplaceVertically(matrix, x, y1, searchChar, brush, 4);
			}
			else  {
				break;
			}
		}
		
		/* 
		 * Start searching the x ascending from the x1 (excluded)
		 */
		for (int x=x1+1; x<matrix[y1].length; x++) {
			if (matrix[y1][x]==searchChar || matrix[y1][x]==brush) {
				this.checkAndReplaceVertically(matrix, x, y1, searchChar, brush, 4);
			}
			else  {
				break;
			}
		}


		/* 
		 * Start searching the y descending from the y1 (included)
		 */
		for (int y=y1; y>=0; y--) {
			if (matrix[y][x1]==searchChar || matrix[y][x1]==brush) {
				this.checkAndReplaceHorizontally(matrix, y, x1, searchChar, brush, 4);
			}
			else  {
				break;
			}
		}
		
		/* 
		 * Start searching the y ascending from the y1 (excluded)
		 */
		for (int y=y1+1; y<matrix.length; y++) {
			if (matrix[y][x1]==searchChar || matrix[y][x1]==brush) {
				this.checkAndReplaceHorizontally(matrix, y, x1, searchChar, brush, 4);
			}
			else  {
				break;
			}
		}

		return matrix;
	}
	
	/**
	 * Function that allow to check if two point are in the correct position or if they
	 * need to be canged of position to draw correctly a line.
	 * @param matrix - Canvas data matrix
	 * @param x1 - x coordinate of the first point
	 * @param y1 - y coordinate of the first point
	 * @param x2 - x coordinate of the second point
	 * @param y2 - y coordinate of the second point
	 * @return the flag of matching or not
	 */
	public boolean verifyPoints(int x1, int y1, int x2, int y2) {
		if (x1==x2) {
			if (y1>y2) {
				return false;
			}
		}
		else {
			if (x1>x2) {
				return false;
			}
		}
		return true;
	}

	/**
	 * The GeometryHelper Singleton instance static getter method.
	 * @return The Singleton instance of the GeometryHelper
	 */
	public static final GeometryHelper getInstance() {
		if (instance==null) {
			instance = new GeometryHelper();
		}
		return instance;
	}
}
