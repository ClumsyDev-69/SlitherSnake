package snake_game;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

/**
 * The <code>GameBoard</code> class is the main frame of the program, which also contains the game.
 * It acts as a container for the snake Panel, and also implements the KeyListener interface,
 * which allows the user to change the direction of the snake through the arrow keys.
 * @author Ojas Pednekar
 */
@SuppressWarnings("serial")
public class GameBoard2 extends JFrame implements KeyListener {

	/**
	 * represents the direction of snake. 0 for up, 1 for right, 2 for down, 3 for left.
	 */
	public static int direction = 1;

	/**
	 * responsible for all the frame settings, and initializing and adding the snake
	 * panel to the frame. Also sets the default direction to 1(right). 
	 */
	public GameBoard2() {
		Snake2 snake = new Snake2();
		this.add(snake);
		this.setBackground(Color.black);
		this.addKeyListener(this);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setTitle("Snake");
		this.setResizable(false);
		this.pack();
		direction = 1;
		this.setVisible(true);
		this.setLocationRelativeTo(null);
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == 38 && direction != 2) {
			direction = 0;
		}
		if (e.getKeyCode() == 39 && direction != 3) {
			direction = 1;
		}
		if (e.getKeyCode() == 40 && direction != 0) {
			direction = 2;
		}
		if (e.getKeyCode() == 37 && direction != 1) {
			direction = 3;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}
	
}
