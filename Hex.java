package pocket_imperium;

import java.util.ArrayList;
import java.util.List;

/**
 * La classe Hex permet de déterminer les différents Hex de la carte
 */
public class Hex {
	private int xPosition;
	private int yPosition;
	private ArrayList<Ship> shipsOnHex = new ArrayList<Ship>();
	private int value;
	private int nbMaxShips;
	private boolean triPrime, systemHex1, systemHex2;
	private boolean controlled=false;
	private Player controlledBy=null;
	
	public Hex(int x, int y, boolean triPrime, boolean systemHex1, boolean systemHex2) {
		this.xPosition=x;
		this.yPosition=y;
		this.triPrime=triPrime;
		this.systemHex1=systemHex1;
		this.systemHex2=systemHex2;
		
		if(triPrime==true) {
			value = 3;
			this.nbMaxShips=4;
		}else if (systemHex2==true){
			value = 2;
			this.nbMaxShips=3;
		}else if (systemHex1==true){
			value = 1;
			this.nbMaxShips=2;
		}else {
			value=0;
			this.nbMaxShips=1; // potentiellement 0
		}
	}
	

	public boolean isControlled() {
		return controlled;
	}

	public void setControlled(boolean controlled) {
		this.controlled = controlled;
	}

	public Player getControlledBy() {
		return controlledBy;
	}

	public void setControlledBy(Player controlledBy) {
		this.controlledBy = controlledBy;
	}
	public void setCoordinates(int x, int y) {
		this.xPosition = x; 
		this.yPosition = y;
	}
	
	@Override
    public String toString() {
        return "Hex{" +
                " x='" + xPosition + '\'' +
                " y='" + yPosition + '\'' +
                " value='" + value + '\'' +
                " nb_vaisseaux='"+this.shipsOnHex.size()+'\''+
                '}';
    }
	public int getxPosition() {
		return xPosition;
	}

	public int getyPosition() {
		return yPosition;
	}
	
	public ArrayList<Ship> getShipsOnHex() {
		return shipsOnHex;
	}
	public void setShipsOnHex(ArrayList<Ship> shipsOnHex) {
		this.shipsOnHex = shipsOnHex;
	}
	public int getValue() {
		return value;
	}
	
	public int getNbMaxShips() {
		return nbMaxShips;
	}
	
	public boolean isTriPrime() {
		return triPrime;
	}
	
	public boolean isSystemHex1() {
		return systemHex1;
	}
	
	public boolean isSystemHex2() {
		return systemHex2;
	}
	
	
}
