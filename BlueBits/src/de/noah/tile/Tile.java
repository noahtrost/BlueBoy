package de.noah.tile;

import java.awt.image.BufferedImage;

//CLASS FOR ALL TILES
public class Tile {
	
//------------ATTRIBTUES---------------------
	private BufferedImage sprite;
	private boolean collision = false;
	
//------------CONSTRUCTOR--------------------
	public Tile(boolean collision) {
		this.collision = collision;
	}

//------------GETTER/SETTER--------------------
	public void setSprite(BufferedImage sprite) {
		this.sprite = sprite;
	}

	public BufferedImage getSprite() {
		return sprite;
	}

	public boolean isCollision() {
		return collision;
	}
}
