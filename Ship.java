package com.example.demo;
import java.io.*;

/**
 * La classe Ship représente un vaisseau dans le jeu.
 *
 * Un vaisseau possède une position sur une grille, un propriétaire et un état indiquant
 * s'il est actuellement placé sur le plateau de jeu.
 * Quand il n'est pas placé sur le plateau, ses coordonnées sont (-1,-1).
 *
 * @author Anaelle Melo, Daria Avdeeva
 * @version 1.0
 */

public class Ship implements Serializable {
    private static final long serialVersionUID = 1L;

    //Attributs

    /**
     * Propriétaire du vaisseau
     */
    private Player owner;

    /**
     * ID du vaisseau
     */
    private int id;

    /**
     * Position x du vaisseau
     */
    private int xPosition;

    /**
     * Position y du vaisseau
     */
    private int yPosition;

    /**
     * Indique l'état de placement du vaisseau : true si le vaisseau est placé, false sinon
     */
    private boolean placedOnBoard;


    //Constructeur
    /**
     * Instancie un nouveau vaisseau avec un propriétaire donné et un identifiant.
     *
     * Par défaut, le vaisseau est initialisé hors du plateau avec les coordonnées x = -1, y = -1.
     *
     * @param owner le joueur propriétaire du vaisseau
     * @param id l'identifiant unique du vaisseau
     */
    public Ship( Player owner, int id) {
        this.owner=owner;
        this.id=id;
        this.xPosition=-1;
        this.yPosition=-1;
        this.placedOnBoard=false;
    }

    /**
     * Définit la position du vaisseau sur le plateau et le place sur le plateau.
     *
     * @param x la coordonnée x
     * @param y la coordonnée y
     */
    public void setPosition(int x,int y){
        this.xPosition=x;
        this.yPosition=y;
        this.placedOnBoard = true;
    }

    /**
     * Détruit le vaisseau en le retirant du plateau.
     * Les coordonnées sont réinitialisées à (-1,-1).
     */
    public void destroyShip(){
        this.xPosition=-1;
        this.yPosition=-1;
        placedOnBoard=false;
    }

    /**
     * Vérifie si le vaisseau est placé sur le plateau.
     *
     * @return true si le vaisseau est placé, false sinon.
     */
    public boolean isPlaced() {
        return placedOnBoard;
    }

    /**
     * Récupère la coordonnée x du vaisseau.
     *
     * @return La coordonnée x actuelle.
     */
    public int getxPosition() {
        return xPosition;
    }

    /**
     * Récupère la coordonnée y du vaisseau.
     *
     * @return La coordonnée y actuelle.
     */
    public int getyPosition() {
        return yPosition;
    }

    /**
     * Obtient le joueur propriétaire du vaisseau.
     *
     * @return Le joueur propriétaire.
     */
    public Player getOwner() {
        return owner;
    }

    /**
     * Récupère le nom du joueur propriétaire du vaisseau.
     *
     * @return Le nom du propriétaire.
     */
    public String getOwnerName() {
        return owner.getName();
    }

    /**
     * Renvoie une représentation textuelle du vaisseau, incluant son propriétaire et sa position.
     *
     * @return Une chaîne décrivant le vaisseau.
     */
    public String toString(){
        return "Vaisseau de " + this.owner.getName() + " en x = " + xPosition + ", y = " + yPosition + "\n";
    }
}
