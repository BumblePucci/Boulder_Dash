package Controller;

import Menu.Level;
import Menu.MenuController;
import Model.LevelModel;
import View.LevelView;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import static Model.Pfeil.*;

public class Controller implements Observer {
    private LevelView levelView;
    private LevelModel levelModel;
    private MenuController menuController;
    private Level level;
    private boolean shift;
    private boolean doorIsOpen;
    private  int pGes;
    private boolean levelNichtBesetzt;

    //alle gedrückten Keys, weil es mehrere sein können und manchmal sein müssen, wird hier eine Liste erstellt
    private ArrayList<KeyCode> pressedKeys = new ArrayList<>();


    public Controller(LevelModel levelModel, LevelView levelView) {
        this.levelModel = levelModel;
        this.levelView = levelView;
        this.menuController = menuController;
        this.level = level;
        shift = false;
        doorIsOpen = false;
        pGes = 0;
        this.levelNichtBesetzt = true;


        this.levelView.getStage().addEventHandler(KeyEvent.KEY_PRESSED, ev -> {
            //wenn die Liste der gedrückten Keys den aktuell gedrückten Key nicht enthält, wird dieser hinzugefügt
            if (!pressedKeys.contains(ev.getCode())) {
                pressedKeys.add(ev.getCode());
            }
            //immer wenn ein Key neu gedrückt wird, wird setPfeilOnInput ausgeführt
            setPfeilOnInput();
        });


        this.levelView.getStage().addEventHandler(KeyEvent.KEY_TYPED, ev -> {

        });


        this.levelView.getStage().addEventHandler(KeyEvent.KEY_RELEASED, ev -> {
            //lösche den aktuellen Key aus der Liste der gedrückten Keys,
            //immer wenn ein Key los gelassen wird, wird setPfeilOnInput ausgeführt
            pressedKeys.remove(ev.getCode());
            setPfeilOnInput();
        });


        KeyFrame drawframe = new KeyFrame(Duration.seconds(1/(5* levelModel.getTick())), event -> {
        //KeyFrame drawframe = new KeyFrame(Duration.seconds(1), event -> {
            System.out.print(levelModel);
            levelModel.reset();
            levelModel.pre();
            levelModel.update();
            levelModel.post();
            System.out.print(levelModel);
            System.out.println("-");

        });
        Timeline t1 = new Timeline(drawframe);
        t1.setCycleCount(Timeline.INDEFINITE);
        t1.play();

        levelModel.addObserver(this);
        levelModel.addObserver(levelView);
        levelView.getStage().show();
    }

    private void setPfeilOnInput() {
        //shift ist true, wenn pressedKeys die Taste Shift enthält
        shift = pressedKeys.contains(KeyCode.SHIFT);
        //enthält pressedKeys die Taste Right...
        if (pressedKeys.contains(KeyCode.RIGHT)) {
            //...und ist shift true, dann wird pfeil auf SRIGHT gesetzt
            if (shift) {
                this.levelModel.setPfeil(SRIGHT);

            //...und shift ist false, dann wird pfeil auf RIGHT gesetzt
            } else {
                this.levelModel.setPfeil(RIGHT);
            }
        } else if (pressedKeys.contains(KeyCode.LEFT)) {
            if (shift) {
                this.levelModel.setPfeil(SLEFT);
            } else {
                this.levelModel.setPfeil(LEFT);
            }
        } else if (pressedKeys.contains(KeyCode.UP)) {
            if (shift) {
                this.levelModel.setPfeil(SUP);
            } else {
                this.levelModel.setPfeil(UP);
            }
        } else if (pressedKeys.contains(KeyCode.DOWN)) {
            if (shift) {
                this.levelModel.setPfeil(SDOWN);
                shift = false;
            } else {
                this.levelModel.setPfeil(DOWN);
            }

        //ist keine Pfeiltaste gedrückt, so wird pfeil auf NO gesetzt
        } else {
            this.levelModel.setPfeil(NO);
        }
    }
    // Setzt die Gem und Time werte in der Level Klasse auf die richtigen Werte
    public void setLevelData(){
        int gem[] = levelModel.getGems();
        int time[] = levelModel.getTicks();

        level.setMinGems(gem[0]);
        level.setMidGems(gem[1]);
        level.setMaxGems(gem[2]);
        level.setMinTime(time[0]);
        level.setMidTime(time[1]);
        level.setMaxTime(time[2]);

        levelNichtBesetzt = false;
    }



    // Mathoden für Update

    public void openDoor() {
        if (levelModel.getGemcounter() == level.getMinGems()) {
            doorIsOpen = true;
        }
    }
        //todo Allgemeine reset methode ??
    public void resetDoor(){
            doorIsOpen = false;
    }

    // Führt Bewertung durch und Speichert ergebnis in Daten
    public void score() {
        int p1 = 1;
        int p2 = 0;

        if (levelModel.getGemcounter() > level.getMidGems() && levelModel.getGemcounter() < level.getMaxGems()) {
            p1 = 2;
        }

        if (levelModel.getGemcounter() > level.getMaxGems()) {
            p1 = 3;
        }

        if (level.getMaxTime() >= levelModel.getTick()) {
            p2 = 3;
        } else {
            if (level.getMidTime() >= levelModel.getTick()) {
                p2 = 2;
            } else {
                if (level.getMinTime() >= levelModel.getTick()) {
                    p2 = 1;
                } else {
                    p2 = 0;
                }
            }
        }

        if(p1> p2) {
            pGes = p2;
        }
        else {
            pGes = p1;
        }

        safeScore();
        menuController.nextLevelFreischalten(level.getLevelNummer());
    }
    //TODO ANPASSEN
    // Speichert Punkte in Daten ab
    public void safeScore (){

        level.setLevelScore(pGes);
        menuController.addScore(pGes);

    }

    public void update(Observable o, Object arg){

        if(levelNichtBesetzt){
            setLevelData();
        }
        //todo umsetzen
        /*
        if(//bool wert das ME auf exit){
        score();

        //zurück zum menü
        */
    }

    public boolean isDoorIsOpen() { return doorIsOpen;}
}
