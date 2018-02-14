package Menu;

import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 * Created by sakinakamilova on 28.01.18.
 */
public class MenuItem extends HBox {
    private TriCircle c1 = new TriCircle(), c2 = new TriCircle();
    private Text text;
    private Runnable script;
    private static final Font FONT = Font.font("", FontWeight.BOLD, 20); // Todo: ändern


    public MenuItem(String name) { // name des menüs
        super(15);// super konstruktor, mit abstand 15 zum text
        setAlignment(Pos.CENTER);

        text = new Text(name);
        text.setFont(FONT);

        getChildren().addAll(c1, text, c2); // c1= linker Kreis; dann Text; dann c2=rechter Kreis
        setActive(false);
        // setOnActivate(() -> System.out.println(name + " aktiviert"));

    }

    public void setActive(boolean b) {
        c1.setVisible(b);
        c2.setVisible(b);
        text.setFill(b ? Color.WHITE : Color.GREY); // aktiv=weis; nicht aktiv=grau
    }

    public void setOnActivate(Runnable r) {
        script = r;
    }

    public void activate() {
        if (script != null) // Wenn nicht null dann
            script.run();// runnt der script
    }
}


