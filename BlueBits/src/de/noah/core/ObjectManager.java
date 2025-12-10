package de.noah.core;

import java.awt.Rectangle;

import de.noah.audio.SoundManager;
import de.noah.config.Config;
import de.noah.entity.Player;
import de.noah.object.SuperObject;

public class ObjectManager {

	// ---------------------ATTRIBUTES----------------------

	private Player player;
	private SuperObject[] objects;
	private SoundManager soundEffectManager;
	private boolean invokeDialogState = false;
	private boolean objectInteractionCoolDown = false;
	private static Rectangle interactionCoolDownZone = new Rectangle(0, 0, 0, 0);
	private boolean endGame = false;
	private String objectName = "";

	// -------------------CONSTRUCTOR----------------------
	public ObjectManager(Player player, SuperObject[] objects, SoundManager soundEffectManager) {
		this.player = player;
		this.objects = objects;
		this.soundEffectManager = soundEffectManager;
	}

	// CHECK IF PLAYER LEFT OBJECT COOLDOWN ZONE
	private void checkObjectCoolDown() {
		Rectangle playerHitBox = new Rectangle(player.getWorldX() + player.getHitBox().x,
				player.getWorldY() + player.getHitBox().y, player.getHitBox().width, player.getHitBox().height);
		if (!playerHitBox.intersects(interactionCoolDownZone)) {
			objectInteractionCoolDown = false;
			interactionCoolDownZone = null;
		}
	}

	// HANDLES KEY COLLISION
	private void keyHandlingRoutine(int i) {
		player.setKeyCounter(player.getKeyCounter() + 1);
		objects[i] = null;
		soundEffectManager.playSE("coin");
	}

	// HANDLES DOOR COLLISION
	private void doorHandlingRoutine(int i) {

		// PLAYER HAS ENOUGH KEYS - OPEN DOOR
		if (player.getKeyCounter() > 0) {
			objects[i] = null;
			player.setKeyCounter(player.getKeyCounter() - 1);
			soundEffectManager.playSE("unlock");
			return;
		}

		// PLAYER HASN'T ENOUGH KEYS && NO COOL DOWN - (CAN'T RETRIGGER WITHOUT MOVING
		// AWAY)
		if (!objectInteractionCoolDown) {

			// ACTIVATE COOLDOWN && SPECIFY AREA WHICH NEEDS TO BE LEFT TO RETRIGGER DIALOG
			objectInteractionCoolDown = true;
			int x = objects[i].getWorldX() - Config.TILE_SIZE;
			int y = objects[i].getWorldY() - Config.TILE_SIZE;
			int width = Config.TILE_SIZE * 3;
			int height = Config.TILE_SIZE * 3;
			interactionCoolDownZone = new Rectangle(x, y, width, height);

			// SETS DIALOG STATE TRUE AND PROPAGATES KIND OF OBJECT THAT TRIGGERED EVENT
			soundEffectManager.playSE("blocked");
			invokeDialogState = true;
			objectName = "door";
		}
	}

	// HANDLES CHEST COLLISION
	private void chestHandlingRoutine(int i) {
		objects[i] = null;
		soundEffectManager.playSE("fanfare");
		endGame = true;
	}

	// HANDLER FOR PLAYER OBJECT INTERATION
	public void handle(int i) {

		if (objectInteractionCoolDown) {
			checkObjectCoolDown();
		}

		if (i == -1)
			return;

		String name = objects[i].getName();

		switch (name) {
		case "key": {
			keyHandlingRoutine(i);
			break;
		}

		case "door": {
			doorHandlingRoutine(i);
			break;
		}

		case "chest": {
			chestHandlingRoutine(i);
			break;
		}
		}

	}

	// --------------------GETTER AND SETTERS-------------------------------

	public void setInvokeDialogState(boolean invokeDialogState) {
		this.invokeDialogState = invokeDialogState;
	}

	public boolean isEndGame() {
		return endGame;
	}

	public boolean isInvokeDialogState() {
		return invokeDialogState;
	}

	public String getObjectName() {
		return objectName;
	}

	public void setObjectIndex(String objectName) {
		this.objectName = objectName;
	}
}
