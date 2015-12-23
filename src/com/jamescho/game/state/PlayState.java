package com.jamescho.game.state;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import com.jamescho.framework.util.RandomNumberGenerator;
import com.jamescho.game.main.GameMain;
import com.jamescho.game.main.Resources;
import com.jamescho.game.model.Cloud;
import com.jamescho.game.model.Player;
import com.jamescho.game.model.Rat;

public class PlayState extends State {
	private Player player;
	
	private ArrayList<Rat> rats;
	
	private Cloud cloud, cloud2;

	private Font scoreFont;
	private int playerScore = 0;

	private static final int PLAYER_WIDTH = 225;
	private static final int PLAYER_HEIGHT = 225;

	private static final int RAT_WIDTH = 110;
	private static final int RAT_HEIGHT = 65;
	
	private int player_width, player_x, rat_width, rat_x;

	@Override
	public void init() {
		player = new Player(GameMain.GAME_WIDTH / 3, GameMain.GAME_HEIGHT - PLAYER_HEIGHT,
				PLAYER_WIDTH, PLAYER_HEIGHT);
		
		rats = new ArrayList<Rat>();
		
		for(int i = 0; i < 5; i++) {
			Rat r = new Rat(GameMain.GAME_WIDTH, 
							GameMain.GAME_HEIGHT - RAT_HEIGHT - 40, RAT_WIDTH, RAT_HEIGHT, Resources.bonk);
			rats.add(r);
		}

		cloud = new Cloud(100, 100);
		cloud2 = new Cloud(500, 50);
		
		scoreFont = new Font("SansSerif", Font.BOLD, 25);
	
	}

	@Override
	public void update(float delta) {
		cloud.update(delta);
		cloud2.update(delta);
		Resources.runAnim.update(delta);
		Resources.ratAnim.update(delta);
		Resources.smackAnim.update(delta);
		player.update(delta);
		updateRats(delta);
		if(!player.isAlive()) {
			setCurrentState( new GameOverState( playerScore / 100));
		}
	}
	
	private void updateRats(float delta) {
		for (Rat r: rats) {
			r.update(delta);
			if(r.getRect().intersects(player.getHammerRect())) {
				r.isDead = true;
				r.squeek.play(); // This needs to be fixed, something wonky.
			}
		}
	}


	@Override
	public void render(Graphics g) {
		g.setColor(Resources.skyBlue);
		g.fillRect(0, 0, GameMain.GAME_WIDTH, GameMain.GAME_HEIGHT);
		renderPlayer(g);
		renderRats(g);
		renderSun(g);
		renderClouds(g);
		g.drawImage(Resources.grass, 0, 405, null);
		renderScore(g);
	}

	private void renderScore(Graphics g) {
		g.setFont(scoreFont);
		g.setColor(Color.GRAY);
		g.drawString("" + playerScore / 100, 20, 30);
	}

	private void renderPlayer(Graphics g) {
		for(Rat r: rats){
			if(player.getRect().intersects(r.getRect())) {
				player.isAlive = false;
			}
		}
		if(player.getDir()) {
			player_width = player.getWidth();
			player_x = (int) player.getX();
		} else {
			player_width = - player.getWidth();
			player_x = (int) player.getX() - player_width;
		}
		
		if (player.isGrounded()) {
			if(!player.isRunning() && !player.isSmacking()) {
				Resources.standAnim.render(g, player_x, (int) player.getY(), player_width, player.getHeight());
			} else if(player.isSmacking()) {
				Resources.smackAnim.render(g, player_x, (int) player.getY(), player_width, player.getHeight());
				g.setColor(Resources.purple);
				//g.drawRect(player.getHammerRect().x, player.getHammerRect().y, player.getHammerRect().width, player.getHammerRect().height);
			} else {
				Resources.runAnim.render(g, player_x, (int) player.getY(), player_width, player.getHeight()); 
			}
		} else {
			g.drawImage(Resources.jump, (int) player_x, (int) player.getY(), player_width, player.getHeight(), null);
		}
		
		g.setColor(Resources.purple);
		//g.drawRect((int) (player.getX() + player.getWidth() / 2), (int) player.getY(), 10, player.getHeight()); // Debugging Collision 
		
		
	}
	
	private void renderRats(Graphics g) {
		for(Rat r: rats) {
			if(!r.getDir()) {
				rat_width = r.getWidth();
				rat_x = (int) r.getX();
			} else {
				rat_width = - r.getWidth();
				rat_x = (int) r.getX() - rat_width;
			}
			Resources.ratAnim.render(g, rat_x, (int) r.getY(), rat_width, r.getHeight());
			g.setColor(Resources.rectRed);
			//g.drawRect((int) r.getX(), (int) (r.getY() + r.getHeight() / 2), r.getWidth() - 20, r.getHeight() / 2); // Debugging Collision
		}
	}
	
	private void renderSun(Graphics g) {
		g.setColor(Color.orange);
		g.fillOval(715, -85, 170, 170);
		g.setColor(Color.yellow);
		g.fillOval(725, -75, 150, 150);
	}
	private void renderClouds(Graphics g) {
		g.drawImage(Resources.cloud1, (int) cloud.getX(), (int) cloud.getY(),
				100, 60, null);
		g.drawImage(Resources.cloud2, (int) cloud2.getX(), (int) cloud2.getY(),
				100, 60, null);
	}

	@Override
	public void onClick(MouseEvent e) {
	}

	@Override
	public void onKeyPress(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			player.jump();
		} else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			player.move_left();
		} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			player.move_right();
		} else if (e.getKeyCode() == KeyEvent.VK_D) {
			player.turn_right();
			player.smack();
		} else if (e.getKeyCode() == KeyEvent.VK_A) {
			player.turn_left();
			player.smack();
		}
	}

	@Override
	public void onKeyRelease(KeyEvent e) {
		if(e.getKeyCode() != KeyEvent.VK_SPACE) player.stop_moving();
	}
}