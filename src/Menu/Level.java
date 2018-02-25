package Menu;

import Model.Feld;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sakinakamilova on 04.02.18.
 */
public class Level {
    public String levelName;
    public int levelScore;
    public Boolean levelPlayed;

    public int levelNummer;


    private int minGems;
    private int midGems;
    private int maxGems;
    private int minTime;
    private int midTime;
    private int maxTime;


    public Level(String levelName, int levelScore, boolean levelPlayed) {
        //Initialisierungen
        this.levelName = levelName;
        this.levelScore = levelScore;
        this.levelPlayed = levelPlayed;

        this.levelNummer = levelNummer;


       //todo werte die ausgelesen werden
        minGems = 1;
        midGems = 2;
        maxGems = 3;
        minTime = 1000;
        midTime = 500;
        maxTime = 100;



        //Todo Einlesen aus main implementieren

    }

    public String getLevelName() {
        return levelName;
    }

    public int getLevelScore() {
        return levelScore;
    }

    public Boolean getLevelPlayed() {
        return levelPlayed;
    }

    public int getMinGems() { return minGems; }

    public void setMinGems(int minGems) { this.minGems = minGems; }

    public int getMidGems() { return midGems; }

    public void setMidGems(int midGems) { this.midGems = midGems; }

    public int getMaxGems() { return maxGems; }

    public void setMaxGems(int maxGems) { this.maxGems = maxGems; }

    public int getMinTime() { return minTime; }

    public void setMinTime(int minTime) { this.minTime = minTime; }

    public int getMidTime() { return midTime; }

    public void setMidTime(int midTime) { this.midTime = midTime; }

    public int getMaxTime() { return maxTime; }

    public void setMaxTime(int maxTime) { this.maxTime = maxTime; }

    public void setLevelName(String levelName) { this.levelName = levelName; }

    public void setLevelScore(int levelScore) { this.levelScore = levelScore; }

    public void setLevelPlayed(Boolean levelPlayed) { this.levelPlayed = levelPlayed; }

    public int getLevelNummer() { return levelNummer; }

    public void setLevelNummer(int levelNummer) { this.levelNummer = levelNummer; }
}
