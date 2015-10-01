import java.io.File;
import java.util.ArrayList;
//ACCOMODATED FOR DUAL LAYER, DELETE WHEN FINISHED
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
//ACCOMODATED FOR DUAL LAYER, DELETE WHEN FINISHED
public class FileHandler{

	public FileHandler(){
		
	}
	
	public static File loopingFileChooserSequence(){
		try{
			boolean repeat = true;
			String[] options = {"Yes", "Choose Another File", "Quit"};
			JFileChooser fc = new JFileChooser("maps");
			while(repeat){
				fc.showOpenDialog(null);
				int choice = JOptionPane.showOptionDialog(null,
						fc.getSelectedFile().getName()+"\n"+
						"is this the correct file?",
						"Confirm", JOptionPane.YES_NO_CANCEL_OPTION,
						JOptionPane.QUESTION_MESSAGE, null,
						options, options[1]);
				switch(choice){
				case 0:
					repeat = false;
					break;
				case 1:
					break;
				case 2: case -1:
					System.exit(0);
				}
			}
			return fc.getSelectedFile();
		}catch(Exception e){}
		return null;
	}
	
	public static Map createMap(File file){
		FileInput in = new FileInput(file.getPath());
		//get adventure name
		String name = in.readLine();
		//SKIP COMMENT LINE
		in.readLine();
		//get spawn point (room, y, x)
		int spawnRoom = in.readInt();
		int spawnY = in.readInt();
		int spawnX = in.readInt();
//		//TODO DEBUG, cumulative check
//		JOptionPane.showMessageDialog(null, name+"\n"+
//											"Room "+spawnRoom+"\n"+
//											"SpawnCoords "+spawnCoord, "Info", JOptionPane.PLAIN_MESSAGE);
		//create empty list of rooms
		ArrayList<Room> rooms = new ArrayList<Room>();
		//start checking rooms
		while(!in.eof()){
			if(!in.readLine().equals("ROOMSTART"))
				break;
			//add new room to list
			int[][][] tiles = new int[Room.NUMLAYERS][Room.HEIGHT][Room.WIDTH];
			for(int i = 0; i < Room.NUMLAYERS; i++)
				for(int y = 0; y < Room.HEIGHT; y++)
					for(int x = 0; x < Room.WIDTH; x++)
						tiles[i][y][x] = in.readInt();
			rooms.add(new Room(tiles));
			//read and add BGM destination (cut out string "BGM " from the data line)
			rooms.get(rooms.size()-1).setBGMTrack(in.readLine().replaceAll("BGM ", ""));
			//read for extra features, until ROOMEND
			boolean repeat = true;
			String line;
			while(repeat){
				line = in.readWord();
				if(line.equals("ROOMEND"))
					repeat = false;
				else if(line.equals("TELE")){
					Trigger t = new Trigger(Trigger.TELE, in.readInt(), in.readInt(),
									in.readInt(), in.readInt(), in.readInt());
					System.out.println("TELE Entrance "+t.getIntParam(0)+", "+t.getIntParam(1)+"\n"+
												"Dest "+t.getIntParam(2)+": "+t.getIntParam(3)+", "+t.getIntParam(4)); //TODO DEBUG
					rooms.get(rooms.size()-1).addTrigger(t);
				}else if(line.equals("WALL")){
					Trigger t = new Trigger(Trigger.WALL, in.readInt(), in.readInt());
					System.out.println("WALLTELE Side "+t.getIntParam(0)+"\n"+
												"Dest "+t.getIntParam(1)); //TODO DEBUG
					rooms.get(rooms.size()-1).addTrigger(t);
				}else if(line.equals("ENEMYSPAWN")){
					Trigger t = new Trigger(Trigger.ENEMYSPAWN, in.readInt(), in.readInt(), in.readInt());
					System.out.println("ENEMYSPAWN Location "+t.getIntParam(0)+", "+t.getIntParam(1)+"\n"+
												"Enemy Type "+t.getIntParam(2)); //TODO DEBUG
					rooms.get(rooms.size()-1).addTrigger(t);
				}
			}
		}
//		//TODO DEBUG, show that map file was read
//		JOptionPane.showMessageDialog(null, "Chill", "Info", JOptionPane.PLAIN_MESSAGE);
		//return new map
		return new Map(rooms, name, spawnRoom, spawnY, spawnX);
	}
	
	public static void saveMap(Map map, String name){
		FileOutput out = new FileOutput("maps/"+name+".txt");
		//save adventure name
		out.writeString(name);
		out.writeEndOfLine();
		//save comment line
		out.writeString("//spawn point (room, y, x)");
		out.writeEndOfLine();
		//save spawn point (room, y, x)
		out.writeString(map.getSpawnRoom()+" ");
		out.writeString(map.getSpawnY()+" ");
		out.writeString(map.getSpawnX()+"");
		out.writeEndOfLine();
		//save rooms
		map.saveRooms(out);
		//finish
		out.close();
	}
}