package Menu;

import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import static java.awt.SystemColor.text;

/**
 * Created by sakinakamilova on 14.01.18.
 */
public class GameMenu extends Application {

    private static final Font FONT = Font.font("", FontWeight.BOLD, 20);
    private VBox menuList; // die liste


    private Parent createContent() {
        Pane root = new Pane();
        root.setPrefSize(600, 400);// Fenstergröße
        Rectangle bg = new Rectangle(600, 400); // hintergrund_Rechteck



        ContentFrame frame = new ContentFrame(createMainContent());

        HBox hbox = new HBox(15, frame);
        hbox.setTranslateX(200);
        hbox.setTranslateY(50);

        MenuItem itemExit = new MenuItem("EXIT");
        itemExit.setOnActivate(() -> System.exit(0));
        itemExit.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            setActivateMenuItem(itemExit);
            System.exit(0);
        });

        MenuItem continueItem = new MenuItem("CONTINUE");
        continueItem.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            setActivateMenuItem(continueItem);
            //wie springt man in das aktuelle Level?
        });

        MenuItem levelsItem = new MenuItem("LEVELS");
        levelsItem.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            setActivateMenuItem(levelsItem);

            //woher kommt die Anzahl der Levels?
            //woher weiß man, in welchem Level der Spieler ist?
            //wie springt man wieder in ein Level?

            GridPane gridPane = new GridPane();
            int numberOfLevels = 12;
            int numberOfButtonsInARow = 4;
            int levelReached = 3;

            Image lockClosed = new Image(getClass().getResourceAsStream("lockClosedSmall.png"));

            for (int i = 0; i < numberOfLevels; i++) {

                int row = 0;
                row = i / numberOfButtonsInARow;

                int column = i % numberOfButtonsInARow;

                Button b = new Button();
                if (i > levelReached) {
                    b.setGraphic(new ImageView(lockClosed));
                } else {
                    b.setText(new Integer(i+1).toString());
                }
                b.setMinSize(50,50);
                gridPane.add(b, column, row);

            }



            Scene scene = new Scene(gridPane, 300, 275);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        });

        menuList = new VBox(10, continueItem, levelsItem, itemExit);
        menuList.setAlignment(Pos.TOP_CENTER);
        menuList.setTranslateX(240);
        menuList.setTranslateY(300);

        getMenuItem(0).setActive(true);
        root.getChildren().addAll(bg, hbox, menuList);
        return root;
    }

    private void setActivateMenuItem(MenuItem thisMenuItem) {
        ObservableList<Node> menuItems = menuList.getChildren();
        for (Node node : menuItems) {
            MenuItem menuItem = (MenuItem) node;
            if (menuItem == thisMenuItem) {
                menuItem.setActive(true);
            } else {
                menuItem.setActive(false);
            }
        }
    }

    private Node createMainContent() {
        String title = "Boulder Dash";
        HBox letters = new HBox(0);
        letters.setAlignment(Pos.CENTER); // buchstaben in der mitte
        for (int i = 0; i < title.length(); i++) {
            Text letter = new Text(title.charAt(i) + "");
            letter.setFont(FONT);
            letter.setFill(Color.LIGHTGREEN);
            letters.getChildren().add(letter);

            TranslateTransition tt = new TranslateTransition(Duration.seconds(2), letter); // animatin
            // einzelner
            // buchstaben
            tt.setDelay(Duration.millis(i * 50));// Welle
            tt.setToY(-25); // grenze
            tt.setAutoReverse(true);
            tt.setCycleCount(TranslateTransition.INDEFINITE);// für immer
            // abspielen
            tt.play();
        }
        return letters;
    }

    private MenuItem getMenuItem(int index) {
        return (MenuItem) menuList.getChildren().get(index); // menü box, welchen
        // oben definiert
        // wird
    }// menuItem, weil oben die menuItems sind

    private static class ContentFrame extends StackPane {
        public ContentFrame(Node content) {
            setAlignment(Pos.CENTER);

            Rectangle frame = new Rectangle(200, 200);
            frame.setArcWidth(25);
            frame.setArcHeight(25); // ecken sind runder
            frame.setStroke(Color.WHITESMOKE);

            getChildren().addAll(frame, content);
        }
    }

    // drei kreise
    private static class MenuItem extends HBox {
        private TriCircle c1 = new TriCircle(), c2 = new TriCircle();
        private Text text;
        private Runnable script;

        public MenuItem(String name) { // name des menüs
            super(15);// super konstruktor, mit abstand 15 zum text
            setAlignment(Pos.CENTER);

            text = new Text(name);
            text.setFont(FONT);

            getChildren().addAll(c1, text, c2); // c1= linker Kreis; dann Text;
            // dann c2=rechter Kreis
            setActive(false);
            setOnActivate(() -> System.out.println(name + " aktiviert"));
        }

        public void setActive(boolean b) {
            c1.setVisible(b);
            c2.setVisible(b);
            text.setFill(b ? Color.WHITE : Color.GREY); // aktiv=weis; nicht
            // aktiv=grau
        }

        public void setOnActivate(Runnable r) {
            script = r;
        }

        public void activate() {
            if (script != null) // Wenn nicht null dann
                script.run();// runt der script
        }
    }

    // drei kreise werden gezeichnet
    private static class TriCircle extends Parent {
        public TriCircle() {
            // unten links, keine bewegung
            Shape shape1 = Shape.subtract(new Circle(5), new Circle(2)); // großer
            // kreis
            // minus
            // kleiner
            // K
            shape1.setFill(Color.WHITE);

            // unten Rechts
            Shape shape2 = Shape.subtract(new Circle(5), new Circle(2));
            shape2.setFill(Color.WHITE);
            shape2.setTranslateX(5); // 5 einheiten in die x richtung

            // Oben
            Shape shape3 = Shape.subtract(new Circle(5), new Circle(2));
            shape3.setFill(Color.WHITE);
            shape3.setTranslateX(2.5);// x richtung, dann ist es in der mitte
            shape3.setTranslateY(-5); // wird um 5 nach oben geschoben

            getChildren().addAll(shape1, shape2, shape3);
            setEffect(new GaussianBlur(1));
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Scene scene = new Scene(createContent());

        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}


