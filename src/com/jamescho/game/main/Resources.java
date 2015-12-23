package com.jamescho.game.main;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import com.jamescho.framework.animation.Animation;
import com.jamescho.framework.animation.Frame;

public class Resources {

	public static BufferedImage welcome, iconimage, block, cloud1, cloud2,
			duck, grass, stand, jump, run1, run2, run3, run4, run5, run6, selector, rat1, rat2, rat3, rat4,
			smack1, smack2, smack3, smack4, smack5, smack6, smack7, smack8;
	
	public static AudioClip hit, onjump, bonk, smack_sound, activated;
	public static Color skyBlue, rectRed, purple;
	public static Animation runAnim, standAnim, ratAnim, smackAnim;

	public static void load() {
		welcome = loadImage("welcome.png");
		iconimage = loadImage("iconimage.png");
		block = loadImage("block.png");
		cloud1 = loadImage("cloud1.png");
		cloud2 = loadImage("cloud2.png");
		rat1 = loadImage("rat1.png");
		rat2 = loadImage("rat2.png");
		rat3 = loadImage("rat_flies3.png");
		rat4 = loadImage("rat_flies4.png");
		duck = loadImage("duck.png");
		grass = loadImage("grass.png");
		stand = loadImage("hero_stand.png");
		jump = loadImage("run_anim1.png");
		 
		run1 = loadImage("run_anim1.png");
		run2 = loadImage("run_anim2.png");
		run3 = loadImage("run_anim3.png");
		run4 = loadImage("run_anim4.png");
		run5 = loadImage("run_anim5.png");
		run6 = loadImage("run_anim6.png");
		
		smack1 = loadImage("hero_bat1.png");
		smack2 = loadImage("hero_bat2.png");
		smack3 = loadImage("hero_bat3.png");
		smack4 = loadImage("hero_bat4.png");
		smack5 = loadImage("hero_bat5.png");
		smack6 = loadImage("hero_bat6.png");
		smack7 = loadImage("hero_bat7.png");
		smack8 = loadImage("hero_bat7.png");
		
		
		selector = loadImage("selector.png");
		hit = loadSound("hit.wav");
		onjump = loadSound("jump.wav");
		bonk = loadSound("zap1.wav");
		smack_sound = loadSound("zap2.wav");
		activated = loadSound("zap1.wav");
		
		
		skyBlue = new Color(208, 244, 247);
		rectRed = new Color(255, 0, 0);
		purple = new Color(128, 0, 128);

		Frame f_stand = new Frame(stand, 0.1f);
			
		Frame f1 = new Frame(run1, 0.03f);
		Frame f2 = new Frame(run2, 0.03f);
		Frame f3 = new Frame(run3, 0.03f);
		Frame f4 = new Frame(run4, 0.03f);
		Frame f5 = new Frame(run5, 0.03f);
		Frame f6 = new Frame(run6, 0.03f);

		Frame r1 = new Frame(rat1, 0.05f);
		Frame r2 = new Frame(rat2, 0.05f);
		//Frame r3 = new Frame(rat3, 0.05f);
		//Frame r4 = new Frame(rat4, 0.05f);
		
		Frame s1 = new Frame(smack1, 0.04f);
		Frame s2 = new Frame(smack2, 0.04f);
		Frame s3 = new Frame(smack3, 0.03f);
		Frame s4 = new Frame(smack4, 0.03f);
		Frame s5 = new Frame(smack5, 0.03f);
		Frame s6 = new Frame(smack6, 0.03f);
		Frame s7 = new Frame(smack5, 0.03f);
		Frame s8 = new Frame(smack6, 0.03f);

		
		runAnim = new Animation(f1, f2, f3, f4, f5, f6);
		standAnim = new Animation(f_stand);
		
		ratAnim = new Animation(r1, r2);
		
		smackAnim = new Animation(s1, s2, s3, s4, s5, s6, s7, s8);
		
	}

	private static AudioClip loadSound(String filename) {
		URL fileURL = Resources.class.getResource("/resources/" + filename);
		return Applet.newAudioClip(fileURL);
	}

	private static BufferedImage loadImage(String filename) {
		BufferedImage img = null;
		try {
			img = ImageIO.read(Resources.class
					.getResourceAsStream("/resources/" + filename));
		} catch (IOException e) {
			System.out.println("Error while reading: " + filename);
			e.printStackTrace();
		}
		return img;
	}
}