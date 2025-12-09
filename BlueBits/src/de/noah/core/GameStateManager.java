package de.noah.core;

import de.noah.gamestates.GameState;

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
