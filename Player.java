package pocket_imperium;

import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;

public class Player {
    private String name;
    private GameStrategy strategy;
    private Color couleur;
    private ArrayList<Ship> ships; 
    private ArrayList<Command> commands; 
    private boolean isVirtual;
    private boolean controllsTriPrime;
    
    //ajouter une liste des Hex controllés

    public Player(String name, boolean isVirtual, GameStrategy strategy, Color couleur) {
        this.name = name;
        this.isVirtual = isVirtual;
        this.strategy = strategy;
        this.commands = new ArrayList<>();
        this.ships = new ArrayList<>();
        this.couleur=couleur;
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



	public List<Command> getCommands() {
		return commands;
	}



	public void setCommands(ArrayList<Command> commands) {
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
    	for (int i;i<effectivness+1;i++) {
			System.out.println("Choisissez un hex où vous souhaitez ajouter un vaisseau");
			Hex choosedHex=new Hex(); //récuperer depuis la console
			boolean hexOccupiedByThisPlayer=Helper.TestOccupationPlayerHex(choosedHex, this);
			while(hexOccupiedByThisPlayer==false) {
				choosedHex=null;
				System.out.println("Vous n'occupez pas le hex choisi. Veuillez choisir un hex que vous occupez.");
				choosedHex=new Hex(); //récuperer depuis la console
				hexOccupiedByThisPlayer=Helper.TestOccupationPlayerHex(choosedHex, this);
			}
			int nbShipsTotal=choosedHex.getShipsOnHex().size() +1;
			if(nbShipsTotal>choosedHex.getNbMaxShips()) {
				System.out.println("Vous ne pouvez plus ajouter de vaiseaux sur cet hex car il est plein. Choisissez un autre hex.");
			}else {
				//append choosedHex.getShipsOnHex() avec choosedHex
				System.out.println("Votre vaiseau a bien été ajouté!");
			}
		}
	}

    /**
     * La fonction Explore permet de deplacer les flottes de navires du joueur de hex à hex.
     * Elle verifie egalement que le joueur deplace sa flotte sur un hex voisin, et pas sur un hex trop eloigne. 
     * @param effectivness determine le nombre de flottes que le joueur peut bouger. Elle est definie en fonction du nombre de joueurs ayant choisi la meme commande
     */
    //illisible - mettre des méthodes dans la classe HelperExplore
    public void Explore(int effectivness) {
    	for(int i;i<effectivness+1;i++) {
    		boolean response=true;
    		int nbMovement=0; 
    		System.out.println("Choisissez un hex d'où vous voulez partir");
    		//Afficher la liste des hex controllés par le joueur
			Hex hexDeparture=new Hex(); //prend la valeur indiqué par le joueur dans la console
			//Checker si le joueur a bien choisi un hex qu'il controle
			boolean hexOccupiedByThisPlayer=Helper.TestOccupationPlayerHex(hexDeparture, this);
			while(hexOccupiedByThisPlayer==false) {
				System.out.println("Vous n'occupez pas le hex choisi. Veuillez choisir un hex que vous occupez.");
				hexDeparture=new Hex(); //prend la valeur indiqué par le joueur dans la console
				hexOccupiedByThisPlayer=Helper.TestOccupationPlayerHex(hexDeparture, this);
			}
    		while(response==true) { 
    			System.out.println("Choisissez le hex où vous souhaitez aller");
    			Hex hexDestination=new Hex(); //prend la valeur indiquée dans la console
    			boolean hexOccupied=Helper.TestOccupationHex(hexDestination);
    			while(hexOccupied==true) {
    				System.out.println("Le hex que vous avez choisi est occupé par un autre joueur. Choisissez un autre hex.");
    				hexDestination=new Hex(); //prend la valeur indiquee dans la console
        			hexOccupied=Helper.TestOccupationHex(hexDestination);
    			}
    			int nbShipsOnHex=hexDeparture.getShipsOnHex().size(); 
    			//Faire un String toString pour renvoyer le nb de vaisseaux disponibles sur cet Hex et que le joueur peut bouger
    			System.out.println("Choisissez le nombre de vaisseaux que vous souhaitez déplacer");
    			int nbShipsToMove; //prend la valeur indiqué par le joueur dans la console
    			if (nbShipsToMove>nbShipsOnHex) {
    				System.out.println("Impossible de prendre plus de vaisseaux qu'il n'y en a. Choisissez un nombre plus petit.");
    			}else if(nbShipsToMove<0) {
    				System.out.println("Impossible de ne prendre aucun vaisseau. Choisissez un chiffre supérieur à 0.");
    			}else {
    				int xHexDeparture=hexDeparture.getxPosition();
    				int yHexDeparture=hexDeparture.getyPosition();
    				int xHexDestination=hexDestination.getxPosition();
    				int yHexDestination=hexDestination.getyPosition();
    				boolean hexIsNeighbour=Helper.CheckNeighboursHex(xHexDeparture,yHexDeparture,xHexDestination,yHexDestination);
    				if(hexIsNeighbour==false) {
    					System.out.println("Le hex de destination choisi est trop éloigné. Veuillez choisir un hex voisin");
    				}else {
    					//Bouge la fleet du joueur à l'hex de destination
    					hexDeparture=hexDestination;
    				}
    			}
    			nbMovement+=1;
    			if((nbMovement<2)&&(hexDeparture.isTriPrime()!=true)) {
    				System.out.println("Souhaitez-vous bouger de nouveau votre flotte ?");
        			if(non) {
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
    	ArrayList systemsToInvadeFrom= new ArrayList<Hex>();
    	int nbShipsAttacker;
    	boolean response=true;
    	for(int i=0;i<effectivness;i++) {
    		//L'attaquant choisit l'ensemble des hexs à partir desquels il veut attaquer ainsi que le nombre de vaisseau qu'il veut utiliser pour l'attaque
    		while(response) {
    			System.out.println("Choisissez à partir de quel système que vous controllez vous souhaitez envahir.");
    			Hex systemToInvadeFrom=new Hex();//résultat à récuperer à partir de la console
    			systemsToInvadeFrom.add(systemToInvadeFrom); 
    			System.out.println("Choisissez le nombre de vaisseaux que vous souhaitez utiliser dans l'attaque.");
    			nbShipsAttacker=3; //résultat à récupérer à partir de la console
    			while(nbShipsAttacker>systemToInvadeFrom.getShipsOnHex().size()) {
    				System.out.println("Vous n'avez pas suffisamment de vaisseaux sur cet hex. Choisissez un nombre plus petit.");
    				nbShipsAttacker=2; //résultat à récuperer depuis la console
    			}
    			System.out.println("Souhaitez vous ajouter d'autres systèmes à partir desquels attaquer ?");
    			response=false; //récuperer depuis la console
    		}
    		//Choix du système à envahir
    		System.out.println("Choisissez un système voisin à envahir.");
    		Hex systemToInvade=new Hex(); //résultat à récupere à partir de la console
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
    
    public boolean isWinner() {
    	
    }
    
    
}


