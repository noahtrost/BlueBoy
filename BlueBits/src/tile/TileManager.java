package tile;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.UtilityTool;

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
		setup(0, "grass", false);
		setup(1, "wall", true);
		setup(2, "water", true);
		setup(3, "earth", false);
		setup(4, "tree", true);
		setup(5, "sand", false);
	}

	public void setup(int index, String imagePath, boolean collision) {
		
		UtilityTool uTool = new UtilityTool();
		
		try {
			tile[index] = new Tile();
			tile[index].image = tile[index].image = ImageIO.read(getClass().getResourceAsStream("/tiles/"+imagePath+".png"));
			tile[index].image = uTool.scaleImage(tile[index].image, gp.tileSize, gp.tileSize);
			tile[index].collision = collision;
		} catch (IOException e) {}
	}

	public void draw(Graphics2D g2) {

		int leftWorldCol = (gp.player.worldX - gp.player.screenX) / gp.tileSize - 1;
		int rightWorldCol = (gp.player.worldX + gp.player.screenX) / gp.tileSize + 1;
		int topWorldRow = (gp.player.worldY - gp.player.screenY) / gp.tileSize - 1;
		int bottomWorldRow = (gp.player.worldY + gp.player.screenY) / gp.tileSize + 1;

		if (leftWorldCol < 0)
			leftWorldCol = 0;
		if (topWorldRow < 0)
			topWorldRow = 0;
		if (rightWorldCol >= gp.maxWorldCol)
			rightWorldCol = gp.maxWorldCol - 1;
		if (bottomWorldRow >= gp.maxWorldRow)
			bottomWorldRow = gp.maxWorldRow - 1;

		int worldX;
		int worldY;
		int screenX;
		int screenY;

		for (int col = leftWorldCol; col <= rightWorldCol; col++) {
			for (int row = topWorldRow; row <= bottomWorldRow; row++) {

				worldX = col * gp.tileSize;
				worldY = row * gp.tileSize;

				screenX = worldX - gp.player.worldX + gp.player.screenX;
				screenY = worldY - gp.player.worldY + gp.player.screenY;

				BufferedImage image = tile[mapTileNum[col][row]].image;
				g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
			}

		}
	}
}
