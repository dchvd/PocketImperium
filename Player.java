package pocket_imperium;

import java.util.List;

public class Player {
    private String name;
    private GameStrategy strategy;
    private List<Ship> ships; 
    private List<Command> commands; 
    private boolean isVirtual;
    private boolean controllsTriPrime;
    
    //ajouter une liste des Hex controllés

    public Player(String name, boolean isVirtual, GameStrategy strategy) {
        this.name = name;
        this.isVirtual = isVirtual;
        this.strategy = strategy;
        this.commands = new ArrayList<>();
        this.ships = new ArrayList<>();
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



	public void setShips(List<Ship> ships) {
		this.ships = ships;
	}



	public List<Command> getCommands() {
		return commands;
	}



	public void setCommands(List<Command> commands) {
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
			System.out.println("Choisissez un hex où vous souhatez ajouter un vaisseau");
			Hex choosedHex=new Hex(); //récuperer depuis la console
			boolean hexOccupiedByThisPlayer=Helper.TestOccupationPlayerHex(choosedHex, this);
			while(hexOccupiedByThisPlayer==false) {
				choosedHex=null;
				System.out.println("Vous n'occupez pas le hex choisi. Veuillez choisir un hex que vous occupez.");
				choosedHex=new Hex(); //récuperer depuis la console
				hexOccupiedByThisPlayer=Helper.TestOccupationPlayerHex(choosedHex, this);
			}
			int nbShipsTotal=length(choosedHex.getShipsOnHex())+1;
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
    			int nbShipsOnHex=hexDeparture.getShipsOnHex().length; //t'as compris l'idée
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
    	for(int i=0;i<effectivness;i++) {
    		System.out.println("Choisissez à partir de quel système que vous controllez vous souhaitez envahir.");
    		Hex systemToInvadeFrom=new Hex(); //résultat à récuperer à partir de la console
    		System.out.println("Choisissez un système voisin à envahir.");
    		Hex systemToInvade=new Hex(); //résultat à récupere à partir de la console
    		System.out.println("Choisissez le nombre de vaisseaux que vous souhaitez utiliser dans l'attaque.");
    		int nbShipsAttacker; //résultat à récupérer à partir de la console
    		int nbShipsDefendant=systemToInvade.getShipsOnHex().length; //je sais toujours pas faire les listes
    		//Construit l'action
    		Exterminate exterminate=new Exterminate(systemToInvade, systemToInvadeFrom, nbShipsAttacker, nbShipsDefendant, this);
    		//Détermine le nombre de bateaux restants
    		int nbShipsAttackerLeft=Exterminate.RemoveShipsAttacker(nbShipsAttacker, nbShipsDefendant);
    		int nbShipsDefendantLeft=Exterminate.RemoveShipsDefendant(nbShipsAttackerLeft, nbShipsDefendant);
    		//Détermine le gagnant
    		if(nbShipsAttackerLeft>nbShipsDefendantLeft) {
    			Helper.GainControllHex(systemToInvade, nbShipsAttackerLeft,this);
    		}else if(nbShipsAttackerLeft<nbShipsDefendantLeft) {
    			systemToInvade.setShipsOnHex(nbShipsDefendantLeft); 
    		}
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


