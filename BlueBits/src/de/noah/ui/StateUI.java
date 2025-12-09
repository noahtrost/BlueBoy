package de.noah.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import de.noah.config.Config;

public abstract class StateUI {

	// --------------------ATTIBUTES---------------------------------------

	protected boolean space, enter;
	

	protected BufferedImage[] sprites;
	protected Font baseFont;
	protected String messageToDisplay = " ";
	protected int speechCounter = 0;

	// --------------------CONSTRUCTOR---------------------------------------

	public StateUI(BufferedImage[] uiSprites) {
		sprites = uiSprites;
		try {
			InputStream is = getClass().getResourceAsStream("/fonts/pressStart2P.ttf");
			if (is == null) {
				System.out.println("Font is null");
			}
			Font font = Font.createFont(Font.TRUETYPE_FONT, is);
			baseFont = font.deriveFont(14f);
		} catch (FontFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// --------------------DRAWING---------------------------------------

	protected Point getCenteredMessagePosition(Graphics2D g2, String message, int yOffsetFromBottom) {
		FontMetrics fm = g2.getFontMetrics();
		int textWidth = fm.stringWidth(message);

		int x = (Config.SCREEN_WIDTH - textWidth) / 2;
		int y = (Config.SCREEN_HEIGHT - yOffsetFromBottom);
		return new Point(x, y);
	}

	public void draw(Graphics2D g2) {
		// Anti-Aliasing für geometrische Formen
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		// Anti-Aliasing für Text
		g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

		// SET FONT AND COLOR
		g2.setFont(baseFont);
		g2.setColor(Color.white);
	}
	

	public void setSpace(boolean space) {
		this.space = space;
	}

	public void setEnter(boolean enter) {
		this.enter = enter;
	}

}
