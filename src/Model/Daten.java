package Model;

public class Daten {
    private Boolean[] levelFreigeschaltet;
    private  int anzahlLevel;
    private int punkte;
    private int aktuellesLevel;

    public Daten(){
        anzahlLevel = 5;
        levelFreigeschaltet = new Boolean[getAnzahlLevel()];
        punkte = 0;
        anzahlLevel = 0;
    }

    public Boolean getLevelFreigeschaltet(int i) {
        return levelFreigeschaltet[i];
    }

    public int getAnzahlLevel() {
        return anzahlLevel;
    }

    public int getPunkte() {
        return punkte;
    }

    public int getAktuellesLevel() {
        return aktuellesLevel;
    }

    public void setLevelFreigeschaltet(int level) {
        levelFreigeschaltet[level] = true;
    }

    public void setAnzahlLevel(int anzahlLevel) {
        this.anzahlLevel = anzahlLevel;
    }

    public void setPunkte(int punkte) {
        this.punkte = punkte;
    }

    public void setAktuellesLevel(int aktuellesLevel) {
        this.aktuellesLevel = aktuellesLevel;
    }
}
