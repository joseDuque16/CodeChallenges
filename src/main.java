import java.util.ArrayList;
import java.io.*;

/* The attached text file contains the favorite musical artists of 1000 users from 
Some Popular Music Review Website. Each line is a list of up to 50 artists, 
formatted as follows: 

Radiohead,Pulp,Morrissey,Delays,Stereophonics,Blur,Suede,Sleeper,The La's,
Super Furry Animals,Iggy Pop\n
Band of Horses,Smashing Pumpkins,The Velvet Underground,Radiohead,
The Decemberists,Morrissey,Television\n 
etc. 

Write a program that, using this file as input, produces an output file 
containing a list of pairs of artists which appear TOGETHER in at least 
fifty different lists. For example, in the above sample, Radiohead and 
Morrissey appear together twice, but every other pair appears only once. 
Your solution should be a csv, with each row being a pair. For example: 

Morrissey,Radiohead\n 

Your solution MAY return a best guess, i.e. pairs which appear at least 
50 times with high probability, as long as you explain why this tradeoff 
improves the performance of the algorithm. Please include, either in 
comments or in a separate file, a brief one-paragraph description of 
any optimizations you made and how they impact the run-time of the algorithm. */

/* ALgorithm basic documentation
   Step 1 - Parse the folder storing each line into a dynamic array
   Step 2 - Split the string into a list, with each band name being an item in the list
   Step 3 - First thing is first, we need the list item to appear at least 50 times
   Step 4 - perform a count of every band on the list. If the number of times a band appears is
            less than 50, remove them from the list.
   Step 5 - Now that we have a list of potential candidates, we pick two items from the first list
            and check that both items exist in all of the other lists 50 times. If they do we add the pair
            to an arraylist of found lists. Now we pick another two items from the first list such that the pair
            we select does not exist in an arraylist of failed couples. Until we are done with every
            possible permutation of a list        
   
*/
// Started at 6pm on saturday pause at 6:29, continuing 6:32 to 7:15, continuing at 740 - 8:15
// Continued at 2:32 pm finished at 2:50
// TODO: add unicode support
public class main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// TODO: Perform file processing
		File fileIn = new File("./Artist_lists_small.txt");
		String inputAsString = "";
		BufferedReader br;
		
		try {// Store the file as a string
			br = new BufferedReader(new FileReader(fileIn));
			String st; 
			while ((st = br.readLine()) != null) {
				inputAsString += st;}
		} catch (IOException e) {
			e.printStackTrace();
		} 
		
		  
		

		
		String inputAsString2 = "Radiohead,Pulp,Morrissey,Delays,Stereophonics,Blur,Suede,Sleeper,The La's," + 
				"Super Furry Animals,Iggy Pop\n" + 
				"Band of Horses,Smashing Pumpkins,The Velvet Underground,Radiohead," + 
				"The Decemberists,Morrissey,Television\\n ";
		
		// Implement algorithm on the input string
		itemCoupleFindingAlgorithm test1 = new itemCoupleFindingAlgorithm(inputAsString);
		
	
		
	}

}
