package com.javagame;

public interface Settings {

	int FRAME_RATE = 60;
	int DELAY = 1000/FRAME_RATE;
	int WINDOW_WIDTH = 800;
	int WINDOW_HEIGHT = 600;
	int GROUND = 500;
	
	int ENEMY_ARMY_WIDTH = 6;
	int ENEMY_ARMY_HEIGHT = 4;
	int ENEMY_INIT_X = 0;
	int ENEMY_INIT_Y = 5;
	int LEVEL = 1;
	int NOOFTARGET = ENEMY_ARMY_WIDTH * ENEMY_ARMY_HEIGHT;
	int BORDER_RIGHT = 30;
	int BORDER_LEFT = 6;
	int GO_DOWN = 60;
	int ENEMY_HEIGHT = 22;
	int ENEMY_WIDTH = 32;
	int CHANCE = 5;
	int PLAYER_WIDTH = 16;
	int PLAYER_HEIGHT = 16;
	int BOMB_HEIGHT = 5;

	
}
