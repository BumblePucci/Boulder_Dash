package Controller;

import Menu.Level;
import Menu.MenuController;
import Model.Daten;
import Model.Hauptregeln.Spielerbewegung;
import Model.LevelModel;
import View.LevelView;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.Duration;

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

    public Controller(LevelModel levelModel, LevelView levelView) {
        this.levelModel = levelModel;
        this.levelView = levelView;
        this.levelModel = levelModel;
        this.menuController = menuController;
        this.level = level;
        shift = false;
        doorIsOpen = false;
        pGes = 0;


        this.levelView.getStage().addEventHandler(KeyEvent.KEY_PRESSED, ev -> {
            if (ev.getCode()== KeyCode.SHIFT) {
                shift = true;
            }
            if (ev.getCode() == KeyCode.RIGHT) {
                if (shift) {
                    this.levelModel.setPfeil(SRIGHT);
                    shift = false;
                } else {
                    this.levelModel.setPfeil(RIGHT);
                }
            }

            if (ev.getCode() == KeyCode.LEFT) {
                if (shift) {
                    this.levelModel.setPfeil(SLEFT);
                    shift = false;
                } else {
                    this.levelModel.setPfeil(LEFT);
                }
            }

            if (ev.getCode() == KeyCode.UP) {
                if (shift) {
                    this.levelModel.setPfeil(SUP);
                    shift = false;
                } else {
                    this.levelModel.setPfeil(UP);
                }
            }

            if (ev.getCode() == KeyCode.DOWN) {
                if (shift) {
                    this.levelModel.setPfeil(SDOWN);
                    shift = false;
                } else {
                    this.levelModel.setPfeil(DOWN);
                }
            }

        });

        this.levelView.getStage().addEventHandler(KeyEvent.KEY_TYPED, ev -> {

        });

        this.levelView.getStage().addEventHandler(KeyEvent.KEY_RELEASED, ev -> {
            if (ev.getCode()==KeyCode.SHIFT) {
                this.levelModel.setPfeil(NO);
            }
        });


        KeyFrame drawframe = new KeyFrame(Duration.seconds(1/(5* levelModel.getTick())), event -> {
        //KeyFrame drawframe = new KeyFrame(Duration.seconds(1), event -> {
            System.out.print(levelModel);
            levelModel.reset();
            levelModel.update();
            System.out.print(levelModel);
            System.out.println("-");

        });
        Timeline t1 = new Timeline(drawframe);
        t1.setCycleCount(Timeline.INDEFINITE);
        t1.play();

        levelModel.addObserver(this);
        levelModel.addObserver(levelView);
        //Todo Nötige Observer
        levelView.getStage().show();
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
    }
    //TODO ANPASSEN
    // Speichert Punkte in Daten ab
    public void safeScore (){

        level.setLevelScore(pGes);
        menuController.addScore(pGes);

    }

    public void update(Observable o, Object arg){

    }

    public boolean isDoorIsOpen() { return doorIsOpen;}
}
