package Model;

import javafx.scene.image.Image;

public enum Gegenstand {
    ME("me", "./resources/me.jpg"), MUD("mud", "./resources/mud.jpg"), STONE("stone", "./resources/stone.jpg"), GEM("gem","./resources/gem.jpg"), EXIT("exit", "./resources/exit.jpg"),
    WALL("wall", "./resources/wall.jpg"), BRICKS("bricks", "./resources/brick.jpg"), PATH("path", "./resources/path.jpg"), EXPLOSION("explosion", "./resources/explosion.jpg"), SLIME("slime", "./resources/slime.jpg"),
    SWAPLING("swapling", "./resources/swapling.jpg"), BLOCKLING("blockling", "./resources/blockling.jpg"), XLING("xling", "./resources/xling.jpg"),
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
