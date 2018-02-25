package Model;

import javafx.scene.image.Image;

public enum Gegenstand {
    ME("me", "./resources/me.png"), MUD("mud", "./resources/mud.png"), STONE("stone", "./resources/stone.png"), GEM("gem","./resources/gem.png"), EXIT("exit", "./resources/exit.png"),
    WALL("wall", "./resources/wall.png"), BRICKS("bricks", "./resources/brick.png"), PATH("path", "./resources/path.png"), EXPLOSION("explosion", "./resources/explosion.jpg"), SLIME("slime", "./resources/slime.png"),
    SWAPLING("swapling", "./resources/swapling.png"), BLOCKLING("blockling", "./resources/blockling.png"), XLING("xling", "./resources/xling.png"),
    GHOSTLING("ghostling", "./resources/ghostling.jpg"), FIRE("fire", "./resources/fire.jpg"), NORTHTHING("norththing", "./resources/norththing.jpg"),
    EASTTHING("eastthing", "./resources/eastthing.jpg"), SOUTHTHING("souththing", "./resources/souththing.jpg"), WESTTHING("westthing" , "./resources/westthing.jpg"),
    POT("pot", "./resources/pot.jpg"), SIEVE("sieve", "./resources/sieve.jpg"), SAND("sand", "./resources/sand.jpg");

    private final String typ;
    private final String bildPath;
    private final Image img;
    Gegenstand (String typ, String bildPath){
        this.typ=typ;
        this.bildPath = bildPath;
        this.img = new Image("file:" + bildPath);
    }

    public String getTyp() {
        return typ;
    }

    public Image getImg() {
        return img;
    }
}
