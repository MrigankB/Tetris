import java.awt.Color;
import java.awt.Graphics;

public class Point 
{
	private int x;
	private int y;
	
	public Point(int x, int y)
	{
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void shiftDown()
	{
		this.y++;
	}
	
	public void shiftRight()
	{
		this.x++;
	}
	
	public void shiftLeft()
	{
		this.x--;
	}
	
	public void drawAtPoint(Graphics g, Color c)
	{
		g.setColor(Color.BLACK);
		g.drawRect(x * 10, y * 10, 10, 10);
		g.setColor(c);
		g.fillRect(x * 10, y * 10, 10, 10);
	}
}
