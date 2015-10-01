import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

//ACCOMODATED FOR DUAL LAYER, DELETE WHEN FINISHED
public class Canvas extends JPanel implements MouseListener{
	private Editor parent;
	private int width, height;
	
	public Canvas(int height, int width, Editor parent){
		this.width = width;
		this.height = height;
		this.parent = parent;
		this.addMouseListener(this);
	}
	
	public boolean isFocusable(){
		return true;
	}
	
	public Dimension getPreferredSize(){
		return new Dimension(width, height);
	}
	
	public Dimension getMinimumSize(){
		return new Dimension(width, height);
	}
	
	public Dimension getMaximumSize(){
		return new Dimension(width, height);
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		//paint top-left wall marker
		g.drawImage(ImageHandler.getWall(), 0, 0, null);
		//paint grid labels
		g.setColor(Color.BLACK);
		g.setFont(new Font("COURIER", Font.BOLD, Room.TILE_SIZE/2));
		//y axis
		for(int y = 0; y < Room.HEIGHT; y++)
			g.drawString(y+"", Room.TILE_SIZE/4, (int)((y+1.5)*Room.TILE_SIZE));
		//x axis
		for(int x = 0; x < Room.WIDTH; x++)
			g.drawString(x+"", (int)((x+1.25)*Room.TILE_SIZE), Room.TILE_SIZE/2);
		//paint everything else
		parent.paint(g);
	}
	
	public int getWidth(){
		return width;
	}
	
	public int getHeight(){
		return height;
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void mousePressed(MouseEvent e){
		if(e.getX() > Room.TILE_SIZE && e.getX() < width-Room.TILE_SIZE && e.getY() > Room.TILE_SIZE && e.getY() < height-Room.TILE_SIZE)
			parent.setTile(e.getY()/Room.TILE_SIZE-1, e.getX()/Room.TILE_SIZE-1);
		this.repaint();
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}