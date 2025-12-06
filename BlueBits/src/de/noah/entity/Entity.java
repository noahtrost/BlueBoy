package de.noah.entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Random;

import de.noah.main.Config;
import de.noah.util.CollisionManager;

//Entity CLASS specifies all entity in game like PLAYER, FRIENDLY-NPC, MONSTER-NPC ...
public class Entity {
	
	//--------------------ATTIBUTES--------------------------------------- 
	
	//WORLD COORDIANTES
	private int worldX, worldY;
	
	//MOVEMENT
	private int speed;
	private String direction = "down";
	
	//SPRITES
	private BufferedImage [] sprites;
	private int spriteCounter = 0;
	private int spriteNum = 1;

	//HITBOXES
	private Rectangle hitBox = new Rectangle(0, 0, 48, 48);
	private boolean collisionOn = false;
	private boolean interactibaleNPC;
	
	//COLLISIONMANAGER FOR COLLISION CHECKING
	protected CollisionManager collisionManager;
	
	//RANDON_NUMBER_GENERATOR
	protected static Random random = new Random();
	
	//--------------------CONSTRCTOR---------------------------------------- 
	
	//CONSTRUCTOR
	public Entity(int worldX, int worldY, int speed, BufferedImage[] sprites, boolean interactibleNPC) {
		this.worldX = worldX;
		this.worldY = worldY;
		this.speed = speed;
		this.sprites = sprites;
		this.interactibaleNPC = interactibleNPC;
	}
	


	//--------------------REAL-METHODS---------------------------------------- 
	
	//RETURN DETERMINES X-SCREEN POSTION OF ENTITY. CALCULATED RELATIVE TO PLAYER. 
	private int calculatingScreenXPosition(int playerWorldX) {
		return worldX - playerWorldX + Config.PLAYER_SCREEN_X;
	}
	
	//RETURN DETERMINES Y-SCREEN POSTION OF ENTITY. CALCULATED RELATIVE TO PLAYER. 
	private int calculatingScreenYPosition(int playerWorldY) {
		return worldY - playerWorldY + Config.PLAYER_SCREEN_Y;
	}
	
	//RETURN DETERMINES X-SCREEN POSTION OF ENTITYSHITBOX. CALCULATED RELATIVE TO PLAYER. 
	public int calculatingScreenHitBoxXPosition(int playerWorldX) {
		return worldX + hitBox.x- playerWorldX + Config.PLAYER_SCREEN_X;
	}
	
	//RETURN DETERMINES Y-SCREEN POSTION OF ENTITYSHITBOX. CALCULATED RELATIVE TO PLAYER. 
	public int calculatingScreenHitBoxYPosition(int playerWorldY) {
		return worldY + hitBox.x - playerWorldY + Config.PLAYER_SCREEN_Y;
	}
	
	//UPDATE SPRITES
	protected void setFrameSprite() {
		spriteCounter++;
		if (spriteCounter > 12) {
			if (spriteNum == 1) {
				spriteNum = 2;
			} else if (spriteNum == 2) {
				spriteNum = 1;
			}
			spriteCounter = 0;
		}
	}

	//SPECIFIES ACTION WHICH ARE ENTITY SPECIFIC - GETS INVOKED AT START OF UPDATE
	public void setAction() {}

	//MOVES ENTITY ACCORDINGLY
	protected void move() {
		if (direction.equals("up")) {
			worldY  = worldY - speed; 
		}
		else if (direction.equals("down")) {
			worldY  = worldY + speed; 
		}
		else if (direction.equals("left")) {
			worldX  = worldX - speed; 
		}
		else if (direction.equals("right")) {
			worldX  = worldX + speed; 
		}
	}
	
	//UPDATES THE ENTITYS VALUES
	public void update() {
		setCollisionOn(false);
		collisionManager.checkCollision(this);
		setAction();
		if(!isCollisionOn()) {
			move();
		}
		setFrameSprite();
	}
	
	//DRAW METHOD - DRAWS CURRENT SPRITE WHEN ENTIYS COORDS ARE IN SCREEN
	public void draw(Graphics2D g2, int playerWorldX, int playerWorldY) {
		
		//CALC SCREEN-COORDS
		int screenX = calculatingScreenXPosition(playerWorldX);
		int screenY = calculatingScreenYPosition(playerWorldY);

		// CHECK IF COORDS IN SCREEN
		if (!(screenX - Config.TILE_SIZE >= 0 && screenX <= Config.SCREEN_WIDTH  + Config.TILE_SIZE)) return;
		if (!(screenY - Config.TILE_SIZE >= 0 && screenY <= Config.SCREEN_HEIGHT + Config.TILE_SIZE)) return;
		
		
		//GET THE RIGHT SPRITE
		BufferedImage sprite = null;

		switch (direction) {
		case "up":
			if (spriteNum == 1) {
				sprite = sprites[0];
			}
			if (spriteNum == 2) {
				sprite = sprites[1];
			}
			break;
		case "down":
			if (spriteNum == 1) {
				sprite = sprites[2];
			}
			if (spriteNum == 2) {
				sprite = sprites[3];
			}
			break;
		case "left":
			if (spriteNum == 1) {
				sprite = sprites[4];
			}
			if (spriteNum == 2) {
				sprite = sprites[5];
			}
			break;
		case "right":
			if (spriteNum == 1) {
				sprite = sprites[6];
			}if (spriteNum == 2) {
				sprite = sprites[7];
			}
			break;
		}
		
		//DRAW SPRITE
		g2.drawImage(sprite, screenX, screenY, Config.TILE_SIZE, Config.TILE_SIZE, null);
	}

	//--------------------GETTERS/SETTERS---------------------------------------- 
	
	
	public int getWorldX() {
		return worldX;
	}


	public void setWorldX(int worldX) {
		this.worldX = worldX;
	}


	public int getWorldY() {
		return worldY;
	}


	public void setWorldY(int worldY) {
		this.worldY = worldY;
	}


	public String getDirection() {
		return direction;
	}


	public void setDirection(String direction) {
		this.direction = direction;
	}


	public boolean isCollisionOn() {
		return collisionOn;
	}


	public void setCollisionOn(boolean collisionOn) {
		this.collisionOn = collisionOn;
	}


	public int getSpeed() {
		return speed;
	}


	public BufferedImage[] getSprites() {
		return sprites;
	}


	public int getSpriteCounter() {
		return spriteCounter;
	}


	public int getSpriteNum() {
		return spriteNum;
	}


	public Rectangle getHitBox() {
		return hitBox;
	}
	
	public void setHitBox(Rectangle hitBox) {
		this.hitBox = hitBox;
	}


	public void setSpriteCounter(int spriteCounter) {
		this.spriteCounter = spriteCounter;
	}


	public void setSpriteNum(int spriteNum) {
		this.spriteNum = spriteNum;
	}


	public boolean isInteractibaleNPC() {
		return interactibaleNPC;
	}



	public void setCollisionManager(CollisionManager collisionManager) {
		this.collisionManager = collisionManager;
	}
}
