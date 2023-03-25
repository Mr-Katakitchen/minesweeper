import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
public class Tile extends Rectangle {
    public void revealTile(GridPane gridPane, int row, int col, int[][] tileStatus, boolean[][] revealed, int side, boolean foundSupermine) {
        //foundSupermine για να μη γίνει αναδρομικό reveal αν απλά βρέθηκε το supermine
        if (!revealed[row][col]) {
            revealed[row][col] = true;
            Button button = (Button) gridPane.getChildren().get(row * side + col);
            unflag(button);
            button.setStyle("-fx-background-color: rgb(209, 240, 238); -fx-border-color: black; -fx-border-width: 0.2px;");

            if (tileStatus[row][col] != 0){
                Text text = new Text();
                switch (tileStatus[row][col]){
                    case -2:
                        text.setText("X");
                        text.setFill(Color.GOLDENROD);
                        break;
                    case -1:
                        text.setText("X");
                        text.setFill(Color.GREY);
                        break;
                    case 1:
                        text.setText(Integer.toString(tileStatus[row][col]));
                        text.setFill(Color.BLUE);
                        break;
                    case 2:
                        text.setText(Integer.toString(tileStatus[row][col]));
                        text.setFill(Color.RED);
                        break;
                    case 3:
                        text.setText(Integer.toString(tileStatus[row][col]));
                        text.setFill(Color.GREEN);
                        break;
                    case 4:
                        text.setText(Integer.toString(tileStatus[row][col]));
                        text.setFill(Color.BLACK);
                        break;
                }
                text.setFont(Font.font("Sans Serif", FontWeight.BOLD, 20));
                button.setGraphic(text);
            }
            button.setDisable(true);
            button.setOpacity(1);

            if (!foundSupermine) {
                if (tileStatus[row][col] == 0) {
                    for (int i = row - 1; i <= row + 1; i++) {
                        for (int j = col - 1; j <= col + 1; j++) {
                            if (i >= 0 && i < side && j >= 0 && j < side) {
                                revealTile(gridPane, i, j, tileStatus, revealed, side, false);
                            }
                        }
                    }
                }
            }
        }
    }

    public void revealEverything(GridPane gridPane, int side, int[][] tileStatus, boolean[][] revealed,  int x_row, int x_col) {
        for (int row = 0; row < side; row++) {
            for (int col = 0; col < side; col++) {
                if (row == x_row && col == x_col){
                    //Για να φαίνεται η νάρκη που κόστισε το παιχνίδι
                    Button button = (Button) gridPane.getChildren().get(row * side + col);
                    Text text = new Text("X");
                    button.setStyle("-fx-background-color: red; -fx-border-color: black; -fx-border-width: 0.2px;");
                    if (tileStatus[row][col] == -2){
                        text.setFill(Color.GOLD);
                    }
                    else{
                        text.setFill(Color.GREY);
                    }
                    text.setFont(Font.font("Sans Serif", FontWeight.BOLD, 20));
                    button.setGraphic(text);
                    button.setDisable(true);
                    button.setOpacity(1);
                }
                else{
                    revealTile(gridPane, row, col, tileStatus, revealed, side, false);
                }
            }
        }
    }

    public void flag(Button button){
        Minesweeping game = new Minesweeping();
        if (button.getGraphic() == null) {
                Text text = new Text("!");
                text.setFill(Color.PURPLE);
                text.setFont(Font.font("Sans Serif", FontWeight.BOLD, 16));
                button.setGraphic(text);
                game.flaggedTiles++;
        }
    }
    public void unflag(Button button){
        Minesweeping game = new Minesweeping();
        if (button.getGraphic() != null) {
            button.setGraphic(null);
            game.flaggedTiles--;
        }
    }
}