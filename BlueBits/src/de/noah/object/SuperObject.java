package de.noah.object;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import de.noah.main.Config;


public class SuperObject {
// --------------------ATTIBUTES---------------------------------------
	private final String name;
	private final int worldX, worldY;
	private final boolean collision;
	private final BufferedImage sprite;
	private final Rectangle hitBox = new Rectangle(0, 0, 48, 48);

// --------------------CONSTRUCTOR---------------------------------------
	public SuperObject(String name, int worldX, int worldY, boolean collision, BufferedImage sprite) {
		this.name = name;
		this.worldX = worldX;
		this.worldY = worldY;
		this.collision = collision;
		this.sprite = sprite;
	}

// --------------------REAL-METHODS---------------------------------------

	// RETURN DETERMINES X-SCREEN POSTION OF ENTITY. CALCULATED RELATIVE TO PLAYER.
	protected int calculatingScreenXPosition(int playerWorldX) {
		return worldX - playerWorldX + Config.PLAYER_SCREEN_X;
	}

	// RETURN DETERMINES Y-SCREEN POSTION OF ENTITY. CALCULATED RELATIVE TO PLAYER.
	protected int calculatingScreenYPosition(int playerWorldY) {
		return worldY - playerWorldY + Config.PLAYER_SCREEN_Y;
	}

	public void draw(Graphics2D g2, int playerWorldX, int playerWorldY) {
		// CALC SCREEN-COORDS
		int screenX = calculatingScreenXPosition(playerWorldX);
		int screenY = calculatingScreenXPosition(playerWorldY);

		// CHECK IF COORDS IN SCREEN
		if (!(screenX - Config.TILE_SIZE >= 0 && screenX <= Config.SCREEN_WIDTH  + Config.TILE_SIZE)) return;
		if (!(screenY - Config.TILE_SIZE >= 0 && screenY <= Config.SCREEN_HEIGHT + Config.TILE_SIZE)) return;

		//DRAW SPRITE
		g2.drawImage(sprite, screenX, screenY, Config.TILE_SIZE, Config.TILE_SIZE, null);
	}
	
	// --------------------GETTERS/SETTERS---------------------------------------
	public String getName() {
		return name;
	}

	public int getWorldX() {
		return worldX;
	}

	public int getWorldY() {
		return worldY;
	}

	public boolean isCollision() {
		return collision;
	}


	public Rectangle getHitBox() {
		return hitBox;
	}

	
}
