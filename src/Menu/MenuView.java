package Menu;


import Controller.Controller;
import Model.Feld;
import Model.LevelModel;
import View.LevelView;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
//import sun.java2d.x11.X11SurfaceDataProxy;

import java.io.IOException;
import java.util.AbstractList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by sakinakamilova on 28.01.18.
 */
public class MenuView implements Observer {

    private final Stage stage; //Instanzvariable
    public Button continueButton;
    private MenuModel menuModel;
    private Button levelButton;
    private Button exitButton;
    //private VBox levelSign;
    private BorderPane levelSign;
    private StackPane anzeigeBox;

    public MenuView(MenuModel menuModel, Stage stage) {
        this.menuModel = menuModel;
        this.stage = stage;


        createMainStage();

    }

    public Button getContinueButton() {
        return continueButton;
    }

    public Button getLevelButton() {
        return levelButton;
    }


    public Button getExitButton() {
        return exitButton;
    }


    private void createMainStage() {
        stage.setTitle("Boulder Dash");
        stage.setFullScreen(false); //TODO auf true
        VBox root = new VBox(); //oberer und unterer Teil des Fensters
        root.setSpacing(40); //abstand zw. oberer uns unterer VBox
        root.setAlignment(Pos.CENTER);

        Image imgBackground = new Image(getClass().getResource("backgroundGroß.png").toExternalForm());
        BackgroundImage backgroundImage = new BackgroundImage(
                imgBackground,
                BackgroundRepeat.NO_REPEAT,//bild nur einmal einfuegen mit X und Y Position (horizontal u. Vertikal)
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        Background background = new Background(backgroundImage);
        root.setBackground(background);

        Scene menuScene = new Scene(root, 800, 600);

        anzeigeBox = new StackPane();

        Rectangle rectangle = new Rectangle(700, 300);

        rectangle.setStroke(Color.BROWN);
        rectangle.setStrokeWidth(3);
        rectangle.setFill(Color.rgb(200, 200, 200, 0.3));
        rectangle.setArcHeight(25);
        rectangle.setArcWidth(25);
        //TODO Rectangle weiß opasity wenig √

        anzeigeBox.getChildren().add(rectangle);

        Label label = new Label();
        label.setText("Boulder Dash");
        label.setFont(Font.font("Chalkduster", 50));
        label.setTextFill(Color.LIGHTGRAY);

        anzeigeBox.getChildren().add(label);

        root.getChildren().add(anzeigeBox);

        VBox buttonBox = new VBox();
        buttonBox.setSpacing(10);//Abstand zw. buttons
        buttonBox.setAlignment(Pos.CENTER);

        continueButton = new Button("Continue");
        continueButton.setPrefWidth(200);
        continueButton.setFont(Font.font("Chalkduster"));
        continueButton.setStyle("-fx-border-color: #8B2323; -fx-border-width: 3px; -fx-border-radius: 5; -fx-background-color: rgba(139, 35, 35, 0.3); -fx-background-radius: 5;");
        continueButton.setTextFill(Color.WHITE);

        levelButton = new Button("Levels");
        levelButton.setPrefWidth(200);
        levelButton.setFont(Font.font("Chalkduster"));
        levelButton.setStyle("-fx-border-color: #8B2323; -fx-border-width: 3px; -fx-border-radius: 5; -fx-background-color: rgba(139, 35, 35, 0.3); -fx-background-radius: 5;");
        levelButton.setTextFill(Color.WHITE);


        exitButton = new Button("Exit");
        exitButton.setPrefWidth(200);
        exitButton.setFont(Font.font("Chalkduster"));
        exitButton.setStyle("-fx-border-color: #8B2323; -fx-border-width: 3px; -fx-border-radius: 5; -fx-background-color: rgba(139, 35, 35, 0.3); -fx-background-radius: 5;");
        exitButton.setTextFill(Color.WHITE);

        buttonBox.getChildren().addAll(continueButton, levelButton, exitButton);
        root.getChildren().add(buttonBox); //buttonBox wird auf die untere VBox zugefügt

        stage.setScene(menuScene); //scene kommt auf die stage
        stage.show();

    }


    @Override
    public void update(Observable o, Object arg) {
    }

    //TODO flowpane oder scrollpane für die level mit einer zeile √
    public void setScrollPane(AbstractList<Level> levelList) { //Methode fuer LevelScrollPane
        ScrollPane scrollPane = new ScrollPane();

        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);//immer anwesend
        scrollPane.setMaxSize(690, 290);

        //TODO: ScrollPane transparent machen √
        scrollPane.setStyle("-fx-background: transparent;-fx-background-color: transparent; "); //CSS notation
        HBox levelScrollBox = new HBox();
        levelScrollBox.setSpacing(20);

        levelScrollBox.setAlignment(Pos.BOTTOM_LEFT);
        levelScrollBox.setPadding(new Insets(6, 6, 6, 12));


        int numberOfLevels = levelList.size(); //anzahl der level = anzahl der level Objekte in der liste im model

        for (int i = 0; i < numberOfLevels; i++) {
            Level level = levelList.get(i); //Levelobjekt


            levelSign = new BorderPane(); //Türen

            //Levelname
            Label levelName = new Label(level.getLevelName());
            levelName.setFont(Font.font("Chalkduster", 20)); //Comic Sans
            levelName.setTextFill(Color.LIGHTGRAY);
            levelName.setMinWidth(150);
            levelName.setAlignment(Pos.CENTER);

            levelSign.setTop(levelName);
            levelSign.setMinWidth(150);

            //das Spiel beim klick einer tür
            Image imgBackground = null;
            if (level.getLevelPlayed()) {
                imgBackground = new Image(getClass().getResource("turAuf.png").toExternalForm());
                levelSign.setOnMouseClicked(e -> {
                    JSON_Verarbeitung jsonVerarbeitung = new JSON_Verarbeitung(); //JSON-Files erst aufrufen, wenn im Menü auf jeweiliges Level geklickt wurde
                    String path = "./src/JSONLevels/"+level.getLevelName()+".json";                                      //dann auch alle anderen Paths setzen
                    try {
                        jsonVerarbeitung.load_json_file(path);//Path für Level 1, 2, 3,...
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }

                    final String name = jsonVerarbeitung.getName();
                    final int width = jsonVerarbeitung.getWidth();
                    final int height = jsonVerarbeitung.getHeight();
                    final int[] gems = jsonVerarbeitung.getGems();
                    final int[] ticks = jsonVerarbeitung.getTicks();
                    final List<List<String>> pre = jsonVerarbeitung.getPre();  //Sind es wirklich STRINGlisten?
                    final List<List<String>> post = jsonVerarbeitung.getPost();
                    final int maxslime = jsonVerarbeitung.getMaxslime();
                    final Feld[][] map = jsonVerarbeitung.getMap();

                    LevelModel levelModel = new LevelModel(name, width, height, gems, ticks, pre, post, maxslime, map);
                    LevelView levelView = new LevelView(levelModel, stage);
                    new Controller(levelModel, levelView);
                });
            } else {
                imgBackground = new Image(getClass().getResource("turZu.png").toExternalForm());
            }


            //Türe auf oder zu
            ImageView backgroundImage = new ImageView(imgBackground);
            levelSign.setCenter(backgroundImage);


            //Schlüssel
            int levelScore = level.getLevelScore();
            Image keyImage = new Image(getClass().getResource("schluessel.png").toExternalForm());
            Image keyImageGray = new Image(getClass().getResource("SchluesselSchwarz.png").toExternalForm());

            HBox keyBox = new HBox();
            keyBox.setSpacing(5);
            keyBox.setMinWidth(150);
            keyBox.setAlignment(Pos.CENTER);

            if ((level.getLevelPlayed())) {
                for (int j = 0; j < levelScore; j++) {
                    keyBox.getChildren().add(new ImageView(keyImage));
                }
            } else {
                for (int k = 0; k < 3; k++) {
                    keyBox.getChildren().add(new ImageView(keyImageGray));
                }
            }
            BorderPane.setAlignment(keyBox, Pos.CENTER);
            levelSign.setBottom(keyBox);


            //TODO: Starte das Spiel in einem bestimmten Level für TANJA


            levelScrollBox.getChildren().add(levelSign);


        }

        scrollPane.setContent(levelScrollBox); //levelScrollBox auf Pane

        int numberOfNodes = anzeigeBox.getChildren().size();
        anzeigeBox.getChildren().remove(numberOfNodes - 1);//letzte element wird entfernt und levelansicht
        anzeigeBox.getChildren().add(scrollPane);


    }


}
