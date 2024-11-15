package pocket_imperium;

public abstract class Command {
	
	//Attributs
	private Effectivness effectivness;
	private String nomCommande;
	
	//Constructeur
	public Command(Effectivness effectivness, String nomCommande) {
		this.effectivness=effectivness;
		this.nomCommande=nomCommande;
	}
	
	//Effectuer une commande
	public void startCommand() {
	}

}
