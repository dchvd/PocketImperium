package pocket_imperium;

import java.util.ArrayList;
/**
 * La classe Hex permet de déterminer les diffèrents Hex de la carte
 */
public class Hex {
	private int xPosition;
	private int yPosition;
	private ArrayList<Ship> shipsOnHex;
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
			this.value=0;
			this.nbMaxShips=4;
		}else if(systemHex1==true){
			this.value=1;
			this.nbMaxShips=2;
		}else {
			this.value=2;
			this.nbMaxShips=3;
		}
	}

	public void setCoordinates(int x, int y) {

		 this.xPosition = x; 
		 this.yPosition = y;

		 }

	public int getxPosition() {
		return xPosition;
	}



	public void setxPosition(int xPosition) {
		this.xPosition = xPosition;
	}



	public int getyPosition() {
		return yPosition;
	}



	public void setyPosition(int yPosition) {
		this.yPosition = yPosition;
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

	public void setValue(int value) {
		this.value = value;
	}

	public int getNbMaxShips() {
		return nbMaxShips;
	}

	public void setNbMaxShips(int nbMaxShips) {
		this.nbMaxShips = nbMaxShips;
	}

	public boolean isTriPrime() {
		return triPrime;
	}

	public void setTriPrime(boolean triPrime) {
		this.triPrime = triPrime;
	}

	public boolean isSystemHex1() {
		return systemHex1;
	}

	public void setSystemHex1(boolean systemHex1) {
		this.systemHex1 = systemHex1;
	}

	public boolean isSystemHex2() {
		return systemHex2;
	}

	public void setSystemHex2(boolean systemHex2) {
		this.systemHex2 = systemHex2;
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
	
}


