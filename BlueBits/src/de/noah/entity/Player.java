package de.noah.entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import de.noah.main.Config;

//PLAYER CLASS
public class Player extends Entity {

	
	// --------------------ATTIBUTES---------------------------------------

	
	//INPUT
	@SuppressWarnings("unused")
	private boolean left, right, up, down, space , pause;


	//HITBOX FOR INTERACTIONS WITH NPCS
	private Rectangle interactionHitBox;

	
	// --------------------CONSTRUCTOR---------------------------------------

	public Player(int worldX, int worldY, int speed, BufferedImage[] sprites) {
		super(Config.TILE_SIZE*23, Config.TILE_SIZE*21, speed, sprites, false);
		Rectangle playerHitBox = new Rectangle(8,16,16,32);
		setHitBox(playerHitBox);
		this.interactionHitBox = new Rectangle(-Config.TILE_SIZE, -Config.TILE_SIZE, Config.TILE_SIZE*3, Config.TILE_SIZE*3);
		
	}

	
	// --------------------REAL-METHODS---------------------------------------

	
	// CHECKS IF THERE WAS ANY INPUTS
	private boolean movementOccured() {
		return (up || down|| left || right);
	}
	
	private void setDirection() {
		if (up) {
			setDirection("up");
			return;
		}
		if (down) {
			setDirection("down");
			return;
		} 
		if (right) {
			setDirection("right");
			return;
		} 
		if (left) {
			setDirection("left");
			return;
		}
	}
	
	private void resetPlayerInputs() {
		setUp(false);
		setDown(false);
		setLeft(false);
		setRight(false);
}

	//UPDATING PLAYER VALUES
	public void update() {
		
		if (movementOccured()) {
			setDirection();

			// CHECK TILE COLLISION
			//gp.cc.checkTile(this);

			// CHECK OBJECT COLLISION
			//int objIndex = gp.cc.checkObject(this, true);
			//objectCollisionHandler(objIndex);

			// CHECK NPC COLLISION
			//gp.cc.checkEntities(this);

			if (isCollisionOn() == false) {
				move();
			}
			setFrameSprite();
			}
		
		resetPlayerInputs();
		setCollisionOn(false);
		}

		// CHECK NPC INTERACTION
		//handleNPC(gp.cc.checkInteractibleEntities(this));
		

	//DRAWS PLAYER
	public void draw(Graphics2D g2) {
		BufferedImage image = null;

		switch (getDirection()) {
		case "up":
			if (getSpriteNum() == 1) {
				image = getSprites()[0];
			}
			if (getSpriteNum() == 2) {
				image = getSprites()[1];
			}
			break;
		case "down":
			if (getSpriteNum() == 1) {
				image = getSprites()[2];
			}
			if (getSpriteNum() == 2) {
				image = getSprites()[3];
			}
			break;
		case "left":
			if (getSpriteNum() == 1) {
				image = getSprites()[4];
			}
			if (getSpriteNum() == 2) {
				image = getSprites()[5];
			}
			break;
		case "right":
			if (getSpriteNum() == 1) {
				image = getSprites()[6];
			}
			if (getSpriteNum() == 2) {
				image = getSprites()[7];
			}
			break;
		}

		g2.drawImage(image, Config.PLAYER_SCREEN_X,Config.PLAYER_SCREEN_Y, Config.TILE_SIZE, Config.TILE_SIZE, null);
	}
	
	
	// -------------------GETTER/SETTERS---------------------------------------
	
	public boolean isLeft() {
		return left;
	}


	public void setLeft(boolean left) {
		this.left = left;
	}


	public boolean isRight() {
		return right;
	}


	public void setRight(boolean right) {
		this.right = right;
	}


	public boolean isUp() {
		return up;
	}


	public void setUp(boolean up) {
		this.up = up;
	}


	public boolean isDown() {
		return down;
	}


	public void setDown(boolean down) {
		this.down = down;
	}


	public boolean isSpace() {
		return space;
	}


	public void setSpace(boolean space) {
		this.space = space;
	}

	// GETTERS AND SETTERS
	public Rectangle getInteractionHitBox() {
		return interactionHitBox;
	}
}
