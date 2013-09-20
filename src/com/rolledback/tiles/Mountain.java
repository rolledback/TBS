package com.rolledback.tiles;


public class Mountain extends Tile {
	
	public Mountain() {
		type = "mountain";
		landPassable = false;
	}
	
	public String toString() {
		return "Mountains: no movement allowed.";
	}
}
