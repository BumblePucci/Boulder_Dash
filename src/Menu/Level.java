package Menu;

/**
 * Created by sakinakamilova on 04.02.18.
 */
public class Level {
    public String levelName;
    public int levelScore;
    public Boolean levelPlayed;

    public Level(String levelName, int levelScore, boolean levelPlayed) {
        this.levelName = levelName;
        this.levelScore = levelScore;
        this.levelPlayed = levelPlayed;

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
}
