package de.noah.ui;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;

import de.noah.config.Config;

public class PlayStateUI extends StateUI {

	// --------------------CONSTRUCTOR---------------------------------------

	public PlayStateUI(BufferedImage[] sprites) {
		super(sprites);
	}

	// --------------------DRAWING-------------------------------------------

	public void draw(Graphics2D g2, int interactiableNPC) {
		super.draw(g2);
		if (interactiableNPC != -1) {
			drawPossibleInteraction(g2, interactiableNPC);
		}
	}

	private void drawPossibleInteraction(Graphics2D g2, int interactiableNPC) {
		switch (interactiableNPC) {
		case 0: {
			messageToDisplay = "PRESS SPACE TO INTERACT";
			Point messagePoint = getCenteredMessagePosition(g2, messageToDisplay, Config.TILE_SIZE / 2);
			g2.drawString(messageToDisplay, messagePoint.x - 16, messagePoint.y);

			g2.drawImage(sprites[0], Config.TILE_SIZE * 11 + 4, Config.SCREEN_HEIGHT - Config.TILE_SIZE + 1, 32, 32,
					null);

			break;
		}
		}
	}
}
