package Model.Hauptregeln;

import Model.Feld;
import Model.Gegenstand;
import Model.LevelModel;

import java.util.Observable;
import java.util.Observer;
import static Model.Gegenstand.*;


public class Gegnerbewegung implements Observer {
    LevelModel levelModel;
    private Feld[][] map;
    private int x;
    private int y;
    private int rechts;
    private int links;
    private int oben;
    private int unten;
    private int w;
    private int h;

    public Gegnerbewegung (LevelModel levelModel, int x, int y){
        this.levelModel=levelModel;
        this.x=x;
        this.y=y;
        rechts = x+1;
        links = x-1;
        oben = y-1;
        unten = y+1;
        this.map=levelModel.getMap();
        this.w = levelModel.width;
        this.h = levelModel.height;
    }

    private boolean checkRowOfTwoTokenHori(int x, int y, int richtung, Gegenstand gegner, Gegenstand nachbar){
        return (map[x][y].getToken().equals(gegner) && map[richtung][y].getToken().equals(nachbar));
    }

    private boolean checkRowOfTwoTokenVerti(int x, int y, int richtung, Gegenstand pos, Gegenstand nachbar){
        return (map[x][y].getToken().equals(pos) && map[x][richtung].getToken().equals(nachbar));
    }

    private boolean checkIfBlockedHori(int x, int y){
        return (checkRowOfTwoTokenHori(x,y,rechts,SWAPLING,MUD) || checkRowOfTwoTokenHori(x,y,rechts,SWAPLING,STONE) ||
                checkRowOfTwoTokenHori(x,y,rechts,SWAPLING,GEM) || checkRowOfTwoTokenHori(x,y,rechts,SWAPLING,EXIT) ||
                checkRowOfTwoTokenHori(x,y,rechts,SWAPLING,WALL) || checkRowOfTwoTokenHori(x,y,rechts,SWAPLING,BRICKS) ||
                checkRowOfTwoTokenHori(x,y,rechts,SWAPLING,EXPLOSION) || checkRowOfTwoTokenHori(x,y,rechts,SWAPLING,SLIME) ||
                checkRowOfTwoTokenHori(x,y,rechts,SWAPLING,SWAPLING) || checkRowOfTwoTokenHori(x,y,rechts,SWAPLING,BLOCKLING) ||
                checkRowOfTwoTokenHori(x,y,rechts,SWAPLING,XLING) || checkRowOfTwoTokenHori(x,y,rechts,SWAPLING,GHOSTLING) ||
                checkRowOfTwoTokenHori(x,y,rechts,SWAPLING,FIRE) || checkRowOfTwoTokenHori(x,y,rechts,SWAPLING,NORTHTHING) ||
                checkRowOfTwoTokenHori(x,y,rechts,SWAPLING,EASTTHING) || checkRowOfTwoTokenHori(x,y,rechts,SWAPLING,SOUTHTHING) ||
                checkRowOfTwoTokenHori(x,y,rechts,SWAPLING,WESTTHING) || checkRowOfTwoTokenHori(x,y,rechts,SWAPLING,POT) ||
                checkRowOfTwoTokenHori(x,y,rechts,SWAPLING,SIEVE) || checkRowOfTwoTokenHori(x,y,rechts,SWAPLING,SAND));
    }

    private boolean checkIfBlockedVerti(int x, int y){
        return (checkRowOfTwoTokenVerti(x,y,rechts,SWAPLING,MUD) || checkRowOfTwoTokenVerti(x,y,rechts,SWAPLING,STONE) ||
                checkRowOfTwoTokenVerti(x,y,rechts,SWAPLING,GEM) || checkRowOfTwoTokenVerti(x,y,rechts,SWAPLING,EXIT) ||
                checkRowOfTwoTokenVerti(x,y,rechts,SWAPLING,WALL) || checkRowOfTwoTokenVerti(x,y,rechts,SWAPLING,BRICKS) ||
                checkRowOfTwoTokenVerti(x,y,rechts,SWAPLING,EXPLOSION) || checkRowOfTwoTokenVerti(x,y,rechts,SWAPLING,SLIME) ||
                checkRowOfTwoTokenVerti(x,y,rechts,SWAPLING,SWAPLING) || checkRowOfTwoTokenVerti(x,y,rechts,SWAPLING,BLOCKLING) ||
                checkRowOfTwoTokenVerti(x,y,rechts,SWAPLING,XLING) || checkRowOfTwoTokenVerti(x,y,rechts,SWAPLING,GHOSTLING) ||
                checkRowOfTwoTokenVerti(x,y,rechts,SWAPLING,FIRE) || checkRowOfTwoTokenVerti(x,y,rechts,SWAPLING,NORTHTHING) ||
                checkRowOfTwoTokenVerti(x,y,rechts,SWAPLING,EASTTHING) || checkRowOfTwoTokenVerti(x,y,rechts,SWAPLING,SOUTHTHING) ||
                checkRowOfTwoTokenVerti(x,y,rechts,SWAPLING,WESTTHING) || checkRowOfTwoTokenVerti(x,y,rechts,SWAPLING,POT) ||
                checkRowOfTwoTokenVerti(x,y,rechts,SWAPLING,SIEVE) || checkRowOfTwoTokenVerti(x,y,rechts,SWAPLING,SAND));
    }

    private boolean checkWallExit (int x, int y){
        return (map[x][y].getToken().equals(ME) || map[x][y].getToken().equals(MUD) || map[x][y].getToken().equals(STONE)) ||
                map[x][y].getToken().equals(GEM) || map[x][y].getToken().equals(BRICKS) || map[x][y].getToken().equals(PATH) ||
                map[x][y].getToken().equals(EXPLOSION) || map[x][y].getToken().equals(SLIME) || map[x][y].getToken().equals(SWAPLING) ||
                map[x][y].getToken().equals(BLOCKLING) || map[x][y].getToken().equals(XLING) || map[x][y].getToken().equals(GHOSTLING) ||
                map[x][y].getToken().equals(FIRE) || map[x][y].getToken().equals(NORTHTHING) || map[x][y].getToken().equals(EASTTHING) ||
                map[x][y].getToken().equals(SOUTHTHING) || map[x][y].getToken().equals(WESTTHING) || map[x][y].getToken().equals(POT) ||
                map[x][y].getToken().equals(SIEVE) || map[x][y].getToken().equals(SAND);
    }

    public void swapling (int x, int y) {
        if (map[x][y].getDirection() == 1) {
            if (checkRowOfTwoTokenHori(x, y, rechts, SWAPLING, PATH)) {
                map[x][y].setToken(PATH);
                map[x][y].setMoved(1);
                map[rechts][y].setToken(SWAPLING);
                map[rechts][y].setMoved(1);
                map[rechts][y].setDirection(1);
            }
            else if (checkIfBlockedHori(x, y)) {
                map[x][y].setDirection(3);
                map[x][y].setMoved(1);
            }

            //TODO: noch nicht mit Zusatzwert Bamrich...
            else if (checkRowOfTwoTokenHori(x,y,rechts,SWAPLING,ME)){
                map[rechts][y].setToken(GEM);
                map[rechts][y].setMoved(1);
                if (checkWallExit(x,oben)) {
                    map[rechts][oben].setToken(GEM);
                    map[rechts][oben].setMoved(1);
                }
                if (checkWallExit(x,unten)){
                    map[rechts][unten].setToken(GEM);
                    map[rechts][unten].setMoved(1);
                }
                if (checkWallExit(rechts,y)){
                    map[x+2][y].setToken(GEM);
                    map[x+2][y].setMoved(1);
                }
                if (checkWallExit(links,y)){
                    map[x][y].setToken(GEM);
                    map[x][y].setMoved(1);
                }
                if (checkWallExit(rechts,oben)){
                    map[x+2][oben].setToken(GEM);
                    map[x+2][oben].setMoved(1);
                }
                if (checkWallExit(rechts,unten)){
                    map[x+2][unten].setToken(GEM);
                    map[x+2][unten].setMoved(1);
                }
                if (checkWallExit(links,oben)){
                    map[x][oben].setToken(GEM);
                    map[x][oben].setMoved(1);
                }
                if (checkWallExit(links,unten)){
                    map[x][unten].setToken(GEM);
                    map[x][unten].setMoved(1);
                }
            }

        }
        else if (map[x][y].getDirection() == 2) {
            if (checkRowOfTwoTokenVerti(x, y, rechts, SWAPLING, PATH)) {
                map[x][y].setToken(PATH);
                map[x][y].setMoved(1);
                map[rechts][y].setToken(SWAPLING);
                map[rechts][y].setMoved(1);
                map[rechts][y].setDirection(2);
            }
            else if (checkIfBlockedVerti(x, y)) {
                map[x][y].setDirection(4);
                map[x][y].setMoved(1);
            }

            //TODO: swapling strikes


        }
        else if (map[x][y].getDirection() == 3) {
            if (checkRowOfTwoTokenHori(x, y, rechts, SWAPLING, PATH)) {
                map[x][y].setToken(PATH);
                map[x][y].setMoved(1);
                map[rechts][y].setToken(SWAPLING);
                map[rechts][y].setMoved(1);
                map[rechts][y].setDirection(3);
            }
            else if (checkIfBlockedHori(x, y)) {
                map[x][y].setDirection(1);
                map[x][y].setMoved(1);
            }

            //TODO: swapling strikes


        }
        else if (map[x][y].getDirection() == 4) {
            if (checkRowOfTwoTokenVerti(x, y, rechts, SWAPLING, PATH)) {
                map[x][y].setToken(PATH);
                map[x][y].setMoved(1);
                map[rechts][y].setToken(SWAPLING);
                map[rechts][y].setMoved(1);
                map[rechts][y].setDirection(4);
            }
            else if (checkIfBlockedVerti(x, y)) {
                map[x][y].setDirection(2);
                map[x][y].setMoved(1);
            }

            //TODO: swapling strikes


        }
    }

    public void update(Observable o, Object arg) {}

}
