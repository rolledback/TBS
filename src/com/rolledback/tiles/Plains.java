package com.rolledback.tiles;


public class Plains extends Tile {
	
	public Plains() {
		type = "plains";
		landPassable = true;
	}
	
	public String toString() {
		return "Plains: +1 movement range.";
	}
}
