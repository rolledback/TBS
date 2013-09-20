package com.rolledback.units;

import java.util.Random;

import com.rolledback.framework.Team;
import com.rolledback.tiles.Tile;

public class Unit {
	
	protected int attackMin;
	protected int attackMax;
	protected int defense;
	protected int moveRange;
	protected int hp;
	protected Team owner;
	protected String type;
	protected int xCord;
	protected int yCord;
	protected boolean isUsualDirection;
	protected boolean alive;
	protected boolean[][] valid;
	protected boolean[][] attackValid;
	protected boolean moved;
	protected Tile world[][];
	protected boolean[][] occupied;
	protected int maxHP;
	
	public Unit(Tile world[][], boolean occupied[][]) {
		this.world = world;
		this.occupied = occupied;
		valid = new boolean[this.world.length][this.world[0].length];
		attackValid = new boolean[this.world.length][this.world[0].length];		
		owner = null;
		type = "unit";
		moveRange = 100;
		hp = 100;
		maxHP = 100;
		isUsualDirection = true;
		attackMax = 20;
		attackMin = 10;
		defense = 0;
		alive = true;
		moved = false;
	}
	
	public void prepareArrays() {
		for(int x = 0; x < valid.length; x++) {
			for(int y = 0; y < valid[x].length; y++) {
				valid[x][y] = false;
				attackValid[x][y] = false;
			}
		}
	}
	
	public boolean[][] checkAround(int a, int b, int movesLeft, boolean firstCall) {
		if(!firstCall) {
			if(world[a][b].getType().equals("forest"))
				movesLeft -= 2;
			else						
				movesLeft--;
		}
		if(movesLeft > -1) {
			if(!firstCall)
				valid[a][b] = true;
			try {						
				if(checkSpot(world[a + 1][b], occupied[a + 1][b]))
					checkAround(a + 1, b, movesLeft, false);
			}
			catch(Exception e) {}

			try {
				if(checkSpot(world[a - 1][b], occupied[a - 1][b]))
					checkAround(a - 1, b, movesLeft, false);
			}
			catch(Exception e) {}

			try {
				if(checkSpot(world[a][b + 1], occupied[a][b + 1]))
					checkAround(a, b + 1, movesLeft, false);
			}
			catch(Exception e) {}

			try {						
				if(checkSpot(world[a][b - 1], occupied[a][b - 1])) 
					checkAround(a, b - 1, movesLeft, false);
			}
			catch(Exception e) {}
		}
		return valid;
	}
	
	public boolean[][] checkValidAttackSpot(boolean[][] moveSpots, Team other) {
		moveSpots[xCord][yCord] = true;
		for (int col = 0; col < moveSpots.length; col++)
			for (int row = 0; row < moveSpots[col].length; row++) {
				if(moveSpots[col][row]) {					
					for(int x = 0; x < other.getArmy().length; x++) {
						try {		
							if(other.getArmy()[x].getxCord() == col + 1 && other.getArmy()[x].getyCord() == row && other.getArmy()[x].isAlive())
								attackValid[col + 1][row] = true;
						}
						catch(Exception e) {}

						try {
							if(other.getArmy()[x].getxCord() == col - 1 && other.getArmy()[x].getyCord() == row && other.getArmy()[x].isAlive())
								attackValid[col - 1][row] = true;
						}
						catch(Exception e) {}

						try {
							if(other.getArmy()[x].getxCord() == col && other.getArmy()[x].getyCord() == row + 1 && other.getArmy()[x].isAlive())
								attackValid[col][row + 1] = true;
						}
						catch(Exception e) {}

						try {
							if(other.getArmy()[x].getxCord() == col && other.getArmy()[x].getyCord() == row - 1 && other.getArmy()[x].isAlive())
								attackValid[col][row - 1] = true;
						}
						catch(Exception e) {}
					}
				}
		}		
		moveSpots[xCord][yCord] = false;
		return attackValid;
	}			
	
	public boolean checkSpot(Tile tile, boolean occupied) {
		if(occupied)
			return false;
		if(!tile.isLandPassable())
			return false;
		return true;
	}

	public String toString() {
		return type;
	}
	
	public int getMoveRange() {
		return moveRange;
	}

	public void setMoveRange(int moveRange) {
		this.moveRange = moveRange;
	}

	public int attack() {
		Random random = new Random();
		int initialAttack = random.nextInt(attackMax - attackMin) + attackMin;
		return initialAttack;		
	}
	
	public void damaged(int amount) {
		Random random = new Random();
		double damage;
		int maxDef = defense + getDefBonus();
		double percMinus = random.nextInt(maxDef - (maxDef / 2)) + (maxDef / 2);
		damage = amount - Math.ceil(amount * (percMinus / 100));
		hp -= (int)damage;
		alive = hp > 0;
	}
	
	public int getDefBonus() {
		Tile current = world[xCord][yCord];
		int bonus = 0;
		if(current.getType().equals("forest")) {
			bonus += 25;
		}
		if(current.getType().equals("base")) {
			bonus += 40;
		}
		return bonus;
	}
	
	public int getHp() {
		return hp;
	}

	public void setHp(int hp) {
		this.hp = hp;
		if(this.hp > maxHP)
			this.hp = maxHP;
	}

	public Team getOwner() {
		return owner;
	}

	public void setOwner(Team owner) {
		this.owner = owner;
	}

	public int getxCord() {
		return xCord;
	}

	public void setxCord(int xCord) {
		this.xCord = xCord;
	}

	public int getyCord() {
		return yCord;
	}

	public void setyCord(int yCord) {
		this.yCord = yCord;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public boolean isUsualDirection() {
		return isUsualDirection;
	}

	public void setUsualDirection(boolean isUsualDirection) {
		this.isUsualDirection = isUsualDirection;
	}

	public int getAttackMin() {
		return attackMin;
	}

	public void setAttackMin(int attackMin) {
		this.attackMin = attackMin;
	}

	public int getAttackMax() {
		return attackMax;
	}

	public void setAttackMax(int attackMax) {
		this.attackMax = attackMax;
	}

	public int getDefense() {
		return defense;
	}

	public void setDefense(int defense) {
		this.defense = defense;
	}

	public boolean isAlive() {
		return alive;
	}

	public void setAlive(boolean alive) {
		this.alive = alive;
	}

	public boolean isMoved() {
		return moved;
	}

	public void setMoved(boolean moved) {
		this.moved = moved;
	}
	
	public Tile[][] getWorld() {
		return world;
	}	

	public void setWorld(Tile world[][]) {
		this.world = world;
	}

	public int getMaxHP() {
		return maxHP;
	}

	public void setMaxHP(int maxHP) {
		this.maxHP = maxHP;
	}
}
