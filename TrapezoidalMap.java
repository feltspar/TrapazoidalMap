import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * This is the main class to run the Trapezoidal Maps program
 * @author Shubham Saxena
 *
 */
public class TrapezoidalMap {

	public static void main(String[] args) throws FileNotFoundException {

		//input file
		File input = new File(args[0]);
		Scanner sc = new Scanner(input);

		int numSeg = Integer.parseInt(sc.next());

		Point boundLow = new Point(Double.parseDouble(sc.next()),
				Double.parseDouble(sc.next()), 1);
		Point boundHigh = new Point(Double.parseDouble(sc.next()),
				Double.parseDouble(sc.next()), 2);

		Segment[] segs = new Segment[numSeg];

		for (int i = 0; i < numSeg; i++) {

			segs[i] = new Segment(new Point(Double.parseDouble(sc.next()),
					Double.parseDouble(sc.next()), 1), new Point(
					Double.parseDouble(sc.next()),
					Double.parseDouble(sc.next()), 2));
		}

		//bounding trapezoid
		TrapTree obj = new TrapTree(TrapTree.makeRoot(boundLow, boundHigh));
		
		//adding segments. 
		for (Segment s : segs) {
			obj.addNode(s);
			System.out.println();
		}
		
		
		obj.allTraps();
		//System.out.println("Before:" + obj.allTraps);
		
		//renames same trapezoids. 
		obj.renameAdj(obj.allTraps);
		
		//obj.printlevel();
		//System.out.println("After:" + obj.allTraps);

		obj.listAllNodes(obj.root);
		String [][] fileMat = obj.adjMatrix();
		
		PrintWriter pw = new PrintWriter(new File("output.txt"));
		
		for (int i = 0; i < fileMat.length; i++) {
			String s = "";
			for (int j = 0; j < fileMat.length; j++) {
				if (fileMat[i][j].length() > 2) {
					s += (fileMat[i][j] + " ");
				} else if (fileMat[i][j].length() == 2) {
					s += (fileMat[i][j] + "  ");
				} else {
					s += (fileMat[i][j] + "   ");
				}
			}
			pw.println(s);
		}
		
		pw.close();
		
		if (args.length > 2) {
			Point locP = new Point(Integer.parseInt(args[1]),
					Integer.parseInt(args[2]), 1);
			System.out.println();
			System.out.print("String Location: ");
			obj.root.stringLoc(locP);
		}
		sc.close();
	}
}
