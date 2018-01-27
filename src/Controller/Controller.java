package Controller;

import Model.LevelModel;
import View.LevelView;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.Duration;

import java.util.Observable;
import java.util.Observer;

public class Controller implements Observer {
    private LevelView levelView;
    private LevelModel levelModel;
    private boolean shift;

    public Controller(LevelModel levelModel, LevelView levelView) {
        this.levelModel = levelModel;
        this.levelView = levelView;
        shift = false;


        this.levelView.getStage().addEventHandler(KeyEvent.KEY_PRESSED, ev -> {
            if (ev.getCode()== KeyCode.SHIFT) {
                shift = true;
            }
            if (ev.getCode() == KeyCode.RIGHT) {
                if (shift) {
                    this.levelModel.setsRight(true);
                    shift = false;
                } else {
                    this.levelModel.setRight(true);
                }
            }

            if (ev.getCode() == KeyCode.LEFT) {
                if (shift) {
                    this.levelModel.setsLeft(true);
                    shift = false;
                } else {
                    this.levelModel.setLeft(true);
                }
            }

            if (ev.getCode() == KeyCode.UP) {
                if (shift) {
                    this.levelModel.setsUp(true);
                    shift = false;
                } else {
                    this.levelModel.setUp(true);
                }
            }

            if (ev.getCode() == KeyCode.DOWN) {
                if (shift) {
                    this.levelModel.setsDown(true);
                    shift = false;
                } else {
                    this.levelModel.setDown(true);
                }
            }

        });

        this.levelView.getStage().addEventHandler(KeyEvent.KEY_TYPED, ev -> {

        });

        this.levelView.getStage().addEventHandler(KeyEvent.KEY_RELEASED, ev -> {
            if (ev.getCode()==KeyCode.SHIFT) {
                this.levelModel.setRight(false);
                this.levelModel.setLeft(false);
                this.levelModel.setUp(false);
                this.levelModel.setDown(false);

                this.levelModel.setsRight(false);
                this.levelModel.setsLeft(false);
                this.levelModel.setsUp(false);
                this.levelModel.setsDown(false);
            }
        });


        KeyFrame drawframe = new KeyFrame(Duration.seconds(1/(5* levelModel.getTick())), event -> {
            levelModel.update();
            levelModel.reset();
            //System.out.print(levelModel);

        });
        Timeline t1 = new Timeline(drawframe);
        t1.setCycleCount(Timeline.INDEFINITE);
        t1.play();

        levelModel.addObserver(this);
        levelModel.addObserver(levelView);
        levelView.getStage().show();
    }

    public void update(Observable o, Object arg){}
}
