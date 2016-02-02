package game;
import java.awt.Color;
import java.awt.Graphics;

public class BombBlock extends Block{
	private boolean covered;
	
	/**
	 * The constructor to create a block
	 * @param x the x-coordinate for the top-left corner of the block
	 * @param y the y-coordinate for the top-left corner of the block
	 * @param width the horizontal width of the block
	 * @param height the vertical height of the block
	 */
	public BombBlock(int x, int y, int width, int height)
	{
		super(x, y, width, height);

		this.covered = true;
	}
	


	/**
	 * Implements the parent method and satisfies invariants
	 */
	public void uncover() 
	{
		if(this.blockState() == BlockState.FLAG)
		{
			this.placeFlag();
		}
		this.covered = false;
	}
	
	/**
	 * This method returns the state of the current block
	 * @return FLAG is there is a flag, COVERED if not, UNCOVERED if it's uncovered
	 */
	public BlockState blockState()
	{
		if(covered)
		{
			return super.blockState();
		}
		return BlockState.UNCOVERED;
	}

	/**
	 * If the block has a flag, removes the current flag. Else, it adds a flag.
	 * Note that a uncovered block cannot ever have a flag.
	 * @return whether the block has a flag, AFTER THE METHOD IS EXECUTED
	 */
	public boolean placeFlag()
	{
		if(covered)
		{
			return super.placeFlag();
		}
		return false;
	}
	
	/**
	 * Draws the block black if uncovered
	 * If it's covered, obeys the super method
	 * @param g the graphics state
	 */
	public void draw(Graphics g) 
	{
		if(covered)
		{
			super.draw(g);
		}
		else
		{
			g.setColor(Color.black);
			g.fillRect(this.getX(), this.getY(), this.getWidth(), this.getHeight());
		}
	}
}
