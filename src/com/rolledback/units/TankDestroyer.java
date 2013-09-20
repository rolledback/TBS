package com.rolledback.units;

import com.rolledback.framework.Team;
import com.rolledback.tiles.Tile;

public class TankDestroyer extends Unit {
	
	public TankDestroyer(Team owner, Tile world[][], boolean occupied[][]) {
		super(world, occupied);
		moveRange = 2;
		this.owner = owner;
		type = "tank destroyer";
		hp = 50;
		maxHP = 50;
		alive = true;
		attackMax = 55;
		attackMin = 45;
		defense = 5;
	}
	
	public TankDestroyer(Team owner, int x, int y, Tile world[][], boolean occupied[][]) {
		super(world, occupied);
		moveRange = 2;
		this.owner = owner;
		type = "tank destroyer";
		hp = 50;
		maxHP = 50;
		xCord = x;
		yCord = y;
		alive = true;
		attackMax = 55;
		attackMin = 45;
		defense = 5;
	}

}
