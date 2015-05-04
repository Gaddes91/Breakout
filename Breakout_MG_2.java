/*
 * File: Breakout.java
 * -------------------
 * Name:
 * Section Leader:
 * 
 * This file will eventually implement the game of Breakout.
 */

import acm.graphics.*;
import acm.program.*;
import acm.util.*;

import java.applet.*;
import java.awt.*;
import java.awt.event.*;

public class Breakout_MG_2 extends GraphicsProgram {

	/** Width and height of application window in pixels */
	public static final int APPLICATION_WIDTH = 400;
	public static final int APPLICATION_HEIGHT = 600;

	/** Dimensions of game board (usually the same) */
	private static final int WIDTH = APPLICATION_WIDTH;
	private static final int HEIGHT = APPLICATION_HEIGHT;

	/** Dimensions of the paddle */
	private static final int PADDLE_WIDTH = 60;
	private static final int PADDLE_HEIGHT = 10;

	/** Offset of the paddle up from the bottom */
	private static final int PADDLE_Y_OFFSET = 30;

	/** Number of bricks per row */
	private static final int NBRICKS_PER_ROW = 10;

	/** Number of rows of bricks */
	private static final int NBRICK_ROWS = 10;

	/** Separation between bricks */
	private static final int BRICK_SEP = 4;

	/** Width of a brick */
	private static final int BRICK_WIDTH =
	  (WIDTH - (NBRICKS_PER_ROW - 1) * BRICK_SEP) / NBRICKS_PER_ROW;

	/** Height of a brick */
	private static final int BRICK_HEIGHT = 8;

	/** Radius of the ball in pixels */
	private static final int BALL_RADIUS = 10;

	/** Offset of the top brick row from the top */
	private static final int BRICK_Y_OFFSET = 70;

	/** Number of turns */
	private static final int NTURNS = 3;

	/* Method: init() */
	/** Sets up the application - layout etc. */
	public void init() {
		
		// Resize canvas regardless of original size
		if(true) {
			// Increase size of canvas so that pyramid will fit
			setSize(APPLICATION_WIDTH, APPLICATION_HEIGHT);
		}
		
		
		
		// Create bricks of each colour
		GRect redBrick = new GRect(BRICK_WIDTH, BRICK_HEIGHT);
		redBrick.setColor(Color.RED);
		redBrick.setFillColor(Color.RED);
		redBrick.setFilled(true);
		
		GRect orangeBrick = new GRect(BRICK_WIDTH, BRICK_HEIGHT);
		orangeBrick.setColor(Color.ORANGE);
		orangeBrick.setFillColor(Color.ORANGE);
		orangeBrick.setFilled(true);
		
		GRect yellowBrick = new GRect(BRICK_WIDTH, BRICK_HEIGHT);
		yellowBrick.setColor(Color.YELLOW);
		yellowBrick.setFillColor(Color.YELLOW);
		yellowBrick.setFilled(true);
		
		GRect greenBrick = new GRect(BRICK_WIDTH, BRICK_HEIGHT);
		greenBrick.setColor(Color.GREEN);
		greenBrick.setFillColor(Color.GREEN);
		greenBrick.setFilled(true);
		
		GRect cyanBrick = new GRect(BRICK_WIDTH, BRICK_HEIGHT);
		cyanBrick.setColor(Color.CYAN);
		cyanBrick.setFillColor(Color.CYAN);
		cyanBrick.setFilled(true);

		
		

		// Counter to hold remaining number of rows to be laid.
		int rowsRemaining = 0;
		// Initialise row counter (row No. 1)
		int rowCount = 0;
		// Initialise generic "brick" as red
		GRect brick = redBrick;
		
		// Start point (x-axis) for each individual brick
		int brickX = 0;
		/* Start point (y-axis) for each individual brick
		   The offset will increase by the size of a brick each time we move down a row */
		int brickY = BRICK_Y_OFFSET;
		
		// Runs until all rows have been laid
		while (rowCount < NBRICK_ROWS) {
			
			// Changes the colour of the brick depending upon the row number 
			if (rowCount == 0)  {
				brick = redBrick;
			} else if (rowCount == 1)  {
				brick = redBrick;
			} else if (rowCount == 2) {
				brick = orangeBrick;
			} else if (rowCount == 3) {
				brick = orangeBrick;
			} else if (rowCount == 4) {
				brick = yellowBrick;
			} else if (rowCount == 5) {
				brick = yellowBrick;
			} else if (rowCount == 6) {
				brick = greenBrick;
			} else if (rowCount == 7) {
				brick = greenBrick;
			} else if (rowCount == 8) {
				brick = cyanBrick;
			} else if (rowCount == 9) {
				brick = cyanBrick;
			}	
			
			
			// Counter to hold number of bricks remaining in each row.
			int bricksInRow = 0;
			
			// Runs until 10No bricks have been laid
			while (bricksInRow < NBRICKS_PER_ROW) {
				
				// Create new brick
				GRect currentBrick = new GRect(brickX, brickY, BRICK_WIDTH, BRICK_HEIGHT);
				// Set brick location
				currentBrick.setLocation(brickX, brickY);
				// Add to canvas
				add(brick);
				
				
				
				
				
				
				// Set brick location
				brick.setLocation(brickX, brickY);
				add(brick);
				
				// Shift x-location of next brick to be laid
				brickX = (BRICK_WIDTH + BRICK_SEP);
				
				// Shift y-location of next brick to be laid
				brickY = (brickY + BRICK_HEIGHT + BRICK_SEP);  
				
				// Append one to number of bricks in row
				bricksInRow ++;
				
			}
			
			// Append one to rowCount
			rowCount ++;
			
			
		}
		
	}

	
	/* Method: run() */
	/** Runs the Breakout program. */
	public void run() {
		init();
	}
	
	
}
