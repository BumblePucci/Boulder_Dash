package Model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Observable;

public class LevelModel extends Observable {

    public String name;
    public int width;
    public int height;
    public int[] gems = new int[3];
    public int gemcounter;
    public int[] ticks = new int[3];
    public List<List<String>> pre = new ArrayList<>();  //Sind es wirklich STRINGlisten?
    public List<List<String>> post = new ArrayList<>();
    public int maxslime;
    private Feld[][] map = new Feld[width][height];



    public Feld[][] getMap() {
        return map;
    }

    public void setMap(Feld[][] map) {
        this.map = map;
    }

    public double tick = 1;
    //TODO: eine Klasse nur für die Levelübersicht

    //TODO: Ausgangssituation anzeigen, vor den Ticks

    //TODO: hier alle Methoden in der richtigen Reihenfolge rein schreiben, die in einem Tick ausgeführt werden (auch pre / post?)
    public void update(){
        //System.out.println(Arrays.toString(this.gems));
    }
}
