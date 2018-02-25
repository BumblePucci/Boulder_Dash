package Menu;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Observable;

/**
 * Created by sakinakamilova on 28.01.18.
 */
public class MenuModel extends Observable {
    private int punkte;
    public ArrayList<Level> levelList;


    public MenuModel() {
     //levelinfos muss hier hin
        int numberOfLevels = 12;
       // ArrayList<Level> levelList = new ArrayList<>();
        this.levelList = new ArrayList<>();
        //Liest Ordner mit Leveln ein
        try {
            File f = new File("./src/JSONLevels/");
            File [] fileArray = f.listFiles();
            //erstellt entsprechende Level
            for(int i = 0; i < fileArray.length ; i++){
                String name = fileArray[i].getName();
                String ausgabe = name.substring(0, name.indexOf('.'));
                Level level = new Level(ausgabe,0,true);
                level.setLevelNummer(i);
                levelList.add(level);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        freischalten(0); // macht das erste Level spielbar
    }

    public void freischalten(int i){
        levelList.get(i).setLevelPlayed(true);
    }


    public void addScore(int i) {
        punkte = punkte + i;
    }

    public int getPunkte() {
        return punkte;
    }

}
