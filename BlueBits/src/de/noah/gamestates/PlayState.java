package de.noah.gamestates;

import de.noah.entity.Entity;
import de.noah.entity.Player;

public class PlayState extends State {
	
	// --------------------CONSTRUCTOR---------------------------------------

	public PlayState(Player player, Entity[] npcs) {
		super(player, npcs);
	}

	// --------------------UPDATING---------------------------------------
	
	@Override
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

}
