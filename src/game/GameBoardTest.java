package game;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import javax.swing.JLabel;

import org.junit.*;

public class GameBoardTest {

	JLabel bombL = new JLabel();
	JLabel blockL = new JLabel();
	JLabel flagL = new JLabel();
	
	@Test public void floodNoBombs()
	{
		GameBoard board = new GameBoard(0, 15, 15, bombL, blockL, flagL);
		board.flood(0, 0);
		Block[][] array = board.gridCopy();
		for(int x = 0; x < array.length; x++)
		{
			for(int y = 0; y < array[0].length; y++)
			{
				String report = "("+x+", "+y+") ";
				assertTrue(report, array[x][y].blockState() == BlockState.UNCOVERED);
			}
		}
	}
	
	@Test public void floodOneBomb()
	{
		GameBoard board = new GameBoard(1, 2, 2, bombL, bombL, bombL);
		Block[][] array = board.gridCopy();
		
		for(int x = 0; x < array.length; x++)
		{
			for(int y = 0; y < array[0].length; y++)
			{
					if(array[x][y] instanceof NumberBlock)
					{
						board.flood(x, y);
					}
			}
		}
		
		int numUncovered = 0;
		
		for(int x = 0; x < array.length; x++)
		{
			for(int y = 0; y < array[0].length; y++)
			{
				if(array[x][y].blockState() == BlockState.UNCOVERED)
				{
					numUncovered++;
				}
			}
		}

		assertFalse(board.inProgress());
		assertEquals(4, numUncovered);
		assertEquals("You Won!", bombL.getText());
	
	}
	
	@Test public void floodBomb()
	{
		GameBoard board = new GameBoard(1, 1, 1, bombL, blockL, flagL);
		boolean exceptionThrown = false;
		try
		{
			board.flood(0, 0);
		}
		catch (IllegalArgumentException e)
		{
			exceptionThrown = true;
		}
		assertTrue(exceptionThrown);
	}
	
	@Test public void floodTwoBombs()
	{
		GameBoard board = new GameBoard(2, 2, 2, bombL, blockL, flagL);
		Block[][] array = board.gridCopy();
		
		boolean floodCalled = false;
		do
		{
			int x = (int)(Math.random()*2);
			int y = (int)(Math.random()*2);
			if(array[x][y] instanceof NumberBlock)
			{
				board.flood(x, y);
				floodCalled = true;
			}
		} while(!floodCalled);
		

		assertTrue(board.inProgress());
		
		array = board.gridCopy();
		int numUncovered = 0;
		for(int x = 0; x < array.length; x++)
		{
			for(int y = 0; y < array[0].length; y++)
			{
				if(array[x][y].blockState() == BlockState.UNCOVERED)
				{
					numUncovered++;
				}
			}
		}
		
		assertEquals(1, numUncovered);
	}
	
	@Test public void noBombs()
	{
		GameBoard board = new GameBoard(0, 2, 2, bombL, blockL, flagL);
		Block[][] array = board.gridCopy();
		int numBombs = 0;
		for(int x = 0; x < array.length; x++)
		{
			for(int y = 0; y < array.length; y++)
			{
				if(array[x][y] instanceof BombBlock)
				{
					numBombs++;
				}
			}
		}
		
		assertEquals(0, numBombs);
	}
	
	public void allBombs()
	{
		GameBoard board = new GameBoard(0, 2, 2, bombL, blockL, flagL);
		Block[][] array = board.gridCopy();
		int numBombs = 0;
		for(int x = 0; x < array.length; x++)
		{
			for(int y = 0; y < array.length; y++)
			{
				if(array[x][y] instanceof BombBlock)
				{
					numBombs++;
				}
			}
		}
		
		assertEquals(0, numBombs);
	}
	
	public void oneBomb()
	{
		GameBoard board = new GameBoard(4, 2, 2, bombL, blockL, flagL);
		Block[][] array = board.gridCopy();
		int numBombs = 0;
		for(int x = 0; x < array.length; x++)
		{
			for(int y = 0; y < array.length; y++)
			{
				if(array[x][y] instanceof BombBlock)
				{
					numBombs++;
				}
			}
		}
		
		assertEquals(4, numBombs);
	}
	
	@Test public void tooManyBombs()
	{
		GameBoard board = new GameBoard(5, 2, 2, bombL, blockL, flagL);
		Block[][] array = board.gridCopy();
		int numBombs = 0;
		for(int x = 0; x < array.length; x++)
		{
			for(int y = 0; y < array.length; y++)
			{
				if(array[x][y] instanceof BombBlock)
				{
					numBombs++;
				}
			}
		}
		assertEquals(4, numBombs);
	}
	
	@Test public void allBombsBigGrid()
	{
		GameBoard board = new GameBoard(40000, 200, 200, bombL, blockL, flagL);
		Block[][] array = board.gridCopy();
		int numBombs = 0;
		for(int x = 0; x < array.length; x++)
		{
			for(int y = 0; y < array.length; y++)
			{
				if(array[x][y] instanceof BombBlock)
				{
					numBombs++;
				}
			}
		}
		
		assertEquals(40000, numBombs);
	}
	
	public void someBombsBigGrid()
	{
		GameBoard board = new GameBoard(4, 200, 200, bombL, blockL, flagL);
		Block[][] array = board.gridCopy();
		int numBombs = 0;
		for(int x = 0; x < array.length; x++)
		{
			for(int y = 0; y < array.length; y++)
			{
				if(array[x][y] instanceof BombBlock)
				{
					numBombs++;
				}
			}
		}
		
		assertEquals(4, numBombs);
	}
	
	
}
