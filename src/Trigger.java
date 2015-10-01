
public class Trigger{
	public final static int TELE = 0, WALL = 1, ENEMYSPAWN = 2;
	private int type;
	private int[] intParams;
	/*
	 * PARAMS:
	 * TELE 5 (y, x, dr, dy, dx)
	 * WALL 2 (side, dr)
	 * ENEMY SPAWN 3 (y, x, type)
	 */
	
	public Trigger(int type, int ... intParams){
		this.type = type;
		this.intParams = intParams;
	}
	
	public int getType(){
		return type;
	}
	
	public int getIntParam(int index){
		return intParams[index];
	}
	
	public String toString(){
		String result = "";
		switch(type){
		case TELE:
			result += "TELE";
			break;
		case WALL:
			result += "WALL";
			break;
		case ENEMYSPAWN:
			result += "ENEMYSPAWN";
		}
		for(Integer i : intParams)
			result += " "+i;
		return result;
	}
	
	public String getSideString(){
		if(type != WALL)
			return "";
		else if(intParams[0] == 0)
			return "N";
		else if(intParams[0] == 1)
			return "E";
		else if(intParams[0] == 2)
			return "S";
		else if(intParams[0] == 3)
			return "W";
		return "";
	}
	
	public String getEnemyString(){
		if(type != ENEMYSPAWN)
			return "";
		switch(intParams[2]){
		case 0:
			return "BASIC ENEMY";
		}
		return "N/A";
	}
	
	public boolean equals(int y, int x){
		switch(type){
		case TELE:
		case ENEMYSPAWN:
			if(intParams[0] == y && intParams[1] == x)
				return true;
		}
		return false;
	}
}