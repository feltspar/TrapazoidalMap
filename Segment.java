import java.util.ArrayList;

/**
 * Segment class to hold segments
 * 
 * @author Shubham Saxena
 * 
 */
public class Segment implements Node {
	// counter for segment naming
	static int ctrs = 0; // starting point

	Point a; // start point
	Point b; // end point
	String id; // name

	Node downRight, upLeft; // trapezoid above and below

	//constructor
	Segment() {
		a = new Point();
		b = new Point();
	}

	//constructor
	Segment(Point a, Point b) {
		if (a.x < b.x) {
			this.a = a;
			this.b = b;
		}
		if (b.x < a.x) {
			this.b = a;
			this.a = b;
		}
		id = "s" + ++ctrs;
		
	}

	//constructor
	Segment(int x1, int y1, int x2, int y2) {
		if (x1 < x2) {
			a = new Point(x1, y1,1);
			b = new Point(x2, y2,2);
		}
		if (x2 < x1) {
			b = new Point(x1, y1, 1);
			a = new Point(x2, y2, 2);
		}
		if (x1 - x2 == 0) {
			System.out.println("cannot accept Verticle lines");
		}
	}

	public String toString() {
//		String s = id +"("+ a.toString() + " " + b.toString()+")";
//		return s;
		return this.id;
	}

	

	/**
	 * checks if intersection is within segment range.
	 * 
	 * @param i
	 * @param s
	 * @return
	 */
	boolean outOfRange(Point i, Segment s) {
		if ((i.x < s.a.x && i.x < s.b.x) || (i.x > s.a.x && i.x > s.b.x)
				|| (i.y < s.a.y && i.y < s.b.y) || (i.y > s.a.y && i.y > s.b.y)) {
			return true;
		} else
			return false;
	}

	@Override
	public void addSegment(Segment toAdd, ArrayList<Node> traps) {
		int upDown = aboveBelow(toAdd.a);

		if (upDown > 0) {
			this.downRight.addSegment(toAdd, traps);
		} else
			this.upLeft.addSegment(toAdd, traps);
	}

	int aboveBelow(Point toAdd) {

		// check toAdd's start point is above or below this segment
		double m = (this.b.y - this.a.y) / (this.b.x - this.a.x);
		// y = mx + c ;
		double c = (this.a.y - this.a.x * m);

		if (-toAdd.y + m*toAdd.x + c < 0) {
			return 1; // above
		} else
			return -1; // below

	}

	public boolean equals(Object s) {
		Segment x = (Segment) s;
		if (this.a.equals(x.a) && this.b.equals(x.b))
			return true;
		else
			return false;
	}

	@Override
	public Node getLeft() {
		return this.downRight;
	}

	@Override
	public Node getRight() {
		return this.upLeft;

	}

	@Override
	public void setLeftUp(Node l) {
		this.upLeft = l;

	}

	@Override
	public void setRightDown(Node r) {
		this.downRight = r;
	}


	
	/**
	 * calculates the intersection point
	 * 
	 * @param s1
	 * @return
	 */
	public Point intersect(Segment s1) {
		Point intersect = new Point();
		if (this == null || s1 == null) {
			return null;
		}
		intersect.x = ((s1.a.x * s1.b.y - s1.a.y * s1.b.x)
				* (this.a.x - this.b.x) - (s1.a.x - s1.b.x)
				* (this.a.x * this.b.y - this.a.y * this.b.x))
				/ ((s1.a.x - s1.b.x) * (this.a.y - this.b.y) - (s1.a.y - s1.b.y)
						* (this.a.x - this.b.x));
		intersect.y = ((s1.a.x * s1.b.y - s1.a.y * s1.b.x)
				* (this.a.y - this.b.y) - (s1.a.y - s1.b.y)
				* (this.a.x * this.b.y - this.a.y * this.b.x))
				/ ((s1.a.x - s1.b.x) * (this.a.y - this.b.y) - (s1.a.y - s1.b.y)
						* (this.a.x - this.b.x));

		if (outOfRange(intersect, s1) || outOfRange(intersect, this)
				|| Double.isNaN(intersect.x) || Double.isNaN(intersect.x)) {
			intersect = null;
		}

		if (intersect != null) {
			intersect.x = Math.round(intersect.x * 100.0) / 100.0; // rounding
																	// to 2
																	// decimal
																	// places
			intersect.y = Math.round(intersect.y * 100.0) / 100.0;
		}
		return intersect;
	}
	
	public String getId(){
		return this.id;
	}

	@Override
	public void stringLoc(Point p) {
		System.out.print(this.id+" ");
		if(this.aboveBelow(p)==1){
			this.getLeft().stringLoc(p);
		}
		else{
			this.getRight().stringLoc(p);
		}
	}

	@Override
	public int compareTo(Node arg0) {
		String s = arg0.getId().substring(0, 1);
		String mys = this.getId().substring(0, 1);
		
		Integer i = Integer.parseInt(arg0.getId().substring(1, arg0.getId().length()));
		Integer myi = Integer.parseInt(this.getId().substring(1, this.getId().length()));
		
		return mys.compareTo(s) == 0? myi.compareTo(i) : mys.compareTo(s);
		
	}

	
}
