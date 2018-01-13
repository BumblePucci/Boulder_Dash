package View;

import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.Observable;
import java.util.Observer;
import Model.LevelModel;


//TODO: eine Klasse nur für die Levelübersicht

public class LevelView implements Observer {
    private LevelModel levelModel;
    private Stage stage;
    public Canvas canvas;
    double wCanvas;
    double hCanvas;
    double wScene;
    double hScene;
    Pane pane;

    public LevelView(LevelModel levelModel, Stage stage) {
        this.levelModel = levelModel;
        this.stage = stage;

        wCanvas = 600;
        hCanvas = 400;
        canvas = new Canvas(wCanvas, hCanvas);
        pane = new Pane(canvas);
        wScene = 600;
        hScene = 500;
        //TODO: Eine Scene für ein Level, eine Scene für die Levelübersicht
        Scene scene = new Scene(pane, wScene, hScene);
        stage.setTitle("Boulder Dash");
        stage.setScene(scene);
        stage.show();
    }



        public void update(Observable o, Object arg) {
    }

    public Stage getStage() {return stage;}

}
