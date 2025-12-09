package de.noah.ui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.image.BufferedImage;

import de.noah.config.Config;

public class DialogStateUI extends StateUI {

	// --------------------CONSTRUCTOR---------------------------------------
	
	
	
	public DialogStateUI(BufferedImage[] sprites) {
		super(sprites);
	}

	
	// --------------------DRAWING-------------------------------------------
	
		public void draw(Graphics2D g2, String[] npcLines) {
			super.draw(g2);
			drawDialogBox(g2);
			drawDialog(g2, npcLines);
			if(speechCounter < npcLines.length-1) drawContinueField(g2);
			drawLeaveField(g2);
		}
		
		private void drawDialogBox(Graphics2D g2) {
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
		
		
		private void drawLeaveField(Graphics2D g2) {
			Font font;
			// 1. Transparentes Hintergrundfeld
		    g2.setColor(new Color(0, 0, 0, 200));
		    g2.fillRoundRect(Config.TILE_SIZE * 9 - 6, Config.SCREEN_HEIGHT - Config.TILE_SIZE - 30,
		                Config.TILE_SIZE * 5, Config.TILE_SIZE * 1,20,20);

		    // 2. Weißer dicker Rahmen drüber
		    Stroke old = g2.getStroke();
		    g2.setStroke(new BasicStroke(6));
		    g2.setColor(Color.white);
		    g2.drawRoundRect(Config.TILE_SIZE * 9 - 6, Config.SCREEN_HEIGHT - Config.TILE_SIZE - 30,
	                Config.TILE_SIZE * 5, Config.TILE_SIZE * 1,20,20);
		    g2.setStroke(old);
			
			messageToDisplay = "PRESS ENTER TO LEAVE";
			font = baseFont.deriveFont(10f);
			g2.setFont(font);
			g2.drawString(messageToDisplay, Config.TILE_SIZE *9 + 12, Config.SCREEN_HEIGHT - Config.TILE_SIZE + 1);
			
		}


		private void drawDialog(Graphics2D g2, String[] npcLines) {
			Font font;
			g2.setColor(Color.white);
			font = baseFont.deriveFont(16f);
			g2.setFont(font);
			
			messageToDisplay = npcLines[speechCounter];
			String lines [] = seprateLine(messageToDisplay);
			
			for(int i = 0; i < lines.length; i++) {
				g2.drawString(lines[i], Config.TILE_SIZE*2 + 20, Config.TILE_SIZE + 10 + i*25);
			}
			
			if(space) {
				if(speechCounter < npcLines.length-1)
				speechCounter++;
				space = false;
			}
		}
		
		private void drawContinueField(Graphics2D g2) {
			Font font;
			// 1. Transparentes Hintergrundfeld
		    g2.setColor(new Color(0, 0, 0, 200));
		    g2.fillRoundRect(Config.TILE_SIZE * 2 - 6, Config.SCREEN_HEIGHT - Config.TILE_SIZE - 30,
		                Config.TILE_SIZE * 5, Config.TILE_SIZE * 1,20,20);

		    // 2. Weißer dicker Rahmen drüber
		    Stroke old = g2.getStroke();
		    g2.setStroke(new BasicStroke(6));
		    g2.setColor(Color.white);
		    g2.drawRoundRect(Config.TILE_SIZE * 2 - 6, Config.SCREEN_HEIGHT - Config.TILE_SIZE - 30,
	                Config.TILE_SIZE * 5, Config.TILE_SIZE * 1,20,20);
		    g2.setStroke(old);
			
			messageToDisplay = "PRESS SPACE TO CONTINUE";
			font = baseFont.deriveFont(10f);
			g2.setFont(font);
			g2.drawString(messageToDisplay, Config.TILE_SIZE *2, Config.SCREEN_HEIGHT - Config.TILE_SIZE + 1);
		}
		
		private String [] seprateLine(String line) {
					String[] drawLines = line.split("\n");
					return drawLines;
		}
		
}
