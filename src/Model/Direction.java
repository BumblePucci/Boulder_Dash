package Model;

public enum Direction {
    NO(0),RECHTS(1),OBEN(2),LINKS(3),UNTEN(4);

    private final int number;

    private Direction (int number){
        this.number=number;
    }

    public int getNumber() {
        return number;
    }
}
