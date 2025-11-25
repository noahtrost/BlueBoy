package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;

import javax.swing.JPanel;

import entity.Player;
import object.SuperObject;
import tile.TileManager;

@SuppressWarnings("serial")
public class GamePanel extends JPanel implements Runnable {
	
	// SCREEN SETTINGS
	final int originalTileSize = 16;	//16x16 tile
	final int scale = 3;
	
	public final int tileSize = originalTileSize * scale; // 48x48 tile
	public final int maxScreenCol = 16;
	public final int maxScreenRow = 12;
	public final int screenWidth = tileSize * maxScreenCol;	// 768pixel
	public final int screenHeight = tileSize * maxScreenRow;	// 576pixel
	
	// WORLD SETTINGS
	
	public final int maxWorldCol = 50;
	public final int maxWorldRow = 50;
	public final int worldWidth = maxWorldCol * tileSize;
	public final int worldHeight = maxWorldRow * tileSize;
	
	//FPS
	final int FPS = 60;

	//KEYHANDLER
	KeyHandler keyH = new KeyHandler();
	
	//GAMETREAD
	public Thread gameThread;
	
	//PLAYER
	public Player player = new Player(this, keyH);
	
	//TileManager
	TileManager tileM = new TileManager(this);
	
	//CollisionChecker
	public CollisionChecker cc = new CollisionChecker(this);
	
	//OBJECT 
	public SuperObject obj [] = new SuperObject[10];
		
	//AssetSetter
	public AssetSetter as = new AssetSetter(this);
	
	//SoundManager
	public SoundManager soundManager = new SoundManager();
	public SoundManager soundEffectManager = new SoundManager();
	
	//UI
	public UI ui = new UI(this);
	
	public GamePanel() {
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
		this.addKeyListener(keyH);
		this.setFocusable(true);
	}
	
	public void setupGame() {
		as.setObject();
		playMusic();
	}
	
	private void playMusic() {
		soundManager.setSound(0);
		soundManager.play();
		soundManager.loop();
	}
	
	private void stopMusic() {
		soundManager.stop();
	}
	
	public void playSE(int i) {
		soundEffectManager.setSound(i);
		soundEffectManager.play();
	}

	public void startGameThread() {
		gameThread = new Thread(this);
		gameThread.start();
		}

	@Override
	public void run() {
		
		double drawInterval = 1000000000/FPS;	// 0.01666 seconds
		double delta = 0.0;
		long lastTime = System.nanoTime();
		long currentTime;
		
		long timer = 0;
		int drawCount =0;
		
		while(gameThread != null) {
			currentTime = System.nanoTime();
			delta += (currentTime - lastTime) / drawInterval;
			timer += (currentTime - lastTime);
			lastTime = currentTime;
			
			
			
			if(delta >= 1.0) {
				update();
				repaint();
				delta = 0.0;
				drawCount++;
			}
			
			if(timer >= 1000000000) {
				System.out.println("FPS: " + drawCount);
				drawCount = 0;
				timer = 0;
			}
		}
		stopMusic();
	} 
	
	
	private void update() {
		player.update();
	}
	
	public void paintComponent(Graphics g) {

		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;

		long startDraw = System.nanoTime();

		// Tile
		tileM.draw(g2);

		// Object
		for (int i = 0; i < obj.length; i++) {
			if (obj[i] != null)
				obj[i].draw(g2, this);
		}

		// Player
		player.draw(g2);

		// UI
		ui.draw(g2, startDraw);

		

		g2.dispose();
		Toolkit.getDefaultToolkit().sync();
	}
	
}
