package Model.Hauptregeln;

import Model.Direction;
import Model.Feld;
import Model.Gegenstand;

import java.util.Observable;
import java.util.Observer;

import static Model.Direction.*;
import static Model.Gegenstand.*;


public class Gegnerbewegung implements Observer {
    private Feld[][] map;

    private int o;
    private int w;
    private int n;
    private int s;
    private int vorne;
    private int hinten;
    private int rechteHand;
    private int linkeHand;
    private int hori1;
    private int hori2;
    private Direction currentDirection;

    public Gegnerbewegung (Feld[][] map){

        this.map=map;
    }

    //legt allgemeine Richtungen an: Osten, Westen, Norden, Süden, ausgesehen von x,y
    private void setRichtungen(int x, int y) {
        o = x+1;
        w = x-1;
        n = y-1;
        s = y+1;
    }

    //legt vom Xling aus gesehen die Positionen linkeHand, rechteHand, vorne und hinten fest
    private void setEnvironement (int x, int y) {
        if (currentDirection.equals(OSTEN)){
            linkeHand=y-1;
            rechteHand=y+1;
            vorne=x+1;
            hinten=x-1;
        }
        else if (currentDirection.equals(NORDEN)){
            linkeHand=x-1;
            rechteHand=x+1;
            vorne=y-1;
            hinten=y+1;
        }
        else if (currentDirection.equals(WESTEN)){
            linkeHand=y+1;
            rechteHand=y-1;
            vorne=x-1;
            hinten=x+1;
        }
        else if (currentDirection.equals(SUEDEN)){
            linkeHand=x+1;
            rechteHand=x-1;
            vorne=y+1;
            hinten=y-1;
        }

        //hori1 und hori2 haben je nach Gegner eine unterschiedliche funktion
        //Rechte-Hand-Regel
        if (map[x][y].getToken().equals(XLING)){
            hori1=rechteHand;
            hori2=linkeHand;
        //Linke-Hand-Regel
        } else if (map[x][y].getToken().equals(BLOCKLING)) {
            hori1=linkeHand;
            hori2=rechteHand;
        }
    }

    private void getEnvironement(){
        getVorne();
        getHinten();
        getHori1();
        getHori2();
    }

    public int getVorne() {
        return vorne;
    }

    public int getHinten() {
        return hinten;
    }

    public int getHori1() {
        return hori1;
    }

    public int getHori2() {
        return hori2;
    }

    //Vergleiche aktuelle Postion (also die Position des Gegners) mit einer bestimmten Nachbarpostion
    //x und y sind alte Positionen, richtung ist neue Position
    private boolean checkRowOfTwoToken (int x, int y, int richtung, Gegenstand gegner, Gegenstand nachbar) {
        //setEnvironement(x,y);

        //ist die alte Direction OSTEN oder WESTEN
        if (map[x][y].getDirection().equals(OSTEN) || map[x][y].getDirection().equals(WESTEN)) {

            //und die neue Richtung vorne oder hinten
            if (richtung==vorne || richtung==hinten) {
                //dann frage die Stelle ober- oder unterhalb des Ursprungsfelds ab (y-Richtung)
                return (checkRowOfTwoTokenHori(x, y, richtung, gegner, nachbar));

            //oder die neue Richtung rechteHand oder linkeHand
            } else {
                //dann frage die Stelle rechts oder links des Ursprungsfelds ab (x-Richtung)
                return (checkRowOfTwoTokenVerti(x, y, richtung, gegner, nachbar));
            }

        //oder die alte Direction ist NORDEN oder SUEDEN
        } else {
            //und die neue Richtung zur rechten oder linken Hand
            if (richtung==hori1 || richtung==hori2) {
                //dann frage die Stelle rechts oder links des Ursprungsfelds ab (x-Richtung)
                return (checkRowOfTwoTokenHori(x, y, richtung, gegner, nachbar));

            //oder die neue Richtung vorne oder hinten
            } else {
                //dann frage die Stelle ober- oder unterhalb des Ursprungsfelds ab (y-Richtung)
                return (checkRowOfTwoTokenVerti(x, y, richtung, gegner, nachbar));
            }
        }
    }

    private boolean checkRowOfTwoTokenHori(int x, int y, int richtung, Gegenstand gegner, Gegenstand nachbar){
        //return true if auf dem aktuellen Feld ein Gegner liegt und daneben ein Nachbar
        //horizontale Richtung muss angegeben werden, horizontal->rechts oder links, x-Richtung
        return (map[x][y].getToken().equals(gegner) && map[richtung][y].getToken().equals(nachbar));
    }

    private boolean checkRowOfTwoTokenVerti(int x, int y, int richtung, Gegenstand gegner, Gegenstand nachbar){
        //return true if auf dem aktuellen Feld ein Gegner liegt und daneben ein Nachbar
        //vertikale Richtung muss angegeben werden, vertikal->oben oder unten, x-Richtung
        return (map[x][y].getToken().equals(gegner) && map[x][richtung].getToken().equals(nachbar));
    }


    //Methode, um die Felder rechts oben, links oben, rechts unten und links unten abzufragen
    private boolean checkTwoToken(int x, int y, int horiRichtung, int vertiRichtung, Gegenstand gegner, Gegenstand nachbar) {
        //setEnvironement(x,y);
        //ist die alte Direction OSTEN oder WESTEN
        if (map[x][y].getDirection().equals(OSTEN) || map[x][y].getDirection().equals(WESTEN)) {
            //return true if auf dem aktuellen Feld ein Gegner liegt und daneben ein Nachbar
            //vertikale Richtungen, also vorne und hinten liegen auf der x-Achse, horizontale Richtungen (rechteHand, linkeHand) liegen auf der y-Achse
            return (map[x][y].getToken().equals(gegner) && map[vertiRichtung][horiRichtung].getToken().equals(nachbar));

        //ist die alte Direction NORDEN oder SUEDEN
        } else {
            //return true if auf dem aktuellen Feld ein Gegner liegt und daneben ein Nachbar
            //vertikale Richtungen, also vorne und hinten liegen auf der y-Achse, horizontale Richtungen (rechteHand, linkeHand) liegen auf der x-Achse
            return (map[x][y].getToken().equals(gegner) && map[horiRichtung][vertiRichtung].getToken().equals(nachbar));
        }

    }



    /**     Setze den Gegner auf die neue Position (je nach Richtung)
     *
     * @param x             alter x Wert
     * @param y             alter y Wert
     * @param richtung      neuer x/y Wert / neue Richtung
     * @param gegner        Gegnertyp, der bewegt wird
     * @param direction     alter Direction-Value
     * @param turningDegree Grad der Drehung im Uhrzeigersinn (von 0-3, 0->keine Drehung, 1->Drehung um 1 im Uhrzeigersinn
     */
    private void setResult (int x, int y, int richtung, Gegenstand gegner, Direction direction, int turningDegree) {
        //setEnvironement(x,y);
        //wenn die alte Direction OSTEN oder WESTEN ist
        if (map[x][y].getDirection().equals(OSTEN) || map[x][y].getDirection().equals(WESTEN)) {
            //und wenn die neue Richtung vorne oder hinten ist
            System.out.println("Gegnerb.: richtung: "+richtung);
            System.out.println("Gegnerb.: vorne: "+vorne);
            System.out.println("Gegnerb.: hinten: "+hinten);
            System.out.println("Gegnerb.: hori2: "+hori2);
            if (richtung==vorne || richtung==hinten) {
                //setze den Gegner in die neue Richtung in x-Richtung (rechts oder links)
                setResultWithoutLooking(richtung,y,gegner,direction,turningDegree);
                System.out.println("Gegnerb.: vorne/hinten");
                System.out.println("Gegnerb.: x: " +richtung);
                System.out.println("Gegnerb.: y: " +y);
                System.out.println("Gegnerb.: direction: " +direction);

            //oder wenn die neue Richtung rechteHand oder linkeHand ist
            } else {
                //setze den Gegner in die neue Richtung in y-Richtung (oben oder unten)
                setResultWithoutLooking(x,richtung,gegner,direction,turningDegree);
                System.out.println("Gegnerb.: rechteHand/linkeHand");
                System.out.println("Gegnerb.: x: " +x);
                System.out.println("Gegnerb.: y: " +richtung);
            }

        //wenn die alte Direction NORDEN oder SUEDEN ist
        } else {
            //und die neue Richtung rechteHand oder linkeHand ist
            if (richtung==hori1 || richtung==hori2) {
                //setze den Gegner in die neue Richtung in x-Richtung (rechts oder links)
                setResultWithoutLooking(richtung,y,gegner,direction,turningDegree);

            //oder die neue Richtung vorne oder hinten ist
            } else {
                //setze den Gegner in die neue Richtung in y-Richtung (oben oder unten)
                setResultWithoutLooking(x,richtung,gegner,direction,turningDegree);
            }
        }
    }

    /**
     *
     * @param x             Neuer x Wert
     * @param y             Neuer y Wert
     * @param gegner        s.o.
     * @param direction     s.o.
     * @param turningDegree s.o.
     */
    private void setResultWithoutLooking (int x, int y, Gegenstand gegner, Direction direction, int turningDegree) {
        //setze den Gegner auf das neue Feld und setze Moved-Value, setze die Direction in Abhängigkeit des turningDegrees
        System.out.println("Gegnerb.: direction der neuen Richtung: "+map[x][y].getDirection());
        //System.out.println("x: "+x+" und y: "+y);
        map[x][y].setToken(gegner);
        map[x][y].setMoved(1);
        if (turningDegree==0) {
            setDirection0(x,y,direction);
        } else if (turningDegree==1){
            setDirection1InUhr(x,y,direction);
        } else if (turningDegree==2){
            setDirection2InUhr(x,y,direction);
        } else if (turningDegree==3){
            setDirection3InUhr(x,y,direction);
        }
        System.out.println("Gegnerb.: direction der neuen Richtung: "+map[x][y].getDirection());
    }


    //Auf dem Feld, auf dem sich ein Gegner als nächstes befindet, wird eine zu bestimmende neue Direction gesetzt

    //Direction bleibt gleich
    private void setDirection0 (int x, int y, Direction direction) {
        map[x][y].setDirection(direction);
    }

    //Direction verändet sich um eine Richtung im Uhrzeigersinn
    private void setDirection1InUhr (int x, int y, Direction direction) {
        if (direction.equals(OSTEN)){
            map[x][y].setDirection(SUEDEN);
        } else if (direction.equals(SUEDEN)) {
            map[x][y].setDirection(WESTEN);
        } else if (direction.equals(WESTEN)) {
            map[x][y].setDirection(NORDEN);
        } else if (direction.equals(NORDEN)) {
            map[x][y].setDirection(OSTEN);
        }
    }

    //Direction verändet sich um zwei Richtungen im Uhrzeigersinn
    private void setDirection2InUhr (int x, int y, Direction direction) {
        if (direction.equals(OSTEN)){
            map[x][y].setDirection(WESTEN);
        } else if (direction.equals(SUEDEN)) {
            map[x][y].setDirection(NORDEN);
        } else if (direction.equals(WESTEN)) {
            map[x][y].setDirection(OSTEN);
        } else if (direction.equals(NORDEN)) {
            map[x][y].setDirection(SUEDEN);
        }
    }

    //Direction verändet sich um drei Richtungen im Uhrzeigersinn
    private void setDirection3InUhr (int x, int y, Direction direction) {
        System.out.println("Gegnerb.: vor 3 Drehungen in Uhr: "+map[x][y].getDirection());
        if (direction.equals(OSTEN)){
            map[x][y].setDirection(NORDEN);
        } else if (direction.equals(NORDEN)) {
            map[x][y].setDirection(WESTEN);
        } else if (direction.equals(WESTEN)) {
            map[x][y].setDirection(SUEDEN);
        } else if (direction.equals(SUEDEN)) {
            map[x][y].setDirection(OSTEN);
        }
        System.out.println("Gegnerb.: nach 3 Drehungen in Uhr: "+map[x][y].getDirection());
        System.out.println("Gegnerb.: nach 3 Drehungen in Uhr: x: " +x);
        System.out.println("Gegnerb.: nach 3 Drehungen in Uhr: y: " +y);
    }


    //Ein Gegner hinterlässt beim Verlassen eines Feldes einen Path (ohne Direction, Moved-Value wird gesetzt)
    private void resetOrigin (int x, int y){
        map[x][y].setToken(PATH);
        map[x][y].setMoved(1);
        map[x][y].setDirection(NO);
    }


    //ME wird getroffen
    private void bump (int x, int y, int richtung, Gegenstand gegner) {
        //setEnvironement(x,y);
        //ist die alte Direction OSTEN oder WESTEN
        if (map[x][y].getDirection().equals(OSTEN) || map[x][y].getDirection().equals(WESTEN)) {
            //und die neue Richtung vorne oder hinten
            if (richtung==vorne || richtung==hinten) {
                //stoße mit ME, das sich in x-Richtung befindet, zusammen
                bumpWithoutLooking(richtung,y,gegner);

            //oder die neue Richtung rechteHand oder linkeHand ist
            } else {
                //stoße mit ME, das sich in y-Richtung befindet, zusammen
                bumpWithoutLooking(x,richtung,gegner);
            }

        //oder ist die alte Direction NORDEN oder SUEDEN
        } else {
            //und die neue Richtung rechteHand oder linkeHand
            if (richtung==hori1 || richtung==hori2) {
                //stoße mit ME, das sich in x-Richtung befindet, zusammen
                bumpWithoutLooking(richtung,y,gegner);

            //oder die neue Richtung vorne oder hinten
            } else {
                //stoße mit ME, das sich in y-Richtung befindet, zusammen
                bumpWithoutLooking(y,richtung,gegner);
            }
        }
    }

    private void bumpWithoutLooking (int x, int y, Gegenstand gegner){
        setRichtungen(x,y);

        //ist der Gegner ein Xling oder SWAPLING, so verwandle alle acht Felder um das ME herum und auch das ME in GEMs
        if (gegner.equals(XLING) || gegner.equals(SWAPLING)){
            transformToGem(w,n);
            transformToGem(w,y);
            transformToGem(w,s);

            transformToGem(x,n);
            transformToGem(x,y);
            transformToGem(x,s);

            transformToGem(o,n);
            transformToGem(o,y);
            transformToGem(o,s);

        //ist der Gegner ein BLOCKLING, so verwndle alle acht Felder um das ME herum und auch das ME in STONEs
        } else if (gegner.equals(BLOCKLING)) {
            transformToStone(w,n);
            transformToStone(w,y);
            transformToStone(w,s);

            transformToStone(x,n);
            transformToStone(x,y);
            transformToStone(x,s);

            transformToStone(o,n);
            transformToStone(o,y);
            transformToStone(o,s);
        }
    }

    //was passiert, wenn ME auf einen XLING oder einen SWAPLING stößt
    private void transformToGem (int x, int y){
        //ist auf dem Feld keine WALL und auch kein EXIT, so setze darauf ein GEM, setze den moved-value und setze den direction-value auf 0
        if (checkWallExit(x,y)) {
            map[x][y].setToken(GEM);
            map[x][y].setMoved(1);
            map[x][y].setDirection(NO);
        }
    }

    //was passiert, wenn ME auf einen BLOCKLING stößt
    private void transformToStone (int x, int y) {
        //ist auf dem Feld keine WALL und auch kein EXIT, so setze darauf ein STONE, setze den moved-value und setze den direction-value auf 0
        if (checkWallExit(x,y)) {
            map[x][y].setToken(STONE);
            map[x][y].setMoved(1);
            map[x][y].setDirection(NO);
        }
    }

    //Damit eine Verwandlung beim Aufeinandertreffen eines Gegners, mit einem Spieler nicht eine WALL oder ein EXIT verwandelt
    //Checke, ob auf einem Feld eine WALL oder ein EXIT liegt
    private boolean checkWallExit (int x, int y){
        return (!(map[x][y].getToken().equals(WALL) || map[x][y].getToken().equals(EXIT)));
    }


    //Methode wird in LevelModel aufgerufen, wenn ein Gegenstand ein SWAPLING ist
    public void swapling (int x, int y) {
        setEnvironement(x,y);
        currentDirection=map[x][y].getDirection();
        System.out.println("Gegnerb.: Direction des SWAPLING: "+currentDirection);
        //ist vorne ein PATH
        if (checkRowOfTwoToken(x, y, vorne, SWAPLING, PATH)) {
            //setze SWAPLING nach vorne, Richtung bleibt gleich, altes Feld wird zurück gesetzt
            setResult(x, y, vorne, SWAPLING, currentDirection, 0);
            resetOrigin(x, y);

        //ist vorne ein ME
        } else if (checkRowOfTwoToken(x,y,vorne,SWAPLING,ME)) {
            //stoße mit ME zusammen
            bump(x, y, vorne, SWAPLING);

        //ist vorne eine BLOCKADE
        } else {
            //setze SWAPLING nach hinten und drehe um 180°, altes Feld wird zurück gesetzt
            setResult(x, y, hinten, SWAPLING, currentDirection, 2);
            resetOrigin(x, y);
        }
    }


    //Methode wird in LevelModel aufgerufen, wenn ein Gegenstand ein XLING oder ein BLOCKLING ist
    public void xlingOrBlockling (int x, int y, Gegenstand gegner) {
        currentDirection=map[x][y].getDirection();
        setEnvironement(x,y);

        //für Xling ist hori1 rechteHand, für Blockling ist hori1 linkeHand
        //weitere Beschreibungen sind aus der Sicht eines Xlings geschrieben
        //eine BLOCKADE ist alles bis auf ein PATH oder ein ME

        //rechts ist ein PATH
        if (checkRowOfTwoToken(x, y, hori1, gegner, PATH)) {
            //rechts hinten ist ein PATH oder ein ME
            if (checkTwoToken(x, y, hori1, hinten, gegner, PATH) || checkTwoToken(x, y, hori1, hinten, gegner, ME)) {
                //gehe eins nach rechts und drehe dich um 180°
                setResult(x, y, hori1, gegner, currentDirection, 2);
            } else {
                //rechts hinten ist eine BLOCKADE, gehe eins nach rechts, drehe dich nach rechts
                setResult(x, y, hori1, gegner, currentDirection, 1);
            }
            //setze das alte Feld zurück
            resetOrigin(x, y);

        //rechts ist ein ME, stoße mit ihm zusammen
        } else if (checkRowOfTwoToken(x, y, hori1, gegner, ME)) {
            bump(x, y, hori1, gegner);

        //rechts ist eine BLOCKADE und...
        } else {

            //vorne ist ein PATH
            if (checkRowOfTwoToken(x, y, vorne, gegner, PATH)) {
                //rechts vorne ist ein PATH oder ein ME, gehe nach vorne und drehe dich nach rechts
                if (checkTwoToken(x,y,hori1,vorne,gegner,PATH) || checkTwoToken(x,y,hori1,vorne,gegner,ME)) {
                    setResult(x, y, vorne, gegner, currentDirection, 1);
                } else {
                    //rechts vorne ist eine BLOCKADE, gehe nach vorne, behalte die Richtung
                    setResult(x, y, vorne, gegner, currentDirection, 0);
                }
                resetOrigin(x, y);

            //vorne ist ein ME, stoße zusammen
            } else if (checkRowOfTwoToken(x, y, vorne, gegner, ME)) {
                bump(x, y, vorne, gegner);

            //vorne ist eine BLOCKADE und...
            } else {

                //links ist ein PATH
                if (checkRowOfTwoToken(x, y, hori2, gegner, PATH)) {

                    //links vorne ist ein PATH oder ein ME, gehe nach links und behalte die Richtung
                    if (checkTwoToken(x, y, hori2, vorne, gegner, PATH) || checkTwoToken(x, y, hori2, vorne, gegner, ME)) {
                        setResult(x, y, hori2, gegner, currentDirection, 0);
                    } else {
                        //links vorne ist eine BLOCKADE, gehe nach links und drehe nach links
                        setResult(x, y, hori2, gegner, currentDirection, 3);
                        /*map[x][y+1].setToken(XLING);
                        map[x][y+1].setMoved(1);
                        map[x][y+1].setDirection(SUEDEN);
                        */
                    }
                    resetOrigin(x, y);

                //links ist ein ME, stoße zusammen
                } else if (checkRowOfTwoToken(x, y, hori2, gegner, ME)) {
                    bump(x, y, hori2, gegner);

                //links ist eine BLOCKADE und...
                } else {

                    //hinten ist ein PATH
                    if (checkRowOfTwoToken(x, y, hinten, gegner, PATH)) {
                        //links hinten ist ein PATH oder ME, gehe nach hinten und drehe nach links
                        if (checkTwoToken(x, y, hori2, hinten, gegner, PATH) || checkTwoToken(x, y, hori2, hinten, gegner, ME)) {
                            setResult(x, y, hinten, gegner, currentDirection, 3);
                        } else {
                            //links hinten ist eine BLOCKADE, gehe nach hinten ud drehe um 180°
                            setResult(x, y, hinten, gegner, currentDirection, 2);
                        }
                        resetOrigin(x, y);

                    //hinten ist ein ME, stoße zusammen
                    } else if (checkRowOfTwoToken(x, y, hinten, gegner, ME)) {
                        bump(x, y, hinten, gegner);

                    //hinten ist eine BLOCKADE, drehe nach links
                    } else {
                        map[x][y].setMoved(1);
                        setDirection3InUhr(x,y,currentDirection);
                    }
                }
            }
        }
    }



    public void update(Observable o, Object arg) {}

}
