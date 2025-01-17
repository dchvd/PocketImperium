package com.example.demo;


import java.util.ArrayList;
import java.io.*;

/**
 * La classe Hex représente un hexagone sur le plateau de jeu.
 *
 * Un hexagone peut contenir des vaisseaux et avoir différentes valeurs selon son type
 * (normal, système de niveau 1, système de niveau 2 ou TriPrime). Il peut être contrôlé
 * par un joueur et a une limite maximum de vaisseaux selon son type.
 *
 * @author Anaelle Melo, Daria Avdeeva
 * @version 1.0
 */
public class Hex implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * Position x du hex
     */
    private int xPosition;

    /**
     * Position y du hex
     */
    private int yPosition;

    /**
     * Liste des vaisseaux sur le hex
     */
    private ArrayList<Ship> shipsOnHex = new ArrayList<Ship>();

    /**
     * Valeur du hex
     */
    private int value;

    /**
     * Nombre maximum de vaisseaux sur ce hex à la fin d'un tour
     */
    private int nbMaxShips;

    /**
     * True si le hex est le triPrime, false sinon
     */
    private boolean triPrime;

    /**
     * True si le hex est un système 1, false sinon
     */
    private boolean systemHex1;

    /**
     * True si le hex est un système 2, false sinon
     */
    private boolean systemHex2;

    /**
     * Etat de controlle du Hex : True si le hex est controllé, false sinon
     */
    private boolean controlled=false;

    /**
     * Le joueur qui controlle le Hex
     */
    private Player controlledBy=null;

    /**
     * Instancie un nouvel hexagone.
     *
     * @param x coordonnée x de l'hexagone sur le plateau
     * @param y coordonnée y de l'hexagone sur le plateau
     * @param triPrime true si l'hexagone est de type TriPrime
     * @param systemHex1 true si l'hexagone est un système de niveau 1
     * @param systemHex2 true si l'hexagone est un système de niveau 2
     */
    public Hex(int x, int y, boolean triPrime, boolean systemHex1, boolean systemHex2) {
        this.xPosition=x;
        this.yPosition=y;
        this.triPrime=triPrime;
        this.systemHex1=systemHex1;
        this.systemHex2=systemHex2;

        if(triPrime==true) {
            value = 3;
            this.nbMaxShips=4;
        }else if (systemHex2==true){
            value = 2;
            this.nbMaxShips=3;
        }else if (systemHex1==true){
            value = 1;
            this.nbMaxShips=2;
        }else {
            value=0;
            this.nbMaxShips=1; // potentiellement 0
        }
    }

    /**
     * Vérifie si le Hex est controllé.
     * @return true si le hex est controllé et false sinon.
     */
    public boolean isControlled() {
        return controlled;
    }

    /**
     * Définit si le hex est controllé.
     *
     * @param controlled true ou false selon si le hex est controllé ou non
     */
    public void setControlled(boolean controlled) {
        this.controlled = controlled;
    }

    /**
     * Récupère le joueur qui controlle le hex.
     *
     * @return le joueur qui controlle, null si personne ne le controle.
     */
    public Player getControlledBy() {
        return controlledBy;
    }

    /**
     * Définit le joueur qui contrôle l'hexagone.
     * Met à jour le statut TriPrime du joueur si nécessaire.
     *
     * @param controlledBy le joueur qui prend le contrôle de l'hexagone, null si aucun
     */
    public void setControlledBy(Player controlledBy) {
        if (this.isTriPrime() && this.controlledBy != null) {
            this.controlledBy.setControllsTriPrime(false);
        }
        this.controlledBy = controlledBy;
        if (controlledBy==null) {
            this.controlled=false;
        }else{
            this.controlled=true;
            if(this.isTriPrime()){
                controlledBy.setControllsTriPrime(true);
            }
        }
    }

    /**
     * Place les coordonnées du Hex.
     *
     * @param x la coordonnée en x du Hex.
     * @param y la coordonnée en y du Hex.
     */
    public void setCoordinates(int x, int y) {
        this.xPosition = x;
        this.yPosition = y;
    }

    /**
     * Renvoie une représentation textuelle du hex, incluant sa valeur et les vaisseaux qui y sont.
     *
     * @return Une chaîne décrivant le hex.
     */
    @Override
    public String toString() {
        String shipsInString = "*".repeat(this.shipsOnHex.size());
        return " { x=" + xPosition + "y=" + yPosition + "| systLevel=" + value + " ships="+ shipsInString +"}";
    }

    /**
     * Récupère la coordonnée x du Hex.
     *
     * @return La coordonnée x actuelle.
     */
    public int getxPosition() {
        return xPosition;
    }

    /**
     * Récupère la coordonnée y du Hex.
     *
     * @return La coordonnée y du Hex.
     */
    public int getyPosition() {
        return yPosition;
    }

    /**
     * Récupère les vaisseaux placés sur le Hex.
     *
     * @return les vaisseaux sur le Hex.
     */
    public ArrayList<Ship> getShipsOnHex() {
        return shipsOnHex;
    }

    /**
     * Place un ensemble de vaisseaux sur l'hexagone.
     *
     * @param shipsOnHex les vaisseaux à placer sur l'hexagone.
     */
    public void setShipsOnHex(ArrayList<Ship> shipsOnHex) {
        this.shipsOnHex = shipsOnHex;
    }

    /**
     * Ajoute un vaisseau sur l'hexagone.
     *
     * @param shipToAdd le vaisseau à ajouter sur l'hexagone
     */
    public void addShipOnHex(Ship shipToAdd){
        this.shipsOnHex.add(shipToAdd);
    }

    /**
     * Récupère la valeur du Hex.
     *
     * @return La valeur de Hex.
     */
    public int getValue() {
        return value;
    }

    /**
     * Récupère le nombre maximum de vaisseaux autorisés sur ce Hex.
     *
     * @return le nombre maximum de vaisseaux autorisés sur le Hex.
     */
    public int getNbMaxShips() {
        return nbMaxShips;
    }

    /**
     * Vérifie si le hex fait partie du TriPrime.
     *
     * @return true si le hex fait partie du TriPrime, false sinon.
     */
    public boolean isTriPrime() {
        return triPrime;
    }

    /**
     * Vérifie si le hex est un système 1.
     *
     * @return true si le hex est un système 1, false sinon.
     */
    public boolean isSystemHex1() {
        return systemHex1;
    }

    /**
     * Vérifie si le hex est un système 2.
     *
     * @return true si le hex est un système 2, false sinon.
     */
    public boolean isSystemHex2() {
        return systemHex2;
    }


}
