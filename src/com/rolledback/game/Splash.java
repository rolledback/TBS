package com.rolledback.game;

import java.applet.Applet;
import java.awt.Graphics;
import java.awt.Image;

import javax.imageio.ImageIO;

@SuppressWarnings("serial")
public class Splash extends Applet {
	Image splash;
	public void init() {
		try {
			splash = ImageIO.read(Splash.class.getClassLoader().getResource("splash.png"));
		}
		catch(Exception e) {}
	}
	public void paint(Graphics g) {		
		g.drawImage(splash, 0, 0, 1200, 900, this);
	}

}
