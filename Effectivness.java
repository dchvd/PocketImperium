package pocket_imperium;

public enum Effectivness {
		EFF_nulle(0),
		EFF_min(1),
		EFF_moy(2),
		EFF_max(3);
	
	//Attributs
	private int eff;
	
	//Constructeur
	private Effectivness(int eff) {
		this.eff=eff;
	}
}
