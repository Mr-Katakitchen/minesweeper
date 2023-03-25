import java.util.Random;

public class Board {

    public int side;
    public int[][] initialize(int numMines, int diffLvl, int existsSupermine) {

        this.side = (diffLvl == 1) ? 9 : 16;

        int[][] tileStatus = new int[side][side];

        Random random = new Random();
        int numMinesRemaining = numMines;
        int[] mineRow = new int[numMines];
        int[] mineCol = new int[numMines];
        int[] supermine = new int[numMines];
        boolean supermineSet;
        supermineSet = (existsSupermine == 1) ? false : true; //Αν δεν υπάρχει υπερνάρκη, είναι πάντα true
        while (numMinesRemaining > 0) {
            int row = random.nextInt(side);
            int col = random.nextInt(side);
            if (tileStatus[row][col] == 0) {
                if (!supermineSet){
                    tileStatus[row][col] = -2;
                    supermine[numMines - numMinesRemaining] = 1;
                    supermineSet = true;
                }
                else{
                    tileStatus[row][col] = -1;
                }
                mineRow[numMines - numMinesRemaining] = row;
                mineCol[numMines - numMinesRemaining] = col;
                numMinesRemaining--;
            }
        }
        WriteDownMines mines = new WriteDownMines();
        mines.save_mines(numMines, mineRow, mineCol, supermine);

        for (int row = 0; row < side; row++) {
            for (int col = 0; col < side; col++) {
                if (tileStatus[row][col] != -1 && tileStatus[row][col] != -2) {
                    int numAdjacentMines = 0;
                    for (int i = row - 1; i <= row + 1; i++) {
                        for (int j = col - 1; j <= col + 1; j++) {
                            if (i >= 0 && i < side && j >= 0 && j < side && (tileStatus[i][j] == -1 || tileStatus[i][j] == -2)) {
                                numAdjacentMines++;
                            }
                        }
                    }
                    tileStatus[row][col] = numAdjacentMines;
                }
            }
        }
        return tileStatus;
    }

}
