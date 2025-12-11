package de.noah.gamestates;

import de.noah.audio.SoundManager;
import de.noah.entity.Player;

public abstract class State {
	
	// --------------------ATTIBUTES---------------------------------------
	
	Player player;
	protected boolean space, left, right, enter;
	

	protected SoundManager soundEffectManager;

	// --------------------CONSTRUCTOR---------------------------------------
	
	public State(Player player, SoundManager soundEffectManager) {
		this.player =  player;
		this.soundEffectManager = soundEffectManager;
	}
	
	// --------------------GETTER/SETTER---------------------------------------

	public boolean isSpace() {
		return space;
	}

	public void setSpace(boolean space) {
		this.space = space;
	}
	
	public boolean isLeft() {
		return left;
	}

	public void setLeft(boolean left) {
		this.left = left;
	}

	public boolean isRight() {
		return right;
	}

	public void setRight(boolean right) {
		this.right = right;
	}

	public boolean isEnter() {
		return enter;
	}

	public void setEnter(boolean enter) {
		this.enter = enter;
	}

}
