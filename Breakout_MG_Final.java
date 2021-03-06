
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
 
public class Breakout_MG_Final extends GraphicsProgram {
 
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
 
/**Ball velocity*/   
    private double vx, vy;
 
/**Random number generator for vx*/   
    private RandomGenerator rgen = RandomGenerator.getInstance();
 
/** Animation delay or pause time between ball moves */   
    private static final int DELAY = 10;
 
/* Method: run() */
/** Runs the Breakout program. */
    public void run() {
    	
		// Resize canvas regardless of original size
		if(true) {
			// Increase size of canvas so that game will fit
			setSize(APPLICATION_WIDTH + BRICK_SEP, APPLICATION_HEIGHT);
		}
		
		printOpeningMessage();
    	print321();
    	
        for(int i=0; i < NTURNS; i++) {
        	

            setUpGame();          
            playGame();
            if(brickCounter == 0) {
                ball.setVisible(false);
                printWinner();
                break;
            }
            if(brickCounter > 0) {
                removeAll();
            }
            
            /* Check we have not entered a "game over" state, i.e. the user still has lives remaining.
             * If so, prompt the user to click to continue the game.
             */
            if (i < NTURNS - 1) {
                clickToContinue();
            }

        }
        if(brickCounter > 0) {
            printGameOver();
        }
    }
 
    private void setUpGame() {
    	
        drawBricks(APPLICATION_WIDTH / 2, BRICK_Y_OFFSET);
        drawPaddle();
        drawBall();
    }
 
    //adding an individual brick object
    private GRect brick;
 
    //drawing all the bricks necessary for the game
    private void drawBricks(double cx, double cy) {                
 
        /* We need to have several columns in each row
         * so there need to be two for loops; 
         * one loop for the rows and one loop for the columns.
         */
 
        for (int row = 0; row < NBRICK_ROWS; row++) {
 
            for (int column = 0; column < NBRICKS_PER_ROW; column++) {
 
                /* To get the x coordinate for the starting width:
                 *     start at the center width, 
                 *     subtract half of the bricks (width) in the row,  
                 *     subtract half of the separations (width) between the bricks in the row,
                 * now you're at where the first brick should be, 
                 * so for the starting point of the next bricks in the column, you need to: 
                 *     add a brick width 
                 *     add a separation width
                 */
 
                double x = cx - (NBRICKS_PER_ROW * BRICK_WIDTH) / 2 - ((NBRICKS_PER_ROW - 1) * BRICK_SEP) / 2 + column * BRICK_WIDTH + column * BRICK_SEP;
 
                /* To get the y coordinate of the starting height:
                 *     start at the given length from the top for the first row,
                 *     then add a brick height and a brick separation for each of the following rows
                 */
 
                double y = cy + row * BRICK_HEIGHT + row * BRICK_SEP;
 
                brick = new GRect(x, y, BRICK_WIDTH, BRICK_HEIGHT);
                add (brick);
                brick.setFilled(true);
 
                // Set colors depending on which row the bricks are in
 
                if (row < 2) {
                    brick.setColor(Color.RED);
                }
                if (row == 2 || row == 3) {
                    brick.setColor(Color.ORANGE);
                }
                if (row == 4 || row == 5) {
                    brick.setColor(Color.YELLOW);
                }
                if (row == 6 || row == 7) {
                    brick.setColor(Color.GREEN);
                }
                if (row == 8 || row == 9) {
                    brick.setColor(Color.CYAN);
                }
            }
        }
    }
 
    // Add individual paddle object
    private GRect paddle;
 
    // Paddle set-up
    private void drawPaddle() {
        // Starting the paddle in the middle of the screen
        double x = getWidth() / 2 - PADDLE_WIDTH / 2; 
        /* The paddle height stays consistent throughout the game.
         * We need to make sure to subtract the PADDLE_HEIGHT, 
         * since the rectangle gets drawn from the top left corner
         */
        double y = getHeight() - PADDLE_Y_OFFSET - PADDLE_HEIGHT;
        paddle = new GRect (x, y, PADDLE_WIDTH, PADDLE_HEIGHT);
        paddle.setFilled(true);
        add (paddle);
        addMouseListeners();
    }
 
    // Make the mouse track the paddle
    public void mouseMoved(MouseEvent e) {
        /* The mouse tracks the middle point of the paddle. 
         * If the middle point of the paddle is between half paddle width of the screen
         * and half a paddle width before the end of the screen, 
         * the x location of the paddle is set at where the mouse is minus half a paddle's width, 
         * and the height remains the same
         */
        if ((e.getX() < getWidth() - PADDLE_WIDTH / 2) && (e.getX() > PADDLE_WIDTH / 2)) {
            paddle.setLocation(e.getX() - PADDLE_WIDTH / 2, getHeight() - PADDLE_Y_OFFSET - PADDLE_HEIGHT);
        }
    }
 
    // Add an individual ball object
    private GOval ball;
 
    // Ball set-up
    private void drawBall() {
        double x = getWidth() / 2 - BALL_RADIUS;
        double y = getHeight() / 2 - BALL_RADIUS;
        ball = new GOval(x, y, BALL_RADIUS, BALL_RADIUS);
        ball.setFilled(true);
        add(ball);
    }
 
    private void playGame() {
        //waitForClick();
        getBallVelocity();
        while (true) {
            moveBall();
            if (ball.getY() >= getHeight()) {
                break;
            }
            if (brickCounter == 0) {
                break;
            }
        }
    }
 
    private void getBallVelocity() {
        vy = 4.0;
        vx = rgen.nextDouble(1.0, 3.0);
        if (rgen.nextBoolean(0.5)) {
            vx = -vx; 
        }
    }
 
    private void moveBall() {
        ball.move(vx, vy);
        /* Check for walls.
         * We need to get vx and vy at the point closest to 0 or the other edge
         */
        if ((ball.getX() - vx <= 0 && vx < 0 ) || (ball.getX() + vx >= (getWidth() - BALL_RADIUS * 2) && vx > 0)) {
            vx = -vx;
        }
        
        /* We don't need to check for the bottom wall, 
         * since the ball can fall through the wall at that point
         */
        if ((ball.getY() - vy <= 0 && vy < 0 )) {
            vy = -vy;
        }
 
        // Check for other objects
        GObject collider = getCollidingObject();
        if (collider == paddle) {
            
        	/* We need to make sure that the ball only bounces off the top part of the paddle  
             * and also that it doesn't "stick" to it if different sides of the ball hit the paddle quickly and get the ball "stuck" on the paddle.
             * I ran "println ("vx: " + vx + ", vy: " + vy + ", ballX: " + ball.getX() + ", ballY: " + ball.getY());"
             * and found that the ball.getY() changes by 4 every time, instead of 1,
             * so it never reaches exactly the the height at which the ball hits the paddle (paddle height + ball height), 
             * therefore, I estimate the point to be greater or equal to the height at which the ball hits the paddle, 
             * but less than the height where the ball hits the paddle minus 4. 
             */
            if (ball.getY() >= getHeight() - PADDLE_Y_OFFSET - PADDLE_HEIGHT - BALL_RADIUS*2 && ball.getY() < getHeight() - PADDLE_Y_OFFSET - PADDLE_HEIGHT - BALL_RADIUS*2 + 4) {
                vy = -vy;    
            }
        }
        
        /* Since we lay down a row of bricks, the last brick in the brick wall is assigned the value brick.
         * so we narrow it down by saying that the collier does not equal to a paddle or null, 
         * so all that is left is the brick
         */
        else if (collider != null) {
            remove(collider); 
            brickCounter--;
            vy = -vy;
        }
        
        pause (DELAY);
    }
 
    private GObject getCollidingObject() {
 
        if((getElementAt(ball.getX(), ball.getY())) != null) {
             return getElementAt(ball.getX(), ball.getY());
          }
        else if (getElementAt( (ball.getX() + BALL_RADIUS*2), ball.getY()) != null ){
             return getElementAt(ball.getX() + BALL_RADIUS*2, ball.getY());
          }
        else if(getElementAt(ball.getX(), (ball.getY() + BALL_RADIUS*2)) != null ){
             return getElementAt(ball.getX(), ball.getY() + BALL_RADIUS*2);
          }
        else if(getElementAt((ball.getX() + BALL_RADIUS*2), (ball.getY() + BALL_RADIUS*2)) != null ){
             return getElementAt(ball.getX() + BALL_RADIUS*2, ball.getY() + BALL_RADIUS*2);
          }
        
        // We need to return null if there are no objects present
        else{
        	
             return null;
          }
    }
    
    // The following methods contain messages etc.
    
	/* The following method firstly creates a set of messages and then moves
	 * each of them to the correct location on the canvas. */
    private void printOpeningMessage() {
    	
    	/* N.B. For some reason, even though getWidth() was returning the correct value,
    	 * the "welcome" label was not being positioned correctly on the screen.
    	 * To rectify this, the formula was edited as seen below. 
    	 */
    	GLabel welcome = new GLabel ("Let the game BEGIN!", (APPLICATION_WIDTH + BRICK_SEP)/2, APPLICATION_HEIGHT/2);
    	welcome.move(-welcome.getWidth()/2, -welcome.getHeight());
    	
    	GLabel instruction = new GLabel ("Click to continue...", getWidth()/2, getHeight()/2);
    	instruction.move(-instruction.getWidth()/2, 50);
    	
    	add (welcome);
    	add (instruction);
    	
    	// This click allows the method to continue, i.e. the labels can be removed
    	waitForClick();
    	
    	// Remove both labels from canvas so the game can continue
    	remove (welcome);
    	remove (instruction);
    }
    
    // The following method ensures the user must click to continue the game each time they lose a life
    private void clickToContinue() {
    	
    	GLabel instruction = new GLabel ("Click to continue...", getWidth()/2, getHeight()/2);
    	instruction.move(-instruction.getWidth()/2, 50);
    	add (instruction);
    	
    	waitForClick();
    	
    	remove (instruction);
    }
    
    // Print countdown 3..2..1
    private void print321() {
    	
    	// Create the three labels and place them, initially, at the top left of the canvas 
    	GLabel three = new GLabel ("3...", 0, 0);
    	GLabel two = new GLabel ("2...", 0, 0);
    	GLabel one = new GLabel ("1...", 0, 0);
    	
    	/* The label "2..." will be centered on the canvas.
    	 * Other labels are set out in relation to label "2..."
    	 */
    	two.move(getWidth()/2 - two.getWidth()/2, getHeight()/2 - two.getHeight());
    	three.move(getWidth()/2 - two.getWidth()/2 - three.getWidth(), getHeight()/2 - two.getHeight());
    	one.move(getWidth()/2 + two.getWidth()/2, getHeight()/2 - two.getHeight());
    	
    	// Add labels to canvas, one at a time
    	add (three);
    	// Pause for effect
    	pause (500);
    	add (two);
    	pause (500);
    	add (one);
    	pause (500);
    	
    	// Remove labels from canvas so the game can begin!
    	remove(three);
    	remove(two);
    	remove(one);
    }
    
    private void printGameOver() {
        GLabel gameOver = new GLabel ("Game Over", getWidth()/2, getHeight()/2);
        gameOver.move(-gameOver.getWidth()/2, -gameOver.getHeight());
        gameOver.setColor(Color.RED);
        add (gameOver);
    }
 
    private int brickCounter = 100;
 
    private void printWinner() {
        GLabel Winner = new GLabel ("Congratulations!!!", getWidth()/2, getHeight()/2);
        Winner.move(-Winner.getWidth()/2, -Winner.getHeight());
        Winner.setColor(Color.RED);
        add (Winner);
    }
}