import java.util.Vector;

public class DistanceCalculatorRunnable implements Runnable {
	Vector<Vector<Double>> matrix;
	int i,j;
	Vector<Point> points;
	
	public DistanceCalculatorRunnable(Vector<Vector<Double>> matrix, Vector<Point> points, int i, int j) {
        this.matrix = matrix;
        this.points = points;
        this.i = i;
        this.j = j;
    }

	@Override
    public void run() {
		double distance = Point.calcDistance(points.get(i), points.get(j));
		matrix.get(i).set(j, distance);
		matrix.get(j).set(i, distance);
    }
}
