package de.noah.entity;

import java.awt.image.BufferedImage;

//FRIENDLY OLDMAN
public class NPC_OldMan extends Entity {

	// --------------------ATTIBUTES---------------------------------------

	// COUNTER AND LIMIT FOR NEW DIRECTION - SETS OLDMAN UP TO 3 SECONDS IN ONE DIRECTION
	private int directionSwitchCounter;
	private final int DIRECTION_LIMIT = 180;
	private int diretionDecider;

	
	//--------------------CONSTRCTOR---------------------------------------- 

	public NPC_OldMan(int worldX, int worldY, int speed, BufferedImage[] sprites, boolean interactibleNPC) {
		super(worldX, worldY, speed, sprites, interactibleNPC);
	}

	
	
	//--------------------REAL-METHODS---------------------------------------- 
	
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

	//SPECIFIC ACTION FOR OLDMAN ENTITY
	public void setAction() {
		
		if(isPlayerCollision()) {
			setDirection(getDirection());
			setSpriteNum(1);
		}
		
		else if(directionSwitchCounter > DIRECTION_LIMIT || isCollisionOn()) setDirection();
		directionSwitchCounter++;
		
	}		
}
