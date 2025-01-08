package pocket_imperium;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;
import java.util.*;

public class Backup implements Serializable{
	private static final long serialVersionUID = 1L;
	private ArrayList<Partie> parties=new ArrayList<Partie>();

	public void saveGames(String fileName) {
		try (ObjectOutputStream oos = new ObjectOutputStream(
				new FileOutputStream(fileName))) {
			oos.writeObject(this);
			System.out.println("Parties sauvegardées avec succès !");
		} catch (IOException e) {
			System.out.println("Erreur lors de la sauvegarde : " + e.getMessage());
		}
	}

	public static Backup loadGames(String fileName) {
		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
			return (Backup) ois.readObject();
		} catch (IOException | ClassNotFoundException e) {
			System.out.println("Erreur lors du chargement : " + e.getMessage());
			return new Backup(); // Return new backup if loading fails
		}
	}

	public void menu() {
		Scanner sc = new Scanner(System.in);
		while (true) {
			System.out.print(" \n Bienvenue dans le jeu Pocket Imperium ! \n "
					+ "  1 - Commencer une nouvelle partie "
					+ "\n   2 - Reprendre une partie en cours"
					+ "\n   0 - Sortir du jeu"
					+ "\n Votre choix : ");
	    	try {
	    		String choix = sc.nextLine();
				switch (choix) {
					case "0":
						System.out.println("Vous allez quitter le jeu \nA bientot sur le jeu Pocket Imperium !");
						saveGames("parties.dat"); // Auto-save on exit
						sc.close();
						return;
					case "1":
						System.out.println("Début d'une nouvelle partie... \n");
						this.parties.add(new Partie(sc));
						break;
					case "2":
						if (parties.isEmpty()) {
							System.out.println("Aucune partie sauvegardée.");
							break;
						}
						System.out.println("Les sauvegardes sont : ");
						for (int i = 0; i < parties.size(); i++) {
							System.out.println("Sauvegarde n°" + i);
						}
						System.out.println("Entrez le numéro de la partie que vous souhaitez reprendre :");
						try {
							int num = Integer.parseInt(sc.nextLine());
							if (num >= 0 && num < parties.size()) {
								Partie selectedGame = parties.get(num);
								selectedGame.resumeGame(sc);  // Use resumeGame instead of startGame
							} else {
								System.out.println("Numéro de partie invalide");
							}
						} catch (NumberFormatException e) {
							System.out.println("Mauvaise saisie, veuillez réessayer");
						}
						break;
					case "3":
						saveGames("parties.dat");
						break;
					default:
						System.out.println("Mauvaise saisie, veuillez réessayer");
				}
				}
	    	catch (Exception e){
	    		System.out.println("Erreur : " + e.getMessage());
	    		
	   		}
		}
		
    	
    	   	
	}

	public static void main(String[] args) {
		Backup jeu = loadGames("parties.dat");
		jeu.menu();
    }
}
