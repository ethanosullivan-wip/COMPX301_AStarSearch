public class Node implements Comparable<Node> {

	public int index;	//I think it'll be handy to have a unique index, though we could just reference the index where we find them in stars... hmm maybe I'll delete later
    public int x,y;
    public Node parent;
    public double hValue; //distance to the goal
    public double gValue; //distance from start
    public double fValue; // hValue+ gValue aka Fvalue
    public Node p_node;		//Could be index(int) or Node, will decide later

    //Included an index
    public Node(int index, int x, int y){
        this.index = index;
		this.x = x;
        this.y = y;
		//Not sure if we need to initialise the following because...
		//p_index is only assigned once we have found a path to it (during A*).
        this.p_node = null;
		//hValue will be assigned after all Nodes have been created as we need to know the distance to end node before we can calculate hValue
        this.hValue = Double.POSITIVE_INFINITY;		//If we somehow miss one this will make the algorithm ignore it
		//G value will be assigned during the A* algorithm and may be changed multiple times during it.
        //this.gValue = gValue;
        //this.fValue = hValue + gValue; // final value is hValue + gValue so we do not need to input it separately.
    }
    
	/**
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

	public boolean equals(Node n) {
		return this.fValue == n.fValue;
	}

}