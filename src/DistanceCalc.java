public class DistanceCalc {
	int i,j;
	Point a, b;
	Double distance;
	
	public DistanceCalc(Point a, Point b, int i, int j) {
        this.a = a;
        this.b = b;
        this.i = i;
        this.j = j;
    }

	public DistanceCalc calc() {
		distance = Math.sqrt(Math.pow(a.x - b.x, 2) + Math.pow(a.y - b.y, 2));

		return this; 
	}
}
