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
    //private LevelModel levelModel;
    private int gemcounter;
    private int width;          //Breite des Felds
    private int o;              //östliches Feld von x,y aus gesehen
    private int w;              //westliches Feld von x,y aus gesehen
    private int n;              //nördliches Feld von x,y aus gesehen
    private int s;              //südliches Feld von x,y aus gesehen
    private int richtung;       //bestimmte neue Richtung, je nach Pfeiltastendruck
    private boolean shift;      //Wert, der nur die Tasten mit Shift einbeziehen wird
    private int oo;             //zwei Felder weiter östlich von x,y aus gesehen
    private int ww;             //zwei Felder weiter westlich von x,y aus gesehen
    private int zweiWeiter;     //ein Wert, der zwei Felder weiter von x,y aus gesehen liegt
    private Pfeil pfeil;

    public Spielerbewegung (Feld[][] map, int gemcounter, int width){
        this.map=map;
        //this.levelModel=levelModel
        this.gemcounter=gemcounter;
        this.width=width;
    }

    // public int getGemcounter() {return gemcounter;} // Wird nicht mehr gebracuht

    //Initialisierung von o,w,n,s in Abhängigkeit von x und y
    private void setRichtungen(int x, int y) {
        o = x+1;
        w = x-1;
        n = y-1;
        s = y+1;
        shift = false;
    }

    //je nach gedrückter Pfeilrichtung, wird die richtung (o,w,n oder s) übergeben (ein S vor der Pfeilrichtung steht für gedrückte shift-taste)
    private void setEnvironement (int x, int y, Pfeil pfeil) {
        this.pfeil = pfeil;
        setRichtungen(x,y);
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

    //oo und ww wird initialisiert, zweiWeiter wird je nach richtung initialisiert
    private void setZweiWeiter (int x) {
        oo = x+2;
        ww = x-2;
        if (richtung==o) {
            zweiWeiter=oo;
        } else if (richtung==w) {
            zweiWeiter=ww;
        }
    }

    public int getGemcounter() {
        return gemcounter;
    }


    //vergleiche den Gegenstand der aktuellen Position mit einem bestimmten Nachbarn, welcher gerade nich fällt
    //Je nach richtung wird die richtung an den x- oder an den y-Wert gesetzt
    private boolean checkRowOfTwoToken (int x, int y, int richtung, Gegenstand pos, Gegenstand nachbar) {

        //ist die Richtung OSTEN oder WESTEN
        if ((richtung == o || richtung == w) && (pfeil == RIGHT || pfeil == SRIGHT || pfeil == LEFT || pfeil == SLEFT)) {
            System.out.println("links und rechts");
            return (map[richtung][y].getMoved()==0 && map[richtung][y].getFalling()==0 &&

                    map[x][y].getToken().equals(pos) && map[richtung][y].getToken().equals(nachbar));

            //ist die Richtung NORDEN oder SÜDEN
        } else {
            return (map[x][richtung].getMoved()==0 && map[x][richtung].getFalling()==0 &&

                    map[x][y].getToken().equals(pos) && map[x][richtung].getToken().equals(nachbar));        }
    }


    //Methode um später zu checken, ob auch die Felder, die zweiWeiter liegen, innerhalb der Grenzen sind
    private boolean inBoundHoriZweiWeiter (int richtung){
        return (richtung-1 >= 0) && (richtung+1<width);
    }


    //Betrachte drei Felder in x-Richtung, das erste neben x,y soll pushable sein und nicht-falling, das zweiWeiter soll ein PATH sein
    private boolean checkRowOfThreePushable (int x, int y, int richtung) {
        return (map[richtung][y].getMoved()==0 && map[zweiWeiter][y].getMoved()==0 &&

                map[richtung][y].getFalling()==0 &&
                map[richtung][y].getPushable()==1 && map[zweiWeiter][y].getToken().equals(PATH));
    }


    //Setzt je nach richtung einen neuen Gegenstand ein Feld weiter in x- oder y-Richtung (moved-value wird auch dort gesetzt)
    private void setResult (int x, int y, int richtung, Gegenstand gegenstand) {
        if ((richtung==w || richtung==o) && (pfeil == RIGHT || pfeil == SRIGHT || pfeil == LEFT || pfeil == SLEFT)) {
            map[richtung][y].setToken(gegenstand);
            map[richtung][y].setMoved(1);
            System.out.println("y = " + y);
            System.out.println("richtung = " + richtung);
        } else {
            map[x][richtung].setToken(gegenstand);
            map[x][richtung].setMoved(1);
            System.out.println("x = " + x);
            System.out.println("richtung = " + richtung);
        }
    }

    //Setzt aktuelles Feld auf PATH und setzt moved-value
    private void resetOrigin (int x, int y) {
        map[x][y].setToken(PATH);
        map[x][y].setMoved(1);
    }

    //Methode wird im LevelModel für jedes ME ausgeführt, setzt ME in bewegung
    public void walk (int x, int y, Pfeil pfeil) {
        setEnvironement(x,y,pfeil);
        setZweiWeiter(x);

        //ist der shift-Wert true...
        if (shift) {
            //...und das Nachbarfeld ein MUD oder ein GEM...
            if (checkRowOfTwoToken(x, y, richtung, ME, MUD) || checkRowOfTwoToken(x,y,richtung,ME,GEM)) {

                //...so wird das Nachbarfeld zum PATH...
                setResult(x,y,richtung,PATH);
                //...und falls es ein GEM ist, wird zusätzlich der gemcounter erhöht
                if (checkRowOfTwoToken(x,y,richtung,ME,GEM)) {
                    gemcounter++;
                    //levelModel.countUp(); // ersetzt gemcount++
                    //System.out.println("gem gefunden!!!!!!!!!!!!!!!!!!!!!!!");
                }
            }

        //ist der shift-Wert nicht gedrückt...
        } else {
            //...und der Nachbar ein PATH, MUD oder GEM...
            if (checkRowOfTwoToken(x, y, richtung, ME, PATH) || checkRowOfTwoToken(x, y, richtung, ME, MUD) ||
                    checkRowOfTwoToken(x, y, richtung, ME, GEM)) {
                System.out.println("Spielerbewegung: Richtung move: " + richtung);
                System.out.println("Spielerbewegung: Pfeil1: " + pfeil);
                //...so setzte auf das Nachbarfeld ein ME und resette das Ausgangsfeld...
                setResult(x, y, richtung, ME);
                resetOrigin(x, y);
                //...bei GEM, zähle zusätzlich den gemcounter hoch
                if (checkRowOfTwoToken(x, y, richtung, ME, GEM)) {
                    gemcounter++;
                    //levelModel.countUp(); // ersetzt gemcount++
                    //System.out.println("gem gefunden!!!!!!!!!!!!!!!!!!!!!!!");
                }
            }

            //...und die richtung horizontal...
            if ((richtung==w || richtung==o)){
                //System.out.println("Spielerbewegung: Richtung: "+richtung);

                //...und die Felder zweiWeiter noch innerhalb des Arrays...
                if (inBoundHoriZweiWeiter(richtung)) {
                    //...und das Nachbarfeld pushable und dahinter ein PATH...
                    if (checkRowOfThreePushable(x, y, richtung)) {
                        System.out.println("Spielerbewegung: zweiWeiter: "+zweiWeiter+" | zu verschiebender Gegenstand: "+map[richtung][x].getToken());
                        System.out.println("Spielerbewegung: richtung: "+richtung);
                        System.out.println("Spielerbewegung: x|y: "+x+"|"+y);

                        //...so setze zweiWeiter den pushable Gegenstand, auf das Nachbarfeld ein ME und resette das Ausgangsfeld
                        setResult(x, y, zweiWeiter, map[richtung][y].getToken());
                        setResult(x, y, richtung, ME);
                        resetOrigin(x, y);
                    }
                }
            }
            System.out.println("------------------------------------------------");
        }
    }

    public void update(Observable o, Object arg) {}

}
