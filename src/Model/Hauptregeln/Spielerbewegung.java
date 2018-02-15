package Model.Hauptregeln;

import Model.Feld;
import Model.Gegenstand;
import Model.LevelModel;
import Model.Pfeil;

import java.util.Observable;
import java.util.Observer;

import static Model.Gegenstand.*;
import static Model.Pfeil.*;

public class Spielerbewegung implements Observer {
    private Feld[][] map;
    private LevelModel levelModel;
    //private int gemcounter;
    private int gemcounter;
    private int o;
    private int w;
    private int n;
    private int s;
    private int richtung;
    private boolean shift;
    int oo;
    int ww;
    int zweiWeiter;

    public Spielerbewegung (Feld[][] map, int gemcounter){
        this.map=map;
        this.levelModel = levelModel;
    }

    // public int getGemcounter() {return gemcounter;} // Wird nicht mehr gebracuht
    private void setDirections (int x, int y) {
        o = x+1;
        w = x-1;
        n = y-1;
        s = y+1;
        shift = false;
        oo = x+2;
        ww = x-2;
        if (richtung==o) {
            zweiWeiter=oo;
        } else {
            zweiWeiter=ww;
        }
    }

    private void setEnvironement (int x, int y, Pfeil pfeil) {
        setDirections(x,y);
        if (pfeil==RIGHT || pfeil==SRIGHT){
            richtung=o;
        } else if (pfeil==LEFT || pfeil==SLEFT) {
            richtung=w;
        } else if (pfeil==UP || pfeil==SUP) {
            richtung=n;
        } else if (pfeil==DOWN || pfeil==SDOWN){
            richtung=s;
        }

        if (pfeil==SRIGHT || pfeil==SLEFT || pfeil==SUP || pfeil==SDOWN) {
            shift=true;
        }
    }

    //public int getGemcounter() { return gemcounter; } Wird nimmer benötigt

    //Methode um später zu checken, ob die Felder innerhalb der Grenzen sind
    //private boolean inBound (int richtung){
    //    return (richtung >= 0) && (richtung<map.length);
    //}


    //vergleiche den Gegenstand der aktuellen Position mit einem bestimmten Nachbarn, welcher gerade nich fällt
    private boolean checkRowOfTwoToken (int x, int y, int richtung, Gegenstand pos, Gegenstand nachbar) {

        //ist die Richtung OSTEN oder WESTEN
        if (richtung == o || richtung == w) {
            return (map[richtung][y].getMoved()==0 && map[richtung][y].getFalling()==0 &&

                    map[x][y].getToken().equals(pos) && map[richtung][y].getToken().equals(nachbar));

            //ist die Richtung NORDEN oder SÜDEN
        } else {
            return (map[x][richtung].getMoved()==0 && map[x][richtung].getFalling()==0 &&

                    map[x][y].getToken().equals(pos) && map[x][richtung].getToken().equals(nachbar));        }
    }



    private boolean checkRowOfThreePushable (int x, int y, int richtung) {
        setDirections(x,y);
        return (map[x][y].getMoved()==0 && map[richtung][y].getMoved()==0 || map[zweiWeiter][y].getMoved()==0 &&

                map[richtung][y].getFalling()==0 &&
                map[richtung][y].getPushable()==1 && map[zweiWeiter][y].getToken().equals(PATH));
    }


    private void setResult (int x, int y, int richtung, Gegenstand gegenstand) {
        if (richtung==w || richtung==o) {
            map[richtung][y].setToken(gegenstand);
            map[richtung][y].setMoved(1);
        } else {
            map[x][richtung].setToken(gegenstand);
            map[x][richtung].setMoved(1);
        }
    }

    private void resetOrigin (int x, int y) {
        map[x][y].setToken(PATH);
        map[x][y].setMoved(1);
    }

    public void walk (int x, int y, Pfeil pfeil) {
        setEnvironement(x,y,pfeil);
        if (shift) {
            if (checkRowOfTwoToken(x, y, richtung, ME, MUD) || checkRowOfTwoToken(x,y,richtung,ME,GEM)) {
                setResult(x,y,richtung,PATH);
                if (checkRowOfTwoToken(x,y,richtung,ME,GEM)) {
                    levelModel.countUp(); // ersetzt gemcount++
                    System.out.println("gem gefunden!!!!!!!!!!!!!!!!!!!!!!!");
                }
            }
        } else {
            if (checkRowOfTwoToken(x, y, richtung, ME, PATH) || checkRowOfTwoToken(x, y, richtung, ME, MUD) ||
                    checkRowOfTwoToken(x, y, richtung, ME, GEM)) {
                setResult(x, y, richtung, ME);
                resetOrigin(x, y);
                if (checkRowOfTwoToken(x, y, richtung, ME, GEM)) {
                    levelModel.countUp(); // ersetzt gemcount++
                    System.out.println("gem gefunden!!!!!!!!!!!!!!!!!!!!!!!");
                }
            }

            if (richtung==w || richtung==o) {
                if (checkRowOfThreePushable(x,y,richtung)) {
                    setResult(x,y,zweiWeiter,map[richtung][y].getToken());
                    setResult(x,y,richtung,ME);
                    resetOrigin(x,y);
                }
            }
        }
    }

    public void update(Observable o, Object arg) {}

}
