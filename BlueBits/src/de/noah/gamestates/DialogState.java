package de.noah.gamestates;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import de.noah.audio.SoundManager;
import de.noah.entity.Entity;
import de.noah.entity.Player;
import de.noah.object.SuperObject;
import de.noah.ui.DialogStateUI;

public class DialogState extends State{
	

	private boolean npcDialog, objectDialog;
	
	private DialogStateUI dialogStateUI;
	private BufferedImage npcSprite;
	private String npcLine;
	private boolean renderContinueField;
	
	private String objectName;
	
	// --------------------CONSTRUCTOR---------------------------------------


	public DialogState(Player player,SoundManager soundEffectManager, DialogStateUI dialogStateUI) {
		super(player, soundEffectManager);
		this.dialogStateUI = dialogStateUI;
	}
	
	// DIALOG STATE INVOKED BY NPC
	
	private void makeEyeContact(Entity dialogPartner) {

		int xDiff = Math.abs(player.getWorldX() - dialogPartner.getWorldX());
		int yDiff = Math.abs(player.getWorldY() - dialogPartner.getWorldY());

		if (xDiff >= yDiff) {
			if (player.getWorldX() <= dialogPartner.getWorldX()) {
				player.setDialogFacing(6);
				dialogPartner.setDialogFacing(4);
			} else {
				player.setDialogFacing(4);
				dialogPartner.setDialogFacing(6);
			}
		} else {
			if (player.getWorldY() <= dialogPartner.getWorldY()) {
				player.setDialogFacing(2);
				dialogPartner.setDialogFacing(0);
			} else {
				player.setDialogFacing(0);
				dialogPartner.setDialogFacing(2);
			}
		}
	}

	private String getNextNPCLine(Entity dialogPartner) {
		
		if (isSpace() && dialogPartner.getSpeechCounter() < dialogPartner.getSpeech().length - 1) {
			dialogPartner.setSpeechCounter(dialogPartner.getSpeechCounter() + 1);
			soundEffectManager.playSE("speak");
		}	
		return dialogPartner.getSpeech()[dialogPartner.getSpeechCounter()];
	}
	
	private boolean lastNPCLine(Entity dialogPartner) {
		return dialogPartner.getSpeechCounter() == dialogPartner.getSpeech().length -1;
	}
	
	// --------------------UPDATING------------------------------------------
	
	public void update(Entity dialogPartner, SuperObject object) {
		
		if(dialogPartner != null) {
			
			npcDialog = true;
			
			makeEyeContact(dialogPartner);
			npcLine = getNextNPCLine(dialogPartner);
			renderContinueField = !lastNPCLine(dialogPartner);
			npcSprite = dialogPartner.getSprites()[8];
			
			dialogPartner = null;
	

		}
		else if(object != null) {
			
			objectDialog = true;
			
			objectName = object.getName();
			object = null;
		}
		
		setSpace(false);
	}
	
	// --------------------DRAWING------------------------------------------
	
	public void draw(Graphics2D g2) {
		
		if (npcDialog) { 
			
			dialogStateUI.draw(g2, npcLine, renderContinueField, npcSprite);
			npcDialog = false;
			
		}

		else if (objectDialog) {   
			
			dialogStateUI.draw(g2, objectName);
			objectDialog = false;
			
		}
		
	}
	
	// --------------------GETTER/SETTERS-----------------------------------------
	
}
