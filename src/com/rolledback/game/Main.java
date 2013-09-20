package com.rolledback.game;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import com.rolledback.framework.Team;
import com.rolledback.units.Unit;

@SuppressWarnings("serial")

public class Main extends Applet implements MouseListener, KeyListener {
	
	Rectangle grid[][];
	ArrayList<Rectangle> buttons;
	
	boolean activated[][];
	boolean occupied[][];
	boolean moveArray[][];
	boolean attackArray[][];
	
	AudioClip tank_fire, tank_destroyed;
	Image tiles[];
	Image units[];	
	MediaTracker tr;
	
	Game test;
	Team currentTeam, original;
	String seed, customSeed;
	Unit selectedUnit, enemyUnit;
	Dashboard dash;
	
	int activatedX, activatedY, nextX, nextY, oneLeft, twoLeft, turn;
	boolean unitSelected, winner;
	
	final int numRow = 7;
	final int numCol = 12;
	final int teamSize = 8;
	final int gridSize = 100;
	final int tankSize = 135;
	
	public Main(String s) {
		customSeed = s;
	}
	
	public void init() {
		winner = false;
		buttons = new ArrayList<Rectangle>();
		buttons.add(new Rectangle(900, 750, 200, 100));
		
		tiles = new Image[9];
		units = new Image[8];
		try {
			declareImages();
		}
		catch(Exception e) {e.printStackTrace();}
		
		dash = new Dashboard(1200, 900, this);
		
		grid = new Rectangle[numCol][numRow];
		activated = new boolean[numCol][numRow];
		occupied = new boolean[numCol][numRow];
		moveArray = new boolean[numCol][numRow];
		attackArray = new boolean[numCol][numRow];		

		test = new Game(occupied, customSeed);	
		if(Math.round(Math.random()) == 0) {
			currentTeam = test.getOne();
			original = test.getOne();
		}
		else {
			currentTeam = test.getTwo();
			original = test.getTwo();
		}
				
		seed = test.getSeed();
		turn = 1;
		
		for (int col = 0; col < numCol; col++)
			for (int row = 0; row < numRow; row++) {
				grid[col][row] = new Rectangle(gridSize * col, gridSize * row, gridSize, gridSize);	 
				occupied[col][row] = false;
				attackArray[col][row] = false;
				moveArray[col][row] = false;
			}
		addMouseListener(this);
		addKeyListener(this);
		}
	
	public boolean hasWinner() {
		return test.getOne().getTeamSize() == 0 || test.getTwo().getTeamSize() == 0;
	}
	
	public String winner() {
		if(test.getOne().getTeamSize() == 0)
			return "Team Two";
		else
			return "Team One";
	}
	
	public void declareImages() throws IOException {
		tank_fire = newAudioClip(Main.class.getClassLoader().getResource("tank_fire.wav"));
		tank_destroyed = newAudioClip(Main.class.getClassLoader().getResource("tank_destroyed.wav"));		
		
		tiles[0] = ImageIO.read(Main.class.getClassLoader().getResource("plains.png"));		
		tiles[1] = ImageIO.read(Main.class.getClassLoader().getResource("mountains.png"));
		tiles[2] = ImageIO.read(Main.class.getClassLoader().getResource("forest.png"));		
		tiles[3] = ImageIO.read(Main.class.getClassLoader().getResource("plains_2.png"));			
		tiles[4] = ImageIO.read(Main.class.getClassLoader().getResource("mountains_2.png"));		
		tiles[5] = ImageIO.read(Main.class.getClassLoader().getResource("forest_2.png"));
		tiles[6] = ImageIO.read(Main.class.getClassLoader().getResource("lake.png"));
		tiles[7] = ImageIO.read(Main.class.getClassLoader().getResource("base_team1.png"));
		tiles[8] = ImageIO.read(Main.class.getClassLoader().getResource("base_team2.png"));
		
		units[0] = ImageIO.read(Main.class.getClassLoader().getResource("tank_team1_right.png"));	
		units[1] = ImageIO.read(Main.class.getClassLoader().getResource("tank_team1_left.png"));
		units[2] = ImageIO.read(Main.class.getClassLoader().getResource("tank_team2_left.png"));
		units[3] = ImageIO.read(Main.class.getClassLoader().getResource("tank_team2_right.png"));
		units[4] = ImageIO.read(Main.class.getClassLoader().getResource("tank_destroyer_team1_right.png"));	
		units[5] = ImageIO.read(Main.class.getClassLoader().getResource("tank_destroyer_team1_left.png"));
		units[6] = ImageIO.read(Main.class.getClassLoader().getResource("tank_destroyer_team2_left.png"));
		units[7] = ImageIO.read(Main.class.getClassLoader().getResource("tank_destroyer_team2_right.png"));		
	}
	
	public void paint(Graphics g) {			
		Cartographer.drawTiles(g, this, test, tiles);  	 
		g.setColor(Color.RED);		
		for(int x = 0; x < teamSize * 2; x++) {			
			if(test.getGlobalUnits()[x].isAlive()) {
				occupied[test.getGlobalUnits()[x].getxCord()][test.getGlobalUnits()[x].getyCord()] = true;
				Cartographer.drawUnit(g, this, test.getGlobalUnits()[x], test, units);
				if(test.getGlobalUnits()[x].isMoved())
					g.drawRect(test.getGlobalUnits()[x].getxCord() * gridSize, test.getGlobalUnits()[x].getyCord() * gridSize, gridSize, gridSize);
			}
		}
		drawHealthBars(g);
		if(!hasWinner()) {
			if(!unitSelected) {
				drawActivated(g);
			}
		}
		else {
			g.setColor(Color.RED);
			g.setFont(new Font("Custom", Font.BOLD, 72));
			g.drawString(winner() + " has won!", 200, 200);
		}
		drawDashBoard(g, selectedUnit);			
	}   
	
	public void debugOutput() {
		System.out.println("activatedX " + activatedX);
		System.out.println("activatedY " + activatedY);
		System.out.println("unitSelected " + unitSelected);
		if(selectedUnit != null)
			System.out.println("selectedUnit " + selectedUnit + " " + selectedUnit.getxCord() + " " +  + selectedUnit.getyCord());
		else
			System.out.println("selectedUnit " + selectedUnit);
		if(enemyUnit != null)
			System.out.println("enemyUnit " + enemyUnit + " " + enemyUnit.getxCord() + " " + enemyUnit.getyCord() + " " + enemyUnit.getHp());
		else
			System.out.println("enemyUnit " + enemyUnit);
		System.out.println("nextX " + nextX);
		System.out.println("nextY " + nextY);
		System.out.println();
		for (int col = 0; col < numCol; col++) {
			for (int row = 0; row < numRow; row++) {
				if(occupied[col][row])
					System.out.println(col + " " + row + " " + occupied[col][row] + " occupied");
			}
		}
		System.out.println("--------------------------------------------------------------");
	}
	
	public void drawDashBoard(Graphics g, Unit selected) {
		dash.drawDash(g);
		dash.drawTeamBoxes(g, currentTeam, test.getOne(), test.getTwo(), turn);
		dash.drawUnitBox(g);
		dash.drawEndButton(g);
		try {	
			if(selected.getOwner().equals(test.getOne())) {
				if(selected.getType().equals("tank"))
					dash.displayUnitInfo(g, units[0], tankSize, test.getWorld(), selected);	
				else
					dash.displayUnitInfo(g, units[4], tankSize, test.getWorld(), selected);	
			}
			
			else if(selected.getOwner().equals(test.getTwo())) {
				if(selected.getType().equals("tank"))
					dash.displayUnitInfo(g, units[3], tankSize, test.getWorld(), selected);	
				else
					dash.displayUnitInfo(g, units[7], tankSize, test.getWorld(), selected);	
			}
		}
		catch(Exception e) {
			dash.defaultDisplay(g);
		}
		
	}	
	
	public void drawHealthBars(Graphics g) {
		for(int x = 0; x < test.getGlobalUnits().length; x++) {
			if(test.getGlobalUnits()[x].isAlive() && test.getGlobalUnits()[x].getHp() < test.getGlobalUnits()[x].getMaxHP()) {
				g.setColor(Color.RED);
				g.fillRect((100 * test.getGlobalUnits()[x].getxCord()) + 10, (100 * test.getGlobalUnits()[x].getyCord()) + 10, 80, 10);
				g.setColor(Color.GREEN);
				g.fillRect((100 * test.getGlobalUnits()[x].getxCord()) + 10, (100 * test.getGlobalUnits()[x].getyCord()) + 10, (80 * test.getGlobalUnits()[x].getHp()) / test.getGlobalUnits()[x].getMaxHP(), 10);
			}				
		}
	}	
	
	public void drawActivated(Graphics g) {
		for (int col = 0; col < numCol; col++) {
			for (int row = 0; row < numRow; row++) {
	        	if(activated[col][row]) {
	        		g.setColor(Color.black);
	        		g.drawOval(gridSize * col, gridSize * row, gridSize, gridSize);
        			activatedX = col;
        			activatedY = row;
        		}	
			}
		}
		if(activatedX != -1 && !activated[activatedX][activatedY]) {
			activatedX = -1;
			activatedY = -1;
		}
		unitSelected = findSelectedUnit();
		if(unitSelected && selectedUnit != null && !selectedUnit.isMoved())
			drawMoveSpots(g);
	}	
	
	public boolean findSelectedUnit() {
		for(int x = 0; x < test.getGlobalUnits().length; x++) {
			if(test.getGlobalUnits()[x].getxCord() == activatedX && test.getGlobalUnits()[x].getyCord() == activatedY && test.getGlobalUnits()[x].isAlive()) {
					selectedUnit = test.getGlobalUnits()[x];
					return test.getGlobalUnits()[x].getOwner().equals(currentTeam);
				}
		}
		return false;
	}	
	
	public void baseRepairCheck() {
		for(int x = 0; x < teamSize; x++) {
			if(currentTeam.getBase().unitIsOn(currentTeam.getArmy()[x], teamSize))
				currentTeam.getArmy()[x].setHp(currentTeam.getArmy()[x].getHp() + 20);
		}	
	}

	public void drawMoveSpots(Graphics g) {
		Color moveSet = new Color((float).0, (float).0, (float)1, (float).65);
		g.setColor(moveSet);
		moveArray = selectedUnit.checkAround(activatedX, activatedY, selectedUnit.getMoveRange(), true);
			
		for (int col = 0; col < numCol; col++)
			for (int row = 0; row < numRow; row++) {
				if(moveArray[col][row])
					g.fillRect(gridSize * col, gridSize * row, gridSize, gridSize);
			}
		drawAttackSpots(g);
		activatedX = -1;
		activatedY = -1;
	}		
	
	public void drawAttackSpots(Graphics g) {
		Color attackSet = new Color((float)1, (float).0, (float).0, (float).65);
		g.setColor(attackSet);
		if(currentTeam == test.getOne())
			attackArray = selectedUnit.checkValidAttackSpot(moveArray, test.getTwo());
		else
			attackArray = selectedUnit.checkValidAttackSpot(moveArray, test.getOne());
		
		for (int col = 0; col < numCol; col++)
			for (int row = 0; row < numRow; row++) {
				if(attackArray[col][row])
					moveArray[col][row] = false;
				if(attackArray[col][row]) {
					g.fillRect(gridSize * col, gridSize * row, gridSize, gridSize); 
					moveArray[col][row] = false;
				}
			}
	}	
	
	public void findEnemy(int col, int row) {
		for(int x = 0; x < test.getGlobalUnits().length; x++) {
			if(!test.getGlobalUnits()[x].getOwner().equals(currentTeam) && test.getGlobalUnits()[x].getxCord() == col && test.getGlobalUnits()[x].getyCord() == row && test.getGlobalUnits()[x].isAlive())
				enemyUnit = test.getGlobalUnits()[x];
		}
		findAttackSpot(0);
	}		
	
	public void findAttackSpot(int x) {
		boolean moveFound = false;
		if(!isNextTo(enemyUnit.getxCord(), enemyUnit.getyCord(), selectedUnit.getxCord(), selectedUnit.getyCord())) {			
			int minMove = ((Math.abs(enemyUnit.getxCord() - selectedUnit.getxCord()) + Math.abs(enemyUnit.getyCord() - selectedUnit.getyCord())) - 1) - x;			
			int choiceOne = Math.abs(selectedUnit.getxCord() - (enemyUnit.getxCord() - 1)) + Math.abs(selectedUnit.getyCord() - enemyUnit.getyCord());
			int choiceTwo = Math.abs(selectedUnit.getxCord() - (enemyUnit.getxCord() + 1)) + Math.abs(selectedUnit.getyCord() - enemyUnit.getyCord());
			int choiceThree = Math.abs(selectedUnit.getxCord() - enemyUnit.getxCord()) + Math.abs(selectedUnit.getyCord() - (enemyUnit.getyCord() - 1));
			int choiceFour = Math.abs(selectedUnit.getxCord() - enemyUnit.getxCord()) + Math.abs(selectedUnit.getyCord() - (enemyUnit.getyCord() + 1));
				
			if(enemyUnit.getxCord() != 0 && moveArray[enemyUnit.getxCord() - 1][enemyUnit.getyCord()] && choiceOne <= minMove) {
				nextX = enemyUnit.getxCord() - 1;
				nextY = enemyUnit.getyCord();
				moveFound = true;
			}
			if(enemyUnit.getxCord() != numCol - 1 && moveArray[enemyUnit.getxCord() + 1][enemyUnit.getyCord()] && choiceTwo <= minMove) {
				nextX = enemyUnit.getxCord() + 1;
				nextY = enemyUnit.getyCord();
				moveFound =  true;
			}
			if(enemyUnit.getyCord() != 0 && moveArray[enemyUnit.getxCord()][enemyUnit.getyCord() - 1] && choiceThree <= minMove) {
				nextX = enemyUnit.getxCord();
				nextY = enemyUnit.getyCord() - 1;
				moveFound =  true;
			}		
			if(enemyUnit.getyCord() != numRow - 1 && moveArray[enemyUnit.getxCord()][enemyUnit.getyCord() + 1] && choiceFour <= minMove) {
				nextX = enemyUnit.getxCord();
				nextY = enemyUnit.getyCord() + 1; 
				moveFound =  true;
			}
			if(!moveFound) {
				findAttackSpot(x - 1);
			}
		}
		else {
			nextX = selectedUnit.getxCord();
			nextY = selectedUnit.getyCord();
		}
	}
	
	public void finalizeMove() {
		occupied[selectedUnit.getxCord()][selectedUnit.getyCord()] = false;							
		setDirection(nextX);						
		occupied[nextX][nextY] = true;
		selectedUnit.setxCord(nextX);
		selectedUnit.setyCord(nextY);
		selectedUnit.prepareArrays();
		selectedUnit.setMoved(true);
		unitSelected = false;
		resetArrays();
	}
	
	public void setDirection(int x) {
		if(selectedUnit.getOwner().equals(test.getOne())) {
			if(selectedUnit.getxCord() != x) {
				if(selectedUnit.getxCord() > x)
					selectedUnit.setUsualDirection(false);
				else
					selectedUnit.setUsualDirection(true);
			}
		}
		if(selectedUnit.getOwner().equals(test.getTwo())) {
			if(selectedUnit.getxCord() != x) {
				if(selectedUnit.getxCord() > x)
					selectedUnit.setUsualDirection(true);
				else
					selectedUnit.setUsualDirection(false);
			}
		}
	}
	
	public void handleAttack() {
		int hpBefore = enemyUnit.getHp();
		int dealt = selectedUnit.attack();
		enemyUnit.damaged(dealt);
		if(enemyUnit.getHp() <= 0) {
			tank_destroyed.play();
			if(currentTeam.equals(test.getOne()))
				test.getTwo().setTeamSize(test.getTwo().getTeamSize() - 1);
			else
				test.getOne().setTeamSize(test.getOne().getTeamSize() - 1);
			occupied[enemyUnit.getxCord()][enemyUnit.getyCord()] = false;
		}
		else if(hpBefore > enemyUnit.getHp())
			tank_fire.play();
	}
	
	public void drawGrid(Graphics g, int sep) {	
		g.setColor(Color.black);
		for(int x = 0; x < numCol * gridSize; x += sep)
			g.drawLine(x, 0, x, numRow * 100);
		for(int x = 0; x < numRow * gridSize; x += sep)
			g.drawLine(0, x, numCol * gridSize, x);			
	}
	
	public void resetArrays() {
		try {
		for (int col = 0; col < numCol; col++)
			for (int row = 0; row < numRow; row++) {
				moveArray[col][row] = false;
				attackArray[col][row] = false;
				occupied[col][row] = false;
			}
		}
		catch(Exception e) {}
	}
	
	public boolean isNextTo(int a, int b, int c, int d) {
		if(Math.abs(a - c) == 1 && b == d)
			return true;
		if(Math.abs(b - d) == 1 && a == c)
			return true;
		return false;
	}	
	
	public void endTurn() {
		for(int x = 0; x < teamSize; x++)
			currentTeam.getArmy()[x].setMoved(false);
		if(currentTeam.equals(test.getOne())) 
			currentTeam = test.getTwo();
		else
			currentTeam = test.getOne();
		JOptionPane.showMessageDialog(null, "Turn over.");	
		baseRepairCheck();
		if(original.equals(currentTeam))
			turn++;
			
	}	
	
	public void keyTyped(KeyEvent e) {
		char c = e.getKeyChar();
		if(c == ' ')
			endTurn();
		repaint();
	}
	
	public void mouseClicked(MouseEvent e) {
			for (int col = 0; col < numCol; col++)
				for (int row = 0; row < numRow; row++) {
					if(grid[col][row].contains(e.getX(), e.getY())) {
						if(unitSelected && moveArray[col][row]) {
							nextX = col;
							nextY = row;
							finalizeMove();
						}
						else if(unitSelected && attackArray[col][row]) {
							findEnemy(col, row);
							finalizeMove();	
							handleAttack();
						}
						else if(unitSelected && !moveArray[col][row] && !attackArray[col][row]) {
							activated[col][row] = !activated[col][row]; 
							selectedUnit.prepareArrays();
							unitSelected = false;
							selectedUnit = null;
						}
						else {
							activated[col][row] = !activated[col][row];
							unitSelected = false;
							selectedUnit = null;
						}						
					}
					else
						activated[col][row] = false;     	 
			}
		for(int x = 0; x < buttons.size(); x++)
			if(buttons.get(x).contains(e.getX(), e.getY())) {
				if(x == 0)
					endTurn();
			}
		resetArrays();
		unitSelected = false;
		repaint();
	}
	
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
	
	public void keyPressed(KeyEvent arg0) {}
	public void keyReleased(KeyEvent arg0) {}
}
