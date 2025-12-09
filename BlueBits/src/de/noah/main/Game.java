package de.noah.main;

import java.awt.Graphics2D;

import de.noah.audio.SoundManager;
import de.noah.config.Config;
import de.noah.core.AssetSetter;
import de.noah.core.CollisionManager;
import de.noah.core.GameStateManager;
import de.noah.entity.Entity;
import de.noah.entity.Player;
import de.noah.gamestates.DialogState;
import de.noah.gamestates.GameState;
import de.noah.gamestates.PlayState;
import de.noah.graphics.SpriteManager;
import de.noah.input.InputManager;
import de.noah.object.SuperObject;
import de.noah.tile.TileManager;
import de.noah.ui.DialogStateUI;
import de.noah.ui.PlayStateUI;

public class Game {

	// ---------------------ATTRIBUTES----------------------

	
	// GAMESTATES
	private PlayState playState;
	private DialogState dialogState;

	
	// UISTATES
	private PlayStateUI playStateUI;
	private DialogStateUI dialogStateUI;

	
	// SPRITEMANAGER
	private final SpriteManager spriteManager = new SpriteManager();

	// TILEMANAGER
	private final TileManager tileManager = new TileManager();

	// COLLISIONMANAGER
	private CollisionManager collisionManager;

	// SOUNDMANAGER
	private SoundManager soundManager = new SoundManager();
	@SuppressWarnings("unused")
	private SoundManager soundEffectManager = new SoundManager();

	// GAME STATE MANAGER
	private GameStateManager gameStateManager = new GameStateManager();

	// AssetSetter
	private AssetSetter assetSetter = new AssetSetter();

	
	// PLAYER
	private Player player;

	// NPCS
	private final Entity npcs[] = new Entity[10];

	// OBJECTS
	private final SuperObject objects[] = new SuperObject[10];

	
	// INPUTMANAGER
	private final InputManager inputManager;

	
	// -------------------CONSTRUCTOR----------------------

	
	public Game(InputManager inputManager) {
		this.inputManager = inputManager;
	}

	
	// --------------------SETUP-GAME------------------------

	
	private void setCollisionManagerToAllEntitys() {
		player.setCollisionManager(collisionManager);
		for (Entity entity : npcs) {
			if (entity == null)
				continue;
			entity.setCollisionManager(collisionManager);
		}
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
		playStateUI = new PlayStateUI(spriteManager.getUiSprites());
		dialogStateUI = new DialogStateUI(spriteManager.getUiSprites());
		gameStateManager.setGameState(GameState.PLAYSTATE);
		playState = new PlayState(player, npcs);
		dialogState = new DialogState(player, npcs);

		soundManager.playMusic(0);
	}

	
	// --------------------UPDATE----------------------------
	
	
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

	private void setNextGameState() {
		// PLAYSTATE -> DIALOGSTATE
		if (gameStateManager.getGameState() == GameState.PLAYSTATE) {
			if (player.getinteractiableNPC() != -1 && inputManager.isSpaceJustPressed()) {
				gameStateManager.setGameState(GameState.DIALOGSTATE);
			}
		}
		// DIALOG -> PLAYSTATE
		else if (gameStateManager.getGameState() == GameState.DIALOGSTATE) {
			if (inputManager.isSpaceJustPressed()) {
				gameStateManager.setGameState(GameState.PLAYSTATE);
			}
		}
	}

	public void update() {
		// PLAYERINPUTS
		setPlayerInputs();

		// PLATSTATE
		if (gameStateManager.getGameState() == GameState.PLAYSTATE) {
			playState.update();
		}
		// DIALOGSTATE
		else if (gameStateManager.getGameState() == GameState.DIALOGSTATE) {
			dialogState.update();
		}
		// SET GAME STATE FOR NEW FRAME
		setNextGameState();
		inputManager.setSpaceJustPressed(false);
	}
	
	
	// --------------------DRAW-------------------------------
	

	public void draw(Graphics2D g2) {
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

//			// DEBUG ONLY HITBOXES
//			collisionManager.drawHitBox(g2, player);
//			collisionManager.drawInteractionHitBox(g2, player);
//			collisionManager.drawHitBox(g2, npcs[0]);

		// UI
		// PLATSTATE
		if (gameStateManager.getGameState() == GameState.PLAYSTATE) {
			playStateUI.draw(g2, player.getinteractiableNPC());
		}
		// DIALOGSTATE
		else if (gameStateManager.getGameState() == GameState.DIALOGSTATE) {
			dialogStateUI.draw(g2);
		}
	}
	
	
}
