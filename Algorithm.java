import java.util.priorityQueue;
import java.util.ArrayList;

public class Algorithm {
    static PriorityQueue<Node> OpenList = new PriorityQueue<Node>();
    static PriorityQueue<Node> CloseList = new PriorityQueue<Node>();
    static ArrayList<int[]> Path = new ArrayList<int[]>(); // Where we will store the path?.. this is what you have mentioned in the documentation

    // Found a code where it uses a path arrayList and it had a code about backtracking at the bottom of the page, (from line 84)
    // I am using the AI coding program (the one i told you last time) and its suggestions pretty much took me to the end point
    // maybe unnatural here and there but it should help us getting the idea of how we should apply this to our assignment
    
    // OK all this stuff I've written below is just me thinking things through, tlDON'Tr; thanks for some perspective, 
        //  - have decided using some kind previous_node in a Node would be good and should replace my old Path idea (which wasn't this), 
        //  - also can we just call OpenList "frontier" in our code? It kinda confuses me.
        //  - finally I think we can do away with CloseList by adding previous_node to our Nodes and checking the F value of a Node every time we find it 
        //      to see if we've found it before. If we have then compare that value to the new one and if the new is smaller, add to the frontier.
    
    // Also delete all this text if it gets in your way it's all in the history I can go check it whenever I want.
    
    // This seems to be based on searching a grid, like in a top-down game. The basics are the same I'm just writing notes to help me understand it
    // It assumes nothing is in OpenList and CloseList, right? And the only way it ADDs new nodes is assuming, because it's based on a grid, that the 
        // neighbouring squares on all 4 sides can contain a new node, creating one for each of them, then moving to the best one.
    // OpenList refers to the frontier I think?
    // CloseList is the nodes we've already visited, most importantly including the one we're currently expanding at "nowindex".
        // However I don't think we need a whole list, we can replace all the other functions of it by having a node's "previous_index" be a "previous_node",
            // actually pointing to a node so we can backtrack using that to get the path.
    // This Path list is a different method to what I meant. I meant a Path class that contained the full list of previous nodes for every node that we'd
        // found a path for. So kinda similar to p_index, which I'm thinking may be the better solution?
        // Yeah using just the prevous index would be way better (With my way we'd need to update ALL paths containing a node if we found a shorter path to that node)
    // Storing the F_value and G_value with the node still seems kinda weird since it's a property of a whole path, not an individual node
    // Problem is if we find a shorter path to a node that other nodes already use on their path, we have to change ALL the children of that node's G and F 
        // values, since those values would've been based on the previous shortest path which can now be shorter.
            // Honestly not sure if this situation could even occur but if we can't prove it won't we should assume it can
        // i.e if a node we've already explored has the path A-B-C-D-E-F-G and we find a shorter path through A-H-I-E, then we need to change node G's path to
            // be A-H-I-E-F-G. But we need to run through EVERY child again because we may have A-B-C-D-E-F-J stored somewhere in the bottom of our frontier 
            // because F-J is a really costly path to travel, but it should be changed to A-H-I-E-F-J in case the other paths get long enough that we need it later
        // Question is, do we need to look at F. I guess that's about the method. Do we go through the whole frontier looking for paths containing E (NOPE we're storing 
            // just the previous node now so that's an awful idea), or do we recursively check all the children of E, then F, etc. until we've updated them all 
            // BEFORE we continue our search, or maybe best of all, we just add E to our frontier again and keep barrelling on through the process without even 
            // bothering to check if any nodes in the frontier need to be changed. 
                // This is good! Why? Now, because we've found a SHORTER path to E, when we find new paths to the places we'd already found before (like G) that are
                // still in the frontier, our new path WILL be shorter and when we compare them before putting our newly found node in the list, we'll end up 
                // deleting the old value, replacing it with this, and doing the same process we did with E by putting F or G or whatever back in the frontier and 
                // keeping on looking for its children on and on until we reach new ground.
                // Obviously it's not ideal to go through the same nodes more than once but A* should hopefully minimise how often we do this and there's no simpler
                // or quicker way I can think of to update the nodes.
    
                // Remember how I said I wasn't sure if this was even possible? Pretty sure now that it's not. If I'm right in my thinking A* will always find the 
                // shortest path to a node first. Wait or am I thinking of best-first? maybe it doesn't? IDK we're keeping it in just in case.

    public static void main(String[] args){
        int startx = 0;
        int starty = 0;
        int endx = 5;
        int endy = 5;

        int nowindex = 0;

        CloseList.add(new Node(startx, starty, -1, 0, 0)) //putting 'start' in the closelist so the search begins.
        while(CloseList.get(nowindex).x != endx || CloseList.get(nowindex).y !=endy){ //repeat until we reach the goal 
            for (int way = 0; way <4; way++){ //order of left,right,up,down
                int nowx=CloseList.get(nowindex).x;
                int nowy=CloseList.get(nowindex).y;
                boolean flag = false; // this is used for checking if the current location is in openlist or closelist
                if (way == 0) nowy++;
                else if (way == 1) nowy--;
                else if (way == 2) nowy--;  //Pretty sure 2 of these are meant to be x++ and x--
                else if (way == 3) nowy++;

                //What does this do?
                for (int i = 0; i < CloseList.size(); i++){
                    if (CloseList.get(i).x == nowx && CloseList.get(i).y == nowy){//this AI coding is so great wow it came up with this current line by itself
                    flag = true;

                    } 
                }

                for (int i= 0; i< OpenList.size(); i++){ //if it already exist in open list, compare gValue, and if gValue is smaller, change its parent's index and its hvalue
                //So this is where we replace a longer path with a shorter path if we find one
                if(OpenList.get(i).x == nowx && OpenList.get(i).y == nowy){//
                flag = true;
                
                    if (OpenList.get(i).G > CloseList.get(nowindex).G + 1){//I haven't worked through the thought fully in my head but not sure this wouldn't incorrectly change some values. It seems to assume we can get to anything in the frontier (OpenList) from wherever we are now, and in only one step.
                    OpenList.set(i, Node(nowx, nowy, nowindex, CloseList.get(nowindex).G+1, OpenList.get(i).hvalue));
                    
                    }
                    
                    
                    }


                }

                if (flag == false){ // if it's in neither of open/closelist, add it to the open list
                    //So this is adding the neighbouring node (which one?) to the frontier if it's not already been explored or added to the frontier
                    //If it's already in the frontier you check if you've found a shorter path (the code immediately above)
                    //If it's already in the CloseList, you just ignore it. I think this is the scenario I talked about at the end of my rant and while I am 
                        //kinda sure it won't be necessary I think we shouldn't ignore it, but instead check the value we've given it (should be stored in the Node
                        //class in our implementation) and if the new value is smaller put it back in the frontier.
                    // If we do so we don't need a CloseList, this was the last thing I needed to check before I could say that.
                    OpenList.add(Node(nowx, nowy, nowindex, CloseList.get(nowIndex).G + 1), Math.abs(endx - nowx) + Math.abs(endy - nowy))

                }
            }

            //Finding the next one we want to search, like you say \/
            if (OpenList.size() != 0){ // select the one with least Fvalue in the openlist and set it as the location of search.
            int Ftemp = OpenList.get(0).F;
            int indextemp = 0;
            for (int i=0; i < OpenList.size(); i++){
                if (OpenList.get(i).F > Ftemp){      //Is this meant to be '<'? Otherwise we're picking the largest F value we can find in OpenList?
                    Ftemp = OpenList.get(i).F;
                    indextemp = i;
                }
                else if (OpenList.get(i).F == Ftemp){ //if Fvalue is the same, pick the one with less Hvalue
                if(OpenList.get(i).H < OpenList.get(indextemp).H) {
                    indextemp = i;
                }
                }
            }
            CloseList.add(OpenList.get(indextemp)); //Add the neighbouring node with the lowest F
            OpenList.remove(indextemp); //removing from openList and add location of search to closelist
            nowindex ++; //The one we've newly added becomes our currentnode that we'll expand
            }
            else { //if open list is empty, there is no path
                System.out.println("There is no location, LOCATION ERROR");
                break;
            }
        }
        while (nowindex != -1){ //backtracking from the goal, reversing the path 
            int[] pathtemp = { CloseList.get(nowindex).x, CLosesList.get(nowindex).y};
            Path.add(pathtemp); // + " " + CloseList.get(nowindex).y);
            nowindex = CloseList.get(nowindex).p_index;     //What is p_index and where did we set its value?
            //Oh wait I think I get it, it's the previous index? So it points to the one we visited before it? So we'd add a line every time we add 
                //to CloseList that saves the now_index as its p_index I guess. But if we find a shorter path we'd want to 
        
        }

        while (Path.size() != 0) { //re-print the "already back-tracked path"
        System.out.println(Path.get(Path.size() - 1)[0]) + " " + Path.get(Path.size() - 1)[1];
        Path.remove(Path.size() - 1);

        }
    
    }
}
