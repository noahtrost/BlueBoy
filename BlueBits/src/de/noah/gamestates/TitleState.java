package de.noah.gamestates;

import java.awt.Graphics2D;

import de.noah.audio.SoundManager;
import de.noah.entity.Player;
import de.noah.ui.TitleStateUI;

public class TitleState extends State{

	private TitleStateUI titleStateUI;
	private int cursorCounter = 0;
	private boolean nameScreen = false;
	private boolean invokePlayState = false;
	private boolean invokeQuitGame = false;
	private String playerName = "";
	private char character = 0;
	
	
	public TitleState(Player player, SoundManager soundEffectManager, TitleStateUI titleStateUI) {
		super(player, soundEffectManager);
		this.titleStateUI = titleStateUI;
	}


	public void update() {
		addToName();
		getCurserInput();
		if(cursorCounter == 0 && (enter || space)) {
			nameScreen = true;
		}
		if(cursorCounter == 2 && (enter || space)) {
			invokeQuitGame = true;
		}
		setLeft(false);
		setRight(false);
		setSpace(false);
		setEnter(false);
	}


	private void getCurserInput() {
		if(left) {
			cursorCounter--;
		}
		else if(right) { 
			cursorCounter++;
		}
		
		if(cursorCounter < 0) { 
			cursorCounter = 2;
		}
		if(cursorCounter > 2) { 
			cursorCounter = 0;
		}
	}
	
	private void addToName() {
		if(character != 0) playerName += getCharacter();
		character = 0;
	}

	public void draw(Graphics2D g2) {
		if(!isNameScreen()) {

			titleStateUI.drawMainMenu(g2, cursorCounter);
		}
		else {
			
			titleStateUI.drawEnterNameMenu(g2, playerName, cursorCounter);
		}
	}


	public boolean isInvokePlayState() {
		return invokePlayState;
	}


	public void setInvokePlayState(boolean invokePlayState) {
		this.invokePlayState = invokePlayState;
	}


	public boolean isInvokeQuitGame() {
		return invokeQuitGame;
	}


	public void setInvokeQuitGame(boolean invokeQuitGame) {
		this.invokeQuitGame = invokeQuitGame;
	}


	public char getCharacter() {
		return character;
	}


	public void setCharacter(char character) {
		this.character = character;
	}


	public boolean isNameScreen() {
		return nameScreen;
	}

}
