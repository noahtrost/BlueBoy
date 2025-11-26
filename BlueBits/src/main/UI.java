package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class UI {
	GamePanel gp;
	BufferedImage keyImage;
	Font arial_40 = new Font("arial", Font.PLAIN, 40);
	Font arial_80 = new Font("arial", Font.BOLD, 80);
	public String message = " ";
	public boolean messageOn = false;
	int messageCounter = 0;
	long startingTime = System.nanoTime();

	public UI(GamePanel gp) {
		this.gp = gp;
		try {
			keyImage = ImageIO.read(getClass().getResourceAsStream("/objects/key.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void draw(Graphics2D g2, long startDraw) {

		// WON GAME - DRAW CONGRATS
		if (gp.gameThread == null) {
			g2.setFont(arial_80);
			g2.setColor(Color.yellow);
			String text = "Congratulations!";
			int textWidth = g2.getFontMetrics().stringWidth(text);
			int textHeight = g2.getFontMetrics().getAscent();
			int x = (gp.screenWidth - textWidth) / 2;
			int y = (gp.screenHeight /2) + textHeight;
			g2.drawString(text, x, y);
			
			g2.setFont(arial_40);
			g2.setColor(Color.white);
			text = "You found the Treasure!";
			x = g2.getFontMetrics().stringWidth(text);
			x = (gp.screenWidth - x) / 2;
			g2.drawString(text, x, y + gp.tileSize*2);
			
		} else {

			// DRAW KEYPICTURE
			g2.drawImage(keyImage, gp.tileSize, gp.tileSize / 6, gp.tileSize, gp.tileSize, null);

			// DRAW KEYTEXT
			g2.setFont(arial_40);
			g2.setColor(Color.white);
			String text = "x " + gp.player.hasKey;
			g2.drawString(text, 2 * gp.tileSize, gp.tileSize);

			// DRAW MESSAGE
			if (messageOn) {
				g2.drawString(message, gp.tileSize, 6 * gp.tileSize);
				messageCounter++;
				if (messageCounter > 120) {
					messageOn = false;
					messageCounter = 0;
				}
			}
		}

		// DRAW TIMER
		g2.setFont(arial_40);
		g2.setColor(Color.white);

		long currentTime = System.nanoTime() - startingTime;
		double seconds = currentTime / 1_000_000_000.0;
		String timeText = String.format("%.2f", seconds);
		g2.drawString(timeText, 12 * gp.tileSize, gp.tileSize);

		// DEBUG - DRAW DRAWTIME
		if (gp.keyH.debug) {
			long stopDraw = System.nanoTime();
			long time = stopDraw - startDraw;
			String timeString = String.valueOf(time);
			g2.drawString(timeString, 12 * gp.tileSize, 11 * gp.tileSize);
			System.out.println("DRAWTIME: " + timeString);
		}
	}
}
