package snake_game;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Snake extends JPanel {

	/**
	 * The length of each square in the checkers.
	 */
	public static int length;
	/**
	 * no. of rows in the frame.
	 */
	public static int rows;
	/**
	 * no. of columns in the frame.
	 */
	public static int columns;
	/**
	 * points of the player. Initalized to 3 points.
	 */
	public static int points;
	/**
	 * the speed of the snake, or 1/ the time interval between 2 frames.
	 */
	public static double speed;
	/**
	 * Gives the top-left corner of the snake's head
	 */
	private static Point position;
	/**
	 * Gives the top-left corner of the current position of the apple on the board.
	 */
	private static Point applePoint; 
	/**
	 * Gives the list of all the positions occupied by the snake.(top-left corner)
	 */
	public static ArrayList<Point> positions;
	/**
	 * Gives the positions of all the unoccupied positions on the board(top-left corners)
	 */
	public static ArrayList<Point> allpos;
	/**
	 * Shoes whether the apple is eaten by the snake or not. Helps to reproduce the
	 * apple everytime it returns true.
	 */
	static boolean appleEaten;

	/**
	 * Constructor. Helps to initialize all the variables before restarting the game.
	 * Also sets the settings for the Panel like its size.
	 */
	public Snake() {
		try {
			setSettings();
		} catch (Exception e) {
			e.printStackTrace();
		}
		initialize();
		this.setPreferredSize(new Dimension((rows * length), (columns * length)));
	}

	/**
	 * responsible for the movement of the snake, production of apples, and displaying it all 
	 * on the Snake Panel.
	 */
	public void paintComponent(Graphics g) {
		if(position.equals(applePoint))
			appleEaten = true;
		
		populate_allpos();
		changeDirection();
		modifyPointsArray();
		isGameOver();
		
		Graphics2D g2d = (Graphics2D) g;
		super.paintComponent(g);
		this.setBackground(Color.black);
		createCheckers(g2d);
		
		if(appleEaten) {
			Collections.shuffle(allpos);
			applePoint = allpos.get(0);
			appleEaten = false;
			points++;
		}
		g2d.setColor(Color.red);
		g2d.fillRect(applePoint.x, applePoint.y, length, length);
		

		for (Point pos : positions) {
			g2d.setColor(Color.white);
			g2d.fillRect((int) pos.getX(), (int) pos.getY(), length, length);
			g2d.setColor(Color.black);
			g2d.drawRect((int) pos.getX(), (int) pos.getY(), length, length);
		}
		
		allpos.clear();
		
		try {
			Thread.sleep((long) speed);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		repaint();
	}

	/**
	 * accepts a Graphic2D object to draw the checkers on the Snake Panel. 
	 * gives a good aesthetic to the program.
	 * @param g2d
	 */
	public static void createCheckers(Graphics2D g2d) {
		g2d.setColor(Color.white);
		g2d.setStroke(new BasicStroke(1));

		for (int i = 0; i < columns; i++) {
			for (int j = 0; j < rows; j++) {
				g2d.drawRect(length * j, length * i, length, length);
			}
		}
	}

	/**
	 * takes the top-left corners of all the squares on the board, and removes the entries occupied
	 * by the snake. 
	 */
	public static void populate_allpos() {
		for (int i = 0; i < columns; i++) {
			for (int j = 0; j < rows; j++) {
				allpos.add(new Point(i * length, j * length));
			}
		}
		allpos.removeAll(positions);
	}

	/**
	 * If the direction is changed by the KeyListener interface in the GameBoard class,
	 * this method helps to change the direction of the snake and maintain it.
	 */
	public static void changeDirection() {
		if (GameBoard.direction == 0)
			position.setLocation(position.getX(), position.getY() - 20);
		if (GameBoard.direction == 1)
			position.setLocation(position.getX() + 20, position.getY());
		if (GameBoard.direction == 2)
			position.setLocation(position.getX(), position.getY() + 20);
		if (GameBoard.direction == 3)
			position.setLocation(position.getX() - 20, position.getY());
	}

	/**
	 * Allows the snake to move freely by removing the last block of the snake, and adding one at the front.
	 */
	public static void modifyPointsArray() {
		if (positions.size() < points) {
			Point buffer = new Point();
			buffer.setLocation(position.getX(), position.getY());
			positions.add(0, buffer);
		} else if (positions.size() == points) {
			Point buffer = new Point();
			buffer.setLocation(position.getX(), position.getY());
			positions.remove(positions.size() - 1);
			positions.add(0, buffer);
		}
	}

	/**
	 * Checks whether the game is over and invokes <code>Main.gameOver()</code> if necessary
	 */
	public static void isGameOver() {
		if (position.x > (length) * rows-1 || position.y > (length) * columns-1 || position.x < 0 || position.y < 0) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {}
			Main.gameOver();
		}
		int counter = 0;
		for (Point pos : positions) {
			if (pos.equals(position) && counter != 0) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {}
				Main.gameOver();
			}
			counter++;
		}
	}

	/**
	 * invoked in the constructor. responsible for defaulting all the variables
	 * before restarting the game.
	 */
	public static void initialize() {
		length = 20;
		points = 3;
		position = new Point(((length * rows) / 2) - (length / 2),((length * columns) / 2) - (length / 2));
		positions = new ArrayList<>();
		allpos = new ArrayList<>();
		applePoint = new Point(length * (rows- 5), ((length * columns) / 2) - (length / 2));
		appleEaten = false;
	}

	/**
	 * allows the user to change settings like no. of rows and columns, and the speed multiplier
	 * through the settings file. 
	 * @throws Exception
	 */
	public static void setSettings() throws Exception {
		BufferedReader br = new BufferedReader(new FileReader(new File("Prerequisites\\settings")));
		String text = "";
		while(br.readLine() != null) {
			text = text.concat(br.readLine());
		}
		String[] values = text.split(",");
		rows = Integer.valueOf(values[0]);
		columns = Integer.valueOf(values[1]);
		speed = 200 / Double.valueOf(values[2]);
		
	}
	
}
