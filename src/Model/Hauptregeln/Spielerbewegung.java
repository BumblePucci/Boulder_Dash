package Model.Hauptregeln;

import Model.Feld;
import Model.Gegenstand;
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
    private boolean shift;      //Wert, der nur die Tasten mit Shift einbeziehen wird

    private int posX;           //aktuelle Position
    private int posY;           //aktuelle Position

    private int dirX;           //Vektor für aktuelle Richtung
    private int dirY;           //Vektor für aktuelle Richtung

    public Spielerbewegung (Feld[][] map, int gemcounter, int width){
        this.map=map;
        //this.levelModel=levelModel
        this.gemcounter=gemcounter;
        this.width=width;
    }

    // public int getGemcounter() {return gemcounter;} // Wird nicht mehr gebracuht



    //je nach gedrückter Pfeilrichtung, wird die richtung (o,w,n oder s) übergeben (ein S vor der Pfeilrichtung steht für gedrückte shift-taste)
    private void setEnvironement (int x, int y, Pfeil pfeil) {
        posX = x;
        posY = y;

        //ist keine Taste gedrückt ist die neue Richtung 0
        dirX = 0;
        dirY = 0;

        //ist eine bestimmte Pfeiltaste gedrückt (ob mit shift oder ohne),
        //so ist in x-Richtung dirX 1 oder -1 und in Y-Richtung dirY 1 bzw. -1
        Pfeil dir = pfeil.getWithoutShift();
        if (dir==RIGHT ){
            dirX = 1;
        } else if (dir==LEFT) {
            dirX = -1;
        } else if (dir==UP) {
            dirY = -1;
        } else if (dir==DOWN){
            dirY = 1;
        }

        //ist die Shifttaste gedrückt ist shift true
        if (pfeil==SRIGHT || pfeil==SLEFT || pfeil==SUP || pfeil==SDOWN) {
            shift=true;
        }
    }

    public int getGemcounter() {
        return gemcounter;
    }


    //checke Feld nach moved, falling und token
    private boolean checkFeld (int x, int y, Gegenstand token) {
        return map[x][y].getMoved() == 0 && map[x][y].getFalling() == 0 &&
                map[x][y].getToken().equals(token);
    }

    //Methode um später zu checken, ob ein Feld innerhalb der Grenzen liegt (nur in x-Richtung)
    private boolean inBound(int x){
        return (x >= 0 && x < width);
    }

    //checkt neues Feld in x-Richtung nach moved-, falling- und pushable-value und das Feld dahinter nach moved-value und ob ein PATH darauf liegt
    private boolean checkPushable() {
        return map[posX + dirX][posY].getMoved() == 0 && map[posX + 2 * dirX][posY].getMoved() == 0 &&

                map[posX + dirX][posY].getFalling() == 0 && map[posX + 2 * dirX][posY].getToken().equals(PATH) &&
                map[posX + dirX][posY].getPushable() == 1;
    }


    //Setzt je nach richtung einen neuen Gegenstand ein Feld weiter (moved-value wird auch dort gesetzt)
    private void setResult (int x, int y, Gegenstand gegenstand) {
        map[x][y].setToken(gegenstand);
        map[x][y].setMoved(1);
    }

    //Setzt aktuelles Feld auf PATH und setzt moved-value
    private void resetOrigin (int x, int y) {
        map[x][y].setToken(PATH);
        map[x][y].setMoved(1);
    }

    //Methode wird im LevelModel für jedes ME ausgeführt, setzt ME in Bewegung
    public void walk (int x, int y, Pfeil pfeil) {
        setEnvironement(x,y,pfeil);

        //neue Position = aktuelle Position + Vektor der neuen Richtung
        int newX = posX + dirX;
        int newY = posY + dirY;

        //ist der shift-Wert true...
        if (shift) {
            //...und das vordere Feld ein MUD oder ein GEM...
            if (checkFeld(newX, newY, MUD) || checkFeld(newX, newY, GEM)) {
                //...und falls es ein GEM ist, wird zusätzlich der gemcounter erhöht
                if (checkFeld(newX, newY, GEM)) {
                    gemcounter++;
                }
                //...so wird das vordere Feld zum PATH...
                setResult(newX, newY, PATH);
            }
            shift=false;

        //ist der shift-Wert false...
        } else {
            //...und das vordere Feld ein PATH, MUD oder GEM...
            if (checkFeld(newX, newY, PATH) || checkFeld(newX, newY, MUD) ||
                    checkFeld(newX, newY, GEM)) {
                //...bei GEM, zähle zusätzlich den gemcounter hoch
                if (checkFeld(newX, newY, GEM)) {
                    gemcounter++;
                }
                //...so setzte auf das vordere Feld ein ME und resette das Ausgangsfeld...
                setResult(newX, newY, ME);
                resetOrigin(x, y);
            }

            //...und die richtung horizontal...
            //also das neue Feld nicht in der gleichen Spalte, wie das aktuelle Feld
            if (newX != posX) {
                //...und die Felder zwei weiter noch innerhalb des Arrays...
                //Feld zwei Weiter = aktuelle Position + 2*Richtungsvektor
                if (inBound(posX+2*dirX)) {
                    //...und das vordere Feld pushable und dahinter ein PATH...
                    if (checkPushable()) {

                        //...so setze zwei weiter den pushable Gegenstand, auf das fordere Feld ein ME und resette das Ausgangsfeld
                        setResult(posX+2*dirX, newY, map[newX][newY].getToken());
                        setResult(newX, newY, ME);
                        resetOrigin(x, y);
                    }
                }
            }
        }
    }

    public void update(Observable o, Object arg) {}

}
