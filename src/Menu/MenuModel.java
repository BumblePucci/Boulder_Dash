package Menu;

import java.util.ArrayList;
import java.util.Observable;

/**
 * Created by sakinakamilova on 28.01.18.
 */
public class MenuModel extends Observable {

//level infos muss hier hin
    public int numberOfLevels = 12;
    private int punkte = 0;


    public ArrayList<Level> levelList = new ArrayList<>();
    Level l1 = new Level("Bewegung", 3, true);
    Level l2 = new Level("Labyrinth", 3, true);
    Level l3 = new Level("Schleimer", 2, true);
    Level l4 = new Level("Spiegelgeist", 0, false);
    Level l5 = new Level("Text", 0, false);
    Level l6 = new Level("Wand", 0, false);

    public MenuModel() {
        this.levelList.add(l1);
        this.levelList.add(l2);
        this.levelList.add(l3);
        this.levelList.add(l4);
        this.levelList.add(l5);
        this.levelList.add(l6);

    }
    public  void addScore (int i){
        punkte = punkte + i;
    }

    public int getPunkte() { return punkte; }}
