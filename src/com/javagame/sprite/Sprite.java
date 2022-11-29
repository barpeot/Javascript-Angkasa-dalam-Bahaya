package com.javagame.sprite;

import java.awt.Image;

import javax.swing.JPanel;

import com.javagame.Settings;

public class Sprite extends JPanel implements Settings{

	private boolean visible;
	private Image image;
	private boolean dying;
	
	int x;
	int y;
	int dx;
	
	public Sprite() {
		visible = true;
	}
	
	public void die() {
		visible = false;
	}
	
	public boolean isVisible() {
		return visible;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public void setDying(boolean dying) {
		this.dying = dying;
	}
	
	public boolean isDying() {
		return this.dying;
	}
	
	public Image getImage() {
	        return image;
	}
	
	public void setImage(Image image) {
        this.image = image;
    }
}
