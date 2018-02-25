package Model.Hauptregeln;

import Model.Feld;
import Model.Gegenstand;

import java.util.Observable;
import java.util.Observer;

import static Model.Gegenstand.*;

public class Slime implements Observer {

    private Feld[][] map;

    private int currentPosX;
    private int currentPosY;

    private int dirX;
    private int dirY;

    private int o;  //östliches Feld von x,y aus gesehen
    private int w;  //westliches Feld von x,y aus gesehen
    private int n;  //nördliches Feld von x,y aus gesehen
    private int s;  //südliches Feld von x,y aus gesehen

    public Slime (Feld[][] map){

        this.map=map;

        o = 1;
        w = -1;
        n = -1;
        s = 1;
    }

    private void setEnvironement (int x, int y, int dir) {
        currentPosX = x;
        currentPosY = y;

        dirX = 0;
        dirY = 0;

        if (dir==o) {
            dirX = o;
        } else if (dir==w) {
            dirX = w;
        } else if (dir==n) {
            dirY = n;
        } else if (dir==s) {
            dirY = s;
        }
    }

    //checkt in Abhängigkeit der horizontalen bzw. vertikalen Richtung, ob der eingesetzte Gegenstand unbewegt (moved-value=0) auf dem Nachbarfeld liegt
    private boolean checkIfTurns (int x, int y, Gegenstand nachbar) {
        return (map[x][y].getMoved() == 0 && (map[x][y].getToken().equals(nachbar)));
    }

    //Methode, die mit einer Wahrscheinlichkeit von 3% auf ein Feld, in Abhängigkeit des Gegenstandes auf dem Feld, (einen) neue(n) Gegenstand(e) setzt
    private void spreadOne (int x, int y, int dir) {
        setEnvironement(x, y, dir);

        int newX = currentPosX + dirX;
        int newY = currentPosY + dirY;
        System.out.println("Slime: dir: "+dir);
        System.out.println("Slime: currentPosX: "+currentPosX);
        System.out.println("Slime: currentPosY: "+currentPosY);
        System.out.println("Slime: newX: "+newX);
        System.out.println("Slime: newY: "+newY);

        if (Math.random() <= 0.03) {
            //bei PATH / MUD...
            if (checkIfTurns(newX, newY, PATH) || checkIfTurns(newX, newY, MUD)) {
                //...wird das Nachbarfeld zum Slime
                System.out.println("Slime: Nachbar ist ein PATH oder ein MUD");
                setResult(newX,newY,SLIME);

            //bei SWAPLING / XLING...
            } else if (checkIfTurns(newX, newY, SWAPLING) || checkIfTurns(newX, newY, XLING)) {
                //...entstehen um das Nachbarfeld herum und auf dem Nachbarfeld lauter GEMs
                turnNine(newX, newY, GEM);

            //bei BLOCKLING...
            } else if (checkIfTurns(newX, newY, BLOCKLING)) {
                //...entstehen um das Nachbarfeld herum und auf dem Nachbarfeld lauter EXPLOSIONs
                turnNine(newX, newY, EXPLOSION);
            }
        }

    }




    //verwandelt alle Felder um ein Feld herum und das Feld selbst in einen neuen Gegenstand "type"
    private void turnNine (int x, int y, Gegenstand type) {
        int rechts = x+1;
        int links = x-1;
        int oben = y-1;
        int unten = y+1;

        setResult(rechts,oben,type);
        setResult(rechts,y,type);
        setResult(rechts,unten,type);

        setResult(x,oben,type);
        setResult(x,y,type);
        setResult(x,unten,type);

        setResult(links,oben,type);
        setResult(links,y,type);
        setResult(links,unten,type);
    }

    //verwandelt ein Feld in ein neues Feld und setzt den moved-value
    private void setResult (int x, int y, Gegenstand type) {
        map[x][y].setToken(type);
        map[x][y].setMoved(1);
    }


    //Methode, wie sich ein Slime ausbreitet
    public void spreadSlime (int x, int y){
        //pro tick ist es Möglich, dass sich in jede Richtung ein Slime ausbreitet oder ein Gegenstand durch den Slime daneben auf andere Weise verwandelt wird
        spreadOne(x,y,o);
        spreadOne(x,y,w);
        spreadOne(x,y,n);
        spreadOne(x,y,s);
    }


    //Methode, um fest zu stellen, ob alle Felder eingeschlossen sind (nicht weiter verbessert -> Warten bis Regression beschrieben wird)
    //Wenn in jeder x- und y-Richtung weder ein PATH noch ein MUD oder ein SWAPLING, oder ein XLING oder ein BLOCKLING liegt
    //Dann kann in LevelModel für dieses eine Feld true zurück gegeben werden
    public boolean checkIfTurnToGems(int x,int y){
        int o = x+1;
        int w = x-1;
        int n = y-1;
        int s = y+1;
        return (map[x][y].getMoved()==0 && map[o][y].getMoved()==0 && map[w][y].getMoved()==0 &&
                map[x][n].getMoved()==0 && map[x][s].getMoved()==0 &&

                map[x][y].getToken().equals(Gegenstand.SLIME) &&
                !(map[o][y].getToken().equals(Gegenstand.PATH) || map[o][y].getToken().equals(Gegenstand.MUD) ||
                        map[o][y].getToken().equals(Gegenstand.SWAPLING) || map[o][y].getToken().equals(Gegenstand.XLING) ||
                        map[o][y].getToken().equals(Gegenstand.BLOCKLING)) &&

                !(map[w][y].getToken().equals(Gegenstand.PATH) || map[w][y].getToken().equals(Gegenstand.MUD) ||
                        map[w][y].getToken().equals(Gegenstand.SWAPLING) || map[w][y].getToken().equals(Gegenstand.XLING) ||
                        map[w][y].getToken().equals(Gegenstand.BLOCKLING)) &&

                !(map[x][n].getToken().equals(Gegenstand.PATH) || map[x][n].getToken().equals(Gegenstand.MUD) ||
                        map[x][n].getToken().equals(Gegenstand.SWAPLING) || map[x][n].getToken().equals(Gegenstand.XLING) ||
                        map[x][n].getToken().equals(Gegenstand.BLOCKLING)) &&

                !(map[x][s].getToken().equals(Gegenstand.PATH) || map[x][s].getToken().equals(Gegenstand.MUD) ||
                        map[x][s].getToken().equals(Gegenstand.SWAPLING) || map[x][s].getToken().equals(Gegenstand.XLING) ||
                        map[x][s].getToken().equals(Gegenstand.BLOCKLING))
                );
    }


    public void update(Observable o, Object arg) {}
}