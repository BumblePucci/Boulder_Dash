package Menu;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Created by sakinakamilova on 28.01.18.
 */
public class MenuMain extends Application {

    @Override
    public void start(Stage menuStage) throws Exception {
        MenuModel menuModel = new MenuModel();
        MenuView menuView = new MenuView(menuModel, menuStage);
        MenuController menuController = new MenuController(menuModel, menuView);

    }



}
