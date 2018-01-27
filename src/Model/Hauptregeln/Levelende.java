package Model.Hauptregeln;

import Model.LevelModel;
import org.JSONObject;

import java.util.Observable;
import java.util.Observer;

import java.io.FileWriter;
import  java.io.IOException;

public class Levelende implements Observer {

    private boolean doorOpen = false;
    private static int mPunkte = 3;
    private int p1 = 0;
    private int p2 = 0;
    private int pGes = 100;
    private LevelModel levelModel;
    private Spielerbewegung spielerbewegung;
    private int aktuellerSpielstand;
    private JSONObject stand;


    public Levelende(LevelModel levelModel, Spielerbewegung spielerbewegung) {
        int[] gems = levelModel.getGems();
        int[] ticks = levelModel.getTicks();
        this.levelModel = levelModel;
        this.spielerbewegung = spielerbewegung;
        aktuellerSpielstand = 1;
        JSONObject stand = new JSONObject();
        safe();
        System.out.println("Start");
    }

    //Alle getter-Methoden
    public boolean isDoorOpen() { return doorOpen; }
    public int getpGes() { return pGes; }

    //öffnet Tür wenn min Gem erreicht.
    // Sollte die erste Stelle des entsprechenden Gem Array übergeben bekommen
    public void openDoor(int gems){
     if(spielerbewegung.getGemcounter() == gems){
         doorOpen = true;
     }
    }

    // legt Punkte fest. Muss Array übergeben bekommen bei Aufruf
    public void bewertung(int [] gems){
        int count1 = 0;
        int count2 = 0;
        for(int i = 0; i < gems.length; i++){
            if(gems[i] <= spielerbewegung.getGemcounter()) {
                p1 = mPunkte - count1;
                break;
            }
                    else{
                count1++;
            }
        }
        // NOchmal mit Zeit
        //for...

        if(p1> p2) {
            pGes = p2;
        }
        else {
            pGes = p1;
        }
    }

    //Speichert Spielstände nach jedem Level
    public void safe () {
        stand.put("punkte", pGes);
        if (aktuellerSpielstand == 1) {
            try {
                FileWriter file1 = new FileWriter("C:\\Users\\Tanja\\Desktop\\JSON\\Spielstand1.json");
                file1.write(stand.toString());
                System.out.println("Successfully Copied JSON Object to File...");
            }
            catch (IOException e){
                e.printStackTrace();
                System.out.println("Failed");
            }
        }
        else{
            System.out.println("Else");
        }
        //ToDo wenn 1 funktioniert 2 und 3 abdecken;
    }
    // resettet alle Werte
    public void resetAll() {
        doorOpen = false;
        p1 = 0;
        p2 = 0;
        pGes = 0;
    }

    @Override
    public void update(Observable o, Object arg) {

    }
}
