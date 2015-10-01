import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
//ACCOMODATED FOR DUAL LAYER, DELETE WHEN FINISHED
public class Room{
	public final static int HEIGHT = 10, WIDTH = 14, TILE_SIZE = 32, HALF_TILE = TILE_SIZE/2, NUMLAYERS = 2;
	private int[][][] tiles;
	private ArrayList<Trigger> triggers;
	private String bgmDest;
	
	public Room(){
		this.tiles = new int[NUMLAYERS][HEIGHT][WIDTH];
		this.triggers = new ArrayList<Trigger>();
		this.bgmDest = "song02";
	}
	
	public Room(int[][][] tiles){
		this.tiles = tiles;
		this.triggers = new ArrayList<Trigger>();
		this.bgmDest = "song02";
	}
	
	public void paint(Graphics g){
		//draw tiles
		for(int i = 0; i < NUMLAYERS; i++)
			for(int y = 0; y < HEIGHT; y++)
				for(int x = 0; x < WIDTH; x++)
					g.drawImage(ImageHandler.getTileImage(i, tiles[i][y][x]), (x+1)*TILE_SIZE, (y+1)*TILE_SIZE, null);
		//draw triggers
		for(Trigger t : triggers)
			switch(t.getType()){
			case Trigger.TELE:
				g.drawImage(ImageHandler.getTele(), (t.getIntParam(1)+1)*TILE_SIZE,
						(t.getIntParam(0)+1)*TILE_SIZE, null);
				g.setColor(Color.WHITE);
				g.setFont(new Font("COURIER", Font.BOLD, 13));
				g.drawString(t.getIntParam(2)+"r",
						(t.getIntParam(1)+1)*TILE_SIZE+8, (t.getIntParam(0)+1)*TILE_SIZE+32);
				g.drawString("("+t.getIntParam(3)+"y,"+t.getIntParam(4)+"x)",
						(t.getIntParam(1)+1)*TILE_SIZE+8, (t.getIntParam(0)+1)*TILE_SIZE+48);
				break;
			case Trigger.WALL:
				g.setColor(Color.LIGHT_GRAY);
				g.setFont(new Font("COURIER", Font.BOLD, 15));
				g.drawString(t.getSideString()+" "+t.getIntParam(1)+"r", 4, t.getIntParam(0)*14+14);
				break;
			case Trigger.ENEMYSPAWN:
				g.drawImage(ImageHandler.getEnemySpawn(), (t.getIntParam(1)+1)*TILE_SIZE,
						(t.getIntParam(0)+1)*TILE_SIZE, null);
				g.setColor(Color.BLACK);
				g.setFont(new Font("COURIER", Font.PLAIN, 10));
				g.drawString(t.getEnemyString(), (t.getIntParam(1)+1)*TILE_SIZE+4,
						(t.getIntParam(0)+1)*TILE_SIZE+32);
				break;
			}
	}
	
	public void addTrigger(Trigger t){
		triggers.add(t);
	}
	
	public void removeTrigger(int y, int x){
		for(int i = 0; i < triggers.size(); i++)
			if(triggers.get(i).equals(y, x)){
				System.out.println("Trigger Removed at "+y+", "+x);
				triggers.remove(i);
				i--;
			}
	}
	
	public void setTile(int type, int layer, int y, int x){
		tiles[layer][y][x] = type;
	}
	
	public void setBGMTrack(String dest){
		bgmDest = dest;
	}
	
	public void save(FileOutput out){
		//save tiles
		for(int i = 0; i < NUMLAYERS; i++)
			for(int y = 0; y < HEIGHT; y++){
				for(int x = 0; x < WIDTH; x++){
					//if not first in row, place space before the value
					if(x != 0)
						out.writeString(" ");
					//write tile type
					out.writeString(tiles[i][y][x]+"");
				}
				out.writeEndOfLine();
			}
		//save BGM destination
		out.writeString("BGM "+bgmDest);
		out.writeEndOfLine();
		//save triggers
		for(Trigger t : triggers){
			out.writeString(t.toString());
			out.writeEndOfLine();
		}
	}
}