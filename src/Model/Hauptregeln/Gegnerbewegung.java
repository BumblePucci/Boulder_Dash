package Model.Hauptregeln;

import Model.Direction;
import Model.Feld;
import Model.Gegenstand;

import java.util.Observable;
import java.util.Observer;

import static Model.Direction.*;
import static Model.Gegenstand.*;


public class Gegnerbewegung implements Observer {
    private Feld[][] map;


    public Gegnerbewegung (Feld[][] map){

        this.map=map;
    }

    //checke den Gegenstand eines Feldes
    private boolean checkToken(int x, int y, Gegenstand token) {
        return (map[x][y].getMoved()==0 && map[x][y].getToken().equals(token));
    }

    //setze den alten Gegenstand auf den neuen, setze moved-value und setze Direction
    private void moveTo(int x, int y, int newX, int newY) {
        map[newX][newY].setMoved(1);
        map[newX][newY].setToken(map[x][y].getToken());
        map[newX][newY].setDirection(map[x][y].getDirection());
        resetOrigin(x, y);
    }

    private void moveToWithNewDirection(int x, int y, int newX, int newY, Direction direction) {
        map[newX][newY].setMoved(1);
        map[newX][newY].setToken(map[x][y].getToken());
        map[newX][newY].setDirection(direction);
        resetOrigin(x, y);
    }

    //Ein Gegner hinterlässt beim Verlassen eines Feldes einen Path (ohne Direction, Moved-Value wird gesetzt)
    private void resetOrigin (int x, int y){
        map[x][y].setToken(PATH);
        map[x][y].setMoved(1);
        map[x][y].setDirection(NO);
    }


    //ME wird getroffen
    private void bump (int x, int y, Gegenstand gegner) {
        int o = x+1;
        int w = x-1;
        int n = y-1;
        int s = y+1;

        //ist der Gegner ein Xling oder SWAPLING, so verwandle alle acht Felder um das ME herum und auch das ME in GEMs
        if (gegner.equals(XLING) || gegner.equals(SWAPLING)){
            transformToGem(w,n);
            transformToGem(w,y);
            transformToGem(w,s);

            transformToGem(x,n);
            transformToGem(x,y);
            transformToGem(x,s);

            transformToGem(o,n);
            transformToGem(o,y);
            transformToGem(o,s);

            //ist der Gegner ein BLOCKLING, so verwndle alle acht Felder um das ME herum und auch das ME in STONEs
        } else if (gegner.equals(BLOCKLING)) {
            transformToStone(w,n);
            transformToStone(w,y);
            transformToStone(w,s);

            transformToStone(x,n);
            transformToStone(x,y);
            transformToStone(x,s);

            transformToStone(o,n);
            transformToStone(o,y);
            transformToStone(o,s);
        }
    }


    //was passiert, wenn ME auf einen XLING oder einen SWAPLING stößt
    private void transformToGem (int x, int y){
        //ist auf dem Feld keine WALL und auch kein EXIT, so setze darauf ein GEM, setze den moved-value und setze den direction-value auf 0
        if (checkWallExit(x,y)) {
            map[x][y].setToken(GEM);
            map[x][y].setMoved(1);
            map[x][y].setDirection(NO);
        }
    }

    //was passiert, wenn ME auf einen BLOCKLING stößt
    private void transformToStone (int x, int y) {
        //ist auf dem Feld keine WALL und auch kein EXIT, so setze darauf ein STONE, setze den moved-value und setze den direction-value auf 0
        if (checkWallExit(x,y)) {
            map[x][y].setToken(STONE);
            map[x][y].setMoved(1);
            map[x][y].setDirection(NO);
        }
    }

    //Damit eine Verwandlung beim Aufeinandertreffen eines Gegners, mit einem Spieler nicht eine WALL oder ein EXIT verwandelt
    //Checke, ob auf einem Feld eine WALL oder ein EXIT liegt
    private boolean checkWallExit (int x, int y){
        return (!(map[x][y].getToken().equals(WALL) || map[x][y].getToken().equals(EXIT)));
    }


    //um x- und y-Positionen zusammenzufassen
    private class XYVectors {
        int x;
        int y;

        //Konstruktor
        XYVectors(int x, int y) {
            this.x = x;
            this.y = y;
        }

        //Konstruiert den neuen Punkt ausgehend vom aktuellen
        //-> ausgangspunkt.add(Richtungsvektor);    =neuer Punkt
        public XYVectors add(XYVectors vec) {
            return new XYVectors(x + vec.x, y + vec.y);
        }
    }

    //Getter für vorne (front), also die Richtung, in die der Gegner zeigt
    private XYVectors getDirection(Direction direction) {
        int dirX = 0, dirY = 0;
        switch (direction) {
            case NORDEN:
                dirY = -1;
                break;
            case OSTEN:
                dirX = 1;
                break;
            case SUEDEN:
                dirY = 1;
                break;
            case WESTEN:
                dirX = -1;
                break;
            default:
                break;
        }
        return new XYVectors(dirX, dirY);
    }

    //Getter für hinten
    private XYVectors getBack(Direction direction) {
        int backX = 0, backY = 0;
        switch (direction) {
            case NORDEN:
                backY = 1;
                break;
            case OSTEN:
                backX = -1;
                break;
            case SUEDEN:
                backY = -1;
                break;
            case WESTEN:
                backX = 1;
                break;
            default:
                break;
        }
        return new XYVectors(backX, backY);
    }


    //Getter für rechts
    private XYVectors getRight(Direction direction) {
        int rightX = 0, rightY = 0;
        switch (direction) {
            case NORDEN:
                rightX = 1;
                break;
            case OSTEN:
                rightY = 1;
                break;
            case SUEDEN:
                rightX = -1;
                break;
            case WESTEN:
                rightY = -1;
                break;
            default:
                break;
        }
        return new XYVectors(rightX, rightY);
    }

    //Getter für links
    private XYVectors getLeft(Direction direction) {
        int leftX = 0, leftY = 0;
        switch (direction) {
            case NORDEN:
                leftX = -1;
                break;
            case OSTEN:
                leftY = -1;
                break;
            case SUEDEN:
                leftX = 1;
                break;
            case WESTEN:
                leftY = 1;
                break;
            default:
                break;
        }
        return new XYVectors(leftX, leftY);
    }

    //checke token eines Feldes
    private boolean checkField(XYVectors pos, Gegenstand token) {
        return checkToken(pos.x, pos.y, token);
    }

    //checke ob auf den acht Feldern um eine Position herum ein bestimmtes token liegt
    private boolean checkFieldsAround(XYVectors pos, Gegenstand token) {
        return (checkToken(pos.x - 1, pos.y, token) &&
                checkToken(pos.x + 1, pos.y, token) &&

                checkToken(pos.x, pos.y + 1, token) &&
                checkToken(pos.x, pos.y - 1, token));
    }


    //Rekursion:
    //# ist vor dem Swapling ein PATH geht es nach vorne /fertig
    //ist vor dem Swapling ein ME stößt es zusammen /fertig
    //ist vor dem Swapling eine Blockade, und hinter ihm nicht, so dreht es sich um und beginnt von vorne #
    public void swapling(int x, int y) {
        //Hinweg
        Direction currentDirection = map[x][y].getDirection();

        XYVectors position = new XYVectors(x, y);
        XYVectors dir = getDirection(currentDirection);
        //pos+dir=newPos
        XYVectors newPos = position.add(dir);

        //ist vorne ein ME, bump
        if (checkToken(newPos.x, newPos.y, ME)) {
            bump(newPos.x, newPos.y, SWAPLING);

            //ist vorne eine Blockade
        } else if (!checkToken(newPos.x, newPos.y, PATH)) {

            //ist hinten eine Blockade
            if (!checkToken(x-dir.x, y-dir.y, PATH)) {
                return; //Abbruch der Rekursion, Rückweg
            }

            //so drehe dich um 180°
            map[x][y].setDirection(currentDirection.rotateClockwise().rotateClockwise());

            swapling(x, y); //neue Rekursion
            return; //Abbruch der Rekursion, Rückweg
        }
        //ist die Rekursion abgeschlossen, bzw. befindet sich vor ihm ein PATH, so bewegt sich das Swapling auf die neue Position
        moveTo(x, y, newPos.x, newPos.y);
    }


    public void xling(int x, int y) {
        Direction currentDirection = map[x][y].getDirection();

        //TODO: als erstes muss das XLING nach rechts gehen wollen
        XYVectors currentPos = new XYVectors(x, y);

        XYVectors dir = getDirection(currentDirection);
        XYVectors frontPos = currentPos.add(dir);

        XYVectors right = getRight(currentDirection);
        XYVectors rPos = currentPos.add(right);

        XYVectors left = getLeft(currentDirection);
        XYVectors lPos = currentPos.add(left);

        XYVectors frontRPos = frontPos.add(right);

        XYVectors back = getBack(currentDirection);
        XYVectors bPos = currentPos.add(back);

        if (checkFieldsAround(currentPos, PATH)) {
            moveToWithNewDirection(currentPos.x, currentPos.y, rPos.x, rPos.y, currentDirection.rotateClockwise());
            return;
        }

        if (!(checkField(frontPos, PATH) || checkField(frontPos, ME)) &&
                !(checkField(bPos, PATH) || checkField(bPos, ME)) &&
                !(checkField(rPos, PATH) || checkField(rPos, ME)) &&
                !(checkField(lPos, PATH) || checkField(lPos, ME))) {
            map[x][y].setDirection(currentDirection.rotateClockwise().rotateClockwise().rotateClockwise());
            return;
        }

        if ((checkField(rPos, PATH) || checkField(rPos, ME)) &&
                (checkField(frontRPos, PATH) || checkField(frontRPos, ME))) {
            map[x][y].setDirection(currentDirection.rotateClockwise());
            xling(x, y);
            return;
        }

        if (!(checkField(frontPos, PATH) || checkField(frontPos, ME))) {
            if (checkField(rPos, PATH) || checkField(rPos, ME)) {
                map[x][y].setDirection(currentDirection.rotateClockwise());
                xling(x, y);
                return;
            } else {

                map[x][y].setDirection(currentDirection.rotateClockwise().rotateClockwise().rotateClockwise());
                xling(x, y);
                return;
            }
        }

        if (checkField(frontPos, ME)) {
            bump(frontPos.x, frontPos.y, XLING);
        }
        moveTo(currentPos.x, currentPos.y, frontPos.x, frontPos.y);
    }


    public void blockling(int x, int y) {
        Direction currentDirection = map[x][y].getDirection();

        XYVectors pos = new XYVectors(x, y);
        XYVectors dir = getDirection(currentDirection);
        XYVectors front = pos.add(dir);

        XYVectors left = getLeft(currentDirection);
        XYVectors l = pos.add(left);

        XYVectors nextL = front.add(left);

        XYVectors right = getRight(currentDirection);
        XYVectors r = pos.add(right);

        XYVectors back = getBack(currentDirection);
        XYVectors b = pos.add(back);

        if (checkFieldsAround(pos, PATH)) {
            moveToWithNewDirection(pos.x, pos.y, l.x, l.y, currentDirection.rotateClockwise().rotateClockwise().rotateClockwise());
            return;
        }

        if (!(checkField(front, PATH) || checkField(front, ME)) &&
                !(checkField(b, PATH) || checkField(b, ME)) &&
                !(checkField(r, PATH) || checkField(r, ME)) &&
                !(checkField(l, PATH) || checkField(l, ME))) {
            map[x][y].setDirection(currentDirection.rotateClockwise());
            return;
        }

        if ((checkField(l, PATH) || checkField(l, ME)) &&
                (checkField(nextL, PATH) || checkField(nextL, ME))) {
            map[x][y].setDirection(currentDirection.rotateClockwise().rotateClockwise().rotateClockwise());
            blockling(x, y);
            return;
        }

        if (!(checkField(front, PATH) || checkField(front, ME))) {
            if (checkField(l, PATH) || checkField(l, ME)) {
                map[x][y].setDirection(currentDirection.rotateClockwise().rotateClockwise().rotateClockwise());
                xling(x, y);
                return;
            } else {
                map[x][y].setDirection(currentDirection.rotateClockwise());
                blockling(x, y);
                return;
            }
        }

        if (checkField(front, ME)) {
            bump(front.x, front.y, BLOCKLING);
        }
        moveTo(pos.x, pos.y, front.x, front.y);
    }

    public void update(Observable o, Object arg) {}

}
