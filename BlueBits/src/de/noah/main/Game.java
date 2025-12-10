package de.noah.main;

import java.awt.Graphics2D;

import de.noah.audio.SoundManager;
import de.noah.config.Config;
import de.noah.core.AssetSetter;
import de.noah.core.CollisionManager;
import de.noah.core.GameStateManager;
import de.noah.core.ObjectManager;
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
	private SoundManager soundEffectManager = new SoundManager();

	// GAME STATE MANAGER
	private GameStateManager gameStateManager = new GameStateManager();

	// OBJECTMANAGER
	private ObjectManager objectManager;

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

	// RUNNING
	private boolean running;

	// -------------------CONSTRUCTOR----------------------

	public Game(InputManager inputManager) {
		this.inputManager = inputManager;
	}

	// --------------------SETUP-GAME------------------------

	// GIVES THE COLLISIONMANAGER TO ALL REQUIERING OBJECTS
	private void setCollisionManagerToAllEntitys() {
		player.setCollisionManager(collisionManager);
		for (Entity entity : npcs) {
			if (entity == null)
				continue;
			entity.setCollisionManager(collisionManager);
		}
	}

	// SETUPS GAMESTART
	public void setupGame() {
		spriteManager.setUp();
		tileManager.setUp(spriteManager.getTileSprites());
		player = new Player(Config.TILE_SIZE * 23, Config.TILE_SIZE * 21, 4, spriteManager.getPlayerSprites());
		assetSetter.setObjects(objects, spriteManager.getObjectSprites());
		assetSetter.setNPCS(npcs, spriteManager.getAllNPCsprites());
		objectManager = new ObjectManager(player, objects, soundEffectManager);
		collisionManager = new CollisionManager(player, npcs, objects, tileManager.getMapTileNum(),
				tileManager.getTile(), objectManager);
		setCollisionManagerToAllEntitys();
		playStateUI = new PlayStateUI(spriteManager.getUiSprites());
		dialogStateUI = new DialogStateUI(spriteManager.getUiSprites(), soundEffectManager);
		gameStateManager.setGameState(GameState.PLAYSTATE);
		playState = new PlayState(player, npcs);
		dialogState = new DialogState(player, npcs);

		soundManager.playMusic(0);
		running = true;
	}

	// --------------------UPDATE: CHECK NEXT STATE----------------------------

	// HANDLES CHANGING PLAYSTATE TO DIALOGSTATE
	private void checkPlayStateToDialogState() {

		// IF PLAYER IS IN RANGE OF INTERACTIABLE NPC && PRESSES SPACE
		if (player.getinteractiableNPC() != -1 && inputManager.isSpaceJustPressed()) {
			soundEffectManager.playSE("speak");
			dialogState.setNpcDialog(true);
			gameStateManager.setGameState(GameState.DIALOGSTATE);
			inputManager.setSpaceJustPressed(false);
			return;
		}

		// IF PLAYER TOUCHES OBJECTS WHICH REQUIRES DAILOG
		if (objectManager.isInvokeDialogState()) {
			gameStateManager.setGameState(GameState.DIALOGSTATE);
			dialogState.setObjectDialog(true);
			return;
		}

	}

	// HANDLES CHANGING DIALOGSTATE TO PLAYSTATE
	private void checkDialogStateToPlayState() {

		// IF PLAYER PRESSES ENTER
		if (inputManager.isEnterJustPressed()) {
			dialogState.setNpcDialog(false);
			dialogState.setObjectDialog(false);
			gameStateManager.setGameState(GameState.PLAYSTATE);
		}
	}

	// INVOKES END OF GAME
	private void checkEndGame() {

		// IF PLAYER COLLECTS END INVOKING OBJECT
		if (objectManager.isEndGame()) {
			running = false;
			soundManager.stop();
		}
	}

	// SETS NEXT GAMESTATE BASED ON CONDITIONS
	private void setNextGameState() {

		// PLAYSTATE -> DIALOGSTATE
		if (gameStateManager.getGameState() == GameState.PLAYSTATE) {
			checkPlayStateToDialogState();
		}

		// DIALOG -> PLAYSTATE
		else if (gameStateManager.getGameState() == GameState.DIALOGSTATE) {
			checkDialogStateToPlayState();
		}

		// X -> END
		checkEndGame();
	}

	// --------------------UPDATING---------------------------

	// PROPAGATES INPUTS TO PLAYER ENTITY
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

	// RESET JUST PRESSED INPUTS
	private void resetJustPressedInputs() {
		inputManager.setSpaceJustPressed(false);
		inputManager.setEnterJustPressed(false);
	}

	// UPDATES GAME LOGIC
	public void update() {

		setPlayerInputs();

		// UPDATES PLATSTATE
		if (gameStateManager.getGameState() == GameState.PLAYSTATE) {
			playState.update();
		}

		// UPDATES DIALOGSTATE
		else if (gameStateManager.getGameState() == GameState.DIALOGSTATE) {
			dialogState.update();
		}

		setNextGameState();
	}

	// --------------------DRAW-------------------------------

	// PROPAGATES INPUTS TO DIALOG STATE UI
	private void setDialogStateUIInputs() {
		if (inputManager.isSpaceJustPressed()) {
			dialogStateUI.setSpace(true);
		}
	}

	// DRAWS GAME
	public void draw(Graphics2D g2) {

		// DRAW TILES
		tileManager.draw(g2, player.getWorldX(), player.getWorldY());

		// DRAW OBJECTS
		for (int i = 0; i < objects.length; i++) {
			if (objects[i] != null) {
				objects[i].draw(g2, player.getWorldX(), player.getWorldY());
			}
		}

		// DRAW NPC
		for (int i = 0; i < npcs.length; i++) {
			if (npcs[i] != null) {
				npcs[i].draw(g2, player.getWorldX(), player.getWorldY());
			}
		}

		// DRAW PLAYER
		player.draw(g2);

//--------------------- ONLY FOR DEBUGGING PURPOSES - DISPLAYS HITBOXES OF ALL ENTITYS---------------------

//			collisionManager.drawHitBox(g2, player);
//			collisionManager.drawInteractionHitBox(g2, player);
//			collisionManager.drawHitBox(g2, npcs[0]);

//---------------------------------------------------------------------------------------------------------

		// DRAWING UI BASED ON GAMESTATE

		// DRAW PLATSTATE - UI (EXPECTS INDEX OF INTERACTIABLE PLAYER IN RANGE && PLAYER
		// CURRENT KEY COUNT)
		if (gameStateManager.getGameState() == GameState.PLAYSTATE) {

			playStateUI.draw(g2, player.getinteractiableNPC(), player.getKeyCounter());

		}

		// DRAW DIALOGSTATE - UI (EXPECTS INDEX OF INTERACTIABLE PLAYER IN RANGE &&
		// PLAYER CURRENT KEY COUNT)
		else if (gameStateManager.getGameState() == GameState.DIALOGSTATE) {

			setDialogStateUIInputs();

			// DIALOG STATE INVOKED BY NPC
			if (dialogState.isNpcDialog()) {
				dialogStateUI.draw(g2, npcs[player.getinteractiableNPC()]);
			}
			// DIALOG STATE INVOKED BY OBJECT
			else if (dialogState.isObjectDialog()) {
				dialogStateUI.draw(g2, objectManager.getObjectName());
				objectManager.setInvokeDialogState(false);
			}

		}

		// RESET THE JUST PRESSED INPUTS
		resetJustPressedInputs();
	}

	// --------------------GETTER AND SETTERS-------------------------------

	public boolean isRunning() {
		return running;
	}

}
