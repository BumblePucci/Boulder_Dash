package View;

import Model.Feld;
import Model.Gegenstand;
import Model.LevelModel;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
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
    TilePane minimap;
    int wLevelPane;
    int hLevelPane;
    private Gegenstand[][] viewMap;
    Pane window;
    int counter = 0;

    public LevelView(LevelModel levelModel, Stage stage) {
        this.levelModel = levelModel;
        this.stage = stage;
        wLevelPane = 1280;
        hLevelPane = 720;
        wScene = 600;
        hScene = 500;
        levelPane = new TilePane();
        minimap = new TilePane();
        levelPane.setPrefColumns(levelModel.getWidth());
        levelPane.setPrefRows(levelModel.getHeight());
        levelPane.setStyle("-fx-background-color: brown;");
        viewMap = new Gegenstand[levelModel.getWidth()][levelModel.getHeight()];
        fillPane(levelPane);
        fillPane(minimap);

        minimap.setScaleX(0.2);
        minimap.setScaleY(0.2);
        minimap.setStyle("-fx-background-color: brown;");
        minimap.setStyle("-fx-border-color: black;" +
                         "-fx-border-style: double;" +
                         "-fx-border-width: 8;");
        //TODO: Eine Scene für ein Level, eine Scene für die Levelübersicht
        levelPane.setMaxWidth(wLevelPane);
        levelPane.setMinWidth(wLevelPane);
        levelPane.setMaxHeight(hLevelPane);
        levelPane.setMinHeight(hLevelPane);
        window = new StackPane(levelPane);
        Pane ui = new Pane();
        //ui.setPrefSize(wLevelPane, hLevelPane);
        Text gemCount = new Text(30, 30, String.valueOf(counter));
        gemCount.setFont(Font.font("Comic Sans", 30));
        ui.getChildren().add(gemCount);
        ui.setStyle("-fx-border-color: black");
        window.getChildren().add(ui);
        minimap.setMaxWidth(wLevelPane+16);
        minimap.setMinWidth(wLevelPane+16);
        minimap.setMaxHeight(minimap.getBoundsInLocal().getHeight()*levelModel.getHeight()+16);
        minimap.setMinHeight(minimap.getBoundsInLocal().getHeight()*levelModel.getHeight()+16);
        minimap.setOpacity(0.75);
        System.out.println("blub: " + minimap.getBoundsInParent().getWidth()*levelModel.getWidth());
        minimap.setTranslateX(minimap.getBoundsInParent().getWidth()*levelModel.getWidth()*2);
        minimap.setTranslateY(-minimap.getBoundsInParent().getWidth()*levelModel.getWidth()-6);
        window.getChildren().add(minimap);
        window.setBackground(Background.EMPTY);
        levelScene = new Scene(window,wLevelPane, hLevelPane);
        stage.setTitle("Boulder Dash");
        stage.setScene(levelScene);
        stage.show();


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
                tokenImageView.setFitWidth(wLevelPane/levelModel.getWidth());
                tokenImageView.setFitHeight(wLevelPane/levelModel.getWidth());
                pane.getChildren().add(tokenImageView);
            }
        }
        updateViewMap();
    }

    //Vergleicht alte und neue Map (viewMap und levelModel Map)
    //View angepasst mit neuem Token
    public void updateToken(Pane pane, boolean done)
    {
        counter++;
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
                    token.setFitWidth(Math.round(wLevelPane/levelModel.getWidth()));
                    token.setFitHeight(Math.round(wLevelPane/levelModel.getWidth()));
                    if(done) {
                        viewMap[x][y] = levelModel.getMap()[x][y].getToken();
                    }
                }
            }
        }
        Pane ui = (Pane) window.getChildren().get(1);
        Text gemCount = (Text)ui.getChildren().get(0);
        gemCount.setText(String.valueOf(counter));
        System.out.println("Gems: " + counter);
    }


    public void update(Observable o, Object arg) {
        updateToken(levelPane, false);
        updateToken(minimap, true);
    }

    public Stage getStage() {return stage;}

}
