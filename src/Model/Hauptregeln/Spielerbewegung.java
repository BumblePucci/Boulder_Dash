package Model.Hauptregeln;

import Model.Feld;
import Model.Gegenstand;
import Model.LevelModel;

import java.util.Observable;
import java.util.Observer;

import static Model.Gegenstand.*;

public class Spielerbewegung implements Observer {
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

    public Spielerbewegung (LevelModel levelModel, int x, int y){
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


    //Methode um später zu checken, ob die Felder innerhalb der Grenzen sind
    private boolean inBound (int richtung, int size){
        return (richtung >= 0) && (richtung<size);
    }

    private boolean checkRowOfTwoTokenHori(int x, int y, int richtung, Gegenstand pos, Gegenstand nachbar){
        return (map[x][y].getToken().equals(pos) && map[richtung][y].getToken().equals(nachbar));
    }

    private boolean checkRowOfTwoTokenVerti(int x, int y, int richtung, Gegenstand pos, Gegenstand nachbar){
        return (map[x][y].getToken().equals(pos) && map[x][richtung].getToken().equals(nachbar));
    }

    private boolean checkRowOfTwoTokenFallingHori(int x, int y, int richtung, Gegenstand pos, Gegenstand nachbar){
        return (map[x][y].getToken().equals(pos) && map[richtung][y].getToken().equals(nachbar) && map[richtung][y].getFalling() == 0);
    }

    private boolean checkRowOfTwoTokenFallingVerti(int x, int y, int richtung, Gegenstand pos, Gegenstand nachbar){
        return (map[x][y].getToken().equals(pos) && map[x][richtung].getToken().equals(nachbar) && map[x][richtung].getFalling() == 0);
    }

    private boolean checkRowOfThreePushableHori(int x, int y, int richtung, Gegenstand pos, Gegenstand free) {
        return ((map[richtung-1][y].getToken().equals(pos) && map[x][y].getPushable() == 1) && map[richtung][y].getToken().equals(free));
    }

    public void walk(int x, int y, int richtung){
        //for (int i=0; i<h; i++){          //TODO: anderswo muss diese Methode für alle Felder des 2D-Arrays durchlaufen und überprüft werden, ob sich die hier beschriebenen Felder nicht am Rand des Feldes befinden
          //  for (int j=0; j<w; j++){
        if ((richtung==links || richtung==rechts) && (richtung!=oben || richtung!=unten)) {
            if (checkRowOfTwoTokenHori(x, y, richtung, ME, PATH) || checkRowOfTwoTokenHori(x, y, richtung, ME, MUD)) {
                map[x][y].setToken(PATH);
                map[x][y].setMoved(1);
                map[richtung][y].setToken(ME);
                map[richtung][y].setMoved(1);

            }
        }
        else if (richtung==oben || richtung==unten) {
            if (checkRowOfTwoTokenVerti(x, y, richtung, ME, PATH) || checkRowOfTwoTokenVerti(x, y, richtung, ME, MUD)) {
                map[x][y].setToken(PATH);
                map[x][y].setMoved(1);
                map[x][richtung].setToken(ME);
                map[x][richtung].setMoved(1);
            }
        }
        levelModel.setMap(map);
    }

    public void dig(int x, int y, int richtung){
        if ((richtung==links || richtung==rechts) && (richtung!=oben || richtung!=unten)) {
            if (checkRowOfTwoTokenHori(x, y, richtung, ME, MUD)) {
                map[richtung][y].setToken(PATH);
                map[richtung][y].setMoved(1);
            }
        }
        else if (richtung==oben || richtung==unten) {
            if (checkRowOfTwoTokenVerti(x, y, richtung, ME, MUD)) {
                map[richtung][y].setToken(PATH);
                map[richtung][y].setMoved(1);
            }
        }
        levelModel.setMap(map);
    }

    public void gemWalk(int x, int y, int richtung){
        if ((richtung==links || richtung==rechts) && (richtung!=oben || richtung!=unten)) {
            if (checkRowOfTwoTokenFallingHori(x, y, richtung, ME, GEM)) {
                map[x][y].setToken(PATH);
                map[x][y].setMoved(1);
                map[richtung][y].setToken(ME);
                map[richtung][y].setMoved(1);
                levelModel.gemcounter++;
            }
        }
        else if (richtung==oben || richtung==unten) {
            if (checkRowOfTwoTokenFallingVerti(x, y, richtung, ME, GEM)) {
                map[x][y].setToken(PATH);
                map[x][y].setMoved(1);
                map[x][richtung].setToken(ME);
                map[x][richtung].setMoved(1);
                levelModel.gemcounter++;
            }
        }
        levelModel.setMap(map);
    }

    public void gemDig(int x, int y, int richtung){
        if ((richtung==links || richtung==rechts) && (richtung!=oben || richtung!=unten)) {
            if (checkRowOfTwoTokenFallingHori(x, y, richtung, ME, GEM)) {
                map[richtung][y].setToken(PATH);
                map[richtung][y].setMoved(1);
                levelModel.gemcounter++;
            }
        }
        if (richtung==oben || richtung==unten) {
            if (checkRowOfTwoTokenFallingVerti(x, y, richtung, ME, GEM)) {
                map[x][richtung].setToken(PATH);
                map[x][richtung].setMoved(1);
                levelModel.gemcounter++;
            }
        }
        levelModel.setMap(map);
    }

    //hier passt das mit der Richtung schon soweit
    public void moveThing(int x, int y, int richtung){
        if ((richtung==links || richtung==rechts) && (richtung!=oben || richtung!=unten)) {
            if (checkRowOfThreePushableHori(x, y, richtung, ME, PATH)) {
                map[richtung][y].setToken(map[x][y].getToken());
                map[richtung][y].setMoved(1);
                map[x][y].setToken(ME);
                map[x][y].setMoved(1);
                map[richtung -1][y].setToken(PATH);
                map[richtung -1][y].setMoved(1);
            }
        }
        levelModel.setMap(map);
    }

    private void movePlayer(int x, int y){
        walk(x,y,rechts);
        walk(x,y,links);
        walk(x,y,oben);
        walk(x,y,unten);
        dig(x,y,rechts);
        dig(x,y,links);
        dig(x,y,oben);
        dig(x,y,unten);
        gemWalk(x,y,rechts);
        gemWalk(x,y,links);
        gemWalk(x,y,oben);
        gemWalk(x,y,unten);
        gemDig(x,y,rechts);
        gemDig(x,y,links);
        gemDig(x,y,oben);
        gemDig(x,y,unten);
        moveThing(x,y,rechts);
        moveThing(x,y,links);
    }
    public void update(Observable o, Object arg) {}

}
