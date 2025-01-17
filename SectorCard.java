package com.example.demo;

import java.util.ArrayList;
import java.util.List;
import java.io.*;

/**
 * La classe SectorCard représente une carte secteur du plateau de jeu.
 *
 * Une carte secteur peut être de différents types (Centrale, Extérieure, Moyenne-Extérieure)
 * et contient plusieurs hexagones. Elle peut être placée à différentes positions sur le plateau
 * et permet de calculer les scores des joueurs.
 *
 * @author Anaelle Melo, Daria Avdeeva
 * @version 1.0
 */
public class SectorCard implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * Type du secteur
     */
    private String type; //MiddleExteriorCard ou CentralCard ou ExteriorCard

    /**
     * Nom du secteur
     */
    private String name;

    /**
     * Liste des Hex dans ce secteur
     */
    private List<Hex> hexes= new ArrayList<>();

    /**
     * Etat du secteur (s'il est déjà choisi ou non lors du calcul des scores à la fin d'un tour)
     */
    private boolean isAlreadyChoosed=false;

    /**
     *  Etat du secteur, s'il est le triPrime ou non (true si oui, false sinon)
     */
    private boolean estTriPrime=false;

    /**
     * La fonction permet de créer une carte de secteur
     * @param type correspond au type de carte ("Central", "MiddleExterior", "Exterior")
     */
    public SectorCard(String type) { //	public SectorCard(String name, String type) {
        this.type = type;
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

    /**
     * Récupère le type de ce secteur.
     *
     * @return le type de ce secteur.
     */
    public String getType() {
        return type;
    }

    /**
     * Définit la position de la carte sur le plateau et met à jour son nom.
     *
     * @param x coordonnée x de l'ancrage de la carte
     * @param y coordonnée y de l'ancrage de la carte
     * @throws IllegalArgumentException si la position est invalide
     */
    public void setPlacement(int x, int y) throws IllegalArgumentException {
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

    /**
     * Récupère si le secteur est déjà choisi.
     *
     * @return true s'il est choisi, false sinon.
     */
    public boolean getIsAlreadyChosen() {
        return this.isAlreadyChoosed;
    }

    /**
     * Marque le secteur comme choisi.
     */
    public void isChoosed() {
        this.isAlreadyChoosed=true;
    }

    /**
     * Récupère les Hex placés dans ce secteur.
     *
     * @return les hexs placés dans ce secteur.
     */
    public List<Hex> getHexes() {
        return hexes;
    }

    /**
     * Vérifie si le secteur est le triPrime.
     *
     * @return true si oui, false sinon.
     */
    public boolean getIsTriPrime() {
        return this.estTriPrime;
    }

    /**
     * Ajoute un hexagone à la carte secteur avec le type spécifié.
     *
     * @param relativeX position x relative dans la carte
     * @param relativeY position y relative dans la carte
     * @param typeH type d'hexagone ("TriPrime", "System1", "System2" ou autre)
     */
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

    /**
     * Renvoie une représentation textuelle du Secteur, incluant son nom, son type et les Hex qui le composent.
     *
     * @return Une chaîne décrivant le hex.
     */
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

    /**
     * Crée une carte secteur extérieure au milieu avec des systèmes placés aléatoirement.
     */
    public void createMiddleExteriorCard() {
        String typeHex = "regularHex";
        int[][] coordSystems = Helper.placeSystems("MiddleExteriorCard");
        for (int i=0; i<3; i++) {
            for (int j=0; j<3; j++) {
                if ( (i==1) && (j==2) ) {
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

    /**
     * Crée une carte secteur centrale.
     */
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

    /**
     * Calcule le score d'un joueur pour cette carte secteur.
     *
     * @param player le joueur dont on calcule le score
     * @param i le multiplicateur de score
     * @return le score calculé
     */
    public int calculateScore(Player player, int i) {
        int score=0;
        for(Hex currentHex : this.hexes) {
            //System.out.println(currentHex);
            //System.out.println("Controllé : " + currentHex.isControlled());
            if (currentHex.isControlled()) {
                //System.out.println(" et par : " + currentHex.getControlledBy().getName());
                //System.out.println("Celui du joueur en train d'etre testé : " + player.getId());
                //System.out.println("Celui du joueur qui contrôle le currentHex : " + currentHex.getControlledBy().getId());

                if(player.getId()==currentHex.getControlledBy().getId()) { //player.getControlledHexs().contains(currentHex)
                    score+= i*currentHex.getValue();
                    //System.out.println("valeur du hex : " + currentHex.getValue());
                }
            }
        }
        return score;
    }

    /**
     * Vérifie si la carte est vide (aucun hexagone contrôlé).
     *
     * @return true si la carte est vide, false sinon
     */
    public boolean isEmpty() {
        for(Hex currentHex : this.hexes) {
            if(currentHex.isControlled()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Crée une carte secteur extérieure avec des systèmes placés aléatoirement.
     */
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
                    typeHex="RegularHex";
                }
                addHexInSector(i, j, typeHex);
            }
        }
    }
}
