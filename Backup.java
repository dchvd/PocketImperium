package pocket_imperium;
import java.util.ArrayList;
import java.util.Scanner;

public class Backup {
	
	private ArrayList<Partie> parties=new ArrayList<Partie>();
	
	public void menu() {
		
		while (true) {
			Scanner sc = new Scanner(System.in);
			System.out.print(" \n Bienvenue dans le jeu Pocket Imperium ! \n "
					+ "  1 - Commencer une nouvelle partie "
					+ "\n   2 - Reprendre une partie en cours"
					+ "\n   0 - Sortir du jeu"
					+ "\n Votre choix : ");
	    	try {
	    		String choix = sc.nextLine();
	    		if (choix.equals("0")) {
	    			System.out.println("Vous allez quitter le jeu \nA bientot sur le jeu Pocket Imperium !");
	        		return;
	        	}
	    		else if (choix.equals("1")) {
	        		System.out.println("Début d'une nouvelle partie... \n");
	        		this.parties.add(new Partie());
	        		this.menu();
	        		return;
	        		
	        	}
	        	else if (choix.equals("2")) {
	        		System.out.println("Les sauvegardes sont : ");
	        		for (int i =0; i<parties.size(); i++) {
	        			System.out.println("Sauvegarde n°" + i);
	        		}
	        		System.out.println("Entrez le numéro de la partie que vous souhaitez reprendre :");
	        		try{
	        			int num = sc.nextInt();
	        			if ((num>0) && (num<parties.size())) {
	            			//parties.get(num).startGame(); // PAS START GAME MAIS AUTRE CHOSE
	            		}
	        		}catch(Exception e) {
	        			System.out.println("Mauvaise saisie, veuillez réessayer" );
	        		}
	        	}else {
	        		System.out.println("Mauvaise saisie, veuillez réessayer");
	        	}
	    	}
	    	catch (Exception e){
	    		System.out.println("Erreur : " + e.getMessage());
	    		
	   		}
		}
		
    	
    	   	
	}
	public static void main(String[] args) {
    	Backup jeu = new Backup();
    	jeu.menu();
    	
    	/*
    	ArrayList<Player> players=new ArrayList<>();
    	players.add(new Player(false));
    	players.add(new Player(false));
    	players.add(new Player(false));*/
    	//Partie partie1=new Partie(players);
    }
}