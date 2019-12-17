import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class Main {
	static int NUM_OF_THREADS = 7;

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
		
        // Create and allocate a matrix 
		Vector<Vector<Double>> matrix = new Vector<Vector<Double>>();
		matrix.setSize(sizeOfTable);
		for (int i = 0; i < sizeOfTable; i++) {
			Vector<Double> innerVec = new Vector<Double>();
			innerVec.setSize(sizeOfTable);
			matrix.set(i, innerVec);
		}
        
		// Run each calculation in a task asynchronously, using n-threads. 
		ExecutorService executor = Executors.newFixedThreadPool(numOfThreads);
		List<CompletableFuture<DistanceCalc>> futureList = new ArrayList<CompletableFuture<DistanceCalc>>();
        for (int i = 0; i < sizeOfTable; i++) {
        	for (int j = i; j < sizeOfTable; j++) {
        		final Integer innerI = i;
        		final Integer innerJ = j;
        		futureList.add(CompletableFuture.supplyAsync(
        				() -> new DistanceCalc(points.get(innerI), points.get(innerJ), innerI, innerJ).calc(), executor));
        	}
        }

        // Join - wait for all tasks to complete, and fill each result into the matrix.
		futureList.stream().map(CompletableFuture::join).forEach(
				res -> {matrix.get(res.i).set(res.j, res.distance);
				matrix.get(res.j).set(res.i, res.distance);});
				
		executor.shutdown();
		
		return matrix;
	}
}
