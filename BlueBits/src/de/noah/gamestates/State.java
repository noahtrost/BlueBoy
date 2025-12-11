package de.noah.gamestates;

import de.noah.audio.SoundManager;
import de.noah.entity.Entity;
import de.noah.entity.Player;

public abstract class State {
	
	// --------------------ATTIBUTES---------------------------------------
	
	Player player;
	Entity npcs [];
	private boolean space;
	protected SoundManager soundEffectManager;

	// --------------------CONSTRUCTOR---------------------------------------
	
	public State(Player player, Entity[] npcs, SoundManager soundEffectManager) {
		this.player =  player;
		this.npcs = npcs;
		this.soundEffectManager = soundEffectManager;
	}
	
	// --------------------UPDATING---------------------------------------
	
	public abstract void update();
	
	// --------------------GETTER/SETTER---------------------------------------

	public boolean isSpace() {
		return space;
	}

	public void setSpace(boolean space) {
		this.space = space;
	}

}
