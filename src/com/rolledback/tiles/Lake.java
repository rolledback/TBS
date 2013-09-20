package com.rolledback.tiles;


public class Lake extends Tile {
	
	public Lake() {
		type = "lake";
		landPassable = false;
	}
	
	public String toString() {
		return "Lake: no movement allowed.";
	}
}