package game;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class Game implements Runnable{
	
	public static final String instructionsText = 
			"Instructions: \n\n"+
			"Object:\n"+
			 "Minesweeper is a game where bombs are hidden randomly in a grid. \n"+ 
			"Clicking a block reveals what's hiding behind. The object of the game"+
			 "is to uncover all blocks\n"+
			 "that do not have bombs behind them. If you uncover a block with a bomb behind it "+
			 "you lose. \n\n"+
			 "Types of Blocks:\n"+
			 "After you uncover a block, it will either be blank, have a number behind it, or"+
			 " be black.\n"+
			 "Black blocks are bombs. If you see a black block, it means you've lost "+
			 "the game. \nA number indicates the number of bombs adjacent to "+
			 "itself.\n"+
			 "A blank block simply means none of the adjacent blocks are bombs.\n\n"+
			 "Flags:\n"+
			 "Pushing SPACEBAR puts the game into \"Flag Mode\".\n"+
			 "In Flag Mode, you can click on any covered block to flag it, turning it orange. \n"+
			 "Uncovered blocks cannot be flagged. A flagged block cannot be uncovered without "+
			 "un-flagging it, \nso use "+"Flag Mode on blocks you know are bombs to prevent you"+
			 " from accidentally clicking on them.\n"+
			 "To turn Flag Mode off, just push SPACEBAR again. \n Note that placing a flag"+
			 " automatically turns Flag Mode off";

	public void run() {
		final JFrame frame = new JFrame("Minesweeper");
		frame.setLocation(300, 300);
		
		final JPanel status_panel = new JPanel();
		frame.add(status_panel, BorderLayout.NORTH);
		final JLabel bombsStatus = new JLabel();
		final JLabel nonBombsStatus = new JLabel();
		status_panel.add(bombsStatus);
		status_panel.add(nonBombsStatus);
		
		final JPanel bottom_status_panel = new JPanel();
		frame.add(bottom_status_panel, BorderLayout.SOUTH);
		final JLabel flagLabel = new JLabel();
		bottom_status_panel.add(flagLabel);
		
		final JButton instructions = new JButton("Instructions");
		instructions.setFocusable(false);
		instructions.addActionListener( new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						JOptionPane.showMessageDialog(null, instructionsText);
					}
				}
			);
		bottom_status_panel.add(instructions);
		
		final GameBoard board = new GameBoard(40, 16, 16, bombsStatus, nonBombsStatus, flagLabel);
		frame.add(board, BorderLayout.CENTER);
		
		final JButton reset = new JButton("Reset");
		reset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				board.wipeGrid();
				board.setupGame();
			}
		});
		reset.setFocusable(false);
		bottom_status_panel.add(reset);
		

		
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	/*
	 * Main method run to start and run the game Initializes the GUI elements
	 * specified in Game and runs it IMPORTANT: Do NOT delete! You MUST include
	 * this in the final submission of your game.
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Game());
	}


}
