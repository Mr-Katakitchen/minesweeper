import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
public class WriteDownMines {
    static String address = System.getProperty("user.dir") + "/medialab/mines.txt";
    static void save_mines(int mineN, int[] mineRow, int[] mineCol, int[] supermine){

        try (BufferedWriter buffer = new BufferedWriter(new FileWriter(address))) {
            for (int i=0; i<mineN; i++) {
                buffer.write(mineRow[i] + " " + mineCol[i] + " " + supermine[i] + "\n");
            }
        } catch (IOException e) {
                throw new RuntimeException(e);
            }
    }

}
