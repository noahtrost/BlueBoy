package de.noah.core;

import java.awt.Color;
import java.awt.Graphics2D;

import de.noah.config.Config;
import de.noah.entity.Entity;
import de.noah.entity.Player;
import de.noah.object.SuperObject;
import de.noah.tile.Tile;

//CLASS FOR COLLISIONCHECKING
public class CollisionManager {


	// --------------------ATTIBUTES---------------------------------------
	private Player player;
	private Entity[] npcs;
	private SuperObject[] objects;
	private int[][] mapTileNum;
	private Tile[] tile;
	private ObjectManager objectManager;

//-----------------------CONSTRCTOR---------------------------------------- 
	public CollisionManager(Player player, Entity[] npcs, SuperObject[] objects, int[][] mapTileNum, Tile[] tile, ObjectManager objectManager) {
		this.player = player;
		this.npcs = npcs;
		this.objects = objects;
		this.mapTileNum = mapTileNum;
		this.tile = tile;
		this.objectManager = objectManager;
	}

//-----------------------REAL-METHODS---------------------------------------

	// CHECKING COLLISION OF ENTITYS WITHS TILES - RETURN TRUE WHEN COLLISION OCCURS
	public boolean checkTile(Entity entity) {

		int entityLeftWorldX = entity.getWorldX() + entity.getHitBox().x;
		int entityRightWorldX = entity.getWorldX() + entity.getHitBox().x + entity.getHitBox().width - 1;
		int entityTopWorldY = entity.getWorldY() + entity.getHitBox().y;
		int entityBottomWorldY = entity.getWorldY() + entity.getHitBox().y + entity.getHitBox().height - 1;

		int entityLeftCol = entityLeftWorldX / Config.TILE_SIZE;
		int entityRightCol = entityRightWorldX / Config.TILE_SIZE;
		int entityTopRow = entityTopWorldY / Config.TILE_SIZE;
		int entityBottomRow = entityBottomWorldY / Config.TILE_SIZE;

		int tileNum1, tileNum2;

		if (entity.getDirection().contains("up")) {
			entityTopRow = (entityTopWorldY - entity.getSpeed()) / Config.TILE_SIZE;
			tileNum1 = mapTileNum[entityLeftCol][entityTopRow];
			tileNum2 = mapTileNum[entityRightCol][entityTopRow];
			if (tile[tileNum1].isCollision() || tile[tileNum2].isCollision()) {
				entity.setCollisionOn(true);
			}
		}
		if (entity.getDirection().contains("down")) {

			entityBottomRow = (entityBottomWorldY + entity.getSpeed()) / Config.TILE_SIZE;
			tileNum1 = mapTileNum[entityLeftCol][entityBottomRow];
			tileNum2 = mapTileNum[entityRightCol][entityBottomRow];
			if (tile[tileNum1].isCollision() || tile[tileNum2].isCollision()) {
				entity.setCollisionOn(true);
			}
		}
		if (entity.getDirection().contains("left")) {
			entityLeftCol = (entityLeftWorldX - entity.getSpeed()) / Config.TILE_SIZE;
			tileNum1 = mapTileNum[entityLeftCol][entityTopRow];
			tileNum2 = mapTileNum[entityLeftCol][entityBottomRow];
			if (tile[tileNum1].isCollision() || tile[tileNum2].isCollision()) {
				entity.setCollisionOn(true);
			}
		}
		if (entity.getDirection().contains("right")) {
			entityRightCol = (entityRightWorldX + entity.getSpeed()) / Config.TILE_SIZE;
			tileNum1 = mapTileNum[entityRightCol][entityTopRow];
			tileNum2 = mapTileNum[entityRightCol][entityBottomRow];
			if (tile[tileNum1].isCollision() || tile[tileNum2].isCollision()) {
				entity.setCollisionOn(true);
			}
		}
		return entity.isCollisionOn();
	}

	// CHECKING COLLISION OF ENTITYS WITH OBJECTS - RETURNS INDEX OF OBJECTS WHICH
	// ENTITY COLLIDIDED WITH
	private int checkObject(Entity entity) {

		int index = -1;

		for (int i = 0; i < objects.length; i++) {

			if (objects[i] == null)
				continue;
			if (intersects(entity, objects[i])) {

				if (objects[i].isCollision())
					entity.setCollisionOn(true);
				index = i;
			}
		}
		return index;
	}

	// CHECKING COLLISION OF ENTITYS WITHS OTHER ENTITIES - RETURNS INDEX OF NPCS
	// WHICH INVOKING ENTITY COLLIDIDED WITH
	private int checkEntities(Entity entity) {

		int index = -1;

		for (int i = 0; i < npcs.length; i++) {

			if (npcs[i] == null || entity == npcs[i])
				continue;
			if (intersects(entity, npcs[i])) {
				entity.setCollisionOn(true);
				index = i;
			}
		}
		return index;
	}

	// CHECKING COLLISION OF ENTITY WITH PLAYER
	private boolean checkPlayer(Entity entity) {

		if (intersects(entity, player)) {
			entity.setCollisionOn(true);
			return true;
		}
		return false;
	}

	// CHECKING COLLLISION OF NPCS WITH THE PLAYERS INTERACTION HITBOX - RETURNS
	// INDEX OF NPC WHICH INVOKED COLLISION
	private int checkInteractibleEntities(Player player) {

		int index = -1;

		for (int i = 0; i < npcs.length; i++) {
			if (npcs[i] == null)
				continue;

			if (intersectsInteraction(player, npcs[i])) {
				if (npcs[i].isInteractibaleNPC()) {
					index = i;
				}
			}
		}
		return index;
	}

	// CHECKS IF HTIBOX OVERLAP VIA AABB CHECK - FOR OBJECTS
	private boolean intersects(Entity entity, SuperObject object) {
		// DETERMINE POS ENTITY
		int left = entity.getWorldX() + entity.getHitBox().x;
		int right = entity.getWorldX() + entity.getHitBox().x + entity.getHitBox().width;
		int top = entity.getWorldY() + entity.getHitBox().y;
		int bottom = entity.getWorldY() + entity.getHitBox().y + entity.getHitBox().height;

		// DETERMINES POS OBJECT
		int oLeft = object.getWorldX() + object.getHitBox().x;
		int oRight = object.getWorldX() + object.getHitBox().x + object.getHitBox().width;
		int oTop = object.getWorldY() + object.getHitBox().y;
		int oBottom = object.getWorldY() + object.getHitBox().y + object.getHitBox().height;

		// calculate players next position
		if (entity.getDirection().contains("up")) {
			top -= entity.getSpeed();
			bottom -= entity.getSpeed();
		}
		if (entity.getDirection().contains("down")) {
			bottom += entity.getSpeed();
			top += entity.getSpeed();
		}
		if (entity.getDirection().contains("left")) {
			left -= entity.getSpeed();
			right -= entity.getSpeed();
		}
		if (entity.getDirection().contains("right")) {
			right += entity.getSpeed();
			left += entity.getSpeed();
		}

		// check intersection (Hard Intersection)
		return (!(left > oRight || right < oLeft || top > oBottom || bottom < oTop));
	}

	// METHOD WHICH CHECK IF HTIBOX OVERLAP VIA AABB CHECK - FOR 2 ENTITYS
	private boolean intersects(Entity entity, Entity entity2) {
		// DETERMINE POS ENTITY
		int left = entity.getWorldX() + entity.getHitBox().x;
		int right = entity.getWorldX() + entity.getHitBox().x + entity.getHitBox().width;
		int top = entity.getWorldY() + entity.getHitBox().y;
		int bottom = entity.getWorldY() + entity.getHitBox().y + entity.getHitBox().height;

		// DETERMINE POS ENTITY2
		int left2 = entity2.getWorldX() + entity2.getHitBox().x;
		int right2 = entity2.getWorldX() + entity2.getHitBox().x + entity2.getHitBox().width;
		int top2 = entity2.getWorldY() + entity2.getHitBox().y;
		int bottom2 = entity2.getWorldY() + entity2.getHitBox().y + entity2.getHitBox().height;

		// CALCULATES ENTITYS NEXT POSITION
		if (entity.getDirection().contains("up")) {
			top -= entity.getSpeed();
			bottom -= entity.getSpeed();
		}
		if (entity.getDirection().contains("down")) {
			bottom += entity.getSpeed();
			top += entity.getSpeed();
		}
		if (entity.getDirection().contains("left")) {
			left -= entity.getSpeed();
			right -= entity.getSpeed();
		}
		if (entity.getDirection().contains("right")) {
			right += entity.getSpeed();
			left += entity.getSpeed();
		}

		// check intersection (Hard Intersection)
		return (!(left > right2 || right < left2 || top > bottom2 || bottom < top2));
	}

	// METHOD WHICH CHECK IF HTIBOX OVERLAP VIA AABB CHECK - FOR PLAYER AND ENTITY
	// (ONLY FOR INTERACTION)
	public boolean intersectsInteraction(Player player, Entity entity) {
		// DETERMINTE POS PLAYER
		int left = player.getWorldX() + player.getInteractionHitBox().x;
		int right = player.getWorldX() + player.getInteractionHitBox().x + player.getInteractionHitBox().width;
		int top = player.getWorldY() + player.getInteractionHitBox().y;
		int bottom = player.getWorldY() + player.getInteractionHitBox().y + player.getInteractionHitBox().height;

		// DETERMINE POS ENTITY
		int left2 = entity.getWorldX() + entity.getHitBox().x;
		int right2 = entity.getWorldX() + entity.getHitBox().x + entity.getHitBox().width;
		int top2 = entity.getWorldY() + entity.getHitBox().y;
		int bottom2 = entity.getWorldY() + entity.getHitBox().y + entity.getHitBox().height;

		// check intersection
		return (!(left > right2 || right < left2 || top > bottom2 || bottom < top2));
	}

	public void checkCollision(Player player) {
		checkTile(player);
		checkEntities(player);
		objectManager.handle(checkObject(player));
		player.setinteractiableNPC(checkInteractibleEntities(player));
	}

	public void checkCollision(Entity entity) {
		checkTile(entity);
		checkEntities(entity);
		checkObject(entity);
		entity.setPlayerCollision(checkPlayer(entity));
	}

	public void drawHitBox(Graphics2D g2, Entity entity) {
		g2.setColor(Color.red);
		g2.drawRect(entity.calculatingScreenHitBoxXPosition(player.getWorldX()),
				entity.calculatingScreenHitBoxYPosition(player.getWorldY()), entity.getHitBox().width,
				entity.getHitBox().height);
	}

	public void drawHitBox(Graphics2D g2, Player player) {
		g2.setColor(Color.red);
		g2.drawRect(Config.PLAYER_SCREEN_X + player.getHitBox().x, Config.PLAYER_SCREEN_Y + player.getHitBox().y,
				player.getHitBox().width, player.getHitBox().height);
	}
	
	public void drawInteractionHitBox(Graphics2D g2, Player player) {
		g2.setColor(Color.red);
		g2.drawRect(Config.PLAYER_SCREEN_X + player.getInteractionHitBox().x, Config.PLAYER_SCREEN_Y + player.getInteractionHitBox().y,
				player.getInteractionHitBox().width, player.getInteractionHitBox().height);
	}

}
