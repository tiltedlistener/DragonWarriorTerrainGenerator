package main;

public class Rectangle {
	
	public Point a;
	public int size = 16;
	public int val;
	
	public Rectangle(Point _a, int _val) {
		this.a = _a;
		this.val = _val;
	}
	
	public int x() {
		return a.x * this.size;
	}
	
	public int y() {
		return a.y * this.size;
	}
		
}
