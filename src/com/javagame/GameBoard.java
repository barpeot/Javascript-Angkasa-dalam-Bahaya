package com.javagame;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import com.javagame.sprite.Enemy;
import com.javagame.sprite.Player;
import com.javagame.sprite.Shot;

public class GameBoard extends JPanel implements Settings{

	private Dimension d;
	private ArrayList<Enemy> enemies;
	private Player player;
	private Shot shot;
	
	private int direction = 2;
	private int deaths = 0;
	private int deathcounter = 0;
	private int currentEnemyArmyWidth = Settings.ENEMY_ARMY_WIDTH;
	private int currentEnemyArmyHeight = Settings.ENEMY_ARMY_HEIGHT;
	private int currentNoOfEnemies = currentEnemyArmyHeight * currentEnemyArmyWidth;
	private int score = 10 * deaths;
	private int hiscore = 0;
	
	private boolean inGame = true;
	private String explImg = "src/art/explosion.png";
    private String message = "Game Over";
    private JButton retry = new JButton("Retry?");
    private JButton nextLevel = new JButton("Next Level");
    
    private Timer timer;
	
    public GameBoard() {
    	initBoard();
    	startGame();
    	this.add(retry);
    	this.add(nextLevel);
    	
    	retry.setVisible(false);
    	retry.addActionListener(new ActionListener() {
			
    		@Override
    		public void actionPerformed(ActionEvent e) {
    			retry();
    			repaint();
    		}
    });
    	nextLevel.setVisible(false);
    	nextLevel.addActionListener(new ActionListener() {
			
    		@Override
    		public void actionPerformed(ActionEvent e) {
    			nextLevel();
    			repaint();
    		}
    });
    }

	private void initBoard() {

        addKeyListener(new TAdapter());
        setFocusable(true);
		d = new Dimension(Settings.WINDOW_WIDTH, Settings.WINDOW_HEIGHT);
		setBackground(Color.black);

        timer = new Timer(Settings.DELAY, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				doGameCycle();
				
			}
		});
        
        timer.start();
        startGame();
	}

	private void startGame() {
		
		enemies = new ArrayList<>();
		for(int i = 0; i < currentEnemyArmyWidth; i++) {
			for(int j = 0; j < currentEnemyArmyHeight; j++) {
				Enemy enemy = new Enemy(Settings.ENEMY_INIT_X + 28 * i, Settings.ENEMY_INIT_Y + 28 * j);
				enemies.add(enemy);
			}
		}
		
		player = new Player();
		shot = new Shot();
		
	}
	
	private void drawAliens(Graphics g) {

        for (Enemy e : enemies) {

            if (e.isVisible()) {

                g.drawImage(e.getImage(), e.getX(), e.getY(), this);
            }

            if (e.isDying()) {

                e.die();
            }
        }
    }
	
	private void drawPlayer(Graphics g) {

        if (player.isVisible()) {

            g.drawImage(player.getImage(), player.getX(), player.getY(), this);
        }

        if (player.isDying()) {

            player.die();
            inGame = false;
        }
    }
	
	private void drawShot(Graphics g) {

        if (shot.isVisible()) {

            g.drawImage(shot.getImage(), shot.getX(), shot.getY(), this);
        }
    }
	
	private void drawBombing(Graphics g) {

        for (Enemy e : enemies) {

            Enemy.Bomb b = e.getBomb();

            if (!b.isDestroyed()) {

                g.drawImage(b.getImage(), b.getX(), b.getY(), this);
            }
        }
    }
	
	@Override
	protected void paintComponent(Graphics g) {

		super.paintComponent(g);
		drawBoard(g);
	}
	
	private void drawBoard(Graphics g) {
		
		g.setColor(Color.black);
        g.fillRect(0, 0, d.width, d.height);
        g.setColor(Color.green);

        if (inGame) {

            g.drawLine(0, Settings.GROUND,
                    Settings.WINDOW_WIDTH, Settings.GROUND);
            
            g.setColor(Color.white);
            g.drawString("Score : " + score, 20, 30);
            g.drawString("Hi-Score : " + hiscore, 20, 50);

            drawAliens(g);
            drawPlayer(g);
            drawShot(g);
            drawBombing(g);
            

        } else {

            if (timer.isRunning()) {
                timer.stop();
            }

            gameOver(g);
        }

        Toolkit.getDefaultToolkit().sync();
    }
	
	private void gameOver(Graphics g) {
		
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, Settings.WINDOW_WIDTH, Settings.WINDOW_HEIGHT);
		
		g.setColor(new Color(0, 32, 48));
        g.fillRect(50, Settings.WINDOW_WIDTH / 2 - 30, Settings.WINDOW_WIDTH - 100, 50);
        g.setColor(Color.white);
        g.drawRect(50, Settings.WINDOW_WIDTH / 2 - 30, Settings.WINDOW_WIDTH - 100, 50);
        
        var small = new Font("Helvetica", Font.BOLD, 14);
        var fontMetrics = this.getFontMetrics(small);

        g.setColor(Color.white);
        g.setFont(small);
        g.drawString(message, (Settings.WINDOW_WIDTH - fontMetrics.stringWidth(message)) / 2, Settings.WINDOW_HEIGHT / 2 + 100);
        
        retry.setBounds(Settings.WINDOW_WIDTH/2 - 75, 450, 150, 30);
        nextLevel.setBounds(Settings.WINDOW_WIDTH/2 - 75, 500, 150, 30);
        retry.setVisible(true);
        
	}

	private void update() {

        if (deaths == currentNoOfEnemies) {

            inGame = false;
            timer.stop();
            message = "Level Complete!";
            retry.setVisible(true);
            nextLevel.setVisible(true);
            
        }
        
        score = 10 * deathcounter;

        player.act();

        if (shot.isVisible()) {

            int shotX = shot.getX();
            int shotY = shot.getY();

            for (Enemy e : enemies) {

                int enemyX = e.getX();
                int enemyY = e.getY();

                if (e.isVisible() && shot.isVisible()) {
                    if (shotX >= (enemyX)
                            && shotX <= (enemyX + Settings.ENEMY_WIDTH)
                            && shotY >= (enemyY)
                            && shotY <= (enemyY + Settings.ENEMY_HEIGHT)) {

                        var ii = new ImageIcon(explImg);
                        e.setImage(ii.getImage());
                        e.setDying(true);
                        deaths++;
                        deathcounter++;
                        shot.die();
                    }
                }
            }

            int y = shot.getY();
            y -= 12;

            if (y < 0) {
                shot.die();
            } else {
                shot.setY(y);
            }
        }


        for (Enemy e : enemies) {

            int x = e.getX();

            if (x >= Settings.WINDOW_WIDTH - Settings.BORDER_RIGHT && direction != -2) {

                direction = -2;

                Iterator<Enemy> i1 = enemies.iterator();

                while (i1.hasNext()) {

                    Enemy a2 = i1.next();
                    a2.setY(a2.getY() + Settings.GO_DOWN);
                }
            }

            if (x <= Settings.BORDER_LEFT && direction != 2) {

                direction = 2;

                Iterator<Enemy> i2 = enemies.iterator();

                while (i2.hasNext()) {

                    Enemy a = i2.next();
                    a.setY(a.getY() + Settings.GO_DOWN);
                }
            }
        }

        Iterator<Enemy> it = enemies.iterator();

        while (it.hasNext()) {

            Enemy e = it.next();

            if (e.isVisible()) {

                int y = e.getY();

                if (y > Settings.GROUND - Settings.ENEMY_HEIGHT) {
                    inGame = false;
                    message = "Invasion!";
                    retry.setVisible(true);
                }

                e.act(direction);
            }
        }

        var generator = new Random();

        for (Enemy e : enemies) {

            int shot = generator.nextInt(20);
            Enemy.Bomb bomb = e.getBomb();

            if (shot == Settings.CHANCE && e.isVisible() && bomb.isDestroyed()) {

                bomb.setDestroyed(false);
                bomb.setX(e.getX());
                bomb.setY(e.getY());
            }

            int bombX = bomb.getX();
            int bombY = bomb.getY();
            int playerX = player.getX();
            int playerY = player.getY();

            if (player.isVisible() && !bomb.isDestroyed()) {

                if (bombX >= (playerX)
                        && bombX <= (playerX + Settings.PLAYER_WIDTH)
                        && bombY >= (playerY)
                        && bombY <= (playerY + Settings.PLAYER_HEIGHT)) {

                    var ii = new ImageIcon(explImg);
                    player.setImage(ii.getImage());
                    player.setDying(true);
                    bomb.setDestroyed(true);
                }
            }

            if (!bomb.isDestroyed()) {

                bomb.setY(bomb.getY() + 3);

                if (bomb.getY() >= Settings.GROUND - Settings.BOMB_HEIGHT) {

                    bomb.setDestroyed(true);
                }
            }
        }
    }
	
	protected void doGameCycle() {
		
		update();
		repaint();
		
	}
	
    private class TAdapter extends KeyAdapter {

        @Override
        public void keyReleased(KeyEvent e) {

            player.keyReleased(e);
        }

        @Override
        public void keyPressed(KeyEvent e) {

            player.keyPressed(e);

            int x = player.getX();
            int y = player.getY();

            int key = e.getKeyCode();

            if (key == KeyEvent.VK_SPACE) {

                if (inGame) {

                    if (!shot.isVisible()) {

                        shot = new Shot(x, y);
                    }
                }
            }
        }
    }

	private void nextLevel() {
		this.inGame = true;
		this.currentEnemyArmyWidth += 1;
		this.currentNoOfEnemies = currentEnemyArmyWidth * currentEnemyArmyHeight;
		this.direction = 2;
	    this.deaths = 0;
	    this.message = "Game Over";
		this.initBoard();
		this.startGame();
		retry.setVisible(false);
		nextLevel.setVisible(false);
		repaint();
	}
    
    private void retry() {
		this.inGame = true;
		this.currentEnemyArmyWidth = Settings.ENEMY_ARMY_WIDTH;
		this.currentEnemyArmyHeight = Settings.ENEMY_ARMY_HEIGHT;
		this.currentNoOfEnemies = currentEnemyArmyWidth * currentEnemyArmyHeight;
		this.direction = 2;
		this.deathcounter = 0;
	    this.deaths = 0;
	    this.message = "Game Over";
	    if(score > hiscore)hiscore = score;
		this.initBoard();
		this.startGame();
		retry.setVisible(false);
		nextLevel.setVisible(false);
		repaint();
	}
}
