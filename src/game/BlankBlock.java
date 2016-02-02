package game;
import java.awt.Color;
import java.awt.Graphics;

public class BlankBlock extends NumberBlock{

	
	
	/**
	 * The constructor to create a block
	 * @param x the x-coordinate for the top-left corner of the block
	 * @param y the y-coordinate for the top-left corner of the block
	 * @param width the horizontal width of the block
	 * @param height the vertical height of the block
	 */
	public BlankBlock(int x, int y, int width, int height)
	{
		super(x, y, width, height, 0);
	}
	
	
	/**
	 * Draws the block light teal if it's uncovered.
	 * If it's covered, obeys the super method
	 * @param g the graphics state
	 */
	public void draw(Graphics g) 
	{
		if(this.blockState() == BlockState.UNCOVERED)
		{
			g.setColor(bgColor);
			g.fillRect(this.getX(), this.getY(), this.getWidth(), this.getHeight());
		}
		else
		{
			super.draw(g);
		}
	}
	
}
