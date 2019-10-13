/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.snake;

/**
 *
 * @author fupn26
 */
import java.awt.EventQueue;
import javax.swing.JFrame;

public class Snake extends JFrame {

    public Snake() {
        
        initUI();
    }
    
    private void initUI() {
        
        Board m_board = new Board();
//        m_board.running = true;
        
        add(m_board);
               
//        setUndecorated(true);
        setAlwaysOnTop(true);
        setResizable(false);
        pack();
        
//        setTitle("Snake");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    

    public static void main(String[] args) {
        
        EventQueue.invokeLater(() -> {
            JFrame ex = new Snake();
            ex.setVisible(true);
        });
    }
}
