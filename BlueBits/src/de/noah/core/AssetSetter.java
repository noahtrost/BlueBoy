package de.noah.core;

import java.awt.image.BufferedImage;

import de.noah.entity.Entity;
import de.noah.entity.NPC_OldMan;
import de.noah.object.OBJ_Chest;
import de.noah.object.OBJ_Door;
import de.noah.object.OBJ_Key;
import de.noah.object.SuperObject;

//CLASS DESIGNED TO PLACE NPC AND OBJECTS INTO THE WORLD
public class AssetSetter {

	//--------------------CONSTRCTOR---------------------------------------- 
	public AssetSetter() {
}
	
	
	// --------------------REAL-METHODS---------------------------------------

	//PLACE OBJECTS HERE
	public void setObjects(SuperObject [] object,  BufferedImage[] sprites) {
		object[0] = new OBJ_Door(14, 28, sprites[2]);
		object[1] = new OBJ_Door(8, 19, sprites[2]);
		object[2] = new OBJ_Door(10, 12, sprites[2]);
		
		object[3] = new OBJ_Key(23, 7, sprites[3]);
		object[4] = new OBJ_Key(30, 40, sprites[3]);
		object[5] = new OBJ_Key(38, 8, sprites[3]);
		
		object[6] = new OBJ_Chest(10, 8, sprites[1]);
	}
	
	
	//PLACE NPCS HERE
	public void setNPCS(Entity [] npcs, BufferedImage[][] sprites) {
				npcs[0] = new NPC_OldMan(10, 28, 1, sprites[0], true);
	}
}
