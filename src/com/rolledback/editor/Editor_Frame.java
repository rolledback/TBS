package com.rolledback.editor;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

public class Editor_Frame {
	public static void main(String[] args) { 
		JFrame frame = new JFrame("TBS Editor");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1215, 735);

		final Editor applet = new Editor();
		applet.init();
		frame.add(applet);
		frame.setVisible(true);		

		frame.addWindowListener(new WindowAdapter() {
		    public void windowClosing(WindowEvent we) {
		        applet.stop();
		        applet.destroy();
		    }
		});
	}
}
