package pocket_imperium;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Player {
	private String name;
	private GameStrategy strategy;
	private Color couleur;
	private ArrayList<Ship> ships=new ArrayList<Ship>();
	private ArrayList<Integer> commands=new ArrayList<Integer>();
	private boolean isVirtual;
	private boolean controllsTriPrime;
	private ArrayList<Hex> controlledHexs=new ArrayList<Hex>();


	public ArrayList<Hex> getControlledHexs() {
		return controlledHexs;
	}

	public void setControlledHexs(ArrayList<Hex> controlledHexs) {
		this.controlledHexs = controlledHexs;
	}

	public Player(Scanner sc) {
		// Demande si le joueur est virtuel
		while (true) {
			System.out.println("Le joueur est-il humain? Entrez oui ou non : ");
			String estVirtuel = sc.nextLine();
			if (estVirtuel.equals("oui")) {
				System.out.print("    Entrez votre surnom : ");
				this.name = sc.nextLine();
				System.out.print("    Entrez la couleur que vous souhaitez (j pour jaune...) : "); // finir
				//char col = sc.nextChar(); // Voir comment faire ça (plus simple de faire avec des entrées de chiffres?)
				// Rajouter des try catch partout du coup
				//this.couleur=couleur;
				System.out.println("\n Bienvenue " + this.name);
				break;
			}else if (estVirtuel.equals("non")) {
				this.isVirtual=true;
				System.out.println("    Entrez le surnom du joueur virtuel : ");
				this.name = sc.nextLine();
				System.out.println(" Assignation d'une stratégie secrète au joueur virtuel...");
				Random randomNumbers = new Random();
				this.strategy = GameStrategy.values()[randomNumbers.nextInt(GameStrategy.values().length)];
				System.out.println("Stratégie attribuée au joueur virtuel : " + this.strategy);
				break;
			}else {
				System.out.println("Mauvaise saisie, veuillez recommencer");
			}
		}
		//Génération de vaisseaux
		this.ships = new ArrayList<>();
		for (int i=0;i<15; i++) {
			ships.add(new Ship(this, 1)); // Remplacer 1 par this.id
		}
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
		this.commands.clear();
		//Cas où le joueur est humain
		if(!this.isVirtual){
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
			//Cas où le joueur est virtuel
		}else{
			ArrayList<Integer> remainingCommands = new ArrayList<>();
			remainingCommands.add(1); // Expand
			remainingCommands.add(2); // Explore
			remainingCommands.add(3); // Exterminate
			Random random = new Random();

			if(this.strategy==GameStrategy.AGGRESSIVE){
				this.commands.add(3);
				remainingCommands.remove(Integer.valueOf(3));
				while (!remainingCommands.isEmpty()) {
					int randomIndex = random.nextInt(remainingCommands.size());
					this.commands.add(remainingCommands.remove(randomIndex));
				}
			}else if(this.strategy==GameStrategy.EXPENDER){
				this.commands.add(1);
				remainingCommands.remove(Integer.valueOf(1));
				while (!remainingCommands.isEmpty()) {
					int randomIndex = random.nextInt(remainingCommands.size());
					this.commands.add(remainingCommands.remove(randomIndex));
				}
			}else{
				this.commands.add(2);
				remainingCommands.remove(Integer.valueOf(2));
				while (!remainingCommands.isEmpty()) {
					int randomIndex = random.nextInt(remainingCommands.size());
					this.commands.add(remainingCommands.remove(randomIndex));
				}
			}
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

	/**
	 * La fonction Expand permet au joueur d'ajouter au moins un de ses vaisseaux sur au moins un hex qu'il contrôle.
	 * @param effectivness détermine le nombre de vaisseaux que le joueur peut ajouter. Elle est définie en fonction du nombre de joueurs ayant choisi la même commande
	 */
	public void Expand(int effectivness) {
		System.out.println("----------EXPAND---------- \n");
		Scanner scanner=new Scanner(System.in);
		for (int i=0;i<effectivness;i++) {

			//Définir le hex où le vaisseau doit être ajouté
			System.out.println("Choisissez un hex où vous souhaitez ajouter un vaisseau");
			System.out.println("Entrez le x du hex choisi.");
			int x=scanner.nextInt();
			System.out.println("Entrez le y du hex choisi.");
			int y=scanner.nextInt();
			Hex choosedHex=Board.gameBoard.get(x).get(y);

			//Verifier si le hex choisi ets bien occupé par le joueur ou non
			boolean hexOccupiedByThisPlayer=Helper.TestOccupationPlayerHex(choosedHex, this);
			while(hexOccupiedByThisPlayer==false) {
				choosedHex=null;
				System.out.println("Vous n'occupez pas le hex choisi. Veuillez choisir un hex que vous occupez.");
				System.out.println("Entrez le x du hex choisi.");
				x=scanner.nextInt();
				System.out.println("Entrez le y du hex choisi.");
				y=scanner.nextInt();
				choosedHex=Board.gameBoard.get(x).get(y);
				hexOccupiedByThisPlayer=Helper.TestOccupationPlayerHex(choosedHex, this);
			}

			//Ajouter le vaisseau
			Ship newShip = new Ship(this, (int) Math.random()); //A changer une fois que l'initialisation des vaisseaux sera faite
			choosedHex.getShipsOnHex().add(newShip);
			int nbShipsTotal = choosedHex.getShipsOnHex().size();
			System.out.println("Votre vaisseau a bien été ajouté!");
			System.out.println("Nombre de vaisseaux sur le hex: " + nbShipsTotal);

			//Résumer les informations du joueur
			System.out.println(this.toString());
			System.out.println("Note: si le hex que vous controllez possède plus de vaisseaux que ce qu'il peut accueillir, des vaisseaux seront supprimé à la fin de ce tour.");

		}
	}

	/**
	 * La fonction Explore permet de deplacer les flottes de navires du joueur de hex à hex.
	 * Elle verifie egalement que le joueur deplace sa flotte sur un hex voisin, et pas sur un hex trop eloigne.
	 * @param effectivness determine le nombre de flottes que le joueur peut bouger. Elle est definie en fonction du nombre de joueurs ayant choisi la meme commande
	 */

	public void Explore(int effectivness) {
		System.out.println("----------EXPLORE---------- \n");
		ArrayList<Integer> shipsIds=new ArrayList<Integer>();
		for(int i=0;i<effectivness;i++) {
			System.out.println("i="+i);
			boolean response=true;
			int nbMovement=0;
			Scanner scanner = new Scanner(System.in);
			System.out.println("Choisissez un hex d'où vous voulez partir");
			System.out.println(this.toString());
			System.out.println("Entrez le x du hex choisi.");
			int xDeparture=scanner.nextInt();
			System.out.println("Entrez le y du hex choisi");
			int yDeparture=scanner.nextInt();
			Hex hexDeparture=Board.gameBoard.get(xDeparture).get(yDeparture);

			//Le joueur choisit le hex duquel il veut partir
			boolean hexOccupiedByThisPlayer=Helper.TestOccupationPlayerHex(hexDeparture, this);
			while(!hexOccupiedByThisPlayer) {
				System.out.println("Vous n'occupez pas le hex choisi. Veuillez choisir un hex que vous occupez.");
				System.out.println("Entrez le x du hex choisi.");
				xDeparture=scanner.nextInt();
				System.out.println("Entrez le y du hex choisi");
				yDeparture=scanner.nextInt();
				hexDeparture=Board.gameBoard.get(xDeparture).get(yDeparture);
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
				Hex hexDestination=Board.gameBoard.get(xDestination).get(yDestination);
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
					hexDestination=Board.gameBoard.get(xDestination).get(yDestination);
					hexOccupied=Helper.TestOccupationHex(hexDestination);
				}

				//regler le cas du hex eloigné
				while(!hexIsNeighbour) {
					System.out.println("Le hex que vous avez choisi est trop éloigné. Choisissez un hex voisin.");
					System.out.println("Entrez le x du hex choisi.");
					xDestination=scanner.nextInt();
					System.out.println("Entrez le y du hex choisi");
					yDestination=scanner.nextInt();
					hexDestination=Board.gameBoard.get(xDestination).get(yDestination);
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

				//regler le cas de valeurs aberrantes
				while(nbShipsToMove<=0) {
					System.out.println("Impossible de ne prendre aucun vaisseau. Choisissez un chiffre supérieur à 0.");
					System.out.println("Choisissez le nombre de vaisseaux que vous souhaitez déplacer");
					nbShipsToMove=scanner.nextInt();
				}
				//Ajoute les vaisseaux au hex choisi
				List<Ship> shipsToMove = new ArrayList<>();
				for (int j = 0; j < nbShipsToMove; j++) {
					shipsToMove.add(hexDeparture.getShipsOnHex().remove(0));
				}
				hexDestination.getShipsOnHex().addAll(shipsToMove);
				hexDestination.setControlledBy(this);
				this.controlledHexs.add(hexDestination);
				System.out.println("Vous avez pris contrôle du hex (" + xDestination + ", " + yDestination + ") avec " + nbShipsToMove + " vaisseaux.");

				//Demande au joueur s'il souhaite bouger cette même fleet une seconde fois
				nbMovement+=1;
				//Donner la possibilité de bouger sa flotte une seconde fois
				if((nbMovement<2)&&(!hexDestination.isTriPrime())) {
					String answer="random";
					while (!answer.equals("oui") && !answer.equals("non")) {
						System.out.println("Souhaitez-vous bouger de nouveau votre flotte ? Entrez 'oui' ou 'non'.");
						answer=scanner.nextLine();
					}
					if(answer.equals("non")) {
						nbMovement=0;
						response=false;
						//Enregistre le vaisseau bougé dans la liste des vaisseaux qui ne peuvent plus être réutilisés
					}
				}else {
					nbMovement=0;
					response=false;
				}
			}
			//Résumer les informations du joueur
			System.out.println(this.toString());
		}

	}

	public void Exterminate(int effectivness, Board board) {
		System.out.println("----------EXTERMINATE---------- \n");
		ArrayList<Hex> systemsToInvadeFrom= new ArrayList<Hex>(); //Liste des hexs que le joueur souhaite utiliser pour envahir
		int nbShipsAttacker=0; //nb de vaisseaux du joueur qui attaque
		int nbShipsDefendant=0; //nb de vaisseaux du joueur attaqué
		Scanner scanner = new Scanner(System.in);
		boolean response=true; //permet au joueur de choisir autant de hexs qu'il souhaite pour attaquer
		boolean systemToAttackChoosed=false; //permet de savoir si le joueur a deja choisi le hex a attaquer ou non
		Hex systemToInvade = null; //hex à attaquer
		for(int i=0;i<effectivness;i++) {
			//Afficher les infos du joueur
			System.out.println(this.toString());

			//L'attaquant choisit l'ensemble des hexs à partir desquels il veut attaquer ainsi que le nombre de vaisseaux qu'il veut utiliser pour l'attaque
			while(response) {
				System.out.println("Choisissez à partir de quel système que vous controllez vous souhaitez envahir. \nEntrez le x du systeme choisi. ");
				int xInvadeFrom=scanner.nextInt();
				System.out.println("Entrez le y du systeme choisi");
				int yInvadeFrom=scanner.nextInt();
				Hex systemToInvadeFrom=Board.gameBoard.get(xInvadeFrom).get(yInvadeFrom);
				boolean hexOccupiedByThisPlayer=Helper.TestOccupationPlayerHex(systemToInvadeFrom, this);

				//Gerer le cas où l'attaquant n'as pas choisit un hex qu'il controle
				while(!hexOccupiedByThisPlayer) {
					System.out.println("Vous ne controllez pas ce systeme. Choisissez un système que vous controllez. \nEntrez le x du systeme choisi.");
					xInvadeFrom=scanner.nextInt();
					System.out.println("Entrez le y du systeme choisi");
					yInvadeFrom=scanner.nextInt();
					systemToInvadeFrom=Board.gameBoard.get(xInvadeFrom).get(yInvadeFrom);
					hexOccupiedByThisPlayer=Helper.TestOccupationPlayerHex(systemToInvadeFrom, this);
				}

				//Accrémenter la liste des hexs à partir desquels le joueur souhaite envahir
				systemsToInvadeFrom.add(systemToInvadeFrom);

				//L'attaquant choisit le nombre de vaisseaux qu'il souhaite utiliser pour l'attaque à partir de cet hex là
				System.out.println("Choisissez le nombre de vaisseaux que vous souhaitez utiliser dans l'attaque.");
				nbShipsAttacker=scanner.nextInt();

				//Gérer le cas où l'attaquant n'a pas assez de vaisseaux
				while(nbShipsAttacker>systemToInvadeFrom.getShipsOnHex().size()) {
					System.out.println("Vous n'avez pas suffisamment de vaisseaux sur cet hex. Choisissez un nombre plus petit.");
					nbShipsAttacker=scanner.nextInt();
				}

				//Gérer le cas où le nombre de vaisseaux qu'il souhaite utiliser est un nombre abberant
				while(nbShipsAttacker<=0) {
					System.out.println("Veuillez choisir un nombre supérieur à 0");
					nbShipsAttacker=scanner.nextInt();
				}

				//Choix du système à envahir
				if(!systemToAttackChoosed) {
					//Afficher et enregistrer les systemes voisins
					ArrayList<Hex> systemNeighbours=Helper.findSystemNeighbours(systemToInvadeFrom, board);

					//Choix du systeme à envahir
					System.out.println("Choisissez un système voisin à envahir. \nEntrez le x du hex choisi.");
					int xInvade=scanner.nextInt();
					System.out.println("Entrez le y du hex choisi");
					int yInvade=scanner.nextInt();

					//Definir le hex à envahir
					systemToInvade=Board.gameBoard.get(xInvade).get(yInvade);

					//Verifier que le hex à attaquer est un voisin ET un systeme
					boolean isSystemNeighbour=systemNeighbours.contains(systemToInvade);
					System.out.println(isSystemNeighbour);
					while(!isSystemNeighbour) {
						System.out.println("IMPORTANT: veuillez choisir un hex voisin qui est un système!");
						System.out.println("Entrez le x du hex choisi.");
						xInvade=scanner.nextInt();
						System.out.println("Entrez le y du hex choisi");
						yInvade=scanner.nextInt();
						systemToInvade=Board.gameBoard.get(xInvade).get(yInvade);
						isSystemNeighbour=systemNeighbours.contains(systemToInvade);
					}

					//Verifier que le hex à attaquer n'est pas déjà controllé par le joueur
					Player playerWhoControllsHex=systemToInvade.getControlledBy();
					while(playerWhoControllsHex==this) {
						System.out.println("Vous controllez cet hex. Vous ne pouvez pas envahir un hex que vous controllez déjà. Choisissez un autre hex");
						System.out.println("Entrez le x du hex choisi.");
						xInvade=scanner.nextInt();
						System.out.println("Entrez le y du hex choisi");
						yInvade=scanner.nextInt();
						systemToInvade=Board.gameBoard.get(xInvade).get(yInvade);
						playerWhoControllsHex=systemToInvade.getControlledBy();
					}

					//Definir le nombre de vaisseaux disponibles pour defendre le hex
					nbShipsDefendant=systemToInvade.getShipsOnHex().size();
					systemToAttackChoosed=true;
				}

				//Cas où le systeme à envahir est deja choisi, mais le joueur souhaite attaquer à partir de plusieurs hexs
				else{
					systemsToInvadeFrom.remove(systemToInvadeFrom); //On supprime le hex en attendant qu'il passe les verifications

					//Verifier que le hex choisi est bien occupé par le joueur
					boolean newHexOccupiedByThisPlayer=Helper.TestOccupationPlayerHex(systemToInvadeFrom, this);
					//Verifier que le hex choisi est voisin au systeme à attaquer
					boolean newHexIsNeighbour=Helper.CheckNeighboursHex(xInvadeFrom, yInvadeFrom, systemToInvade.getxPosition(), systemToInvade.getyPosition());
					//Verifier que le hex choisi n'est pas deja présent dans la liste des hexs choisis
					boolean hexAlreadyChoosed = Helper.HexAlreadyChoosed(systemToInvadeFrom, systemsToInvadeFrom);

					//Corriger les erreurs
					while(!newHexOccupiedByThisPlayer||!newHexIsNeighbour||hexAlreadyChoosed) {

						//Corriger quand le hex choisi n'est pas occupé par le joueur
						if(!newHexOccupiedByThisPlayer) {
							System.out.println("Vous n'occupez pas le hex choisi. Choisissez un hex que vous occupez");
							System.out.println("Choisissez à partir de quel système que vous controllez vous souhaitez envahir.");
							System.out.println("Entrez le x du systeme choisi.");
							xInvadeFrom=scanner.nextInt();
							System.out.println("Entrez le y du systeme choisi");
							yInvadeFrom=scanner.nextInt();
							systemToInvadeFrom=Board.gameBoard.get(xInvadeFrom).get(yInvadeFrom);
						}

						//Corriger quand le hex choisi n'est pas un voisin du systeme à attaquer
						else if(!newHexIsNeighbour) {
							System.out.println("Le hex choisi n'est pas un voisin du hex que vous attaquez.");
							System.out.println("Si vous n'avez pas d'autres hexs occupés voisins au système que vous attaquez, écrivez 'stop', sinon ecrivez 'continuer'");

							String stop = scanner.nextLine(); // Lit la ligne complète
							if (stop.equals("stop")) {
								newHexIsNeighbour=true;
								break;
							} else if(stop.equals("continuer")){
								System.out.println("Choisissez à partir de quel système que vous contrôlez vous souhaitez envahir.");
								System.out.println("Entrez le x du système choisi.");
								xInvadeFrom = scanner.nextInt();
								scanner.nextLine(); // Consomme le caractère de nouvelle ligne laissé par nextInt()

								System.out.println("Entrez le y du système choisi.");
								yInvadeFrom = scanner.nextInt();
								scanner.nextLine(); // Consomme le caractère de nouvelle ligne laissé par nextInt()

								systemToInvadeFrom = Board.gameBoard.get(xInvadeFrom).get(yInvadeFrom);
							}

						}

						//Corriger quand le hex choisi a déjà été choisi auparavant
						else if(hexAlreadyChoosed) {
							System.out.println("Cet hex là est déjà choisi pour attaquer le systeme. Choisissez un autre hex");
							System.out.println("Si vous n'avez pas d'autres hexs occupés voisins au système que vous attaquez, écrivez 'stop', sinon ecrivez 'continuer'");

							String stop=scanner.nextLine();
							if(stop.equals("stop")) {
								hexAlreadyChoosed=false;
								break;
							}else if(stop.equals("continuer")) {
								System.out.println("Choisissez à partir de quel système que vous contrôlez vous souhaitez envahir.");
								System.out.println("Entrez le x du système choisi.");
								xInvadeFrom = scanner.nextInt();
								scanner.nextLine(); // Consomme le caractère de nouvelle ligne laissé par nextInt()

								System.out.println("Entrez le y du système choisi.");
								yInvadeFrom = scanner.nextInt();
								scanner.nextLine(); // Consomme le caractère de nouvelle ligne laissé par nextInt()

								systemToInvadeFrom = Board.gameBoard.get(xInvadeFrom).get(yInvadeFrom);
								hexAlreadyChoosed=systemsToInvadeFrom.contains(systemToInvadeFrom);
							}

						}

						newHexOccupiedByThisPlayer=Helper.TestOccupationPlayerHex(systemToInvadeFrom, this);
						newHexIsNeighbour = Helper.CheckNeighboursHex(xInvadeFrom, yInvadeFrom, systemToInvade.getxPosition(), systemToInvade.getyPosition());
						hexAlreadyChoosed=Helper.HexAlreadyChoosed(systemToInvadeFrom, systemsToInvadeFrom);
					}

					//Accrémenter la liste des hexs à partir desquels le joueur souhaite envahir
					systemsToInvadeFrom.add(systemToInvadeFrom);
				}

				//Possibilité de choisir plusieurs hexs à partir desquels attaquer
				System.out.println("Souhaitez vous ajouter d'autres systèmes à partir desquels attaquer ? Entrez 'oui' ou 'non'.");
				String answer=scanner.nextLine();
				while(!answer.equals("oui")&&!answer.equals("non")) {
					System.out.println("Entrez 'oui' ou 'non'.");
					answer=scanner.nextLine();
				}
				if(answer.equals("non")) {
					response=false;
				}
			}

			//Construit l'action
			for (int j=0;j<systemsToInvadeFrom.size();j++) {
				Hex systemToInvadeFrom=systemsToInvadeFrom.get(j);
				//Exterminate exterminate=new Exterminate(systemToInvade, systemToInvadeFrom, nbShipsAttacker, nbShipsDefendant, this); --> Potentiellement inutile ?

				//Bouge les vaisseaux du système à partir duquel on attaque
				Helper.removeShipsFromHex(nbShipsAttacker, systemToInvadeFrom);
			}
			//Détermine le gagnant
			System.out.println(Exterminate.DetermineWinner(nbShipsAttacker, nbShipsDefendant, systemToInvade, this));

			//Résumer les informations du joueur
			System.out.println(this.toString());
		}

	}

	public boolean isEliminated() {
		return ships.isEmpty();
	}


	public String toString() {
		return "État actuel de " + this.name + ":\n"
				+ "- Hexs contrôlés: " + this.controlledHexs + "\n"
				+ "- Contrôle le Tri Prime: " + this.controllsTriPrime;
	}


	//public boolean isWinner() {
}

