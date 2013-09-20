package com.rolledback.game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.ImageObserver;

import com.rolledback.framework.Team;
import com.rolledback.tiles.Tile;
import com.rolledback.units.Unit;

public class Dashboard {
	
	private int xSize;
	private int ySize;
	private  ImageObserver io;
	
	public Dashboard(int x, int y, ImageObserver io) {
		this.io = io;
		xSize = x;
		ySize = y;	
	}
	
	public void drawDash(Graphics g) {			
		Color dash = new Color(120, 120, 120);		
		g.setColor(dash);
		g.fillRect(0, ySize - 200, xSize, ySize);
		g.setColor(Color.BLACK);		
		g.drawLine(0, ySize - 200, xSize, ySize - 200);
	}
	
	public void drawTeamBoxes(Graphics g, Team currentTeam, Team one, Team two, int turn) {
		Color team1 = new Color(202, 0, 0);
		g.setColor(team1);
		g.fillRoundRect(10, ySize - 150, 150, 50, 15, 15);

		Color team2 = new Color(65, 84, 142);
		g.setColor(team2);
		g.fillRoundRect(10, ySize - 85, 150, 50, 15, 15);
		
		g.setColor(Color.BLACK);
		g.drawRoundRect(10, ySize - 150, 150, 50, 15, 15);
		g.drawRoundRect(10, ySize - 85, 150, 50, 15, 15);
		
		g.setFont(new Font("Custom", Font.BOLD, 22));
		g.drawString("Current team: " + currentTeam.toString() + ", Turn: " + turn, 10, 725);
		
		g.setFont(new Font("Custom", Font.BOLD, 18));		
		g.drawString("Units Left: " + one.getTeamSize(), 20, 780);
		g.drawString("Units Left: " + two.getTeamSize(), 20, 845);
	}
	
	public void drawUnitBox(Graphics g) {
		Color unitBox = new Color(159, 159, 159);
		g.setColor(unitBox);
		g.fillRoundRect(200, ySize - 160, 140, 140, 20, 20);
		
		g.setColor(Color.BLACK);
		g.drawRoundRect(200, ySize - 160, 140, 140, 20, 20);
	}
	
	public void drawEndButton(Graphics g) {
		g.setColor(Color.RED);
		g.fillRoundRect(xSize - 250, ySize - 150, 200, 100, 50, 50);
		
		g.setColor(Color.BLACK);
		g.setFont(new Font("Custom", Font.BOLD, 38));
		g.drawString("END TURN", xSize - 245, ySize - 85);
		g.drawRoundRect(xSize - 250, ySize - 150, 200, 100, 50, 50);
	}
	
	public void displayUnitInfo(Graphics g, Image unit, int tankSize, Tile world[][], Unit selected) {	
		g.setFont(new Font("Custom", Font.BOLD, 18));	
		int bonus = selected.getDefBonus();
		g.drawString("Team: " + selected.getOwner().toString(), 350, 755);
		g.drawString("Type: " + selected.toString(), 350, 785);
		g.drawString("Health: " + selected.getHp() + "/" + selected.getMaxHP(), 350, 815);
		g.drawString("Defense: " + Integer.toString(selected.getDefense() + bonus), 350, 845);
		g.drawString("Attack power: " + selected.getAttackMin() + " - " + selected.getAttackMax(), 350, 875);
		g.drawImage(unit, 205, 790, tankSize + 100, tankSize + 100, io);
	
	}
	
	public void defaultDisplay(Graphics g) {
		g.setFont(new Font("Custom", Font.BOLD, 18));
		g.drawString("Team: ", 350, 755);
		g.drawString("Type: ", 350, 785);
		g.drawString("Health: ", 350, 815);
		g.drawString("Defense: ", 350, 845);
		g.drawString("Attack power: ", 350, 875);
	}
}
