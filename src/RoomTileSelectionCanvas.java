import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;
//ACCOMODATED FOR DUAL LAYER, DELETE WHEN FINISHED
public class RoomTileSelectionCanvas extends JPanel implements MouseListener{
	private RoomTileSelectionFrame parent;
	private Editor editor;
	private int width, height, y, x;
	private boolean selected;
	
	public RoomTileSelectionCanvas(int height, int width, RoomTileSelectionFrame parent, Editor editor){
		this.parent = parent;
		this.editor = editor;
		this.height = height;
		this.width = width;
		this.selected = false;
		this.y = 0;
		this.x = 0;
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
		parent.paint(g);
	}
	
	public void mouseClicked(MouseEvent arg0){}
	
	public void mouseEntered(MouseEvent arg0){}
	
	public void mouseExited(MouseEvent arg0){}
	
	public void mousePressed(MouseEvent e){
		y = e.getY()/Room.TILE_SIZE;
		x = e.getX()/Room.TILE_SIZE;
		selected = true;
		System.out.println("SELECTED RTSF"); //TODO DEBUG
	}
	
	public void mouseReleased(MouseEvent arg0){}
	
	public int getSelectedY(){
		return y;
	}
	
	public int getSelectedX(){
		return x;
	}
	
	public boolean hasSelectedTile(){
		return selected;
	}
}