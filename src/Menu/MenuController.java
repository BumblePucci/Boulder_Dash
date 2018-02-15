package Menu;

import Controller.Controller;

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
            menuView.createGridPane(menuModel.levelList);
        });
    }

    public void addScore(int i){
        menuModel.addScore(i);
    }

    @Override
    public void update(Observable o, Object arg) {


    }
}
