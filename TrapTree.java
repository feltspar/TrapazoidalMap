import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.TreeSet;

/**
 * 
 * @author Shubham Class for tree structure of the Trapezoid Map
 */
public class TrapTree {

	Node root;

	// all trapezoids (for testing)
	ArrayList<Trapezoid> allTraps = new ArrayList<Trapezoid>();

	// (list of all nodes. Set for unique
	TreeSet<Node> allNodesSet = new TreeSet<Node>();

	// list of all nodes
	Node[] nodeList;

	// constructor
	TrapTree() {
	}

	// parameterised constructor
	TrapTree(Node root) {
		this.root = root;
	}

	// adds and returns all trapezoids in tree.
	ArrayList<Trapezoid> allTraps() {
		addToList(this.root);
		return allTraps;
	}

	// returns all traps
	ArrayList<Trapezoid> getAllTraps() {
		return allTraps;
	}

	// adds all the traps to a list.
	void addToList(Node n) {

		if (n == null) {
			return;
		}
		if (n.getLeft() != null) {
			addToList(n.getLeft());
			addToList(n.getRight());
		} else {
			allTraps.add((Trapezoid) n);
		}

	}

	// renames trapezoids which are same
	void renameAdj(ArrayList<Trapezoid> tList) {
		int size = tList.size();
		for (int i = 0; i < size - 1; i++) {
			for (int j = i + 1; j < size; j++) {
				Trapezoid a = tList.get(i);
				Trapezoid b = tList.get(j);
				if ((a.xr == b.xl || a.xl == b.xr) && a.s1.equals(b.s1)
						&& a.s2.equals(b.s2)) {
					b.id = a.id;
				}
			}
		}
	}

	// makes the root node from the bounding points
	public static Node makeRoot(Point p1, Point p2) {
		// make root trap and return it
		Point b = new Point(p2.x, p1.y, 1);
		Point c = new Point(p1.x, p2.y, 2);
		Segment s1 = new Segment(p1, b);
		Segment s2 = new Segment(c, p2);

		Trapezoid t = new Trapezoid(p1.x, p2.x, s1, s2, null, 0);
		return t;
	}

	// adds segments into tree.
	void addNode(Segment seg) {
		ArrayList<Node> traps = new ArrayList<Node>();
		root.addSegment(seg, traps);
		System.out.println("traps: " + traps);

		if (traps.size() == 1 && traps.get(0) == root) {
			root = Trapezoid.case2((Trapezoid) root, seg);
			return;
		}

		for (Node n : traps) {
			Trapezoid.getTrim((Trapezoid) n, seg);
		}
	}

	// lists all nodes in tree
	void listAllNodes(Node n) {
		if (n != null) {
			allNodesSet.add(n);
			if (n.getLeft() != null) {
				listAllNodes(n.getLeft());
				listAllNodes(n.getRight());
			}
		}
	}

	// makes adjacency
	String[][] adjMatrix() {
		listAllNodes(this.root);
		int size = allNodesSet.size();
		Object[] a = allNodesSet.toArray();
		nodeList = new Node[size];
		// getting list of nodes in array
		for (int i = 0; i < size; i++) {
			nodeList[i] = (Node) a[i];
			// System.out.println("NodeList:" + nodeList[i]);
		}
		String[][] aMat = initialise();
		for (int i = 0; i < nodeList.length; i++) {
			findChildren(this.root, nodeList[i], i, aMat);
		}

		// printing
		printMat(aMat);
		return aMat;
	}

	// printing of matrix
	void printMat(String[][] mat) {
		for (int i = 0; i < mat.length; i++) {
			for (int j = 0; j < mat.length; j++) {
				if (mat[i][j].length() > 2) {
					System.out.print(mat[i][j] + " ");
				} else if (mat[i][j].length() == 2) {
					System.out.print(mat[i][j] + "  ");
				} else {
					System.out.print(mat[i][j] + "   ");
				}
			}
			System.out.println();
		}
	}

	// initilises the matrix
	String[][] initialise() {
		String[][] ami = new String[nodeList.length + 1][nodeList.length + 1];
		ami[0][0] = " ";
		for (int i = 1; i < nodeList.length + 1; i++) {
			ami[0][i] = "" + nodeList[i - 1];
			ami[i][0] = "" + nodeList[i - 1];
		}
		for (int i = 1; i < nodeList.length + 1; i++) {
			for (int j = 1; j < nodeList.length + 1; j++) {
				ami[i][j] = "0";
			}
		}
		return ami;
	}

	// returns position of the node in the list
	int returnPosition(String id, Node[] l) {
		for (int i = 0; i < l.length; i++) {
			if (id.equals(l[i].getId())) {
				return i;
			}
		}
		return -1;
	}

	// checks the children of a node in tree and sets the adjMatrix
	public void findChildren(Node present, Node check, int i, String[][] adjM) {
		if (present.getLeft() != null) {
			if (present.getId().equals(check.getId())) {
				int col;

				String lchildId = present.getLeft().getId();
				col = returnPosition(lchildId, nodeList);
				adjM[col + 1][i + 1] = "1";

				String rchildId = present.getRight().getId();
				col = returnPosition(rchildId, nodeList);
				adjM[col + 1][i + 1] = "1";
			}
			findChildren(present.getLeft(), check, i, adjM);
			findChildren(present.getRight(), check, i, adjM);
		}
	}
}
