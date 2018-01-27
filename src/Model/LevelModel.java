package Model;

import Model.Hauptregeln.Explosion;
import Model.Hauptregeln.Gegnerbewegung;
import Model.Hauptregeln.Gravitation;
import Model.Hauptregeln.Spielerbewegung;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Observable;

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


    public LevelModel(String name, int width, int height, int[] gems, int[] ticks, List<List<String>> pre,
                      List<List<String>> post, int maxslime, Feld [][] map){
        this.name=name;
        this.width=width;
        this.height=height;
        this.gems=gems;
        this.ticks=ticks;
        this.pre=pre;
        this.post=post;
        this.maxslime=maxslime;
        this.map=map;

        gemcounter = 0;
        tick = 1;

        spielerbewegung = new Spielerbewegung(map,gemcounter);
        gravitation = new Gravitation(map);
        gegnerbewegung = new Gegnerbewegung(map);
        explosion = new Explosion(map);
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

    public int[] getGems() {
        return gems;
    }

    public int getGemcounter() {
        return gemcounter;
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
//TODO: eine Klasse nur für die Levelübersicht

    //TODO?: Ausgangssituation anzeigen, vor den Ticks

    //TODO: hier alle Methoden in der richtigen Reihenfolge rein schreiben, die in einem Tick ausgeführt werden auch pre / post

    //Setzt alle Values(Zusatzwerte) zu Beginn eines Ticks zurück
    public void reset(){
        right = false;
        left = false;
        up = false;
        down = false;

        sRight = false;
        sLeft = false;
        sUp = false;
        sDown = false;

        for (int i=0; i<height; i++) {
            for (int j = 0; j < width; j++) {
                int x = j;
                int y = i;
                map[x][y].setMoved(0);
                map[x][y].setFalling(0);
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
                map[x][y].setBam(0);
                map[x][y].setBamrich(0);
            }
        }
    }

    //TODO: pre-Regel-Metode

    //TODO: right left up down und shift-"- hier vor der update()-Methode setzen
    private boolean right = false;
    private boolean left = false;
    private boolean up = false;
    private boolean down = false;

    private boolean sRight = false;
    private boolean sLeft = false;
    private boolean sUp = false;
    private boolean sDown = false;

    public void setRight(boolean right) {
        this.right = right;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public void setDown(boolean down) {
        this.down = down;
    }

    public void setsRight(boolean sRight) {
        this.sRight = sRight;
    }

    public void setsLeft(boolean sLeft) {
        this.sLeft = sLeft;
    }

    public void setsUp(boolean sUp) {
        this.sUp = sUp;
    }

    public void setsDown(boolean sDown) {
        this.sDown = sDown;
    }

    //Methode um später zu checken, ob die Felder innerhalb der Grenzen sind
    private boolean inBoundHori (int richtung){
        return (richtung >= 0) && (richtung<width);
    }

    private boolean inBoundVerti (int richtung){
        return (richtung >=0) && (richtung<height);
    }

    public void update(){
        for (int i=0; i<height; i++){
            for (int j=0; j<width; j++) {
                int x = j;
                int y = i;
                int rechts = x+1;
                int links = x-1;
                int oben = y-1;
                int unten = y+1;

                if (map[x][y].getMoved()==0) {
                    if (map[x][y].getToken().equals(Gegenstand.ME)) {
                        if (sRight) {
                            spielerbewegung.dig(x, y, rechts);
                            spielerbewegung.gemDig(x, y, rechts);
                            setChanged();
                            notifyObservers();

                        } else if (right) {
                            spielerbewegung.walk(x, y, rechts);
                            spielerbewegung.gemWalk(x, y, rechts);
                            spielerbewegung.moveThing(x, y, rechts);
                            setChanged();
                            notifyObservers();

                        } else if (sLeft) {
                            spielerbewegung.dig(x, y, links);
                            spielerbewegung.gemDig(x, y, links);
                            setChanged();
                            notifyObservers();

                        } else if (left) {
                            spielerbewegung.walk(x, y, links);
                            spielerbewegung.gemWalk(x, y, links);
                            spielerbewegung.moveThing(x, y, links);
                            setChanged();
                            notifyObservers();

                        } else if (sUp) {
                            spielerbewegung.dig(x, y, oben);
                            spielerbewegung.gemDig(x, y, oben);
                            setChanged();
                            notifyObservers();

                        } else if (up) {
                            spielerbewegung.walk(x, y, oben);
                            spielerbewegung.gemWalk(x, y, oben);
                            spielerbewegung.moveThing(x, y, oben);
                            setChanged();
                            notifyObservers();

                        } else if (sDown) {
                            spielerbewegung.dig(x, y, unten);
                            spielerbewegung.gemDig(x, y, unten);
                            setChanged();
                            notifyObservers();
                        } else if (down) {
                            spielerbewegung.walk(x, y, unten);
                            spielerbewegung.gemWalk(x, y, unten);
                            spielerbewegung.moveThing(x, y, unten);
                            setChanged();
                            notifyObservers();
                        }
                    }

                    if (map[x][y].getLoose() == 1) {
                        gravitation.fall(x, y);
                        gravitation.fallAskew(x, y);
                        gravitation.strike(x, y);
                        setChanged();
                        notifyObservers();
                    }

                    if (map[x][y].getToken().equals(Gegenstand.SWAPLING)) {
                        gegnerbewegung.swapling(x, y);
                        setChanged();
                        notifyObservers();
                    }
                    if (map[x][y].getToken().equals(Gegenstand.XLING)) {
                        gegnerbewegung.xling(x, y);
                        setChanged();
                        notifyObservers();

                    }

                    if (map[x][y].getToken().equals(Gegenstand.BLOCKLING)) {
                        gegnerbewegung.blockling(x,y);
                        setChanged();
                        notifyObservers();

                    }

                    if (map[x][y].getToken().equals(Gegenstand.EXPLOSION)) {
                        explosion.endOrExplode(x, y);
                        setChanged();
                        notifyObservers();

                    }

                }
            }
        }

    }
    //TODO: post-Methode
    public String toString(){
        String s ="";
        for(int i=0; i<height; i++){
            s+="|";
            for(int j = 0; j<width; j++){
                s=s+ map[j][i].getToken() + "|";
            }
            s+="\n";
        }
        return s;
    }
}
