//I added a comment
import java.io.*;
import java.util.*;
//import java.lang.IllegalArgumentException;
//Pretty sure IllegalArgumentException is an unchecked exception and doesn't have to be acknowledged or caught by this class.
// Another reason not to import it is I could just catch it in a general catch block.


class AStar0 {
	
	public static void main(String[] args) {
		//kevin kim 1592254
		//Check we have (correct) input
		if ( args.length != 4 ) {
			System.err.println("Incorrect input. Usage: java AStar [filename] [start_index] [end_index] [maximum_step_distance]");
			return;
		} //implicit else
		
		String filename = args[0];
		int start_index, end_index;
		double d;
		try {
			start_index = Integer.parseInt(args[1]);
			end_index = Integer.parseInt(args[2]);
			d = Double.parseDouble(args[3]);
		}
		catch (Exception e) {
			System.err.println("Error with input indexes or distances. Ensure you input real numbers, and integers for indexes");
			return;
		}
		if ( start_index < 1 || end_index < 1 || d < 0) {
			System.err.println("Please input positive numbers");
			return;
		}
		//Scale d to match the scaling we do to star coordinates later
		d *= 100;
		
		ArrayList<Star> stars = new ArrayList<Star>();	//Stores the inputted stars.
		
		try {
			BufferedReader bruh = new BufferedReader(new FileReader(filename));
			
			
			//Read input into data structure - priority queue first to save on sorting or arrayList then sort? Also do I even sort?
			//Y'know what? For now we're gonna skip on the sorting idea and just go straight to making connections and calculating distances.
			//That means an unsorted ArrayList for now
			String line = bruh.readLine();
			String[] values;
			int lineCount = 1;	//Indexed starting at 1. (this effects stars and error messages so far)
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
				//I'm still going to assume that all stars have at most 2 decimal places (less will still work).
				//	despite trap.csv having only integers raising my suspicions, they did say this was an option
				//	So will *100 and convert to int. Worst case we lose some data. Or maybe best to throw an error?
				
				//Just making do with what we're given:
				scaledX = Math.round(x*100);
				scaledY = Math.round(y*100);
				
				/*	SEEMS TO ME THIS METHOD IS A BAD IDEA. Floats seem very inaccurate and often add a small amount (~5-6 decimal places down) when multiplied by 100.
					So sure I could develop another method of checking decimals (just literally checking them before multiplying)
					But I think the first option's better anyway.
				//System.err.println(x);
				//Throwing error if it has more than 2 dp:
				x = x*100;
				//System.err.println(x);
				scaledX = (int)x;
				if ( scaledX < x ) {
					System.err.println("Input should only have a maximum of 2 decimal places");
					System.err.println("Error occurred on line:" + lineCount);
					return;}
				
				y = y*100;
				scaledY = (int)y;
				if ( scaledY < y ) {
					System.err.println("Input should only have a maximum of 2 decimal placesYYY");
					System.err.println("Error occurred on line:" + lineCount);
					return;
				}
				*/
				
				//The third alternative of course is to use floats in the Star class and all calculations, but wouldn't that be less efficient?
				
				//Can't calculate the heuristic yet since we have to read in the destination star first				
				
				Star newStar = new Star(lineCount, scaledX, scaledY);
				
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
		
		//Suppose we can continue from here now that the input is done
		
		//Create a structure to save distances in.
			//I'm thinking if we do use a 2D array we could save the values twice, since saving something twice isn't that bad for time complexity and having to reorder
			// parameters before accessing the array later on is (would require an if).
		
		int[][] distances = new int[stars.size()][stars.size()];
		
		//Calculate and store distances
		//We know arraylists add to the end of the list so of course the arraylist of stars is still in order thankfully
		int dx, dy, dist;
		for (int i = 0; i < stars.size(); i++) {
			
			for (int j = 0; j < stars.size(); j++) {
				//Don't recalculate previously stored distances
				if ( !(j < i) ) {
					//get horizontal and vertical distance between them
					dx = stars.get(i).x - stars.get(j).x;
					dy = stars.get(i).y - stars.get(j).y;
					//Calculate euclid distance
					dist = fast_approx_distance(dx, dy);
					//dist = (int)Math.sqrt(dx*dx + dy*dy);
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
		
		//Debug
		for (int[] innerArray : distances ) {
			for ( int distance : innerArray ) {
				System.err.print(distance + " ");
			}
			System.out.println("");
		}
		/*
		for (int i = 0; i < stars.size(); i++) {
			for ( int j = 0; j < stars.size(); j++ ) {
				//System.err.print(distance + ", ");
				System.err.print(i);
			}
			System.out.println("");
		}
		*/
		
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