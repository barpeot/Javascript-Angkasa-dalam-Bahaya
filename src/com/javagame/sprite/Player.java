package com.javagame.sprite;

import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;

import com.javagame.Settings;

public class Player extends Sprite{
  
  private int width;

    public Player() {

        initPlayer();
    }

    private void initPlayer() {
    	var playerImg = "src/art/player.png";
        var ii = new ImageIcon(playerImg);

        width = ii.getImage().getWidth(null);
        setImage(ii.getImage());

        int START_X = Settings.WINDOW_WIDTH/2 - width - 8;
        setX(START_X);

        int START_Y = 475;
        setY(START_Y);
		
	}

    public void act() {

        x += dx;

        if (x <= 6) {

            x = 6;
        }

        if (x >= Settings.WINDOW_WIDTH - (2 * width - Settings.BORDER_RIGHT/2)) {

            x = Settings.WINDOW_WIDTH - (2 * width - Settings.BORDER_RIGHT/2);
        }
    }

	public void keyPressed(KeyEvent e) {

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {

            dx = -6;
        }

        if (key == KeyEvent.VK_RIGHT) {

            dx = 6;
        }
    }



	public void keyReleased(KeyEvent e) {
		
		int key = e.getKeyCode();
		
		if (key == KeyEvent.VK_LEFT) {

            dx = 0;
        }

		if (key == KeyEvent.VK_RIGHT) {

            dx = 0;
        }
		
	}
}