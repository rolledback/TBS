package com.rolledback.tiles;

public class Tile {
	
	protected boolean landPassable, occupied;
	protected int image;
	protected String type;
	
	public Tile() {
		landPassable = true;
		occupied = false;
		image = 0;
		type = "tile";
	}
	
	public String toString() {
		return type;
	}
	
	public boolean isLandPassable() {
		return landPassable;
	}

	public void setLandPassable(boolean landPassable) {
		this.landPassable = landPassable;
	}
	
	public boolean getLandPassable() {
		return landPassable;
	}

	public int getImage() {
		return image;
	}

	public void setImage(int image) {
		this.image = image;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
