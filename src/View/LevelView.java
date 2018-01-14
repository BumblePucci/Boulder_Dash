package View;

import Model.Gegenstand;
import Model.LevelModel;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Screen;
import javafx.stage.Stage;
import java.awt.*;
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
    public Scene levelScene;
    double screenHeight;
    double wLevelPane;
    double hLevelPane;
    ImageView[][] imgView2D;

    public LevelView(LevelModel levelModel, Stage stage) {
        this.levelModel = levelModel;
        this.stage = stage;
        wLevelPane = 1280;
        hLevelPane = 720;
        /*Rectangle2D visualBounds = Screen.getPrimary().getVisualBounds();
        screenHeight = visualBounds.getHeight()-30;
        System.out.println(visualBounds.getHeight());*/
        wCanvas = 600;
        hCanvas = 400;
        canvas = new Canvas(wCanvas, hCanvas);
        pane = new Pane(canvas);
        wScene = 600;
        hScene = 500;
        levelPane = new TilePane();
        levelPane.setPrefColumns(levelModel.width);
        fillPane();
        levelPane.setMaxWidth(wLevelPane);
        levelPane.setMinWidth(wLevelPane);
        levelScene = new Scene(new StackPane(levelPane),wLevelPane, hLevelPane);
        //TODO: Eine Scene für ein Level, eine Scene für die Levelübersicht
        Scene scene = new Scene(pane, wScene, hScene);
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
                ImageView tokenImageView = new ImageView();
                Image image = new Image("file:" + levelModel.getMap()[j][i].getToken().getBildPath());
                tokenImageView.setImage(image);
                tokenImageView.setFitWidth(Math.round(wLevelPane/levelModel.width));
                tokenImageView.setFitHeight(Math.round(wLevelPane/levelModel.width));


                levelPane.getChildren().add(tokenImageView);
            }

        }
    }


    public void updateToken(int x, int y, int x1, int y1)
    {
        int mapWidth = levelModel.width;
        int pos = mapWidth*x + y;
        ImageView token = (ImageView) levelPane.getChildren().get(pos);
        Image image = new Image("file:" + levelModel.getMap()[y][x].getToken().getBildPath());
        token.setImage(image);
        token.setFitWidth(Math.round(wLevelPane/levelModel.width));
        token.setFitHeight(Math.round(wLevelPane/levelModel.width));
        int pos1 = mapWidth*x1 + y1;
        ImageView token1 = (ImageView) levelPane.getChildren().get(pos1);
        Image image1 = new Image("file:" + levelModel.getMap()[y1][x1].getToken().getBildPath());
        token1.setImage(image1);
        token1.setFitWidth(Math.round(wLevelPane/levelModel.width));
        token1.setFitHeight(Math.round(wLevelPane/levelModel.width));
    }

        public void update(Observable o, Object arg) {

    }

    public Stage getStage() {return stage;}

}
