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
	private int interactiableNPC = -1;

	
	// --------------------CONSTRUCTOR---------------------------------------

	public Player(int worldX, int worldY, int speed, BufferedImage[] sprites) {
		super(Config.TILE_SIZE*23, Config.TILE_SIZE*21, speed, sprites, false);
		Rectangle playerHitBox = new Rectangle(8,16,32,32);
		setHitBox(playerHitBox);
		this.interactionHitBox = new Rectangle(-Config.TILE_SIZE, -Config.TILE_SIZE, Config.TILE_SIZE*3, Config.TILE_SIZE*3);
	}

	
	// --------------------REAL-METHODS---------------------------------------

	
	// CHECKS IF THERE WAS ANY INPUTS
	private boolean movementOccured() {
		return (up || down|| left || right);
	}
	
	private void setDirection() {
		
		String direction =  "";
		
		if (up) {
			direction  += "up";
		}
		if (down) {
			direction  += "down";
		} 
		if (right) {
			direction  += "right";
		} 
		if (left) {
			direction  += "left";
		}
		setDirection(direction);
	}
	
	private void resetPlayerInputs() {
		setUp(false);
		setDown(false);
		setLeft(false);
		setRight(false);
	}
	
	//MOVES ENTITY ACCORDINGLY
	protected void move() {
		if (up) {
			setWorldY(getWorldY() - getSpeed());  
		}
		if (down) {
			setWorldY(getWorldY() + getSpeed());  
		}
		if (left) {
			setWorldX(getWorldX() - getSpeed());  
		}
		if (right) {
			setWorldX(getWorldX() + getSpeed());  
		}
	}
	
	
	//UPDATING PLAYER VALUES
	public void update() {
		setCollisionOn(false);
		
		if (movementOccured()) {
			setDirection();
			collisionManager.checkCollision(this);
			if (isCollisionOn() == false) {
				move();
			}
			setFrameSprite();
			}
		
		resetPlayerInputs();
		}
		

	//DRAWS PLAYER
	public void draw(Graphics2D g2) {
		BufferedImage image = null;
		
		if(getDialogFacing() != -1) {
			image = getSprites()[getDialogFacing()];
		}
		else {
			if(getDirection().contains("up")) {
				if (getSpriteNum() == 1) {
					image = getSprites()[0];
				}
				if (getSpriteNum() == 2) {
					image = getSprites()[1];
				}
			}
			else if(getDirection().contains("down")) {
				if (getSpriteNum() == 1) {
					image = getSprites()[2];
				}
				if (getSpriteNum() == 2) {
					image = getSprites()[3];
				}
			}
			else if(getDirection().contains("left")) {
				if (getSpriteNum() == 1) {
					image = getSprites()[4];
				}
				if (getSpriteNum() == 2) {
					image = getSprites()[5];
				}
			}
			else if(getDirection().contains("right")) { 
				if (getSpriteNum() == 1) {
					image = getSprites()[6];
				}
				if (getSpriteNum() == 2) {
					image = getSprites()[7];
				}
			}
		}

		g2.drawImage(image, Config.PLAYER_SCREEN_X,Config.PLAYER_SCREEN_Y, Config.TILE_SIZE, Config.TILE_SIZE, null);
		setDialogFacing(-1);
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


	public int isInteractionPossible() {
		return getinteractiableNPC();
	}


	public int getinteractiableNPC() {
		return interactiableNPC;
	}


	public void setinteractiableNPC(int interactiableNPC) {
		this.interactiableNPC = interactiableNPC;
	}
}
