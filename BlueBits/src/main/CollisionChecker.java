package main;

import entity.Entity;
import object.SuperObject;

public class CollisionChecker {
	
	GamePanel gp;
	
	public CollisionChecker(GamePanel gp) {
		this.gp = gp;
	}
	
	public void checkTile(Entity entity) {
		
		int entityLeftWorldX = entity.worldX + entity.solidArea.x;
		int entityRightWorldX = entity.worldX + entity.solidArea.x + entity.solidArea.width;
		int entityTopWorldY = entity.worldY + entity.solidArea.y;
		int entityBottomWorldY = entity.worldY + entity.solidArea.y + entity.solidArea.height;
		
		int entityLeftCol = entityLeftWorldX / gp.tileSize;
		int entityRightCol = entityRightWorldX / gp.tileSize;
		int entityTopRow = entityTopWorldY / gp.tileSize;
		int entityBottomRow = entityBottomWorldY / gp.tileSize;
		
		int tileNum1, tileNum2;
		
		switch(entity.direction) {
		case "up":
			entityTopRow = (entityTopWorldY - entity.speed)/gp.tileSize; 
			tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
			tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
			if(gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision)
			{
				entity.collisionOn = true;
			}
			break;
		case "down":
			entityBottomRow = (entityBottomWorldY + entity.speed)/gp.tileSize; 
			tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
			tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
			if(gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision)
			{
				entity.collisionOn = true;
			}
			break;
		case "left":
			entityLeftCol = (entityLeftWorldX - entity.speed)/gp.tileSize; 
			tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
			tileNum2 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
			if(gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision)
			{
				entity.collisionOn = true;
			}
			break;
		case "right":
			entityRightCol = (entityRightWorldX + entity.speed)/gp.tileSize; 
			tileNum1 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
			tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
			if(gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision)
			{
				entity.collisionOn = true;
			}
			break;
		}
	}
	
	public int checkObject(Entity e, boolean player) {
	
		int index = -1;
		
		for(int i = 0; i < gp.obj.length; i++) {
			
			if(gp.obj[i] == null) continue;
			if(intersects(e,gp.obj[i])) {
				
				if(gp.obj[i].collision) e.collisionOn = true;
				index = i;
				
			}
		}
		return index;
	}

	private boolean intersects(Entity e, SuperObject o) {
		// calculate position player
		int left = e.worldX + e.solidArea.x;
		int right = e.worldX + e.solidArea.x + e.solidArea.width;
		int top = e.worldY + e.solidArea.y;
		int bottom = e.worldY + e.solidArea.y + e.solidArea.height;

		// calculate objects position
		int oLeft = o.worldX + o.solidArea.x;
		int oRight = o.worldX + o.solidArea.x + o.solidArea.width;
		int oTop = o.worldY + o.solidArea.y;
		int oBottom = o.worldY + o.solidArea.y + o.solidArea.height;

		// calculate players next position
		switch (e.direction) {
		case "up":
			top -= e.speed;
			break;
		case "down":
			bottom += e.speed;
			break;
		case "left":
			left -= e.speed;
			break;
		case "right":
			right += e.speed;
			break;
		}
		
		//check intersection (Hard Intersection)
		return (!(left > oRight || right < oLeft || top > oBottom || bottom < oTop));
	}
}
