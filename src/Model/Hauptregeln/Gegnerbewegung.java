package Model.Hauptregeln;

import Model.Feld;
import Model.Gegenstand;

import java.util.Observable;
import java.util.Observer;

import static Model.Direction.*;
import static Model.Gegenstand.*;


public class Gegnerbewegung implements Observer {
    private Feld[][] map;

    private int vorne;
    private int hinten;
    private int rechteHand;
    private int linkeHand;

    public Gegnerbewegung (Feld[][] map){

        this.map=map;
    }



    //was passiert, wenn ME auf einen Gegner stößt
    private void transformToGem (int x, int y){
        if (checkWallExit(x,y)) {
            map[x][y].setToken(GEM);
            map[x][y].setMoved(1);
            map[x][y].setDirection(NO);
        }
    }

    //Gegner trifft auf ME
    private void bump (int x, int y, Gegenstand gegner){
        int rechts = x+1;
        int links = x-1;
        int oben = y-1;
        int unten = y+1;

        if (checkRowOfTwoTokenHori(x,y,rechts,gegner,ME) && map[x][y].getDirection().equals(RECHTS)){
            transformToGem(x,oben);
            transformToGem(x,y);
            transformToGem(x,unten);
            transformToGem(rechts,oben);
            transformToGem(rechts,y);
            transformToGem(rechts,unten);
            transformToGem(x+2,oben);
            transformToGem(x+2,y);
            transformToGem(x+2,unten);
        }
        else if (checkRowOfTwoTokenHori(x,y,links,gegner,ME) && map[x][y].getDirection().equals(LINKS)){
            transformToGem(x,oben);
            transformToGem(x,y);
            transformToGem(x,unten);
            transformToGem(links,oben);
            transformToGem(links,y);
            transformToGem(links,unten);
            transformToGem(x-2,oben);
            transformToGem(x-2,y);
            transformToGem(x-2,unten);
        }
        if (checkRowOfTwoTokenVerti(x,y,oben,SWAPLING,ME) && map[x][y].getDirection().equals(OBEN)){
            transformToGem(links,y);
            transformToGem(x,y);
            transformToGem(rechts,y);
            transformToGem(links,oben);
            transformToGem(x,oben);
            transformToGem(rechts,oben);
            transformToGem(links,y-2);
            transformToGem(x,y-2);
            transformToGem(rechts,y-2);
        }
        if (checkRowOfTwoTokenVerti(x,y,unten,SWAPLING,ME) && map[x][y].getDirection().equals(UNTEN)){
            transformToGem(links,y);
            transformToGem(x,y);
            transformToGem(rechts,y);
            transformToGem(links,unten);
            transformToGem(x,unten);
            transformToGem(rechts,unten);
            transformToGem(links,y+2);
            transformToGem(x,y+2);
            transformToGem(rechts,y+2);
        }
    }

    //legt vom Xling aus gesehen die Positionen linkeHand, rechteHand, vorne und hinten fest
    private void setEnvironement (int x, int y) {
        if (map[x][y].getDirection().equals(RECHTS)){
            linkeHand=y-1;
            rechteHand=y+1;
            vorne=x+1;
            hinten=x-1;
        }
        else if (map[x][y].getDirection().equals(OBEN)){
            linkeHand=x-1;
            rechteHand=x+1;
            vorne=y-1;
            hinten=y+1;
        }
        else if (map[x][y].getDirection().equals(LINKS)){
            linkeHand=y+1;
            rechteHand=y-1;
            vorne=x-1;
            hinten=x+1;
        }
        else if (map[x][y].getDirection().equals(UNTEN)){
            linkeHand=x+1;
            rechteHand=x-1;
            vorne=y+1;
            hinten=y-1;
        }
    }

    //ALLGEMEINER CHECK: Damit eine Verwandlung beim aufeinandertreffen eines Gegners, mit einem Spieler nicht eine WALL oder ein EXIT verwandelt
    //Checke, ob auf einem Feld eine WALL oder ein EXIT liegt (Später wird diese Methode auf Felder in der Näche einer aktuellen Gegnerposition angewandt)
    private boolean checkWallExit (int x, int y){
        return (!(map[x][y].getToken().equals(WALL) || map[x][y].getToken().equals(EXIT)));
    }


    //SWAPLING CHECKS
    //Vergleiche aktuelle Postion (also die Position des Gegners) mit einer bestimmten Nachbarpostion
    private boolean checkRowOfTwoTokenHori(int x, int y, int richtung, Gegenstand gegner, Gegenstand nachbar){
        return (map[x][y].getToken().equals(gegner) && map[richtung][y].getToken().equals(nachbar));
    }

    private boolean checkRowOfTwoTokenVerti(int x, int y, int richtung, Gegenstand pos, Gegenstand nachbar){
        return (map[x][y].getToken().equals(pos) && map[x][richtung].getToken().equals(nachbar));
    }

    //Checke, ob dem Gegner auf einer Nachbarposition ein Gegenstand (also nicht PATH / ME) im Weg steht
    private boolean checkIfBlockedHori(int x, int y){
        int rechts = x+1;
        return (!(checkRowOfTwoTokenHori(x,y,rechts,SWAPLING,PATH) || checkRowOfTwoTokenHori(x,y,rechts,SWAPLING,ME)));
    }

    private boolean checkIfBlockedVerti(int x, int y){
        int oben = y-1;
        return (!(checkRowOfTwoTokenVerti(x,y,oben,SWAPLING,PATH) || checkRowOfTwoTokenVerti(x,y,oben,SWAPLING,ME)));
    }


    //XLING CHECKS
    //Immer wenn rechteHand kein Path (= hier: Gegenstand) und rechteHand vorne auch ein Gegendstand ist und vorne ein Path, geht Xling nach vorne
    private boolean checkFourWalkForwardXlingHori(int x, int y) {
        return (map[vorne][y].getMoved()==0 && map[x][rechteHand].getMoved()==0 && map[vorne][rechteHand].getMoved()==0 &&

                map[x][y].getToken().equals(XLING) && map[vorne][y].getToken().equals(PATH) &&
                !(map[x][rechteHand].getToken().equals(PATH) || map[x][rechteHand].getToken().equals(ME)) &&
                !(map[vorne][rechteHand].getToken().equals(PATH) || map[vorne][rechteHand].getToken().equals(ME)));
    }

    private boolean checkFourWalkForwardXlingVerti(int x, int y) {
        return (map[x][vorne].getMoved()==0 && map[rechteHand][y].getMoved()==0 && map[rechteHand][vorne].getMoved()==0 &&

                map[x][y].getToken().equals(XLING) && map[x][vorne].getToken().equals(PATH) &&
                !(map[rechteHand][y].getToken().equals(PATH) || map[rechteHand][y].getToken().equals(ME)) &&
                !(map[rechteHand][vorne].getToken().equals(PATH) || map[rechteHand][vorne].getToken().equals(ME)));
    }

    //Wenn rechteHand ein Gegenstand ist, rechteHand vorne und vorne ein Path, geht Xling nach vorne und ändert seine Richtung um 1 im Uhrzeigersinn
    private boolean checkFourWalkForwardAndTurnXlingHori(int x, int y) {
        return (map[vorne][y].getMoved()==0 && map[x][rechteHand].getMoved()==0 && map[vorne][rechteHand].getMoved()==0 &&

                map[x][y].getToken().equals(XLING) && map[vorne][y].getToken().equals(PATH) &&
                !(map[x][rechteHand].getToken().equals(PATH) || map[x][rechteHand].getToken().equals(ME)) &&
                map[vorne][rechteHand].getToken().equals(PATH));
    }

    private boolean checkFourWalkForwardAndTurnXlingVerti(int x, int y) {
        return (map[x][vorne].getMoved()==0 && map[rechteHand][y].getMoved()==0 && map[rechteHand][vorne].getMoved()==0 &&

                map[x][y].getToken().equals(XLING) && map[x][vorne].getToken().equals(PATH) &&
                !(map[rechteHand][y].getToken().equals(PATH) || map[rechteHand][y].getToken().equals(ME)) &&
                map[rechteHand][vorne].getToken().equals(PATH));
    }

    //Wenn rechteHand, vorne und linkeHand vorne ein Gegenstand ist und linkeHand ein Path, geht Xling nach linkeHand und ändert seine Richtung um 1 gegen den Uhrzeigersinn
    private boolean checkSixWalkLeftAndTurnXlingHori(int x, int y) {
        return (map[vorne][y].getMoved()==0 && map[x][rechteHand].getMoved()==0 && map[vorne][linkeHand].getMoved()==0 &&
                map[x][linkeHand].getMoved()==0 &&

                map[x][y].getToken().equals(XLING) &&
                !(map[vorne][y].getToken().equals(PATH) || map[vorne][y].getToken().equals(ME)) &&
                !(map[x][rechteHand].getToken().equals(PATH) || map[x][rechteHand].getToken().equals(ME)) &&
                !(map[vorne][linkeHand].getToken().equals(PATH) || map[vorne][linkeHand].getToken().equals(ME)) &&
                map[x][linkeHand].getToken().equals(PATH));
    }

    private boolean checkSixWalkLeftAndTurnXlingVerti(int x, int y) {
        return (map[x][vorne].getMoved()==0 && map[rechteHand][y].getMoved()==0 && map[linkeHand][vorne].getMoved()==0 &&
                map[linkeHand][x].getMoved()==0 &&

                map[x][y].getToken().equals(XLING) &&
                !(map[x][vorne].getToken().equals(PATH) || map[x][vorne].getToken().equals(ME)) &&
                !(map[rechteHand][y].getToken().equals(PATH) || map[rechteHand][y].getToken().equals(ME)) &&
                !(map[linkeHand][vorne].getToken().equals(PATH) || map[linkeHand][vorne].getToken().equals(ME)) &&
                map[linkeHand][y].getToken().equals(PATH));
    }

    //Wenn rechteHand und vorne ein Gegenstand ist und linkeHand vorne und linkeHand ein Path, geht Xling nach linkeHand (Richtung bleibt)
    private boolean checkSixWalkLeftXlingHori(int x, int y) {
        return (map[vorne][y].getMoved()==0 && map[x][rechteHand].getMoved()==0 && map[vorne][linkeHand].getMoved()==0 &&
                map[x][linkeHand].getMoved()==0 &&

                map[x][y].getToken().equals(XLING) &&
                !(map[vorne][y].getToken().equals(PATH) || map[vorne][y].getToken().equals(ME)) &&
                !(map[x][rechteHand].getToken().equals(PATH) || map[x][rechteHand].getToken().equals(ME)) &&
                map[vorne][linkeHand].getToken().equals(PATH) &&
                map[x][linkeHand].getToken().equals(PATH));
    }

    private boolean checkSixWalkLeftXlingVerti (int x, int y) {
        return (map[x][vorne].getMoved()==0 && map[rechteHand][y].getMoved()==0 && map[linkeHand][vorne].getMoved()==0 &&
                map[linkeHand][x].getMoved()==0 &&

                map[x][y].getToken().equals(XLING) &&
                !(map[x][vorne].getToken().equals(PATH) || map[x][vorne].getToken().equals(ME)) &&
                !(map[rechteHand][y].getToken().equals(PATH) || map[rechteHand][y].getToken().equals(ME)) &&
                map[linkeHand][vorne].getToken().equals(PATH) &&
                map[linkeHand][y].getToken().equals(PATH));
    }

    //Wenn rechteHand, vorne, linkeHand und linkeHand hinten ein Gegenstand ist und hinten ein Path, geht Xling nach hinten und ändert seine Richtung um 2
    private boolean checkNineWalkAndTurnBackXlingHori (int x, int y){
        return (map[vorne][y].getMoved()==0 && map[x][rechteHand].getMoved()==0 && map[x][linkeHand].getMoved()==0 &&
                map[hinten][linkeHand].getMoved()==0 && map[hinten][y].getMoved()==0 &&

                map[x][y].getToken().equals(XLING) &&
                !(map[vorne][y].getToken().equals(PATH) || map[vorne][y].getToken().equals(ME)) &&
                !(map[x][rechteHand].getToken().equals(PATH) || map[x][rechteHand].getToken().equals(ME)) &&
                !(map[x][linkeHand].getToken().equals(PATH) || map[x][linkeHand].getToken().equals(ME)) &&
                !(map[hinten][linkeHand].getToken().equals(PATH) || map[hinten][linkeHand].getToken().equals(ME)) &&
                map[hinten][y].getToken().equals(PATH));
    }

    private boolean checkNineWalkAndTurnBackXlingVerti (int x, int y){
        return (map[x][vorne].getMoved()==0 && map[rechteHand][y].getMoved()==0 && map[linkeHand][y].getMoved()==0 &&
                map[linkeHand][hinten].getMoved()==0 && map[x][hinten].getMoved()==0 &&

                map[x][y].getToken().equals(XLING) &&
                !(map[y][vorne].getToken().equals(PATH) || map[y][vorne].getToken().equals(ME)) &&
                !(map[rechteHand][y].getToken().equals(PATH) || map[rechteHand][y].getToken().equals(ME)) &&
                !(map[linkeHand][y].getToken().equals(PATH) || map[linkeHand][y].getToken().equals(ME)) &&
                !(map[linkeHand][hinten].getToken().equals(PATH) || map[linkeHand][hinten].getToken().equals(ME)) &&
                map[x][hinten].getToken().equals(PATH));
    }

    //Wenn rechteHand, vorne und linkeHand ein Gegenstand ist und hinten linkeHand und hinten ein Path, geht Xling nach hinten und ändert seine Richtung um 1 gegen den Uhrzeigersinn
    private boolean checkNineWalkBackAndTurnXlingHori (int x, int y){
        return (map[vorne][y].getMoved()==0 && map[x][rechteHand].getMoved()==0 && map[x][linkeHand].getMoved()==0 &&
                map[hinten][linkeHand].getMoved()==0 && map[hinten][y].getMoved()==0 &&

                map[x][y].getToken().equals(XLING) &&
                !(map[vorne][y].getToken().equals(PATH) || map[vorne][y].getToken().equals(ME)) &&
                !(map[x][rechteHand].getToken().equals(PATH) || map[x][rechteHand].getToken().equals(ME)) &&
                !(map[x][linkeHand].getToken().equals(PATH) || map[x][linkeHand].getToken().equals(ME)) &&
                map[hinten][linkeHand].getToken().equals(PATH) &&
                map[hinten][y].getToken().equals(PATH));
    }

    private boolean checkNineWalkBackAndTurnXlingVerti (int x, int y){
        return (map[x][vorne].getMoved()==0 && map[rechteHand][y].getMoved()==0 && map[linkeHand][y].getMoved()==0 &&
                map[linkeHand][hinten].getMoved()==0 && map[x][hinten].getMoved()==0 &&

                map[x][y].getToken().equals(XLING) &&
                !(map[y][vorne].getToken().equals(PATH) || map[y][vorne].getToken().equals(ME)) &&
                !(map[rechteHand][y].getToken().equals(PATH) || map[rechteHand][y].getToken().equals(ME)) &&
                !(map[linkeHand][y].getToken().equals(PATH) || map[linkeHand][y].getToken().equals(ME)) &&
                map[linkeHand][hinten].getToken().equals(PATH) &&
                map[x][hinten].getToken().equals(PATH));
    }

    //Wenn rechteHand, vorne, linkeHand und hinten ein Gegenstand ist, ändert Xling seine Richtung um 1 gegen den Uhrzeigersinn
    private boolean checkNineBlockedXlingHori(int x, int y){
        return (map[vorne][y].getMoved()==0 && map[x][rechteHand].getMoved()==0 && map[x][linkeHand].getMoved()==0 &&
                map[hinten][y].getMoved()==0 &&

                map[x][y].getToken().equals(XLING) &&
                !(map[vorne][y].getToken().equals(PATH) || map[vorne][y].getToken().equals(ME)) &&
                !(map[x][rechteHand].getToken().equals(PATH) || map[x][rechteHand].getToken().equals(ME)) &&
                !(map[x][linkeHand].getToken().equals(PATH) || map[x][linkeHand].getToken().equals(ME)) &&
                !(map[hinten][y].getToken().equals(PATH) || map[hinten][y].getToken().equals(ME)));
    }

    private boolean checkNineBlockedXlingVerti(int x, int y){
        return (map[x][vorne].getMoved()==0 && map[rechteHand][y].getMoved()==0 && map[linkeHand][y].getMoved()==0 &&
                map[x][hinten].getMoved()==0 &&

                map[x][y].getToken().equals(XLING) &&
                !(map[y][vorne].getToken().equals(PATH) || map[y][vorne].getToken().equals(ME)) &&
                !(map[rechteHand][y].getToken().equals(PATH) || map[rechteHand][y].getToken().equals(ME)) &&
                !(map[linkeHand][y].getToken().equals(PATH) || map[linkeHand][y].getToken().equals(ME)) &&
                !(map[x][hinten].getToken().equals(PATH) || map[x][hinten].getToken().equals(ME)));
    }


    //BLOCKLING CHECKS
    //Immer wenn linkeHand kein Path (= hier: Gegenstand) und linkeHand vorne auch ein Gegendstand ist und vorne ein Path, geht Xling nach vorne
    private boolean checkFourWalkForwardBlockHori(int x, int y) {
        return (map[vorne][y].getMoved()==0 && map[x][linkeHand].getMoved()==0 && map[vorne][linkeHand].getMoved()==0 &&

                map[x][y].getToken().equals(XLING) && map[vorne][y].getToken().equals(PATH) &&
                !(map[x][linkeHand].getToken().equals(PATH) || map[x][linkeHand].getToken().equals(ME)) &&
                !(map[vorne][linkeHand].getToken().equals(PATH) || map[vorne][linkeHand].getToken().equals(ME)));
    }

    private boolean checkFourWalkForwardBlockVerti(int x, int y) {
        return (map[x][vorne].getMoved()==0 && map[linkeHand][y].getMoved()==0 && map[linkeHand][vorne].getMoved()==0 &&

                map[x][y].getToken().equals(XLING) && map[x][vorne].getToken().equals(PATH) &&
                !(map[linkeHand][y].getToken().equals(PATH) || map[linkeHand][y].getToken().equals(ME)) &&
                !(map[linkeHand][vorne].getToken().equals(PATH) || map[linkeHand][vorne].getToken().equals(ME)));
    }

    //Wenn linkeHand ein Gegenstand ist, linkeHand vorne und vorne ein Path, geht Xling nach vorne und ändert seine Richtung um 1 gegen den Uhrzeigersinn
    private boolean checkFourWalkForwardAndTurnBlockHori(int x, int y) {
        return (map[vorne][y].getMoved()==0 && map[x][linkeHand].getMoved()==0 && map[vorne][linkeHand].getMoved()==0 &&

                map[x][y].getToken().equals(XLING) && map[vorne][y].getToken().equals(PATH) &&
                !(map[x][linkeHand].getToken().equals(PATH) || map[x][linkeHand].getToken().equals(ME)) &&
                map[vorne][linkeHand].getToken().equals(PATH));
    }

    private boolean checkFourWalkForwardAndTurnBlockVerti(int x, int y) {
        return (map[x][vorne].getMoved()==0 && map[linkeHand][y].getMoved()==0 && map[linkeHand][vorne].getMoved()==0 &&

                map[x][y].getToken().equals(XLING) && map[x][vorne].getToken().equals(PATH) &&
                !(map[linkeHand][y].getToken().equals(PATH) || map[linkeHand][y].getToken().equals(ME)) &&
                map[linkeHand][vorne].getToken().equals(PATH));
    }

    //Wenn linkeHand, vorne und rechteHand vorne ein Gegenstand ist und rechteHand ein Path, geht Xling nach rechteHand und ändert seine Richtung um 1 im Uhrzeigersinn
    private boolean checkSixWalkRightAndTurnBlockHori(int x, int y) {
        return (map[vorne][y].getMoved()==0 && map[x][linkeHand].getMoved()==0 && map[vorne][rechteHand].getMoved()==0 &&
                map[x][rechteHand].getMoved()==0 &&

                map[x][y].getToken().equals(XLING) &&
                !(map[vorne][y].getToken().equals(PATH) || map[vorne][y].getToken().equals(ME)) &&
                !(map[x][linkeHand].getToken().equals(PATH) || map[x][linkeHand].getToken().equals(ME)) &&
                !(map[vorne][rechteHand].getToken().equals(PATH) || map[vorne][rechteHand].getToken().equals(ME)) &&
                map[x][rechteHand].getToken().equals(PATH));
    }

    private boolean checkSixWalkRightAndTurnBlockVerti(int x, int y) {
        return (map[x][vorne].getMoved()==0 && map[linkeHand][y].getMoved()==0 && map[rechteHand][vorne].getMoved()==0 &&
                map[rechteHand][x].getMoved()==0 &&

                map[x][y].getToken().equals(XLING) &&
                !(map[x][vorne].getToken().equals(PATH) || map[x][vorne].getToken().equals(ME)) &&
                !(map[linkeHand][y].getToken().equals(PATH) || map[linkeHand][y].getToken().equals(ME)) &&
                !(map[rechteHand][vorne].getToken().equals(PATH) || map[rechteHand][vorne].getToken().equals(ME)) &&
                map[rechteHand][y].getToken().equals(PATH));
    }

    //Wenn linkeHand und vorne ein Gegenstand ist und rechteHand vorne und rechteHand ein Path, geht Xling nach rechteHand (Richtung bleibt)
    private boolean checkSixWalkRightBlockHori(int x, int y) {
        return (map[vorne][y].getMoved()==0 && map[x][linkeHand].getMoved()==0 && map[vorne][rechteHand].getMoved()==0 &&
                map[x][rechteHand].getMoved()==0 &&

                map[x][y].getToken().equals(XLING) &&
                !(map[vorne][y].getToken().equals(PATH) || map[vorne][y].getToken().equals(ME)) &&
                !(map[x][linkeHand].getToken().equals(PATH) || map[x][linkeHand].getToken().equals(ME)) &&
                map[vorne][rechteHand].getToken().equals(PATH) &&
                map[x][rechteHand].getToken().equals(PATH));
    }

    private boolean checkSixWalkRightBlockVerti (int x, int y) {
        return (map[x][vorne].getMoved()==0 && map[linkeHand][y].getMoved()==0 && map[rechteHand][vorne].getMoved()==0 &&
                map[rechteHand][x].getMoved()==0 &&

                map[x][y].getToken().equals(XLING) &&
                !(map[x][vorne].getToken().equals(PATH) || map[x][vorne].getToken().equals(ME)) &&
                !(map[linkeHand][y].getToken().equals(PATH) || map[linkeHand][y].getToken().equals(ME)) &&
                map[rechteHand][vorne].getToken().equals(PATH) &&
                map[rechteHand][y].getToken().equals(PATH));
    }

    //Wenn linkeHand, vorne, rechteHand und rechteHand hinten ein Gegenstand ist und hinten ein Path, geht Xling nach hinten und ändert seine Richtung um 2
    private boolean checkNineWalkAndTurnBackBlockHori (int x, int y){
        return (map[vorne][y].getMoved()==0 && map[x][linkeHand].getMoved()==0 && map[x][rechteHand].getMoved()==0 &&
                map[hinten][rechteHand].getMoved()==0 && map[hinten][y].getMoved()==0 &&

                map[x][y].getToken().equals(XLING) &&
                !(map[vorne][y].getToken().equals(PATH) || map[vorne][y].getToken().equals(ME)) &&
                !(map[x][linkeHand].getToken().equals(PATH) || map[x][linkeHand].getToken().equals(ME)) &&
                !(map[x][rechteHand].getToken().equals(PATH) || map[x][rechteHand].getToken().equals(ME)) &&
                !(map[hinten][rechteHand].getToken().equals(PATH) || map[hinten][rechteHand].getToken().equals(ME)) &&
                map[hinten][y].getToken().equals(PATH));
    }

    private boolean checkNineWalkAndTurnBackBlockVerti (int x, int y){
        return (map[x][vorne].getMoved()==0 && map[linkeHand][y].getMoved()==0 && map[rechteHand][y].getMoved()==0 &&
                map[rechteHand][hinten].getMoved()==0 && map[x][hinten].getMoved()==0 &&

                map[x][y].getToken().equals(XLING) &&
                !(map[y][vorne].getToken().equals(PATH) || map[y][vorne].getToken().equals(ME)) &&
                !(map[linkeHand][y].getToken().equals(PATH) || map[linkeHand][y].getToken().equals(ME)) &&
                !(map[rechteHand][y].getToken().equals(PATH) || map[rechteHand][y].getToken().equals(ME)) &&
                !(map[rechteHand][hinten].getToken().equals(PATH) || map[rechteHand][hinten].getToken().equals(ME)) &&
                map[x][hinten].getToken().equals(PATH));
    }

    //Wenn linkeHand, vorne und rechteHand ein Gegenstand ist und hinten rechteHand und hinten ein Path, geht Xling nach hinten und ändert seine Richtung um 1 im Uhrzeigersinn
    private boolean checkNineWalkBackAndTurnBlockHori (int x, int y){
        return (map[vorne][y].getMoved()==0 && map[x][linkeHand].getMoved()==0 && map[x][rechteHand].getMoved()==0 &&
                map[hinten][rechteHand].getMoved()==0 && map[hinten][y].getMoved()==0 &&

                map[x][y].getToken().equals(XLING) &&
                !(map[vorne][y].getToken().equals(PATH) || map[vorne][y].getToken().equals(ME)) &&
                !(map[x][linkeHand].getToken().equals(PATH) || map[x][linkeHand].getToken().equals(ME)) &&
                !(map[x][rechteHand].getToken().equals(PATH) || map[x][rechteHand].getToken().equals(ME)) &&
                map[hinten][rechteHand].getToken().equals(PATH) &&
                map[hinten][y].getToken().equals(PATH));
    }

    private boolean checkNineWalkBackAndTurnBlockVerti (int x, int y){
        return (map[x][vorne].getMoved()==0 && map[linkeHand][y].getMoved()==0 && map[rechteHand][y].getMoved()==0 &&
                map[rechteHand][hinten].getMoved()==0 && map[x][hinten].getMoved()==0 &&

                map[x][y].getToken().equals(XLING) &&
                !(map[y][vorne].getToken().equals(PATH) || map[y][vorne].getToken().equals(ME)) &&
                !(map[linkeHand][y].getToken().equals(PATH) || map[linkeHand][y].getToken().equals(ME)) &&
                !(map[rechteHand][y].getToken().equals(PATH) || map[rechteHand][y].getToken().equals(ME)) &&
                map[rechteHand][hinten].getToken().equals(PATH) &&
                map[x][hinten].getToken().equals(PATH));
    }

    //Wenn linkeHand, vorne, rechteHand und hinten ein Gegenstand ist, ändert Xling seine Richtung um 1 im Uhrzeigersinn
    private boolean checkNineBlockedBlockHori (int x, int y){
        return (map[vorne][y].getMoved()==0 && map[x][linkeHand].getMoved()==0 && map[x][rechteHand].getMoved()==0 &&
                map[hinten][y].getMoved()==0 &&

                map[x][y].getToken().equals(XLING) &&
                !(map[vorne][y].getToken().equals(PATH) || map[vorne][y].getToken().equals(ME)) &&
                !(map[x][linkeHand].getToken().equals(PATH) || map[x][linkeHand].getToken().equals(ME)) &&
                !(map[x][rechteHand].getToken().equals(PATH) || map[x][rechteHand].getToken().equals(ME)) &&
                !(map[hinten][y].getToken().equals(PATH) || map[hinten][y].getToken().equals(ME)));
    }

    private boolean checkNineBlockedBlockVerti (int x, int y){
        return (map[x][vorne].getMoved()==0 && map[linkeHand][y].getMoved()==0 && map[rechteHand][y].getMoved()==0 &&
                map[x][hinten].getMoved()==0 &&

                map[x][y].getToken().equals(XLING) &&
                !(map[y][vorne].getToken().equals(PATH) || map[y][vorne].getToken().equals(ME)) &&
                !(map[linkeHand][y].getToken().equals(PATH) || map[linkeHand][y].getToken().equals(ME)) &&
                !(map[rechteHand][y].getToken().equals(PATH) || map[rechteHand][y].getToken().equals(ME)) &&
                !(map[x][hinten].getToken().equals(PATH) || map[x][hinten].getToken().equals(ME)));
    }



    public void swapling (int x, int y) {
        int rechts = x+1;
        int links = x-1;
        int oben = y-1;
        int unten = y+1;
        if (map[x][y].getDirection().equals(RECHTS)) {
            if (checkRowOfTwoTokenHori(x, y, rechts, SWAPLING, PATH)) {
                map[x][y].setToken(PATH);
                map[x][y].setMoved(1);
                map[x][y].setDirection(NO);
                map[rechts][y].setToken(SWAPLING);
                map[rechts][y].setMoved(1);
                map[rechts][y].setDirection(RECHTS);
            }
            else if (checkIfBlockedHori(x, y)) {
                map[x][y].setDirection(LINKS);
                map[x][y].setMoved(1);
            }
        }

        else if (map[x][y].getDirection().equals(OBEN)) {
            if (checkRowOfTwoTokenVerti(x, y, oben, SWAPLING, PATH)) {
                map[x][y].setToken(PATH);
                map[x][y].setMoved(1);
                map[x][y].setDirection(NO);
                map[oben][y].setToken(SWAPLING);
                map[oben][y].setMoved(1);
                map[oben][y].setDirection(OBEN);
            }
            else if (checkIfBlockedVerti(x, y)) {
                map[x][y].setDirection(UNTEN);
                map[x][y].setMoved(1);
            }
        }

        else if (map[x][y].getDirection().equals(LINKS)) {
            if (checkRowOfTwoTokenHori(x, y, links, SWAPLING, PATH)) {
                map[x][y].setToken(PATH);
                map[x][y].setMoved(1);
                map[x][y].setDirection(NO);
                map[links][y].setToken(SWAPLING);
                map[links][y].setMoved(1);
                map[links][y].setDirection(LINKS);
            }
            else if (checkIfBlockedHori(x, y)) {
                map[x][y].setDirection(RECHTS);
                map[x][y].setMoved(1);
            }
        }

        else if (map[x][y].getDirection().equals(UNTEN)) {
            if (checkRowOfTwoTokenVerti(x, y, unten, SWAPLING, PATH)) {
                map[x][y].setToken(PATH);
                map[x][y].setMoved(1);
                map[x][y].setDirection(NO);
                map[unten][y].setToken(SWAPLING);
                map[unten][y].setMoved(1);
                map[unten][y].setDirection(UNTEN);
            }
            else if (checkIfBlockedVerti(x, y)) {
                map[x][y].setDirection(OBEN);
                map[x][y].setMoved(1);
            }
        }

        bump(x,y,SWAPLING);
    }

    public void xling (int x, int y) {
        setEnvironement(x, y);
        if (map[x][y].getDirection().equals(RECHTS) || map[x][y].getDirection().equals(LINKS)) {
            if (checkNineBlockedXlingHori(x, y)) {
                map[x][y].setMoved(1);
                if (map[x][y].getDirection().equals(RECHTS)) {
                    map[x][y].setDirection(OBEN);
                } else {
                    map[x][y].setDirection(UNTEN);
                }
            } else {
                if (checkFourWalkForwardXlingHori(x, y)) {
                    map[vorne][y].setToken(XLING);
                    map[vorne][y].setMoved(1);
                    map[vorne][y].setDirection(map[x][y].getDirection());

                } else if (checkFourWalkForwardAndTurnXlingHori(x, y)) {
                    map[vorne][y].setToken(XLING);
                    map[vorne][y].setMoved(1);
                    if (map[x][y].getDirection().equals(RECHTS)) {
                        map[vorne][y].setDirection(UNTEN);
                    } else {
                        map[vorne][y].setDirection(OBEN);
                    }

                } else if (checkSixWalkLeftAndTurnXlingHori(x, y)) {
                    map[x][linkeHand].setToken(XLING);
                    map[x][linkeHand].setMoved(1);
                    if (map[x][y].getDirection().equals(RECHTS)) {
                        map[x][linkeHand].setDirection(OBEN);
                    } else {
                        map[x][linkeHand].setDirection(UNTEN);
                    }

                } else if (checkSixWalkLeftXlingHori(x, y)) {
                    map[x][linkeHand].setToken(XLING);
                    map[x][linkeHand].setMoved(1);
                    map[x][linkeHand].setDirection(map[x][y].getDirection());

                } else if (checkNineWalkAndTurnBackXlingHori(x, y)) {
                    map[hinten][y].setToken(XLING);
                    map[hinten][y].setMoved(1);
                    if (map[x][y].getDirection().equals(RECHTS)) {
                        map[hinten][y].setDirection(LINKS);
                    } else {
                        map[hinten][y].setDirection(RECHTS);
                    }

                } else if (checkNineWalkBackAndTurnXlingHori(x, y)) {
                    map[hinten][y].setToken(XLING);
                    map[hinten][y].setMoved(1);
                    if (map[x][y].getDirection().equals(RECHTS)) {
                        map[hinten][y].setDirection(OBEN);
                    } else {
                        map[hinten][y].setDirection(UNTEN);
                    }
                }
                map[x][y].setToken(PATH);
                map[x][y].setMoved(1);
                map[x][y].setDirection(NO);
            }

        } else if (map[x][y].getDirection().equals(OBEN) || map[x][y].getDirection().equals(UNTEN)) {
            if (checkNineBlockedXlingVerti(x, y)) {
                map[x][y].setMoved(1);
                if (map[x][y].getDirection().equals(UNTEN)) {
                    map[x][y].setDirection(RECHTS);
                } else {
                    map[x][y].setDirection(LINKS);
                }
            } else {
                if (checkFourWalkForwardXlingVerti(x, y)) {
                    map[x][vorne].setToken(XLING);
                    map[x][vorne].setMoved(1);
                    map[x][vorne].setDirection(map[x][y].getDirection());

                } else if (checkFourWalkForwardAndTurnXlingVerti(x, y)) {
                    map[x][vorne].setToken(XLING);
                    map[x][vorne].setMoved(1);
                    if (map[x][y].getDirection().equals(OBEN)) {
                        map[x][vorne].setDirection(RECHTS);
                    } else {
                        map[vorne][y].setDirection(LINKS);
                    }

                } else if (checkSixWalkLeftAndTurnXlingVerti(x, y)) {
                    map[linkeHand][y].setToken(XLING);
                    map[linkeHand][y].setMoved(1);
                    if (map[x][y].getDirection().equals(OBEN)) {
                        map[linkeHand][y].setDirection(LINKS);
                    } else {
                        map[linkeHand][y].setDirection(RECHTS);
                    }

                } else if (checkSixWalkLeftXlingVerti(x, y)) {
                    map[linkeHand][y].setToken(XLING);
                    map[linkeHand][y].setMoved(1);
                    map[linkeHand][y].setDirection(map[x][y].getDirection());
                }
                else if (checkNineWalkAndTurnBackXlingVerti(x,y)){
                    map[x][hinten].setToken(XLING);
                    map[x][hinten].setMoved(1);
                    if (map[x][y].getDirection().equals(OBEN)) {
                        map[x][hinten].setDirection(UNTEN);
                    }
                    else {
                        map[x][hinten].setDirection(OBEN);
                    }
                }

                else if (checkNineWalkBackAndTurnXlingVerti(x,y)){
                    map[x][hinten].setToken(XLING);
                    map[x][hinten].setMoved(1);
                    if (map[x][y].getDirection().equals(OBEN)) {
                        map[x][hinten].setDirection(LINKS);
                    }
                    else {
                        map[x][hinten].setDirection(RECHTS);
                    }
                }
                map[x][y].setToken(PATH);
                map[x][y].setMoved(1);
                map[x][y].setDirection(NO);
            }
        }
        bump(x,y,XLING);
    }

    public void blockling (int x, int y) {
        setEnvironement(x, y);
        if (map[x][y].getDirection().equals(LINKS) || map[x][y].getDirection().equals(RECHTS)) {
            if (checkNineBlockedBlockHori(x, y)) {
                map[x][y].setMoved(1);
                if (map[x][y].getDirection().equals(LINKS)) {
                    map[x][y].setDirection(OBEN);
                } else {
                    map[x][y].setDirection(UNTEN);
                }
            } else {
                if (checkFourWalkForwardBlockHori(x, y)) {
                    map[vorne][y].setToken(BLOCKLING);
                    map[vorne][y].setMoved(1);
                    map[vorne][y].setDirection(map[x][y].getDirection());

                } else if (checkFourWalkForwardAndTurnBlockHori(x, y)) {
                    map[vorne][y].setToken(BLOCKLING);
                    map[vorne][y].setMoved(1);
                    if (map[x][y].getDirection().equals(LINKS)) {
                        map[vorne][y].setDirection(UNTEN);
                    } else {
                        map[vorne][y].setDirection(OBEN);
                    }

                } else if (checkSixWalkRightAndTurnBlockHori(x, y)) {
                    map[x][rechteHand].setToken(BLOCKLING);
                    map[x][rechteHand].setMoved(1);
                    if (map[x][y].getDirection().equals(LINKS)) {
                        map[x][rechteHand].setDirection(OBEN);
                    } else {
                        map[x][rechteHand].setDirection(UNTEN);
                    }

                } else if (checkSixWalkRightBlockHori(x, y)) {
                    map[x][rechteHand].setToken(BLOCKLING);
                    map[x][rechteHand].setMoved(1);
                    map[x][rechteHand].setDirection(map[x][y].getDirection());

                } else if (checkNineWalkAndTurnBackBlockHori(x, y)) {
                    map[hinten][y].setToken(BLOCKLING);
                    map[hinten][y].setMoved(1);
                    if (map[x][y].getDirection().equals(LINKS)) {
                        map[hinten][y].setDirection(RECHTS);
                    } else {
                        map[hinten][y].setDirection(LINKS);
                    }

                } else if (checkNineWalkBackAndTurnBlockHori(x, y)) {
                    map[hinten][y].setToken(BLOCKLING);
                    map[hinten][y].setMoved(1);
                    if (map[x][y].getDirection().equals(LINKS)) {
                        map[hinten][y].setDirection(OBEN);
                    } else {
                        map[hinten][y].setDirection(UNTEN);
                    }
                }
                map[x][y].setToken(PATH);
                map[x][y].setMoved(1);
                map[x][y].setDirection(NO);
            }

        } else if (map[x][y].getDirection().equals(OBEN) || map[x][y].getDirection().equals(UNTEN)) {
            if (checkNineBlockedBlockVerti(x, y)) {
                map[x][y].setMoved(1);
                if (map[x][y].getDirection().equals(UNTEN)) {
                    map[x][y].setDirection(LINKS);
                } else {
                    map[x][y].setDirection(RECHTS);
                }
            } else {
                if (checkFourWalkForwardBlockVerti(x, y)) {
                    map[x][vorne].setToken(BLOCKLING);
                    map[x][vorne].setMoved(1);
                    map[x][vorne].setDirection(map[x][y].getDirection());

                } else if (checkFourWalkForwardAndTurnBlockVerti(x, y)) {
                    map[x][vorne].setToken(BLOCKLING);
                    map[x][vorne].setMoved(1);
                    if (map[x][y].getDirection().equals(OBEN)) {
                        map[x][vorne].setDirection(LINKS);
                    } else {
                        map[vorne][y].setDirection(RECHTS);
                    }

                } else if (checkSixWalkRightAndTurnBlockVerti(x, y)) {
                    map[rechteHand][y].setToken(BLOCKLING);
                    map[rechteHand][y].setMoved(1);
                    if (map[x][y].getDirection().equals(OBEN)) {
                        map[rechteHand][y].setDirection(RECHTS);
                    } else {
                        map[rechteHand][y].setDirection(LINKS);
                    }

                } else if (checkSixWalkRightBlockVerti(x, y)) {
                    map[rechteHand][y].setToken(BLOCKLING);
                    map[rechteHand][y].setMoved(1);
                    map[rechteHand][y].setDirection(map[x][y].getDirection());
                }
                else if (checkNineWalkAndTurnBackBlockVerti(x,y)){
                    map[x][hinten].setToken(BLOCKLING);
                    map[x][hinten].setMoved(1);
                    if (map[x][y].getDirection().equals(OBEN)) {
                        map[x][hinten].setDirection(UNTEN);
                    }
                    else {
                        map[x][hinten].setDirection(OBEN);
                    }
                }

                else if (checkNineWalkBackAndTurnBlockVerti(x,y)){
                    map[x][hinten].setToken(BLOCKLING);
                    map[x][hinten].setMoved(1);
                    if (map[x][y].getDirection().equals(OBEN)) {
                        map[x][hinten].setDirection(RECHTS);
                    }
                    else {
                        map[x][hinten].setDirection(LINKS);
                    }
                }
                map[x][y].setToken(PATH);
                map[x][y].setMoved(1);
                map[x][y].setDirection(NO);
            }
        }
        bump(x,y,BLOCKLING);
    }


    public void update(Observable o, Object arg) {}

}
