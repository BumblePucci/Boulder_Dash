import Controller.*;
import View.*;
import javafx.application.Application;
import javafx.stage.Stage;
import Model.*;

import java.io.IOException;
public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        LevelModel levelModel = new LevelModel();
        LevelView levelView = new LevelView(levelModel, stage);
        new TimelineController(levelModel, levelView);
        JSON_Verarbeitung jsonVerarbeitung = new JSON_Verarbeitung(levelModel); //JSON-Files erst aufrufen, wenn im Menü auf jeweiliges Level geklickt wurde
        String path = "./src/JSONLevels/text.json";                                      //dann auch alle anderen Paths setzen
        jsonVerarbeitung.load_json_file(path);                                //Path für Level 1, 2, 3,...
    }
    public static void main(String[] args) throws IOException {
        launch(args);
    }
}