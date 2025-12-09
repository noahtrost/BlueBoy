package de.noah.gamestates;

import de.noah.entity.Entity;
import de.noah.entity.Player;

public class DialogState extends State{
	
	// --------------------CONSTRUCTOR---------------------------------------

	
	public DialogState(Player player, Entity[] npcs) {
		super(player, npcs);
	}


	// --------------------UPDATING------------------------------------------
	
	@Override
	public void update() {
		makeEyeContact();
	}
	
	private void makeEyeContact() {
		int npcIndex = player.getinteractiableNPC();

		int xDiff = Math.abs(player.getWorldX() - npcs[npcIndex].getWorldX());
		int yDiff = Math.abs(player.getWorldY() - npcs[npcIndex].getWorldY());

		if (xDiff >= yDiff) {
			if (player.getWorldX() <= npcs[npcIndex].getWorldX()) {
				player.setDialogFacing(6);
				npcs[npcIndex].setDialogFacing(4);
			} else {
				player.setDialogFacing(4);
				npcs[npcIndex].setDialogFacing(6);
			}
		} else {
			if (player.getWorldY() <= npcs[npcIndex].getWorldY()) {
				player.setDialogFacing(2);
				npcs[npcIndex].setDialogFacing(0);
			} else {
				player.setDialogFacing(0);
				npcs[npcIndex].setDialogFacing(2);
			}
		}
	}

}
