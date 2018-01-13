package Controller;

import Model.LevelModel;
import View.LevelView;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

import java.util.Observable;
import java.util.Observer;

public class TimelineController implements Observer {
    private LevelView levelView;
    private LevelModel levelModel;

    public TimelineController(LevelModel levelModel, LevelView levelView) {
        this.levelModel = levelModel;
        this.levelView = levelView;


        KeyFrame drawframe = new KeyFrame(Duration.seconds(1/(5* levelModel.tick)), event -> {
           levelModel.update();

        });
        Timeline t1 = new Timeline(drawframe);
        t1.setCycleCount(Timeline.INDEFINITE);
        t1.play();

        levelModel.addObserver(this);
        levelView.getStage().show();
    }

    public void update(Observable o, Object arg){}
}
