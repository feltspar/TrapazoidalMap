import java.util.ArrayList;
/**
 * 
 * @author Shubham
 *	Interface for the three types of nodes: Traps, segments, point
 */

public interface Node extends Comparable<Node>{
	//adds segment to the tree 
	public void addSegment(Segment toAdd, ArrayList<Node> traps);
	// string location
	public void stringLoc(Point p);
	//gets left child
	public Node getLeft();
	//gets right child
	public Node getRight();
	//sets left child
	public void setLeftUp(Node l);
	//sets right child
	public void setRightDown(Node r);
	//returns id of node
	public String getId();
	//for printing
	public String toString();
}
