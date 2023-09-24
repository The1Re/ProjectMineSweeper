package minesweeper;

import javax.swing.*;
import java.awt.*;

public class Cell extends JButton {
    public static final Color RED_COLOR = new Color(255, 102, 102);
    public static final Font FONT_NUMBER = new Font("Fira Code", Font.BOLD, 16);
    public int row, col;
    public boolean isFlag;
    public boolean isMark;
    public boolean isMine;
    public Cell(int row, int col){
        this.row = row;
        this.col = col;
        setFont(FONT_NUMBER);
        setFocusable(false);
    }
    public void newGame(boolean isMine){
        this.isFlag = false;
        this.isMark = false;
        this.isMine = isMine;
        setEnabled(true);
        setText("");
        setBackground(null);
    }

    public void paintMines(){
        this.setBackground(RED_COLOR);
    }
}
