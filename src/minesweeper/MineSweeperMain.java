package minesweeper;

import javax.swing.*;
import java.awt.*;

public class MineSweeperMain extends JFrame {
    GamePanel board = new GamePanel();
    JButton newGameBtn = new JButton("New Game");
    public MineSweeperMain() {
        setTitle("MineSweeper");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setIconImage(new ImageIcon("src/resource/icon.png").getImage());
        addComponents();
        pack();
        setVisible(true);
    }

    private void addComponents(){
        getContentPane().add(board, BorderLayout.CENTER);
        getContentPane().add(newGameBtn, BorderLayout.SOUTH);
        newGameBtn.setFocusable(false);
        newGameBtn.addActionListener(e -> {
            board.newGame();
        });
        board.newGame();
    }

}
