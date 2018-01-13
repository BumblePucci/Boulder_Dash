package Model;

public enum Gegenstand {
    ME("me"), MUD("mud"), STONE("stone"), GEM("gem"), EXIT("exit"),
    WALL("wall"), BRICKS("bricks"), PATH("path"), EXPLOSION("explosion"), SLIME("slime"),
    SWAPLING("swapling"), BLOCKLING("blockling"), XLING("xling"),
    GHOSTLING("ghostling"), FIRE("fire"), NORTHTHING("norththing"),
    EASTTHING("eastthing"), SOUTHTHING("souththing"), WESTTHING("westthing"),
    POT("pot"), SIEVE("sieve"), SAND("sand");

    private final String typ;

    private Gegenstand (String typ){
        this.typ=typ;
    }

    public String getTyp() {
        return typ;
    }
}
