
package pocket_imperium;


public abstract class Command {
	
	//Attributs
	protected Effectiveness effectiveness;
	private String nomCommande;
	
	//Constructeur
	public Command(Effectiveness effectiveness, String nomCommande) {
		this.effectiveness=effectiveness;
		this.nomCommande=nomCommande;
	}
	
	//Effectuer une commande
	public void startCommand() {
	}

}
