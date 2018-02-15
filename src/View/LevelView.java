package View;


import Model.Gegenstand;
import Model.LevelModel;

import javafx.geometry.Pos;
import javafx.scene.Scene;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import javafx.scene.text.Font;
import javafx.scene.text.Text;

import javafx.stage.Stage;


import java.util.Observable;
import java.util.Observer;


//TODO: eine Klasse nur für die Levelübersicht



public class LevelView implements Observer {
    private static final int TILE_SIZE = 40;
    private LevelModel levelModel;
    private Stage stage;;

    private Scene levelScene;
    TilePane levelPane;
    TilePane minimap;
    int wLevelPane;
    int hLevelPane;
    private Gegenstand[][] viewMap;
    Pane window;

    public LevelView(LevelModel levelModel, Stage stage) {
        this.levelModel = levelModel;
        this.stage = stage;
        wLevelPane = 1280;
        hLevelPane = 720;
        viewMap = new Gegenstand[levelModel.getWidth()][levelModel.getHeight()];

        levelPane = new TilePane();
        levelPane.setPrefColumns(levelModel.getWidth());
        levelPane.setPrefRows(levelModel.getHeight());
        levelPane.setStyle("-fx-background-color: brown;");
        levelPane.setMaxWidth(TILE_SIZE*levelModel.getWidth());
        levelPane.setMinWidth(TILE_SIZE*levelModel.getWidth());
        levelPane.setMaxHeight(TILE_SIZE*levelModel.getHeight());
        levelPane.setMinHeight(TILE_SIZE*levelModel.getHeight());
        fillPane(levelPane);

        minimap = new TilePane();
        minimap.setScaleX(0.2);
        minimap.setScaleY(0.2);
        minimap.setStyle("-fx-background-color: brown;");
        minimap.setStyle("-fx-border-color: black;" +
                         "-fx-border-width: 8;");
        fillPane(minimap);
        minimap.setMaxWidth(TILE_SIZE*levelModel.getWidth()+16);
        minimap.setMinWidth(TILE_SIZE*levelModel.getWidth()+16);
        minimap.setMaxHeight(TILE_SIZE*levelModel.getHeight()+16);
        minimap.setMinHeight(TILE_SIZE*levelModel.getHeight()+16);
        minimap.setOpacity(0.75);
        minimap.setTranslateX(wLevelPane*0.2*2-16);
        minimap.setTranslateY(-hLevelPane*0.2*2+10);

        Text gemCount = new Text(30, 30, String.valueOf(levelModel.getGemcounter()));
        gemCount.setFont(Font.font("Verdana", 30));
        Pane ui = new StackPane();
        StackPane.setAlignment(gemCount, Pos.TOP_LEFT);
        StackPane.setAlignment(minimap, Pos.CENTER);
        ui.setPrefSize(wLevelPane, hLevelPane);
        ui.setMinSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
        ui.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
        ui.getChildren().add(minimap);
        ui.getChildren().add(gemCount);
        ui.setStyle("-fx-border-color: red;" +
                "-fx-border-width: 8;");
        window = new StackPane();
        window.setMaxSize(wLevelPane, hLevelPane);
        window.setMinSize(wLevelPane, hLevelPane);
        window.getChildren().addAll(levelPane, ui);
        window.setStyle("-fx-background-color: black;");


        levelScene = new Scene(window,wLevelPane, hLevelPane);
        stage.setTitle("Boulder Dash");
        stage.setScene(levelScene);
        stage.show();
        stage.setResizable(false);


    }
    //alle Token aus SpielfeldMap in ViewMap speichern
    //bei Änderungen kann Spielfeld auf Änderungen überprüft werden
    private void updateViewMap() {
        for(int i = 0; i < levelModel.getHeight(); i++)
        {
            for(int j = 0; j < levelModel.getWidth(); j++)
            {
                viewMap[j][i] = levelModel.getMap()[j][i].getToken();
            }
        }
    }

    //Token auf Spielfeld anzeigen
    private void fillPane(Pane pane) {
        for (int i = 0; i < levelModel.getHeight(); i++) {
            for (int j = 0; j < levelModel.getWidth(); j++) {
                ImageView tokenImageView = new ImageView();
                Image image = levelModel.getMap()[j][i].getToken().getImg();
                tokenImageView.setImage(image);
                tokenImageView.setFitWidth(TILE_SIZE);
                tokenImageView.setFitHeight(TILE_SIZE);
                pane.getChildren().add(tokenImageView);
            }
        }
        updateViewMap();
    }

    //Vergleicht alte und neue Map (viewMap und levelModel Map)
    //View angepasst mit neuem Token
    public void updateToken(Pane pane, boolean done)
    {
        for (int i=0; i<levelModel.getHeight(); i++) {
            for (int j = 0; j < levelModel.getWidth(); j++) {
                int x = j;
                int y = i;
                if(!viewMap[x][y].getTyp().equals(levelModel.getMap()[x][y].getToken().getTyp()))
                {
                    int mapWidth = levelModel.getWidth();
                    int pos = mapWidth*y + x; //Stelle berechnen fuer LevelPane - Koordinate im ein Dimensionalen Array
                    //Breite Zeile * Stelle auf y + Position in Zeile x
                    ImageView token = (ImageView) pane.getChildren().get(pos);
                    token.setImage(levelModel.getMap()[x][y].getToken().getImg());
                    token.setFitWidth(TILE_SIZE);
                    token.setFitHeight(TILE_SIZE);
                    if(done) {
                        viewMap[x][y] = levelModel.getMap()[x][y].getToken();
                    }
                }
            }
        }
        Pane ui = (Pane)window.getChildren().get(1);
        Text gemCount = (Text)ui.getChildren().get(1);
        gemCount.setText(String.valueOf(levelModel.getGemcounter()));
        System.out.println("Gems: " + levelModel.getGemcounter());
    }


    public void update(Observable o, Object arg) {
        updateToken(levelPane, false);
        updateToken(minimap, true);
    }

    public Stage getStage() {return stage;}

}
