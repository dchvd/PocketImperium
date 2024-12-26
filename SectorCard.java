package pocket_imperium;

import java.util.ArrayList;
import java.util.List;

public class SectorCard {
    private int anchorX;
    private int anchorY;
    private String type; //MiddleExteriorCard ou CentralCard ou ExteriorCard
    private String name;
    private List<Hex> hexes= new ArrayList<>();
    private boolean isAlreadyChoosed=false; // NOUVEAU
	private boolean estTriPrime=false;
	
    /**
     * La fonction permet de créer une carte de secteur
     * @param type correspond au type de carte ("Central", "MiddleExterior", "Exterior")
     */
	public SectorCard(String type) { //	public SectorCard(String name, String type) {
        this.type = type;
        this.anchorX = 0;
        this.anchorY = 0;
        this.name = "";
        
        this.hexes = new ArrayList<>();
        if (type.equals("MiddleExteriorCard")) {
            createMiddleExteriorCard();
        } else if (type.equals("CentralCard")) {
        	this.name = "TriPrime";
            createCentralCard();
        } else if (type.equals("ExteriorCard")) {
            createExteriorCard();
        } else {
            throw new IllegalArgumentException("Type de carte inconnu : " + type);
        }
    }

    public String getType() {
        return type;
    }

    public void setPlacement(int x, int y) {
    	this.anchorX=x;
    	this.anchorY=y;
    	if (x==0 && y==0) {
    		this.name="TopLeft";
    	}else if (x==0 && y==1) {
    		this.name="TopMiddle";
    	}else if (x==0 && y==2) {
    		this.name="TopRight";
    	}else if (x==1 && y==0) {
    		this.name="MiddleLeft";
    	}else if (x==1 && y==2) {
    		this.name="MiddleRight";
    	}else if (x==2 && y==0) {
    		this.name="BottomLeft";
    	}else if (x==2 && y==0) {
    		this.name="BottomMiddle";
    	}else if (x==2 && y==0) {
    		this.name="BottomRight";
    	}else {
    		throw new IllegalArgumentException("Emplacement de carte impossible : x=" + x + "y=" + y);
    	}
    }
    
    // deux prochaines fonctions : nouvelles
    public boolean getIsAlreadyChosen() {
    	return this.isAlreadyChoosed;
    }
    public void isChoosed() {
    	this.isAlreadyChoosed=true;
    }
    public List<Hex> getHexes() {
        return this.hexes;
    }
    public boolean getIsTriPrime() {
    	return this.estTriPrime;
    }

    public void addHexInSector(int relativeX, int relativeY, String typeH) {
    	if (typeH=="TriPrime") {
    		this.hexes.add(new Hex(relativeX, relativeY, true, false, false));
    	}else if(typeH=="System1") {
            this.hexes.add(new Hex(relativeX, relativeY, false, true, false));
    	}else if(typeH=="System2") {
            this.hexes.add(new Hex(relativeX, relativeY, false, false, true));
    	}else {
            this.hexes.add(new Hex(relativeX, relativeY, false, false, false));
    	}
    }
    
    	@Override
    public String toString() {
    	StringBuilder sb = new StringBuilder();
    	sb.append("SectorCard : ").append(name).append('\n');
    	sb.append("Type: ").append(type).append("\n");
        sb.append("Hexes:\n");

        for (Hex hex : hexes) {
            sb.append(" - ").append(hex).append("\n");
        }
        return sb.toString();
    }
    
    public void createMiddleExteriorCard() {
        String typeHex = "regularHex";
        int[][] coordSystems = Helper.placeSystems("MiddleExteriorCard");
        for (int i=0; i<3; i++) {
        	for (int j=0; j<3; j++) {
        		if ((i==1) && (j==2)) {
            		break;
            	}
            	if ((coordSystems[0][0]==i && coordSystems[0][1]==j) || (coordSystems[1][0]==i && coordSystems[1][1]==j)) {
            		typeHex = "System1";
            	}else if (coordSystems[2][0]==i && coordSystems[2][1]==j) {
            		typeHex = "System2";
            	}else {
            		typeHex="RegularHex";
            	}
        		addHexInSector(i, j, typeHex);
        	} 
        }
    }
    
    public void createCentralCard() {
        for (int i=0; i<3; i++) {
        	for (int j=0; j<3; j++) {
            	if ((i==1) && (j==2)) {
            		break;
            	}if (i==1 ||(i==0 && j==1)||(i==2 && j==1)) {
            		addHexInSector(i, j, "TriPrime");
            	}else {
            		addHexInSector(i, j, "regularHex");
            	}
        	}
        }
        this.estTriPrime = true;
    }
    
    public void createExteriorCard() {
        String typeHex = "regularHex";
        int[][] coordSystems = Helper.placeSystems("ExteriorCard");
        for (int i=0; i<3; i++) {
        	for (int j=0; j<3; j++) {
            	if ( ((i==0) || (i==2)) && (j==2) ) {
            		break;
            	}
            	if ((coordSystems[0][0]==i && coordSystems[0][1]==j) || (coordSystems[1][0]==i && coordSystems[1][1]==j)) {
            		typeHex = "System1";
            	}else if (coordSystems[2][0]==i && coordSystems[2][1]==j) {
            		typeHex = "System2";
            	}else {
            		typeHex="regularHex";
            	}
        		addHexInSector(i, j, typeHex);
        	} 
        }
    }
	
    public int calculateScore(Player player, int i) {
    	int score=0;
    	for(Hex currentHex : this.hexes) {
    		System.out.println(currentHex);
    		System.out.println("Controllé : " + currentHex.isControlled());
    		if (currentHex.isControlled()) {
    			System.out.println(" et par : " + currentHex.getControlledBy().getName());
    			System.out.println("Celui du joueur en train d'etre testé : " + player.getId());
    			System.out.println("Celui du joueur qui contrôle le currentHex : " + currentHex.getControlledBy().getId());
    			
    			if(player.getId()==currentHex.getControlledBy().getId()) { //player.getControlledHexs().contains(currentHex)
        			score+= i*currentHex.getValue();
        			System.out.println("valeur du hex : " + currentHex.getValue());
        		}
    		}
    	}
		return score;
	}

	
    public boolean isEmpty() {
    	for(Hex currentHex : this.hexes) {
    		if(currentHex.isControlled()) {
    			return false;
    		}
    	}
		return true;
	}
}