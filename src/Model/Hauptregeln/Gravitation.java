package Model.Hauptregeln;

import Model.Feld;
import Model.Gegenstand;

import java.util.Observable;
import java.util.Observer;

import static Model.Gegenstand.*;

public class Gravitation implements Observer {
    private Feld[][] map;
    private int o;  //östliches Feld von x,y aus gesehen
    private int w;  //westliches Feld von x,y aus gesehen
    private int n;  //nördliches Feld von x,y aus gesehen
    private int s;  //südliches Feld von x,y aus gesehen


    public Gravitation (Feld[][] map){
        this.map=map;
    }

    //Initialisierung von o,w,n,s in Abhängigkeit von x und y
    private void setRichtungen(int x, int y) {
        o = x+1;
        w = x-1;
        n = y-1;
        s = y+1;
    }

    //prüfen, ob der südliche Gegenstand eines Feldes dem eingesetzten Gegenstand entspricht und unbewegt ist
    private boolean checkRowOfTwoSouth(int x, Gegenstand target) {
        return (map[x][s].getMoved()==0 && map[x][s].getToken().equals(target));
    }

    //prüfen, ob der südliche Gegenstand eines Feldes rutschig ist
    private boolean checkRowOfTwoSouthSlippery(int x) {
        return (map[x][s].getMoved()==0 && map[x][s].getSlippery()==1);
    }

    //prüfen, ob rechts neben einem Feld, sowie auch rechts unterhalb des Felds ein PATH liegt, beide unbewegt
    private boolean checkConstellationFallRight (int y) {
        return (map[o][y].getMoved()==0 && map[o][s].getMoved()==0 &&

                map[o][y].getToken().equals(PATH) && map[o][s].getToken().equals(PATH));
    }

    //prüfen, ob links neben einem Feld, sowie auch links unterhalb des Felds ein PATH liegt, beide unbewegt
    private boolean checkConstellationFallLeft (int y) {
        return (map[w][y].getMoved()==0 && map[w][s].getMoved()==0 &&

                map[w][y].getToken().equals(PATH) && map[w][s].getToken().equals(PATH));
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
    private void transformOne(int x, int y, Gegenstand type) {
        if (checkWallExit(x,y)) {
            map[x][y].setToken(type);
            map[x][y].setMoved(1);
        }
    }

    //Prüft, ob ein Feld eine WALL oder ein EXIT ist
    private boolean checkWallExit (int x, int y){
        return (!(map[x][y].getToken().equals(WALL) || map[x][y].getToken().equals(EXIT)));
    }


    //setzt auf ein Feld mit den Koordinaten xRichtung/yRichtung einen neuen Gegenstand, sowie den moved- und den falling-value
    private void setResult (int x, int y, int xRichtung, int yRichtung) {
        map[xRichtung][yRichtung].setToken(map[x][y].getToken());
        map[xRichtung][yRichtung].setMoved(1);
        map[xRichtung][yRichtung].setFalling(1);
    }

    //setzt auf das alte Feld ein PATH und den moved-value
    private void resetOrigin (int x, int y) {
        map[x][y].setToken(PATH);
        map[x][y].setMoved(1);
    }



    //Methode, die in LevelModel für alle Felder mit gesetztem loose-value ausgeführt wird
    public void fall (int x, int y) {
        setRichtungen(x,y);
        //befindet sich unterhalb des Feldes ein PATH...
        if (checkRowOfTwoSouth(x,PATH)) {
            //...so wird auf das neue Feld der Gegenstand des alten Felds gesetzt und das alte Feld resettet
            setResult(x,y,x,s);
            System.out.println("Gravitation: falling: "+map[x][s].getFalling());
            resetOrigin(x,y);

        //befindet sich unterhalb des Felds ein slippery Gegenstand...
        } else if (checkRowOfTwoSouthSlippery(x)) {
            //...so fällt der Gegenstand auf das süd-östliche Feld herunter, wenn es kann...
            if (checkConstellationFallRight(y)) {
                setResult(x,y,o,s);
                resetOrigin(x,y);

            //...wenn nicht, so fällt es auf das süd-westliche Feld herunter, wenn es kann
            } else if (checkConstellationFallLeft(y)) {
                setResult(x,y,w,s);
                resetOrigin(x,y);
            }


        }
    }


    //STRIKE-METHODEN
    //diese Methoden sind aus der Perspektive eines MEs, oder eines Gegners, die erschlagen werden

    //checkt, ob das nördliche Feld loose und falling aber nicht moved ist
    private boolean checkRowOfTwoNorthLooseFalling(int x) {
        return (map[x][n].getMoved()==0 && map[x][n].getLoose()==1  && map[x][n].getFalling()==1);
    }


    //diese Methode wird im LevelModel für MEs, XLINGs und SWAPLINGs ausgeführt
    public void strikeToGems (int x, int y) {
        setRichtungen(x,y);
        if (checkRowOfTwoNorthLooseFalling(x)) {
            transformNine(x,y,GEM);
        }
    }

    //diese Methode wird im LevelModel für BLOCKLINGs ausgeführt
    public void strikeToStones (int x, int y) {
        setRichtungen(x,y);
        if (checkRowOfTwoNorthLooseFalling(x)) {
            transformNine(x,y,STONE);
        }
    }

    public void update(Observable o, Object arg) {}

}
