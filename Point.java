import java.util.ArrayList;

/**
 * Point class to hold points
 * 
 * @author Shubham Saxena
 * 
 */
public class Point implements Node {
	double x;
	double y;

	// int sOe; // start point or end point.

	static int ctrps = -1; // counter for points for naming.
	static int ctrpe = -1; // counter for points for naming.
	
	//for naming ie ID
	String id = "p";
	
	//left child and right child.
	Node left, right;

	//constructor
	Point() {
	}

	//parameterised constructor
	Point(double x, double y, int sOe) {
		this.x = x;
		this.y = y;

		if (sOe == 1) {
			this.id = "p" + ++ctrps;
		} if(sOe==2)
			this.id = "q" + ++ctrpe;
	}

	//for printing
	public String toString() {
		/*String p = id + "(" + x + " " + y + ")";
		return p;*/
		return this.id;
	}

	//routes the point to the next correct node
	@Override
	public void addSegment(Segment toAdd, ArrayList<Node> traps) {
		// if both points of toAdd left
		if (toAdd.a.x < this.x && toAdd.b.x <= this.x) {
			this.left.addSegment(toAdd, traps);
		}else if (toAdd.a.x >= this.x && toAdd.b.x > this.x) {
			this.right.addSegment(toAdd, traps);
		}else{
			// if on either side
			this.left.addSegment(toAdd, traps);
			this.right.addSegment(toAdd, traps);
		}
		
	}

	//returns left child
	@Override
	public Node getLeft() {
		return this.left;
	}

	//returns right child
	@Override
	public Node getRight() {
		return this.right;

	}

	//setter
	@Override
	public void setLeftUp(Node l) {
		this.left = l;

	}

	//setter
	@Override
	public void setRightDown(Node r) {
		this.right = r;

	}

	//for object comparison
	public boolean equals(Object s) {
		Point a = (Point) s;		
		if (this.x == a.x && this.y == a.y)
			return true;
		else
			return false;
	}
	
	//return id
	public String getId(){
		return this.id;
	}

	
	//routing for location string
	@Override
	public void stringLoc(Point p) {
		// TODO Auto-generated method stub
		System.out.print(this.id+" ");
		if(this.x > p.x){
			this.getLeft().stringLoc(p);
		}
		else{
			this.getRight().stringLoc(p);
		}			
		
	}

	//compareTo function
	@Override
	public int compareTo(Node arg0) {
		String s = arg0.getId().substring(0, 1);
		String mys = this.getId().substring(0, 1);
		
		Integer i = Integer.parseInt(arg0.getId().substring(1, arg0.getId().length()));
		Integer myi = Integer.parseInt(this.getId().substring(1, this.getId().length()));
		
		return mys.compareTo(s) == 0? myi.compareTo(i) : mys.compareTo(s);
		
	}
}
