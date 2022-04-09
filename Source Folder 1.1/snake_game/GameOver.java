package snake_game;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * This is the frame which comes up after the game is lost. It shows your score, and allows you
 * to restart the game, or to close the game. Called on by <code>Main.gameOver()</code> after 
 * disposing the <code>GameBoard</code> frame. The buttons restart and close implement
 * the ActionListeer interface to respond to user clicks.
 * @author Ojas Pednekar
 */
@SuppressWarnings("serial")
public class GameOver extends JFrame implements ActionListener{

	/**
	 * Allows you to restart the game.
	 */
	JButton restart = new JButton("Restart Game");
	/**
	 * Allows you to close the game. Close Button does nothing.
	 */
	JButton close = new JButton("Close Game");

	/**
	 * <code>Constructor</code>. Sets the default settings of the frame.
	 * Also defaults the settings of the components. 
	 */
	public GameOver() {
		this.setSize(new Dimension(500, 100));
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		this.setLayout(new BorderLayout());
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setTitle("Game Over!");
		JPanel main = new JPanel();
		JPanel south = new JPanel();
		south.setLayout(new GridLayout(1,2));
		south.add(restart);
		south.add(close);
		main.add(new JLabel("Game Over! Your score was: " + Snake.points));
		this.add(main);
		restart.addActionListener(this);
		close.addActionListener(this);
		this.add(south, BorderLayout.SOUTH);
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == restart) {
			this.dispose();
			Main.startGame();
		}
		if(e.getSource() == close) {
			System.exit(0);
		}
	}

}
