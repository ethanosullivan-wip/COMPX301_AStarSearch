/**
	All unknown h, g and f values are by default positive infinity, fyi
**/
public class Node implements Comparable<Node> {

	public int index;	//I think it'll be handy to have a unique index, though we could just reference the index where we find them in stars... hmm maybe I'll delete later
    public int x,y;
    public Node parent;
    public double hValue; //distance to the goal
    private double gValue; //distance from start
    public double fValue; // hValue+ gValue aka Fvalue
    public Node pNode;		//Could be index(int) or Node, will decide later

    //Included an index
    public Node(int index, int x, int y){
        this.index = index;
		this.x = x;
        this.y = y;
		//Not sure if we need to initialise the following because...
		//p_index is only assigned once we have found a path to it (during A*).
        this.pNode = null;
		//Using Infinity for these means anything we find will be an improvement, and if the h_value somehow doesn't get assigned it'll be easy to identify?
		//hValue will be assigned after all Nodes have been created as we need to know the distance to end node before we can calculate hValue
        this.hValue = -1;		
		//G value will be assigned during the A* algorithm and may be changed multiple times during it.
        this.gValue = Double.POSITIVE_INFINITY;
        this.fValue = Double.POSITIVE_INFINITY; // final value is hValue + gValue so we do not need to input it separately.
    }
    
	public double getGValue() { return this.gValue; }
	/**
		Only accepts positive values
		@returns false if input was bad
	**/
	public boolean setGValue(double newG) {
		if ( newG < 0 ) {
			return false;
		} //Implicit else
		this.gValue = newG;
		this.fValue = this.gValue + this.hValue;
		return true;
	}
	
	
	/**
		Compares based on f value. 
		Since the fValues compared are doubles, beware. Two different values will never return 0 when compared to each other,
		but if both are compared to another number and they are similar, they may return the same number.
		@returns difference between each fValue. -ve if this value is less than the one being compared to
	**/
	public int compareTo(Node n) {
		double diff = this.fValue - n.fValue;
		if (diff > 0) 
			return (int)Math.ceil(diff);
		else
			return (int)-Math.ceil(Math.abs(diff));
		//unsure about choosing to use this method but compareTo uses an int and this way it'll only return 0 if they're the same
	}

	/**
		Compares based on fValue, if identical, returns true.
	**/
	public boolean equals(Node n) {
		return this.fValue == n.fValue;
	}

}