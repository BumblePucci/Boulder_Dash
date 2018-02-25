package Model;

public enum Pfeil {
    NO(), RIGHT(),LEFT(),UP(),DOWN(),SRIGHT(), SLEFT(), SUP(), SDOWN();

    private Pfeil () {}

    //Getter für die gedrückte Pfeiltaste, unabhängig davon, ob auch die Shift-Taste gedrückt wird
    public Pfeil getWithoutShift() {
        switch (this) {
            case SRIGHT:
                return RIGHT;
            case SLEFT:
                return LEFT;
            case SUP:
                return UP;
            case SDOWN:
                return DOWN;
            default:
                return this;
        }
    }

}
