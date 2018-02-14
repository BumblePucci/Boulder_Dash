package Menu;

import javafx.scene.Parent;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

/**
 * Created by sakinakamilova on 28.01.18.
 */
//Todo: diamant statt kreis
// drei kreise werden gezeichnet
public class TriCircle extends Parent {


    public TriCircle() {
        // unten links, keine bewegung
        Shape shape1 = Shape.subtract(new Circle(5), new Circle(2)); // großer kreis minus kleiner K
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