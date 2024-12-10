
package pocket_imperium;

import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Random;
import java.util.Arrays;

public class Player {
    private String name;
    private GameStrategy strategy;
    private Color couleur;
    private ArrayList<Ship> ships; 
    private ArrayList<Integer> commands; 
    private boolean isVirtual;
    private boolean controllsTriPrime;
    private ArrayList<Hex> controlledHexs;
    
    
    public Player() {
    	Scanner sc = new Scanner(System.in);
    	
    	// Demande si le joueur est virtuel
    	while (true) {
    		System.out.println("Le joueur est-il humain? Entrez oui ou non : ");
        	String estVirtuel = sc.nextLine();
    		if (estVirtuel.equals("oui")) {
    			System.out.print("    Entrez votre surnom : ");
    	    	this.name = sc.nextLine();
    	       	System.out.println("Bienvenue " + this.name);
    	       	break;
    		}else if (estVirtuel.equals("non")) {
    			// Demander le surnom
    	    	System.out.println("    Entrez le surnom du joueur virtuel : ");
    	    	this.name = sc.nextLine();
    	    	System.out.println(" Assignation d'une stratégie secrète au joueur virtuel...");
    	    	Random randomNumbers = new Random();
    	    	int numStrategie = randomNumbers.nextInt(6);
    	    	//GameStrategy randomStrategy= GameStrategy ;
    	    	//this.strategy = randomStrategy;
    	    	break;
    		}else {
    			System.out.println("Mauvaise saisie, veuillez recommencer");
    		}
    	}        
        this.ships = new ArrayList<>();
  }
    
    public ArrayList<Hex> getControlledHexs() {
		  return controlledHexs;
	}

	public void setControlledHexs(ArrayList<Hex> controlledHexs) {
		this.controlledHexs = controlledHexs;
	}

    
    public SectorCard chooseSector(Board plat) {
    	System.out.print(this.name);
    	System.out.println( ": Choisissez sur quel secteur vous souhaitez gagner des points ");
    	plat.printCards();
    	plat.printBoard();
    	try (Scanner sc = new Scanner(System.in)) {
    		//Recupérer secteur
    		int x = 0;
    		int y = 0;
    		boolean coordXValide = false;
    		boolean coordYValide = false;
    		while (coordXValide) {
    			System.out.print("Vous souhaitez la carte de la ligne (0 à 2) : ");
    			coordXValide = sc.hasNextInt();
    			if (coordXValide==true) {
    				x = sc.nextInt();
    				if (x!=0 ||x!=1 ||x!=2) {
    					coordXValide = false;
    					}
    				}
    			}
    		while (coordYValide) {
    			System.out.print(" et de la colonne (0 à 2) : ");
    			coordYValide = sc.hasNextInt();
    			if (coordYValide==true) {
    				y = sc.nextInt();
    				if (y!=0 ||y!=1 ||y!=2) {
    					coordYValide = false;
    					}
    				}else {
    					System.out.print(" Entrée incorrecte ");
    					}
    			}
    		return plat.getBoard()[x][y]; 
    		}
    	}
    
    /**
     * La methode plan permet au joueur de planifier l'ordre dans lequel il souhaire executer les commandes.
     */
    public void plan() {
    	//commands = null;
    	/**
    	 * RAPPEL POUR NOUS 
    	
    	int commandExpand=1;
    	int commandExplore=2;
    	int commandExterminate=3;
    	*/
    	int command;
    	Scanner scanner = new Scanner(System.in);
    	
    	System.out.println("Planifiez l'ordre dans lequel vous souhaitez effectuer les commandes.");
    	System.out.println("Tapez 1 pour Expand");
    	System.out.println("Tapez 2 pour Explore");
    	System.out.println("Tapez 3 pour Exterminate");
    	for(int i=1;i<4;i++) {
    		System.out.println("Choisissez l'action num "+i+".");
    		command=scanner.nextInt();
    		while(command!=1&&command!=2&&command!=3) {
    			System.out.println("Le nombre entré n'est pas valide.");
    			System.out.println("Tapez 1 pour Expand");
    	    	System.out.println("Tapez 2 pour Explore");
    	    	System.out.println("Tapez 3 pour Exterminate");
    	    	command=scanner.nextInt();
    		}
    		this.commands.add(command);
    	}
    }

    public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public GameStrategy getStrategy() {
		return strategy;
	}



	public void setStrategy(GameStrategy strategy) {
		this.strategy = strategy;
	}



	public List<Ship> getShips() {
		return ships;
	}



	public void setShips(ArrayList<Ship> ships) {
		this.ships = ships;
	}



	public List<Integer> getCommands() {
		return commands;
	}



	public void setCommands(ArrayList<Integer> commands) {
		this.commands = commands;
	}



	public boolean isVirtual() {
		return isVirtual;
	}



	public void setVirtual(boolean isVirtual) {
		this.isVirtual = isVirtual;
	}



	public boolean isControllsTriPrime() {
		return controllsTriPrime;
	}



	public void setControllsTriPrime(boolean controllsTriPrime) {
		this.controllsTriPrime = controllsTriPrime;
	}



	public void planCommands(String command1, String command2, String command3) {
     
    }
    
	/**
	 * La fonction Expand permet au joueur d'ajouter au moins un de ses vaisseaux sur au moins un hex qu'il contrôle.
	 * @param effectivness détermine le nombre de vaisseaux que le joueur peut ajouter. Elle est définie en fonction du nombre de joueurs ayant choisi la même commande
	 */
    public void Expand(int effectivness) {
    	System.out.println("EXPAND");
    	Scanner scanner=new Scanner(System.in);
    	for (int i=0;i<effectivness;i++) {
			System.out.println("Choisissez un hex où vous souhaitez ajouter un vaisseau");
			System.out.println("Entrez le x du hex choisi.");
			int x=scanner.nextInt(); 
			System.out.println("Entrez le y du hex choisi.");
			int y=scanner.nextInt();
			Hex choosedHex=Board.determineHexFromCoordinates(x, y);
			boolean hexOccupiedByThisPlayer=Helper.TestOccupationPlayerHex(choosedHex, this);
			while(hexOccupiedByThisPlayer==false) {
				choosedHex=null;
				System.out.println("Vous n'occupez pas le hex choisi. Veuillez choisir un hex que vous occupez.");
				System.out.println("Entrez le x du hex choisi.");
				x=scanner.nextInt(); 
				System.out.println("Entrez le y du hex choisi.");
				y=scanner.nextInt();
				choosedHex=Board.determineHexFromCoordinates(x, y);
				hexOccupiedByThisPlayer=Helper.TestOccupationPlayerHex(choosedHex, this);
			}
			choosedHex.getShipsOnHex().add(new Ship(this, (int) Math.random()));
			int nbShipsTotal=choosedHex.getShipsOnHex().size();
			System.out.println("Nombre maximal de vaisseaux sur le hex: "+choosedHex.getNbMaxShips());
			System.out.println("Nombre de vaisseaux sur le hex: "+nbShipsTotal);
			while(nbShipsTotal>choosedHex.getNbMaxShips()) {
				System.out.println("Vous ne pouvez plus ajouter de vaiseaux sur cet hex car il est plein. Choisissez un autre hex.");
				choosedHex=null;
				System.out.println("Vous n'occupez pas le hex choisi. Veuillez choisir un hex que vous occupez.");
				System.out.println("Entrez le x du hex choisi.");
				x=scanner.nextInt(); 
				System.out.println("Entrez le y du hex choisi.");
				y=scanner.nextInt();
				choosedHex=Board.determineHexFromCoordinates(x, y);
				hexOccupiedByThisPlayer=Helper.TestOccupationPlayerHex(choosedHex, this);
			}
				//append choosedHex.getShipsOnHex() avec choosedHex
				System.out.println("Votre vaisseau a bien été ajouté!");
		}
	}

    /**
     * La fonction Explore permet de deplacer les flottes de navires du joueur de hex à hex.
     * Elle verifie egalement que le joueur deplace sa flotte sur un hex voisin, et pas sur un hex trop eloigne. 
     * @param effectivness determine le nombre de flottes que le joueur peut bouger. Elle est definie en fonction du nombre de joueurs ayant choisi la meme commande
     */
    public void Explore(int effectivness) {
    	System.out.println("EXPLORE");
    	for(int i=0;i<effectivness;i++) {
    		boolean response=true;
    		int nbMovement=0; 
    		Scanner scanner = new Scanner(System.in);
    		System.out.println("Choisissez un hex d'où vous voulez partir");
    		//Afficher la liste des hex controllés par le joueur
    		System.out.println("Entrez le x du hex choisi.");
    		int xDeparture=scanner.nextInt();
    		System.out.println("Entrez le y du hex choisi");
    		int yDeparture=scanner.nextInt();
			Hex hexDeparture=Board.determineHexFromCoordinates(xDeparture, yDeparture);
			
			//Le joueur choisit le hex duquel il veut partir
			boolean hexOccupiedByThisPlayer=Helper.TestOccupationPlayerHex(hexDeparture, this);
			while(!hexOccupiedByThisPlayer) {
				System.out.println("Vous n'occupez pas le hex choisi. Veuillez choisir un hex que vous occupez.");
				System.out.println("Entrez le x du hex choisi.");
	    		xDeparture=scanner.nextInt();
	    		System.out.println("Entrez le y du hex choisi");
	    		yDeparture=scanner.nextInt();
				hexDeparture=Board.determineHexFromCoordinates(xDeparture, yDeparture);
				hexOccupiedByThisPlayer=Helper.TestOccupationPlayerHex(hexDeparture, this);
			}
			//Le joueur choisit le hex où il veut aller
			//Deux cas d'erreurs possibles: soit le hex est occupé, soit il est trop éloigné
    		while(response) { 
    			System.out.println("Choisissez le hex où vous souhaitez aller");
    			System.out.println("Entrez le x du hex choisi.");
        		int xDestination=scanner.nextInt();
        		System.out.println("Entrez le y du hex choisi");
        		int yDestination=scanner.nextInt();
    			Hex hexDestination=Board.determineHexFromCoordinates(xDestination, yDestination);
    			boolean hexOccupied=Helper.TestOccupationHex(hexDestination); //tester si le hex est occupé
    			boolean hexIsNeighbour=Helper.CheckNeighboursHex(xDeparture,yDeparture,xDestination,yDestination); //tester si le hex est eloigné
    			System.out.println(hexIsNeighbour);
    			//regler le cas du hex occupé
    			while(hexOccupied) {
    				System.out.println("Le hex que vous avez choisi est occupé par un autre joueur. Choisissez un autre hex.");
    				System.out.println("Entrez le x du hex choisi.");
    	    		xDestination=scanner.nextInt();
    	    		System.out.println("Entrez le y du hex choisi");
    	    		yDestination=scanner.nextInt();
    				hexDestination=Board.determineHexFromCoordinates(xDestination, yDestination);
        			hexOccupied=Helper.TestOccupationHex(hexDestination);
    			}
    			
    			//regler le cas du hex eloigné
    			while(!hexIsNeighbour) {
    				System.out.println("Le hex que vous avez choisi est trop éloigné. Choisissez un hex voisin.");
    				System.out.println("Entrez le x du hex choisi.");
    	    		xDestination=scanner.nextInt();
    	    		System.out.println("Entrez le y du hex choisi");
    	    		yDestination=scanner.nextInt();
    				hexDestination=Board.determineHexFromCoordinates(xDestination, yDestination);
    				hexIsNeighbour=Helper.CheckNeighboursHex(xDeparture,yDeparture,xDestination,yDestination);
    			}
    			
    			//Le joueur choisit le nombre de vaisseaux qu'il veut déplacer
    			int nbShipsOnHex=hexDeparture.getShipsOnHex().size(); 
    			//Faire un String toString pour renvoyer le nb de vaisseaux disponibles sur cet Hex et que le joueur peut bouger
    			System.out.println("Choisissez le nombre de vaisseaux que vous souhaitez déplacer");
    			int nbShipsToMove=scanner.nextInt(); 
    			
    			//regler le cas d'un nombre de vaisseau trop grand
    			while (nbShipsToMove>nbShipsOnHex) {
    				System.out.println("Impossible de prendre plus de vaisseaux qu'il n'y en a. Choisissez un nombre plus petit.");
    				System.out.println("Choisissez le nombre de vaisseaux que vous souhaitez déplacer");
        			nbShipsToMove=scanner.nextInt(); 
    			}
    			
    			//regler le cas de valaurs aberrantes
    			while(nbShipsToMove<0) {
    				System.out.println("Impossible de ne prendre aucun vaisseau. Choisissez un chiffre supérieur à 0.");
    				System.out.println("Choisissez le nombre de vaisseaux que vous souhaitez déplacer");
        			nbShipsToMove=scanner.nextInt(); 
    			}
    			
    			//Bouge la fleet du joueur à l'hex de destination
    			hexDeparture=hexDestination;
    			
    			//Demande au joueur si il souhaite bouger cette même fleet un seconde fois
    			nbMovement+=1;
    			if((nbMovement<2)&&(hexDeparture.isTriPrime()!=true)) {
    				System.out.println("Souhaitez-vous bouger de nouveau votre flotte ? Entrez 'oui' ou 'non'.");
    				String answer=scanner.nextLine();
    				while (!answer.equals("oui") && !answer.equals("non")) {
    					System.out.println("Souhaitez-vous bouger de nouveau votre flotte ? Entrez 'oui' ou 'non'.");
        				answer=scanner.nextLine();
    				}
        			if(answer.equals("non")) {
        				nbMovement=0;
        				response=false;
        				hexDeparture.setControlledBy(this); 
        				System.out.println("Vous avez pris contrôle du sytème!");
        			}
    			}else {
    				nbMovement=0;
    				response=false;
    				hexDeparture.setControlledBy(this);
    				System.out.println("Vous avez pris contrôle du système!");
    			}
    		}
    	}
    	
    }
    
    public void Exterminate(int effectivness) {
    	System.out.println("EXTERMINATE");
    	ArrayList systemsToInvadeFrom= new ArrayList<Hex>();
    	int nbShipsAttacker;
    	Scanner scanner = new Scanner(System.in);
    	boolean response=true;
    	for(int i=0;i<effectivness;i++) {
    		//L'attaquant choisit l'ensemble des hexs à partir desquels il veut attaquer ainsi que le nombre de vaisseau qu'il veut utiliser pour l'attaque
    		while(response) {
    			System.out.println("Choisissez à partir de quel système que vous controllez vous souhaitez envahir.");
    			System.out.println("Entrez le x du systeme choisi.");
        		int xInvadeFrom=scanner.nextInt();
        		System.out.println("Entrez le y du systeme choisi");
        		int yInvadeFrom=scanner.nextInt();
    			Hex systemToInvadeFrom=Board.determineHexFromCoordinates(xInvadeFrom, yInvadeFrom);
    			systemsToInvadeFrom.add(systemToInvadeFrom); 
    			System.out.println("Choisissez le nombre de vaisseaux que vous souhaitez utiliser dans l'attaque.");
    			nbShipsAttacker=scanner.nextInt();
    			while(nbShipsAttacker>systemToInvadeFrom.getShipsOnHex().size()) {
    				System.out.println("Vous n'avez pas suffisamment de vaisseaux sur cet hex. Choisissez un nombre plus petit.");
    				nbShipsAttacker=scanner.nextInt();
    			}
    			System.out.println("Souhaitez vous ajouter d'autres systèmes à partir desquels attaquer ? Entrez 'oui' ou 'non'.");
    			String answer=scanner.nextLine();
    			while(answer!="oui"||answer!="non") {
    				System.out.println("Entrez 'oui' ou 'non'.");
    				answer=scanner.nextLine();
    			}
    			if(answer=="non") {
    				response=false;
    			}
    		}
    		//Choix du système à envahir
    		System.out.println("Choisissez un système voisin à envahir.");
    		System.out.println("Entrez le x du hex choisi.");
    		int xInvade=scanner.nextInt();
    		System.out.println("Entrez le y du hex choisi");
    		int yInvade=scanner.nextInt();
			Hex systemToInvade=Board.determineHexFromCoordinates(xInvade, yInvade);
			int nbShipsDefendant=systemToInvade.getShipsOnHex().size();
    		//Construit l'action
    		for (int j=0;j<systemsToInvadeFrom.size();j++) {
    			Hex systemToInvadeFrom=systemsToInvadeFrom.get(j); //j'espere que ça marche pas juste parce que le tableau est vide
    			Exterminate exterminate=new Exterminate(systemToInvade, systemToInvadeFrom, nbShipsAttacker, nbShipsDefendant, this);
    			//Bouge les vaisseaux du système à partir duquel on attaque
        		Helper.removeShipsFromHex(nbShipsAttacker, systemToInvadeFrom);
    		}
    		//Détermine le gagnant
    		System.out.println(Exterminate.DetermineWinner(nbShipsAttacker, nbShipsDefendant, systemToInvade, this));
    	}
    	
    }

    public void executeCommands() {
    	
    }

    public boolean isEliminated() {
    	return ships.isEmpty();
    }
    
    //public boolean isWinner() {
    	
    //}
    
    public static void main(String[] args) {
    	//Generer le plateau
    	Board board = new Board();
    	
    	//Initialiser un  premier joueur 
    	//Hexs controlles
    	Player player1 = new Player("dacha", false);
    	player1.getControlledHexs().add(board.getGameBoard().get(0).get(3));
    	player1.getControlledHexs().add(board.getGameBoard().get(2).get(1));
    	player1.getControlledHexs().get(0).setControlledBy(player1);
    	player1.getControlledHexs().get(1).setControlledBy(player1);
    	//ships
    	Ship ship1 = new Ship(player1, 1);
    	Ship ship2 = new Ship(player1,2);
    	player1.getControlledHexs().get(0).getShipsOnHex().add(ship1);
    	player1.getControlledHexs().get(1).getShipsOnHex().add(ship2);
    	
    	//Initialiser un second joueur
    	//Hexs controlles
    	Player player2=new Player("anaelle",false);
    	player2.getControlledHexs().add(board.getGameBoard().get(1).get(3));
    	player2.getControlledHexs().add(board.getGameBoard().get(1).get(2));
    	player2.getControlledHexs().get(0).setControlledBy(player2);
    	player2.getControlledHexs().get(1).setControlledBy(player2);
    	//ships
    	Ship ship3=new Ship(player2, 3);
    	Ship ship4 = new Ship(player2, 4);
    	player2.getControlledHexs().get(0).getShipsOnHex().add(ship3);
    	player2.getControlledHexs().get(1).getShipsOnHex().add(ship4);
    	
    	//Test des commandes
    	player1.plan();
    	player1.Expand(3);
    	player1.Explore(2);
    	player1.Exterminate(1);
    }

    
}


