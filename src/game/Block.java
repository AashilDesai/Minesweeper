package game;
import java.awt.Color;
import java.awt.Graphics;

public abstract class Block {

	private boolean flag;
	private final int x;
	private final int y;
	private final int width;
	private final int height;
	public static final Color blockColor = new Color(0,139,139); //teal blue
	public static final Color flagColor = new Color(255,127,36); // metalic orange
	public static final Color bgColor = new Color(00, 185, 178); //light teal
	
	/**
	 * The constructor to create a block
	 * @param x the x-coordinate for the top-left corner of the block
	 * @param y the y-coordinate for the top-left corner of the block
	 * @param width the horizontal width of the block
	 * @param height the vertical height of the block
	 */
	public Block(int x, int y, int width, int height)
	{
		this.flag = false;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	/**
	 * This method returns whether or not the block has a flag.
	 * @return FLAG is there is a flag, COVERED if not
	 */
	public BlockState blockState()
	{
		if(flag)
		{
			return BlockState.FLAG;
		}
		return BlockState.COVERED;
	}
	
	/**
	 * If the block has a flag, removes the current flag. Else, it adds a flag.
	 * Note that a uncovered block cannot ever have a flag.
	 * @return whether the block has a flag, AFTER THE METHOD IS EXECUTED
	 * 
	 * Note that this method must always statisfy the invariant that running
	 * placeFlag() on a block after running uncover() does nothing.
	 */
	public boolean placeFlag()
	{
		flag = !flag;
		return flag;
	}
	
	/**
	 * Draws the block, a teal rectangle if no flag and an orange one if yes flag.
	 * @param g the graphics state
	 */
	public void draw(Graphics g) 
	{
		if(flag)
		{
			g.setColor(flagColor);
		}
		else
		{
			g.setColor(blockColor);
		}
		
		g.fillRect(x, y, width, height);
	}
	
	public int getX()
	{
		return x;
	}
	
	public int getY()
	{
		return y;
	}
	
	public int getWidth()
	{
		return width;
	}
	
	public int getHeight()
	{
		return height;
	}
	
	/**
	 * Sees if the given coordinate is inside the Block
	 * @param x the x-component of the coordinate
	 * @param y the y-componenet of the coordinate
	 * @return true if the point is inside the Block, false if not
	 */
	public boolean contains(int x, int y)
	{
	  return this.x <= x && this.x + this.width >= x && this.y <= y && this.y + this.height >= y;
	}
	
	
	/**
	 * Handles uncovering the block
	 * Any children must satisfy the following invariant:
	 * 	An uncovered block cannot ever be "re-covered"
	 * 	Running oncover on a flagged block first removes the flag, then uncovers the block
	 */
	public abstract void uncover();
	
}
