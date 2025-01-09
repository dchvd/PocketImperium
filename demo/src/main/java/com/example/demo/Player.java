package com.example.demo;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private String name;
    private Color couleur;
    private ArrayList<Ship> ships;
    private ArrayList<Hex> controlledHexs;
    private int id; // NEW


    public List<Ship> getShips() {
        return ships;
    }

    public Player(String r){
        this.ships = new ArrayList<>();
        for (int i=0;i<15; i++) {
            ships.add(new Ship(this, this.id*15+i));
        }
        if (r=="r"){
            this.couleur = Color.RED;
        }else if(r=="g"){
            this.couleur = Color.GREEN;
        }else if(r=="b"){
            this.couleur = Color.BLUE;
        }
    }
    public Color getColor() {
        return this.couleur;
    }

    public void setShip(Hex hex) {
        for(Ship s : this.ships){
            if(s.getxPosition()==-1 && s.getyPosition()==-1){
                s.setPosition(hex.getxPosition(), hex.getyPosition());
                break;
            }
        }
        System.out.println("Vaisseau placé en " + hex.getxPosition()+","+hex.getyPosition());
    }
}
