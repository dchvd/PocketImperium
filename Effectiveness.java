package pocket_imperium;

public enum Effectiveness {
		EFF_nulle(0),
		EFF_min(1),
		EFF_moy(2),
		EFF_max(3);
	
	//Attributs
	private int eff;
	
	//Constructeur
	private Effectiveness(int eff) {
		this.eff=eff;
	}
}