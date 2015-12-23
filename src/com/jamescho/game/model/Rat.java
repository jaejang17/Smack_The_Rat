package com.jamescho.game.model;

import java.awt.Rectangle;
import java.applet.AudioClip;
import java.util.Timer;

import com.jamescho.framework.util.RandomNumberGenerator;
import com.jamescho.game.main.GameMain;
import com.jamescho.game.main.Resources;

public class Rat {
	private float x, y;
	private int width, height, velX, rand_num, lev_x;
	private Rectangle rect;
	private boolean dir_right;
	public boolean isDead, bound, right_bound;
	public AudioClip squeek;
	public Timer timer;
	public long creation_time, go_time;
	
	public Rat(int level_x, float y, int width, int height, AudioClip squeek) {
		
		rand_num = RandomNumberGenerator.getRandIntBetween(0, 2);
		if(rand_num < 1) {
			this.x = 25;
			this.right_bound = false;
		} else {
			this.x = 700;
			this.right_bound = true;
		}
		this.creation_time = System.currentTimeMillis() / 1000;
		this.go_time = (long) RandomNumberGenerator.getRandIntBetween(3, 15);
		this.y = y;
		this.width = width;
		this.height = height;
		this.isDead = false;
		this.squeek = squeek;
		this.bound = true;
		this.timer = new Timer();
		dir_right = false;
		velX = RandomNumberGenerator.getRandIntBetween(100, 600);
		rect = new Rectangle(); 
		lev_x = level_x;

	}
	
	public void update(float delta) {
		if(delta > 0.020) delta = 0.017f;
		
		if(System.currentTimeMillis() / 1000 - creation_time > go_time) {
			bound = false;
			Resources.activated.play();
		} 
		if(bound) {
			if(right_bound) {
				//System.out.println("I'm in the right bound");
				if((x > lev_x ) || (x < (lev_x * 0.75))) {
					velX = -velX;
					if(x > lev_x) {
						dir_right = false;
					} else {
						dir_right = false; // Menacing.
					}
				}
			} else if(!right_bound) {
				if((x > (lev_x / 6)) || (x < 0 - width)) {
					//System.out.println("I'm in the left bound");
					velX = -velX;
					if(x > (lev_x / 6)) {
						dir_right = true;
					} else {
						dir_right = true; // Menacing.
					}
				} 
			}
			
			x += velX * delta;
			
		} else if(!bound && !isDead) {
			if(x > GameMain.GAME_WIDTH || x < 0) {
				velX = -velX;
				if(x > GameMain.GAME_WIDTH) {
					dir_right = false;
				} else {
					dir_right = true;
				}
			}
			x += velX * delta;
		} else {
			x = 1000;
		}
	
		updateRects();
	}
	
	public void updateRects() {
		rect.setBounds((int) x, (int) (y + .5*height), width - 20, height / 2);
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
	
	public int getVel() {
		return velX;
	}
	
	public boolean getDir() {
		return dir_right;
	}
	
	public Rectangle getRect() {
		return rect;
	}
	
}

