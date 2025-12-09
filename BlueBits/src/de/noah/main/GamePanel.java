package de.noah.main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

import de.noah.config.Config;
import de.noah.input.InputManager;

@SuppressWarnings("serial")
public class GamePanel extends JPanel implements Runnable {

	// ---------------------ATTRIBUTES----------------------

	// FPS
	private final int FPS = 60;

	// GAMETREAD
	private Thread gameThread;
	
	// INPUTMANAGER
	private final InputManager inputManager = new InputManager();
	
	// GAME
	private final Game game = new Game(inputManager);
	
	// -------------------CONSTRUCTOR----------------------

	public GamePanel() {
		this.setPreferredSize(new Dimension(Config.SCREEN_WIDTH, Config.SCREEN_HEIGHT));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
		this.addKeyListener(inputManager);
		this.setFocusable(true);
	}

	// --------------------THREAD---------------------------

	@Override
	public void run() {

		double drawInterval = 1000000000 / FPS; // 0.01666 seconds
		double delta = 0.0;
		long lastTime = System.nanoTime();
		long currentTime;

		long timer = 0;
		int drawCount = 0;

		while (gameThread != null) {
			currentTime = System.nanoTime();
			delta += (currentTime - lastTime) / drawInterval;
			timer += (currentTime - lastTime);
			lastTime = currentTime;

			if (delta >= 1.0) {
				update();
				repaint();
				delta -= 1.0;
				drawCount++;
				Config.FRAMECOUNT++;
			}

			if (timer >= 1000000000) {
				//System.out.println("FPS: " + drawCount);
				drawCount = 0;
				timer = 0;
			}
		}
	}

	public void startGame() {
		game.setupGame();
		gameThread = new Thread(this);
		gameThread.start();
	}

	

	// --------------------UPDATE----------------------------
	
	private void update() {
		game.update();
	}
	
	// --------------------DRAW-------------------------------

	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		game.draw(g2);
		g2.dispose();
	}
	

}
