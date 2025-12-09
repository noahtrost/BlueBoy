package de.noah.core;

import java.awt.image.BufferedImage;

import de.noah.config.Config;
import de.noah.entity.Entity;
import de.noah.entity.NPC_OldMan;
import de.noah.object.SuperObject;

//CLASS DESIGNED TO PLACE NPC AND OBJECTS INTO THE WORLD
public class AssetSetter {

	//--------------------CONSTRCTOR---------------------------------------- 
	public AssetSetter() {
}
	
	
	// --------------------REAL-METHODS---------------------------------------

	//PLACE OBJECTS HERE
	public void setObjects(SuperObject [] object,  BufferedImage[] sprites) {
		
	}
	
	//PLACE NPCS HERE
	public void setNPCS(Entity [] npcs, BufferedImage[][] sprites) {
				npcs[0] = new NPC_OldMan(Config.TILE_SIZE*19, Config.TILE_SIZE*21, 1, sprites[0], true);
	}
}
