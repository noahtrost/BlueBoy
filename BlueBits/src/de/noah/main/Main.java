package de.noah.main;

import javax.swing.JFrame;

//STARING CLASS
public class Main {
	
	public static void main(String [] args) {
		
		//CREATING WINDOW
		JFrame window = new JFrame();
		window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		window.setResizable(true);
		window.setTitle("BlueBits");
		
		//CREATING PANEL AND ADDING IT TO WINDOW
		GamePanel gamePanel = new GamePanel();
		window.add(gamePanel);
		window.pack();
		
		//SETTING GAME UP AND STARTING GAME THREAD
		gamePanel.setupGame();
		gamePanel.startGameThread();

		//MAKE WINDOW VISIBLE AFTER EVERTHING IS SETUP  - DRAWING STARTS NOW
		window.setLocationRelativeTo(null);
		window.setVisible(true); 
	}
}
