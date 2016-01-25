import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public class IBlock implements Block
{
	private Point location; // pivoting point of the block
	private BlockOrientation orientation;
	private Point[] points;
	private Color color;
	
	public IBlock(Point location)
	{
		this.location = location;
		this.color = getRandomColor();
		
		//first position is the up position
		this.orientation = BlockOrientation.UP;
		points = new Point[3];
		points[0] = new Point(location.getX(), location.getY() - 1);
		points[1] = new Point(location.getX(), location.getY() + 1);
		points[2] = new Point(location.getX(), location.getY() + 2);
	}
	
	public Color getColor()
	{
		return this.color;
	}
	
	private Color getRandomColor()
	{
		Random r = new Random();
		int color = r.nextInt(11);
		
		switch(color)
		{
			case 0:
				return Color.BLUE;
			case 1:
				return Color.CYAN;
			case 2:
				return Color.DARK_GRAY;
			case 3:
				return Color.GRAY;
			case 4:
				return Color.GREEN;
			case 5:
				return Color.MAGENTA;
			case 6:
				return Color.ORANGE;
			case 7:
				return Color.PINK;
			case 8:
				return Color.RED;
			case 9:
				return Color.WHITE;
			case 10:
				return Color.YELLOW;
			default:
				return Color.BLUE;
			
		}
	}
	
	/*Draws the block*/
	public void draw(Graphics g)
	{
		for(int i = 0; i < points.length; i++)
		{
			points[i].drawAtPoint(g, this.color);
		}
		location.drawAtPoint(g, this.color);
	}
	
	/*Returns the block as an array of points*/
	public Point[] blockPoints()
	{
		Point[] p = new Point[points.length + 1];
		
		for(int i = 0; i < p.length - 1; i++)
		{
			p[i] = points[i];
		}
		
		p[p.length - 1] = location;
		
		return p;
	}
	
	/*Returns true if bump below. False if no bump below.*/
	public boolean collisionDetectBelow(FieldGrid f)
	{
		if((orientation == BlockOrientation.UP) || (orientation == BlockOrientation.DOWN))
		{
			return points[2].getY() == f.getRowLimit() ||
					f.isFilled(points[2].getX(), points[2].getY() + 1);
		}
		else
		{
			return points[2].getY() == f.getRowLimit() ||
					f.isFilled(points[0].getX(), points[0].getY() + 1) ||
					f.isFilled(points[1].getX(), points[1].getY() + 1) ||
					f.isFilled(points[2].getX(), points[2].getY() + 1) ||
					f.isFilled(location.getX(), location.getY() + 1);
		}
	}
	
	/*Returns true if bump to the right. False if no bump to the right*/
	public boolean collisionDetectRight(FieldGrid f)
	{
		if((orientation == BlockOrientation.UP) || (orientation == BlockOrientation.DOWN))
		{
			return points[2].getX() == f.getColumnLimit() ||
					f.isFilled(points[0].getX() + 1, points[0].getY()) ||
					f.isFilled(points[1].getX() + 1, points[1].getY()) ||
					f.isFilled(points[2].getX() + 1, points[2].getY()) ||
					f.isFilled(location.getX() + 1, location.getY());
		}
		else
		{
			return points[2].getX() == f.getColumnLimit() ||
					f.isFilled(points[2].getX() + 1, points[2].getY());
		}
	}
	
	/*Returns true if bump to the left. False if no bump to the left*/
	public boolean collisionDetectLeft(FieldGrid f)
	{
		if((orientation == BlockOrientation.UP) || (orientation == BlockOrientation.DOWN))
		{
			return points[2].getX() == 0 ||
					f.isFilled(points[0].getX() - 1, points[0].getY()) ||
					f.isFilled(points[1].getX() - 1, points[1].getY()) ||
					f.isFilled(points[2].getX() - 1, points[2].getY()) ||
					f.isFilled(location.getX() - 1, location.getY());
		}
		else
		{
			return points[0].getX() == 0 ||
					f.isFilled(points[0].getX() - 1, points[0].getY());
		}
	}
	
	public void rotate()
	{
		if((orientation == BlockOrientation.UP) || (orientation == BlockOrientation.DOWN))
		{
			points[0].setX(location.getX() - 1);
			points[0].setY(location.getY());
			points[1].setX(location.getX() + 1);
			points[1].setY(location.getY());
			points[2].setX(location.getX() + 2);
			points[2].setY(location.getY());
			this.orientation = BlockOrientation.RIGHT;
		}
		else
		{
			points[0].setX(location.getX());
			points[0].setY(location.getY() - 1);
			points[1].setX(location.getX());
			points[1].setY(location.getY() + 1);
			points[2].setX(location.getX());
			points[2].setY(location.getY() + 2);
			
			this.orientation = BlockOrientation.UP;
		}
	}

	public void shiftdown()
	{
		for(int i = 0; i < points.length; i++)
		{
			points[i].shiftDown();
		}
		location.shiftDown();
	}
	
	public void shiftRight()
	{
		for(int i = 0; i < points.length; i++)
		{
			points[i].shiftRight();
		}
		location.shiftRight();
	}
	
	public void shiftLeft()
	{
		for(int i = 0; i < points.length; i++)
		{
			points[i].shiftLeft();
		}
		location.shiftLeft();
	}

}
