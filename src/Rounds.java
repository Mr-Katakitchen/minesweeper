import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.io.*;

public class Rounds {

    static String address = System.getProperty("user.dir") + "/medialab/rounds.txt";
    static void updateRounds(int mineNumber, int attempts, int time, boolean winner){

        try {
            File file = new File(address);
            if (!file.exists()) {
                file.createNewFile();
            }
            FileReader fr = new FileReader(address);
            BufferedReader br = new BufferedReader(fr);

            String lastRound = br.readLine();

            FileWriter fw = new FileWriter(address);
            BufferedWriter bw = new BufferedWriter(fw);

            bw.write(mineNumber + " " + attempts + " " + time + " " + winner + "\n");

            if (lastRound != null){
                bw.write(lastRound);
            }
            String entry;
            for (int i=0; (entry = br.readLine()) != null; i++){ //Για να μένει το τελευταίο round πάνω πάνω
                bw.write("\n" + entry);
            }
            br.close();
            fr.close();
            bw.close();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static String[] getRounds(){

        String[] entry = new String[5];

        try (BufferedReader br = new BufferedReader(new FileReader(address))) {

            for (int i = 0; i < 5; i++) {
                String line = br.readLine();
                if (line == null) {
                    break;
                }
                String[] value = line.split(" ");
                String winner = (Boolean.parseBoolean(value[3])) ? "Player" : "PC";
                StringBuilder sb = new StringBuilder();
                sb.append("Mines: "+value[0]+", Attempts: "+value[1]+", Time: "+value[2]+" seconds, Winner: "+winner);
                entry[i] = sb.toString();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return entry;
    }

    static void roundsPop_up(){
        Stage pop_up = new Stage();
        pop_up.setTitle("Your results");
        Rounds rounds = new Rounds();
        String[] str = rounds.getRounds();
        VBox games = new VBox();
        VBox results = new VBox();
        HBox hbox = new HBox();
        hbox.setSpacing(35);
        for (int i=0; i<5; i++){
            int j = i + 1;
            Label gameN = new Label("GAME "+j);
            Font gameNFont = Font.font("Sans Serif", FontWeight.BOLD, 20);
            gameN.setFont(gameNFont);
            games.getChildren().add(gameN);
            Label result = new Label(str[i]);
            Font resultFont = Font.font("Sans Serif", FontPosture.ITALIC, 15);
            result.setFont(resultFont);
            results.getChildren().add(result);
        }
        games.setAlignment(Pos.CENTER);
        games.setSpacing(12);
        results.setAlignment(Pos.CENTER_LEFT);
        results.setSpacing(22);
        hbox.getChildren().addAll(games, results);
        hbox.setAlignment(Pos.CENTER);
        hbox.setStyle("-fx-background-color: rgb(107, 181, 120);");
        Scene pop_upScene = new Scene(hbox, 550, 300);
        pop_up.setScene(pop_upScene);
        pop_up.show();
    }

}
