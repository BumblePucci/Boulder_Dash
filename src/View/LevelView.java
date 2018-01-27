package View;

import Model.Feld;
import Model.Gegenstand;
import Model.LevelModel;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;

import java.util.Arrays;
import java.util.Observable;
import java.util.Observer;


//TODO: eine Klasse nur für die Levelübersicht

public class LevelView implements Observer {
    private LevelModel levelModel;
    private Stage stage;;
    double wScene;
    double hScene;
    Pane pane;
    private Scene levelScene;
    TilePane levelPane;
    int wLevelPane;
    int hLevelPane;
    private Gegenstand[][] viewMap;

    public LevelView(LevelModel levelModel, Stage stage) {
        this.levelModel = levelModel;
        this.stage = stage;
        wLevelPane = 1280;
        hLevelPane = 720;
        wScene = 600;
        hScene = 500;
        levelPane = new TilePane();
        levelPane.setPrefColumns(levelModel.getWidth());
        levelPane.setPrefRows(levelModel.getHeight());
        viewMap = new Gegenstand[levelModel.getWidth()][levelModel.getHeight()];
        fillPane();
        //TODO: Eine Scene für ein Level, eine Scene für die Levelübersicht
        levelPane.setMaxWidth(wLevelPane);
        levelPane.setMinWidth(wLevelPane);
        levelScene = new Scene(new StackPane(levelPane),wLevelPane, hLevelPane);
        stage.setTitle("Boulder Dash");
        stage.setScene(levelScene);
        stage.show();


    }

    private void updateViewMap() {
        for(int i = 0; i < levelModel.getHeight(); i++)
        {
            for(int j = 0; j < levelModel.getWidth(); j++)
            {
                viewMap[j][i] = levelModel.getMap()[j][i].getToken();
            }
        }
    }


    private void fillPane() {
        for (int i = 0; i < levelModel.getHeight(); i++) {
            for (int j = 0; j < levelModel.getWidth(); j++) {
                ImageView tokenImageView = new ImageView();
                Image image = levelModel.getMap()[j][i].getToken().getImg();
                tokenImageView.setImage(image);
                tokenImageView.setFitWidth(Math.round(wLevelPane / levelModel.getWidth()));
                tokenImageView.setFitHeight(Math.round(wLevelPane / levelModel.getWidth()));
                levelPane.getChildren().add(tokenImageView);
            }
        }
        updateViewMap();
    }

    public void updateToken()
    {
        for (int i=0; i<levelModel.getHeight(); i++) {
            for (int j = 0; j < levelModel.getWidth(); j++) {
                int x = j;
                int y = i;
                if(!viewMap[x][y].getTyp().equals(levelModel.getMap()[x][y].getToken().getTyp()))
                {
                    int mapWidth = levelModel.getWidth();
                    int pos = mapWidth*y + x;
                    ImageView token = (ImageView) levelPane.getChildren().get(pos);
                    token.setImage(levelModel.getMap()[x][y].getToken().getImg());
                    token.setFitWidth(Math.round(wLevelPane/levelModel.getWidth()));
                    token.setFitHeight(Math.round(wLevelPane/levelModel.getWidth()));
                    viewMap[x][y] = levelModel.getMap()[x][y].getToken();
                }
            }
        }


        ;
    }


        public void update(Observable o, Object arg) {
        updateToken();
    }

    public Stage getStage() {return stage;}

}
