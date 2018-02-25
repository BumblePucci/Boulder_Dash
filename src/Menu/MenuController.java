package Menu;

import Controller.Controller;
import Model.Feld;
import Model.LevelModel;
import View.LevelView;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by sakinakamilova on 28.01.18.
 */
public class MenuController implements Observer {
    private MenuModel menuModel;
    private MenuView menuView;
    private Controller controller;

    public MenuController(MenuModel menuModel, MenuView menuView) {
        this.menuModel = menuModel;
        this.menuView = menuView;
        this.controller = controller;

        menuModel.addObserver(this);// wichtig, da sonst das Modell nicht weiss, dass es dieses Objekt bei Aenderungen benachritigen soll.
        menuView.getExitButton().setOnMouseClicked(event -> {
                 System.exit(0);
        });
        menuView.getLevelButton().setOnMouseClicked(event -> {
            menuView.setScrollPane(menuModel.levelList);
        });

        menuView.getContinueButton().setOnMouseClicked(event -> { //TODO beim klick weiterspielen
            Label gameLabel = new Label("Spiel Weiterführen");
            Scene gameScene = new Scene(gameLabel, 230, 100);
            Stage newWindow = new Stage();
            newWindow.setTitle("Weiter im Spiel");
            newWindow.setScene(gameScene);
            newWindow.show();
        });


}

    public void addScore(int i){
        menuModel.addScore(i);
    }
    public void nextLevelFreischalten(int now){
        if(menuModel.levelList.get(now).levelScore == 2){
            menuModel.levelList.get(now+1).setLevelPlayed(true);
        }
    }

    @Override
    public void update(Observable o, Object arg) {


    }
}
