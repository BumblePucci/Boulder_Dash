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

import static Model.Pfeil.*;

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
        levelView.getStage().show();
    }

    public void update(Observable o, Object arg){}
}
