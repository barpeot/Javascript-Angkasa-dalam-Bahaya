package com.javagame.sprite;

public class Player extends Sprite {
  
  private int width;

    public Player() {

        initPlayer();
    }
  
  public void keyPressed(KeyEvent e) {

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {

            dx = -2;
        }

        if (key == KeyEvent.VK_RIGHT) {

            dx = 2;
        }
    }
}
