package pocket_imperium;

import java.util.ArrayList;

public enum GameStrategy {
	
	STRATEGY1,
	STRATEGY2,
	STRATEGY3;
	private ArrayList<Integer> commands;
	public int chooseStrategy() {
		commands = new ArrayList<>();
		switch (this) {
			case STRATEGY1:
				commands.add(1);
				commands.add(2);
				commands.add(3);
			case STRATEGY2:
				commands.add(3);
				commands.add(2);
				commands.add(1);
			case STRATEGY3:
				commands.add(2);
				commands.add(3);
				commands.add(1);
		}
        return 0;
    }
}
