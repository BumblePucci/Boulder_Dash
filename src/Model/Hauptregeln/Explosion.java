package Model.Hauptregeln;

import Model.Feld;

import java.util.Observable;
import java.util.Observer;

import static Model.Gegenstand.*;

public class Explosion implements Observer {
    private Feld[][] map;

    public Explosion (Feld[][] map){
        this.map=map;
    }

    private boolean checkWallExit (int x, int y){
        return (!(map[x][y].getToken().equals(WALL) || map[x][y].getToken().equals(EXIT)));
    }

    public void endOrExplode (int x, int y){
        int rechts = x+1;
        int links = x-1;
        int oben = y-1;
        int unten = y+1;
        if (map[x][y].getToken().equals(EXPLOSION) && map[x][y].getBam()==0 && map[x][y].getBamrich()==0){
            map[x][y].setToken(PATH);
            map[x][y].setMoved(1);
        }
        else if (map[x][y].getToken().equals(EXPLOSION) && map[x][y].getBam()==1){
            map[x][y].setToken(EXPLOSION);
            map[x][y].setMoved(1);
            if (checkWallExit(x,oben)) {
                map[x][oben].setToken(EXPLOSION);
                map[x][oben].setMoved(1);
            }
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

        else if (map[x][y].getToken().equals(EXPLOSION) && map[x][y].getBamrich()==1){
            map[x][y].setToken(GEM);
            map[x][y].setMoved(1);
            if (checkWallExit(x,oben)) {
                map[x][oben].setToken(GEM);
                map[x][oben].setMoved(1);
            }
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
    }

    public void update(Observable o, Object arg) {}

}
