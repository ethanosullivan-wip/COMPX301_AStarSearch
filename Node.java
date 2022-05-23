public class Node {

    public int x,y;
    public Node parent;
    public double hValue; //distance to the goal
    public double gValue; //distance from start
    public double finalValue; // hValue+ gValue aka Fvalue

    public Node{ int x, int y}{
        this.x, this.y;
    }

    //should there be a method for calculating gValue+hvalue ?

}