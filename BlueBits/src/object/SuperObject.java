package object;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import main.GamePanel;

public class SuperObject {

	public BufferedImage image;
	public String name;
	public boolean collision = false;
	public int worldX, worldY;
	public Rectangle solidArea = new Rectangle(0,0,48,48);
	public int solidDefaultX = 0;
	public int solidDefaultY = 0;
	
	
	public void draw(Graphics2D g2, GamePanel gp) {

			 int worldXdiff = worldX - gp.player.worldX;
			 int screenX 	= gp.player.screenX + worldXdiff;
			 
			 int worldYdiff = worldY - gp.player.worldY;
			 int screenY = 	  gp.player.screenY + worldYdiff;
			 
			 if(worldX + gp.tileSize > gp.player.worldX - gp.player.screenX 
					 && worldX  - gp.tileSize < gp.player.worldX + gp.player.screenX
					 && worldY + gp.tileSize > gp.player.worldY - gp.player.screenY
					 && worldY - gp.tileSize < gp.player.worldY + gp.player.screenY)
			 {
				 g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
			 }
			
	}
}
