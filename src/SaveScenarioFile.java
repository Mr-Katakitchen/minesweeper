import java.io.IOException;
import java.io.BufferedWriter;
import java.io.FileWriter;
public class SaveScenarioFile {
    static void save(String scenID, int difLvl, int minesN, int tmr, int superm){

        String address = System.getProperty("user.dir") + "/medialab/" + scenID + ".txt";
        String difficulty_level = String.valueOf(difLvl);
        String mines_number = String.valueOf(minesN);
        String existence_of_supermine = String.valueOf(superm);
        String timer = String.valueOf(tmr);

    try (BufferedWriter buffer = new BufferedWriter(new FileWriter(address))) {
        buffer.write(difficulty_level + "\n" + mines_number + "\n" + timer + "\n" + existence_of_supermine);
    } catch (IOException e) {
        throw new RuntimeException(e);
        }
    }
}


