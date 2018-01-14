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
    private int vorne;
    private int hinten;
    private int rechteHand;
    private int linkeHand;
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

    //Xling checks
    private void setEnvironement (int x, int y) {
        if (map[x][y].getDirection()==1){
            linkeHand=y-1;
            rechteHand=y+1;
            vorne=x+1;
            hinten=x-1;
        }
        else if (map[x][y].getDirection()==2){
            linkeHand=x-1;
            rechteHand=x+1;
            vorne=y-1;
            hinten=y+1;
        }
        else if (map[x][y].getDirection()==3){
            linkeHand=y+1;
            rechteHand=y-1;
            vorne=x-1;
            hinten=x+1;
        }
        else if (map[x][y].getDirection()==4){
            linkeHand=x+1;
            rechteHand=x-1;
            vorne=y+1;
            hinten=y-1;
        }
    }


    private boolean checkFourWalkForwardXlingHori(int x, int y) {
        return (map[x][y].getToken().equals(XLING) && map[vorne][y].getToken().equals(PATH) &&
                !(map[x][rechteHand].getToken().equals(PATH) || map[x][rechteHand].getToken().equals(ME)) &&
                !(map[vorne][rechteHand].getToken().equals(PATH) || map[vorne][rechteHand].getToken().equals(ME)));
    }

    private boolean checkFourWalkForwardXlingVerti(int x, int y) {
        return (map[x][y].getToken().equals(XLING) && map[x][vorne].getToken().equals(PATH) &&
                !(map[rechteHand][y].getToken().equals(PATH) || map[rechteHand][y].getToken().equals(ME)) &&
                !(map[rechteHand][vorne].getToken().equals(PATH) || map[rechteHand][vorne].getToken().equals(ME)));
    }

    private boolean checkFourWalkForwardAndTurnXlingHori(int x, int y) {
        return (map[x][y].getToken().equals(XLING) && map[vorne][y].getToken().equals(PATH) &&
                !(map[x][rechteHand].getToken().equals(PATH) || map[x][rechteHand].getToken().equals(ME)) &&
                map[vorne][rechteHand].getToken().equals(PATH));
    }

    private boolean checkFourWalkForwardAndTurnXlingVerti(int x, int y) {
        return (map[x][y].getToken().equals(XLING) && map[x][vorne].getToken().equals(PATH) &&
                !(map[rechteHand][y].getToken().equals(PATH) || map[rechteHand][y].getToken().equals(ME)) &&
                map[rechteHand][vorne].getToken().equals(PATH));
    }

    private boolean checkSixWalkLeftAndTurnXlingHori(int x, int y) {
        return (map[x][y].getToken().equals(XLING) &&
                !(map[vorne][y].getToken().equals(PATH) || map[vorne][y].getToken().equals(ME)) &&
                !(map[x][rechteHand].getToken().equals(PATH) || map[x][rechteHand].getToken().equals(ME)) &&
                !(map[vorne][linkeHand].getToken().equals(PATH) || map[vorne][linkeHand].getToken().equals(ME)) &&
                map[x][linkeHand].getToken().equals(PATH));
    }

    private boolean checkSixWalkLeftAndTurnXlingVerti(int x, int y) {
        return (map[x][y].getToken().equals(XLING) &&
                !(map[x][vorne].getToken().equals(PATH) || map[x][vorne].getToken().equals(ME)) &&
                !(map[rechteHand][y].getToken().equals(PATH) || map[rechteHand][y].getToken().equals(ME)) &&
                !(map[linkeHand][vorne].getToken().equals(PATH) || map[linkeHand][vorne].getToken().equals(ME)) &&
                map[linkeHand][y].getToken().equals(PATH));
    }

    private boolean checkSixWalkLeftXlingHori(int x, int y) {
        return (map[x][y].getToken().equals(XLING) &&
                !(map[vorne][y].getToken().equals(PATH) || map[vorne][y].getToken().equals(ME)) &&
                !(map[x][rechteHand].getToken().equals(PATH) || map[x][rechteHand].getToken().equals(ME)) &&
                map[vorne][linkeHand].getToken().equals(PATH) &&
                map[x][linkeHand].getToken().equals(PATH));
    }

    private boolean checkSixWalkLeftXlingVerti (int x, int y) {
        return (map[x][y].getToken().equals(XLING) &&
                !(map[x][vorne].getToken().equals(PATH) || map[x][vorne].getToken().equals(ME)) &&
                !(map[rechteHand][y].getToken().equals(PATH) || map[rechteHand][y].getToken().equals(ME)) &&
                map[linkeHand][vorne].getToken().equals(PATH) &&
                map[linkeHand][y].getToken().equals(PATH));
    }

    private boolean checkNineWalkAndTurnBackXlingHori (int x, int y){
        return (map[x][y].getToken().equals(XLING) &&
                !(map[vorne][y].getToken().equals(PATH) || map[vorne][y].getToken().equals(ME)) &&
                !(map[x][rechteHand].getToken().equals(PATH) || map[x][rechteHand].getToken().equals(ME)) &&
                !(map[x][linkeHand].getToken().equals(PATH) || map[x][linkeHand].getToken().equals(ME)) &&
                !(map[hinten][linkeHand].getToken().equals(PATH) || map[hinten][linkeHand].getToken().equals(ME)) &&
                map[hinten][y].getToken().equals(PATH));
    }

    private boolean checkNineWalkAndTurnBackXlingVerti (int x, int y){
        return (map[x][y].getToken().equals(XLING) &&
                !(map[y][vorne].getToken().equals(PATH) || map[y][vorne].getToken().equals(ME)) &&
                !(map[rechteHand][y].getToken().equals(PATH) || map[rechteHand][y].getToken().equals(ME)) &&
                !(map[linkeHand][y].getToken().equals(PATH) || map[linkeHand][y].getToken().equals(ME)) &&
                !(map[linkeHand][hinten].getToken().equals(PATH) || map[linkeHand][hinten].getToken().equals(ME)) &&
                map[x][hinten].getToken().equals(PATH));
    }

    private boolean checkNineWalkBackAndTurnXlingHori (int x, int y){
        return (map[x][y].getToken().equals(XLING) &&
                !(map[vorne][y].getToken().equals(PATH) || map[vorne][y].getToken().equals(ME)) &&
                !(map[x][rechteHand].getToken().equals(PATH) || map[x][rechteHand].getToken().equals(ME)) &&
                !(map[x][linkeHand].getToken().equals(PATH) || map[x][linkeHand].getToken().equals(ME)) &&
                map[hinten][linkeHand].getToken().equals(PATH) &&
                map[hinten][y].getToken().equals(PATH));
    }

    private boolean checkNineWalkBackAndTurnXlingVerti (int x, int y){
        return (map[x][y].getToken().equals(XLING) &&
                !(map[y][vorne].getToken().equals(PATH) || map[y][vorne].getToken().equals(ME)) &&
                !(map[rechteHand][y].getToken().equals(PATH) || map[rechteHand][y].getToken().equals(ME)) &&
                !(map[linkeHand][y].getToken().equals(PATH) || map[linkeHand][y].getToken().equals(ME)) &&
                map[linkeHand][hinten].getToken().equals(PATH) &&
                map[x][hinten].getToken().equals(PATH));
    }

    private boolean checkNineBlockedHori (int x, int y){
        return (map[x][y].getToken().equals(XLING) &&
                !(map[vorne][y].getToken().equals(PATH) || map[vorne][y].getToken().equals(ME)) &&
                !(map[x][rechteHand].getToken().equals(PATH) || map[x][rechteHand].getToken().equals(ME)) &&
                !(map[x][linkeHand].getToken().equals(PATH) || map[x][linkeHand].getToken().equals(ME)) &&
                !(map[hinten][y].getToken().equals(PATH) || map[hinten][y].getToken().equals(ME)));
    }

    private boolean checkNineBlockedVerti (int x, int y){
        return (map[x][y].getToken().equals(XLING) &&
                !(map[y][vorne].getToken().equals(PATH) || map[y][vorne].getToken().equals(ME)) &&
                !(map[rechteHand][y].getToken().equals(PATH) || map[rechteHand][y].getToken().equals(ME)) &&
                !(map[linkeHand][y].getToken().equals(PATH) || map[linkeHand][y].getToken().equals(ME)) &&
                !(map[x][hinten].getToken().equals(PATH) || map[x][hinten].getToken().equals(ME)));
    }


    public void swapling (int x, int y) {
        if (map[x][y].getDirection() == 1) {
            if (checkRowOfTwoTokenHori(x, y, rechts, SWAPLING, PATH)) {
                map[x][y].setToken(PATH);
                map[x][y].setMoved(1);
                map[x][y].setDirection(0);
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
                map[rechts][y].setDirection(0);
                if (checkWallExit(x,oben)) {
                    map[rechts][oben].setToken(GEM);
                    map[rechts][oben].setMoved(1);
                    map[rechts][oben].setDirection(0);
                }
                if (checkWallExit(x,unten)){
                    map[rechts][unten].setToken(GEM);
                    map[rechts][unten].setMoved(1);
                    map[rechts][unten].setDirection(0);
                }
                if (checkWallExit(rechts,y)){
                    map[x+2][y].setToken(GEM);
                    map[x+2][y].setMoved(1);
                    map[x+2][y].setDirection(0);
                }
                if (checkWallExit(links,y)){
                    map[x][y].setToken(GEM);
                    map[x][y].setMoved(1);
                    map[x][y].setDirection(0);
                }
                if (checkWallExit(rechts,oben)){
                    map[x+2][oben].setToken(GEM);
                    map[x+2][oben].setMoved(1);
                    map[x+2][oben].setDirection(0);
                }
                if (checkWallExit(rechts,unten)){
                    map[x+2][unten].setToken(GEM);
                    map[x+2][unten].setMoved(1);
                    map[x+2][unten].setDirection(0);
                }
                if (checkWallExit(links,oben)){
                    map[x][oben].setToken(GEM);
                    map[x][oben].setMoved(1);
                    map[x][oben].setDirection(0);
                }
                if (checkWallExit(links,unten)){
                    map[x][unten].setToken(GEM);
                    map[x][unten].setMoved(1);
                    map[x][unten].setDirection(0);
                }
            }

        }
        else if (map[x][y].getDirection() == 2) {
            if (checkRowOfTwoTokenVerti(x, y, rechts, SWAPLING, PATH)) {
                map[x][y].setToken(PATH);
                map[x][y].setMoved(1);
                map[x][y].setDirection(0);
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
                map[x][y].setDirection(0);
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
                map[x][y].setDirection(0);
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

    public void xling (int x, int y) {
        setEnvironement(x, y);
        if (map[x][y].getDirection() == 1 || map[x][y].getDirection() == 3) {
            if (checkNineBlockedHori(x, y)) {
                map[x][y].setMoved(1);
                map[x][y].setDirection(map[x][y].getDirection() + 1);
            } else {
                if (checkFourWalkForwardXlingHori(x, y)) {
                    map[vorne][y].setToken(XLING);
                    map[vorne][y].setMoved(1);
                    map[vorne][y].setDirection(map[x][y].getDirection());

                } else if (checkFourWalkForwardAndTurnXlingHori(x, y)) {
                    map[vorne][y].setToken(XLING);
                    map[vorne][y].setMoved(1);
                    if (map[x][y].getDirection() == 1) {
                        map[vorne][y].setDirection(4);
                    } else {
                        map[vorne][y].setDirection(2);
                    }

                } else if (checkSixWalkLeftAndTurnXlingHori(x, y)) {
                    map[x][linkeHand].setToken(XLING);
                    map[x][linkeHand].setMoved(1);
                    if (map[x][y].getDirection() == 1) {
                        map[x][linkeHand].setDirection(2);
                    } else {
                        map[x][linkeHand].setDirection(4);
                    }

                } else if (checkSixWalkLeftXlingHori(x, y)) {
                    map[x][linkeHand].setToken(XLING);
                    map[x][linkeHand].setMoved(1);
                    map[x][linkeHand].setDirection(map[x][y].getDirection());

                } else if (checkNineWalkAndTurnBackXlingHori(x, y)) {
                    map[hinten][y].setToken(XLING);
                    map[hinten][y].setMoved(1);
                    if (map[x][y].getDirection() == 1) {
                        map[hinten][y].setDirection(3);
                    } else {
                        map[hinten][y].setDirection(1);
                    }

                } else if (checkNineWalkBackAndTurnXlingHori(x, y)) {
                    map[hinten][y].setToken(XLING);
                    map[hinten][y].setMoved(1);
                    if (map[x][y].getDirection() == 1) {
                        map[hinten][y].setDirection(2);
                    } else {
                        map[hinten][y].setDirection(4);
                    }
                }
                map[x][y].setToken(PATH);
                map[x][y].setMoved(1);
                map[x][y].setDirection(0);
            }

        } else if (map[x][y].getDirection() == 2 || map[x][y].getDirection() == 4) {
            if (checkNineBlockedVerti(x, y)) {
                map[x][y].setMoved(1);
                if (map[x][y].getDirection() == 4) {
                    map[x][y].setDirection(1);
                } else {
                    map[x][y].setDirection(3);
                }
            } else {
                if (checkFourWalkForwardXlingVerti(x, y)) {
                    map[x][vorne].setToken(XLING);
                    map[x][vorne].setMoved(1);
                    map[x][vorne].setDirection(map[x][y].getDirection());

                } else if (checkFourWalkForwardAndTurnXlingVerti(x, y)) {
                    map[x][vorne].setToken(XLING);
                    map[x][vorne].setMoved(1);
                    if (map[x][y].getDirection() == 2) {
                        map[x][vorne].setDirection(1);
                    } else {
                        map[vorne][y].setDirection(3);
                    }

                } else if (checkSixWalkLeftAndTurnXlingVerti(x, y)) {
                    map[linkeHand][y].setToken(XLING);
                    map[linkeHand][y].setMoved(1);
                    if (map[x][y].getDirection() == 2) {
                        map[linkeHand][y].setDirection(3);
                    } else {
                        map[linkeHand][y].setDirection(1);
                    }

                } else if (checkSixWalkLeftXlingVerti(x, y)) {
                    map[linkeHand][y].setToken(XLING);
                    map[linkeHand][y].setMoved(1);
                    map[linkeHand][y].setDirection(map[x][y].getDirection());
                }
                else if (checkNineWalkAndTurnBackXlingVerti(x,y)){
                    map[x][hinten].setToken(XLING);
                    map[x][hinten].setMoved(1);
                    if (map[x][y].getDirection()==2) {
                        map[x][hinten].setDirection(4);
                    }
                    else {
                        map[x][hinten].setDirection(2);
                    }
                }

                else if (checkNineWalkBackAndTurnXlingVerti(x,y)){
                    map[x][hinten].setToken(XLING);
                    map[x][hinten].setMoved(1);
                    if (map[x][y].getDirection()==2) {
                        map[x][hinten].setDirection(3);
                    }
                    else {
                        map[x][hinten].setDirection(1);
                    }
                }
                map[x][y].setToken(PATH);
                map[x][y].setMoved(1);
                map[x][y].setDirection(0);
            }
        }
    }

    public void update(Observable o, Object arg) {}

}
