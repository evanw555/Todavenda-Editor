import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
//ACCOMODATED FOR DUAL LAYER, DELETE WHEN FINISHED
public class ImageHandler{
	private static BufferedImage mainTileImg;
	private static BufferedImage[][] tileImgs;
	
	private static BufferedImage selectedTile;
	private static BufferedImage tele;
	private static BufferedImage spawn;
	private static BufferedImage wall;
	private static BufferedImage enemySpawn;
	
	public static void loadImages(){
		int tileSize = Room.TILE_SIZE;
		loadTileImages();
		selectedTile = loadImage("images/"+tileSize+"/selected.PNG");
		tele = loadImage("images/"+tileSize+"/tele.PNG");
		spawn = loadImage("images/"+tileSize+"/spawn.PNG");
		wall = loadImage("images/"+tileSize+"/wall.PNG");
		enemySpawn = loadImage("images/"+tileSize+"/enemySpawn.PNG");
	}
	
	public static void loadTileImages(){
		int dims = Room.TILE_SIZE;
		mainTileImg = loadImage("images/"+dims+"/map/tiles2.PNG");
		tileImgs = new BufferedImage[mainTileImg.getWidth()/dims][mainTileImg.getHeight()/dims];
		for(int i = 0; i < Room.NUMLAYERS; i++)
			for(int j = 0; j < mainTileImg.getHeight()/dims; j++)
				tileImgs[i][j] = mainTileImg.getSubimage(i*dims, j*dims, dims, dims);
	}
	
	public static BufferedImage getTileImage(int layer, int type){
		return tileImgs[layer][type];
	}
	
	public static int getNumTileTypes(){
		return tileImgs[0].length;
	}
	
	public static BufferedImage getSelectedTile(){
		return selectedTile;
	}
	
	public static BufferedImage getTele(){
		return tele;
	}
	
	public static BufferedImage getSpawn(){
		return spawn;
	}
	
	public static BufferedImage getWall(){
		return wall;
	}
	
	public static BufferedImage getEnemySpawn(){
		return enemySpawn;
	}
	
	public static BufferedImage loadImage(String location){
		try{
			 return ImageIO.read(new File(location));
		}catch(IOException e){ System.out.println("Failed: "+location); }
		return null;
	}
	
	public static BufferedImage loadImageNoCatch(String location) throws Exception{
		return ImageIO.read(new File(location));
	}
}