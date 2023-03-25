import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.image.ImageView;
import java.io.IOException;

public class FinalScreen {
    Font labelFont = Font.font("Sans Serif", FontPosture.ITALIC, 16);
    public void showScreen (GridPane gridPane, BorderPane pane, boolean you_won){

        ImageView imageView = new ImageView();

        if (you_won) {
            Label message = new Label("Ok, I surrender. What do you want from me?");
            message.setFont(labelFont);
            message.setAlignment(Pos.CENTER);
            ImageLoader imload = new ImageLoader();
            try {
                imload.load_image(imageView, "happy randy.png");
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            VBox box = new VBox(message, imageView);
            box.setAlignment(Pos.CENTER);
            pane.setCenter(box);
        }
        else {
            Label threat = new Label("Die rebel scum");
            threat.setFont(labelFont);
            threat.setAlignment(Pos.CENTER);
            VBox box = new VBox(threat, gridPane);
            box.setAlignment(Pos.CENTER);
            pane.setCenter(box);
        }

    }
}
