package pocket_imperium;

import java.util.ArrayList;
import java.util.List;

/**
 * La classe Hex permet de déterminer les différents Hex de la carte
 */
public class Hex {
	private double x_position;
	private double y_position;

	private List<Ship> shipsOnHex;
	private int value;
	private int nbMaxShips;
	private boolean triPrime, systemHex1, systemHex2;
	
	public Hex(double x, double y, boolean triPrime, boolean systemHex1, boolean systemHex2) {
		this.x_position=x;
		this.y_position=y;
		this.triPrime=triPrime;
		this.systemHex1=systemHex1;
		this.systemHex2=systemHex2;
		
		if(triPrime==true) {
			value = 3;
		}else if (systemHex2==true){
			value = 2;
		}else if (systemHex1==true){
			value = 1;
		}else {
			value=0;
		}
	}
	public void setCoordinates(double x, double y) {
		this.x_position = x; 
		this.y_position = y;
	}
	@Override
    public String toString() {
        return "Hex{" +
                " x='" + x_position + '\'' +
                " y='" + y_position + '\'' +
                " value='" + value + '\'' +
                '}';
    }
	
}
