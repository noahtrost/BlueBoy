package de.noah.ui;

import java.awt.BasicStroke;
import java.awt.Color;
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
	
		public void draw(Graphics2D g2) {
			super.draw(g2);
			
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
		
}
