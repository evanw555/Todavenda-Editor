import java.awt.Graphics;
import java.util.ArrayList;
//ACCOMODATED FOR DUAL LAYER, DELETE WHEN FINISHED
public class Map{
	private ArrayList<Room> rooms;
	private String name;
	private int spawnRoom, spawnY, spawnX, currentRoom;
	
	public Map(ArrayList<Room> rooms, String name, int spawnRoom, int spawnY, int spawnX){
		this.rooms = rooms;
		this.name = name;
		this.spawnRoom = spawnRoom;
		this.spawnY = spawnY;
		this.spawnX = spawnX;
		this.currentRoom = 0;
	}
	
	public void paint(Graphics g){
		//paint current room and all its components
		rooms.get(currentRoom).paint(g);
		//if current room is spawn room, paint spawn point
		if(currentRoom == spawnRoom)
			g.drawImage(ImageHandler.getSpawn(), (spawnX+1)*Room.TILE_SIZE,
					(spawnY+1)*Room.TILE_SIZE, null);
	}
	
	public void paint(int room, Graphics g){
		rooms.get(room).paint(g);
	}
	
	public String getName(){
		return name;
	}
	
	public int getSpawnRoom(){
		return spawnRoom;
	}
	
	public int getSpawnY(){
		return spawnY;
	}
	
	public int getCurrentRoom(){
		return currentRoom;
	}
	
	public int getSpawnX(){
		return spawnX;
	}
	
	public void setSpawn(int r, int y, int x){
		spawnRoom = r;
		spawnY = y;
		spawnX = x;
	}
	
	public void setTile(int type, int layer, int y, int x){
		rooms.get(currentRoom).setTile(type, layer, y, x);
	}
	
	public void saveRooms(FileOutput out){
		for(int i = 0; i < rooms.size(); i++){
			out.writeString("ROOMSTART");
			out.writeEndOfLine();
			rooms.get(i).save(out);
			out.writeString("ROOMEND");
			//don't write end of line if at the end of the file
			if(i < rooms.size()-1)
				out.writeEndOfLine();
		}
	}
	
	public int getNumRooms(){
		return rooms.size();
	}
	
	public void createNewRoom(){
		rooms.add(new Room());
		currentRoom = rooms.size()-1;
	}
	
	public void switchToRoom(int index){
		currentRoom = index;
	}
	
	public void addTrigger(Trigger t){
		rooms.get(currentRoom).addTrigger(t);
	}
	
	public void removeTrigger(int y, int x){
		rooms.get(currentRoom).removeTrigger(y, x);
	}
}