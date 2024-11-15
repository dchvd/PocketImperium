package pocket_imperium;

public class Expand extends Command {
	
	public Expand(Effectivness effectivness, String nomCommande) {
		super(effectivness, nomCommande);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void startCommand() {
		for (int i;i<super.effectivness+1;i++) {
			System.out.println("Choose a controlled system to add your ship.");
			
		}
	}
	
	public void addShip() {
		for (int i;i<super.effectivness+1;i++) {
			System.out.println("Choose a controlled system to add your ship");
			
		}
	}
}
