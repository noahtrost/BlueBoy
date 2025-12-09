package de.noah.core;

import de.noah.audio.SoundManager;
import de.noah.entity.Player;
import de.noah.object.SuperObject;

public class ObjectManager {

	Player player;
	SuperObject[] objects;
	SoundManager soundEffectManager;
	
	private boolean endGame = false;

	public ObjectManager(Player player, SuperObject[] objects, SoundManager soundEffectManager) {
		this.player = player;
		this.objects = objects;
		this.soundEffectManager = soundEffectManager;
	}
	
	public void handle(int i) {
		if(i == -1) return;
		
		String name = objects[i].getName();
		
		switch(name) {
		case "key" : {
			player.setKeyCounter(player.getKeyCounter()+1);
			objects[i] = null;
			soundEffectManager.playSE(2);
			break;
			}
		case "door" :{
			if(player.getKeyCounter() > 0) {
				objects[i] = null;
				player.setKeyCounter(player.getKeyCounter()-1);
				soundEffectManager.playSE(5);
				break;
				}
			else {
				System.out.println("Huh... i have no keys.");
			}
			}
		case "chest" :{
				objects[i] = null;
				soundEffectManager.playSE(3);
				endGame = true;
				break;
			}
		}
		
	}

	public boolean isEndGame() {
		return endGame;
	}
}
