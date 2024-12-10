package pocket_imperium;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * La classe Partie permet de gérer le déroulement d'une partie de Imperium Pocket.
 */
public class Partie {
	/**
	 * La liste des Joueurs permet d'enregistrer les différents joueurs qui vont la partie de Imperium Pocket
	 */
	private ArrayList<Player> players = new ArrayList<Player>(); 
	/**
	 * La variable tour permet de compter les tours de la partie, sachant qu'une partie ne peut pas avoir plus de 9 tours.
	 */
    private int tour=0;
    
 // ---- PLATEAU ----
    //board est composé des sectorCards, il permettra notamment de compter les scores
    //private SectorCard[][] board = new SectorCard[3][3];
    //gameBoard sera la grille de Hex que l'on utilise pour les fonctions expand, exterminate et explore
    //public List<List<Hex>> gameBoard = new ArrayList<>();
    
    //Génère le plateau : 
    private Board gameBoard = new Board();
    
    /**
     * Le constructeur Partie permet de créer une partie du jeu Imperium Pocket
     * @param players est la liste des joueurs qui s'affronteront durant la partie
     */
    public Partie() {
    	System.out.println(" --- Création des joueurs ! --- \n Chaque joueur doit choisir un surnom. \n");
    	System.out.println(" Joueur n°1 :");
    	this.players.add(new Player());
    	System.out.println("\n Joueur n°2 :");
    	this.players.add(new Player());
    	System.out.println("\n Joueur n°3 :");
    	this.players.add(new Player());
    	this.startGame();
    }
    
    /**
     * La fonction startGame permet de lancer la partie
     */
    public void startGame() {
    	this.initialisation();
        while (!finPartie()) {
            Tour();
            tour++;
        }
        declareWinner();
    }
    
    private void initialisation() {
    	Scanner sc = new Scanner(System.in);
    	System.out.println(" --- Début de la partie ! --- \n Chaque joueur doit positionner deux bateaux sur un système de niveau 1. \n"
    			+ "Chaque joueur doit être seul sur son secteur : vous ne pouvez pas placer des bateaux sur un secteur déjà occupé");
    	for (Player player : players) {
    		while(true) {
    			System.out.println(player.getName()+ " : choisissez le système à habiter"
    					+ "\n donnez le x du Hex choisi ");
    	    	int x = sc.nextInt();
    	    	System.out.println("Entrez le y du hex choisi.");
				int y=sc.nextInt();
				Hex choosedHex=this.gameBoard.determineHexFromCoordinates(x, y); // pourquoi elle est statique
				if(this.gameBoard.verifyCapacibility(choosedHex)) {
	            	System.out.println("Secteur valide");
	            	//choosedHex.isControlled = true;
	            	//choosedHex.isControlledBy(player);
	            	break;

				}else {
					System.out.println("Secteur déjà occupé: veuillez en choisir un autre.");
				}
				
    		}
    	}
	}

	/**
     * Fonction caractéristique d'un tour qui permet à chaque joueur à tour de rôle de choisir l'ordre des commandes,
     * puis de les exécuter. A la fin du tour, la fonction compte les scores de chaque joueur pour ce tour,
     * puis met à jour le score global du joueur.
     */
    private void Tour() {
    	System.out.println("Tour " + tour);
    	ArrayList <SectorCard> chosenSectors=new ArrayList<>();
    	for (Player player : players) {
    		player.planCommands("1", "2", "3");// faux
    		player.executeCommands();
    		}
    	for (Player player : players) {
    		SectorCard actualSector = player.chooseSector(gameBoard);
    		chosenSectors.add(actualSector);
    		}

    	 //calcul de scores
     	 }


    
    /**
     * Cette fonction permet d'arrêter la partie. Pour rappel, la partie s'arrête au bout de neuf tours, soit lorsqu'un
     * joueur est éliminé
     * @return false si ce n'est pas encore la fin de la partie, true si c'est la fin de la partie
     */
    private boolean finPartie() {
    	if (this.tour>9) {
    		return true; //ajouter l'option du si joueur éliminé
    	}
    	return false;
    }
    
    //Determiner Gagnant
    private void declareWinner() {
       
    }
    
}