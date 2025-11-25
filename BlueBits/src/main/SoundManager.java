package main;

import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class SoundManager {

	Clip clip;
	URL[] soundURL = new URL[30];

	public SoundManager() {
		soundURL[0] = getClass().getResource("/sound/BlueBoyAdventure.wav");
		soundURL[1] = getClass().getResource("/sound/blocked.wav");
		soundURL[2] = getClass().getResource("/sound/coin.wav");
		soundURL[3] = getClass().getResource("/sound/fanfare.wav");
		soundURL[4] = getClass().getResource("/sound/powerup.wav");
		soundURL[5] = getClass().getResource("/sound/unlock.wav");
	}

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

	public void play() {
		clip.start();
	}

	public void loop() {
		clip.loop(Clip.LOOP_CONTINUOUSLY);
	}

	public void stop() {
		clip.stop();
	}

}
