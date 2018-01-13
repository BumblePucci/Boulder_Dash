package Model.Hauptregeln;

import Model.Feld;
import Model.LevelModel;

import java.util.Observable;
import java.util.Observer;

import static Model.Gegenstand.*;

public class Explosion implements Observer {
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

    public Explosion (LevelModel levelModel, int x, int y){
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

    private boolean checkWallExit (int x, int y){
        return (map[x][y].getToken().equals(ME) || map[x][y].getToken().equals(MUD) || map[x][y].getToken().equals(STONE)) ||
                map[x][y].getToken().equals(GEM) || map[x][y].getToken().equals(BRICKS) || map[x][y].getToken().equals(PATH) ||
                map[x][y].getToken().equals(EXPLOSION) || map[x][y].getToken().equals(SLIME) || map[x][y].getToken().equals(SWAPLING) ||
                map[x][y].getToken().equals(BLOCKLING) || map[x][y].getToken().equals(XLING) || map[x][y].getToken().equals(GHOSTLING) ||
                map[x][y].getToken().equals(FIRE) || map[x][y].getToken().equals(NORTHTHING) || map[x][y].getToken().equals(EASTTHING) ||
                map[x][y].getToken().equals(SOUTHTHING) || map[x][y].getToken().equals(WESTTHING) || map[x][y].getToken().equals(POT) ||
                map[x][y].getToken().equals(SIEVE) || map[x][y].getToken().equals(SAND);
    }

    public void endOrExplode (int x, int y){
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
