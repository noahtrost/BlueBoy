package de.noah.audio;

import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;


//CLASS MANAGES ALL SOUNDS
public class SoundManager {
	
//-----------------------ATTRIBUTES-------------------------
	Clip clip;
	URL[] soundURL = new URL[30];
	
//-----------------------CONSTRUCTOR-------------------------
	public SoundManager() {
		soundURL[0] = getClass().getResource("/sound/BlueBoyAdventure.wav");
		soundURL[1] = getClass().getResource("/sound/blocked.wav");
		soundURL[2] = getClass().getResource("/sound/coin.wav");
		soundURL[3] = getClass().getResource("/sound/fanfare.wav");
		soundURL[4] = getClass().getResource("/sound/speak.wav");
		soundURL[5] = getClass().getResource("/sound/unlock.wav");
		soundURL[6] = getClass().getResource("/sound/Merchant.wav");
	}

	
//-----------------------REAL-METHODS-------------------------
	
	//SETTING SOUND VIA INDEX AND SAVES URLS
	public void setSound(int index) {
		try {
			AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[index]);
			clip = AudioSystem.getClip();
			clip.open(ais);
		} catch (Exception e) {
			System.err.println("Konnte Sound nicht laden: index=" + index + ", url=" + soundURL[index]);
			e.printStackTrace();
		}
	}

	//PLAYS SOUND WHICH WAS SET IN CLIP{
	public void play() {
		clip.start();
	}

	//LOOPS SOUND WHICH WAS SET IN CLIP
	public void loop() {
		clip.loop(Clip.LOOP_CONTINUOUSLY);
	}

	//STOPS SOUND WHICH WAS SET IN CLIP
	public void stop() {
		clip.stop();
	}
	
	public void playMusic(int i) {
		setSound(i);
		play();
		loop();
	}
	
	public void playSE(String sound) {
		switch(sound) {
		case "blocked": {
			setSound(1);
			break;
		}
		
		case "coin": {
			setSound(2);
			break;
		}
		
		case "fanfare": {
			setSound(3);
			break;
		}
		
		case "speak": {
			setSound(4);
			break;
		}
		
		case "unlock": {
			setSound(5);
			break;
		}	
		}
		play();
	}

}
