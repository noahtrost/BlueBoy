package de.noah.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

//CLASS FOR HANDLING AND TRANSMITTING USER INPUTS
public class InputManager implements KeyListener {

//--------------------ATTRIBUTES-----------------------------
	private boolean up, down, left, right, space, pause, enter;

	private boolean spaceJustPressed, enterJustPressed, leftJustPressed, rightJustPressed, upJustPressed, downJustPressed;
	
	private char typedChar = 0;
//--------------------CONSTRUCTOR----------------------------

	public InputManager() {}

//--------------------REAL-METHODS---------------------------
	@Override
	public void keyTyped(KeyEvent e) {
		typedChar = e.getKeyChar();
	}
	
	public char consumeTypedChar() {
	    char c = typedChar;
	    typedChar = 0; 
	    return c;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int code = e.getKeyCode();
		
		if (code == KeyEvent.VK_W && !up) {
			upJustPressed = true;
			up = true;
		}
		if (code == KeyEvent.VK_S && !down) {
			downJustPressed = true;
			down = true;
		}
		if (code == KeyEvent.VK_A && !left) {
			leftJustPressed = true;
			left = true;
		}
		if (code == KeyEvent.VK_D && !right) {
			rightJustPressed = true;
			right = true;
		}

		if (code == KeyEvent.VK_SPACE && !space) {
			spaceJustPressed = true;
			space = true;
		}
		
		if (code == KeyEvent.VK_ENTER && !enter) {
			enterJustPressed = true;
			enter = true;
		}
		
		if (code == KeyEvent.VK_P) {
			pause = true;
		}
		
		
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		int code = e.getKeyCode();
		if (code == KeyEvent.VK_W) {
			up = false;
			upJustPressed = false;
		}
		if (code == KeyEvent.VK_S) {
			down = false;
			downJustPressed = false;
		}
		if (code == KeyEvent.VK_A) {
			left = false;
			leftJustPressed = false;
		}
		if (code == KeyEvent.VK_D) {
			right = false;
			rightJustPressed = false;
		}
		
		if (code == KeyEvent.VK_SPACE) {
			space = false;
			spaceJustPressed = false;
		}
		if (code == KeyEvent.VK_ENTER) {
			enter = false;
			enterJustPressed = false;
		}

		if (code == KeyEvent.VK_P) {
			pause = false;
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

	public boolean isEnterJustPressed() {
		return enterJustPressed;
	}

	public void setEnterJustPressed(boolean enterJustPressed) {
		this.enterJustPressed = enterJustPressed;
	}

	public boolean isEnter() {
		return enter;
	}

	public void setEnter(boolean enter) {
		this.enter = enter;
	}
	
	public boolean isLeftJustPressed() {
		return leftJustPressed;
		
	}

	public void setLeftJustPressed(boolean leftJustPressed) {
		this.leftJustPressed = leftJustPressed;
	}

	public boolean isRightJustPressed() {
		return rightJustPressed;
	}

	public void setRightJustPressed(boolean rightJustPressed) {
		this.rightJustPressed = rightJustPressed;
	}

	public boolean isUpJustPressed() {
		return upJustPressed;
	}

	public void setUpJustPressed(boolean upJustPressed) {
		this.upJustPressed = upJustPressed;
	}

	public boolean isDownJustPressed() {
		return downJustPressed;
	}

	public void setDownJustPressed(boolean downJustPressed) {
		this.downJustPressed = downJustPressed;
	}

}
