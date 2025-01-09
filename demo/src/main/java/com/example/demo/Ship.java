package com.example.demo;

import javax.swing.*;

public class Ship {

    //Attributs
    private Color couleur;
    private Player owner;
    private int id;
    private int xPosition;
    private int yPosition;

    //Constructeur
    // lorsque le ship n'est pas placé sur un hex, il est en x=-1 y=-1
    public Ship( Player owner, int id) {
        this.owner=owner;
        this.id=id;
        this.xPosition=-1;
        this.yPosition=-1;
        //this.couleur=owner.getCouleur();
    }

    public void setPosition(int x,int y){
        this.xPosition=x;
        this.yPosition=y;
    }
    public void destroyShip(){
        this.xPosition=-1;
        this.yPosition=-1;

    }

    public int getxPosition() {
        return xPosition;
    }

    public int getyPosition() {
        return yPosition;
    }

    public Player getOwner() {
        return owner;
    }

    @Override
    public String toString() {
        return "x=" + xPosition + ", y=" + yPosition + ", couleur=" + couleur + "id=" + id;
    }
}
