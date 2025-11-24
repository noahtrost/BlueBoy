package tile;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import main.GamePanel;

public class TileManager {
	GamePanel gp;
	public Tile[] tile;
	public int mapTileNum[][];

	public TileManager(GamePanel gp) {

		this.gp = gp;

		tile = new Tile[10];
		mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];
		getTileImage();
		loadMap("/maps/world01.txt");

	}

	public void loadMap(String filePath) {
		try {
			InputStream is = getClass().getResourceAsStream(filePath);
			BufferedReader br = new BufferedReader(new InputStreamReader(is));

			int col = 0;
			int row = 0;

			while (row < gp.maxWorldRow) {
				String line = br.readLine();
				String numbers[] = line.split(" ");
				while (col < gp.maxWorldCol) {
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

	public void getTileImage() {
		try {

			tile[0] = new Tile();
			tile[0].image = ImageIO.read(getClass().getResourceAsStream("/tiles/grass.png"));

			tile[1] = new Tile();
			tile[1].image = ImageIO.read(getClass().getResourceAsStream("/tiles/wall.png"));
			tile[1].collision = true;

			tile[2] = new Tile();
			tile[2].image = ImageIO.read(getClass().getResourceAsStream("/tiles/water.png"));
			tile[2].collision = true;
			
			tile[3] = new Tile();
			tile[3].image = ImageIO.read(getClass().getResourceAsStream("/tiles/earth.png"));

			tile[4] = new Tile();
			tile[4].image = ImageIO.read(getClass().getResourceAsStream("/tiles/tree.png"));
			tile[4].collision = true;
			
			tile[5] = new Tile();
			tile[5].image = ImageIO.read(getClass().getResourceAsStream("/tiles/sand.png"));

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void draw(Graphics2D g2) {
		int worldCol = gp.player.worldX / gp.tileSize - gp.maxScreenCol / 2;
		int worldRow = gp.player.worldY / gp.tileSize - gp.maxScreenRow / 2 ;
		
		if (worldCol < 0)
			worldCol = 0;
		if (worldRow < 0) 
			worldRow = 0;

		int worldColLim = worldCol + gp.maxScreenCol + 1;
		if (worldColLim > gp.maxWorldCol)
			worldColLim = gp.maxWorldCol;

		int worldRowLim = worldRow + gp.maxScreenRow + 1;
		if (worldRowLim > gp.maxWorldRow)
			worldRowLim = gp.maxWorldRow;

		int startX = worldCol;
		while (worldRow <= worldRowLim) {

			int tileNum = mapTileNum[worldCol][worldRow];

			int worldX = worldCol * gp.tileSize;
			int worldY = worldRow * gp.tileSize;
			int screenX = worldX - gp.player.worldX + gp.player.screenX;
			int screenY = worldY - gp.player.worldY + gp.player.screenY;

			g2.drawImage(tile[tileNum].image, screenX, screenY, gp.tileSize, gp.tileSize, null);

			worldCol++;

			if (!(worldCol <= worldColLim)) {
				worldCol = startX;
				worldRow++;
			}
		}
	}

}
