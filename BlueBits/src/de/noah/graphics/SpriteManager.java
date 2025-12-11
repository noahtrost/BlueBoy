package de.noah.graphics;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

import de.noah.config.Config;

public class SpriteManager {

//----------------ATTRIBTUES-----------------------------------
	private BufferedImage[] playerSprites;
	private BufferedImage[] oldManSprites;
	private BufferedImage[] tileSprites;
	@SuppressWarnings("unused")
	private BufferedImage[] objectSprites;

	private BufferedImage[] uiSprites;
	
	private BufferedImage [][] allNPCsprites;

//----------------CONSTRUCTOR---------------------------------
	public SpriteManager() {}

//----------------REAL-METHODS-------------------------------

	//CREATES,LOADES AND SCALES ALL SPRITES
	public void setUp() {
		
		//CREATING SPRITE ARRAYS
		playerSprites = new BufferedImage[8];
		oldManSprites = new BufferedImage[9];
		tileSprites = new BufferedImage[42];
		objectSprites = new BufferedImage[5];
		allNPCsprites = new BufferedImage[1][8];
		uiSprites = new BufferedImage[3];
		
		// LOAD SPRITES INTO ARRAY
		loadPlayerSprites();
		loadOldManSprites();
		loadTileSprites();
		loadObjectSprite();
		loadUISprite(); 
		
		allNPCsprites[0] = oldManSprites;

		// SCALING SPRITES
		scalingSprites(playerSprites, Config.TILE_SIZE, Config.TILE_SIZE);
		scalingSprites(oldManSprites, Config.TILE_SIZE, Config.TILE_SIZE);
		scalingSprites(tileSprites, Config.TILE_SIZE, Config.TILE_SIZE);
		scalingSprites(objectSprites, Config.TILE_SIZE, Config.TILE_SIZE);
		
	}

	//LOAD PLAYER SPRITES
	private void loadPlayerSprites() {
		try {
			// PLAYER UP SPRITES
			playerSprites[0] = ImageIO.read(getClass().getResource("/player/boy_up_1.png"));
			playerSprites[1] = ImageIO.read(getClass().getResource("/player/boy_up_2.png"));

			// PLAYER DOWN SPRITES
			playerSprites[2] = ImageIO.read(getClass().getResource("/player/boy_down_1.png"));
			playerSprites[3] = ImageIO.read(getClass().getResource("/player/boy_down_2.png"));

			// PLAYER LEFT SPRITES
			playerSprites[4] = ImageIO.read(getClass().getResource("/player/boy_left_1.png"));
			playerSprites[5] = ImageIO.read(getClass().getResource("/player/boy_left_2.png"));

			// PLAYER RIGHT SPRITES
			playerSprites[6] = ImageIO.read(getClass().getResource("/player/boy_right_1.png"));
			playerSprites[7] = ImageIO.read(getClass().getResource("/player/boy_right_2.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	//LOAD OLDMAN SPRITES
	private void loadOldManSprites() {
		try {
			// OLD_MAN UP SPRITES
			oldManSprites[0] = ImageIO.read(getClass().getResource("/npcs/oldman_up_1.png"));
			oldManSprites[1] = ImageIO.read(getClass().getResource("/npcs/oldman_up_2.png"));

			// OLD_MAN DOWN SPRITES
			oldManSprites[2] = ImageIO.read(getClass().getResource("/npcs/oldman_down_1.png"));
			oldManSprites[3] = ImageIO.read(getClass().getResource("/npcs/oldman_down_2.png"));

			// OLD_MAN LEFT SPRITES
			oldManSprites[4] = ImageIO.read(getClass().getResource("/npcs/oldman_left_1.png"));
			oldManSprites[5] = ImageIO.read(getClass().getResource("/npcs/oldman_left_2.png"));

			// OLD_MAN RIGHT SPRITES
			oldManSprites[6] = ImageIO.read(getClass().getResource("/npcs/oldman_right_1.png"));
			oldManSprites[7] = ImageIO.read(getClass().getResource("/npcs/oldman_right_2.png"));
			
			// OLD_MAN TALk SPRITES
			oldManSprites[8] = ImageIO.read(getClass().getResource("/npcs/oldman_talk.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	//LOAD TILE SPRITES
	private void loadTileSprites() {
		try {
			// PLACEHOLDERS - NEVER ACTUALLY USED -
			tileSprites[0] = ImageIO.read(getClass().getResource("/tiles/grass00.png"));
			tileSprites[1] = ImageIO.read(getClass().getResource("/tiles/grass00.png"));
			tileSprites[2] = ImageIO.read(getClass().getResource("/tiles/grass00.png"));
			tileSprites[3] = ImageIO.read(getClass().getResource("/tiles/grass00.png"));
			tileSprites[4] = ImageIO.read(getClass().getResource("/tiles/grass00.png"));
			tileSprites[5] = ImageIO.read(getClass().getResource("/tiles/grass00.png"));
			tileSprites[6] = ImageIO.read(getClass().getResource("/tiles/grass00.png"));
			tileSprites[7] = ImageIO.read(getClass().getResource("/tiles/grass00.png"));
			tileSprites[8] = ImageIO.read(getClass().getResource("/tiles/grass00.png"));
			tileSprites[9] = ImageIO.read(getClass().getResource("/tiles/grass00.png"));

			// GRASS-TILE SPRITES
			tileSprites[10] = ImageIO.read(getClass().getResource("/tiles/grass00.png"));
			tileSprites[11] = ImageIO.read(getClass().getResource("/tiles/grass01.png"));

			// WATER-TILE SPRITES
			tileSprites[12] = ImageIO.read(getClass().getResource("/tiles/water00.png"));
			tileSprites[13] = ImageIO.read(getClass().getResource("/tiles/water01.png"));
			tileSprites[14] = ImageIO.read(getClass().getResource("/tiles/water02.png"));
			tileSprites[15] = ImageIO.read(getClass().getResource("/tiles/water03.png"));
			tileSprites[16] = ImageIO.read(getClass().getResource("/tiles/water04.png"));
			tileSprites[17] = ImageIO.read(getClass().getResource("/tiles/water05.png"));
			tileSprites[18] = ImageIO.read(getClass().getResource("/tiles/water06.png"));
			tileSprites[19] = ImageIO.read(getClass().getResource("/tiles/water07.png"));
			tileSprites[20] = ImageIO.read(getClass().getResource("/tiles/water08.png"));
			tileSprites[21] = ImageIO.read(getClass().getResource("/tiles/water09.png"));
			tileSprites[22] = ImageIO.read(getClass().getResource("/tiles/water10.png"));
			tileSprites[23] = ImageIO.read(getClass().getResource("/tiles/water11.png"));
			tileSprites[24] = ImageIO.read(getClass().getResource("/tiles/water12.png"));
			tileSprites[25] = ImageIO.read(getClass().getResource("/tiles/water13.png"));

			// ROAD-TILE SPRITES
			tileSprites[26] = ImageIO.read(getClass().getResource("/tiles/road00.png"));
			tileSprites[27] = ImageIO.read(getClass().getResource("/tiles/road01.png"));
			tileSprites[28] = ImageIO.read(getClass().getResource("/tiles/road02.png"));
			tileSprites[29] = ImageIO.read(getClass().getResource("/tiles/road03.png"));
			tileSprites[30] = ImageIO.read(getClass().getResource("/tiles/road04.png"));
			tileSprites[31] = ImageIO.read(getClass().getResource("/tiles/road05.png"));
			tileSprites[32] = ImageIO.read(getClass().getResource("/tiles/road06.png"));
			tileSprites[33] = ImageIO.read(getClass().getResource("/tiles/road07.png"));
			tileSprites[34] = ImageIO.read(getClass().getResource("/tiles/road08.png"));
			tileSprites[35] = ImageIO.read(getClass().getResource("/tiles/road09.png"));
			tileSprites[36] = ImageIO.read(getClass().getResource("/tiles/road10.png"));
			tileSprites[37] = ImageIO.read(getClass().getResource("/tiles/road11.png"));
			tileSprites[38] = ImageIO.read(getClass().getResource("/tiles/road12.png"));

			// EARTH-TILE SPRITES
			tileSprites[39] = ImageIO.read(getClass().getResource("/tiles/earth.png"));

			// WALL-TILE SPRITES
			tileSprites[40] = ImageIO.read(getClass().getResource("/tiles/wall.png"));

			// TREE-TILE SPRITES
			tileSprites[41] = ImageIO.read(getClass().getResource("/tiles/tree.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	//LOAD OBJECT SPRITES
	private void loadObjectSprite() {
		try {
			// OBJECT-SPRITES
			objectSprites[0] = ImageIO.read(getClass().getResource("/objects/boots.png"));
			objectSprites[1] = ImageIO.read(getClass().getResource("/objects/chest.png"));
			objectSprites[2] = ImageIO.read(getClass().getResource("/objects/door.png"));
			objectSprites[3] = ImageIO.read(getClass().getResource("/objects/key.png"));
			objectSprites[4] = ImageIO.read(getClass().getResource("/objects/talk.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//LOAD UI SPRITES
	private void loadUISprite() {
			try {
				// OBJECT-SPRITES
				uiSprites[0] = ImageIO.read(getClass().getResource("/objects/talk.png"));
				uiSprites[1] = ImageIO.read(getClass().getResource("/ui/key.png"));
				uiSprites[2] = ImageIO.read(getClass().getResource("/ui/sword.png"));
				
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
	
	//SCALES EVERY IMAGE IN GIVEN SPRITE ARRAY
	private void scalingSprites(BufferedImage sprites[], int toScaleWidth, int toScaleHeight) {
		for (int i = 0; i < sprites.length; i++) {
			scaleSprite(sprites[i], toScaleWidth, toScaleHeight);
		}
	}

	//SCALES SPRITE
	private BufferedImage scaleSprite(BufferedImage original, int toScaleWidth, int toScaleHeight) {

		BufferedImage scaledImage = new BufferedImage(toScaleWidth, toScaleHeight, original.getType());
		Graphics2D g2 = scaledImage.createGraphics();
		g2.drawImage(original, 0, 0, toScaleWidth, toScaleHeight, null);

		return scaledImage;
	}

	//----------------GETTER/SETTERS-------------------------------
	public BufferedImage[] getPlayerSprites() {
		return playerSprites;
	}

	public BufferedImage[] getOldManSprites() {
		return oldManSprites;
	}

	public BufferedImage[] getTileSprites() {
		return tileSprites;
	}

	public BufferedImage[] getObjectSprites() {
		return objectSprites;
	}

	public BufferedImage[] getUiSprites() {
		return uiSprites;
	}

	public BufferedImage [][] getAllNPCsprites() {
		return allNPCsprites;
	}
	
}
