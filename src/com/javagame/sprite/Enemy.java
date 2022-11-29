package com.javagame.sprite;

import javax.swing.ImageIcon;

public class Enemy extends Sprite{

	private Bomb bomb;
	
	public Enemy(int x, int y) {
		
		initEnemy(x, y);
	}

	private void initEnemy(int x, int y) {
		
		this.x = x;
        this.y = y;

        bomb = new Bomb(x, y);

        var enemyImg = "src/art/alien.png";
        var ii = new ImageIcon(enemyImg);

        setImage(ii.getImage());
	}
	
	public void act(int direction) {

        this.x += direction;
    }
	
	public Bomb getBomb() {

		return bomb;
	}
	
    public class Bomb extends Sprite {

        private boolean destroyed;

        public Bomb(int x, int y) {

            initBomb(x, y);
        }

        private void initBomb(int x, int y) {

            setDestroyed(true);

            this.x = x;
            this.y = y;

            var bombImg = "src/art/bomb.png";
            var ii = new ImageIcon(bombImg);
            setImage(ii.getImage());
        }

        public void setDestroyed(boolean destroyed) {

            this.destroyed = destroyed;
        }

        public boolean isDestroyed() {

            return destroyed;
        }
    }
}
