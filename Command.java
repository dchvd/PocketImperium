
package pocket_imperium;

public abstract class Command {
	
	//Attributs
	private Effectiveness effectivness;
	private String nomCommande;
	
	//Constructeur
	public Command(Effectiveness effectivness, String nomCommande) {
		this.effectivness=effectivness;
		this.nomCommande=nomCommande;
	}
	
	//Effectuer une commande
	public void startCommand() {
	}

}
