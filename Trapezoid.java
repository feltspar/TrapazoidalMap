import java.util.ArrayList;

/**
 * 
 * @author Shubham
 *
 */
public class Trapezoid implements Node {

	double xl, xr; // x cordinate left and right
	Segment s1, s2; // segment above and below.

	Node parent;
	// 1 for left
	int lor;

	//for naming and ID
	static int ctrT = 0;
	String id;

	Trapezoid(double a, double b, Segment s1, Segment s2, Node parent, int lor) {
		this.xl = a;
		this.xr = b;
		this.s1 = s1;
		this.s2 = s2;
		this.parent = parent;
		this.lor = lor;

		id = "t" + ++ctrT;
	}

	Trapezoid() {

	}

	// adds segment
	@Override
	public void addSegment(Segment toAdd, ArrayList<Node> traps) {
		traps.add(this);
	}

	private static void set(Trapezoid t, Node n) {
		//left
		if (t.lor == 1) {
			t.parent.setLeftUp(n);
		} else {
			t.parent.setRightDown(n);
		}
	}

	// changes the trapezoid according to the cases.
	static void getTrim(Trapezoid a, Segment s) {
		Segment snew = new Segment();
		snew.a=s.a;
		snew.b = s.b;
		snew.id= s.id;
		s=snew;
		
		// check which case
		
		//case 2
		if (s.a.x >= a.xl && s.b.x <= a.xr) {
			set(a, case2(a, s));
		}  else if (s.a.x < a.xl && s.b.x > a.xr) {
			//case 3
			set(a, case3(a, s));
		} else if (s.a.x <= a.xl) {
			//case 1b
			set(a, case1b(a, s));
		} else if (s.b.x >= a.xr) {
			set(a, case1(a, s));
		}
		//System.err.println("ya to boundary case hai ya fir traps sahi se trim nahi hue");
	}

	// case 1
	public static Node case1(Trapezoid a, Segment s) {
		if(s.a.x == a.xl){
			return case3(a,s);
		}
		
		Node p = s.a;
		
		Trapezoid X, Y, Z;
		X = new Trapezoid(a.xl, s.a.x, a.s1, a.s2, p, 1);
		Y = new Trapezoid(s.a.x, a.xr, a.s1, s, s, 1); // changed
		Z = new Trapezoid(s.a.x, a.xr, s, a.s2, s, 2);

		p.setLeftUp(X);
		p.setRightDown(s);
		s.setLeftUp(Y);
		s.setRightDown(Z);

		return p;
	}

	// case 1b when the end point of segment is in a trapezoid
	public static Node case1b(Trapezoid a, Segment s) {
		if(s.b.x == a.xr){
			return case3(a,s);
		}
		
		Node p = s.b;
		
		Trapezoid X, Y, Z;
		X = new Trapezoid(s.b.x, a.xr, a.s1, a.s2, p, 2);
		Y = new Trapezoid(a.xl, s.b.x, a.s1, s, s, 1);
		Z = new Trapezoid(a.xl, s.b.x, s, a.s2, s, 2);

		p.setLeftUp(s);
		p.setRightDown(X);
		s.setLeftUp(Y);
		s.setRightDown(Z);

		return p;
	}

	// case 2
	public static Node case2(Trapezoid a, Segment s) {
		Node p = s.a;

		Trapezoid U = new Trapezoid(a.xl, s.a.x, a.s1, a.s2, p, 1);
		p.setLeftUp(U);

		Node q = s.b;
		p.setRightDown(q);

		q.setLeftUp(s);
		Trapezoid X = new Trapezoid(s.b.x, a.xr, a.s1, a.s2, q, 2);
		q.setRightDown(X);
		Trapezoid Y = new Trapezoid(s.a.x, s.b.x, a.s1, s, s, 1);
		s.setLeftUp(Y);
		Trapezoid Z = new Trapezoid(s.a.x, s.b.x, s, a.s2, s, 2);
		s.setRightDown(Z);
		return p;
	}

	// case 3
	public static Node case3(Trapezoid a, Segment s) {
		Node p = s;
		
		p.setLeftUp(new Trapezoid(a.xl, a.xr, a.s1, s, p, 1));
		p.setRightDown(new Trapezoid(a.xl, a.xr, s, a.s2, p, 2));

		return p;
	}

	// trapezoid does not have any child nodes. A trapezoid is always a leaf
	// node
	// thus null returned in getleft and getright and set functions are empty
	@Override
	public Node getLeft() {
		return null;
	}

	@Override
	public Node getRight() {
		return null;

	}

	@Override
	public void setLeftUp(Node l) {
		//not needed for this object
	}

	@Override
	public void setRightDown(Node r) {
		//not needed for this object
	}

	//for printing
	public String toString() {
		/*String p = "Trap{" + "(" + this.xl + " " + this.xr + ") " + this.s1
				+ " and " + this.s2 + "}";
		return p;*/
		return this.id;
	}
	
	//returns ID
	public String getId(){
		return this.id;
	}
	
	//routing for string location
	@Override
	public void stringLoc(Point p) {
		System.out.print(this.id+" ");
	}
	
	//compareTo
	@Override
	public int compareTo(Node arg0) {
		String s = arg0.getId().substring(0, 1);
		String mys = this.getId().substring(0, 1);
		Integer i = Integer.parseInt(arg0.getId().substring(1, arg0.getId().length()));
		Integer myi = Integer.parseInt(this.getId().substring(1, this.getId().length()));
		return mys.compareTo(s) == 0? myi.compareTo(i) : mys.compareTo(s);
	}

	//for comparison
	public boolean equals(Object t){
		Trapezoid t2 = (Trapezoid) t;
		return this.s1.equals(t2.s1) && this.s2.equals(t2.s2) && this.xl == t2.xl && this.xr == t2.xr;
	}
}
