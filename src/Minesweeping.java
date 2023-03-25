
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.input.MouseButton;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.animation.Animation;
import javafx.util.Duration;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class Minesweeping {

    boolean gameStarted = false;
    private int difficulty;
    private int mineNumber;
    public static int side;
    public static int attempts; //αριθμός προσπαθειών, για τα rounds
    public static int passedTime; //χρόνος που πέρασε, για τα rounds
    public static int[][] tileStatus; //-1 αν είναι νάρκη, αλλιώς το πόσες νάρκες υπάρχουν στα γειτονικά τετράγωνα
    public static boolean[][] revealed; //αν το τετράγωνο έχει αποκαλυφθεί
    public static int flaggedTiles; //μαρκαρισμένα τετραγωνάκια
    GridPane gridPane = new GridPane(); //Βασικό GridPane για τα τετραγωνάκια
    BorderPane mainPane = new BorderPane();
    Font labelFont = Font.font("Sans Serif", FontWeight.BOLD,16);
    Rounds rounds = new Rounds();
    Timeline timeline = new Timeline();
    public void start_sweeping(int difLvl, int mineN, int supermine, int timer, BorderPane pane) {

        this.flaggedTiles = 0;
        this.attempts = 0;
        this.passedTime = 0;
        this.difficulty = difLvl;
        this.mineNumber = mineN;
        this.mainPane = pane;
        if (difficulty == 2) {
            this.side = 16;
        } else {
            this.side = 9;
        }

        gridPane.setAlignment(Pos.CENTER);

        // Αρχικοποίηση ναρκοπεδίου
        this.tileStatus = new int[side][side];
        this.revealed = new boolean[side][side];
        Board starting_board = new Board();
        tileStatus = starting_board.initialize(mineN, difficulty, supermine);

        Label timerLabel = new Label("Time: " + timer);

        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(1), event -> {
            passedTime++;
            int secondsLeft = Integer.parseInt(timerLabel.getText().split(": ")[1]) - 1;
            if (secondsLeft == 0){
                gameOver(-1, -1, false);
            }
            timerLabel.setText("Time: " + secondsLeft);
        }));
        timeline.setCycleCount(Animation.INDEFINITE);


        Label mineNumberLabel = new Label("Mines : " + mineNumber);
        Label flaggedTilesLabel = new Label("Marked tiles : " + flaggedTiles);

        // Φτιάχνει τα τετραγωνάκια
        for (int row = 0; row < side; row++) {
            for (int col = 0; col < side; col++) {
                Button button = new Button();
                button.setPrefWidth(40);
                button.setPrefHeight(40);
                button.setStyle("-fx-background-color: orange; -fx-border-color: black; -fx-border-width: 2px;");
                int Row = row;
                int Col = col;
                gridPane.add(button, col, row);
                Tile tile = new Tile();

                button.setOnMouseClicked(event -> {
                    if (event.getButton() == MouseButton.PRIMARY){
                        attempts++;
                        if (!gameStarted){ //Να ξεκινήσει ο χρόνος με το πρώτο δεξί κλικ
                            gameStarted = true;
                            timeline.play();
                        }
                        if (tileStatus[Row][Col] == -1 || tileStatus[Row][Col] == -2) {   // Πάτησε σε νάρκη
                            gameOver(Row, Col, false);
                        } else {    // Πάτησε σε τετραγωνάκι χωρίς νάρκη
                            tile.revealTile(gridPane, Row, Col, tileStatus, revealed, side, false);
                            flaggedTilesLabel.setText("Marked tiles : " + flaggedTiles);
                            Board board = new Board();
                            if (checkWin()) {
                                gameOver(-1, -1, true);
                            }
                        }
                    } else if (event.getButton() == MouseButton.SECONDARY && !revealed[Row][Col]) {
                        if (button.getGraphic() == null && flaggedTiles < mineNumber){
                            tile.flag(button);
                            flaggedTilesLabel.setText("Marked tiles : " + flaggedTiles);
                            if (tileStatus[Row][Col] == -2 && attempts <= 4){ //αποκάλυψη στήλης και γραμμής όταν βρεθεί το supermine
                                for (int i = 0; i < side; i++){
                                    tile.revealTile(gridPane, i, Col, tileStatus, revealed, side, true);
                                }
                                for (int i = 0; i < side; i++){
                                    tile.revealTile(gridPane, Row, i, tileStatus, revealed, side, true);
                                }
                            }
                        }
                        else if (button.getGraphic() != null){
                            tile.unflag(button);
                            flaggedTilesLabel.setText("Marked tiles : " + flaggedTiles);
                        }
                    }
                });
            }
        }

        timerLabel.setFont(labelFont);
        mineNumberLabel.setFont(labelFont);
        flaggedTilesLabel.setFont(labelFont);
        timerLabel.setTextFill(Color.PURPLE);
        mineNumberLabel.setTextFill(Color.RED);
        flaggedTilesLabel.setTextFill(Color.GREEN);

        HBox labels = new HBox(timerLabel, mineNumberLabel, flaggedTilesLabel);
        labels.setSpacing(50);
        labels.setAlignment(Pos.CENTER);

        VBox vBox = new VBox(labels, gridPane);
        vBox.setAlignment(Pos.CENTER);
        pane.setCenter(vBox);

    }

    public void gameOver(int x_row, int x_col, boolean win){ //οι παράμετροι είναι για να σημαδευτεί η νάρκη που πατήθηκε
        Tile mineTile = new Tile();
        mineTile.revealEverything(gridPane, side, tileStatus, revealed,x_row, x_col);
        FinalScreen gameOver = new FinalScreen();
        gameOver.showScreen(gridPane, mainPane, win);
        timeline.stop();
        rounds.updateRounds(mineNumber,attempts,passedTime,win);
    }

    public boolean checkWin() {
        for (int row = 0; row < side; row++) {
            for (int col = 0; col < side; col++) {
                if (tileStatus[row][col] != -1 && tileStatus[row][col] != -2 && !revealed[row][col]) {
                    return false;
                }
            }
        }
        return true;
    }

}