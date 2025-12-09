package de.noah.gamestates;

import de.noah.entity.Entity;
import de.noah.entity.Player;

public abstract class State {
	
	// --------------------ATTIBUTES---------------------------------------
	
	Player player;
	Entity npcs [];

	// --------------------CONSTRUCTOR---------------------------------------
	
	public State(Player player, Entity[] npcs) {
		this.player =  player;
		this.npcs = npcs;
	}
	
	// --------------------UPDATING---------------------------------------
	
	public abstract void update();

}
