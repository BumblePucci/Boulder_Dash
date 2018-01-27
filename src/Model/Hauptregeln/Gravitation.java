package Model.Hauptregeln;

import Model.Feld;
import Model.Gegenstand;

import java.util.Observable;
import java.util.Observer;

import static Model.Gegenstand.*;

public class Gravitation implements Observer {
    private Feld[][] map;


    public Gravitation (Feld[][] map){
        this.map=map;
    }

    private boolean checkRowOfTwoLoose (int x, int y) {
        int unten = y+1;
        return (map[x][unten].getMoved()==0 &&

                map[x][y].getLoose() == 1 && map[x][unten].getToken().equals(PATH));
    }

    private boolean checkConstellationFallRight (int x, int y) {
        int rechts = x+1;
        int oben = y-1;
        return (map[rechts][y].getMoved()==0 && map[rechts][oben].getMoved()==0 && map[x][oben].getMoved()==0 &&

                map[x][oben].getLoose()==1 && map[x][y].getSlippery()==1 &&
                map[rechts][oben].getToken().equals(PATH) && map[rechts][y].getToken().equals(PATH));
    }

    private boolean checkConstellationFallLeft (int x, int y) {
        int links = x-1;
        int oben = y-1;
        return (map[links][y].getMoved()==0 && map[links][oben].getMoved()==0 && map[x][oben].getMoved()==0 &&

                map[x][oben].getLoose()==1 && map[x][y].getSlippery()==1 &&
                map[links][oben].getToken().equals(PATH) && map[links][y].getToken().equals(PATH));
    }

    private boolean checkIfStrikes (int x, int y, Gegenstand pos) {
        int oben = y-1;
        return (map[x][oben].getMoved()==0 &&

                map[x][oben].getFalling()==1 && map[x][y].getToken().equals(pos));
    }

    private boolean checkWallExit (int x, int y){
        return (!(map[x][y].getToken().equals(WALL) || map[x][y].getToken().equals(EXIT)));
    }

    public void fall (int x, int y){
        int unten = y+1;
        if(checkRowOfTwoLoose(x,y)){
            map[x][unten].setToken(map[x][y].getToken());
            map[x][unten].setMoved(1);
            map[x][unten].setFalling(1);
            map[x][y].setToken(PATH);
            map[x][y].setMoved(1);
        }
    }

    public void fallAskew (int x, int y){
        int rechts = x+1;
        int links = x-1;
        int oben = y-1;
        if (checkConstellationFallRight(x,y)){
            map[rechts][y].setToken(map[x][oben].getToken());
            map[rechts][y].setMoved(1);
            map[rechts][y].setFalling(1);
            map[x][oben].setToken(PATH);
            map[x][oben].setMoved(1);
        }
        else if (checkConstellationFallLeft(x,y)){
            map[links][y].setToken(map[x][oben].getToken());
            map[links][y].setMoved(1);
            map[links][y].setFalling(1);
            map[x][oben].setToken(PATH);
            map[x][oben].setMoved(1);
        }
    }

    public void strike (int x, int y){
        int rechts = x+1;
        int links = x-1;
        int oben = y-1;
        int unten = y+1;
        if (checkIfStrikes(x,y,ME) || checkIfStrikes(x,y,SWAPLING) || checkIfStrikes(x,y,XLING)){
            map[x][y].setToken(GEM);
            map[x][y].setMoved(1);
            map[x][oben].setToken(GEM);
            map[x][oben].setMoved(1);
            if (checkWallExit(x,unten)){
                map[x][unten].setToken(GEM);
                map[x][unten].setMoved(1);
            }
            if (checkWallExit(rechts,y)){
                map[rechts][y].setToken(GEM);
                map[rechts][y].setMoved(1);
            }
            if (checkWallExit(links,y)){
                map[links][y].setToken(GEM);
                map[links][y].setMoved(1);
            }
            if (checkWallExit(rechts,oben)){
                map[rechts][oben].setToken(GEM);
                map[rechts][oben].setMoved(1);
            }
            if (checkWallExit(rechts,unten)){
                map[rechts][unten].setToken(GEM);
                map[rechts][unten].setMoved(1);
            }
            if (checkWallExit(links,oben)){
                map[links][oben].setToken(GEM);
                map[links][oben].setMoved(1);
            }
            if (checkWallExit(links,unten)){
                map[links][unten].setToken(GEM);
                map[links][unten].setMoved(1);
            }
        }
        if (checkIfStrikes(x,y,BLOCKLING)){
            map[x][y].setToken(EXPLOSION);
            map[x][y].setMoved(1);
            map[x][oben].setToken(EXPLOSION);
            map[x][oben].setMoved(1);
            if (checkWallExit(x,unten)){
                map[x][unten].setToken(EXPLOSION);
                map[x][unten].setMoved(1);
            }
            if (checkWallExit(rechts,y)){
                map[rechts][y].setToken(EXPLOSION);
                map[rechts][y].setMoved(1);
            }
            if (checkWallExit(links,y)){
                map[links][y].setToken(EXPLOSION);
                map[links][y].setMoved(1);
            }
            if (checkWallExit(rechts,oben)){
                map[rechts][oben].setToken(EXPLOSION);
                map[rechts][oben].setMoved(1);
            }
            if (checkWallExit(rechts,unten)){
                map[rechts][unten].setToken(EXPLOSION);
                map[rechts][unten].setMoved(1);
            }
            if (checkWallExit(links,oben)){
                map[links][oben].setToken(EXPLOSION);
                map[links][oben].setMoved(1);
            }
            if (checkWallExit(links,unten)){
                map[links][unten].setToken(EXPLOSION);
                map[links][unten].setMoved(1);
            }
        }
    }
    public void update(Observable o, Object arg) {}

}
