package pocket_imperium;

import java.util.List;

public class Player {
    private String name;
    private GameStrategy strategy;
    private List<Ship> ships; //j'ai un doute sur s'il faut faire une nouvelle classe pour une liste
    private List<Command> commands; ////j'ai un doute sur s'il faut faire une nouvelle classe pour une liste
    private boolean isVirtual;
    private boolean controllsTriPrime;
    
    //ajouter une liste des Hex controllés

    public Player(String name, boolean isVirtual, GameStrategy strategy) {
        this.name = name;
        this.isVirtual = isVirtual;
        this.strategy = strategy;
        this.commands = new ArrayList<>();
        this.ships = new ArrayList<>();
    }

    public void planCommands() {
     
    }

    public void executeCommands() {
    	
    }

    public boolean isEliminated() {
    	return ships.isEmpty();
    }
    
    public boolean isWinner() {
    	
    }
}


