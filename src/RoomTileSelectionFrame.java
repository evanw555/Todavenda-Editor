import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class RoomTileSelectionFrame extends JFrame{
	private Editor parent;
	private RoomTileSelectionCanvas canvas;
	private int room;
	
	public RoomTileSelectionFrame(Editor parent){
		this.parent = parent;
		this.room = 0;
	}
	
	public void paint(Graphics g){
		parent.getMap().paint(room, g);
	}
	
	public Coord selectTileSequence(int room){
		//set room
		this.room = room;
		//create canvas
		canvas = new RoomTileSelectionCanvas(parent.getCanvasHeight(),
				parent.getCanvasWidth(), this, parent);
		//add canvas
		this.getContentPane().add(canvas);
		this.pack();
		canvas.repaint();
		//set visible and wait until tiles is selected
		this.setVisible(true);
		do{
			try{
				canvas.repaint();
				Thread.sleep(200);
			}catch(Exception e){}
		}while(!canvas.hasSelectedTile());
		//set invisible and return coord
		this.setVisible(false);
		return new Coord(room, canvas.getSelectedY(), canvas.getSelectedX());
	}
}