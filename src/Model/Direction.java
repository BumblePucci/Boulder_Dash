package Model;

public enum Direction {
    NO(0), OSTEN(1), NORDEN(2), WESTEN(3), SUEDEN(4);

    private final int number;

    private Direction (int number){
        this.number=number;
    }

    public int getNumber() {
        return number;
    }
}
