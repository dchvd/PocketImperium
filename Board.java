package com.example.demo;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.lang.Math;

/**
 * La classe Board représente le plateau de jeu.
 *
 * Le plateau est composé d'une grille 3x3 de cartes secteur (SectorCard) et maintient
 * également une grille d'hexagones pour la gestion des actions expand, exterminate et explore.
 * Le plateau central contient la zone TriPrime et est entouré de cartes secteur placées
 * aléatoirement.
 *
 * @author Anaelle Melo, Daria Avdeeva
 * @version 1.0
 */
public class Board implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * Board est composé des sectorCards, il permettra notamment de compter les scores
     */
    private SectorCard[][] board = new SectorCard[3][3];
    /**
     * gameBoard sera la grille de Hex que l'on utilise pour les fonctions expand, exterminate et explore
     */
    public static List<List<Hex>> gameBoard = new ArrayList<>();

    /**
     * Construit un nouveau plateau de jeu.
     * Initialise la carte centrale, génère et place aléatoirement les cartes
     * extérieures et moyennes-extérieures, puis génère la grille d'hexagones.
     */
    public Board() {
        this.gameBoard.clear();
        // Création des cartes avec leurs points d'ancrage
        SectorCard centralCard = new SectorCard("CentralCard"); // Milieu du plateau
        // Générer les cartes extérieures
        List<SectorCard> middleExteriorCards = new ArrayList<>();
        List<SectorCard> exteriorCards = new ArrayList<>();

        //Génerer les cartes extérieures du milieu
        middleExteriorCards.add(new SectorCard("MiddleExteriorCard"));
        middleExteriorCards.add(new SectorCard("MiddleExteriorCard"));

        for (int i = 0; i < 6; i++) {
            exteriorCards.add(new SectorCard("ExteriorCard"));
        }

        // Mélanger les cartes extérieures pour les placer aléatoirement
        Collections.shuffle(exteriorCards);
        Collections.shuffle(middleExteriorCards);

        // Créer un plateau 3x3

        //Placer la carte centrale
        this.board[1][1] = centralCard;

        //Placer les cartes extérieures au milieu
        //Leur attribuer les noms avec setPlacement avec un for y
        this.board[1][0] = middleExteriorCards.get(0);
        this.board[1][2] = middleExteriorCards.get(1);

        // Placer les cartes extérieures
        this.board[0][0] = exteriorCards.get(0);
        this.board[0][1] = exteriorCards.get(1);
        this.board[0][2] = exteriorCards.get(2);
        this.board[2][0] = exteriorCards.get(3);
        this.board[2][1] = exteriorCards.get(4);
        this.board[2][2] = exteriorCards.get(5);

        for (int i = 0; i < 9; i++) {
            gameBoard.add(new ArrayList<>());
        }

        // Afficher le plateau
        generateGameBoard();
    }

    /**
     * Génère la grille d'hexagones à partir des cartes secteur.
     * Configure les coordonnées de chaque hexagone et gère les cas particuliers
     * pour les différents types de cartes (centrale, extérieure, moyenne-extérieure).
     */
    public void generateGameBoard() {
        List<Hex> hexesCurrentCard= new ArrayList<>();
        int[] colonne = {0,0,0,0,0,0,0,0,0};
        for (int i = 0; i < this.board.length; i++) {
            for (int j = 0; j < this.board[i].length; j++) {
                SectorCard card = this.board[i][j];
                //System.out.println(card);
                if (card != null) {
                    hexesCurrentCard = card.getHexes();
                    if (i==1 && j==1) {
                        hexesCurrentCard.remove(7);
                        hexesCurrentCard.remove(5);
                        hexesCurrentCard.remove(2);
                        hexesCurrentCard.remove(0);
                    }else if(i==1){ //d'après github
                        hexesCurrentCard.remove(7);
                        hexesCurrentCard.remove(2);
                    }
                    else if (i%2==0) {
                        if (j==0 || j==2) {
                            hexesCurrentCard.remove(4);
                            hexesCurrentCard.remove(2);
                        }
                    }
                    Iterator<Hex> iterat = hexesCurrentCard.iterator();
                    int col=0; //Savoir ou on est dans l'itération
                    for (int ibis=0; ibis<3; ibis++) {
                        if (hexesCurrentCard.size()==4) {
                            for (int jbis=0; jbis<2-Math.abs(1-ibis); jbis++) {
                                hexesCurrentCard.get(col).setCoordinates(i*3+ibis, colonne[i*3+ibis]);
                                this.gameBoard.get(i*3+ibis).add(iterat.next());
                                col++;
                                colonne[i*3+ibis]++;
                            }
                        }else if (hexesCurrentCard.size()==5) {
                            for (int jbis=0; jbis<Math.abs(1-ibis)+1; jbis++) {
                                hexesCurrentCard.get(col).setCoordinates(i*3+ibis, colonne[i*3+ibis]);
                                this.gameBoard.get(i*3+ibis).add(iterat.next());
                                col++;
                                colonne[i*3+ibis]++;	                    	}
                        }else if (hexesCurrentCard.size()==6) {
                            for (int jbis=0; jbis<2; jbis++) {
                                hexesCurrentCard.get(col).setCoordinates(i*3+ibis, colonne[i*3+ibis]);
                                this.gameBoard.get(i*3+ibis).add(iterat.next());
                                col++;
                                colonne[i*3+ibis]++;	                    	}
                        }else if (hexesCurrentCard.size()==7) {
                            for (int jbis=0; jbis<-Math.abs(ibis-1)+3; jbis++) {
                                hexesCurrentCard.get(col).setCoordinates(i*3+ibis, colonne[i*3+ibis]);
                                this.gameBoard.get(i*3+ibis).add(iterat.next());
                                col++;
                                colonne[i*3+ibis]++;	                        }
                        }else {
                            System.out.println("Problème dans le nombre de hex dans la carte");
                        }
                    }
                }
            }
        }
    }

    /**
     * Affiche l'état actuel de la grille d'hexagones.
     */
    public void printBoard() {
        System.out.println("Plateau :");
        for (int i = 0; i < this.gameBoard.size(); i++) {
            System.out.println(this.gameBoard.get(i));
        }
    }

    /**
     * Affiche les informations détaillées de toutes les cartes secteur du plateau.
     */
    public void printCards() {
        StringBuilder sb = new StringBuilder();
        sb.append("Cartes:\n");
        for (int i=0; i<board.length; i++) {
            sb.append(" - ").append(i).append("\n");
            for (int j=0; j<board[i].length; j++) {
                sb.append(j).append(" - ").append(board[i][j]).append("\n");
            }
        }
        System.out.println(sb.toString());
    }

    /**
     * Renvoie le tableau des cartes du plateau
     *
     * @return le tableau 2D des cartes secteur
     */
    public SectorCard[][] getBoard(){
        return this.board;
    }

    /**
     * Renvoie le plateau des Hex.
     *
     * @return la liste des listes d'hexagones représentant la grille de jeu
     */
    public List<List<Hex>> getGameBoard(){
        return this.gameBoard;
    }

    /**
     * Vérifie si un hexagone peut être choisi pour une action.
     * Un hexagone doit être un système de niveau 1 et appartenir à une carte
     * qui n'a pas encore été choisie.
     *
     * @param choosedHex l'hexagone à vérifier
     * @param virtual true si la vérification est virtuelle (sans messages), false sinon
     * @return true si l'hexagone peut être choisi, false sinon
     */
    public boolean verifyCapability(Hex choosedHex, boolean virtual) {
        if (choosedHex.isSystemHex1()==false) {
            if(!virtual) {
                System.out.println("Ce hex ne contient pas de système de niveau 1");
            }
            return false;
        }
        for (SectorCard[] rangee: this.board) {
            for (SectorCard card : rangee) {
                if (card.getHexes().contains(choosedHex)) {

                    if (!virtual) {
                        System.out.println("Hex trouvé dans le secteur Carte");
                    }
                    if (!card.getIsAlreadyChosen()){
                        card.isChoosed();
                        return true;
                    }else {
                        return false;
                    }
                }
            }
        }
        System.out.println("Hex non trouvée");
        return false;
    }
}
