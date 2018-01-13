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
    private int rechts = x+1;
    private int links = x-1;
    private int oben = y-1;
    private int unten = y+1;
    private int w;
    private int h;

    public Spielerbewegung (LevelModel levelModel, int x, int y){
        this.levelModel=levelModel;
        this.x=x;
        this.y=y;
        this.map=levelModel.getMap();
        this.w = levelModel.width;
        this.h = levelModel.height;
    }

    //TODO: Richtung muss noch in horizontale und vertikale Richtung aufgeteilt werden!!

    //Methode um später zu checken, ob die Felder innerhalb der Grenzen sind
    private boolean inBound (int richtung, int size){
        return (richtung >= 0) && (richtung<size);
    }

    private boolean checkRowOfTwoToken(int x, int y, int richtung, Gegenstand pos, Gegenstand nachbar){
        return (map[x][y].getToken().equals(pos) && map[richtung][y].getToken().equals(nachbar));
    }

    private boolean checkRowOfTwoTokenFalling(int x, int y, int richtung, Gegenstand pos, Gegenstand nachbar){
        return (map[x][y].getToken().equals(pos) && map[richtung][y].getToken().equals(nachbar) && map[richtung][y].getFalling() == 0);
    }

    private boolean checkRowOfThreePushable(int x, int y, int richtung, Gegenstand pos, Gegenstand free) {
        return ((map[richtung*-1][y].getToken().equals(pos) && map[x][y].getPushable() == 1) && map[richtung][y].getToken().equals(free));
    }

    public void walk(int x, int y, int richtung){
        //for (int i=0; i<h; i++){          //TODO: anderswo muss diese Methode für alle Felder des 2D-Arrays durchlaufen und überprüft werden, ob sich die hier beschriebenen Felder nicht am Rand des Feldes befinden
          //  for (int j=0; j<w; j++){
        if (checkRowOfTwoToken(x,y,richtung, ME, PATH) || checkRowOfTwoToken(x,y,richtung, ME, MUD)) {
            map[x][y].setToken(PATH);
            map[x][y].setMoved(1);
            map[richtung][y].setToken(ME);
            map[richtung][y].setMoved(1);
        }
    }

    public void dig(int x, int y, int richtung){
        if (checkRowOfTwoToken(x,y,richtung, ME, MUD)){
            map[richtung][y].setToken(PATH);
            map[richtung][y].setMoved(1);
        }
    }

    public void gemWalk(int x, int y, int richtung){
        if (checkRowOfTwoTokenFalling(x,y,richtung, ME, GEM)){
            map[x][y].setToken(PATH);
            map[x][y].setMoved(1);
            map[richtung][y].setToken(ME);
            map[x][y].setMoved(1);
            levelModel.gemcounter++;
        }
    }

    public void gemDig(int x, int y, int richtung){
        if (checkRowOfTwoTokenFalling(x,y,richtung, ME, GEM)){
            map[richtung][y].setToken(PATH);
            map[richtung][y].setMoved(1);
            levelModel.gemcounter++;
        }
    }

    //hier passt das mit der Richtung schon soweit
    public void moveThing(int x, int y, int richtung){
        if (checkRowOfThreePushable(x,y,richtung,ME,PATH)){
            map[richtung][y].setToken(map[x][y].getToken());
            map[richtung][y].setMoved(1);
            map[x][y].setToken(ME);
            map[x][y].setMoved(1);
            map[richtung*-1][y].setToken(PATH);
            map[richtung*-1][y].setMoved(1);
        }
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
