package com.rolledback.editor;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import com.rolledback.game.Main;
import com.rolledback.tiles.Base;
import com.rolledback.tiles.Forest;
import com.rolledback.tiles.Lake;
import com.rolledback.tiles.Mountain;
import com.rolledback.tiles.Plains;
import com.rolledback.tiles.Tile;

@SuppressWarnings("serial")

public class Editor extends Applet implements MouseListener, KeyListener {
	
	Tile world[][];
	Rectangle grid[][];
	Image plains1, mountains1, forest1, lake, base_team1, base_team2;
	MediaTracker tr;
	int base1, base2;
	final int numRow = 7;
	final int numCol = 12;
	final int gridSize = 100;
	String seed;
	int editMode;
	
	public void init() {
		seed = "0000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000";
		editMode = 0;
		base1 = -1;
		base2 = -1;
		setFocusable(true);
		try { 
			declareImages();
		}
		catch(Exception e) {}
		world = new Tile[numCol][numRow];
		grid = new Rectangle[numCol][numRow];
		
		seed = "";
		for(int x = 0; x < numRow * numCol; x++) {
			seed += "0";
		}		
		addMouseListener(this);
		addKeyListener(this);
	}
	
	public void interpretSeed() {
		int counter = 0;
		for (int col = 0; col < numCol; col++)
			for (int row = 0; row < numRow; row++)
			{	 
	        	 grid[col][row] = new Rectangle(gridSize * col, gridSize * row, gridSize, gridSize);
	        	 	        	 
	        	 if(seed.charAt(counter) == '0') {
	        		 world[col][row] = new Plains();
	        			world[col][row].setImage(0);
	        	 }
	        	 if(seed.charAt(counter) == '1') {  
		        	world[col][row] = new Mountain();
		        	world[col][row].setImage(0);
	        	 }
	        	 if(seed.charAt(counter) == '2') {
		        	 world[col][row] = new Forest();
		        	 world[col][row].setImage(0);
	        	 }
	        	 if(seed.charAt(counter) == '3') {
	        		 world[col][row] = new Lake();
	        		 world[col][row].setImage(0);
		         }	
	        	 if(seed.charAt(counter) == '4') {
	        		 world[col][row] = new Base(col, row);
	        		 world[col][row].setImage(0);
	        	 }
	        	 counter++;
	         }
	}
	
	public void paint(Graphics g) {
		interpretSeed();
		for (int col = 0; col < numCol; col++)
			for (int row = 0; row < numRow; row++) {
	        	 if(world[col][row].getType().equals("plains")) 
	        			 g.drawImage(plains1, gridSize * col, gridSize * row, gridSize, gridSize, this);
	        	 
	        	 if(world[col][row].getType().equals("mountain")) 
	        			 g.drawImage(mountains1, gridSize * col, gridSize * row, gridSize, gridSize, this);
	        	 
	        	 if(world[col][row].getType().equals("forest")) 
	        			 g.drawImage(forest1, gridSize * col, gridSize * row, gridSize, gridSize, this);

	        	 if(world[col][row].getType().equals("lake")) 
	        		 if(world[col][row].getImage() == 0)
	        			 g.drawImage(lake, gridSize * col, gridSize * row, gridSize, gridSize, this);	
	        	 if(world[col][row].getType().equals("base")) { 	        		 
		        		if(col == 0) {
		        			 g.drawImage(base_team1, gridSize * col, gridSize * row, gridSize, gridSize, this);
		        		}
		        	 	if(col == numCol - 1) {
		        			 g.drawImage(base_team2, gridSize * col, gridSize * row, gridSize, gridSize, this);
		        	 	}
		        	 }
	         }
		new Main(null).drawGrid(g, 100);
		g.setColor(Color.RED);
		g.setFont(new Font("Custom", Font.BOLD, 18));
		if(editMode == 0)
			g.drawString("Tile editing mode", 10, 20);
		else
			g.drawString("Base editing mode", 10, 20);
	}
	
	public void declareImages() throws IOException {
		tr = new MediaTracker(this);
				
		plains1 = ImageIO.read(Editor.class.getClassLoader().getResource("plains.png"));
		tr.addImage(plains1, 0);
		
		mountains1 = ImageIO.read(Editor.class.getClassLoader().getResource("mountains.png"));
		tr.addImage(mountains1, 1);
		
		forest1 = ImageIO.read(Editor.class.getClassLoader().getResource("forest.png"));
		tr.addImage(forest1, 2);
		
		lake = ImageIO.read(Editor.class.getClassLoader().getResource("lake.png"));
		tr.addImage(lake, 3);

		base_team1 = ImageIO.read(Editor.class.getClassLoader().getResource("base_team1.png"));
		tr.addImage(base_team1, 4);

		base_team2 = ImageIO.read(Editor.class.getClassLoader().getResource("base_team2.png"));
		tr.addImage(base_team2, 5);
	}
	
	public boolean validSeed() {
		int counter = 0;;
		for(int x = 0; x < seed.length(); x++)
			if(seed.charAt(x) == '4')
				counter++;
		return counter == 2;
	}

	public String genSeed()	{
		String seed = "";
		Random random = new Random();
		for(int x = 0; x < numCol * numRow; x++) {
			int temp = random.nextInt(10);
			if(temp < 6)
				seed += Integer.toString(0);
			if(temp > 5 && temp < 9)
				seed += Integer.toString(2);
			if(temp > 8)
				seed += Integer.toString(1);			
		}
		char[] sepSeed = seed.toCharArray();
		
		for(int y = 0; y < 2; y++)
			sepSeed[random.nextInt(seed.length())] = '3';		
		
		seed = new String(sepSeed);
		//System.out.println(seed);
		return seed;
	}
	
	public void mouseClicked(MouseEvent e) {
			if(e.getModifiers() != MouseEvent.BUTTON3_MASK) {
				for (int col = 0; col < numCol; col++)
					for (int row = 0; row < numRow; row++) {
						if(grid[col][row].contains(e.getX(), e.getY())) {
							char[] sepSeed = seed.toCharArray();
							
							if(editMode == 0) {
								String temp = Character.toString(seed.charAt((col * numRow) + row));
								int current = Integer.parseInt(temp);
								if(current < 3)
									sepSeed[(col * numRow) + row] = Integer.toString(current + 1).toCharArray()[0];
								else
									sepSeed[(col * numRow) + row] = '0';
							}
							
							else {
								if(col == 0) {
									int temp = base1;
									sepSeed[(col * numRow) + row] = Integer.toString(4).toCharArray()[0];
									if(temp != -1)
										sepSeed[temp] = Integer.toString(0).toCharArray()[0];
									base1 = (col * numRow) + row;
								}
								if(col == numCol - 1) {
									int temp = base2;
									sepSeed[(col * numRow) + row] = Integer.toString(4).toCharArray()[0];
									if(temp != -1)
										sepSeed[temp] = Integer.toString(0).toCharArray()[0];
									base2 = (col * numRow) + row;
								}
							}
							seed = new String(sepSeed);
						}
					}
			}
		else {
			if(validSeed()) {
				StringSelection strsel = new StringSelection(seed);
				Clipboard clbrd = Toolkit.getDefaultToolkit().getSystemClipboard();
				clbrd.setContents(strsel, strsel);
				JOptionPane.showMessageDialog(null, "Seed copied to clipboard.");					
			}
			else
				JOptionPane.showMessageDialog(null, "Map not suitable for export.");	
		}
		repaint();
	}
	
	public void keyTyped(KeyEvent e) {
		char c = e.getKeyChar();
		char[] sepSeed = seed.toCharArray();
		if(editMode == 0) {
			if(c == 'p')
				for(int x = 0; x < sepSeed.length; x++) {
					sepSeed[x] = '0';
				}	
			if(c == 'f')
				for(int x = 0; x < sepSeed.length; x++) {
					sepSeed[x] = '2';
				}	
			if(c == 'm')
				for(int x = 0; x < sepSeed.length; x++) {
					sepSeed[x] = '1';
				}	
			if(c == 'l')
				for(int x = 0; x < sepSeed.length; x++) {
					sepSeed[x] = '3';
				}	
			seed = new String(sepSeed);
		}
		if(c == ' ')
			if(editMode == 0)
				editMode = 1;
			else
				editMode = 0;
		if(c == 'r')
			seed = genSeed();
		if(c == 'h')
			JOptionPane.showMessageDialog(null, "Help\np: fill plains\nf: fill forest\nm: fill mountains\nl: fill lake\nr: random map\nspace: change edit mode\nright click: export map");
		repaint();
	}

	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
	public void keyPressed(KeyEvent arg0) {}
	public void keyReleased(KeyEvent arg0) {}

	
}
