package main;

import java.awt.*;
import javax.swing.*;
import java.util.*;

public class DragonWarriorTerrainGeneration extends JFrame {
	
	static final long serialVersionUID = 0;

	public static void main(String[] args) {
		new DragonWarriorTerrainGeneration();
	}
	
	public ArrayList<Rectangle> rectList;
	public ArrayList<Image> images;
	public int highest = 0;
	public int lowest = 100;
	public int case1 = 0;
	public int case2 = 0;
	public int case3 = 0;
	public int case4 = 0;
	public int case5 = 0;
	public int case6 = 0;
	
	public DragonWarriorTerrainGeneration() {
		super("Dragon Warrior Terrain!");
		setSize(1024, 1024);
		rectList = new ArrayList<Rectangle>();
		setImages();
		
		// Use 2 for detail since we only got 64x64 on a map this size
		Terrain terrain = new Terrain(2,this);
		
		// 0.6 seems to work good for generating maps that look fairly like 
		// the origin al
		terrain.generate(0.6);
		terrain.drawAlefgard(1024, 1024);
		
		// Simpler way to give even balance to our five different types
		computeRange();
		
		// Fire it up!
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	// Just have the 
	public void setImages() {
		images = new ArrayList<Image>();
		images.add(load("images/ocean.png"));
		images.add(load("images/sand.png"));
		images.add(load("images/grass.png"));
		images.add(load("images/forest.png"));
		images.add(load("images/hill.png"));
		images.add(load("images/mountain.png"));
	}
	
	public Image load(String filename) {
		Toolkit tk = Toolkit.getDefaultToolkit();
		Image image = tk.getImage(filename);
		return image;
	}	
	
	public void addRectangle(Rectangle rect){
		rectList.add(rect);
		
		if (rect.val > this.highest) {
			this.highest = rect.val;
		} else if (rect.val < this.lowest) {
			this.lowest = rect.val;
		}
	}
	
	public void computeRange() {
		int range = this.highest - this.lowest;
		this.case1 = this.lowest;
		this.case2 = (int)(range*0.2);
		this.case3 = (int)(range*0.4);
		this.case4 = (int)(range*0.6);
		this.case5 = (int)(range*0.8);
		this.case6 = this.highest;
	}
	
	public void paint(Graphics g) {
		for(int i=0;i<rectList.size();i++) {
			Rectangle current = rectList.get(i);
			
			// Offset made large bodies of water more likely
			int val = current.val - 20;
	        
	        Image imageFinal = images.get(0);
	        if (val < this.case1) {
	        	imageFinal = images.get(0);
	        } else if (val < this.case2 && val > this.case1) {
	        	imageFinal = images.get(1);
	        } else if (val < this.case3 && val > this.case2) {
	        	imageFinal = images.get(2);
	        } else if (val < this.case4 && val > this.case3) {
	        	imageFinal = images.get(3);
	        } else if (val < this.case5 && val > this.case4) {
	        	imageFinal = images.get(4);
	        } else if (val < this.case6 && val > this.case5) {
	        	imageFinal = images.get(5);
	        } else {
	        	imageFinal = images.get(2);
	        }
	        
	        System.out.println(val);
			g.drawImage(imageFinal, current.x(), current.y(), current.size, current.size, this);
		}
	}

}
