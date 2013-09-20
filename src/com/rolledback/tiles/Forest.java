package com.rolledback.tiles;


public class Forest extends Tile {
	
	public Forest() {
		type = "forest";
		landPassable = true;
	}
	
	public String toString() {
		return "Forest: +30 defense.";
	}
}
