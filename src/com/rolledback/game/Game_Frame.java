package com.rolledback.game;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Game_Frame {

	public static void main(String[] args) { 
		JFrame frame = new JFrame("TBS");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1200, 900);
		final Main applet;
		
		final Splash splashScreen = new Splash();
		splashScreen.init();		
		frame.add(splashScreen);
		frame.setVisible(true);
		frame.setSize(1200 + frame.getInsets().right + frame.getInsets().left, 900 + frame.getInsets().top + frame.getInsets().bottom);
		delay(2000);
		
		String temp = JOptionPane.showInputDialog("Enter custom map seed if you have one:");
		if(temp != null && validSeed(temp)) {
			JOptionPane.showMessageDialog(null, "Valid seed entered.");
			applet = new Main(temp);
		}
		else {
			JOptionPane.showMessageDialog(null, "Invalid seed entered.");
			applet = new Main(null);
		}
		
		applet.init();
		frame.remove(splashScreen);
		frame.add(applet);
		frame.setVisible(true);	

		frame.addWindowListener(new WindowAdapter() {
		    public void windowClosing(WindowEvent we) {
		        applet.stop();
		        applet.destroy();
		    }
		});		
	}
	
	public static boolean validSeed(String seed) {
		int counter = 0;;
		for(int x = 0; x < seed.length(); x++)
			if(seed.charAt(x) == '4')
				counter++;
		return counter == 2;
	}
	
	public static void delay(int n)
	{
		long startDelay = System.currentTimeMillis();
		long endDelay = 0;
		while (endDelay - startDelay < n)
			endDelay = System.currentTimeMillis();	
	}
}
