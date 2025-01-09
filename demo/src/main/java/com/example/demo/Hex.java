package com.example.demo;

public class Hex {
    private int col;
    private int row;
    private boolean triPrime;
    private boolean systemHex1;
    private boolean systemHex2;

    public Hex(int col, int row, boolean triPrime, boolean systemHex1, boolean systemHex2) {
        this.col = col;
        this.row = row;
        this.triPrime = triPrime;
        this.systemHex1 = systemHex1;
        this.systemHex2 = systemHex2;
    }

    public boolean isTriPrime() {
        return this.triPrime;
    }

    public boolean isSystemHex2() {
        return this.systemHex2;
    }

    public boolean isSystemHex1() {
        return this.systemHex1;
    }

    public int getxPosition() {
        return this.col;
    }

    public int getyPosition() {
        return this.row;
    }

    public int getValue() {
        if (this.triPrime) {
            return 3;
        }else if (this.systemHex1) {
            return 1;
        }else if (this.systemHex2) {
            return 2;
        }else{
            return 0;
        }
    }

    public int getNbMaxShips() {
        if (this.triPrime) {
            return 4;
        }else if (this.systemHex1) {
            return 2;
        }else if (this.systemHex2) {
            return 3;
        }else{
            return 0;
        }
    }
    public String toString() {
        return "Ce hex est x = " + this.col + " y = " + this.row + " triPrime=" + triPrime + " systemHex1=" + systemHex1 + " systemHex2=" + systemHex2;
    }
}