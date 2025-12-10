package de.noah.ui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;

import de.noah.config.Config;

public class PlayStateUI extends StateUI {

	int keyFrameCounter = 0;
	BufferedImage keyFrame;

	// --------------------CONSTRUCTOR---------------------------------------

	public PlayStateUI(BufferedImage[] sprites) {
		super(sprites);
	}

	// --------------------DRAWING-------------------------------------------

	public void draw(Graphics2D g2, int interactiableNPC, int keyCount) {
		super.draw(g2);

		if (keyFrameCounter % 4 == 0) {
			keyFrame = sprites[1].getSubimage(keyFrameCounter / 4 * 32, 0, 32, 32);
		}
		g2.drawImage(keyFrame, Config.TILE_SIZE / 2, Config.TILE_SIZE, 48, 48, null);
		keyFrameCounter++;
		if (keyFrameCounter == 92) {
			keyFrameCounter = 0;
		}

		String msg = "x" + keyCount;
		int x = Config.TILE_SIZE + 33;
		int y = Config.TILE_SIZE + 33;

		g2.setFont(baseFont.deriveFont(22f));

		// Schwarzer Rand (4 Richtungen)
		g2.setColor(Color.BLACK);
		g2.drawString(msg, x + 1, y);
		g2.drawString(msg, x - 1, y);
		g2.drawString(msg, x, y + 1);
		g2.drawString(msg, x, y - 1);

		// Weißer Text
		g2.setColor(Color.WHITE);
		g2.drawString(msg, x, y);

		if (interactiableNPC != -1) {
			drawPossibleInteraction(g2, interactiableNPC);
		}
	}

	private void drawPossibleInteraction(Graphics2D g2, int interactiableNPC) {
		g2.setFont(baseFont);
		switch (interactiableNPC) {
		case 0: {
			messageToDisplay = "PRESS SPACE TO INTERACT";
			
			Point messagePoint = getCenteredMessagePosition(g2, messageToDisplay, Config.TILE_SIZE / 2);
			int x = messagePoint.x - 16;
			int y = messagePoint.y;
			
			// Schwarzer Rand (4 Richtungen)
			g2.setColor(Color.BLACK);
			g2.drawString(messageToDisplay, x + 1, y);
			g2.drawString(messageToDisplay, x - 1, y);
			g2.drawString(messageToDisplay, x, y + 1);
			g2.drawString(messageToDisplay, x, y - 1);
			
			// Weißer Text
			g2.setColor(Color.WHITE);
			g2.drawString(messageToDisplay, x, y);
			g2.drawString(messageToDisplay, x, y);

			g2.drawImage(sprites[0], Config.TILE_SIZE * 11 + 4, Config.SCREEN_HEIGHT - Config.TILE_SIZE + 1, 32, 32,
					null);

			break;
		}
		}
	}
}
