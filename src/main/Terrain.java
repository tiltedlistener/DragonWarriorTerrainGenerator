package main;

import java.util.*;

public class Terrain {

	public int[] map;
	public int size;
	public int max;
	public int width;
	public int height;
	public DragonWarriorTerrainGeneration generator;
	
	public Random rand;
	
	public Terrain(double detail, DragonWarriorTerrainGeneration _generator) {
		rand = new Random();
		
		this.generator = _generator;
		this.size = (int)Math.pow(8, detail) + 1; 		
		this.max = this.size - 1;
		this.map = new int[(this.size * this.size)];
	}
	
	public int get(int x, int y) {
		if ((x < 0 || x > this.max) || (y<0 || y > this.max)) return -1;
		return this.map[x + this.size*y];
	}
	
	public void set(int x, int y, int val) {
		this.map[x + this.size*y] = val;
	}
	
	public void generate(double roughness) {
		this.set(0, 0, (this.max/2));
		this.set(this.max, 0, (this.max / 2));
		this.set(this.max, this.max, (this.max / 2));
		this.set(0, this.max, (this.max / 2));
		
		this.divide(this.max, roughness);
	}
	
	public void divide(int size, double roughness) {
		int x,y;
		int half = size / 2;
		double scale = roughness * size;
		if (half < 1) return;
		
		for(y = half; y < this.max; y+= size) {
			for (x = half; x < this.max; x += size) {
				square(x, y, half, rand.nextFloat() * scale * 2 - scale);
			}
		}
		
		for (y = 0; y <= this.max; y+=half) {
			for (x = (y+half) % size; x <= this.max; x += size) {
				diamond(x, y, half, rand.nextFloat() * scale * 2 - scale);
			}
		}
		this.divide(size / 2, roughness);
	}
	
	public void square(int x, int y, int sze, double offset) {	
		int[] toAvg = {
		           this.get(x - sze, y - sze),   // upper left
		           this.get(x + sze, y - sze),   // upper right
		           this.get(x + sze, y + sze),   // lower right
		           this.get(x - sze, y + sze)    // lower left
		        };
		
		int ave = this.average(toAvg);		
		this.set(x, y, ave + (int)offset);
	}
	
	public void diamond(int x, int y, int sze, double offset) {
		int[] toAvg = {
		           this.get(x, y - sze),   // upper left
		           this.get(x + sze, y),   // upper right
		           this.get(x, y + sze),   // lower right
		           this.get(x - sze, y)    // lower left
		       };
		
		int ave = this.average(toAvg);
		this.set(x, y, ave + (int)offset);
	}
	
	public int average(int[] values) {
		int length = values.length;
	
		List<Integer> valid = new ArrayList<Integer>();
		for(int i=0;i<length;i++) {
			if (values[i] != -1) {
				valid.add(values[i]);
			}
		}
		int sum = 0;
		for(int j=0;j<valid.size();j++) {
			sum+= valid.get(j);
		}
		
		return sum/valid.size();
	}	
	
	// This supplants PlayfulJS's original draw / project. Much simpler. 
	public void drawAlefgard(int width, int height) {
		for (int y = 0; y < this.size; y++) {
		      for (int x = 0; x < this.size; x++) {
		        int val = this.get(x, y);     
		        Rectangle rect = new Rectangle(new Point(x,y), val);	       
		        this.generator.addRectangle(rect);
		      }
		 }			
	}

		
}