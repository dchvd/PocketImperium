
package pocket_imperium;

public abstract class Command {
	
	//Attributs
	protected Effectiveness effectiveness;
	private String nomCommande;
	
	//Constructeur
	public Command(Effectiveness effectivness, String nomCommande) {
		this.effectiveness=effectivness;
		this.nomCommande=nomCommande;
	}
	
	//Effectuer une commande
	public void startCommand() {
	}

}
