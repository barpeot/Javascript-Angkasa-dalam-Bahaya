package com.javagame;

import java.awt.EventQueue;
import javax.swing.JFrame;

public class Game extends JFrame{

	public Game() {
		InitUI();
	}
	
	public void InitUI() {
		add(new GameBoard());
		
		setTitle("Angkasa dalam Bahaya");
		setSize(Settings.WINDOW_WIDTH, Settings.WINDOW_HEIGHT);
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
	}
	
	
    public static void main(String[] args) {

        EventQueue.invokeLater(() -> {

            var ex = new Game();
            ex.setVisible(true);
        });
    }
}
