import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
//ACCOMODATED FOR DUAL LAYER, DELETE WHEN FINISHED

public class Editor{
	private Map map;
	private Canvas canvas;
	private TileSelectionCanvas tsCanvas;
	private JFrame frame;
	private JScrollPane scroll;
	private JButton save, switchRoom, triggers;
	private JTextField nameField;
	private JPanel tools;
	private RoomTileSelectionFrame rtsf;
	
	public Editor(Map map){
		//initial setup
		this.map = map;
		ImageHandler.loadImages();
		
		rtsf = new RoomTileSelectionFrame(this);
		rtsf.setLocationRelativeTo(null);
		rtsf.setVisible(false);
		
		//general gui/frame setup
		
		frame = new JFrame(map.getName());
		Container cont;
		cont = frame.getContentPane();
		
		nameField = new JTextField(map.getName());
		nameField.setEditable(true);
		cont.add(nameField, BorderLayout.NORTH);
		
		canvas = new Canvas((Room.HEIGHT+2)*Room.TILE_SIZE, (Room.WIDTH+2)*Room.TILE_SIZE, this);
		cont.add(canvas, BorderLayout.CENTER);
		
		tools = new JPanel();
		tools.setLayout(new GridLayout(2, 2));
		
		tsCanvas = new TileSelectionCanvas(this);
		scroll = new JScrollPane(tsCanvas, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
											JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		tools.add(scroll);
		
		save = new JButton("Save");
		save.addMouseListener(new MouseListener(){
			public void mouseClicked(MouseEvent e){
				frame.setVisible(false);
				saveMap();
				JOptionPane.showMessageDialog(null, "File Saved", "File Saved", JOptionPane.INFORMATION_MESSAGE);
				frame.setVisible(true);
			}
			public void mouseEntered(MouseEvent e){}
			public void mouseExited(MouseEvent e){}
			public void mousePressed(MouseEvent e){}
			public void mouseReleased(MouseEvent e){}
		});
		tools.add(save);
		
		switchRoom = new JButton("Switch Room");
		switchRoom.addMouseListener(new MouseListener(){
			public void mouseClicked(MouseEvent e){
				frame.setVisible(false);
				switchRoom();
				canvas.repaint();
				try{ Thread.sleep(150); }catch(Exception ex){}
				frame.setVisible(true);
			}
			public void mouseEntered(MouseEvent e){}
			public void mouseExited(MouseEvent e){}
			public void mousePressed(MouseEvent e){}
			public void mouseReleased(MouseEvent e){}
		});
		tools.add(switchRoom);
		
		triggers = new JButton("Triggers");
		triggers.addMouseListener(new MouseListener(){
			public void mouseClicked(MouseEvent e){
				frame.setVisible(false);
				addTrigger();
				canvas.repaint();
				try{ Thread.sleep(150); }catch(Exception ex){}
				frame.setVisible(true);
			}
			public void mouseEntered(MouseEvent e){}
			public void mouseExited(MouseEvent e){}
			public void mousePressed(MouseEvent e){}
			public void mouseReleased(MouseEvent e){}
		});
		tools.add(triggers);
		
		cont.add(tools, BorderLayout.SOUTH);
		
		frame.pack();
		frame.setSize(getCanvasWidth(), frame.getHeight());
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.toFront();
		frame.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				System.exit(0);
			}
		});
	}
	
	public void run(){
		frame.setVisible(true);
		
	}
	
	public void paint(Graphics g){
		map.paint(g);
	}
	
	public int getSelectedTileType(){
		return tsCanvas.getSelectedTile();
	}
	
	public void setTile(int y, int x){
		map.setTile(tsCanvas.getSelectedTile(), tsCanvas.getSelectedLayer(), y, x);
	}
	
	public void saveMap(){
		FileHandler.saveMap(map, nameField.getText().trim());
	}
	
	public void switchRoom(){
		//create options
		String[] options = new String[map.getNumRooms()+1];
		for(int i = 0; i < options.length; i++){
			if(i == 0)
				options[i] = "Create New";
			else
				options[i] = (i-1)+"";
		}
		//show prompt
		String choice = (String)JOptionPane.showInputDialog(null, "Choose a room to switch to, or create a new one",
				"Switch Room", JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
		//if "create new" selected, create new room
		if(choice.equals("Create New"))
			map.createNewRoom();
		//else, switch to selected room
		else
			try{
				map.switchToRoom(Integer.parseInt(choice));
			}catch(Exception e){}
	}
	
	public void addTrigger(){
		//create options
		String[] options = {"Remove Trigger", "Spawn", "Teleport", "Wall Teleport", "Enemy Spawn"};
		//show prompt for type
		try{
			String choice = ((String)JOptionPane.showInputDialog(null, "Select a trigger type",
					"Add Trigger", JOptionPane.PLAIN_MESSAGE, null, options, options[0])).trim();
			//REMOVE
			if(choice.equals("Remove Trigger")){
				int y = Integer.parseInt(JOptionPane.showInputDialog("Select trigger y", "Add Trigger"));
				int x = Integer.parseInt(JOptionPane.showInputDialog("Select trigger x", "Add Trigger"));
				map.removeTrigger(y, x);
			//SPAWN
			}if(choice.equals("Spawn")){
				int r = Integer.parseInt(JOptionPane.showInputDialog("Select spawn room", "Add Trigger"));
				int y = Integer.parseInt(JOptionPane.showInputDialog("Select spawn y", "Add Trigger"));
				int x = Integer.parseInt(JOptionPane.showInputDialog("Select spawn x", "Add Trigger"));
				map.setSpawn(r, y, x);
			//TELE
			}else if(choice.equals("Teleport")){
				int y = Integer.parseInt(JOptionPane.showInputDialog("Select teleport y", "Add Trigger"));
				int x = Integer.parseInt(JOptionPane.showInputDialog("Select teleport x", "Add Trigger"));
				int dr = Integer.parseInt(JOptionPane.showInputDialog("Select destination room", "Add Trigger"));
				int dy = Integer.parseInt(JOptionPane.showInputDialog("Select destination y", "Add Trigger"));
				int dx = Integer.parseInt(JOptionPane.showInputDialog("Select destination x", "Add Trigger"));
				map.addTrigger(new Trigger(Trigger.TELE, y, x, dr, dy, dx));
				
			//WALL
			}else if(choice.equals("Wall Teleport")){
				int side = Integer.parseInt(JOptionPane.showInputDialog("Select wall", "Add Trigger"));
				int r = Integer.parseInt(JOptionPane.showInputDialog("Select destination room", "Add Trigger"));
				map.addTrigger(new Trigger(Trigger.WALL, side, r));
			//ENEMY SPAWN
			}else if(choice.equals("Enemy Spawn")){
				int y = Integer.parseInt(JOptionPane.showInputDialog("Select y", "Add Trigger"));
				int x = Integer.parseInt(JOptionPane.showInputDialog("Select x", "Add Trigger"));
				//Coord c = rtsf.selectTileSequence(map.getCurrentRoom());
				int type = Integer.parseInt(JOptionPane.showInputDialog("Select enemy type", "Add Trigger"));
				map.addTrigger(new Trigger(Trigger.ENEMYSPAWN, y, x, type));
				//map.addTrigger(new Trigger(Trigger.ENEMYSPAWN, c.getY(), c.getY(), type));
			}
		//error in trigger input
		}catch(Exception e){
			JOptionPane.showMessageDialog(null, "Error in trigger input", "ERROR", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public Map getMap(){
		return map;
	}
	
	public int getCanvasHeight(){
		return canvas.getHeight();
	}
	
	public int getCanvasWidth(){
		return canvas.getWidth();
	}
}