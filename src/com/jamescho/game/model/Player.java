package com.jamescho.game.model;

import java.awt.Rectangle;

import com.jamescho.game.main.Resources;

public class Player {
	private float x, y;
	private int width, height, velX, velY;
	private Rectangle rect, ground, hammer_rect;
	private boolean dir_right;

	public boolean isAlive;
	private boolean isRunning;
	private boolean isSmacking;
	
	private float smackDuration = 0.21f;

	private static final int JUMP_VELOCITY = -600;
	private static final int ACCEL_GRAVITY = 1700;
	private static final int MOVE_VELOCITY =  400;

	public Player(float x, float y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.dir_right = true;

		ground = new Rectangle(0, 405, 800, 45);
		rect = new Rectangle();
		hammer_rect = new Rectangle();
		isAlive = true;
		isRunning = false;
		isSmacking = false;
		updateRects();
	}

	public void update(float delta) {
		if(smackDuration > 0 && isSmacking) {
			smackDuration -= delta;
		} else {
			isSmacking = false;
		}
		
		if (!isGrounded()) {
			velY += ACCEL_GRAVITY * delta;
		} else {
			y = 406 - height;
			velY = 0;
		}
		y += velY * delta;
		if(isRunning) {
			x += velX * delta;
		}
		updateRects();
		//System.out.println(x);
		//System.out.println(isSmacking); # Debugging. 
	}
	
	public void updateRects() {
		if(isSmacking) {
			if(dir_right) {
				hammer_rect.setBounds((int) x + 160, (int) y + height / 2, 50, height / 2);
			} else {
				hammer_rect.setBounds((int) x + 10, (int) y + height / 2, 50, height / 2);
			}
		} else {
			hammer_rect.setBounds((int) 500, (int) 100, 50, height);
		}
		
		rect.setBounds((int) x + width / 2, (int) y, 10, height);
	}

	public void jump() {
		if (isGrounded()) {
			Resources.onjump.play();
			y -= 20;
			velY = JUMP_VELOCITY;
			updateRects();
		}
	}
	
	public void smack() {
		isSmacking = true;
		smackDuration = 0.21f;
		//Resources.smack_sound.play();
	}
	
	public void move_left() {
		isRunning = true;
		velX = -MOVE_VELOCITY;
		dir_right = false;
	}
	
	public void turn_left() {
		dir_right = false;
	}
	
	public void move_right() {
		isRunning = true;
		velX = MOVE_VELOCITY;
		dir_right = true;
	}
	
	public void turn_right() {
		dir_right = true;
	}
	
	public void stop_moving() {
		isRunning = false;
	}

	public boolean isGrounded() {
		return rect.intersects(ground);
	}
	
	public boolean isRunning() {
		return isRunning;
	}

	public boolean isSmacking() {
		return isSmacking;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int getVelY() {
		return velY;
	}

	public Rectangle getRect() {
		return rect;
	}
	
	public Rectangle getHammerRect() {
		return hammer_rect;
	}

	public Rectangle getGround() {
		return ground;
	}

	public boolean isAlive() {
		return isAlive;
	}
	
	public boolean getDir() {
		return dir_right;
	}
}