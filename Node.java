public class Node {

    public int x,y;
    public Node parent;
    public double hValue; //distance to the goal
    public double gValue; //distance from start
    public double finalValue; // hValue+ gValue aka Fvalue
    public int p_index;

    //Included an index
    public Node(int x, int y, int p_index, double hValue, double gValue){
        this.x = x;
        this.y = y;
        this.p_index = p_index;
        this.hValue = hValue;
        this.gValue = gValue;
        this.finalValue = hValue + gValue; // final value is hValue + gValue so we do not need to input it separately.
    }
    

   

}