package pocket_imperium;
/**
 * La classe Partie permet de gérer le déroulement d'une partie de Imperium Pocket.
 */
public class Partie {
	
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
        for (Player player : players) {
            player.planCommands();
            player.executeCommands();
        }
        //calcul de scores
    }
    
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

}
