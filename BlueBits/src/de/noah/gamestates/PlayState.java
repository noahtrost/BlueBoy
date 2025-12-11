package de.noah.gamestates;

import java.awt.Graphics2D;

import de.noah.audio.SoundManager;
import de.noah.entity.Entity;
import de.noah.entity.Player;
import de.noah.ui.PlayStateUI;

public class PlayState extends State {
	
	private Entity npcs[];
	private PlayStateUI playStateUI;
	
	// --------------------CONSTRUCTOR---------------------------------------

	public PlayState(Player player, SoundManager soundEffectManager, PlayStateUI playStateUI, Entity[] npcs) {
		super(player, soundEffectManager);
		this.npcs = npcs;
		this.playStateUI = playStateUI;
	}

	// --------------------UPDATING---------------------------------------
	

	public void update() {
		// UPDATE PLAYER
		player.update();

		// UPDATE NPCS
		for (int i = 0; i < npcs.length; i++) {
			if (npcs[i] != null) {
				npcs[i].update();
			}
		}
	}
	
	public void draw(Graphics2D g2) {
		playStateUI.draw(g2, player.getinteractiableNPC(), player.getKeyCounter());
	}

}
