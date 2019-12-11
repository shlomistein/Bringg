import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {
	static int NUM_OF_THREADS = 3;

	public static void main(String[] args) {
		Vector<Point> pointList = new Vector<Point>();
		pointList.add(new Point(1, 2));
		pointList.add(new Point(2, 4));
		pointList.add(new Point(3, 6));
		pointList.add(new Point(4, 8));

		Vector<Vector<Double>> matrix = null;
		matrix = calcDistanceMatrix(pointList, NUM_OF_THREADS);

		// Printing the result matrix
		for (int i = 0; i < matrix.size(); i++) {
			for (int j = 0; j < matrix.size(); j++) {
				System.out.print(matrix.get(i).get(j) + ", ");
			}
			System.out.println();
		}
	}

	private static Vector<Vector<Double>> calcDistanceMatrix(Vector<Point> points, int numOfThreads) {
		int sizeOfTable = points.size();

		Vector<Vector<Double>> matrix = new Vector<Vector<Double>>();
		matrix.setSize(sizeOfTable);
		for (int i = 0; i < sizeOfTable; i++) {
			Vector<Double> innerVec = new Vector<Double>();
			innerVec.setSize(sizeOfTable);
			matrix.set(i, innerVec);
		}
		
		ExecutorService executor = Executors.newFixedThreadPool(numOfThreads);
        for (int i = 0; i < sizeOfTable; i++) {
        	for (int j = i; j < sizeOfTable; j++) {
            Runnable worker = new DistanceCalculatorRunnable(matrix, points, i, j);
            executor.execute(worker);
          }
        }
        executor.shutdown();
        try {
			executor.awaitTermination(4, TimeUnit.MINUTES);
		} catch (InterruptedException e) {
			e.printStackTrace();
			return null;
		}
        
		return matrix;
	}

}
