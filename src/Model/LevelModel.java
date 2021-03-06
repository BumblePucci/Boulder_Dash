package Model;

import Model.Hauptregeln.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Observable;

import static Model.Pfeil.*;

public class LevelModel extends Observable {

    private final String name;
    private final int width;
    private final int height;
    private final int[] gems;
    private final int[] ticks;
    private final List<List<String>> pre;  //Sind es wirklich STRINGlisten?
    private final List<List<String>> post;
    private final int maxslime;
    private final Feld[][] map;

    private int gemcounter;
    private double tick;

    private Spielerbewegung spielerbewegung;
    private Gravitation gravitation;
    private Gegnerbewegung gegnerbewegung;
    private Explosion explosion;
    private Slime slime;


    public LevelModel(String name, int width, int height, int[] gems, int[] ticks, List<List<String>> pre,
                      List<List<String>> post, int maxslime, Feld[][] map) {
        this.name = name;
        this.width = width;
        this.height = height;
        this.gems = gems;
        this.ticks = ticks;
        this.pre = pre;
        this.post = post;
        this.maxslime = maxslime;
        this.map = map;

        gemcounter = 0;
        tick = 1;

        spielerbewegung = new Spielerbewegung(map, gemcounter, width);
        gravitation = new Gravitation(map);
        gegnerbewegung = new Gegnerbewegung(map);
        explosion = new Explosion(map);
        slime = new Slime(map);
    }

    public String getName() {
        return name;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int[] getGems() { return gems; }

    public int getGemcounter() {
        return spielerbewegung.getGemcounter();
    }

    public int[] getTicks() {
        return ticks;
    }

    public List<List<String>> getPre() {
        return pre;
    }

    public List<List<String>> getPost() {
        return post;
    }

    public int getMaxslime() {
        return maxslime;
    }

    public Feld[][] getMap() {
        return map;
    }

    public double getTick() {
        return tick;
    }

    public void countUp() {
        gemcounter++;
    }

//TODO: eine Klasse nur für die Levelübersicht

    //TODO?: Ausgangssituation anzeigen, vor den Ticks

    //TODO: hier alle Methoden in der richtigen Reihenfolge rein schreiben, die in einem Tick ausgeführt werden auch pre / post

    //Setzt alle Values(Zusatzwerte) zu Beginn eines Ticks zurück
    public void reset(){
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int x = j;
                int y = i;
                map[x][y].setMoved(0);
                //map[x][y].setFalling(0);
                if (map[x][y].getToken().equals(Gegenstand.STONE) || map[x][y].getToken().equals(Gegenstand.GEM)) {
                    map[x][y].setLoose(1);
                } else {
                    map[x][y].setLoose(0);
                }
                if (map[x][y].getToken().equals(Gegenstand.STONE) || map[x][y].getToken().equals(Gegenstand.GEM) ||
                        map[x][y].getToken().equals(Gegenstand.BRICKS)) {
                    map[x][y].setSlippery(1);
                } else {
                    map[x][y].setSlippery(0);
                }
                if (map[x][y].getToken().equals(Gegenstand.STONE)) {
                    map[x][y].setPushable(1);
                } else {
                    map[x][y].setPushable(0);
                }
                //map[x][y].setBam(0);
                //map[x][y].setBamrich(0);
            }
        }
    }

    //TODO: pre-Regel-Metode
    public void pre () {}

    //TODO: right left up down und shift-"- hier vor der update()-Methode setzen

    //Objekt des Enums Pfeil wird erstellt -> Zeigt gedrückte Pfeiltaste an
    private Pfeil pfeil = NO;

    public void setPfeil(Pfeil pfeil) {
        this.pfeil = pfeil;
    }

    public Pfeil getPfeil() {
        return pfeil;
    }

    //Methode um später zu checken, ob die Felder innerhalb der Grenzen sind
    private boolean inBoundHori(int richtung) {
        return (richtung >= 0) && (richtung < width);
    }

    private boolean inBoundVerti(int richtung) {
        return (richtung >= 0) && (richtung < height);
    }


    //TODO: bei allen Hauptregeln nachschauen, wo Richtungen neu gesetzt werden müssen -> weniger ist mehr!
    public void update(){
        System.out.println("Pfeilrichtung: "+pfeil);
        boolean slimeLocked=false;
        for (int i=0; i<height; i++){
            for (int j=0; j<width; j++) {
                int x = j;
                int y = i;
                if (map[x][y].getMoved() == 0) {
                    if (map[x][y].getLoose() == 1) {
                        gravitation.fall(x, y);
                        System.out.println("LevelModel (Gravitation): falling: " + map[x][y + 1].getFalling());
                    }else if (map[x][y].getToken().equals(Gegenstand.ME) && pfeil != Pfeil.NO) {
                        System.out.println("Pfeilrichtung: "+pfeil);
                        spielerbewegung.walk(x, y, pfeil);
                        gravitation.strikeToGems(x, y);
                    } else if (map[x][y].getToken().equals(Gegenstand.SWAPLING)) {
                        gegnerbewegung.swapling(x, y);
                        gravitation.strikeToGems(x, y);

                    } else if (map[x][y].getToken().equals(Gegenstand.XLING)) {
                        System.out.println("Xling-Direction: "+map[x][y].getDirection());
                        gegnerbewegung.xling(x, y);
                        gravitation.strikeToGems(x, y);
                        //System.out.println(map[x][y].getDirection());
                    } else if (map[x][y].getToken().equals(Gegenstand.BLOCKLING)) {
                        gegnerbewegung.blockling(x, y);
                        gravitation.strikeToStones(x, y);
                    } else if (map[x][y].getToken().equals(Gegenstand.EXPLOSION)) {
                        explosion.endOrExplode(x, y);


                    } else if (map[x][y].getToken().equals(Gegenstand.SLIME)) {
                        slime.spreadSlime(x, y);

                        //TODO: mit Rekursion arbeiten
                        /*if (slime.checkIfTurnToGems(x, y)) {
                            slimeLocked = true;
                        } else {
                            slimeLocked = false;
                        }*/

                    }

                }
            }
        }

        /*if (slimeLocked) {
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    int x = j;
                    int y = i;
                    if (map[x][y].getToken().equals(Gegenstand.SLIME)) {
                        map[x][y].setToken(Gegenstand.GEM);
                    }
                }
            }
        }

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int slimeCounter = 0;
                int x = j;
                int y = i;
                if (map[x][y].getToken().equals(Gegenstand.SLIME)) {
                    slimeCounter++;
                    if (slimeCounter > maxslime) {
                        //TODO: Zugriff auf alle Slimes ermöglichen, wieder außerhalb der for-Schleifen?
                        map[x][y].setToken(Gegenstand.STONE);
                    }
                }
            }
        }*/

        setChanged();
        notifyObservers();
    }


    //TODO: post-Methode
    public void post () {}

    public String toString() {
        String s = "";
        for (int i = 0; i < height; i++) {
            s += "|";
            for (int j = 0; j < width; j++) {
                s = s + map[j][i].getToken() + "|";
            }
            s += "\n";
            s += "Gems " + gemcounter;

        }
            return s;

    }
}
