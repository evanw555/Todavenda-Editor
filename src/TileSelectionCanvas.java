import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JPanel;
//ACCOMODATED FOR DUAL LAYER, DELETE WHEN FINISHED

public class TileSelectionCanvas extends JPanel implements MouseListener{
	private Editor parent;
	private int width, height, selectedLayer, selectedTile;
	
	public TileSelectionCanvas(Editor parent){
		this.width = Room.TILE_SIZE*ImageHandler.getNumTileTypes();
		this.height = Room.TILE_SIZE*Room.NUMLAYERS;
		this.parent = parent;
		this.selectedLayer = 0;
		this.selectedTile = 0;
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
		for(int i = 0; i < Room.NUMLAYERS; i++)
			for(int j = 0; j < ImageHandler.getNumTileTypes(); j++)
				g.drawImage(ImageHandler.getTileImage(i, j), j*Room.TILE_SIZE, i*Room.TILE_SIZE, null);
		g.drawImage(ImageHandler.getSelectedTile(), selectedTile*Room.TILE_SIZE, selectedLayer*Room.TILE_SIZE, null);
	}
	
	public int getWidth(){
		return width;
	}
	
	public int getHeight(){
		return height;
	}
	
	public int getSelectedLayer(){
		return selectedLayer;
	}
	
	public int getSelectedTile(){
		return selectedTile;
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
		//if clicked a possible tile type (not too far to the right)
		if(e.getX() > 0 && e.getX() < ImageHandler.getNumTileTypes()*Room.TILE_SIZE)
			selectedTile = e.getX()/Room.TILE_SIZE;
		//if clicked a possible layer level (not too far down)
		if(e.getY() > 0 && e.getY() < Room.NUMLAYERS*Room.TILE_SIZE)
			selectedLayer = e.getY()/Room.TILE_SIZE;
		this.repaint();
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}