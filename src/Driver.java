import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;


public class Driver{

	public static void main(String[] args){
		JOptionPane.showMessageDialog(null, "Welcome! Please choose a file to edit.",
				"Chim Adventure - Editor", JOptionPane.PLAIN_MESSAGE);
		File file = FileHandler.loopingFileChooserSequence();
		//if null file, inform and close program
		if(file == null){
			JOptionPane.showMessageDialog(null, "ERROR. No file selected.",
					"ERROR", JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}
		//create map for this file
		Map map = FileHandler.createMap(file);
		//if null map, inform and close program
		if(map == null){
			JOptionPane.showMessageDialog(null, "ERROR. Map file invalid.",
					"ERROR", JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}
		//start game with this map
		Editor editor = new Editor(map);
		editor.run();
	}
}