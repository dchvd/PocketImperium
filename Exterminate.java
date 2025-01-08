package pocket_imperium;

import java.util.ArrayList;

public class Exterminate{
	private Hex systemToInvade;
	private Hex systemToInvadeFrom;
	private int nbShipsAttacker;
	private int nbShipsDefendant;

	/**
	 * Le constructeur Exterminate permet de vérifier que tous les choix faits par les joueurs sont corrects avant de lancer l'action
	 * @param systemToInvade est le système que le joueur souhaite envahir
	 * @param nbShipsAttacker est le nombre de vaisseaux de l'attaquant
	 * @param nbShipsDefendant est le nombre de vaisseaux du défendant
	 * @param player est le joueur attaquant
	 */
	public Exterminate(Hex systemToInvade, Hex systemsToInvadeFrom, int nbShipsAttacker, int nbShipsDefendant, Player player) {
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
		if(nbShipsAttacker<=systemToInvadeFrom.getShipsOnHex().size()) {
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

	/**
	 * La methode determineWinner permet de déterminer le joueur qui controle le systeme apres a la fin de l'execution de la commande Exterminate
	 * @param nbShipsAttacker est le nombre de vaisseaux du joueur qui attaque le systeme
	 * @param nbShipsDefendant est le nombre de vaisseaux du joueur qui defend son systeme, celuis qui controle le systeme initialement 
	 * @param systemToInvade est le systeme sur lequel est performee la commande Exterminate
	 * @param attacker est l'objet Player qui represente le joueur qui effectue la commande Exterminate
	 * @return un message in diquant lequel des deux joueurs controle le systeme a la fin de l'execution de Exterminate, ou si le systeme n'est plus sous controlle. 
	 */
	public static String DetermineWinner(int nbShipsAttacker, int nbShipsDefendant, Hex systemToInvade, Hex systemToInvadeFrom, Player attacker) {
		// Cas où les deux forces sont égales
		if (nbShipsAttacker == nbShipsDefendant) {
			systemToInvadeFrom.setControlled(false);
			systemToInvadeFrom.setControlledBy(null);

			for (int i =0; i < nbShipsAttacker; i++) {
				systemToInvade.getShipsOnHex().get(0).destroyShip();
				//systemToInvadeFrom.getShipsOnHex().get(0).destroyShip();
			}
			// Supprimer tous les vaisseaux sur les hexs concernés
			if (systemToInvade.getShipsOnHex() != null) {
				systemToInvade.getShipsOnHex().clear();
			}


			// Retirer le contrôle de l'attaquant sur l'hex de départ
			attacker.getControlledHexs().remove(systemToInvadeFrom);

			// Retirer le contrôle du défendant si le système était contrôlé
			if (systemToInvade.getControlledBy() != null) {
				Player defendant = systemToInvade.getControlledBy();
				defendant.getControlledHexs().remove(systemToInvade);
				systemToInvade.setControlled(false);
				systemToInvade.setControlledBy(null);
			}
			return "Aucun gagnant : le système n'est plus contrôlé.";
		}

		// Calcul des forces restantes après le combat
		int nbShipsAttackerLeft = Math.max(0, nbShipsAttacker - nbShipsDefendant);
		int nbShipsDefendantLeft = Math.max(0, nbShipsDefendant - nbShipsAttacker);

		// Cas où l'attaquant gagne
		if (nbShipsAttackerLeft > nbShipsDefendantLeft) {

			//Suppression du système de la liste des hexs contrôllés du défendant
			Player defendant = systemToInvade.getControlledBy();
			if (defendant != null) {
				defendant.getControlledHexs().remove(systemToInvade);
				for (int i =0; i < nbShipsAttacker; i++) {
					systemToInvade.getShipsOnHex().getFirst().destroyShip();
				}
				systemToInvade.getShipsOnHex().clear();
			}
//			for (int i =0; i < nbShipsAttacker; i++) {
//				systemToInvadeFrom.getShipsOnHex().getFirst().destroyShip();
//			}

			//ajout du système à la liste des hexs controllés par l'attaquant
			attacker.getControlledHexs().add(systemToInvade);
			systemToInvade.setControlled(true);
			systemToInvade.setControlledBy(attacker);

			return "Le gagnant est l'attaquant : il contrôle désormais le système attaqué.";
		}

		// Cas où le défendant gagne
		if (nbShipsDefendantLeft > nbShipsAttackerLeft) {

			// Retirer les vaisseaux perdus du défendant
			if (systemToInvade.getShipsOnHex() != null) {
				// Assurez-vous que la taille ne dépasse pas les vaisseaux disponibles
				int shipsToRemove = Math.min(nbShipsAttacker, systemToInvade.getShipsOnHex().size());
				// Retirer le contrôle et les vaisseaux utilisés de l'attaquant sur l'hex de départ et du défendant sur celui attaqué
				for (int i = 0; i < shipsToRemove; i++) {
					systemToInvade.getShipsOnHex().getFirst().destroyShip();
					systemToInvade.getShipsOnHex().removeFirst();
//					systemToInvadeFrom.getShipsOnHex().getFirst().destroyShip();
//					systemToInvadeFrom.getShipsOnHex().removeFirst();
				}
			}


			attacker.getControlledHexs().remove(systemToInvadeFrom);

			systemToInvadeFrom.setControlled(false);
			systemToInvadeFrom.setControlledBy(null);

			return "Le gagnant est le défendant : il continue de contrôler le système attaqué.";
		}

		// Cas d'erreur imprévu
		return "Erreur : résultat du combat non déterminé.";
	}
}
