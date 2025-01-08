package pocket_imperium;
import java.io.*;

public class Ship implements Serializable {
	private static final long serialVersionUID = 1L;

	//Attributs
	private Player owner;
	private int id;
	private int xPosition;
	private int yPosition;
	private boolean placedOnBoard;


	//Constructeur
	// lorsque le ship n'est pas placé sur un hex, il est en x=-1 y=-1
	public Ship( Player owner, int id) {
		this.owner=owner;
		this.id=id;
		this.xPosition=-1;
		this.yPosition=-1;
		this.placedOnBoard=false;
	}

	public void setPosition(int x,int y){
		this.xPosition=x;
		this.yPosition=y;
		this.placedOnBoard = true;
	}

	public void destroyShip(){
		this.xPosition=-1;
		this.yPosition=-1;
		placedOnBoard=false;
	}

	public boolean isPlaced() {
		return placedOnBoard;
	}

	public String getOwnerName() {
		return owner.getName();
	}
	public String toString(){
		return "Vaisseau de " + this.owner.getName() + " en x = " + xPosition + ", y = " + yPosition + "\n";
	}
}
