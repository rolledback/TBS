package com.rolledback.tiles;

import com.rolledback.framework.Team;
import com.rolledback.units.Unit;

public class Base extends Tile {
	
	private int hp;
	private Team owner;
	private int row, col;
	
	public Base(int x, int y) {
		hp = 100;
		type = "base";
		landPassable = true;
		row = y;
		col  = x;
	}	

	public boolean unitIsOn(Unit unit, int teamSize) {
		if(unit.getxCord() == col && unit.getyCord() == row && unit.isAlive())
				return true;
		return false;
	}

	public String toString() {
		return owner + "\nRemaning hp: " + hp;
	}
	
	public int getHp() {
		return hp;
	}

	public void setHp(int hp) {
		this.hp = hp;
	}

	public Team getOwner() {
		return owner;
	}

	public void setOwner(Team owner) {
		this.owner = owner;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getCol() {
		return col;
	}

	public void setCol(int col) {
		this.col = col;
	}

}
