package com.example.demo;

import java.io.Serializable;
import java.util.*;

/**
 * La classe Partie gère le déroulement d'une partie de Pocket Imperium.
 *
 * Cette classe contrôle le flux du jeu, y compris l'initialisation, les tours de jeu,
 * le calcul des scores, et la détermination du gagnant. Elle maintient également
 * l'état du plateau et des joueurs.
 *
 * @author Anaelle Melo, Daria Avdeeva
 * @version 1.0
 */
public class Partie implements Serializable {
    /**
     * Le backup dans lequel est créée cette partie, permet de sauvegarder
     */
    private Backup backup;

    private static final long serialVersionUID = 1L;
    /**
     * La liste des Joueurs permet d'enregistrer les différents joueurs qui vont la partie de Imperium Pocket
     */
    private ArrayList<Player> players = new ArrayList<Player>();
    /**
     * La variable tour permet de compter les tours de la partie, sachant qu'une partie ne peut pas avoir plus de 9 tours.
     */
    private int tour=0;

    /**
     * Tableau des scores
     */
    private int[] scores= {0,0,0};

    //Génère le plateau :
    /**
     * Plateau de cette partie, avec les secteurs et hex
     */
    private Board gameBoard = new Board();

    /**
     * Est true si la partie est en pause et false sinon
     */
    private boolean isPaused = false;

    /**
     * Le constructeur Partie permet de créer une partie du jeu Imperium Pocket
     * @param sc scanner pour récupérer les informations données par le joueur
     * @param backup backup dans lequel est cette partie
     */
    public Partie(Scanner sc, Backup backup) {
        this.backup = backup;
        List<Couleur> availableColors = new ArrayList<>(Arrays.asList(Couleur.JAUNE, Couleur.ROUGE, Couleur.VIOLET));
        System.out.println(" --- Création des joueurs ! --- \n Chaque joueur doit choisir un surnom. \n");

    }

    /**
     * Reprend une partie sauvegardée au tour actuel.
     *
     * @param sc le scanner pour la saisie utilisateur
     */
    public void resumeGame(Scanner sc) {
        System.out.println("Reprise de la partie au tour " + (this.tour + 1));
        System.out.println("État actuel du plateau :");
        this.gameBoard.printBoard();
        System.out.println("------------------------------------ Reprise ! ------------------------------------");

        // Continue the game from the current tour
        for (int i = this.tour; i < 9; i++) {
            this.Tour(this.gameBoard, sc);
            if(this.finPartie(this.tour)){
                break;
            }
        }

        if (this.isPaused == false) {
            this.declareWinner();
            System.out.println("------ Fin de la partie ------");
        }
    }


    /**
     * Démarre une nouvelle partie ou continue une partie existante.
     *
     * @param joueurs la liste des joueurs participant à la partie
     * @param sc le scanner pour la saisie utilisateur
     */
    public void startGame(ArrayList<Player> joueurs, Scanner sc) {
        if (this.tour ==0){
            this.players = joueurs;

            System.out.println("Le plateau est : ");
            this.gameBoard.printBoard();
            System.out.println("------------------------------------ Initialisation ! ------------------------------------");
            this.initialisation(sc);
        }

        for (int i = this.tour; i < 9; i++) {
            this.Tour(this.gameBoard, sc);
            if(this.finPartie(this.tour)){
                break;
            }
        }
        if (!this.isPaused) {
            this.declareWinner();
            System.out.println("------ Fin de la partie ------");
        };
    }

    /**
     * Cette fonction est appelée au début de chaque partie et permet aux joueurs
     * de choisir où poser les QUATRE vaisseaux qu'ils doivent placer au début de chaque partie.
     *
     * @param sc le scanner pour la saisie utilisateur
     */
    private void initialisation(Scanner sc) {
        System.out.println("\n --- Début de la partie ! --- \n Chaque joueur doit positionner deux bateaux sur un système de niveau 1. \n"
                + " Chaque joueur doit être seul sur son secteur : vous ne pouvez pas placer des bateaux sur un secteur déjà occupé");
        for (Player player : this.players) {
            this.gameBoard.printBoard();
            while(true) {
                if(!player.isVirtual()){
                    try{
                        System.out.println(player.getName()+ " : choisissez le système à habiter"
                                + "\n Entrez le x du Hex choisi : ");

                        int x = sc.nextInt();
                        System.out.println("Entrez le y du hex choisi : ");
                        int y=sc.nextInt();
                        if(x>9 || x<0 || y>5 || y<0){
                            System.out.println("Le hex n'est pas valide (x doit être entre 0 et 8 et y entre 0 et 5");
                        }
                        Hex choosedHex=this.gameBoard.gameBoard.get(x).get(y); // pourquoi elle est statique
                        if(this.gameBoard.verifyCapability(choosedHex, player.isVirtual())) {
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
                    }catch (Exception e) {
                        System.out.println("Entrée invalide. Réessayez.");
                        sc.nextLine(); // Nettoie le buffer d'entrée
                    }


                }else{
                    System.out.println(player.getName()+" est entrain de choisir un secteur à habiter...");
                    int x=(int)(Math.random()*9);
                    int y = (int)(Math.random()*5);
                    Hex choosedHex=this.gameBoard.gameBoard.get(x).get(y);
                    while(!this.gameBoard.verifyCapability(choosedHex, player.isVirtual())) {
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
                    try{
                        System.out.print(player.getName()+ " : choisissez le système à habiter"
                                + "\n Entrez le x du Hex choisi ");
                        int x = sc.nextInt();
                        System.out.print("Entrez le y du hex choisi.");
                        int y=sc.nextInt();
                        Hex choosedHex=this.gameBoard.gameBoard.get(x).get(y);
                        if(this.gameBoard.verifyCapability(choosedHex, player.isVirtual())) {
                            System.out.println("Secteur valide");
                            choosedHex.setControlled(true);
                            choosedHex.setControlledBy(player);
                            ArrayList<Ship> shipsOnHex = new ArrayList<Ship>();
                            shipsOnHex.add(player.getShips().get(2));
                            shipsOnHex.add(player.getShips().get(3));
                            choosedHex.setShipsOnHex(shipsOnHex);
                            player.getControlledHexs().add(choosedHex);
                            break;
                        }else {
                            System.out.println("Secteur déjà occupé: veuillez en choisir un autre.");
                        }
                    }catch(Exception e) {
                        System.out.println("Entrée invalide. Réessayez.");
                        sc.nextLine(); // Nettoie le buffer d'entrée
                    }

                }else{
                    System.out.println(player.getName()+" est en train de choisir un secteur à habiter...");
                    int x=(int)(Math.random()*9);
                    int y = (int)(Math.random()*5);
                    Hex choosedHex=this.gameBoard.gameBoard.get(x).get(y);
                    while(!this.gameBoard.verifyCapability(choosedHex, player.isVirtual())) {
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
     * Exécute les commandes choisies par les joueurs pendant leur tour.
     *
     * @param board le plateau de jeu
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
                        this.gameBoard.printBoard();
                        if(!player.isVirtual()){
                            System.out.println(player + ", souhaitez-vous performer cette commande? Répondez par oui ou non"); //donne la possibilite au joueur d'effectuer sa commande ou non
                            String reponse = scanner.nextLine().toLowerCase(); // Convert to lowercase for easier comparison

                            //NOUVEAU CODE
                            while((!reponse.equals("oui") && !reponse.equals("non"))) {
                                System.out.println("Reponse non valide.");
                                System.out.println(player + ", souhaitez-vous performer cette commande? Répondez par 1 pour oui et 0 pour non");
                                reponse = scanner.nextLine().toLowerCase(); // Convert to lowercase for easier comparison

                            }
                            if (reponse.equals("oui")) {
                                if(command==1) {
                                    player.Expand(effectivenessExpand);
                                }else if(command==2) {
                                    player.Explore(effectivenessExplore);
                                }else{
                                    player.Exterminate(effectivenessExterminate,board);
                                }
                            } else {
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
     * Échange les positions du premier et du dernier joueur dans la liste,
     * permettant la rotation du joueur de départ.
     *
     * @param sc le scanner pour la saisie utilisateur
     */
    public void endOfRound(Scanner sc) {
        Player startPlayer = this.players.getFirst();
        Player newStartPlayer=this.players.getLast();
        this.players.removeFirst();
        this.players.removeLast();
        this.players.addFirst(newStartPlayer);
        this.players.addLast(startPlayer);
        boolean validInput = false;
        while (!validInput) {
            System.out.println(" \n \n " +
                    "Voulez vous faire une pause? Tapez 'oui' ou 'non' : ");
            try {
                String choix = sc.nextLine().toLowerCase(); // Convert to lowercase for easier comparison
                if (choix.equals("oui") || choix.equals("1")) {
                    System.out.println("Mise en pause");
                    validInput = true;
                    isPaused=true;

                    // Save current game state
                    try {
                        this.backup.saveGames("parties.dat");
                        System.out.println("Partie sauvegardée avec succès!");

                    } catch (Exception e) {
                        System.out.println("Erreur lors de la sauvegarde : " + e.getMessage());
                    }
                    // Return to menu by ending the current game loop
                    return;
                } else if (choix.equals("non") || choix.equals("0")) {
                    System.out.println("La partie continue !");
                    validInput = true;

                    // Continue with the game
                } else {
                    System.out.println("Entrée invalide. Veuillez taper 'oui' ou 'non' (ou '1' ou '0')");
                }
            } catch (Exception e) {
                System.out.println("Mauvaise saisie, veuillez réessayer");
            }
        }
    }

    /**
     * Gère un tour complet de jeu, incluant la planification des actions,
     * leur exécution, et le calcul des scores.
     *
     * @param board le plateau de jeu
     * @param sc le scanner pour la saisie utilisateur
     */
    public void Tour(Board board, Scanner sc) {
        this.tour+=1;
        System.out.println("------------------------------------------------------------- Tour " + tour+ " -------------------------------------------------------------");
        //Les joueurs choisissent dans quel ordre ils souhaitent effectuer leurs commandes
        for (Player player : players) {
            player.plan();
        }
        //Les actions de chaque joueur sont realisées
        this.perform(board);

        // Maintenance des vaisseaux
        this.sustainShips(sc);
        //Choix de secteurs pour le calcul des scores
        if(this.tour<8){
            this.calcScore(sc, 1);
        }else{
            this.calcScore(sc, 2);
        }
        //Changement de start player
        this.endOfRound(sc);
    }

    /**
     * Lors de l'exécution de cette fonction, si un vaisseau ne peut pas être entretenu car il y a trop de vaisseaux sur un Hex
     * il est supprimé.
     *
     * @param sc le scanner pour la saisie utilisateur
     */
    private void sustainShips(Scanner sc) {
        System.out.println("\n \n ---------------------- Sustain Ships -------------------------- ");
        for (List<Hex> rangee: this.gameBoard.gameBoard) {
            for (Hex hexActuel: rangee) {
                if (hexActuel.isControlled()){ // peut être enlevé
                    while (hexActuel.getNbMaxShips()< hexActuel.getShipsOnHex().size()) {
                        System.out.println("Le vaisseau de " + hexActuel.getShipsOnHex().get(0).getOwnerName() + " en x=" + hexActuel.getxPosition() + ", y=" + hexActuel.getyPosition() + " n'a pas survécu"); //TODO meilleur affichage
                        Ship shipSupp = hexActuel.getShipsOnHex().removeLast();
                        shipSupp.destroyShip();
                    }
                }
            }
        }
    }

    /**
     * Calcule les scores des joueurs pour le tour actuel.
     *
     * @param sc le scanner pour la saisie utilisateur
     * @param scoring le type de scoring à utiliser (1 pour normal, 2 pour fin de partie)
     */
    public void calcScore(Scanner sc, int scoring) {
        System.out.println("-------------- Calcul des scores --------------");
        System.out.println("-------------- Rappel des secteurs controlés par les joueurs --------------");
        for(Player player : players){
            System.out.println(player);
        }
        ArrayList <SectorCard> chosenSectors=new ArrayList <SectorCard>();
        SectorCard actualSector = null;
        boolean plusDeSecteurs=false;
        for (Player player : this.players) {
            while (true) {
                // vérification qu'il reste des secteurs disponibles
                if(chosenSectors.size()!=0){
                    System.out.println("Secteurs choisis" + chosenSectors);
                }else{
                    System.out.println("Aucun secteurs choisis pour l'instant");
                }
                plusDeSecteurs=true;
                for (SectorCard[] rangee: this.gameBoard.getBoard()) {
                    for (SectorCard card : rangee) {
                        for (Hex hex : card.getHexes()){
                            if (hex.isControlled()){
                                if (!hex.isTriPrime() && !chosenSectors.contains(card) && (hex.getControlledBy().equals(player))) {
                                    plusDeSecteurs=false;
                                    break;
                                }
                            }
                        }
                    }
                }
                if (plusDeSecteurs){
                    System.out.println("Plus de secteurs disponibles.");
                    break;
                }
                actualSector = player.chooseSector(this.gameBoard, sc);
                if (chosenSectors.contains(actualSector)) {
                    System.out.println("Ce secteur a déjà été choisi, veuillez recommencer");
                }else if (actualSector.isEmpty()) {System.out.println("Ce secteur est vide, veuillez recommencer");
                }else if (actualSector.getIsTriPrime()) {
                    System.out.println("Ce secteur est le TriPrime, qui ne peut pas être choisi, veuillez recommencer");
                }else{
                    break;
                }
            }
            chosenSectors.add(actualSector);
            this.scores[player.getId()]=this.scores[player.getId()] + actualSector.calculateScore(player, scoring);
        }

        // si ce n'est pas le dernier tour, le joueur qui controle le TriPrime peut choisir deux secteurs
        if(this.tour<8){
            for (Player player : this.players) {
                if (player.isControllsTriPrime()) {
                    plusDeSecteurs=true;
                    for (SectorCard[] rangee: this.gameBoard.getBoard()) {
                        for (SectorCard card : rangee) {
                            for (Hex hex : card.getHexes()){
                                if (hex.isControlled()){
                                    if (!chosenSectors.contains(card) && (hex.getControlledBy().equals(player))) {
                                        plusDeSecteurs=false;
                                        break;
                                    }
                                }
                            }
                        }
                    }

                    if (plusDeSecteurs){
                        System.out.println("Plus de secteurs disponibles.");
                        break;
                    }

                    while (true) {
                        actualSector = player.chooseSector(this.gameBoard, sc);
                        if (!chosenSectors.contains(actualSector)) {
                            break;
                        }else{
                            System.out.println("Secteur déjà choisis");
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
     * Détermine si la partie doit se terminer.
     * La partie s'arrête soit au bout de neuf tours, soit lorsqu'un joueur est éliminé.
     *
     * @param tour le numéro du tour actuel
     * @return true si c'est la fin de la partie, false sinon
     */
    private boolean finPartie(int tour) {
        boolean isEliminated = players.stream().anyMatch(Player::isEliminated);

        return isPaused || isEliminated || tour > 9;
    }


    /**
     * Détermine et annonce le gagnant de la partie.
     * Affiche le podium avec les scores de tous les joueurs.
     */
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
        System.out.println("\n \n--------------------- Fin du jeu ! ---------------------\nLe gagnant est : "+ this.players.get(idGagnant).getName());
        System.out.println("Le podium des scores est :\n ---- 1er : " + this.players.get(idGagnant).getName() + " Avec un score de " + scores[idGagnant]);
        System.out.println("   ---- 2er : " + this.players.get(idSecond).getName() + " Avec un score de " + scores[idSecond]);
        System.out.println("     ---- 3eme : " + this.players.get(idDernier).getName() + " Avec un score de " + scores[idDernier]);

    }

}