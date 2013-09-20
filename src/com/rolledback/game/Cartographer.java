package com.rolledback.game;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.ImageObserver;

import com.rolledback.tiles.Base;
import com.rolledback.units.Unit;


public class Cartographer {
	
	final static int numRow = 7;
	final static int numCol = 12;
	final static int teamSize = 8;
	final static int gridSize = 100;
	final static int tankSize = 135;
	
	public static void drawTiles(Graphics g, ImageObserver io, Game test, Image tiles[]) {
		for (int col = 0; col < numCol; col++)
			for (int row = 0; row < numRow; row++) {
	        	 if(test.getWorld()[col][row].getType().equals("plains")) 
	        		 if(test.getWorld()[col][row].getImage() == 0)
	        			 g.drawImage(tiles[0], gridSize * col, gridSize * row, gridSize, gridSize, io);
	        		 else
	        			 g.drawImage(tiles[3], gridSize * col, gridSize * row, gridSize, gridSize, io);
	        	 
	        	 if(test.getWorld()[col][row].getType().equals("mountain")) 
	        		 if(test.getWorld()[col][row].getImage() == 0)
	        			 g.drawImage(tiles[1], gridSize * col, gridSize * row, gridSize, gridSize, io);
	        		 else
	        			 g.drawImage(tiles[4], gridSize * col, gridSize * row, gridSize, gridSize, io);
	        	 
	        	 if(test.getWorld()[col][row].getType().equals("forest")) 
	        		 if(test.getWorld()[col][row].getImage() == 0)
	        			 g.drawImage(tiles[2], gridSize * col, gridSize * row, gridSize, gridSize, io);
	        		 else
	        			 g.drawImage(tiles[5], gridSize * col, gridSize * row, gridSize, gridSize, io);
	        	
	        	 if(test.getWorld()[col][row].getType().equals("lake")) 
	        		 if(test.getWorld()[col][row].getImage() == 0)
	        			 g.drawImage(tiles[6], gridSize * col, gridSize * row, gridSize, gridSize, io);	        	 

	        	 if(test.getWorld()[col][row].getType().equals("base")) { 	        		 
	        		if(col == 0) {
	        			 g.drawImage(tiles[7], gridSize * col, gridSize * row, gridSize, gridSize, io);
	        	 		 ((Base)test.getWorld()[col][row]).setOwner(test.getOne());
	        	 		 test.getOne().setBase((Base)test.getWorld()[col][row]);
	        		}
	        	 	if(col == numCol - 1) {
	        			 g.drawImage(tiles[8], gridSize * col, gridSize * row, gridSize, gridSize, io);
	        			 ((Base) test.getWorld()[col][row]).setOwner(test.getTwo());
	        			 test.getTwo().setBase((Base)test.getWorld()[col][row]);
	        	 	}
	        	 }
	         }	        
	}
	
	public static void drawUnit(Graphics g, ImageObserver io, Unit current, Game test, Image units[]) {
		if(current.getOwner().equals(test.getOne())) {
			if(current.getType().equals("tank"))
				if(current.isUsualDirection())
					g.drawImage(units[0], (current.getxCord() * gridSize) + 12, (current.getyCord() * gridSize) + 40, tankSize, tankSize, io);
				else
					g.drawImage(units[1], (current.getxCord() * gridSize) + 12, (current.getyCord() * gridSize) + 40, tankSize, tankSize, io);
			else if(current.getType().equals("tank destroyer"))
				if(current.isUsualDirection())
					g.drawImage(units[4], (current.getxCord() * gridSize) + 12, (current.getyCord() * gridSize) + 40, tankSize, tankSize, io);
				else
					g.drawImage(units[5], (current.getxCord() * gridSize) + 12, (current.getyCord() * gridSize) + 40, tankSize, tankSize, io);
		}
		else {
			if(current.getType().equals("tank"))
				if(current.isUsualDirection()) 
					g.drawImage(units[2], (current.getxCord() * gridSize) + 12, (current.getyCord() * gridSize) + 40, tankSize, tankSize, io);
				else
					g.drawImage(units[3], (current.getxCord() * gridSize) + 12, (current.getyCord() * gridSize) + 40, tankSize, tankSize, io);
			else if(current.getType().equals("tank destroyer"))
				if(current.isUsualDirection()) 
					g.drawImage(units[6], (current.getxCord() * gridSize) + 12, (current.getyCord() * gridSize) + 40, tankSize, tankSize, io);
				else
					g.drawImage(units[7], (current.getxCord() * gridSize) + 12, (current.getyCord() * gridSize) + 40, tankSize, tankSize, io);
		}
	}

}
