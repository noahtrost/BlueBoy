package main;

import javax.swing.JFrame;

public class Main {
	public static void main(String [] args) {
		JFrame window = new JFrame();
		window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		window.setResizable(true);
		window.setTitle("BlueBits");
		
		GamePanel gamePanel = new GamePanel();
		window.add(gamePanel);
		window.pack();
		 
		window.setLocationRelativeTo(null);
		window.setVisible(true);
		
		gamePanel.setupGame();
		gamePanel.startGameThread();
	}
}
