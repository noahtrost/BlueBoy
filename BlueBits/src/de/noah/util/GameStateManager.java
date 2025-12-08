package de.noah.util;

import de.noah.main.GameState;

//GAMESTATE MANAGER CLASS
public class GameStateManager {
	

	// --------------------ATTIBUTES---------------------------------------
	private GameState gameState;

	//-----------------------CONSTRCTOR---------------------------------------- 
	public GameStateManager() {}
	
	// -------------------GETTER/SETTERS---------------------------------------
	public GameState getGameState() {
		return gameState;
	}

	public void setGameState(GameState gameState) {
		this.gameState = gameState;
	}
	
}
