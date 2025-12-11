package de.noah.ui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;

import de.noah.config.Config;

public class TitleStateUI extends StateUI {
	
	public TitleStateUI(BufferedImage[] uiSprites) {
		super(uiSprites);
	}
	
	public void drawMainMenu(Graphics2D g2, int cursorCounter) {
		super.draw(g2);
		g2.setFont(baseFont.deriveFont(50f));
		
		String text;
		
		text = "NOAH'S SWORD";
		Point textPos = getCenteredMessagePosition(g2, text, Config.SCREEN_HEIGHT - 100);
		
		g2.setColor(Color.DARK_GRAY);
		g2.drawString(text, textPos.x+5, textPos.y+5);
		g2.setColor(Color.WHITE);
		g2.drawString(text, textPos.x, textPos.y);
		
		g2.drawImage(sprites[2], Config.SCREEN_WIDTH/2- 184 , 140 , Config.TILE_SIZE*8, Config.TILE_SIZE*8, null);
		
		g2.setFont(baseFont.deriveFont(20f));
		
		text = "New Game";
		textPos = getCenteredMessagePosition(g2, text, 0, Config.SCREEN_WIDTH/3 , 100);
		if(cursorCounter== 0) {
			text = ">".concat(text);
		}

		g2.setColor(Color.DARK_GRAY);
		g2.drawString(text, textPos.x+3, textPos.y+3);
		g2.setColor(Color.WHITE);
		g2.drawString(text, textPos.x, textPos.y);
		
		text = "Load Game";
		textPos = getCenteredMessagePosition(g2, text, Config.SCREEN_WIDTH/3, Config.SCREEN_WIDTH/3*2 , 50);
		if(cursorCounter== 1) {
			text = ">".concat(text);
		}

		g2.setColor(Color.DARK_GRAY);
		g2.drawString(text, textPos.x+3, textPos.y+3);
		g2.setColor(Color.WHITE);
		g2.drawString(text, textPos.x, textPos.y);
		
		
		text = "Quit Game";
		textPos = getCenteredMessagePosition(g2, text, Config.SCREEN_WIDTH/3*2, Config.SCREEN_WIDTH , 100);
		if(cursorCounter == 2) {
			text = ">".concat(text);
		}
		
		g2.setColor(Color.DARK_GRAY);
		g2.drawString(text, textPos.x+3, textPos.y+3);
		g2.setColor(Color.WHITE);
		g2.drawString(text, textPos.x, textPos.y);
		
		
	}
	
	public void drawEnterNameMenu(Graphics2D g2, String name, int cursorCounter) {
		super.draw(g2);
		g2.setFont(baseFont.deriveFont(30f));
		
		String text;
		
		text = "Enter your Name:";
		Point textPos = getCenteredMessagePosition(g2, text, 400);
		
		g2.setColor(Color.DARK_GRAY);
		g2.drawString(text, textPos.x+5, textPos.y+5);
		g2.setColor(Color.WHITE);
		g2.drawString(text, textPos.x, textPos.y);
		
		text = name;
		textPos = getCenteredMessagePosition(g2, text, 300);
		
		if(cursorCounter == 0) {
			text = ">".concat(text);
		}
		
		g2.setColor(Color.DARK_GRAY);
		g2.drawString(text, textPos.x+5, textPos.y+5);
		g2.setColor(Color.WHITE);
		g2.drawString(text, textPos.x, textPos.y);
		
		
	}
	
	

}
