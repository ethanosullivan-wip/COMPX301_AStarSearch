//Kevin Kim 1592254
//Ethan O'Sullivan 1539567

import java.io.*;
import java.util.*;
import java.awt.Point;
//import java.lang.IllegalArgumentException;
//Pretty sure IllegalArgumentException is an unchecked exception and doesn't have to be acknowledged or caught by this class.
// Another reason not to import it is I could just catch it in a general catch block.

/*

*/
class AStar {
	
	public static void main(String[] args) {
		
		//Check we have (correct) input
		if ( args.length != 4 ) {
			System.err.println("Incorrect input. Usage: java AStar [filename] [start_index] [end_index] [maximum_step_distance]");
			return;
		} //implicit else
		
		String filename = args[0];
		
		int start_index, end_index;
		double d;
		PriorityQueue<Node> frontier; 
		
		double largestX = 0;
		double largestY = 0;	//for graphing porpoises
		
		try {
			start_index = Integer.parseInt(args[1]);
			end_index = Integer.parseInt(args[2]);
			d = Double.parseDouble(args[3]);
		}
		catch (Exception e) {
			System.err.println("Error with input indexes or distances. Ensure you input real numbers, and integers for indexes");
			return;
		}
		if ( start_index < 0 || end_index < 0 || d < 0) {
			System.err.println("Please input positive numbers");
			return;
		}
		//Scale d to match the scaling we do to star coordinates later
		d *= 100;
		
		ArrayList<Node> stars = new ArrayList<Node>();	//Stores the inputted stars.
		
		try {
			BufferedReader bruh = new BufferedReader(new FileReader(filename));

			String line = bruh.readLine();
			String[] values;
			
			//largestX = 0;
			//largestY = 0;	//Stores the largest values for graphing purposes
			
			int lineCount = 0;	//Indexed starting at 0. (this effects stars and error messages so far)
			while (line != null) {
				float x, y;
				int scaledX, scaledY;
				
				values = line.split(",");
				
				//Check the right number of values was split
				if ( values.length != 2 ) {
					System.err.println("The input file contained an incorrectly formatted line. There should be 3 elements per line.");
					System.err.println("Error occurred on line:" + lineCount);
					return;		//Leaves the whole program, bit of a fit to throw for one error but we don't want the user to waste their time getting an incorrect answer
				}
				//Try to parse the input
				try {
					x = Float.parseFloat(values[0]);
					y = Float.parseFloat(values[1]);
				}
				catch (NumberFormatException e) {
					System.err.println("Number in input file was formatted incorrectly on line:" + lineCount);
					System.err.println("Was expecting an integer.");
					System.err.println("Error occurred on line:" + lineCount);
					return;
				}
				
				//Check if we've found a new largest X or Y:
				if ( largestX < x )
					largestX = x;
				if ( largestY < y )
					largestY = y;
				
				
				//I'm still going to assume that all stars have at most 2 decimal places (less will still work).
				//	despite trap.csv having only integers raising my suspicions, they did say this was an option
				//	So will *100 and convert to int. Worst case we lose some data/accuracy.
				//I'm willing to make that sacrifice...
				
				//Blindly lose anything beyond 2 decimal places, convert to int
				scaledX = Math.round(x*100);
				scaledY = Math.round(y*100);
				
				//Can't calculate the heuristic yet since we have to read in the destination star first				
				
				Node newStar = new Node(lineCount, scaledX, scaledY);
				
				stars.add(newStar);
				
				lineCount++;
				line = bruh.readLine();
			}
			System.out.println("input file read succesfully");
		}
		catch (FileNotFoundException e) {
			System.err.println("That is not a valid file name.");
			System.err.println("Debug info:");
			e.printStackTrace();
		}
		catch (IOException e) {
			System.err.println("Error while reading the file");
			System.err.println("Debug info:");
			e.printStackTrace();
		}
		
		//---------Stars fully inputted
		
		//Check for wrong input
		if ( start_index >= stars.size() || end_index >= stars.size() ) {
			System.err.println("start or end star is outside of the range of stars inputted.");
			System.err.println("maximum index with this data: " + (stars.size() - 1));
			return;
		}
		
		//We have all the stars so we might as well draw the graph now
		ArrayList<Point> dots = new ArrayList<Point>();
		for (int i = 0; i < stars.size(); i++) {
			Point newPoint = new Point(stars.get(i).x / 100, stars.get(i).y / 100);
			dots.add(newPoint);
		}
		
		GUI gui = new GUI(dots, largestX, largestY);
		
		//Create a structure to save distances in.
		double[][] distances = new double[stars.size()][stars.size()];
		
		//Calculate and store distances
		//We know arraylists add to the end of the list so of course the arraylist of stars is still in order thankfully
		int dx, dy;
		double dist;
		for (int i = 0; i < stars.size(); i++) {
			
			//Might as well calculate the heuristic distance in this loop too
			//get horizontal and vertical distance between this and the goal star
			dx = stars.get(i).x - stars.get(end_index).x;
			dy = stars.get(i).y - stars.get(end_index).y;
			//Calculate euclid distance
			//dist = fast_approx_distance(dx, dy);
			dist = Math.sqrt(dx*dx + dy*dy);
			
			stars.get(i).hValue = dist;
			
			for (int j = 0; j < stars.size(); j++) {
				//Don't recalculate previously stored distances
				if ( !(j < i) ) {
					//get horizontal and vertical distance between them
					dx = stars.get(i).x - stars.get(j).x;
					dy = stars.get(i).y - stars.get(j).y;
					//Calculate euclid distance
					//dist = fast_approx_distance(dx, dy);
					dist = Math.sqrt(dx*dx + dy*dy);
						//Honestly haven't noticed the difference between these for the files we've been given, maybe if there were more stars I'd notice
					
					//Check if it's within max_step_distance
					if (dist <= d) {
						distances[i][j] = dist;
						distances[j][i] = dist;
					}
					else {
						distances[i][j] = -1;
						distances[j][i] = -1;
					}
				}
			}
		}
		
		//-------------------------------------------------------------------------------------------------------------------------
		//-------------------------------------------------------------------------------------------------------------------------
		//-----------------------A Star Search-------------------------------------------------------------------------------------
		//-------------------------------------------------------------------------------------------------------------------------
		//-------------------------------------------------------------------------------------------------------------------------
		//-------------------------------------------------------------------------------------------------------------------------
		//-------------------------------------------------------------------------------------------------------------------------
		
		frontier = new PriorityQueue<Node>();
		
		//Set start_node's G value to 0				This ensures no one chooses to put it into the frontier again
		stars.get(start_index).setGValue(0);
		
		//Put the startnode on the frontier	--Pointless, it's taken off immediately
		//frontier.add(stars.get(start_node));
		//"Remove" top node from "frontier" ("currentNode")
		Node currentNode = stars.get(start_index);
		
		//technically don't need the following as we could simply chekc whether currentNode.index == 
		boolean noPath = false;		//false if a path can be found, set to true when we find out there is none
		//Loop until we've found end_node
		while (currentNode.index != end_index) {
			
			
			//Search the array for nodes connected to currentnode, add any that are to frontier (BUT NOT UNTIL WE'VE CALCULATED SOME STUFF (i think))
			for (int i=0; i < stars.size(); i++) {
				Node currentNeighbour = stars.get(i);
				double neighbArc = distances[currentNode.index][i];		//Distance between currentNode and this neighbour, -1 if no connection
				//If current node is connected to neighbour node and we've found a better path
				if ( neighbArc != -1 && currentNeighbour.getGValue() > ( currentNode.getGValue() + neighbArc ) ) { 
					//Debug
					//System.err.println("Putting on the frontier: " + currentNeighbour.index );
					
					//Remove one other instance of them already in the frontier, if any (should only ever be one at a time) (Honestly really curious if PriorityQueues automatically changes their position anyway but playing it safe)
					if ( currentNeighbour.getGValue() != Double.POSITIVE_INFINITY ) 
						frontier.remove(currentNeighbour);
					//Add currentNode's G value to the distance we found for this neighbouring node to calculate its G value
					currentNeighbour.setGValue( neighbArc + currentNode.getGValue() );	//Node class auto-updates FValue here
					//Save currentNode as their p[revious]Node;
					currentNeighbour.pNode = currentNode;
					//Would also save that they've been visited but now realising that may not be necessary at all
					//Add to the frontier
					frontier.add(currentNeighbour);
				}
			}
			
			//Check there is still a possible path to explore
			if ( frontier.peek() == null ) {
				System.out.println("No path found");
				noPath = true;
				gui.popup();
				break;
			}
			/*
			//Debug
			System.out.println(frontier.peek().index);
			System.out.println("G Value:" + frontier.peek().getGValue());
			System.out.println("F Value:" + frontier.peek().fValue);
			*/
			//Remove top node from list (new "currentNode")
			currentNode = frontier.poll();
		}	//repeat
		//When we leave the loop, either currentNode is the end_node or it is the last node we explored before running out of options
		
		if ( noPath ) {
			return;
		}
		
		//Scale path length back down before announcing it
		double scaledPath = currentNode.getGValue()  / 100;
		
		//For graph dolphin
		ArrayList<Integer> resultIndexes = new ArrayList<Integer>();
		
		System.out.println("Shortest path is " + scaledPath + " long and goes through (in reverse order):");
		Node backtracker = currentNode;
		int starCount = 0;
		while ( backtracker != null  && starCount != 6000 ) { //For now putting a wee manual check to see it doesn't get stuck here forever but could use visited property for this.
			
			resultIndexes.add(backtracker.index);
			
			System.out.println("Star number #" + backtracker.index);
			backtracker = backtracker.pNode;
			starCount++;
		}
		System.out.println("Number of stars travelled to: " + starCount);
		
		gui.paintPath(resultIndexes);
		
		
	}
	
	/**
		Takes the horizontal and vertical distance between two points and returns the euclidean diagonal distance
	**/
	private static int fast_approx_distance(int dx, int dy) {
		int max, min, approx;
		//Get the absolute values of dx and dy
		if ( dx < 0 ) dx = -dx;
		if ( dy < 0 ) dy = -dy;
		
		//determine which is smaller
		if ( dx < dy ) {
			min = dx;
			max = dy;
		}
		else {
			min = dy;
			max = dx;
		}
		//Do the multiplication part of the approximation
		approx = (max * 1007) + (min * 441);
		//Make adjustments to improve inaccurate areas
		if (max < (min << 4)) {		//Check if the larger value is 16+ times the size of min
			approx -= max*40;
		}
		
		//The step including adding 512 for proper rounding I think is unnecessary in java. Will assume that's the case for now anyway.
		//return (approx + 512) >> 10;
		return (approx) >> 10;
	}
}