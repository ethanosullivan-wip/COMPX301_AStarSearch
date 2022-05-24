public class Node {

	public int index;	//I think it'll be handy to have a unique index, though we could just reference the index where we find them in stars... hmm maybe I'll delete later
    public int x,y;
    public Node parent;
    public double hValue; //distance to the goal
    public double gValue; //distance from start
    public double finalValue; // hValue+ gValue aka Fvalue
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
        //this.finalValue = hValue + gValue; // final value is hValue + gValue so we do not need to input it separately.
    }
    

   

}