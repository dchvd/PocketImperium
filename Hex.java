package pocket_imperium;
/**
 * La classe Hex permet de déterminer les diffèrents Hex de la carte
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
			
		}else {
			
		}
	}
	
}
