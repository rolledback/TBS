package com.rolledback.units;

import com.rolledback.framework.Team;
import com.rolledback.tiles.Tile;

public class Tank extends Unit {

	public Tank(Team owner, Tile world[][], boolean occupied[][]) {
		super(world, occupied);
		moveRange = 3;
		this.owner = owner;
		type = "tank";
		hp = 100;
		maxHP = 100;
		alive = true;
		attackMax = 40;
		attackMin = 30;
		defense = 10;
	}
	
	public Tank(Team owner, int x, int y, Tile world[][], boolean occupied[][]) {
		super(world, occupied);
		moveRange = 3;
		this.owner = owner;
		type = "tank";
		hp = 100;
		maxHP = 100;
		xCord = x;
		yCord = y;
		alive = true;
		attackMax = 40;
		attackMin = 30;
		defense = 10;
	}
}
