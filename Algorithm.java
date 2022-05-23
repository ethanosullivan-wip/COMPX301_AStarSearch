import java.util.priorityQueue;
import java.util.ArrayList;

public class Algorithm {
    static PriorityQueue<Node> OpenList = new PriorityQueue<Node>();
    static PriorityQueue<Node> CloseList = new PriorityQueue<Node>();
    static ArrayList<int[]> Path = new ArrayList<int[]>(); // Where we will store the path?.. this is what you have mentioned in the documentation

    // Found a code where it uses a path arrayList and it had a code about backtracking at the bottom of the page, (from line 84)
    // I am using the AI coding program (the one i told you last time) and its suggestions pretty much took me to the end point
    // maybe unnatural here and there but it should help us getting the idea of how we should apply this to our assignment

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
                else if (way == 2) nowy--;
                else if (way == 3) nowy++;

                for (int i = 0; i < CloseList.size(); i++){
                    if (CloseList.get(i).x == nowx && CloseList.get(i).y == nowy){//this AI coding is so great wow it came up with this current line by itself
                    flag = true;

                    } 
                }

                for (int i= 0; i< OpenList.size(); i++){ //if it already exist in open list, compare gValue, and if gValue is smaller, change its parent's index and its hvalue
                
                if(OpenList.get(i).x == nowx && OpenList.get(i).y == nowy){//
                flag = true;
                
                    if (OpenList.get(i).G > CloseList.get(nowindex).G + 1){//
                    OpenList.set(i, Node(nowx, nowy, nowindex, CloseList.get(nowindex).G+1, OpenList.get(i).hvalue));
                    
                    }
                    
                    
                    }


                }

                if (flag == false){ // if it's in neither of open/closelist, add it to the open list
                    OpenList.add(Node(nowx, nowy, nowindex, CloseList.get(nowIndex).G + 1), Math.abs(endx - nowx) + Math.abs(endy - nowy))

                }
            }

            if (OpenList.size() != 0){ // select the one with least Fvalue in the openlist and set it as the location of search.
            int Ftemp = OpenList.get(0).F;
            int indextemp = 0;
            for (int i=0; i < OpenList.size(); i++){
                if (OpenLst.get(i).F > Ftemp){
                    Ftemp = OpenList.get(i).F;
                    indextemp = i;
                }
                else if (OpenLst.get(i).F == Ftemp){ //if Fvalue is the same, pick the one with less Hvalue
                if(OpenList.get(i).H < OpenList.get(indextemp).H) {
                    indextemp = i;
                }
                }
            }
            CloseList.add(OpenList.get(indextemp));
            OpenList.remove(indextemp); //removing from openList and add location of search to closelist
            nowindex ++; 
            }
            else { //if open list is empty, there is no path
                System.out.println("There is no location, LOCATION ERROR");
                break;
            }
        }
        while (nowindex != -1){ //backtracking from the goal, reversing the path 
            int[] pathtemp = { CloseList.get(nowindex).x, CLosesList.get(nowindex).y};
            Path.add(pathtemp); // + " " + CloseList.get(nowindex).y);
            nowindex = CloseList.get(nowindex).p_index;
        
        }

        while (Path.size() != 0) { //re-print the "already back-tracked path"
        System.out.println(Path.get(Path.size() - 1)[0]) + " " + Path.get(Path.size() - 1)[1];
        Path.remove(Path.size() - 1);

        }
    
    }
}