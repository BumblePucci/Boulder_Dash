package Model.Hauptregeln;

import Model.Feld;
import Model.Gegenstand;

import java.util.Observable;
import java.util.Observer;

import static Model.Gegenstand.*;

public class Spielerbewegung implements Observer {
    private Feld[][] map;

    private int gemcounter;

    public Spielerbewegung (Feld[][] map, int gemcounter){

        this.map=map;
        this.gemcounter=gemcounter;
    }

    public int getGemcounter() {
        return gemcounter;
    }

    //Methode um später zu checken, ob die Felder innerhalb der Grenzen sind
    private boolean inBound (int richtung){
        return (richtung >= 0) && (richtung<map.length);
    }

    //vergleiche den Gegenstand der aktuellen Position mit einem bestimmten Nachbarn (horizontal/vertikal)
    private boolean checkRowOfTwoTokenHori(int x, int y, int richtung, Gegenstand pos, Gegenstand nachbar){
        return (map[richtung][y].getMoved()==0 &&

                map[x][y].getToken().equals(pos) && map[richtung][y].getToken().equals(nachbar));
    }

    private boolean checkRowOfTwoTokenVerti(int x, int y, int richtung, Gegenstand pos, Gegenstand nachbar){
        return (map[x][richtung].getMoved()==0 &&

                map[x][y].getToken().equals(pos) && map[x][richtung].getToken().equals(nachbar));
    }

    //vergleiche den Gegenstand der aktuellen Position mit einem bestimmten Nachbarn, welcher gerade nicht fällt
    private boolean checkRowOfTwoTokenFallingHori(int x, int y, int richtung, Gegenstand pos, Gegenstand nachbar){
        return (map[richtung][y].getMoved()==0 &&

                map[x][y].getToken().equals(pos) && map[richtung][y].getToken().equals(nachbar) &&
                map[richtung][y].getFalling() == 0);
    }

    private boolean checkRowOfTwoTokenFallingVerti(int x, int y, int richtung, Gegenstand pos, Gegenstand nachbar){
        return (map[x][richtung].getMoved()==0 &&

                map[x][y].getToken().equals(pos) && map[x][richtung].getToken().equals(nachbar) &&
                map[x][richtung].getFalling() == 0);
    }

    //checke, ob ein verschiebbarer Gegenstand in der Nähe ist
    private boolean checkRowOfThreePushableRechts(int x, int y, Gegenstand pos, Gegenstand free) {
        int rechts = x+1;
        int links = x-1;
        return (map[rechts][y].getMoved()==0 && map[links][y].getMoved()==0 &&

                map[links][y].getToken().equals(pos) && map[x][y].getPushable() == 1 && map[rechts][y].getToken().equals(free));
    }

    private boolean checkRowOfThreePushableLinks(int x, int y, Gegenstand pos, Gegenstand free) {
        int rechts = x+1;
        int links = x-1;
        return (map[rechts][y].getMoved()==0 && map[links][y].getMoved()==0 &&

                map[rechts][y].getToken().equals(pos) && map[x][y].getPushable() == 1 && map[links][y].getToken().equals(free));
    }



    //ME läuft in eine Richtung auf einen PATH oder räumt erst den MUD weg und läuft dann auf das Feld
    public void walk(int x, int y, int richtung){
        int rechts = x+1;
        int links = x-1;
        int oben = y-1;
        int unten = y+1;
        //for (int i=0; i<h; i++){          //TODO: anderswo muss diese Methode für alle Felder des 2D-Arrays durchlaufen und überprüft werden, ob sich die hier beschriebenen Felder nicht am Rand des Feldes befinden
          //  for (int j=0; j<w; j++){
        if (richtung==links || richtung==rechts) {
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
    }

    //ME gräbt seitlich, ohne sich zu bewegen
    public void dig(int x, int y, int richtung){
        int rechts = x+1;
        int links = x-1;
        int oben = y-1;
        int unten = y+1;
        if (richtung==links || richtung==rechts) {
            if (checkRowOfTwoTokenHori(x, y, richtung, ME, MUD)) {
                map[richtung][y].setToken(PATH);
                map[richtung][y].setMoved(1);
            }
        }
        else if (richtung==oben || richtung==unten) {
            if (checkRowOfTwoTokenVerti(x, y, richtung, ME, MUD)) {
                map[x][richtung].setToken(PATH);
                map[x][richtung].setMoved(1);
            }
        }
    }

    //ME sammelt GEM, indem es sich auf das Feld des GEMs bewegt
    public void gemWalk(int x, int y, int richtung){
        int rechts = x+1;
        int links = x-1;
        int oben = y-1;
        int unten = y+1;
        if (richtung==links || richtung==rechts) {
            if (checkRowOfTwoTokenFallingHori(x, y, richtung, ME, GEM)) {
                map[x][y].setToken(PATH);
                map[x][y].setMoved(1);
                map[richtung][y].setToken(ME);
                map[richtung][y].setMoved(1);
                gemcounter++;
            }
        }
        else if (richtung==oben || richtung==unten) {
            if (checkRowOfTwoTokenFallingVerti(x, y, richtung, ME, GEM)) {
                map[x][y].setToken(PATH);
                map[x][y].setMoved(1);
                map[x][richtung].setToken(ME);
                map[x][richtung].setMoved(1);
                gemcounter++;
            }
        }
    }

    //ME greift nach GEM, ohne sich zu bewegen
    public void gemDig(int x, int y, int richtung){
        int rechts = x+1;
        int links = x-1;
        int oben = y-1;
        int unten = y+1;
        if (richtung==rechts || richtung==links) {
            if (checkRowOfTwoTokenFallingHori(x, y, richtung, ME, GEM)) {
                map[richtung][y].setToken(PATH);
                map[richtung][y].setMoved(1);
                gemcounter++;
            }
        }
        if (richtung==oben || richtung==unten) {
            if (checkRowOfTwoTokenFallingVerti(x, y, richtung, ME, GEM)) {
                map[x][richtung].setToken(PATH);
                map[x][richtung].setMoved(1);
                gemcounter++;
            }
        }
    }

    //TODO: [richtung * -1] hier ist ein Klammerfehler!
    //hier passt das mit der Richtung schon soweit

    /**
     * ME verschiebt pushable Dinge
     * @param x wird übergeben
     * @param y wird übergeben
     * @param richtung ist hier nur Horizontal, sprich nur links und rechts daneben
     */
    public void moveThing(int x, int y, int richtung){
        int rechts = x+1;
        int links = x-1;
        if (richtung==rechts) {
            if (checkRowOfThreePushableRechts(x, y, ME, PATH)) {
                map[rechts][y].setToken(map[x][y].getToken());
                map[rechts][y].setMoved(1);
                map[x][y].setToken(ME);
                map[x][y].setMoved(1);
                map[links][y].setToken(PATH);
                map[links][y].setMoved(1);
            }
        }
        if (richtung==links){
            if (checkRowOfThreePushableLinks(x, y, ME, PATH)) {
                map[links][y].setToken(map[x][y].getToken());
                map[links][y].setMoved(1);
                map[x][y].setToken(ME);
                map[x][y].setMoved(1);
                map[rechts][y].setToken(PATH);
                map[rechts][y].setMoved(1);
            }
        }
    }


    public void update(Observable o, Object arg) {}

}
