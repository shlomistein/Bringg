
public class Point {
	public double x,y;
	
	Point(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	static public double calcDistance(Point a, Point b) {
		if (a == b) {
			return 0;
		}
		
		return Math.sqrt(Math.pow(a.x - b.x, 2) + Math.pow(a.y - b.y, 2));
	}
}
