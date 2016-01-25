import java.awt.Color;
import java.awt.Graphics;

public class FieldGrid
{
	//private boolean[][] grid;
	private Color[][] grid;
	
	// full grid spots are filled by GROUNDED blocks and represented by the color black. Moving blocks don't count in the grid
	public FieldGrid(int width, int height)
	{
		grid = new Color[height / 10 - 2][width / 10]; 
		for(int i = 0; i < grid.length; i++)
		{
			for(int j = 0; j < grid[0].length; j++)
			{
				grid[i][j] = Color.BLACK; // empty grid spots
			}
		}
	}
	
	/*This function checks to see whether the game is over*/
	public boolean checkTopRow()
	{
		for(int i = 0; i < this.getColumnLimit() + 1; i++)
		{
			if(this.isFilled(i, 0))
				return true;
		}
		
		return false;
	}
	
	// the index of the row limit
	public int getRowLimit()
	{
		return grid.length - 1;
	}
	
	// The index of the column limit
	public int getColumnLimit()
	{
		return grid[0].length - 1;
	}
	
	/* Returns whether a certain grid element has been filled by a grounded block*/
	public boolean isFilled(int x, int y)
	{
		return grid[y][x] != Color.BLACK;
	}
	
	/*Just for testing purposes. Move along.*/
	public void showGrid(Graphics g)
	{
		for(int i = 0; i < grid.length; i++)
		{
			for(int j = 0; j < grid[0].length; j++)
			{
				g.drawRect(j * 10, i * 10, 10, 10);
			}
		}
	}
	
	/* This function is called when a block is grounded and settled on the grid. */
	public void updateGrid(Point[] points, Color c)
	{
		for(int i = 0; i < points.length; i++)
		{
			grid[points[i].getY()][points[i].getX()] = c;
		}
		
		shiftdown();
	}
	
	/*This function checks to see whether it is time to shift down one*/
	private int checkFilledRow()
	{
		for(int i = grid.length - 1; i >= 0; i--)
		{
			boolean filledRow = true;
			for(int j = 0; j < grid[0].length; j++)
			{
				if(!this.isFilled(j, i))
				{
					filledRow = false;
					break;
				}
			}
			
			if(filledRow)
			{
				return i;
			}
		}
		
		return -1;
	}
	
	/*This function is called when a row has been saturated and the grid above the row moves down one*/
	private void shiftdown()
	{
		int filledRow = checkFilledRow();
		if(filledRow != -1)
		{
			for(int i = filledRow; i > 0; i--)
			{
				for(int j = 0; j < grid[0].length; j++)
				{
					grid[i][j] = grid[i - 1][j];
				}
			}
		}
	}
	
	/* This function draws the filled in grid elements from the grounded blocks.
	 * Note: make this function more efficient by establishing a roof variable so the 
	 * for loop doesn't have to go through all the rows*/
	public void drawFilledGrid(Graphics g)
	{
		for(int i = 0; i < grid.length; i++)
		{
			for(int j = 0; j < grid[0].length; j++)
			{
				if(grid[i][j] != Color.BLACK)
				{
					g.setColor(Color.BLACK);
					g.drawRect(j * 10, i * 10, 10, 10);
					g.setColor(grid[i][j]);
					g.fillRect(j * 10, i * 10, 10, 10);
				}
			}
		}
	}
}
