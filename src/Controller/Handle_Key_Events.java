package Controller;

import Model.Gegenstand;
import Model.LevelModel;
import Model.Hauptregeln.Spielerbewegung;
import View.LevelView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.Observable;
import java.util.Observer;

public class Handle_Key_Events implements Observer {

    private LevelView levelView;
    private LevelModel levelModel;
    private Spielerbewegung spielerbewegung;
    private int x,y;
    private int rechts;
    private int links;
    private int oben;
    private int unten;
    private boolean shift;


    public Handle_Key_Events (LevelModel levelModel, LevelView levelView) {
        this.levelModel = levelModel;
        this.levelView = levelView;
        x=0;
        y=0;
        rechts = x+1;
        links = x-1;
        oben = y-1;
        unten = y+1;
        shift = false;
        spielerbewegung = new Spielerbewegung(levelModel,x,y);


        levelView.canvas.addEventHandler(KeyEvent.KEY_PRESSED, ev -> {
            if (ev.getCode()==KeyCode.SHIFT) {
                shift = true;
            }
        });

        levelView.canvas.addEventHandler(KeyEvent.KEY_TYPED, ev -> {
            for (int i=0; i<levelModel.getMap().length; i++){
                for (int j=0; j<levelModel.getMap().length; j++) {
                    if (levelModel.getMap()[i][j].getToken().equals(Gegenstand.ME)) {
                        x = i;
                        y = j;
                    }
                }
            }
            if (ev.getCode()== KeyCode.RIGHT){
                if (shift=true){
                    spielerbewegung.dig(x,y,rechts);
                    spielerbewegung.gemDig(x,y,rechts);
                }
                else {
                    spielerbewegung.walk(x, y, rechts);
                    spielerbewegung.gemWalk(x, y, rechts);
                    spielerbewegung.moveThing(x, y, rechts);
                }
            }

            if (ev.getCode()==KeyCode.LEFT){
                if (shift=true){
                    spielerbewegung.dig(x,y,links);
                    spielerbewegung.gemDig(x,y,links);
                }
                else {
                    spielerbewegung.walk(x, y, links);
                    spielerbewegung.gemWalk(x, y, links);
                    spielerbewegung.moveThing(x, y, links);
                }
            }

            if (ev.getCode()==KeyCode.UP){
                if (shift=true){
                    spielerbewegung.dig(x,y,oben);
                    spielerbewegung.gemDig(x,y,oben);
                }
                else {
                    spielerbewegung.walk(x, y, oben);
                    spielerbewegung.gemWalk(x, y, oben);
                    spielerbewegung.moveThing(x, y, oben);
                }
            }

            if (ev.getCode()==KeyCode.DOWN) {
                if (shift=true){
                    spielerbewegung.dig(x,y,unten);
                    spielerbewegung.gemDig(x,y,unten);
                }
                else {
                    spielerbewegung.walk(x, y, unten);
                    spielerbewegung.gemWalk(x, y, unten);
                    spielerbewegung.moveThing(x, y, unten);
                }
            }

        });

        levelView.canvas.addEventHandler(KeyEvent.KEY_RELEASED, ev -> {
            if (ev.getCode()==KeyCode.SHIFT) {
                shift = false;
            }
        });

        levelModel.addObserver(this);
        levelView.getStage().show();
    }


    public void update(Observable o, Object arg){}
}
