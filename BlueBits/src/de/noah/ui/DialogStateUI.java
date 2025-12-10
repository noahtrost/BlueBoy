package de.noah.ui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import de.noah.audio.SoundManager;
import de.noah.config.Config;
import de.noah.entity.Entity;

public class DialogStateUI extends StateUI {

	// --------------------ATTRIBUTES----------------------------------------

	SoundManager soundEffectManager;

	// --------------------CONSTRUCTOR---------------------------------------

	public DialogStateUI(BufferedImage[] sprites, SoundManager soundEffectManager) {
		super(sprites);
		this.soundEffectManager = soundEffectManager;
	}

	// --------------------CONTAINER-FOR-GENERAL-DIALOG----------------------
	private void drawDialogBox(Graphics2D g2) {

		// BLACK-TRANSPARENT BACKGROUND
		int x = 96;
		int y = 24;
		int width = 576;
		int height = 144;
		int arcWidth = 20;
		int arcHeight = 20;

		g2.setColor(new Color(0, 0, 0, 200));
		g2.fillRoundRect(x, y, width, height, arcWidth, arcHeight);

		// WHITE OUTLINE STROKE

		g2.setStroke(new BasicStroke(6));
		g2.setColor(Color.white);
		g2.drawRoundRect(x, y, width, height, arcWidth, arcHeight);

	}

	private void drawContinueField(Graphics2D g2) {

		// BLACK-TRANSPARENT BACKGROUND
		int x = 90;
		int y = Config.SCREEN_HEIGHT - 76;
		int width = 240;
		int height = 48;
		int arcWidth = 20;
		int arcHeight = 20;

		g2.setColor(new Color(0, 0, 0, 200));
		g2.fillRoundRect(x, y, width, height, arcWidth, arcHeight);

		// WHITE OUTLINE STROKE
		g2.setStroke(new BasicStroke(6));
		g2.setColor(Color.white);
		g2.drawRoundRect(x, y, width, height, arcWidth, arcHeight);

		// TEXT
		x = 97;
		y = Config.SCREEN_HEIGHT - 47;
		messageToDisplay = "PRESS SPACE TO CONTINUE";

		g2.setFont(baseFont.deriveFont(9.5f));
		g2.drawString(messageToDisplay, x, y);
	}

	private void drawLeaveField(Graphics2D g2) {

		// BLACK-TRANSPARENT BACKGROUND
		int x = 426;
		int y = Config.SCREEN_HEIGHT - 76;
		int width = 240;
		int height = 48;
		int arcWidth = 20;
		int arcHeight = 20;

		g2.setColor(new Color(0, 0, 0, 200));
		g2.fillRoundRect(x, y, width, height, arcWidth, arcHeight);

		// WHITE OUTLINE STROKE
		g2.setStroke(new BasicStroke(6));
		g2.setColor(Color.white);
		g2.drawRoundRect(x, y, width, height, arcWidth, arcHeight);

		// TEXT
		x = 448;
		y = Config.SCREEN_HEIGHT - 47;
		messageToDisplay = "PRESS ENTER TO LEAVE";

		g2.setFont(baseFont.deriveFont(9.5f));
		g2.drawString(messageToDisplay, x, y);
	}

	// --------------------CONTAINER-FOR-NPC-DIALOG--------------------------

	private void drawNPCDialog(Graphics2D g2, Entity npc) {

		g2.setColor(Color.white);
		g2.setFont(baseFont.deriveFont(16f));

		messageToDisplay = npc.getSpeech()[npc.getSpeechCounter()];
		String lines[] = messageToDisplay.split("\n");

		int x = 116;
		int y = 58;

		for (int i = 0; i < lines.length; i++) {
			g2.drawString(lines[i], x, y + i * 25);
		}

		if (space && npc.getSpeechCounter() < npc.getSpeech().length - 1) {
			npc.setSpeechCounter(npc.getSpeechCounter() + 1);
			soundEffectManager.playSE("speak");
		}
		space = false;
	}

	// --------------------CONTAINER-FOR-OBJECT-DIALOG-----------------------

	private void drawObjectMessage(Graphics2D g2, String objectName) {
		Font font;
		g2.setColor(Color.white);
		font = baseFont.deriveFont(16f);
		g2.setFont(font);

		switch (objectName) {
		case "door": {
			messageToDisplay = "Not enough Keys..";
			break;
		}
		}

		g2.drawString(messageToDisplay, Config.TILE_SIZE * 2 + 20, Config.TILE_SIZE + 10);
	}

	// --------------------DRAWING-------------------------------------------

	// DRAWS DIALOGSTATE INVOKED BY NPC DIALOG
	public void draw(Graphics2D g2, Entity npc) {
		super.draw(g2);
		drawDialogBox(g2);
		drawNPCDialog(g2, npc);
		if (npc.getSpeechCounter() < npc.getSpeech().length - 1)
			drawContinueField(g2);
		drawLeaveField(g2);
	}

	// DRAWS DIALOGSTATE INVOKED BY OBJECT DIALOG
	public void draw(Graphics2D g2, String objectName) {
		super.draw(g2);
		drawDialogBox(g2);
		drawObjectMessage(g2, objectName);
		drawLeaveField(g2);
	}

	// --------------------GETTERS/SETTERS-----------------------------------

}
