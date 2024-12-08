package pocket_imperium;

public class Exterminate{
	private Hex systemToInvade;
	private Hex systemToInvadeFrom;
	private int nbShipsAttacker;
	private int nbShipsDefendant;
	
	/**
	 * Le constructeur Exterminate permet de vérifier que tous les choix faits par les joueurs sont corrects avant de lancer l'action
	 * @param systemToInvade est le système que le joueur souhaite envahir
	 * @param systemToInvadeFrom est le système à partir duquel le joueur souhaite envahir
	 * @param nbShipsAttacker est le nombre de vaisseaux de l'attaquant
	 * @param nbShipsDefendant est le nombre de vaisseaux du défendant
	 * @param player est le joueur attaquant
	 */
	public Exterminate(Hex systemToInvade, Hex systemToInvadeFrom, int nbShipsAttacker, int nbShipsDefendant, Player player) {
		//Déterminer le système à attaquer et le système attaquant
		if((systemToInvade.isSystemHex1()==true)||(systemToInvade.isSystemHex2()==true)||(systemToInvade.isTriPrime()==true)) {
			boolean systemDefendantControlledByThisPlayer=Helper.TestOccupationPlayerHex(systemToInvade, player);
			if(!systemDefendantControlledByThisPlayer) {
				boolean systemAttackaerControlledByThisPlayer=Helper.TestOccupationPlayerHex(systemToInvadeFrom, player);
				if(systemAttackaerControlledByThisPlayer) {
					int xSystemToInvade=systemToInvade.getxPosition();
					int ySystemToInvade=systemToInvade.getyPosition();
					int xSystemToInvadeFrom=systemToInvadeFrom.getxPosition();
					int ySystemToInvadeFrom=systemToInvadeFrom.getyPosition();
					boolean HexesAreNeighbours=Helper.CheckNeighboursHex(xSystemToInvadeFrom,ySystemToInvadeFrom,xSystemToInvade, ySystemToInvade);
					if(HexesAreNeighbours) {
						this.systemToInvade=systemToInvade;
						this.systemToInvadeFrom=systemToInvadeFrom;
					}else {
						System.out.println("Vous ne pouvez pas envahir ce système car il est trop éloigné. Veuillez choisir un système voisin.");
					}
				}else {
					System.out.println("Vous devez attaquer à partir d'un hex que vous controlez.");
				}
			}else {
				this.systemToInvade=null;
				System.out.println("Veuillez choisir un système à attaquer que vous ne controllez pas.");
			}
		}else {
			this.systemToInvade=null;
			System.out.println("Veuillez choisir un système (1, 2 ou TriPrime) à envahir.");
		}
		
		//Déterminer le nombre de vaisseaux de l'attaquant
		if(nbShipsAttacker<=systemToInvadeFrom.getShipsOnHex().length) {
			this.nbShipsAttacker=nbShipsAttacker;
		}else {
			System.out.println("Vous n'avez pas autant de vaisseau sur votre hex. Choisissez un nombre de vaisseaux plus petit.");
		}
		
		//Déterminer le nombre de vaisseaux du défendant
		this.nbShipsDefendant=nbShipsDefendant;
	}
	
	/**
	 * La methode RemoveShipsAttacker permet de calculer le nombre de vaisseaux de l'attaquant restants après l'attaque
	 * @param nbShipsAttacker est le nombre de vaisseaux de l'attaquant
	 * @param nbShipsDefendant est le nombre de vaisseaux du défendant
	 * @return le nombre de vaisseaux de l'attaquant restants
	 */
	public static int RemoveShipsAttacker(int nbShipsAttacker, int nbShipsDefendant) {
		if(nbShipsAttacker>nbShipsDefendant) {
			nbShipsAttacker-=nbShipsDefendant;
			return nbShipsAttacker;
		}else {
			return 0;
		}
	}
	
	/**
	 * La methode RemoveShipsAttacker permet de calculer le nombre de vaisseaux du defendant restants après l'attaque
	 * @param nbShipsAttacker est le nombre de vaisseaux de l'attaquant
	 * @param nbShipsDefendant est le nombre de vaisseaux du défendant
	 * @return le nombre de vaisseaux du defendant restants
	 */
	public static int RemoveShipsDefendant(int nbShipsAttacker, int nbShipsDefendant) {
		if(nbShipsAttacker<nbShipsDefendant) {
			nbShipsDefendant-=nbShipsAttacker;
			return nbShipsDefendant;
		}else {
			return 0;
		}
	}
}
