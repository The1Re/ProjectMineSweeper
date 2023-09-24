package minesweeper;

import java.util.Random;

import static minesweeper.MineSweeperConstants.*;
public class MineMap {
    int numMines;
    boolean[][] isMined = new boolean[ROWS][COLS];

    public void newMineMap(int numMines){
        this.numMines = numMines;
        resetMine();
        randomMines(numMines);
    }

    private void randomMines(int numMinesLeft){
        if (numMinesLeft == 0)
            return;
        Random random = new Random();
        int row = random.nextInt(ROWS);
        int col = random.nextInt(COLS);
        if (!isMined[row][col]) {
            isMined[row][col] = true;
            randomMines(numMinesLeft - 1);
        }else{
            randomMines(numMinesLeft);
        }
    }

    private void resetMine(){
        for (int row=0; row<ROWS; row++)
            for (int col=0; col<COLS; col++)
                isMined[row][col] = false;
    }
}
