package minesweeper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static minesweeper.MineSweeperConstants.*;

public class GamePanel extends JPanel {
    public static final int CELL_SIZE = 60;
    public static final int CANVAS_WIDTH = CELL_SIZE * COLS;
    public static final int CANVAS_HEIGHT = CELL_SIZE * ROWS;

    Cell[][] cells = new Cell[ROWS][COLS];
    int numMines = 10;

    public GamePanel(){
        setLayout(new GridLayout(ROWS, COLS, 2, 2));
        //Create Cell and Setup
        for (int row=0; row<ROWS; row++)
            for (int col=0; col<COLS; col++) {
                cells[row][col] = new Cell(row, col);
                cells[row][col].addMouseListener(new CellMouseListener());
                add(cells[row][col]);
            }

        setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));
    }

    public void newGame(){
        MineMap minemap = new MineMap();
        minemap.newMineMap(numMines);
        for (int row=0; row<ROWS; row++)
            for (int col=0; col<COLS; col++) {
                cells[row][col].newGame(minemap.isMined[row][col]);
            }
    }

    public int countMines(int srcRow, int srcCol){
        int count = 0;
        for (int row=srcRow-1; row<=srcRow+1; row++)
            for (int col=srcCol-1; col<=srcCol+1; col++)
                if (row >= 0 && row < ROWS && col >= 0 && col < COLS)
                    if (cells[row][col].isMine) count++;
        return count;
    }

    public void revealCell(int srcRow, int srcCol){
        int countMines = countMines(srcRow, srcCol);
        cells[srcRow][srcCol].setText(countMines == 0 ? "" : countMines + "");
        cells[srcRow][srcCol].setEnabled(false);
        cells[srcRow][srcCol].isMark = true;
        if (countMines == 0){
            for (int row = srcRow - 1; row <= srcRow + 1; row++) {
                for (int col = srcCol - 1; col <= srcCol + 1; col++) {
                    if (row >= 0 && row < ROWS && col >= 0 && col < COLS) {
                        if (!cells[row][col].isMark) revealCell(row, col);
                    }
                }
            }
        }
    }

    public boolean hasWon(){
        int count = 0;
        for (int row=0; row<ROWS; row++)
            for (int col=0; col<COLS; col++){
                Cell cell = cells[row][col];
                if (cell.isMark && !cell.isMine)
                    count++;

            }
        return count == ROWS*COLS - numMines;
    }

    private void showBoard(){
        for (int row=0; row<ROWS; row++)
            for (int col=0; col<COLS; col++) {
                if (cells[row][col].isMine) {
                    cells[row][col].setText("#");
                    cells[row][col].paintMines();
                    cells[row][col].setEnabled(false);
                } else {
                    revealCell(row, col);
                }
            }
    }

    private class CellMouseListener extends MouseAdapter{
        @Override
        public void mouseClicked(MouseEvent e) {
            Cell cell = (Cell) e.getSource();
            if (e.getButton() == MouseEvent.BUTTON1){

                if (cell.isMine){
                    int option = JOptionPane.showConfirmDialog(
                            null,
                            "Game Over!\nExit?",
                            "Game Status",
                            JOptionPane.OK_CANCEL_OPTION,
                            JOptionPane.WARNING_MESSAGE
                    );

                    if (option == JOptionPane.YES_OPTION)
                        System.exit(0);
                    else
                        showBoard();
                    return;
                }else
                    revealCell(cell.row, cell.col);

            }else if (e.getButton() == MouseEvent.BUTTON3) {

                cell.isFlag = !cell.isFlag;
                cell.setText( cell.isFlag ? "*" : "");

            }

            if (hasWon()){
                int option = JOptionPane.showConfirmDialog(
                        null,
                        "You Win!\nCongratulation Exit?",
                        "Status",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.INFORMATION_MESSAGE
                );
                if (option == JOptionPane.OK_OPTION){
                    System.exit(0);
                }
                showBoard();
            }
        }
    }
}
