package Model;

import java.util.Observable;

//ein einzeles Kästchen im Raster
public class Feld extends Observable {
    private Gegenstand token;
    private int moved;
    private int falling;
    private int loose;
    private int slippery;
    private int pushable;
    private int bam;
    private int bamrich;
    private Direction direction;
    private int a;
    private int b;
    private int c;
    private int d;

    public Feld(Gegenstand token, int moved, int falling, int loose, int slippery, int pushable, int bam, int bamrich, Direction direction, int a, int b, int c, int d) {
        this.token = token;
        this.moved = moved;
        this.falling = falling;
        this.loose = loose;
        this.slippery = slippery;
        this.pushable = pushable;
        this.bam = bam;
        this.bamrich = bamrich;
        this.direction = direction;
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
    }

    public Gegenstand getToken() {
        return token;
    }

    public void setToken(Gegenstand token) {
        this.token = token;
    }

    public int getMoved() {
        return moved;
    }

    public void setMoved(int moved) {
        this.moved = moved;
    }

    public int getFalling() {
        return falling;
    }

    public void setFalling(int falling) {
        this.falling = falling;
    }

    public int getLoose() {
        return loose;
    }

    public void setLoose(int loose) {
        this.loose = loose;
    }

    public int getSlippery() {
        return slippery;
    }

    public void setSlippery(int slippery) {
        this.slippery = slippery;
    }

    public int getPushable() {
        return pushable;
    }

    public void setPushable(int pushable) {
        this.pushable = pushable;
    }

    public int getBam() {
        return bam;
    }

    public void setBam(int bam) {
        this.bam = bam;
    }

    public int getBamrich() {
        return bamrich;
    }

    public void setBamrich(int bamrich) {
        this.bamrich = bamrich;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public int getA() {
        return a;
    }

    public void setA(int a) {
        this.a = a;
    }

    public int getB() {
        return b;
    }

    public void setB(int b) {
        this.b = b;
    }

    public int getC() {
        return c;
    }

    public void setC(int c) {
        this.c = c;
    }

    public int getD() {
        return d;
    }

    public void setD(int d) {
        this.d = d;
    }
}