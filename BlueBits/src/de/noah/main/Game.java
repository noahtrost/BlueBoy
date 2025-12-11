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
import de.noah.gamestates.TitleState;
import de.noah.graphics.SpriteManager;
import de.noah.input.InputManager;
import de.noah.object.SuperObject;
import de.noah.tile.TileManager;
import de.noah.ui.DialogStateUI;
import de.noah.ui.PlayStateUI;
import de.noah.ui.TitleStateUI;

public class Game {

	// ---------------------ATTRIBUTES----------------------

	// GAMESTATES
	private PlayState playState;
	private DialogState dialogState;
	private TitleState titleState;

	// UISTATES
	private PlayStateUI playStateUI;
	private DialogStateUI dialogStateUI;
	private TitleStateUI titleStateUI;

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
		
		collisionManager = new CollisionManager(
			    player,
			    npcs,
			    objects,
			    tileManager.getMapTileNum(),
			    tileManager.getTile(),
			    objectManager
			);
		setCollisionManagerToAllEntitys();
		
		playStateUI = new PlayStateUI(spriteManager.getUiSprites());
		dialogStateUI = new DialogStateUI(spriteManager.getUiSprites());
		titleStateUI = new TitleStateUI(spriteManager.getUiSprites());
		
		
		playState = new PlayState(player, soundEffectManager, playStateUI, npcs);
		dialogState = new DialogState(player, soundEffectManager, dialogStateUI);
		titleState = new TitleState(player, soundEffectManager, titleStateUI);

		gameStateManager.setGameState(GameState.TITLESTATE);
		soundManager.playMusic(6);
		running = true;
	}

	// --------------------UPDATE: CHECK NEXT STATE----------------------------

	// HANDLES CHANGING PLAYSTATE TO DIALOGSTATE
	private void checkPlayStateToDialogState() {

		// IF PLAYER IS IN RANGE OF INTERACTIABLE NPC && PRESSES SPACE
		if (player.getinteractiableNPC() != -1 && inputManager.isSpaceJustPressed()) {
			inputManager.setSpaceJustPressed(false);
			soundEffectManager.playSE("speak");
			gameStateManager.setGameState(GameState.DIALOGSTATE);

			return;
		}

		// IF PLAYER TOUCHES OBJECTS WHICH REQUIRES DAILOG
		if (objectManager.getDialogNeedingObject() != null) {
	
			gameStateManager.setGameState(GameState.DIALOGSTATE);
			
			return;
		}

	}

	// HANDLES CHANGING DIALOGSTATE TO PLAYSTATE
	private void checkDialogStateToPlayState() {

		// IF PLAYER PRESSES ENTER
		if (inputManager.isEnterJustPressed()) {
			objectManager.setDialogNeedingObject(null);
			gameStateManager.setGameState(GameState.PLAYSTATE);
		}
	}
	
	// HANDLES CHANGING DIALOGSTATE TO PLAYSTATE
	private void checkTitleStateToPlayState() {

			// IF PLAYER PRESSES ENTER
			if (titleState.isInvokePlayState()) {
				gameStateManager.setGameState(GameState.PLAYSTATE);
				soundManager.stop();
				soundManager.playMusic(0);
			}
			if(titleState.isInvokeQuitGame()) {
				soundManager.stop();
				running = false;
				System.exit(0);
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

		//TITLESTATE -> PLAYSTATE
		if(gameStateManager.getGameState() == GameState.TITLESTATE) {
			checkTitleStateToPlayState();
		}
		
		// PLAYSTATE -> DIALOGSTATE
		else if (gameStateManager.getGameState() == GameState.PLAYSTATE) {
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
	private void setPlayStateInputs() {
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
	
	private void setTitleStateInputs() {
		
		if(titleState.isNameScreen()) {
			char c = inputManager.consumeTypedChar();
			if(c != 0) {
				titleState.setCharacter(c);
			}
		}
		
		if (inputManager.isLeftJustPressed()) { 
			titleState.setLeft(true);
		}
		if (inputManager.isRightJustPressed()) { 
			titleState.setRight(true);
		}
		if (inputManager.isEnterJustPressed()) { 
			titleState.setEnter(true);
		}
		if (inputManager.isSpaceJustPressed()) {  
			titleState.setSpace(true);
		}
		
	}
	
	private void setDialogStateInputs() {
		if (inputManager.isSpaceJustPressed()) { 
			dialogState.setSpace(true);
		}
	}

	// RESET JUST PRESSED INPUTS
	private void resetJustPressedInputs() {
		inputManager.setSpaceJustPressed(false);
		inputManager.setEnterJustPressed(false);
		inputManager.setUpJustPressed(false);
		inputManager.setDownJustPressed(false);
		inputManager.setRightJustPressed(false);
		inputManager.setLeftJustPressed(false);
	}


	// UPDATES GAME LOGIC
	public void update() {

		setNextGameState();

		//UPDATES TITLESTATE
		if(gameStateManager.getGameState() == GameState.TITLESTATE) {
			setTitleStateInputs();
			titleState.update();
		}
		
		// UPDATES PLATSTATE
		else if (gameStateManager.getGameState() == GameState.PLAYSTATE) {   
			setPlayStateInputs();
			playState.update();
			
		}

		// UPDATES DIALOGSTATE
		else if (gameStateManager.getGameState() == GameState.DIALOGSTATE) {  
			
			setDialogStateInputs();
				
			
			
			Entity dialogPartner;
			if(player.getinteractiableNPC() == -1) {
				dialogPartner = null;
			} else {
				dialogPartner = npcs[player.getinteractiableNPC()];
			}
			
			SuperObject dialogObject;
			dialogObject = objectManager.getDialogNeedingObject();
			
			
			dialogState.update(dialogPartner, dialogObject);
		}

		// RESET THE JUST PRESSED INPUTS
		resetJustPressedInputs();
	}

	// --------------------DRAW-------------------------------

	// DRAWS GAME
	public void draw(Graphics2D g2) {
		
		// DRAW TITLESTATE - UI
		if (gameStateManager.getGameState() == GameState.TITLESTATE) {
					 
					 titleState.draw(g2);
					 return;

				}

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
		
		// DRAW PLATSTATE - UI
		if (gameStateManager.getGameState() == GameState.PLAYSTATE) {

			playState.draw(g2);

		}

		// DRAW DIALOGSTATE - UI
		else if (gameStateManager.getGameState() == GameState.DIALOGSTATE) {

			dialogState.draw(g2);

		}

	}

	// --------------------GETTER AND SETTERS-------------------------------

	public boolean isRunning() {
		return running;
	}

}
