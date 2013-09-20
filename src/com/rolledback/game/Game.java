package com.rolledback.game;

import java.util.Random;

import com.rolledback.framework.Team;
import com.rolledback.tiles.Base;
import com.rolledback.tiles.Forest;
import com.rolledback.tiles.Lake;
import com.rolledback.tiles.Mountain;
import com.rolledback.tiles.Plains;
import com.rolledback.tiles.Tile;
import com.rolledback.units.Tank;
import com.rolledback.units.TankDestroyer;
import com.rolledback.units.Unit;

	
public class Game {
	
	private Tile world[][];
	private Unit globalUnits[];
	private Team one, two;
	private final int numRow = 7;
	private final int numCol = 12;
	private final int teamSize = 8;
	private String seed;

	public Game(boolean occupied[][], String s) {
		world = new Tile[numCol][numRow];
		globalUnits = new Unit[teamSize * 2];
		one = new Team("Team One", teamSize);
		two = new Team("Team Two", teamSize);
		if(s == null)
			seed = genSeed();
		else
			seed = s;
		constructMap();
		genArmy(one, 1, occupied);
		genArmy(two, numCol - 4, occupied);
	}
	
	public String genSeed()	{
		String seed = "";
		Random random = new Random();
		for(int x = 0; x < numCol * numRow; x++) {
			int temp = random.nextInt(12);
			if(temp < 7)
				seed += Integer.toString(0);
			if(temp > 6 && temp < 11)
				seed += Integer.toString(2);
			if(temp == 11)
				seed += Integer.toString(1);			
		}
		char[] sepSeed = seed.toCharArray();

		for(int y = 0; y < 2; y++)
			sepSeed[random.nextInt(seed.length())] = '3';		

		sepSeed[random.nextInt(numRow)] = '4';
		sepSeed[random.nextInt(numRow) + (numCol * numRow) - numRow] = '4';	

		seed = new String(sepSeed);
		return seed;
	}
	
	public void constructMap() {
		int counter = 0;
		for (int col = 0; col < numCol; col++)
			for (int row = 0; row < numRow; row++)
			{        	  	 
				Random random = new Random();
				int prob = random.nextInt(2);

				if(seed.charAt(counter) == '0') {
					world[col][row] = new Plains();
					if(prob == 0)
						world[col][row].setImage(0);
					else
						world[col][row].setImage(1);
				}
				if(seed.charAt(counter) == '1') {  
					world[col][row] = new Mountain();
					if(prob == 0)
						world[col][row].setImage(0);
					else
						world[col][row].setImage(1);
				}
				if(seed.charAt(counter) == '2') {
					world[col][row] = new Forest();
					if(prob == 0)
						world[col][row].setImage(0);
					else
						world[col][row].setImage(1);
				} 
				if(seed.charAt(counter) == '3') {
					world[col][row] = new Lake();
					world[col][row].setImage(0);
				}	        	 
				if(seed.charAt(counter) == '4') {
					world[col][row] = new Base(col, row);
					world[col][row].setImage(0);
				}
				counter++;
			}
	}
	
	public void genArmy(Team team, int min, boolean occupied[][]) {
		boolean validSpotFound = false;
		int validX = 0;
		int validY = 0;
		Random random = new Random();
		for(int x = 0; x < teamSize; x++) {
			int counter = 0;
			do
			{
				validX = random.nextInt(3) + min;
				validY = random.nextInt(numRow);
				if(world[validX][validY].isLandPassable())
					validSpotFound = true;
				for(int y = 0; y < teamSize; y++)
					if(team.getArmy()[y] != null)
						if(validX == team.getArmy()[y].getxCord() && validY == team.getArmy()[y].getyCord())
							validSpotFound = false;
				counter++;
				if(counter == 100) {
					break;
				}					
			}
			while(!validSpotFound);			
			counter = 0;			
			validSpotFound = false;			
			if(x >= 6 && x <= 7)
				team.getArmy()[x] = new TankDestroyer(team, validX, validY, world, occupied);
			else
				team.getArmy()[x] = new Tank(team, validX, validY, world, occupied);
			if(team.equals(one))
				globalUnits[x] = team.getArmy()[x];
			else
				globalUnits[x + teamSize] = team.getArmy()[x];
		}
	}

	public Tile[][] getWorld() {
		return world;
	}

	public void setWorld(Tile world[][]) {
		this.world = world;
	}

	public Unit[] getGlobalUnits() {
		return globalUnits;
	}

	public void setGlobalUnits(Unit globalUnits[]) {
		this.globalUnits = globalUnits;
	}

	public Team getOne() {
		return one;
	}

	public void setOne(Team one) {
		this.one = one;
	}

	public Team getTwo() {
		return two;
	}

	public void setTwo(Team two) {
		this.two = two;
	}

	public int getNumRow() {
		return numRow;
	}

	public int getNumCol() {
		return numCol;
	}

	public int getTeamSize() {
		return teamSize;
	}

	public String getSeed() {
		return seed;
	}

	public void setSeed(String seed) {
		this.seed = seed;
	}
}
