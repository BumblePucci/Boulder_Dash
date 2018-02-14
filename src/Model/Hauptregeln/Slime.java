package Model.Hauptregeln;

import Model.Feld;
import Model.Gegenstand;

import java.util.Observable;
import java.util.Observer;

public class Slime implements Observer {

    private Feld[][] map;

    public Slime (Feld[][] map){

        this.map=map;
    }

    //abfragen, wie Umgebung sein muss, damit ein Slime neu entsteht
    private boolean checkIfTurnToSlimeHori (int y, int richtung) { //abfragen, was bei x und y da ist
        return (map[richtung][y].getMoved() == 0 && (map[richtung][y].getToken().equals(Gegenstand.MUD) || map[richtung][y].getToken().equals(Gegenstand.PATH)));
    }

    private boolean checkIfTurnToSlimeVerti (int x, int richtung) {
        return (map [x][richtung].getMoved() == 0 && (map[x][richtung].getToken().equals(Gegenstand.MUD) || map[x][richtung].getToken().equals(Gegenstand.PATH)));
    }

    private boolean checkIfTurnToGemHori (int y, int richtung) { //abfragen, was bei x und y da ist
        return (map[richtung][y].getMoved() == 0 && (map[richtung][y].getToken().equals(Gegenstand.SWAPLING) || map[richtung][y].getToken().equals(Gegenstand.XLING)));
    }

    private boolean checkIfTurnToGemVerti (int x, int richtung) {
        return (map [x][richtung].getMoved() == 0 && (map[x][richtung].getToken().equals(Gegenstand.SWAPLING) || map[x][richtung].getToken().equals(Gegenstand.XLING)));
    }

    private boolean checkIfTurnToExplosionHori (int y, int richtung) { //abfragen, was bei x und y da ist
        return (map[richtung][y].getMoved() == 0 && map[richtung][y].getToken().equals(Gegenstand.BLOCKLING));
    }

    private boolean checkIfTurnToExplosionVerti (int x, int richtung) { //abfragen, was bei x und y da ist
        return (map[x][richtung].getMoved() == 0 && map[x][richtung].getToken().equals(Gegenstand.BLOCKLING));
    }




    //Methode, wie sich ein Slime ausbreitet
    public void spreadSlime (int x, int y){
        int rechts = x+1;
        int links = x-1;
        int oben = y-1;
        int unten = y+1;

        if(checkIfTurnToSlimeHori(y,rechts) && Math.random()<=0.03){
            map[rechts][y].setToken(Gegenstand.SLIME);
            map[rechts][y].setMoved(1);
        }

        if(checkIfTurnToSlimeHori(y,links) && Math.random()<=0.03){
            map[links][y].setToken(Gegenstand.SLIME);
            map[links][y].setMoved(1);
        }

        if(checkIfTurnToSlimeVerti(x,oben) && Math.random()<=0.03){
            map[x][oben].setToken(Gegenstand.SLIME);
            map[x][oben].setMoved(1);
        }

        if(checkIfTurnToSlimeVerti(x,unten) && Math.random()<=0.03){
            map[x][unten].setToken(Gegenstand.SLIME);
            map[x][unten].setMoved(1);
        }

        //if(checkIfTurnToGemHori(y,rechts) && Math.random()<=0.03){
          //  map[]
        //}

    }

    public boolean checkIfTurnToGems(int x,int y){
        int rechts = x+1;
        int links = x-1;
        int oben = y-1;
        int unten = y+1;
        return (map[x][y].getMoved()==0 && map[rechts][y].getMoved()==0 && map[links][y].getMoved()==0 &&
                map[x][oben].getMoved()==0 && map[x][unten].getMoved()==0 &&

                map[x][y].getToken().equals(Gegenstand.SLIME) &&
                !(map[rechts][y].getToken().equals(Gegenstand.PATH) || map[rechts][y].getToken().equals(Gegenstand.MUD) ||
                        map[rechts][y].getToken().equals(Gegenstand.SWAPLING) || map[rechts][y].getToken().equals(Gegenstand.XLING) ||
                        map[rechts][y].getToken().equals(Gegenstand.BLOCKLING)) &&

                !(map[links][y].getToken().equals(Gegenstand.PATH) || map[links][y].getToken().equals(Gegenstand.MUD) ||
                        map[links][y].getToken().equals(Gegenstand.SWAPLING) || map[links][y].getToken().equals(Gegenstand.XLING) ||
                        map[links][y].getToken().equals(Gegenstand.BLOCKLING)) &&

                !(map[x][oben].getToken().equals(Gegenstand.PATH) || map[x][oben].getToken().equals(Gegenstand.MUD) ||
                        map[x][oben].getToken().equals(Gegenstand.SWAPLING) || map[x][oben].getToken().equals(Gegenstand.XLING) ||
                        map[x][oben].getToken().equals(Gegenstand.BLOCKLING)) &&

                !(map[x][unten].getToken().equals(Gegenstand.PATH) || map[x][unten].getToken().equals(Gegenstand.MUD) ||
                        map[x][unten].getToken().equals(Gegenstand.SWAPLING) || map[x][unten].getToken().equals(Gegenstand.XLING) ||
                        map[x][unten].getToken().equals(Gegenstand.BLOCKLING))
                );
    }


    public void update(Observable o, Object arg) {}
}