package pocket_imperium;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Helper {

	public static int[][] placeSystems (String typeCarte) {
		Random randomNumbers = new Random();	
		int empPossible;
		if (typeCarte=="ExteriorCard") {
			empPossible = 5;
		}else {
			empPossible=4;
		}
	    boolean ValidPlacement = false;	
	    int[][] coordSyst = {{0,0},{0,0},{0,0}};
	    int[] placements = {0,0,0};
	    while (!ValidPlacement) {
	    	for (int i = 0; i<placements.length; i++) {
	    		placements[i]=randomNumbers.nextInt(empPossible);
	    	}
	        if (placements[0] !=placements[1] && placements[0] !=placements[2] ) {
	        	ValidPlacement=true;
	        }
	    }
	    for (int i = 0; i<placements.length; i++) {
	    	if (typeCarte =="ExteriorCard") {
		    	if (placements[i]==0) {
		    		coordSyst[i][0]=0;
		    		coordSyst[i][1]=0;
		    	}else if (placements[i]==1) {
		    		coordSyst[i][0]=0;
		    		coordSyst[i][1]=1;
		    	}else if (placements[i]==2) {
		    		coordSyst[i][0]=1;
		    		coordSyst[i][1]=1;
		    	}else if (placements[i]==3) {
		    		coordSyst[i][0]=2;
		    		coordSyst[i][1]=0;
		    	}else{
		    		coordSyst[i][0]=2;
		    		coordSyst[i][1]=1;
		    	}
		    	
		    }else if (typeCarte =="MiddleExteriorCard") {
		    	if (placements[i]==0) {
		    		coordSyst[i][0]=0;
		    		coordSyst[i][1]=0;
		    	}else if (placements[i]==1) {
		    		coordSyst[i][0]=1;
		    		coordSyst[i][1]=0;
		    	}else if (placements[i]==2) {
		    		coordSyst[i][0]=1;
		    		coordSyst[i][1]=1;
		    	}else {
		    		coordSyst[i][0]=2;
		    		coordSyst[i][1]=0;
		    	}
		    }else {
		    	System.out.println("problème de type de carte");
		    }
	    }
	    return coordSyst;
	}
	/**
     * La fonction TestOccupationHex permet de vérifier si le hex choisi est occupé par un joueur ou non
     * @param hex est le hex dont on souhaite vérifier l'occupation
     * @return true si il est occupé, false sinon
     */
    public static boolean TestOccupationHex(Hex hex) {
    	if(hex.getControlledBy()==null) {
    		return false;
    	}else {
    		return true;
    	}
    }
    
    /**
     * La fonction TestOccupationPlayerHex permet de vérifier si le hex choisi est occupé par le joueur actuel ou non
     * @param hex est le hex dont on souhaite verifier l'occupation
     * @param player est le joueur actuel
     * @return true si le joueur occupe le hex, false sinon
     */
    public static boolean TestOccupationPlayerHex(Hex hex, Player player) { 
    	if(hex.getControlledBy()!=player) {
    		return false;
    	}else {
    		return true;
    	}
    }
    
    /**
     * La fonction GainControllHex permet à un joueur de prendre le controle d'un hex
     * @param hex est le hex que le joueur va occuper
     * @param nbShips est le nombre de vaisseaux que le joueur a sur cet hex
     * @param player est le joueur qui va occuper le hex
     */
    public static void GainControllHex(Hex hex, int nbShips,Player player) {
    	hex.setControlled(true);
    	hex.setControlledBy(player);
    	ArrayList<Ship> newShipsOnHex=hex.getShipsOnHex();
    	for(int i=0;i<nbShips;i++) {
    		Ship newShip= new Ship(player); //qu'est-ce qu'on fait avec la couleur ducoup ? 
    		newShipsOnHex.add(newShip);
    	}
    	hex.setShipsOnHex(newShipsOnHex);
		
	}
    
	/**
     * La fonction CheckNeighboursHex vérifie si le hex de destination choisi est bien voisin au hex de départ
     * @param xHexDeparture est la coordonnée x du hex de départ
     * @param yHexDeparture est la coordonnée y du hex de départ
     * @param xHexDestination est la coordonnée x du hex de destination
     * @param yHexDestination est la coordonnée y du hex de destination
     * @return true si le hex est bien voisin, false sinon
     */
	public static boolean CheckNeighboursHex(int xHexDeparture, int yHexDeparture, int xHexDestination, int yHexDestination) {
		if((xHexDestination<=xHexDeparture+1)||(xHexDestination>=xHexDeparture-1)){
			if((yHexDestination<=yHexDeparture+1)||(yHexDestination>=yHexDeparture-1)) {
				if(xHexDeparture%2==0) {
					if((xHexDestination==xHexDeparture-1)||(yHexDestination==yHexDeparture-1)) {
						return false;
					}else if((xHexDestination==xHexDeparture+1)||(yHexDestination==yHexDeparture)) {
						return false;
					}else {
						return true;
					}
				}else{
					if((xHexDestination==xHexDeparture-1)||(yHexDestination==yHexDeparture+1)) {
						return false;
					}else if((xHexDestination==xHexDeparture+1)||(yHexDestination==yHexDeparture+1)) {
						return false;
					}else {
						return true;
					}
				}
			}else {
				return false;
			}
			
		}else {
			return false;
		}
    }
	
	/**
	 * La fonction removeShipsFromHex permet de retirer des vaisseaux du joueur d'un des Hex qu'il contrôle
	 * @param nbShips correspond au nombre de vaisseaux que possede le joueur sur le hex
	 * @param hex correspond au hex controlle par la joueur duquel on veut retirer des vaisseaux
	 */
	public static void removeShipsFromHex(int nbShips, Hex hex) {
		ArrayList<Ship> newShipsOnHex=hex.getShipsOnHex();
		for (int i=0;i<nbShips;i++) {
			newShipsOnHex.remove(i);
		}
		hex.setShipsOnHex(newShipsOnHex);
	}

}
