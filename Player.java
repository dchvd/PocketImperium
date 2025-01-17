package com.example.demo;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 * Représente un joueur dans le jeu Pocket Imperium.
 * La classe Player gère les informations d'un joueur, ses actions possibles,
 * et l'interaction avec les éléments du jeu tels que les vaisseaux et les hexagones contrôlés.
 * Cette classe peut représenter un joueur humain ou virtuel.
 * Implemente Serializable pour permettre la sérialisation des objets Player.
 *
 * @author Anaelle Melo et Daria Avdeeva
 * @version 1.0
 */
public class Player implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * Nom du joueur
     */
    private String name;
    /**
     * Stratégie choisie pour le joueur virtuel uniquement
     */
    private GameStrategy strategy;
    /**
     * Couleur utilisée par le joeur
     */
    private Couleur couleur;
    /**
     * Liste des vaisseaux appartenant au joueur
     */
    private ArrayList<Ship> ships=new ArrayList<Ship>();
    /**
     * Liste des commandes planifiées par le joueur
     */
    private ArrayList<Integer> commands=new ArrayList<Integer>();
    /**
     * Indique si le joueur est virtuel
     */
    private boolean isVirtual;
    /**
     * Indique si le joueur controle le TriPrime
     */
    private boolean controllsTriPrime;
    /**
     * Liste des Hexs controlles par le joueur
     */
    private ArrayList<Hex> controlledHexs=new ArrayList<Hex>();
    /**
     * id du joueur
     */
    private int id;
    /**
     * Hex initial que le joueur souhaite envahir
     */
    private Hex firstSystemToInvade;

    public int getId() {
        return id;
    }

    public ArrayList<Hex> getControlledHexs() {
        return controlledHexs;
    }

    public void setControlledHexs(ArrayList<Hex> controlledHexs) {
        this.controlledHexs = controlledHexs;
    }

    //Constructeur
    /**
     * Initialise un nouvel objet Player avec les paramètres spécifiés.
     *
     * @param id Identifiant unique du joueur.
     * @param c la couleur choisie par l'utilisateur.
     * @param name nom de l'utilisateur.
     * @param virtual est true si le joueur est virtuel et false sinon
     */
    public Player(int id, Couleur c, String name, boolean virtual) {
        this.id = id;
        this.name = name;
        this.couleur = c;
        this.isVirtual = virtual;

        if (isVirtual) {
                System.out.println("Assignation d'une stratégie secrète au joueur virtuel...");
                Random randomNumbers = new Random();
                this.strategy = GameStrategy.values()[randomNumbers.nextInt(GameStrategy.values().length)];
                this.isVirtual = true;
        }

        // Initialisation des vaisseaux du joueur
        this.ships = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            ships.add(new Ship(this, i)); // avant : 15*this.id + i
        }
    }

    //Methodes

    /**
     * Permet au joueur de choisir le secteur en fin de partie sur lequel il souhaite gagner des points
     * @param plat Plateau utilise
     * @param sc Scanner pour la saisie utilisateur.
     * @return Secteur choisi
     */
    public SectorCard chooseSector(Board plat, Scanner sc) {
        System.out.println(this.name + ": choisissez sur quel vous souhaitez gagner des points ");
        if (!this.isVirtual) {
            plat.printCards();
        }
        Random random = new Random();

        SectorCard chosenCard = null;
        if(!this.isVirtual){
            while (true) {
                try {
                    while(true){

                        System.out.print("Entrez la ligne (0 à 2) : ");
                        int x = sc.nextInt(); //TODO remplacer les nextInt par des nextLine

                        System.out.print("Entrez la colonne (0 à 2) : ");
                        int y = sc.nextInt();
                        chosenCard = plat.getBoard()[x][y];

                        // Vérification des limites du plateau
                        if (x >= 0 && x <= 2 && y >= 0 && y <= 2) {
                            if(Helper.TestOccupationPlayerCard(chosenCard, this)){
                                return chosenCard;
                            }else{
                                System.out.println("Le joueur n'a pas de vaisseaux dans cette carte");
                            }
                        } else {
                            System.out.println("Coordonnées hors limites. Réessayez.");
                        }
                    }
                } catch (Exception e) {
                    System.out.println("Entrée invalide. Réessayez.");
                    sc.nextLine(); // Nettoie le buffer d'entrée
                }
            }
        }else{
            //Définir la carte où le joueur veut gagner des points
            System.out.println("Le joueur virtuel choisit une carte...");
            int x = 0;
            int y = 0;
            //Verifier si le secteur choisi est bien occupé par le joueur ou non
            boolean sectorOccupiedByThisPlayer=false;
            while(!sectorOccupiedByThisPlayer) {
                x = random.nextInt(3);
                y = random.nextInt(3);
                chosenCard = plat.getBoard()[x][y];
                sectorOccupiedByThisPlayer=Helper.TestOccupationPlayerCard(chosenCard, this);
            }

            //Afficher le choix
            System.out.println("Le joueur virtuel a choisi le secteur : x = " + x + " y=" + y + "!");
            return chosenCard;
        }
    }


    /**
     * Permet au joueur de planifier l'ordre des commandes.
     * Les commandes peuvent être de type Expand, Explore ou Exterminate.
     */
    public void plan() {
        this.commands.clear();
        //Cas où le joueur est humain
        if(!this.isVirtual){
            int command = 0;
            Scanner scanner = new Scanner(System.in);
            System.out.print(this.getName()+"-");
            System.out.println("Planifiez l'ordre dans lequel vous souhaitez effectuer les commandes.");
            System.out.println("Tapez 1 pour Expand");
            System.out.println("Tapez 2 pour Explore");
            System.out.println("Tapez 3 pour Exterminate");
            for(int i=1;i<4;i++) {
                System.out.println("Choisissez l'action num " + i + ".");
                boolean entreeValide = false;
                while (!entreeValide) {
                    try {
                        command = scanner.nextInt();
                        if (command == 1 || command == 2 || command == 3) {
                            entreeValide = true;
                        } else {
                            System.out.println("Le nombre entré n'est pas valide.");
                            System.out.println("Tapez 1 pour Expand");
                            System.out.println("Tapez 2 pour Explore");
                            System.out.println("Tapez 3 pour Exterminate");
                        }
                    } catch (Exception e) {
                        System.out.println("Le nombre entré n'est pas valide.");
                        System.out.println("Tapez 1 pour Expand");
                        System.out.println("Tapez 2 pour Explore");
                        System.out.println("Tapez 3 pour Exterminate");
                        scanner.nextLine();
                    }
                }
                this.commands.add(command);
            }
            this.commands.reversed();
            //Cas où le joueur est virtuel
        }else{
            System.out.println(this.getName()+" est entrain de choisir l'ordre de ses commandes...");
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
            this.commands.reversed();
            System.out.println(this.getName()+" a choisi ses commandes!");
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
     * @param effectiveness Nombre de vaisseaux que le joueur peut ajouter, definie en fonction du nombre de joueur qui performent aussi cette commande.
     */
    public void Expand(int effectiveness) {
        Scanner scanner=new Scanner(System.in);
        Random random = new Random();
        for (int i=0;i<effectiveness;i++) {
            if (this.getFirstShipNotPlaced()==null){
                System.out.println("Ce joueur n'a plus de vaisseaux en stock pour le moment, ce tour est sauté.");
                return;
            }
            else if (this.getControlledHexs().isEmpty()) {
                System.out.println("Le joueur " + this.getName() + " n'a plus de Hex controllés ! Son tour est sauté");
                return;
            }
            Hex choosedHex;

            System.out.println(this);
            //Choix du hex où le joueur souhaite poser ses vaisseaux
            if(!this.isVirtual){ //Cas du joueur humain
                //Définir le hex où le vaisseau doit être ajouté
                int x = 0;
                int y = 0;
                while(true){
                    try {
                        System.out.println("Choisissez un hex où vous souhaitez ajouter un vaisseau");
                        System.out.println("Entrez le x du hex choisi.");
                        x = scanner.nextInt();
                        System.out.println("Entrez le y du hex choisi.");
                        y = scanner.nextInt();
                        choosedHex = Board.gameBoard.get(x).get(y);

                        //Verifier si le hex choisi est bien occupé par le joueur ou non
                        boolean hexOccupiedByThisPlayer = Helper.TestOccupationPlayerHex(choosedHex, this);
                        while (hexOccupiedByThisPlayer == false) {
                            System.out.println("Vous n'occupez pas le hex choisi. Veuillez choisir un hex que vous occupez.");
                            System.out.println("Entrez le x du hex choisi.");
                            x = scanner.nextInt();
                            System.out.println("Entrez le y du hex choisi.");
                            y = scanner.nextInt();
                            choosedHex = Board.gameBoard.get(x).get(y);
                            hexOccupiedByThisPlayer = Helper.TestOccupationPlayerHex(choosedHex, this);
                        }
                        break;
                    }catch(Exception e){
                        System.out.println("Problème de lecture");
                    }
                }

                //Ajouter le vaisseau
                Ship newShip = this.ships.getFirst();
                this.ships.removeFirst();
                newShip.setPosition(x,y);//A changer une fois que l'initialisation des vaisseaux sera faite
                choosedHex.getShipsOnHex().add(newShip);
                int nbShipsTotal = choosedHex.getShipsOnHex().size();
                System.out.println("Votre vaisseau a bien été ajouté!");
                System.out.println("Nombre de vaisseaux sur le hex: " + nbShipsTotal);

            }else{ //Cas du joueur virtuel

                //Définir le hex où le vaisseau doit être ajouté
                System.out.println("Le joueur virtuel choisit un système...");
                int x = random.nextInt(9);
                int y=0;
                if (x %2==0){
                    y = random.nextInt(6);
                }else{
                    y = random.nextInt(5);
                }
                choosedHex = Board.gameBoard.get(x).get(y);

                //Verifier si le hex choisi est bien occupé par le joueur ou non
                boolean hexOccupiedByThisPlayer=Helper.TestOccupationPlayerHex(choosedHex, this);
                while(!hexOccupiedByThisPlayer) {
                    x = random.nextInt(9);
                    if (x %2==0){
                        y = random.nextInt(6);
                    }else{
                        y = random.nextInt(5);
                    }
                    choosedHex=Board.gameBoard.get(x).get(y);
                    hexOccupiedByThisPlayer=Helper.TestOccupationPlayerHex(choosedHex, this);
                }

                //Ajouter le vaisseau
                Ship newShip = this.getFirstShipNotPlaced();
                newShip.setPosition(x,y);
                choosedHex.getShipsOnHex().add(newShip);
                int nbShipsTotal = choosedHex.getShipsOnHex().size();
                System.out.println("Le vaisseau de " + this.getName() + " a bien été ajouté!");
                System.out.println("Nombre de vaisseaux sur le hex: " + nbShipsTotal);
            }

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

    /**
     * Permet au joueur de déplacer ses vaisseaux entre des hexagones voisins.
     *
     * @param effectivness Nombre de flottes que le joueur peut déplacer. Elle est definie en fonction du nombre de joueurs ayant choisi la meme commande
     */

    public void Explore(int effectivness) {
        ArrayList<Integer> shipsIds=new ArrayList<Integer>();
        int nbMovement=0;
        Random random = new Random();
        if(this.ships.isEmpty()) {
            System.out.println("Le joueur " + this.getName() + " n'a plus de vaisseau ! Son tour est sauté");
            return;
        } else if (this.getControlledHexs().isEmpty()) {
            System.out.println("Le joueur " + this.getName() + " n'a plus de Hex controllés ! Son tour est sauté");
            return;
        }
        System.out.println(this);

        for(int i=0;i<effectivness;i++) {
            Hex choosedHex;
            if(!this.isVirtual){
                boolean response=true;
                Scanner scanner = new Scanner(System.in);
                System.out.println("Choisissez un hex d'où vous voulez partir");
                System.out.println(this.toString());
                int xDeparture=0;
                int yDeparture=0;
                Hex hexDeparture=null;
                while(true){
                    try{
                        System.out.println("Entrez le x du hex choisi.");
                        xDeparture=scanner.nextInt();
                        System.out.println("Entrez le y du hex choisi");
                        yDeparture=scanner.nextInt();
                        hexDeparture=Board.gameBoard.get(xDeparture).get(yDeparture);

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
                        break;
                    }catch(Exception e){
                        System.out.println("Entrée invalide. Réessayez.");
                        scanner.nextLine(); // Nettoie le buffer d'entrée
                    }
                }
                //Le joueur choisit le hex où il veut aller
                //Deux cas d'erreurs possibles: soit le hex est occupé, soit il est trop éloigné
                while(response) {
                    boolean hexIsNeighbour=false;
                    int xDestination=0;
                    int yDestination=0;
                    try {
                        System.out.println("Choisissez le hex où vous souhaitez aller");
                        System.out.println("Entrez le x du hex choisi.");
                        xDestination = scanner.nextInt();
                        System.out.println("Entrez le y du hex choisi");
                        yDestination = scanner.nextInt();
                        Hex hexDestination = Board.gameBoard.get(xDestination).get(yDestination);
                        xDeparture = hexDeparture.getxPosition();
                        yDeparture = hexDeparture.getyPosition();
                        boolean hexOccupied = Helper.TestOccupationHex(hexDestination); //tester si le hex est occupé
                        hexIsNeighbour = Helper.CheckNeighboursHex(xDeparture, yDeparture, xDestination, yDestination);
                        //regler le cas du hex occupé
                        while (hexOccupied) {
                            System.out.println("Le hex que vous avez choisi est occupé par un autre joueur. Choisissez un autre hex.");
                            System.out.println("Entrez le x du hex choisi.");
                            xDestination = scanner.nextInt();
                            System.out.println("Entrez le y du hex choisi");
                            yDestination = scanner.nextInt();
                            hexDestination = Board.gameBoard.get(xDestination).get(yDestination);
                            hexOccupied = Helper.TestOccupationHex(hexDestination);
                        }



                    //regler le cas du hex eloigné
                    while (!hexIsNeighbour) {
                        System.out.println("Le hex que vous avez choisi est trop éloigné. Choisissez un hex voisin.");
                        System.out.println("Entrez le x du hex choisi.");
                        xDestination = scanner.nextInt();
                        System.out.println("Entrez le y du hex choisi");
                        yDestination = scanner.nextInt();
                        hexDestination = Board.gameBoard.get(xDestination).get(yDestination);
                        hexIsNeighbour = Helper.CheckNeighboursHex(xDeparture, yDeparture, xDestination, yDestination);
                    }

                    //Le joueur choisit le nombre de vaisseaux qu'il veut déplacer
                    int nbShipsOnHex = hexDeparture.getShipsOnHex().size();
                    //Faire un String toString pour renvoyer le nb de vaisseaux disponibles sur cet Hex et que le joueur peut bouger
                    System.out.println("Choisissez le nombre de vaisseaux que vous souhaitez déplacer");
                    int nbShipsToMove = scanner.nextInt();

                    //regler le cas d'un nombre de vaisseaux trop grand
                    while (nbShipsToMove > nbShipsOnHex) {
                        System.out.println("Impossible de prendre plus de vaisseaux qu'il n'y en a. Choisissez un nombre plus petit.");
                        System.out.println("Choisissez le nombre de vaisseaux que vous souhaitez déplacer");
                        nbShipsToMove = scanner.nextInt();
                    }

                    //regler le cas de valeurs aberrantes
                    while (nbShipsToMove <= 0) {
                        System.out.println("Impossible de ne prendre aucun vaisseau. Choisissez un chiffre supérieur à 0.");
                        System.out.println("Choisissez le nombre de vaisseaux que vous souhaitez déplacer");
                        nbShipsToMove = scanner.nextInt();
                    }
                    //Ajoute les vaisseaux au hex choisi
                    //List<Ship> shipsToMove = new ArrayList<>();
                    for (int j = 0; j < nbShipsToMove; j++) {
                        hexDestination.getShipsOnHex().add(hexDeparture.getShipsOnHex().remove(0));
                    }
                    hexDestination.setControlledBy(this);
                    System.out.println("Vous avez pris contrôle du hex (" + xDestination + ", " + yDestination + ") avec " + nbShipsToMove + " vaisseaux.");



                    //Supprimer le hex contrôllé par le joueur s'il a utilisé tous ces vaisseaux
                    if (nbShipsOnHex == nbShipsToMove) {
                        hexDeparture.setControlledBy(null);
                        this.getControlledHexs().remove(hexDeparture);
                    }

                    //Demande au joueur s'il souhaite bouger cette même fleet une seconde fois
                    nbMovement += 1;
                    //Donner la possibilité de bouger sa flotte une seconde fois
                    if ((nbMovement < 2) && (!hexDestination.isTriPrime())) {
                        String answer = "random";
                        while (!answer.equals("oui") && !answer.equals("non")) {
                            System.out.println("Souhaitez-vous bouger de nouveau votre flotte ? Entrez 'oui' ou 'non'.");
                            answer = scanner.nextLine();
                        }
                        if (answer.equals("non")) {
                            nbMovement = 0;
                            response = false;
                            this.getControlledHexs().add(hexDestination);
                        } else {
                            this.getControlledHexs().add(hexDestination);
                            hexDeparture = hexDestination;
                        }
                    } else {
                        if (hexDestination.isTriPrime()) {
                            this.setControllsTriPrime(true);
                        }
                        nbMovement = 0;
                        response = false;
                        this.getControlledHexs().add(hexDestination);
                    }
                    }catch(Exception e){
                        System.out.println("Entrée invalide. Réessayez.");
                        scanner.nextLine(); // Nettoie le buffer d'entrée

                    }
                }
            }else{ // choix du joueur virtuel
                //Choix d'un hex duquel il veut partir au hasard
                System.out.println("Le joueur  "+ this.getName() + " choisit le hex d'où il veut partir...");
                Hex hexDeparture=this.controlledHexs.get(random.nextInt(controlledHexs.size()));
                int xDeparture= hexDeparture.getxPosition();
                int yDeparture= hexDeparture.getyPosition();

                //Définir le hex où le joueur virtuel veut aller
                System.out.println("Le joueur virtuel choisit le hex où il veut aller...");
                int xDestination=random.nextInt(9);
                int yDestination;
                if(xDestination %2==0){
                    yDestination = random.nextInt(6);
                }else{
                    yDestination = random.nextInt(5);
                }
                Hex hexDestination = Board.gameBoard.get(xDestination).get(yDestination);

                boolean hexOccupied=Helper.TestOccupationHex(hexDestination); //tester si le hex est occupé
                boolean hexIsNeighbour=Helper.CheckNeighboursHex(xDeparture,yDeparture,xDestination,yDestination); //tester si le hex est eloigné

                int nbEssais=0;
                while(hexOccupied || !hexIsNeighbour){
                    nbEssais++;
                    xDestination = random.nextInt(9);
                    if(xDestination %2==0){
                        yDestination = random.nextInt(6);
                    }else{
                        yDestination = random.nextInt(5);
                    }

                    hexDestination = Board.gameBoard.get(xDestination).get(yDestination);
                    hexOccupied=Helper.TestOccupationHex(hexDestination);
                    hexIsNeighbour=Helper.CheckNeighboursHex(xDeparture,yDeparture,xDestination,yDestination);
                    if (nbEssais >9*5){
                        System.out.println("Impossible de réaliser l'action Explore, le joueur n'a nul part ou aller depuis ce Hex");
                        return;
                    }
                }

                //Choix du nombre de vaisseaux
                System.out.println("Le joueur choisit combien de vaisseaux il souhaite déplacer...");
                int nbShipsOnHex=hexDeparture.getShipsOnHex().size();
                int nbShipsToMove = (int)(Math.random()*(nbShipsOnHex+1));

                //Ajoute les vaisseaux au hex choisi
                List<Ship> shipsToMove = new ArrayList<>();
                for (int j = 0; j < nbShipsToMove; j++) {
                    shipsToMove.add(hexDeparture.getShipsOnHex().remove(0));
                }
                hexDestination.getShipsOnHex().addAll(shipsToMove);
                hexDestination.setControlledBy(this);

                //Supprimer le hex contrôllé par le joueur s'il a utilisé tous ces vaisseaux
                if(nbShipsOnHex==nbShipsToMove){
                    hexDeparture.setControlledBy(null);
                    this.getControlledHexs().remove(hexDeparture);
                }

                //Gérer le cas où il y a 0 vaisseaux qui ont été déplacés
                if(nbShipsToMove==0){
                    hexDestination.setControlledBy(null);
                }else{
                    this.getControlledHexs().add(hexDestination);
                    System.out.println("Le joueur virtuel a pris contrôle du hex (" + xDestination + ", " + yDestination + ") avec " + nbShipsToMove + " vaisseaux.");
                }
            }
            //Résumer les informations du joueur
            System.out.println(this.toString());
        }

    }

    /**
     * Permet au joueur d'attaquer un hexagone contrôlé par un autre joueur.
     *
     * @param effectivness Détermine le nombre de systèmes pouvant être attaqués. Elle est definie en fonction du nombre de joueurs ayant choisi la meme commande
     * @param board Plateau de jeu contenant les hexagones.
     */
    public void Exterminate(int effectivness, Board board) {
        ArrayList<Hex> systemsToInvadeFrom= new ArrayList<Hex>(); //Liste des hexs que le joueur souhaite utiliser pour envahir
        int nbShipsAttacker=0; //nb de vaisseaux du joueur qui attaque
        int nbShipsDefendant=0; //nb de vaisseaux du joueur attaqué
        Scanner scanner = new Scanner(System.in);
        boolean response=true; //permet au joueur de choisir autant de hexs qu'il souhaite pour attaquer
        boolean systemToAttackChoosed=false; //permet de savoir si le joueur a deja choisi le hex a attaquer ou non
        Hex systemToInvade = null; //hex à attaquer
        boolean isTriPrime; //Vérifier
        Random random = new Random();

        this.firstSystemToInvade=null;
        if(this.ships.isEmpty()) {
            System.out.println("Le joueur " + this.getName() + " n'a plus de vaisseau ! Son tour est sauté");
            return;
        }else if (this.controlledHexs.isEmpty()) {
            System.out.println("Le joueur " + this.getName() + " n'a plus de Hex controllés ! Son tour est sauté");
            return;
        }

        for(int i=0;i<effectivness;i++) {
            //Afficher les infos du joueur
            System.out.println(this);

            if(!this.isVirtual){

                //L'attaquant choisit l'ensemble des hexs à partir desquels il veut attaquer ainsi que le nombre de vaisseaux qu'il veut utiliser pour l'attaque
                boolean continueAttack = true;

                while (continueAttack) {
                    //Choisir un hex contrôlé par l'attaquant
                    System.out.println("Choisissez un système que vous contrôlez pour attaquer.");
                    System.out.print("Entrez le x : ");
                    int xInvadeFrom = scanner.nextInt();
                    System.out.print("Entrez le y : ");
                    int yInvadeFrom = scanner.nextInt();
                    scanner.nextLine(); // Consomme la ligne restante
                    Hex systemToInvadeFrom= null;
                    try{
                        systemToInvadeFrom = Board.gameBoard.get(xInvadeFrom).get(yInvadeFrom);
                    }catch(Exception e){
                        System.out.println("Les coordonnées ne sont pas valides.");
                    }

                    while (!Helper.TestOccupationPlayerHex(systemToInvadeFrom, this)) {
                        if(firstSystemToInvade!=null){
                            if(!Helper.findSystemNeighbours(firstSystemToInvade, board).contains(systemToInvadeFrom)){
                                System.out.println("Ce hex n'est pas voisin de celui que vous allez envahir.");
                            }else{
                                break;
                            }
                        }
                        if(xInvadeFrom==firstSystemToInvade.getxPosition() && yInvadeFrom == firstSystemToInvade.getyPosition()){
                            System.out.println("Vous ne pouvez pas vous attaquer vous-même.");
                        }
                        System.out.println("Vous ne contrôlez pas ce système. Réessayez.");
                        System.out.print("Entrez le x : ");
                        xInvadeFrom = scanner.nextInt();
                        System.out.print("Entrez le y : ");
                        yInvadeFrom = scanner.nextInt();
                        scanner.nextLine();
                        systemToInvadeFrom = Board.gameBoard.get(xInvadeFrom).get(yInvadeFrom);

                    }

                    //Afficher les systèmes voisins attaquables
                    ArrayList<Hex> neighbours = Helper.findSystemNeighbours(systemToInvadeFrom, board);
                    if (firstSystemToInvade == null){
                        System.out.println("Voici les systèmes voisins que vous pouvez attaquer :");
                        for (Hex hex : neighbours) {
                            if(!this.controllsTriPrime){
                                System.out.println("Système (" + hex.getxPosition() + ", " + hex.getyPosition() + ")");
                            }else{
                                if(!hex.isTriPrime()){
                                    System.out.println("Système (" + hex.getxPosition() + ", " + hex.getyPosition() + ")");
                                }
                            }
                        }

                    }

                    //re attaquer :
                    int xInvade=0;
                    int yInvade=0;
                    if (firstSystemToInvade == null) {
                        //Choisir un système cible si on réattaque pas
                        System.out.println("Choisissez un système à attaquer.");
                        System.out.print("Entrez le x : ");
                        xInvade = scanner.nextInt();
                        System.out.print("Entrez le y : ");
                        yInvade = scanner.nextInt();
                        scanner.nextLine();
                        System.out.println("xInvade = "+ xInvade + " et yInvade =" + yInvade);
                        systemToInvade = Board.gameBoard.get(xInvade).get(yInvade);
                        isTriPrime=systemToInvade.isTriPrime();
                        while (!neighbours.contains(systemToInvade) || Helper.TestOccupationPlayerHex(systemToInvade, this)) {
                            System.out.println("Système invalide. Choisissez un voisin non contrôlé par vous.");
                            System.out.print("Entrez le x : ");
                            xInvade = scanner.nextInt();
                            System.out.print("Entrez le y : ");
                            yInvade = scanner.nextInt();
                            scanner.nextLine();
                            systemToInvade = Board.gameBoard.get(xInvade).get(yInvade);
                            isTriPrime=systemToInvade.isTriPrime();
                        }
                    }else{
                        System.out.println("Vous allez attaquer : " + firstSystemToInvade);
                        systemToInvade = firstSystemToInvade;
                        xInvade = systemToInvade.getxPosition();
                        yInvade = systemToInvade.getyPosition();
                        isTriPrime=systemToInvade.isTriPrime();
                    }


                    //Choisir le nombre de vaisseaux pour l'attaque
                    System.out.println("Combien de vaisseaux souhaitez-vous utiliser pour attaquer ?");
                    nbShipsAttacker = scanner.nextInt();
                    scanner.nextLine();
                    while (nbShipsAttacker <= 0 || nbShipsAttacker > systemToInvadeFrom.getShipsOnHex().size()) {
                        System.out.println("Nombre invalide. Choisissez un nombre entre 1 et " + systemToInvadeFrom.getShipsOnHex().size());
                        nbShipsAttacker = scanner.nextInt();
                        scanner.nextLine();
                    }

                    if(nbShipsAttacker==systemToInvadeFrom.getShipsOnHex().size()) { //cas où tous les vaisseaux de l'attaquant sont utilisés
                        this.getControlledHexs().remove(systemToInvadeFrom);
                        systemToInvadeFrom.setControlledBy(null);
                        systemToInvadeFrom.setControlled(false);
                    }

                    //Supprimer les vaisseaux du système à partir duquel on a attaqué
                    for (int index = 0; index < nbShipsAttacker; index++) {
                        systemToInvadeFrom.getShipsOnHex().getFirst().destroyShip();
                        systemToInvadeFrom.getShipsOnHex().removeFirst();
                    }

                    //Déterminer le gagnant
                    if(!isTriPrime){ //cas où l'attaquant attaque un systeme qui n'est pas TriPrime
                        nbShipsDefendant = systemToInvade.getShipsOnHex().size();
                    }else { //cas où l'attaquant attaque le TriPrime
                        nbShipsDefendant = Helper.nbShipsOnTriPrime();
                    }
                    String result = Exterminate.DetermineWinner(nbShipsAttacker, nbShipsDefendant, systemToInvade, systemToInvadeFrom, this);
                    System.out.println(result);

                    if(result.equals("Le gagnant est l'attaquant : il contrôle désormais le système attaqué.")){
                        for(int ship=0;ship<(nbShipsAttacker-nbShipsDefendant);ship++){
                            //Ajouter le(s) vaisseau(x) du gagnant au Hex capturé
                            Ship newShip = this.getFirstShipNotPlaced();
                            assert newShip != null;
                            newShip.setPosition(xInvade,yInvade);
                            systemToInvade.addShipOnHex(newShip);
                        }

                        if(systemToInvade.isTriPrime()){
                            this.setControllsTriPrime(true);
                        }
                    }

                    if(result.equals("Le gagnant est le défendant : il continue de contrôler le système attaqué.") && systemToInvadeFrom.isTriPrime()){
                        this.setControllsTriPrime(false);
                    }

                    //Vérifier les hexs voisins appartenant à l'attaquant
                    ArrayList<Hex> attackerNeighbours = Helper.findNeighboursOwnedByPlayer(systemToInvade, this, board);
                    this.firstSystemToInvade=null;
                    if (!attackerNeighbours.isEmpty()) {
                        if(attackerNeighbours.size()==1 && attackerNeighbours.get(0).getControlledBy().equals(this)){
                            break;
                        }
                        System.out.println("Hexs voisins que vous contrôlez et pouvez utiliser pour attaquer à nouveau :");
                        for (Hex hex : attackerNeighbours) {
                            System.out.println("Hex (" + hex.getxPosition() + ", " + hex.getyPosition() + ")");
                        }

                        //Proposer une nouvelle attaque
                        System.out.println("Souhaitez-vous attaquer à nouveau ce système ? (oui/non)");
                        String answer = scanner.nextLine();
                        if (!answer.equalsIgnoreCase("oui")) {
                            this.firstSystemToInvade=null;
                            continueAttack = false;
                        }else if(answer.equalsIgnoreCase("oui")){
                            this.firstSystemToInvade = systemToInvade;
                            continueAttack = true;
                        }
                    } else {
                        System.out.println("Aucun hex voisin contrôlé pour attaquer à nouveau.");
                        continueAttack = false;
                    }
                }
            }else{ //cas du joueur virtuel
                boolean continueAttack = true;
                while (continueAttack) {
                    continueAttack=false;
                    System.out.println("Le joueur virtuel choisit à partir de quel système il souhaite envahir...");
                    //Choisir un système contrôlé pour attaquer
                    if(controlledHexs.size()==0){
                        System.out.println("Impossible de continuer, plus de vaisseaux");
                        break;
                    }
                    Hex systemToInvadeFrom = this.controlledHexs.get(random.nextInt(controlledHexs.size()));
                    int xToInvadeFrom = systemToInvadeFrom.getxPosition();
                    int yToInvadeFrom = systemToInvadeFrom.getyPosition();
                    System.out.println("Système choisi pour attaquer : (" + xToInvadeFrom + ", " + yToInvadeFrom + ")");

                    //Trouver les systèmes voisins attaquables
                    ArrayList<Hex> systemNeighbours = Helper.findSystemNeighbours(systemToInvadeFrom, board);

                    // Filtrer les voisins pour exclure ceux déjà contrôlés par le joueur
                    ArrayList<Hex> attackableSystems = new ArrayList<>();
                    if(!systemToInvadeFrom.isTriPrime()){
                        for (Hex neighbour : systemNeighbours) {
                            if (!this.controlledHexs.contains(neighbour)) {
                                attackableSystems.add(neighbour);
                            }
                        }
                    }else{
                        for(Hex neighbour:systemNeighbours){
                            if((!this.controlledHexs.contains(neighbour))||(!neighbour.isTriPrime())){
                                attackableSystems.add(neighbour);
                            }
                        }
                    }

                    // Si aucun système n'est attaquable, arrêter
                    if (attackableSystems.isEmpty()) {
                        System.out.println("Aucun système attaquable à partir de (" + xToInvadeFrom + ", " + yToInvadeFrom + ").");
                        return;
                    }

                    //Choisir un système cible parmi les voisins attaquables, et vérifier qu'on réattaque pas un système
                    int xTarget=0;
                    int yTarget=0;
                    if (firstSystemToInvade == null) {
                        systemToInvade = attackableSystems.get(random.nextInt(attackableSystems.size()));
                        xTarget = systemToInvade.getxPosition();
                        yTarget = systemToInvade.getyPosition();
                        System.out.println("Système choisi à attaquer : (" + xTarget + ", " + yTarget + ")");
                    }else{
                        systemToInvade = firstSystemToInvade;
                        xTarget = systemToInvade.getxPosition();
                        yTarget = systemToInvade.getyPosition();
                    }
                    isTriPrime=systemToInvade.isTriPrime();

                    //Déterminer le nombre de vaisseaux à utiliser
                    System.out.println("Le joueur virtuel choisit combien de vaisseaux utiliser pour attaquer...");
                    int nbShipsAvailable = systemToInvadeFrom.getShipsOnHex().size();

                    int nbShipsToAttack = random.nextInt(nbShipsAvailable) + 1; // Au moins 1 vaisseau
                    for (int index = 0; index < nbShipsToAttack; index++) {
                        systemToInvadeFrom.getShipsOnHex().getFirst().destroyShip();
                        systemToInvadeFrom.getShipsOnHex().removeFirst();
                    }
                    if(nbShipsAvailable==nbShipsToAttack){
                        this.controlledHexs.remove(systemToInvadeFrom);
                        systemToInvadeFrom.setControlled(false);
                        systemToInvadeFrom.setControlledBy(null);
                    }
                    System.out.println("Nombre de vaisseaux choisis pour l'attaque : " + nbShipsToAttack);

                    // Nombre de vaisseaux défendant
                    if(!isTriPrime){ //cas où l'attaquant attaque un systeme qui n'est pas TriPrime
                        nbShipsDefendant = systemToInvade.getShipsOnHex().size();
                    }else{ //cas où l'attaquant attaque le TriPrime
                        nbShipsDefendant=Helper.nbShipsOnTriPrime();
                    }

                    // Exécuter l'attaque
                    System.out.println("Le joueur virtuel attaque avec " + nbShipsToAttack + " vaisseaux contre " + nbShipsDefendant + " vaisseaux.");
                    String result = Exterminate.DetermineWinner(nbShipsToAttack, nbShipsDefendant, systemToInvade, systemToInvadeFrom, this);
                    System.out.println(result + "\n");

                    // Gérer le cas où l'attaquant gagne
                    if(result.equals("Le gagnant est l'attaquant : il contrôle désormais le système attaqué.")){
                        for (int ship=0;ship<(nbShipsToAttack-nbShipsDefendant);ship++){
                            //Ajouter le vaisseau
                            Ship newShip = this.getFirstShipNotPlaced();
                            newShip.setPosition(xTarget,yTarget);//A changer une fois que l'initialisation des vaisseaux sera faite
                            systemToInvade.addShipOnHex(newShip);
                        }
                        if(systemToInvade.isTriPrime()){
                            this.setControllsTriPrime(true);
                        }
                    }

                    if (result.equals("Le gagnant est le défendant : il continue de contrôler le système attaqué.") && systemToInvadeFrom.isTriPrime()) {
                        this.setControllsTriPrime(false);
                    }
                    this.firstSystemToInvade=null;
                    if(result.equals("Le gagnant est le défendant : il continue de contrôler le système attaqué.")){
                        //Vérifier les hexs voisins appartenant à l'attaquant
                        ArrayList<Hex> attackerNeighbours = Helper.findNeighboursOwnedByPlayer(systemToInvade, this, board);
                        if (!attackerNeighbours.isEmpty()) {
                            int attaqueOuPas = random.nextInt(3);
                            if(attaqueOuPas==1){
                                System.out.println("Le joueur décide de ré attaquer! ");
                                this.firstSystemToInvade = systemToInvade;
                                continueAttack = true;
                            }else{
                                this.firstSystemToInvade = null;
                                continueAttack = false;
                            }
                        } else {
                            System.out.println("Aucun hex voisin contrôlé pour attaquer à nouveau.");
                            continueAttack = false;
                        }
                    }
                    if(this.controlledHexs.isEmpty()){
                        continueAttack= false;
                    }
                }
            }
        }

    }

    /**
     * Permet de recuperer le premier vaisseau non place sur le plateau
     * @return le vaisseau à placer, sinon null s'il n'en reste plus
     */
    private Ship getFirstShipNotPlaced() {
        for (int i=0; i<this.ships.size(); i++){
            if(!this.ships.get(i).isPlaced()){
                return this.ships.get(i);
            }
        }
        System.out.println(this.ships);
        System.out.println("Pas de ships restants");
        return null;
    }

    /**
     * Vérifie si le joueur est éliminé du jeu.
     *
     * @return true si le joueur n'a plus de vaisseaux, false sinon.
     */
    public boolean isEliminated() {
        return ships.isEmpty();
    }

    /**
     * Renvoie une description textuelle de l'état actuel du joueur.
     *
     * @return Chaîne de caractères décrivant le joueur.
     */
    public String toString() {
        return "État actuel de " + this.name + ":\n"
                + "- Hexs contrôlés: " + this.controlledHexs + "\n"
                + "- Contrôle le Tri Prime: " + this.controllsTriPrime + "\n";
    }
}

