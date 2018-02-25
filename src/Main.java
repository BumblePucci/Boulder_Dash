import Controller.*;
import View.*;
import javafx.application.Application;
import javafx.stage.Stage;
import Model.*;
import Menu.*;

import java.io.IOException;
import java.util.List;

public class Main extends Application {
    public static void main(String[] args) throws IOException {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        JSON_Verarbeitung jsonVerarbeitung = new JSON_Verarbeitung(); //JSON-Files erst aufrufen, wenn im Menü auf jeweiliges Level geklickt wurde
        String path = "./src/JSONLevels/gegner_test.json";                                      //dann auch alle anderen Paths setzen
        jsonVerarbeitung.load_json_file(path);//Path für Level 1, 2, 3,...

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
        MenuMain menuMain = new MenuMain(); //Menue wird eingebunden
        menuMain.start(stage);
    }
}