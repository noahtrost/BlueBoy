package de.noah.tile;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import de.noah.main.Config;

public class TileManager {

	private Tile[] tile;
	private int mapTileNum[][];

	public TileManager() {
		tile = new Tile[42];
		mapTileNum = new int[Config.WORLD_COLUMNS][Config.WORLD_ROWS];
	}

	public void setUp(BufferedImage[] sprites) {

		setUpTiles();
		loadTileSprites(sprites);

		// LOADS ARRAY WHICH REPRESENTS LOCATION OF SPECIFIC TILE IN
		// WORLDSMAP

		loadMap("/maps/worldV2.txt");
	}

	private void setUpTiles() {
		for (int i = 0; i < tile.length; i++) {
			if ((i > 11 && i < 26) || i > 40) {
				tile[i] = new Tile(true);
				continue;
			}
			tile[i] = new Tile(false);
		}
	}

	private void loadTileSprites(BufferedImage[] sprites) {
		for (int i = 0; i < tile.length; i++) {
			tile[i].setSprite(sprites[i]);
		}
	}

	// LOADS TILE SPRITE NUMBERS IN MAP ARRAY
	public void loadMap(String filePath) {
		try {
			InputStream is = getClass().getResourceAsStream(filePath);
			BufferedReader br = new BufferedReader(new InputStreamReader(is));

			int col = 0;
			int row = 0;

			while (row < Config.WORLD_ROWS) {
				String line = br.readLine();
				String numbers[] = line.split(" ");
				while (col < Config.WORLD_COLUMNS) {
					int num = Integer.parseInt(numbers[col]);
					mapTileNum[col][row] = num;
					col++;
				}
				col = 0;
				row++;
			}
			br.close();
		} catch (Exception e) {
		}
	}

	public void draw(Graphics2D g2, int playerWorldX, int playerWorldY) {

		int leftWorldCol = (playerWorldX - Config.PLAYER_SCREEN_X) / Config.TILE_SIZE - 1;
		int rightWorldCol = (playerWorldX + Config.PLAYER_SCREEN_X) / Config.TILE_SIZE + 1;
		int topWorldRow = (playerWorldY - Config.PLAYER_SCREEN_Y) / Config.TILE_SIZE - 1;
		int bottomWorldRow = (playerWorldY + Config.PLAYER_SCREEN_Y) / Config.TILE_SIZE + 1;

		if (leftWorldCol < 0)
			leftWorldCol = 0;
		if (topWorldRow < 0)
			topWorldRow = 0;
		if (rightWorldCol >= Config.WORLD_COLUMNS)
			rightWorldCol = Config.WORLD_COLUMNS - 1;
		if (bottomWorldRow >= Config.WORLD_ROWS)
			bottomWorldRow = Config.WORLD_COLUMNS - 1;

		int worldX;
		int worldY;
		int screenX;
		int screenY;

		for (int col = leftWorldCol; col <= rightWorldCol; col++) {
			for (int row = topWorldRow; row <= bottomWorldRow; row++) {

				worldX = col * Config.TILE_SIZE;
				worldY = row * Config.TILE_SIZE;

				screenX = worldX - playerWorldX + Config.PLAYER_SCREEN_X;
				screenY = worldY - playerWorldY + Config.PLAYER_SCREEN_Y;

				BufferedImage image = tile[mapTileNum[col][row]].getSprite();
				g2.drawImage(image, screenX, screenY, Config.TILE_SIZE, Config.TILE_SIZE, null);
			}

		}
	}

	public Tile[] getTile() {
		return tile;
	}

	public int[][] getMapTileNum() {
		return mapTileNum;
	}
}
