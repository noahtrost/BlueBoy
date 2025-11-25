package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.KeyHandler;

public class Player extends Entity {
	GamePanel gp;
	KeyHandler keyH;

	public final int screenX;
	public final int screenY;
	public int hasKey = 0;

	public Player(GamePanel gp, KeyHandler keyH) {
		this.gp = gp;
		this.keyH = keyH;

		screenX = gp.screenWidth / 2 - gp.tileSize / 2;
		screenY = gp.screenHeight / 2 - gp.tileSize / 2;

		solidArea = new Rectangle(8, 16, 32, 32);

		setDefaultValues();
		getPlayerImage();

	}

	public void setDefaultValues() {
		worldX = gp.tileSize * 23;
		worldY = gp.tileSize * 21;
		defaultSolidX = 8;
		defaultSolidY = 16;
		speed = 4;
		direction = "down";
	}

	public void getPlayerImage() {
		try {
			up1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_up_1.png"));
			up2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_up_2.png"));
			down1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_down_1.png"));
			down2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_down_2.png"));
			left1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_left_1.png"));
			left2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_left_2.png"));
			right1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_right_1.png"));
			right2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_right_2.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private boolean movement() {
		if (keyH.downPressed || keyH.upPressed || keyH.leftPressed || keyH.rightPressed) {
			return true;
		}
		return false;
	}

	public void update() {
		if (movement()) {
			if (keyH.upPressed) {
				direction = "up";

			}
			else if (keyH.downPressed) {
				direction = "down";

			}
			else if (keyH.rightPressed) {
				direction = "right";

			}
			else if (keyH.leftPressed) {
				direction = "left";

			}
			
			collisionOn = false;
			
			// CHECK TILE COLLISION
			gp.cc.checkTile(this);
			
			// CHECK OBJECT COLLISION
			int objIndex = gp.cc.checkObject(this, true);
			pickUpObject(objIndex);
			
			if (collisionOn == false) {
				if (direction.equals("up"))
					worldY -= speed;
				else if(direction.equals("down"))
					worldY += speed;
				else if (direction.equals("right"))
					worldX += speed;
				else if (direction.equals("left"))
					worldX -= speed;
			}

			spriteCounter++;
			if (spriteCounter > 12) {
				if (spriteNum == 1) {
					spriteNum = 2;
				} else if (spriteNum == 2) {
					spriteNum = 1;
				}
				spriteCounter = 0;
			}
		}
	}

	public void pickUpObject(int index) {
		
		if (index == -1)return;
		String objName = gp.obj[index].name;

		switch (objName) {
		case "key":
			hasKey++;
			gp.obj[index] = null;
			System.out.println("Keys: " +hasKey);
			break;
			
		case "door":
			if (hasKey == 0) break;
			hasKey--;
			System.out.println("Keys: " +hasKey);
			gp.obj[index] = null;
			break;
			
		case "boots":
			speed += 1;
			gp.obj[index] = null;
			break;
		case "chest":
			System.out.println("You won ");
			gp.gameThread = null;
		}
		
		
	}

	public void draw(Graphics2D g2) {
		BufferedImage image = null;

		switch (direction) {
		case "up":
			if (spriteNum == 1) {
				image = up1;
			}
			if (spriteNum == 2) {
				image = up2;
			}
			break;
		case "down":
			if (spriteNum == 1) {
				image = down1;
			}
			if (spriteNum == 2) {
				image = down2;
			}
			break;
		case "right":
			if (spriteNum == 1) {
				image = right1;
			}
			if (spriteNum == 2) {
				image = right2;
			}
			break;
		case "left":
			if (spriteNum == 1) {
				image = left1;
			}
			if (spriteNum == 2) {
				image = left2;
			}
			break;
		}

		g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
	}
}
