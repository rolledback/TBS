package com.rolledback.framework;

import com.rolledback.tiles.Base;
import com.rolledback.units.Unit;

public class Team {
	
	protected Unit[] army;
	protected String name;
	protected int teamSize;
	private Base base;
	
	public Team() {}
	
	public Team(String name, int x) {
		army = new Unit[x];
		teamSize = x;
		this.name = name;	
		base = null;
	}
	
	public String toString() {
		return name;
	}
	
	public Unit[] getArmy() {
		return army;
	}

	public void setArmy(Unit[] army) {
		this.army = army;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getTeamSize() {
		return teamSize;
	}

	public void setTeamSize(int teamSize) {
		this.teamSize = teamSize;
	}

	public Base getBase() {
		return base;
	}

	public void setBase(Base base) {
		this.base = base;
	}

}
