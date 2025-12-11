package de.noah.gamestates;

import java.awt.Graphics2D;

import de.noah.audio.SoundManager;
import de.noah.core.ObjectManager;
import de.noah.entity.Entity;
import de.noah.entity.Player;
import de.noah.ui.DialogStateUI;

public class DialogState extends State{
	

	private boolean objectDialog, npcDialog;
	private DialogStateUI dialogStateUI;
	private ObjectManager objectManager;
	private String npcLine;
	private boolean renderContinueField;
	
	// --------------------CONSTRUCTOR---------------------------------------


	public DialogState(Player player, Entity[] npcs, DialogStateUI dialogStateUI,SoundManager soundEffectManager, ObjectManager objectManager) {
		super(player, npcs, soundEffectManager);
		this.dialogStateUI = dialogStateUI;
		this.objectManager = objectManager;
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
	
	@Override
	public void update() {
		
		if(npcDialog) {
			Entity dialogPartner = npcs[player.getinteractiableNPC()];
			makeEyeContact(dialogPartner);
			npcLine = getNextNPCLine(dialogPartner);
			renderContinueField = !lastNPCLine(dialogPartner);

		}
		else if(objectDialog) {
			
		}
		
		setSpace(false);
	}
	
	// --------------------DRAWING------------------------------------------
	
	public void draw(Graphics2D g2) {
		
		if (npcDialog) { 
			
			dialogStateUI.draw(g2, npcLine, renderContinueField);
			
		}

		else if (objectDialog) {  
			
			dialogStateUI.draw(g2, objectManager.getObjectName());
			objectManager.setInvokeDialogState(false);
			
		}
		
	}
	
	// --------------------GETTER/SETTERS-----------------------------------------


	public void setObjectDialog(boolean objectDialog) {
		this.objectDialog = objectDialog;
	}


	public void setNpcDialog(boolean npcDialog) {
		this.npcDialog = npcDialog;
	}

	public boolean isObjectDialog() {
		return objectDialog;
	}


	public boolean isNpcDialog() {
		return npcDialog;
	}
	
}
