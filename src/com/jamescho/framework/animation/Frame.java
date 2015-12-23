package com.jamescho.framework.animation;

import java.awt.Image;

public class Frame {
	private Image image;
	private double duration;

	public Frame(Image image, double duration) {
		this.image = image;
		this.duration = duration;
	}

	public double getDuration() {
		return duration;
	}

	public Image getImage() {
		return image;
	}
}