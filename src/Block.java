import java.awt.Color;
import java.awt.Graphics;

public interface Block
{	
	public void rotate();
	public void shiftdown();
	public void shiftRight();
	public void shiftLeft();
	public void draw(Graphics g);
	
	public Color getColor();
	
	public Point[] blockPoints();
	
	public boolean collisionDetectBelow(FieldGrid g);
	public boolean collisionDetectRight(FieldGrid g);
	public boolean collisionDetectLeft(FieldGrid g);
}
