package game;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.*;
import java.util.*;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class GameBoard extends JPanel{

	public static final int blockWidth = 20;
	public static final int blockHeight = 20;
	public static final int bufferWidth = 5;
	public static final int bufferHeight = 5;
	
	private final int numberBombs;
	private final Block[][] grid;
	private boolean inProgress;
	private boolean flagMode;
	private int numberLeft;
	
	private final JLabel bombsStatus;
	private final JLabel nonBombsStatus;
	private final JLabel flagStatus;
	
	public GameBoard(int numberBombs, int numCol, int numRow, 
			JLabel bombsStatus, JLabel nonBombsStatus, JLabel flagStatus)
	{	
		this.bombsStatus = bombsStatus;
		bombsStatus.setText("Number of Bombs: "+numberBombs);
		this.nonBombsStatus = nonBombsStatus;
		numberLeft = numCol*numRow - numberBombs;
		nonBombsStatus.setText("Number of Blocks Remaining: "+numberLeft);
		this.flagStatus = flagStatus;
		flagStatus.setText("Flag Mode: OFF");
		inProgress = true;
		flagMode = false;
		
		if(numberBombs > numCol*numRow)
		{
			this.numberBombs = numCol*numRow;
		}
		else
		{
			this.numberBombs = numberBombs;
		}
		
		this.grid = new Block[numCol][numRow];

		this.setupGame();
		
		//Now I handle the mouseclicks
		addMouseListener(new MouseAdapter() 
		{
			public void mouseClicked(MouseEvent e) 
			{
				if(inProgress)
				{
					for(int x = 0; x < grid.length; x++)
					{
						for(int y = 0; y < grid[0].length; y++)
						{
							if(grid[x][y].contains(e.getX(), e.getY()))
							{
								if(flagMode)
								{
									grid[x][y].placeFlag();
									flagMode = false;
									repaint();
								}
								else
								{
									if(grid[x][y].blockState() != BlockState.FLAG)
									{
										if(grid[x][y] instanceof NumberBlock)
										{
											flood(x, y);
										}
										else
										{
											gameLose();
										}
									}
								}
							}
						}
					}
				}
			}
		});
		
		// Pushing spacebar toggles betwwen flagmore and not flagmode.
		setFocusable(true);
		addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_SPACE)
				{
					flagMode = !flagMode;
					refreshFlagText();
				}
			}
		});
		
	}
	
	/**
	 * The standard paint method. Basically, paints borders between blocks and then calls
	 * paint on every block in grid.
	 * @param g
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		g.setColor(Color.gray);
		g.fillRect(0, 0, boardWidth(), boardHeight());
		
		for(int x = 0; x < grid.length; x++)
		{
			for(int y = 0; y < grid[0].length; y++)
			{
				if(grid[x][y] != null)
				{
					grid[x][y].draw(g);
				}
			}
		}
		this.nonBombsStatus.setText("Number of Blocks Remaining: "+this.numberLeft);
		refreshFlagText();
	}
	
	/**
	 * Updates the text about whether the game is in flagmode or not
	 */
	private void refreshFlagText()
	{
		if(flagMode)
		{
			this.flagStatus.setText("Flag Mode: ON");
		}
		else
		{
			this.flagStatus.setText("Flag Mode: OFF");
		}
	}
	
	/**
	 * Returns the width of the gameboard, for dimension purposes
	 * @return the gameboard's width in pixels
	 */
	private int boardWidth()
	{
		return (grid.length + 1) * bufferWidth + (grid.length) * blockWidth;
	}
	
	/**
	 * Returns the height of the gameboard, for dimension purposes
	 * @return the gameboard's height in pixels
	 */
	private int boardHeight()
	{

		return (grid[0].length + 1) * bufferHeight + (grid[0].length) * blockHeight;
	}
	
	/**
	 * Given the coordinates of a block, flip it and all its neighbors (if it's blank)
	 * @param x the x-coordinate of the block to flip
	 * @param y the y-coordinate of the block to flip
	 * @throws IllegalArgumentException is called on a BombBlock
	 * @throws NullPointerException if the block at the specified location is null
	 */
	public void flood(int x, int y)
	{
		if(x < 0 || x >= grid.length || y < 0 || y >= grid[0].length) //out of bounds
		{
			return;
		}
		
		if(grid[x][y].blockState() == BlockState.UNCOVERED) //i.e. flooding an uncovered block
		{
			return;
		}

		if(!(grid[x][y] instanceof NumberBlock))
		{
			throw new IllegalArgumentException("Can only flood NumberBlocks");
		}
		
		NumberBlock b = (NumberBlock) grid[x][y];
		b.uncover();
		this.numberLeft--;
		repaint();
		if(numberLeft == 0)
		{
			gameWin();
		}
		
		//Now flood all its neighbors
		if(b.getNumber() == 0)
		{
			for(int i = x-1; i <= x+1; i++)
			{
				for(int j = y-1; j <= y+1; j++)
				{
					flood(i, j);
				}
			}
		}
	}
	
	/**
	 * Sets every value in the grid to null.
	 */
	public void wipeGrid()
	{
		for(int x = 0; x < grid.length; x++)
		{
			for (int y = 0; y < grid[0].length; y++)
			{
				grid[x][y] = null;
			}
		}
	}
	
	/**
	 * Fills the grid with numberofBombs bombs randomly.
	 * Then it puts the appropriate numbers in all of the non-bomb spots
	 */
	public void setupGame()
	{
		bombsStatus.setText("Number of Bombs: "+numberBombs);
		this.numberLeft = grid.length * grid[0].length - this.numberBombs;
		inProgress = true;
		flagMode = false;
		
		//Now add the bombs to random spots
		Set<Point> bombLocations = new HashSet<Point>();
		while(bombLocations.size() < numberBombs)
		{
			int x = (int) (grid.length * Math.random());
			int y = (int) (grid[0].length * Math.random());
			bombLocations.add(new Point(x, y));
		}
		for(Point loc : bombLocations)
		{
			int blockX = loc.x * blockWidth + (loc.x + 1) * bufferWidth;
			int blockY = loc.y * blockHeight + (loc.y + 1) * bufferHeight;
			grid[loc.x][loc.y] = 
					new BombBlock(blockX, blockY, blockWidth, blockHeight);
		}
		
		//Now we add in numbers
		for(int x = 0; x < grid.length; x++)
		{
			for(int y = 0; y < grid[0].length; y++)
			{
				if(grid[x][y] == null) //so there's no bomb placed there
				{
					int number = 0; //the number of bombs in the neighborhood of the piece
					for(int i = x-1; i <= x+1; i++)
					{
						for(int j = y-1; j <= y+1; j++)
						{
							if(i >= 0 && i < grid.length && j >= 0 && j < grid[0].length)
							{
								//so the square is in the array
								if(grid[i][j] != null && grid[i][j] instanceof BombBlock)
									number++;
							}
						}
					}
					//so now, number is the number of bombs in the neighborhood
					int blockX = x * blockWidth + (x + 1) * bufferWidth;
					int blockY = y * blockHeight + (y + 1) * bufferHeight;
					grid[x][y] = new NumberBlock(blockX, blockY, blockWidth, blockHeight, number);
				}
			}
		}
		
		repaint();
	}
	
	/**
	 * Updates the status bars to indicate that the game is won.
	 * Also, reveals the location of all bombs
	 */
	public void gameWin()
	{
		gameOver();
		this.bombsStatus.setText("Congratulations!");
		this.flagStatus.setText("You Won!");
	}
	
	/**
	 * Updates the status bars to indicate that the game is lost
	 * Also reveals the location of all bombs
	 */
	public void gameLose()
	{
		gameOver();
		this.bombsStatus.setText("Oh no!");
		this.nonBombsStatus.setText("You clicked on a bomb!");
		this.flagStatus.setText("Game Over");
	}
	
	/**
	 * Revealts the location of all bombs
	 */
	private void gameOver()
	{
		inProgress = false;
		for(int x = 0; x < grid.length; x++)
		{
			for(int y = 0; y < grid[0].length; y++)
			{
				if(grid[x][y] instanceof BombBlock)
				{
					grid[x][y].uncover();
				}
			}
		}
		repaint();
	}
	
	/**
	 * Standard dimension method
	 * @return the proper width and height of the board
	 */
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(boardWidth(), boardHeight());
	}
	
	/**
	 * For testing purposes only, returns a copy of the grid
	 */
	public Block[][] gridCopy()
	{
		Block[][] output = new Block[grid.length][grid[0].length];
		for(int x = 0; x < grid.length; x++)
		{
			for(int y = 0; y < grid.length; y++)
			{
				output[x][y] = grid[x][y];
			}
		}
		return output;
	}
	
	public boolean inProgress()
	{
		return this.inProgress;
	}
	
}
