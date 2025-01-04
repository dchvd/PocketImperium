package pocket_imperium;

import java.util.*;

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
	private int[] scores= {0,0,0};
    
 // ---- PLATEAU ----
    //board est composé des sectorCards, il permettra notamment de compter les scores
    //private SectorCard[][] board = new SectorCard[3][3];
    //gameBoard sera la grille de Hex que l'on utilise pour les fonctions expand, exterminate et explore
    //public List<List<Hex>> gameBoard = new ArrayList<>();
    
    //Génère le plateau : 
    private Board gameBoard = new Board();
    
    /**
     * Le constructeur Partie permet de créer une partie du jeu Imperium Pocket
     * @param sc scanner pour récupérer les informations données par le joueur
     */
    public Partie(Scanner sc) {
		List<Color> availableColors = new ArrayList<>(Arrays.asList(Color.JAUNE, Color.ROUGE, Color.VIOLET));
    	System.out.println(" --- Création des joueurs ! --- \n Chaque joueur doit choisir un surnom. \n");
    	System.out.println(" Joueur n°1 :");
    	this.players.add(new Player(sc,0, availableColors));
    	System.out.println("\n Joueur n°2 :");
    	this.players.add(new Player(sc,1, availableColors));
    	System.out.println("\n Joueur n°3 :");
    	this.players.add(new Player(sc,2, availableColors));
    	this.startGame(sc);
    }
    
    /**
     * La fonction startGame permet de lancer la partie
     */
    public void startGame(Scanner sc) {
		System.out.println("Le plateau est : ");
		this.gameBoard.printCards();
		this.gameBoard.printBoard();
    	System.out.println("------ Initialisation ! ------");
    	this.initialisation(sc);
		//System.out.println("\n \n ------ Tour suivant ! ------");
		//sc.nextLine() // utile?
		//System.out.println("\n \n ------ Choix des secteurs sur lesquels gagner des points ! ------");
		//this.calcScore(sc, 1); // sera pas ici à la fin
		// ne pas oublier que quand c'est le dernier tour c'est pas calcScore qui est appelé mais finalCalcScore
    	//this.Tour(sc);
    	//this.tour++;
    	/*
        while (!finPartie()) {
        	System.out.println("------ Tour suivant ! ------");
            this.Tour();
            this.tour++;
        }*/
        //declareWinner();
		//System.out.println("------ Fin de la partie / Mise en Pause ! ------");
    }
    
    private void initialisation(Scanner sc) {
		Random random = new Random();
    	System.out.println("\n --- Début de la partie ! --- \n Chaque joueur doit positionner deux bateaux sur un système de niveau 1. \n"
    			+ " Chaque joueur doit être seul sur son secteur : vous ne pouvez pas placer des bateaux sur un secteur déjà occupé");
    	for (Player player : this.players) {
    		this.gameBoard.printBoard();
    		while(true) {
				if(!player.isVirtual()){
					System.out.println(player.getName()+ " : choisissez le système à habiter"
							+ "\n Entrez le x du Hex choisi ");
					int x = sc.nextInt();
					System.out.println("Entrez le y du hex choisi.");
					int y=sc.nextInt();
					Hex choosedHex=this.gameBoard.gameBoard.get(x).get(y); // pourquoi elle est statique
					if(this.gameBoard.verifyCapability(choosedHex)) {
						System.out.println("Secteur valide");
						choosedHex.setControlled(true);
						choosedHex.setControlledBy(player);
						ArrayList<Ship> shipsOnHex = new ArrayList<Ship>();
						shipsOnHex.add(player.getShips().get(0));
						player.getShips().get(0).setPosition(x, y);
						shipsOnHex.add(player.getShips().get(1));
						player.getShips().get(1).setPosition(x, y);
						choosedHex.setShipsOnHex(shipsOnHex);
						player.getControlledHexs().add(choosedHex);
						break;
					}else {
						System.out.println("Secteur déjà occupé: veuillez en choisir un autre.");
					}
				}else{
					System.out.println(player.getName()+" est entrain de choisir un secteur à habiter...");
					int x=(int)(Math.random()*9);
					int y = (int)(Math.random()*5);
					Hex choosedHex=this.gameBoard.gameBoard.get(x).get(y);
					while(!this.gameBoard.verifyCapability(choosedHex)) {
						x=(int)(Math.random()*9);
						y = (int)(Math.random()*5);
						choosedHex=this.gameBoard.gameBoard.get(x).get(y);
					}
					choosedHex.setControlled(true);
					choosedHex.setControlledBy(player);
					ArrayList<Ship> shipsOnHex = new ArrayList<Ship>();
					shipsOnHex.add(player.getShips().get(0));
					player.getShips().get(0).setPosition(x, y);
					shipsOnHex.add(player.getShips().get(1));
					player.getShips().get(1).setPosition(x, y);
					choosedHex.setShipsOnHex(shipsOnHex);
					player.getControlledHexs().add(choosedHex);
					System.out.println(player.getName()+" a choisi son hex!");
					break;
				}
    		}
    	}
    	System.out.println("\n --- Chaque joueur doit désormais re placer deux bateaux sur un système de niveau 1. \n"
    			+ " Vous ne pouvez pas placer des bateaux sur un secteur déjà occupé");
    	
    	List<Player> invertedPlayerList = new ArrayList<Player>();
    	invertedPlayerList = players.reversed();
    	
    	for (Player player : invertedPlayerList) {
    		this.gameBoard.printBoard();
    		while(true) {
				if(!player.isVirtual()){
					System.out.print(player.getName()+ " : choisissez le système à habiter"
							+ "\n Entrez le x du Hex choisi ");
					int x = sc.nextInt();
					System.out.print("Entrez le y du hex choisi.");
					int y=sc.nextInt();
					Hex choosedHex=this.gameBoard.gameBoard.get(x).get(y); // pourquoi elle est statique
					if(this.gameBoard.verifyCapability(choosedHex)) {
						System.out.println("Secteur valide");
						choosedHex.setControlled(true);
						choosedHex.setControlledBy(player);
						ArrayList<Ship> shipsOnHex = new ArrayList<Ship>();
						//shipsOnHex.add(player.getShips().get(0)); //supprimer ????
						//shipsOnHex.add(player.getShips().get(1)); //supprimer ????
						shipsOnHex.add(player.getShips().get(2));
						shipsOnHex.add(player.getShips().get(3));
						choosedHex.setShipsOnHex(shipsOnHex);
						player.getControlledHexs().add(choosedHex);
						break;
					}else {
						System.out.println("Secteur déjà occupé: veuillez en choisir un autre.");
					}
				}else{
					System.out.println(player.getName()+" est entrain de choisir un secteur à habiter...");
					int x=(int)(Math.random()*9);
					int y = (int)(Math.random()*5);
					Hex choosedHex=this.gameBoard.gameBoard.get(x).get(y);
					while(!this.gameBoard.verifyCapability(choosedHex)) {
						x=(int)(Math.random()*9);
						y = (int)(Math.random()*5);
						choosedHex=this.gameBoard.gameBoard.get(x).get(y);
					}
					System.out.println("Secteur valide");
					choosedHex.setControlled(true);
					choosedHex.setControlledBy(player);
					ArrayList<Ship> shipsOnHex = new ArrayList<Ship>();
					shipsOnHex.add(player.getShips().get(2));
					shipsOnHex.add(player.getShips().get(3));
					choosedHex.setShipsOnHex(shipsOnHex);
					player.getControlledHexs().add(choosedHex);
					System.out.println(player.getName()+" a choisi son hex!");
					break;
				}
    		}
    	}
    	System.out.println("Fin de l'initialisation de la partie");
	}
    
    /**
     * La methode perform permet aux joueurs de réaliser les commandes Expand, Explore et Exterminate
     */
    public void perform(Board board) {
    	Scanner scanner = new Scanner(System.in);
    	for(int i=1;i<4;i++) {

    		//Afficher la commande numero i de chaque joueur
			System.out.println("--------------------------------------------------");
    		System.out.println("Voici les commandes numero "+i+" de chaque joueur!");
    		for(int j=0; j<players.size();j++) {
    			Player player=players.get(j);
    			int command =player.getCommands().get(i-1);
				System.out.print(player.getName() + ": ");
				if(command==1) {
    				System.out.println("Expand");
    			}else if(command==2) {
    				System.out.println("Explore");
    			}else if(command==3) {
    				System.out.println("Exterminate");
    			}
    		}

    		//Determiner l'effectiveness pour chaque commande
    		int effectivenessExpand=4, effectivenessExplore=4, effectivenessExterminate=4;
			int calcEffExpand=0, calcEffExplore=0, calcEffExterminate=0;
    		for(int k=0;k<players.size();k++) {
    			if(players.get(k).getCommands().get(i-1)==1) {
    				calcEffExpand++;
    			}
    			if(players.get(k).getCommands().get(i-1)==2) {
    				calcEffExplore++;
    			}
    			if(players.get(k).getCommands().get(i-1)==3) {
    				calcEffExterminate++;
    			}
    		}
			if(calcEffExpand!=0){
				effectivenessExpand-=calcEffExpand;
				System.out.println("\nExpand effectiveness: "+effectivenessExpand);
			}
			if(calcEffExplore!=0){
				effectivenessExplore-=calcEffExplore;
				System.out.println("\nExplore effectiveness: "+effectivenessExplore);
			}
			if(calcEffExterminate!=0){
				effectivenessExterminate-=calcEffExterminate;
				System.out.println("\nExterminate effectiveness: "+effectivenessExterminate);
			}
    		
    		//executer les commandes dans l'ordre suivant: Expand puis Explore puis Exterminate
			int[] commandOrder = {1, 2, 3};
			for(int command:commandOrder) {
				if(command==1) {
					System.out.println("\n----------Expand----------");
				} else if (command==2) {
					System.out.println("\n----------Explore----------");
				}else{
					System.out.println("\n----------Exterminate----------");
				}
				for(int j=0; j<players.size();j++) {
					Player player=players.get(j); //ok si la liste des joueurs est organisee de sorte à ce que le premier est le joueur principal
					int choosedCommand=player.getCommands().get(i-1);
					if(choosedCommand==command){
						if(!player.isVirtual()){
							System.out.println(player + ", souhaitez-vous performer cette commande? Répondez par 1 pour oui et 0 pour non"); //donne la possibilite au joueur d'effectuer sa commande ou non
							int response=scanner.nextInt();
							while((response!=1)&&(response!=0)) {
								System.out.println("Reponse non valide.");
								System.out.println(player + ", souhaitez-vous performer cette commande? Répondez par 1 pour oui et 0 pour non");
								response=scanner.nextInt();
							}
							if(response==1) {
								if(command==1) {
									player.Expand(effectivenessExpand);
								}else if(command==2) {
									player.Explore(effectivenessExplore);
								}else{
									player.Exterminate(effectivenessExterminate,board);
								}
							}else {
								System.out.println("Votre choix a bien été pris en compte. Vous n'effectuerez pas cette commande.");
							}
						}else{
							if(command==1) {
								player.Expand(effectivenessExpand);
							}else if(command==2) {
								player.Explore(effectivenessExplore);
							}else{
								player.Exterminate(effectivenessExterminate,board);
							}
						}
					}
				}
			}
    	}
    }

    /**
     * La methode endOfRound permet d'echanger les places du premier et du dernier joueur de la liste, afin que le dernier joueur de la liste devienne le Start Player
     */
   public void endOfRound() {
	   Player startPlayer = this.players.getFirst();
	   Player newStartPlayer=this.players.getLast();
	   this.players.removeFirst();
	   this.players.removeLast();
	   this.players.addFirst(newStartPlayer);
	   this.players.addLast(startPlayer);
   }
        	
        		
	/**
     * Fonction caractéristique d'un tour qui permet à chaque joueur à tour de rôle de choisir l'ordre des commandes,
     * puis de les exécuter. A la fin du tour, la fonction compte les scores de chaque joueur pour ce tour,
     * puis met à jour le score global du joueur.
	 *
     */
    public void Tour(Board board, Scanner sc) {
		this.tour+=1;
    	System.out.println("Tour " + tour);
    	ArrayList <SectorCard> chosenSectors=null;
		//Les joueurs choisissent dans quel ordre ils souhaitent effectuer leurs commandes
    	for (Player player : players) {
    		player.plan();
		}
		//Les actions de chaque joueur sont realisées
		this.perform(board);

		//Choix de secteurs pour le calcul des scores
    	for (Player player : players) {
    		SectorCard actualSector = player.chooseSector(gameBoard, sc);
    		chosenSectors.add(actualSector);
			// TODO
			// Sera ici :
			this.sustainShips(sc);
			// Si c'est pas le dernier tour :
			if(tour<9){
				this.calcScore(sc, 1);
			}else{
				this.calcScore(sc, 2);
			}
		}
		//Changement de start player
		this.endOfRound();
	}

	private void sustainShips(Scanner sc) {
		for (List<Hex> rangee: this.gameBoard.gameBoard) {
			for (Hex hexActuel: rangee) {
				if (hexActuel.isControlled()){ // peut être enlevé
					while (hexActuel.getNbMaxShips()< hexActuel.getShipsOnHex().size()) {
						Ship shipSupp = hexActuel.getShipsOnHex().removeLast();
						shipSupp.setPosition(-1,-1);
						System.out.println("Un ship supprimé");
					}
				}
			}
		}
	}

	public void calcScore(Scanner sc, int scoring) { // est-ce que ça renvoie qqc
		ArrayList <SectorCard> chosenSectors=new ArrayList <SectorCard>();
		SectorCard actualSector;
		boolean plusDeSecteurs=false;
		for (Player player : this.players) {
			while (true) {
				actualSector = player.chooseSector(this.gameBoard, sc);
				// vérification qu'il reste des secteurs disponibles
				//TODO pas sûre que ça marche mais vient d'etre
				plusDeSecteurs=true;
				for (SectorCard[] rangee: this.gameBoard.getBoard()) {
					for (SectorCard card : rangee) {
						if (!chosenSectors.contains(card) && !card.isEmpty()) {
							plusDeSecteurs=false;
						}
					}
				}
				if (chosenSectors.contains(actualSector)) {
					System.out.println("Ce secteur a déjà été choisi, veuillez recommencer");
				}else if (actualSector.isEmpty()) {
					System.out.println("Ce secteur est vide, veuillez recommencer");
				}else if (actualSector.getIsTriPrime()) {
					System.out.println("Ce secteur est le TriPrime, veuillez recommencer");
				}else if(plusDeSecteurs){
					System.out.println("Plus de secteurs disponibles.");
					break;
				}else{
					break;
				}
			}
			chosenSectors.add(actualSector);
			this.scores[player.getId()]=actualSector.calculateScore(player, scoring);
		}
		//TODO
		// si c'est pas le dernier tour
		if(tour<9){
			for (Player player : this.players) {
				if (player.isControllsTriPrime()) {
					while (true) {
						actualSector = player.chooseSector(this.gameBoard, sc);
						if (!chosenSectors.contains(actualSector)) {
							break;
						}
					}
					chosenSectors.add(actualSector);
					this.scores[player.getId()]=actualSector.calculateScore(player, scoring);
				}
			}
		}
		System.out.println("Fin du tour ! \n Les scores sont : ");
		for (int i=0; i<3; i++) {
			System.out.println("Joueur : "+ this.players.get(i).getName() + " ---- Score : " + scores[i]);
		}
	}
    
    /**
     * Cette fonction permet d'arrêter la partie. Pour rappel, la partie s'arrête soit au bout de neuf tours, soit lorsqu'un
     * joueur est éliminé
     * @return false si ce n'est pas encore la fin de la partie, true si c'est la fin de la partie
     */
    private boolean finPartie(int tour) {
		boolean isEliminated = players.stream().anyMatch(Player::isEliminated);
		return isEliminated || tour > 9;
	}

    
    //Determiner Gagnant
    private void declareWinner() {
		int maxScore=0;
		int minScore=1000;
		int idGagnant=0;
		int idSecond = 0;
		int idDernier=0;
		for(int i=0; i<3; i++){
			if(this.scores[i]>maxScore) {
				maxScore=this.scores[i];
				idGagnant=i;
			}
			if(this.scores[i]<minScore) {
				minScore=this.scores[i];
				idDernier=i;
			}
		}
		//TODO changer c'est hyper moche

		if(idGagnant==0 && idDernier==1) {
			idSecond = 2;
		}else if(idGagnant==1 && idDernier==2) {
			idSecond = 0;
		}else if(idGagnant==1 && idDernier==0) {
			idSecond = 2;
		}else if(idGagnant==2 && idDernier==1) {
			idSecond = 0;
		}else if(idGagnant==0 && idDernier==2) {
			idSecond = 1;
		}else if(idGagnant==2 && idDernier==0) {
			idSecond = 1;
		}
		System.out.println("Fin du jeu ! \n Le gagnant est : "+ this.players.get(idGagnant).getName());
		System.out.println("Le podium des scores est :\n---- 1er : " + this.players.get(idGagnant).getName() + " Avec un score de " + scores[idGagnant]);
		System.out.println(" ---- 2er : " + this.players.get(idSecond).getName() + " Avec un score de " + scores[idSecond]);
		System.out.println(" ----  3eme : " + this.players.get(idDernier).getName() + " Avec un score de " + scores[idDernier]);

	}

	public static void main(String[] args) {
		System.out.println("**********Bienvenue dans Pocket Imperium!**********"); //message de bienvenue
		Board board = new Board(); //Création du plateau
		Scanner scanner=new Scanner(System.in); //Création du scanner
		Partie partie=new Partie(scanner); //Création de la partie --> les joueurs s'enregistrent + initialisation
		//partie.startGame(scanner);//début de la partie --> initialisation
		//Jeu
		for(int tour=1;tour<9;tour++) {
			boolean finPartie = partie.finPartie(tour);
			while(!finPartie) {
				partie.Tour(board, scanner);
			}
		}
		//On détermine le gagnant
		partie.declareWinner();
	}
    
}
