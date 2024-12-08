package pocket_imperium;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class dump {
	/*int mySystems [];
	mySystems = new int[3];
	for (int h=0; i<2; i++) {
		mySystems[h] = randomNumbers.nextInt(5);
		if (mySystems[h]<2) {
			if 
		}
	}
		public static ArrayList placerSystemes (String typeCarte) {
		Random randomNumbers = new Random();	
	    boolean emplacementValide = false;	
	    int[][] coordSyst;
	    while (!emplacementValide) {
	    	for (int i=0; i<3; i++) {
	        	for (int j=0; j<3; j++) {
	            	if (((i==1 && j==0)||(i==1 && j==2)) &&(typeCarte=="ExteriorCard")) {
	            		break;
	            	}else if (((i==0 && j==0)||(i==0 && j==2)||((i==2 && j==0)||(i==2 && j==2))) &&(typeCarte=="MiddleExteriorCard")) {
	            		break;
	            	}
	            	int syst1bis= randomNumbers.nextInt(5);
	            	int syst2= randomNumbers.nextInt(5);
	            	int syst1 = randomNumbers.nextInt(5);
	            	if (syst1 !=syst1bis ||syst1bis!=syst2) {
	            		emplacementValide=true;
	            		
	            	}
	        	}
	        	
	        }
	    }
	else if (i%2!=1) {
                    	if (j==0 || j==2) {
                    		//hexesCurrentCard.remove(new Hex(0,0,false,false,false));
                    		//hexesCurrentCard.remove(new Hex(2,0,false,false,false));
                    		hexesCurrentCard.remove(4);
                    		hexesCurrentCard.remove(2);
                    	}else if (j==2) {
                    		//hexesCurrentCard.remove(new Hex(0,0,false,false,false));                    		
                    		//hexesCurrentCard.remove(new Hex(2,2,false,false,false));
                    		hexesCurrentCard.remove(4);
                    		hexesCurrentCard.remove(2);
                    	}
                    }
	*/
	public void ancienneSectorCard() {
		/*
		package pocket_imperium;

		import java.util.ArrayList;
		import java.util.List;

		public class SectorCard {
		    private int anchorX;
		    private int anchorY;
		    private String type; //MiddleExteriorCard ou CentralCard ou ExteriorCard
		    private String name;
		    private List<Hex> hexes= new ArrayList<>();
			private boolean estTriPrime=false;
			
		    /*
		     * La fonction permet de créer une carte de secteur
		     * @param anchorX correspond à la position en x (ligne) de la carte dans le plateau
		     * @param anchorY correspond à la position en y (colonne) de la carte dans le plateau 
		     * @param type correspond au type de carte ("Central", "MiddleExterior", "Exterior")
		     
			public SectorCard(String type) { //	public SectorCard(String name, String type) {
		        this.type = type;
		        this.anchorX = 0;
		        this.anchorY = 0;
		        this.name = "";
		        
		        this.hexes = new ArrayList<>();
		        if (type.equals("MiddleExteriorCard")) {
		            createMiddleExteriorCard();
		        } else if (type.equals("CentralCard")) {
		        	this.name = "TriPrime";
		            createCentralCard();
		        } else if (type.equals("ExteriorCard")) {
		            createExteriorCard();
		        } else {
		            throw new IllegalArgumentException("Type de carte inconnu : " + type);
		        }
		    }

		    public String getType() {
		        return type;
		    }

		    public void setPlacement(int x, int y) {
		    	this.anchorX=x;
		    	this.anchorY=y;
		    	if (x==0 && y==0) {
		    		this.name="TopLeft";
		    	}else if (x==0 && y==1) {
		    		this.name="TopMiddle";
		    	}else if (x==0 && y==2) {
		    		this.name="TopRight";
		    	}else if (x==1 && y==0) {
		    		this.name="MiddleLeft";
		    	}else if (x==1 && y==2) {
		    		this.name="MiddleRight";
		    	}else if (x==2 && y==0) {
		    		this.name="BottomLeft";
		    	}else if (x==2 && y==0) {
		    		this.name="BottomMiddle";
		    	}else if (x==2 && y==0) {
		    		this.name="BottomRight";
		    	}else {
		    		throw new IllegalArgumentException("Emplacement de carte impossible : x=" + x + "y=" + y);
		    	}
		    }
		    
		    public List<Hex> getHexes() {
		        return hexes;
		    }

		    public void addHexInSector(int relativeX, int relativeY, String typeH) {
		    	if (typeH=="TriPrime") {
		    		this.hexes.add(new Hex(relativeX, relativeY, true, false, false));
		    	}else if(typeH=="System1") {
		            this.hexes.add(new Hex(relativeX, relativeY, false, true, false));
		    	}else if(typeH=="System2") {
		            this.hexes.add(new Hex(relativeX, relativeY, false, false, true));
		    	}else {
		            this.hexes.add(new Hex(relativeX, relativeY, false, false, false));
		    	}
		    }
		    
		    /*public void addHex(int relativeX, int relativeY, String typeH) {
		    	if (typeH=="TriPrime") {
		    		this.hexes.add(new Hex(anchorX + relativeX, anchorY + relativeY, true, false, false));
		    	}else if(typeH=="System1") {
		            this.hexes.add(new Hex(anchorX + relativeX, anchorY + relativeY, false, true, false));
		    	}else if(typeH=="System2") {
		            this.hexes.add(new Hex(anchorX + relativeX, anchorY + relativeY, false, false, true));
		    	}else {
		            this.hexes.add(new Hex(anchorX + relativeX, anchorY + relativeY, false, false, false));
		    	}
		    }
			
		    
		    @Override
		    public String toString() {
		    	StringBuilder sb = new StringBuilder();
		    	sb.append("SectorCard : ").append(name).append('\n');
		    	sb.append("Type: ").append(type).append("\n");
		        sb.append("Hexes:\n");

		        for (Hex hex : hexes) {
		            sb.append(" - ").append(hex).append("\n");
		        }
		        return sb.toString();
		    }
		    
		    public void createMiddleExteriorCard() {
		        String typeHex = "regularHex";
		        int[][] coordSystems = Helper.placeSystems("MiddleExteriorCard");
		        for (int i=0; i<3; i++) {
		        	for (int j=0; j<3; j++) {
		            	/*if ((i==0) && (j==2) || (i==2) && (j==2)) {
		            		typeHex = "regularHex";
		            	}
		        		if ( (i==1) && (j==2) ) {
		            		break;
		            	}
		            	if ((coordSystems[0][0]==i && coordSystems[0][1]==j) || (coordSystems[1][0]==i && coordSystems[1][1]==j)) {
		            		typeHex = "System1";
		            	}else if (coordSystems[2][0]==i && coordSystems[2][1]==j) {
		            		typeHex = "System2";
		            	}else {
		            		typeHex="RegularHex";
		            	}
		        		addHexInSector(i, j, typeHex);
		        	} 
		        }
		    }
		    
		    public void createCentralCard() {
		        for (int i=0; i<3; i++) {
		        	for (int j=0; j<3; j++) {
		            	if ((i==1) && (j==2)) {
		            		break;
		            	}if (i==1 ||(i==0 && j==1)||(i==2 && j==1)) {
		            		addHexInSector(i, j, "TriPrime");
		            	}else {
		            		addHexInSector(i, j, "regularHex");
		            	}
		        	}
		        }
		        this.estTriPrime = true;
		    }
		    
		    public void createExteriorCard() {
		        String typeHex = "regularHex";
		        int[][] coordSystems = Helper.placeSystems("ExteriorCard");
		        for (int i=0; i<3; i++) {
		        	for (int j=0; j<3; j++) {
		            	if ( ((i==0) || (i==2)) && (j==2) ) {
		            		break;
		            	}
		            	if ((coordSystems[0][0]==i && coordSystems[0][1]==j) || (coordSystems[1][0]==i && coordSystems[1][1]==j)) {
		            		typeHex = "System1";
		            	}else if (coordSystems[2][0]==i && coordSystems[2][1]==j) {
		            		typeHex = "System2";
		            	}else {
		            		typeHex="RegularHex";
		            	}
		        		addHexInSector(i, j, typeHex);
		        	} 
		        }
		    }


		}*/
	}

	public void anciennePartie() {
		/*
		 * package pocket_imperium;

import java.util.ArrayList;
import java.util.List;

/**
 * La classe Partie permet de gérer le déroulement d'une partie de Imperium Pocket.
 */
public class Partie {
	// ---- PLATEAU ----
	//board est composé des sectorCards, il permettra notamment de compter les scores
	//private SectorCard[][] board = new SectorCard[3][3];
	//gameBoard sera la grille de Hex que l'on utilise pour les fonctions expand, exterminate et explore
	//public List<List<Hex>> gameBoard = new ArrayList<>();
	
	//Génère le plateau : 
	private Board gameBoard = new Board();
	
	/**
	 * La liste des Joueurs permet d'enregistrer les différents joueurs qui vont la partie de Imperium Pocket
	 */
	private List<Player> players; //j'ai un doute sur s'il faut faire une nouvelle classe pour une liste
	/**
	 * La variable tour permet de compter les tours de la partie, sachant qu'une partie ne peut pas avoir plus de 9 tours.
	 */
    private int tour=0;
    
    /**
     * Le constructeur Partie permet de créer une partie du jeu Imperium Pocket
     * @param players est la liste des joueurs qui s'affronteront durant la partie
     */
    public Partie(List<Player> players) {
    	this.players = players;
    }
    
    /**
     * La fonction startGame permet de lancer la partie
     */
    public void startGame() {
        while (!finPartie()) {
            Tour();
            tour++;
        }
        declareWinner();
    }
    
    //Fonction caractéristique d'un tour
    private void Tour() {
        System.out.println("Tour " + tour);
        ArrayList <SectorCard> chosenSectors=new ArrayList<>();
        for (Player player : players) {
            player.planCommands("1", "2", "3");// faux
            player.executeCommands();
        }SectorCard actualSector = null;
        for (Player player : players) {
        	boolean choixValide = false;
        	while(!choixValide) {
        		actualSector = player.chooseSector(gameBoard);
            	choixValide = !chosenSectors.contains(actualSector);
            	if(!choixValide) {
            		System.out.println("Choix non valide, le sector card a déjà été choisi par un joueur. \n Veuillez en choisir un autre");
    
            	}
            }
        	chosenSectors.add(actualSector);
        }
        
    }
        //calcul de scores

    
    //Dterminer la fin de la Partie
    private boolean finPartie() {
    	if (this.tour>9) {
    		return true; 
    	}
    	return false;
    }
    
    //Determiner Gagnant
    private void declareWinner() {
       
    }
    
    public static void main(String [] args) {
    	
    }
    

    /*
    public static void printBoard(SectorCard[][] board) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] != null) {
                    System.out.print(board[i][j].getName() + " ");
                } else {
                    System.out.print("Empty ");
                }
            }
            System.out.println();
        }
    }*/
}
		 */
	}
	
	
	
}
