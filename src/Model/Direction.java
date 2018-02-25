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

    public Direction rotateClockwise() {
        switch (this) {
            case OSTEN:
                return SUEDEN;
            case NORDEN:
                return OSTEN;
            case SUEDEN:
                return WESTEN;
            case WESTEN:
                return NORDEN;
            default:
                return NO;
        }
    }
}
