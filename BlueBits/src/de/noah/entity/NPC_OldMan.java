package de.noah.entity;

import java.awt.image.BufferedImage;

//FRIENDLY OLDMAN
public class NPC_OldMan extends Entity {

	// --------------------ATTIBUTES---------------------------------------

	// COUNTER AND LIMIT FOR NEW DIRECTION - SETS OLDMAN UP TO 3 SECONDS IN ONE
	// DIRECTION
	private int directionSwitchCounter;
	private final int DIRECTION_LIMIT = 180;
	private int diretionDecider;
	
	//CHILLING
	private int currentChillLimit = 0;

	// --------------------CONSTRCTOR----------------------------------------

	public NPC_OldMan(int worldX, int worldY, int speed, BufferedImage[] sprites, boolean interactibleNPC) {
		super(worldX, worldY, speed, sprites, interactibleNPC);
		setupLines();
	}

	private void setupLines() {
		setSpeech(new String[5]);
		getSpeech()[0] = "A Stranger?... How...?";
		getSpeech()[1] = "How did you come here?";
		getSpeech()[2] = "You don't know..?\nThat's even more odd.";
		getSpeech()[3] = "I am afraid I can't help you\nfind your way back home..\nBut I heard there is a way.";
		getSpeech()[4] = "Good luck to you my young\nfriend!";
	}

	// --------------------UPDATING THE OLD MAN----------------------------------------

	// DECIDING NEXT DIRECTION WITH PROBABILITY OF 1/4
	private void setDirection() {

		diretionDecider = Entity.random.nextInt(100);

		if (diretionDecider < 25) {
			setDirection("up");
		} else if (diretionDecider < 50) {
			setDirection("down");
		} else if (diretionDecider < 75) {
			setDirection("left");
		} else if (diretionDecider < 100) {
			setDirection("right");
		}

		// OLD MAN WALK AT LEAST 1 AND AT MAX 3 SECONDS PER SECOND
		directionSwitchCounter = Entity.random.nextInt(120);
	}

	private boolean stillChillin() {

		if (getChillTime() >= currentChillLimit) {
			setChillTime(0);
			setChillin(false);
			return false;
		}
		
		setChillTime(getChillTime() + 1);
		return true;
	}

	private boolean maybeChill() {
		if (isChillin())
			return true;

		int randInt = random.nextInt(100);
		if (randInt < 35) {
			currentChillLimit = randInt * 3;
			setChillTime(0);
			setChillin(true);
			if(getDirection().equals("up")) setDirection("down");
			return true;

		}
		return false;
	}

	public void setAction() {

		if (isChillin()) {
			if(stillChillin()) {
				return;
			};
		}

		if (isPlayerCollision()) {
			setDirection(getDirection());
			setSpriteNum(1);
			return;
		}

		if (isCollisionOn()) {
			setDirection();
		} else if (directionSwitchCounter > DIRECTION_LIMIT) { 
			if (!maybeChill()) {
				setDirection();
			}
		}

		if (!isChillin())
			directionSwitchCounter++;
	}

}
