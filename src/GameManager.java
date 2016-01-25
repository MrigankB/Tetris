import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

import javax.swing.*;


public class GameManager extends JPanel implements ActionListener, KeyListener
{
	private int increment = 150;
	private int width = 150;
	private int height = 400;
	private int startX;
	private double type;
	private Timer t;
	
	private Graphics g;
	private FieldGrid field;
	private Block block;
	
	private Random r;
	private boolean newBlock;
	private boolean endGame;
	private boolean isPaused;
	private int landings;
	
	public GameManager()
	{
		super();
		t = new Timer(increment, this);
		t.start();
		
		setFocusable(true);	
		setFocusTraversalKeysEnabled(false);
		addKeyListener(this);
		
		setBackground(Color.WHITE);
		
		field = new FieldGrid(width, height);
		r = new Random();
		g = this.getGraphics();
		
		newBlock = true;
		endGame = false;
		isPaused = false;
		landings = 0;
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		// Drawing code goes here
		//field.showGrid(g);
		field.drawFilledGrid(g);
		block.draw(g);
		
		if(isPaused)
		{
			g.setColor(Color.BLACK);
			g.drawString("PAUSED", getWidth() / 2 - 25, getHeight() / 2);
		}
		
		if(endGame)
		{
			g.setColor(Color.BLACK);
			g.drawRect(getWidth() / 2 - 50, getHeight() / 2 - 25, 100, 50);
			g.setColor(Color.GRAY);
			g.fillRect(getWidth() / 2 - 50, getHeight() / 2 - 25, 100, 50);
			g.setColor(Color.BLACK);
			g.drawString("GAME OVER", getWidth() / 2 - 45, getHeight() / 2 - 12);
			g.drawString("You landed " + landings + " blocks", getWidth() / 2 - 45, getHeight() / 2 - 7);
		}
	}
	
	public void keyPressed(KeyEvent e)
	{		
		switch(e.getKeyCode())
		{
			case KeyEvent.VK_DOWN:
				if(!block.collisionDetectBelow(field))
				{
					block.shiftdown();
				}
				break;
			case KeyEvent.VK_RIGHT:
				if(!block.collisionDetectRight(field))
				{
					block.shiftRight();
				}
				break;
			case KeyEvent.VK_LEFT:
				if(!block.collisionDetectLeft(field))
				{
					block.shiftLeft();
				}
				break;
			case KeyEvent.VK_UP:
				if(!block.collisionDetectLeft(field)
						&& !block.collisionDetectRight(field)
						&& !block.collisionDetectBelow(field))
				{
					block.rotate();
				}
				break;
			case KeyEvent.VK_ESCAPE:
				if(!isPaused)
				{
					t.stop();
					isPaused = true;
					repaint();
				}
				else
				{
					t.start();
					isPaused = false;
				}
				break;
			default:
				break;
		}
	}
	
	public void actionPerformed(ActionEvent e)
	{
		if(field.checkTopRow())
		{
			endGame = true;
		}
		else
		{
			if(newBlock)
			{
				startX = r.nextInt(field.getColumnLimit() - 1);
				type = r.nextDouble();
				if(type < 0.25)
				{
					block = new SquareBlock(new Point(startX, 1)); 
				}
				else if(type >= 0.25 && type < 0.5)
				{
					block = new LBlock(new Point(startX, 1));
				}
				else if(type >= 0.5 && type < 0.75)
				{
					block = new IBlock(new Point(startX, 1));
				}
				else
				{
					block = new TBlock(new Point(startX, 1));
				}
				
				newBlock = false;
				landings++;
				
				if(increment > 80)
				{
					t.stop();
					increment -= 5;
					t.setDelay(increment);
					t.start();
				}
			}
			
			if(!block.collisionDetectBelow(field))
			{
				block.shiftdown();
			}
			else
			{
				field.updateGrid(block.blockPoints(), block.getColor());
				newBlock = true;
			}
		}
		repaint();
	}
	
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub
		GameManager panel = new GameManager(); // window for drawing
		JFrame application = new JFrame();		 // the program itself
		
		application.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // set frame to exit when closed
		
		application.add(panel);
		
		application.setSize(panel.width, panel.height + 2);
		application.setTitle("Tetris");
		application.setVisible(true);
	}
	

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
