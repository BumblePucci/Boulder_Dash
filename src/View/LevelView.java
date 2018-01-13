package View;

import Model.LevelModel;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;

import java.util.Observable;
import java.util.Observer;


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
    TilePane levelPane;

    public LevelView(LevelModel levelModel, Stage stage) {
        this.levelModel = levelModel;
        this.stage = stage;


        wCanvas = 600;
        hCanvas = 400;
        canvas = new Canvas(wCanvas, hCanvas);
        pane = new Pane(canvas);
        wScene = 600;
        hScene = 500;
        levelPane = new TilePane();
        levelPane.setPrefColumns(levelModel.width);
        levelPane.setPrefRows(levelModel.height);
        fillPane();

        //TODO: Eine Scene für ein Level, eine Scene für die Levelübersicht
        Scene scene = new Scene(pane, wScene, hScene);
        Scene levelScene = new Scene(levelPane);
        stage.setTitle("Boulder Dash");
        //stage.setScene(scene);
        stage.setScene(levelScene);
        stage.show();


    }

    private void fillPane()
    {
        for(int i = 0; i < levelModel.height; i++)
        {
            for(int j = 0; j < levelModel.width; j++)
            {
                Canvas tokenCanvas = new Canvas(20,20);
                GraphicsContext gc = tokenCanvas.getGraphicsContext2D();
                Image image = new Image("file:" + levelModel.getMap()[i][j].getToken().getBildPath());
                gc.drawImage(image,0,0);


                levelPane.getChildren().add(tokenCanvas);

            }
        }
    }


        public void update(Observable o, Object arg) {
    }

    public Stage getStage() {return stage;}

}
