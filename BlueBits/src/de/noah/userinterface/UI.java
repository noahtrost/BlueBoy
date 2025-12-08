package de.noah.userinterface;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import de.noah.main.Config;
import de.noah.main.GameState;
import de.noah.util.GameStateManager;

//CLASS WHICH HANDLE THE USERINTERFACE
public class UI {

	// --------------------ATTIBUTES---------------------------------------
	private GameStateManager gameStateManager;
	private BufferedImage[] sprites;

	Font baseFont;
	
	private String messageToDisplay = " ";

	// --------------------CONSTRUCTOR---------------------------------------
	public UI(GameStateManager gameStateManager, BufferedImage[] uiSprites) {
		this.gameStateManager = gameStateManager;
		sprites = uiSprites;
		try {
			InputStream is = getClass().getResourceAsStream("/fonts/pressStart2P.ttf");
			if(is == null) {
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

	// --------------------REAL-METHODS---------------------------------------
	private Point getCenteredMessagePosition(Graphics2D g2, String message, int yOffsetFromBottom) {
		FontMetrics fm = g2.getFontMetrics();
		int textWidth = fm.stringWidth(message);
		
		int x = (Config.SCREEN_WIDTH - textWidth) / 2;
		int y = (Config.SCREEN_HEIGHT - yOffsetFromBottom);
		return new Point(x,y);
	}
	
	public void draw(Graphics2D g2, int interactiableNPC) {

		// Anti-Aliasing für geometrische Formen
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		// Anti-Aliasing für Text
		g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

		// SET FONT AND COLOR
		g2.setFont(baseFont);
		g2.setColor(Color.white);

		// DRAWING PLAYSTATE UI
		if (gameStateManager.getGameState() == GameState.PLAYSTATE) drawPlayStateScreen(g2, interactiableNPC);
		if (gameStateManager.getGameState() == GameState.DIALOGSTATE) drawDialogStateScreen(g2);

		
	}

	// PLAYSTATE UI DRAWINGS
	private void drawPlayStateScreen(Graphics2D g2, int interactiableNPC) {
		if (interactiableNPC != -1) {
			drawPossibleInteraction(g2, interactiableNPC);
		} else {

		}
	}

	// PLAYSTATE UI HELPERMETHODS
	private void drawPossibleInteraction(Graphics2D g2, int interactiableNPC) {
		switch (interactiableNPC) {
		case 0: {
			messageToDisplay = "PRESS SPACE TO INTERACT";
			Point messagePoint = getCenteredMessagePosition(g2, messageToDisplay, Config.TILE_SIZE/2);
			g2.drawString(messageToDisplay, messagePoint.x - 16, messagePoint.y);
			
			
			
			g2.drawImage(sprites[0], Config.TILE_SIZE*11 + 4 ,Config.SCREEN_HEIGHT - Config.TILE_SIZE + 1, 32, 32, null);
			
			break;
			}
		}
	}
	
	// DIALOGSTATE UI DRAWING

	private void drawDialogStateScreen(Graphics2D g2) {
		 // 1. Transparentes Hintergrundfeld
	    g2.setColor(new Color(0, 0, 0, 200));
	    g2.fillRoundRect(Config.TILE_SIZE * 2, Config.TILE_SIZE/2,
	                Config.TILE_SIZE * 12, Config.TILE_SIZE * 3,20,20);

	    // 2. Weißer dicker Rahmen drüber
	    Stroke old = g2.getStroke();
	    g2.setStroke(new BasicStroke(6));
	    g2.setColor(Color.white);
	    g2.drawRoundRect(Config.TILE_SIZE * 2, Config.TILE_SIZE/2,
	                Config.TILE_SIZE * 12, Config.TILE_SIZE * 3,20,20);
	    g2.setStroke(old);
	}
	
   

	// -------------------GETTER/SETTERS---------------------------------------
}
