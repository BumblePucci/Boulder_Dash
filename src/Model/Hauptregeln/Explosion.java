package Model.Hauptregeln;

import Model.Feld;
import Model.Gegenstand;

import java.util.Observable;
import java.util.Observer;

import static Model.Gegenstand.*;

public class Explosion implements Observer {
    private Feld[][] map;
    private int o;  //östliches Feld von x,y aus gesehen
    private int w;  //westliches Feld von x,y aus gesehen
    private int n;  //nördliches Feld von x,y aus gesehen
    private int s;  //südliches Feld von x,y aus gesehen

    public Explosion (Feld[][] map){
        this.map=map;
    }

    //Initialisierung von o,w,n,s in Abhängigkeit von x und y
    private void setRichtungen(int x, int y) {
        o = x+1;
        w = x-1;
        n = y-1;
        s = y+1;
    }

    //Prüft, ob ein Feld eine WALL oder ein EXIT ist
    private boolean checkWallExit (int x, int y){
        return (!(map[x][y].getToken().equals(WALL) || map[x][y].getToken().equals(EXIT)));
    }

    //Methode, um einen 3x3-Bereich um ein Feld zu verwandeln
    private void transformNine (int x, int y, Gegenstand type) {
        transformOne(w,n,type);
        transformOne(w,y,type);
        transformOne(w,s,type);

        transformOne(x,n,type);
        transformOne(x,y,type);
        transformOne(x,s,type);

        transformOne(o,n,type);
        transformOne(o,y,type);
        transformOne(o,s,type);
    }

    //verwandelt ein Feld, solange es keine WALL und kein EXIT ist
    private void transformOne (int x, int y, Gegenstand type) {
        if (checkWallExit(x,y)) {
            map[x][y].setToken(type);
            map[x][y].setMoved(1);
        }
    }

    //setzt auf das alte Feld ein PATH und den moved-value
    private void resetOrigin (int x, int y) {
        map[x][y].setToken(PATH);
        map[x][y].setMoved(1);
    }


    //Methode wird in LevelModel für jedes EXPLOSION ausgeführt
    public void endOrExplode (int x, int y){
        setRichtungen(x,y);

        //Ist der bam-value gesetzt, so entsteht ein 3x3-Feld aus neuen EXPLOSIONs
        if (map[x][y].getBam()==1){
           transformNine(x,y,EXPLOSION);

        //Ist der bamrich-value gesetzt, so entsteht ein 3x3-Feld aus GEMs
        } else if (map[x][y].getBamrich()==1) {
            transformNine(x, y, GEM);

        //Ist auf einem Feld weder der bam-, noch der bamrich-value gesetzt, so erlischt die Explosion, Feld wird resettet
        } else {
            System.out.println("Explosion: auf x|y ist weder bam noch bamrich gesetzt");
            resetOrigin(x, y);
        }
    }

    public void update(Observable o, Object arg) {}

}
