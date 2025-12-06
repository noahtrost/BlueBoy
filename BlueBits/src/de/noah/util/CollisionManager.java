package de.noah.util;

import de.noah.entity.Entity;
import de.noah.entity.Player;
import de.noah.main.Config;
import de.noah.object.SuperObject;
import de.noah.tile.Tile;


//CLASS FOR COLLISIONCHECKING
public class CollisionManager {

//-----------------------CONSTRCTOR---------------------------------------- 
	public CollisionManager() {}

	
	
//-----------------------REAL-METHODS---------------------------------------

	//CHECKING COLLISION OF ENTITYS WITHS TILES - RETURN TRUE WHEN COLLISION OCCURS
	public boolean checkTile(Entity entity, int [] [] mapTileNum, Tile [] tile) {

		int entityLeftWorldX = entity.getWorldX() + entity.getHitBox().x;
		int entityRightWorldX = entity.getWorldX() + entity.getHitBox().x + entity.getHitBox().width;
		int entityTopWorldY = entity.getWorldY() + entity.getHitBox().y;
		int entityBottomWorldY = entity.getWorldY() + entity.getHitBox().y + entity.getHitBox().height;

		int entityLeftCol = entityLeftWorldX / Config.TILE_SIZE;
		int entityRightCol = entityRightWorldX / Config.TILE_SIZE;
		int entityTopRow = entityTopWorldY / Config.TILE_SIZE;
		int entityBottomRow = entityBottomWorldY / Config.TILE_SIZE;

		int tileNum1, tileNum2;

		switch (entity.getDirection()) {
		case "up":
			entityTopRow = (entityTopWorldY - entity.getSpeed()) / Config.TILE_SIZE;
			tileNum1 = mapTileNum[entityLeftCol][entityTopRow];
			tileNum2 = mapTileNum[entityRightCol][entityTopRow];
			if (tile[tileNum1].isCollision() || tile[tileNum2].isCollision()) {
				entity.setCollisionOn(true);
			}
			break;
		case "down":
			entityBottomRow = (entityBottomWorldY + entity.getSpeed()) / Config.TILE_SIZE;
			tileNum1 = mapTileNum[entityLeftCol][entityBottomRow];
			tileNum2 = mapTileNum[entityRightCol][entityBottomRow];
			if (tile[tileNum1].isCollision() || tile[tileNum2].isCollision()) {
				entity.setCollisionOn(true);
			}
			break;
		case "left":
			entityLeftCol = (entityLeftWorldX - entity.getSpeed()) /  Config.TILE_SIZE;
			tileNum1 = mapTileNum[entityLeftCol][entityTopRow];
			tileNum2 = mapTileNum[entityLeftCol][entityBottomRow];
			if (tile[tileNum1].isCollision() ||tile[tileNum2].isCollision()) {
				entity.setCollisionOn(true);
			}
			break;
		case "right":
			entityRightCol = (entityRightWorldX + entity.getSpeed()) /  Config.TILE_SIZE;
			tileNum1 = mapTileNum[entityRightCol][entityTopRow];
			tileNum2 = mapTileNum[entityRightCol][entityBottomRow];
			if (tile[tileNum1].isCollision() || tile[tileNum2].isCollision()) {
				entity.setCollisionOn(true);
			}
			break;
		}
		return entity.isCollisionOn();
	}

	//CHECKING COLLISION OF ENTITYS WITH OBJECTS - RETURNS INDEX OF OBJECTS WHICH ENTITY COLLIDIDED WITH
	public int checkObject(Entity entity, SuperObject[] obj) {

		int index = -1;

		for (int i = 0; i < obj.length; i++) {

			if (obj[i] == null)
				continue;
			if (intersects(entity, obj[i])) {

				if (obj[i].isCollision())
					entity.setCollisionOn(true);
				index = i;
			}
		}
		return index;
	}

	//CHECKING COLLISION OF ENTITYS WITHS OTHER ENTITIES - RETURNS INDEX OF NPCS WHICH INVOKING ENTITY COLLIDIDED WITH
	public int checkEntities(Entity entity , Entity [] npc) {
		
		int index = -1;

		for (int i = 0; i < npc.length; i++) {

			if (npc[i] == null || entity == npc[i] ) continue;
			if (intersects(entity, npc[i])) {
				entity.setCollisionOn(true);
					index = i;
			}
		}
		return index;
	}
	
	//CHECKING COLLLISION OF NPCS WITH THE PLAYERS INTERACTION HITBOX - RETURNS INDEX OF NPC WHICH INVOKED COLLISION
	public int checkInteractibleEntities(Player player,  Entity [] npc) {
		
		int index = -1;

		for (int i = 0; i < npc.length; i++) {
			if (npc[i] == null) continue;
			
			if (intersects(player, npc[i])) {
				if (npc[i].isInteractibaleNPC()) {
					index =  i;
				}
			}
		}
		return index;
	}

	//CHECKS IF HTIBOX OVERLAP VIA AABB CHECK - FOR OBJECTS
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
		switch (entity.getDirection()) {
		case "up":
			top -= entity.getSpeed();
			break;
		case "down":
			bottom += entity.getSpeed();
			break;
		case "left":
			left -= entity.getSpeed();
			break;
		case "right":
			right += entity.getSpeed();
			break;
		}

		// check intersection (Hard Intersection)
		return (!(left > oRight || right < oLeft || top > oBottom || bottom < oTop));
	}
	
	//METHOD WHICH CHECK IF HTIBOX OVERLAP VIA AABB CHECK - FOR 2 ENTITYS
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

		// calculate players next position
		switch (entity.getDirection()) {
		case "up":
			top -= entity.getSpeed();
			break;
		case "down":
			bottom += entity.getSpeed();
			break;
		case "left":
			left -= entity.getSpeed();
			break;
		case "right":
			right += entity.getSpeed();
			break;
		}

		// check intersection (Hard Intersection)
		return (!(left > right2 || right < left2 || top > bottom2 || bottom < top2));
	}
	
	//METHOD WHICH CHECK IF HTIBOX OVERLAP VIA AABB CHECK - FOR PLAYER AND ENTITY (ONLY FOR INTERACTION)
	public boolean intersects(Player player, Entity entity) {
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
		return (!(left > right2 || right < left2 || top > bottom2|| bottom < top2));
	}

}
