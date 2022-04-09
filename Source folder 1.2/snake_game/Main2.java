package snake_game;

import javax.swing.JFrame;

/**
 * The main class, containing the main method, which is called to start
 * execution of the program.
 * @author Ojas Pednekar
 *
 */
public class Main2 {
	
	static JFrame frame;

	public static void main(String[] args) {
		startGame();
	}
	
	/**
	 * Called upon to start the game sequence.
	 */
	public static void startGame() {
		JFrame gameFrame = new GameBoard2();
		frame = gameFrame;
	}
	
	/**
	 * Called when the game ends. Acts as a link between the GameBoard frame 
	 * and the GameOver frame, by disposing the former, and initializing the
	 * latter. 
	 */
	public static void gameOver() {
		frame.dispose();
		new GameOver2();
	}
}
