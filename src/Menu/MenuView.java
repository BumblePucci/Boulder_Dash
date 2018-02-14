package Menu;


import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.AbstractList;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by sakinakamilova on 28.01.18.
 */
public class MenuView implements Observer {

    private final Stage stage; //Instanzvariable
    private MenuModel menuModel;
    public Button continueButton;
    private Button levelButton;
    private Button exitButton;
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
        stage.setFullScreen(false); //TODo auf true
        VBox root = new VBox(); //oberer und unterer Teil des Fensters
        root.setSpacing(40); //abstand zw. oberer uns unterer VBox


        Image imgBackground = new Image(getClass().getResource("hintergrund_1.jpg").toExternalForm()); //TODO richtiges bild
        BackgroundImage backgroundImage = new BackgroundImage(
                imgBackground,
                BackgroundRepeat.NO_REPEAT,//bild nur einmal einfuegen (horizontal u. Vertikal)
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        Background background = new Background(backgroundImage);
        root.setBackground(background);

        Scene menuScene = new Scene(root, 800, 600);

        anzeigeBox = new StackPane();

        Rectangle rectangle = new Rectangle(300, 300);
        rectangle.setArcHeight(25);
        rectangle.setArcWidth(25);

        anzeigeBox.getChildren().add(rectangle);

        Label label = new Label();
        label.setText("Boulder Dash");
        label.setFont(Font.font("Comic Sans", 30));
        label.setTextFill(Color.AQUA);
        anzeigeBox.getChildren().add(label);


        root.getChildren().add(anzeigeBox);

        VBox buttonBox = new VBox();
        buttonBox.setSpacing(10);//Abstand zw. buttons
        buttonBox.setAlignment(Pos.CENTER);

        continueButton = new Button("Continue");
        continueButton.setPrefWidth(200);

        levelButton = new Button("Levels");
        levelButton.setPrefWidth(200);

        exitButton = new Button("Exit");
        exitButton.setPrefWidth(200);


        buttonBox.getChildren().addAll(continueButton, levelButton, exitButton);
        root.getChildren().add(buttonBox); //buttonBox wird auf die untere VBox zugefügt

        //Image bgImage = new Image(getClass().getResourceAsStream("lockClosedSmall.png"));
        stage.setScene(menuScene); //scene kommt auf die stage
        stage.show();

    }


    @Override
    public void update(Observable o, Object arg) {

    }

    public void createGridPane(AbstractList<Level> levelList) { //Methode fuer LevelGridPane
        GridPane levels = new GridPane();
        int numberOfLevels = levelList.size(); //anzahl der level = anzahl der level Objekte in der liste im model
        levels.setAlignment(Pos.CENTER);
        int levelRow = 3; //anzahl der button pro zeile

        for (int i = 0; i < numberOfLevels; i++) {

            Level level = levelList.get(i); //Levelobjekt

            int row = 0;
            row = i / levelRow;

            int column = i % levelRow;

            Button b = new Button();
            b.setMinSize(70,70);
            b.setTextFill(Color.WHITE);
            b.setText(level.getLevelName() + "/" + level.levelScore); //levelname + score wird eingefügt
            //TODO rahmen um Button hier einfügen

            Image imgBackground = null;

            if (level.getLevelPlayed()){
                imgBackground = new Image(getClass().getResource("tuerAufKlein.png").toExternalForm()); //TODO richtiges bild

            }else {
                 imgBackground = new Image(getClass().getResource("tuerZuKlein.png").toExternalForm()); //TODO richtiges bild
                b.setDisable(true);
            }
            BackgroundImage backgroundImage = new BackgroundImage(
                    imgBackground,
                    BackgroundRepeat.NO_REPEAT,//bild nur einmal einfuegen (horizontal u. Vertikal)
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundPosition.DEFAULT,
                    BackgroundSize.DEFAULT);
            Background background = new Background(backgroundImage);
            b.setBackground(background);



            levels.add(b, column, row);


        }
        int numberOfNodes = anzeigeBox.getChildren().size();
        anzeigeBox.getChildren().remove(numberOfNodes-1);//letzte element wird entfernt
        anzeigeBox.getChildren().add(levels);


    }
}
