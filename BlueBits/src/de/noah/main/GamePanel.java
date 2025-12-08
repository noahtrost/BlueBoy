package de.noah.main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import de.noah.entity.Entity;
import de.noah.entity.Player;
import de.noah.object.SuperObject;
import de.noah.tile.TileManager;
import de.noah.userinterface.UI;
import de.noah.util.AssetSetter;
import de.noah.util.CollisionManager;
import de.noah.util.GameStateManager;
import de.noah.util.InputManager;
import de.noah.util.SoundManager;
import de.noah.util.SpriteManager;

@SuppressWarnings("serial")
public class GamePanel extends JPanel implements Runnable {

	// FPS
	private final int FPS = 60;

	// GAMETREAD
	private Thread gameThread;

	// SPRITEMANAGER
	private final SpriteManager spriteManager = new SpriteManager();

	// INPUTMANAGER
	private final InputManager inputManager = new InputManager();

	// PLAYER
	private Player player;

	// TileManager
	private final TileManager tileManager = new TileManager();

	// CollisionChecker
	private CollisionManager collisionManager;

	// OBJECTS
	private final SuperObject objects[] = new SuperObject[10];

	// NPCS
	private final Entity npcs[] = new Entity[10];

	// AssetSetter
	private AssetSetter assetSetter = new AssetSetter();

	// SoundManager
	private SoundManager soundManager = new SoundManager();
	private SoundManager soundEffectManager = new SoundManager();

	// GAME STATE MANAGER
	private GameStateManager gameStateManager = new GameStateManager();

	// UI
	private UI ui;

	// -=------------------------------------CONSTRUCTOR-----------------------------

	public GamePanel() {
		this.setPreferredSize(new Dimension(Config.SCREEN_WIDTH, Config.SCREEN_HEIGHT));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
		this.addKeyListener(inputManager);
		this.setFocusable(true);
	}

	public void setupGame() {
		spriteManager.setUp();
		tileManager.setUp(spriteManager.getTileSprites());
		player = new Player(Config.TILE_SIZE * 23, Config.TILE_SIZE * 21, 4, spriteManager.getPlayerSprites());
		assetSetter.setObjects(objects, spriteManager.getObjectSprites());
		assetSetter.setNPCS(npcs, spriteManager.getAllNPCsprites());
		collisionManager = new CollisionManager(player, npcs, objects, tileManager.getMapTileNum(),
				tileManager.getTile());
		setCollisionManagerToAllEntitys();
		ui = new UI(gameStateManager, spriteManager.getUiSprites());
		gameStateManager.setGameState(GameState.PLAYSTATE);
		playMusic();
	}

	private void setCollisionManagerToAllEntitys() {
		player.setCollisionManager(collisionManager);
		for (Entity entity : npcs) {
			if (entity == null)
				continue;
			entity.setCollisionManager(collisionManager);
		}
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
				System.out.println("FPS: " + drawCount);
				drawCount = 0;
				timer = 0;
			}
		}
		stopMusic();
	}

	private void update() {
		// PLAYERINPUTS
		setPlayerInputs();

		// PLATSTATE
		if (gameStateManager.getGameState() == GameState.PLAYSTATE)
			updatePlayState();
		else if (gameStateManager.getGameState() == GameState.DIALOGSTATE)
			updateDialogState();

		// SET GAME STATE FOR NEW FRAME
		setNextGameState();
		inputManager.setSpaceJustPressed(false);
	}

	private void updateDialogState() {
		makeEyeContact();
	}

	private void makeEyeContact() {
		int npcIndex = player.getinteractiableNPC();

		int xDiff = Math.abs(player.getWorldX() - npcs[npcIndex].getWorldX());
		int yDiff = Math.abs(player.getWorldY() - npcs[npcIndex].getWorldY());

		if (xDiff >= yDiff) {
			if (player.getWorldX() <= npcs[npcIndex].getWorldX()) {
				player.setDialogFacing(6);
				npcs[npcIndex].setDialogFacing(4);
			} else {
				player.setDialogFacing(4);
				npcs[npcIndex].setDialogFacing(6);
			}
		} else {
			if (player.getWorldY() <= npcs[npcIndex].getWorldY()) {
				player.setDialogFacing(2);
				npcs[npcIndex].setDialogFacing(0);
			} else {
				player.setDialogFacing(0);
				npcs[npcIndex].setDialogFacing(2);
			}
		}
	}

	private void setNextGameState() {
		
		//PLAYSTATE -> DIALOGSTATE
		if(gameStateManager.getGameState() == GameState.PLAYSTATE) {
			if (player.getinteractiableNPC() != -1 && inputManager.isSpaceJustPressed()) {
				gameStateManager.setGameState(GameState.DIALOGSTATE);
			}
		}
		//DIALOG -> PLAYSTATE
		else if(gameStateManager.getGameState() == GameState.DIALOGSTATE) {
			if (inputManager.isSpaceJustPressed()) {
				gameStateManager.setGameState(GameState.PLAYSTATE);
			}
		}
		
	}

	private void updatePlayState() {
		// UPDATE PLAYER
		player.update();

		// UPDATE NPCS
		for (int i = 0; i < npcs.length; i++) {
			if (npcs[i] != null) {
				npcs[i].update();
			}
		}
	}

	private void setPlayerInputs() {
		if (inputManager.isUp()) {
			player.setUp(true);
		}
		if (inputManager.isDown()) {
			player.setDown(true);
		}
		if (inputManager.isLeft()) {
			player.setLeft(true);
		}
		if (inputManager.isRight()) {
			player.setRight(true);
		}
	}
	

	public void paintComponent(Graphics g) {

		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		// Tile
		tileManager.draw(g2, player.getWorldX(), player.getWorldY());

		// Object
		for (int i = 0; i < objects.length; i++) {
			if (objects[i] != null) {
				objects[i].draw(g2, player.getWorldX(), player.getWorldY());
			}
		}

		// NPC
		for (int i = 0; i < npcs.length; i++) {
			if (npcs[i] != null) {
				npcs[i].draw(g2, player.getWorldX(), player.getWorldY());
			}
		}

		// Player
		player.draw(g2);

//		// DEBUG ONLY HITBOXES
//		collisionManager.drawHitBox(g2, player);
//		collisionManager.drawInteractionHitBox(g2, player);
//		collisionManager.drawHitBox(g2, npcs[0]);

		// UI
		ui.draw(g2, player.getinteractiableNPC());

		g2.dispose();
	}

}
