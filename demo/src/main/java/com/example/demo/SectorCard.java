package com.example.demo;

import java.util.ArrayList;
import java.util.List;

public class SectorCard {
    private List<Hex> hexes= new ArrayList<>();

    public List<Hex> getHexes() {
        return this.hexes;
    }

    public void addHex(Hex hex) {
        hexes.add(hex);
    }
}
