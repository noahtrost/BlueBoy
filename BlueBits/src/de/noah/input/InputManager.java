package de.noah.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

//CLASS FOR HANDLING AND TRANSMITTING USER INPUTS
public class InputManager implements KeyListener {

//--------------------ATTRIBUTES-----------------------------
	private boolean up, down, left, right, space, pause;

	private boolean spaceJustPressed = false;
//--------------------CONSTRUCTOR----------------------------

	public InputManager() {}

//--------------------REAL-METHODS---------------------------
	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int code = e.getKeyCode();
		if (code == KeyEvent.VK_W) {
			up = true;
		}
		if (code == KeyEvent.VK_S) {
			down = true;
		}
		if (code == KeyEvent.VK_A) {
			left = true;
		}
		if (code == KeyEvent.VK_D) {
			right = true;
		}

		if (code == KeyEvent.VK_P) {
			pause = true;
		}

		if (code == KeyEvent.VK_SPACE && !space) {
			spaceJustPressed = true;
			space = true;
		}
		
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		int code = e.getKeyCode();
		if (code == KeyEvent.VK_W) {
			up = false;
		}
		if (code == KeyEvent.VK_S) {
			down = false;
		}
		if (code == KeyEvent.VK_A) {
			left = false;
		}
		if (code == KeyEvent.VK_D) {
			right = false;
		}

		if (code == KeyEvent.VK_P) {
			pause = false;
		}
		if (code == KeyEvent.VK_SPACE) {
			space = false;
			spaceJustPressed = false;
		}
	}

	//--------------------GETTER/SETTER-----------------------
	public boolean isUp() {
		return up;
	}

	public void setUp(boolean up) {
		this.up = up;
	}

	public boolean isDown() {
		return down;
	}

	public void setDown(boolean down) {
		this.down = down;
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

	public boolean isSpace() {
		return space;
	}

	public void setSpace(boolean space) {
		this.space = space;
	}

	public boolean isPause() {
		return pause;
	}

	public void setPause(boolean pause) {
		this.pause = pause;
	}

	public boolean isSpaceJustPressed() {
		return spaceJustPressed;
	}

	public void setSpaceJustPressed(boolean spaceJustPressed) {
		this.spaceJustPressed = spaceJustPressed;
	}

}
