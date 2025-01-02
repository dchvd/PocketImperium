package pocket_imperium;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Helper {

	public static int[][] placeSystems (String typeCarte) {
		Random randomNumbers = new Random();
		int empPossible;
		if (typeCarte.equals("ExteriorCard")) {
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
			if (placements[0] !=placements[1] && placements[0] !=placements[2] && placements[1] !=placements[2]) {
				ValidPlacement = true;
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

			}else if (typeCarte.equals("MiddleExteriorCard")) {
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
		ArrayList<Ship> newShipsOnHex=hex.getShipsOnHex();//mauvais endroit je crois
		for(int i=0;i<nbShips;i++) {
			Ship newShip= new Ship(player, 0); //qu'est-ce qu'on fait avec la couleur ducoup ?
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
					if((xHexDestination==xHexDeparture-1)||(yHexDestination==yHexDeparture+1)) {
						return false;
					}else if((xHexDestination==xHexDeparture+1)||(yHexDestination==yHexDeparture+1)) {
						return false;
					}else {
						return true;
					}
				}else{
					if((xHexDestination==xHexDeparture-1)||(yHexDestination==yHexDeparture-1)) {
						return false;
					}else if((xHexDestination==xHexDeparture+1)||(yHexDestination==yHexDeparture-1)) {
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
			newShipsOnHex.remove(0);
		}
		hex.setShipsOnHex(newShipsOnHex);
	}

	/**
	 * La methode findSystemNeighbours permet de trouver les systemes voisins du hex d'entree
	 * @param hex correspond au hex d'entree pour lequel on souhaite connaitre les voisins systemes
	 * @param board correspond au plateau de jeu sur lequel se passe la partie
	 * @return la liste des hexs repondants a ces deux criteres
	 */
	public static ArrayList<Hex> findSystemNeighbours(Hex hex, Board board) {
		ArrayList<Hex> systemNeighbours=new ArrayList<Hex>();
		int x=hex.getxPosition();
		int y=hex.getyPosition();
		boolean isSystem=false;
		boolean isNeighbour=false;
		for(int i=0;i<board.getGameBoard().size();i++) {
			for(int j=0;j<board.getGameBoard().get(i).size();j++) {
				Hex potentialHex=board.getGameBoard().get(i).get(j);
				if(potentialHex.isSystemHex1()||potentialHex.isSystemHex2()||potentialHex.isTriPrime()) {
					isSystem=true;
					if(CheckNeighboursHex(x,y,potentialHex.getxPosition(),potentialHex.getyPosition())) {
						isNeighbour=true;
					}
				}
				if(isSystem&&isNeighbour) {
					systemNeighbours.add(potentialHex);
				}
				isSystem=false;
				isNeighbour=false;
			}
		}
		System.out.println("Systèmes voisins trouvés :");
		StringBuilder result = new StringBuilder();
		for (Hex neighbour : systemNeighbours) {
			int neighbourX = neighbour.getxPosition();
			int neighbourY = neighbour.getyPosition();
			System.out.println("Hex voisin - x: " + neighbourX + ", y: " + neighbourY+", nombre de vaisseaux: "+neighbour.getShipsOnHex().size());
			result.append("Hex voisin - x: ").append(neighbourX).append(", y: ").append(neighbourY).append(", nb_vaisseaux: ").append(neighbour.getShipsOnHex().size()).append("\n");
		}

		return systemNeighbours;
	}

	public static boolean HexAlreadyChoosed(Hex hexToInvadeFrom, ArrayList<Hex> hexsToInvadeFrom) {
		boolean answer=hexsToInvadeFrom.contains(hexToInvadeFrom);
		if(answer) {
			return true;
		}else {
			return false;
		}
	}
}
