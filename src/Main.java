
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.stage.Stage;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import java.io.File;
import javafx.stage.FileChooser;
import javafx.scene.image.ImageView;
import java.io.IOException;
import javafx.scene.layout.BorderPane;

public class Main extends Application {
    Menu menu1 = new Menu("File");
    Menu menu2 = new Menu("Details");
    MenuItem createItem = new MenuItem("Create");
    MenuItem loadItem = new MenuItem("Load");
    MenuItem startItem = new MenuItem("Start");
    MenuItem exitItem = new MenuItem("Exit");
    MenuItem solutionItem = new MenuItem("Solution");
    MenuItem roundsItem = new MenuItem("Rounds");
    FileChooser fc = new FileChooser();
    public int difLvl = 1;  //Αρχικοποίηση δεδομένων
    public int mineN = 9;
     public int supermine = 0;
     public int timer = 180;
    Minesweeping new_game = new Minesweeping();
    Font labelFont = Font.font("Sans Serif", FontPosture.ITALIC, 16);

    @Override
    public void start(Stage stage) throws IOException {
        stage.setTitle("MediaLab Minesweeper");

        menu1.getItems().addAll(createItem, loadItem, startItem, exitItem);
        MenuBar menuBar1 = new MenuBar();
        menuBar1.getMenus().add(menu1);

        menu2.getItems().addAll(roundsItem, solutionItem);
        MenuBar menuBar2 = new MenuBar();
        menuBar2.getMenus().add(menu2);

        HBox menuBars = new HBox(menuBar1, menuBar2);
        menuBars.setSpacing(8);

        //Για την αρχική εικόνα
        ImageView imageView = new ImageView();
        ImageLoader imload = new ImageLoader();
        imload.load_image(imageView, "randy.png");
        Label label = new Label("Give it your best shot.");
        label.setFont(labelFont);
        label.setAlignment(Pos.CENTER);
        VBox imageNlabel = new VBox(label, imageView);
        imageNlabel.setAlignment(Pos.CENTER);

        BorderPane borderPane = new BorderPane();
        borderPane.setTop(menuBars);

        BorderPane centerPiece = new BorderPane(imageNlabel);
        borderPane.setCenter(centerPiece);
        borderPane.setStyle("-fx-background-color: rgb(255, 227, 187);");

        Scene scene = new Scene(borderPane, 650, 700);
        stage.setScene(scene);
        stage.show();

        createItem.setOnAction(new CreateScenario());

        loadItem.setOnAction(e -> {  //select file and call loadScenario. import values
            fc.setTitle("Select scenario");
            fc.setInitialDirectory(new File(System.getProperty("user.dir") + "/medialab"));
            fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files","*.txt"));
            File selected_file = fc.showOpenDialog(stage);
            LoadScenario new_loading = new LoadScenario();
            if (selected_file != null) {
                new_loading.load(selected_file.getAbsolutePath());
                difLvl = new_loading.diffLvl;
                mineN = new_loading.mineN;
                supermine = new_loading.supermine;
                timer = new_loading.timer;
            }
        });

        startItem.setOnAction(e -> {
            new_game = new Minesweeping();
            new_game.start_sweeping(difLvl, mineN, supermine, timer, centerPiece);
        });

        exitItem.setOnAction(e -> {
           stage.close();
        });

        solutionItem.setOnAction(e -> {
            new_game.gameOver(-1, -1 , false);
        });

        roundsItem.setOnAction(e -> {
            Rounds rounds = new Rounds();
            rounds.roundsPop_up();
        });

    }
    public static void main(String[] args) {
        launch();
    }

}