import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;

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

public class itemCoupleFindingAlgorithm {
	
	ArrayList <String[]> userBandPicks = new ArrayList<String[]>();
	ArrayList <String> coupleList = new ArrayList<String>();
	ArrayList <String> failedCoupleList = new ArrayList<String>();
	
	// Populates the userBandPicks array
	public itemCoupleFindingAlgorithm(String inputFileAsString, String OutputFileName){
		String[] linesInFile;
		String[] bandsInLine = new String[50]; // a string array of bands picked by each user
		Hashtable<String,Integer> occurrenceCount = new Hashtable<String,Integer>();
		ArrayList<String> uniqueBands = new ArrayList<String>();
		
		linesInFile = inputFileAsString.split("\n");
		
		// STEP 1:
		// Loop through all of the lines, splitting each line into a list of bands
		// Also keep an internal count of the number of occurrences for each band
		for (String bands: linesInFile) {
			bandsInLine = bands.split(",");
			userBandPicks.add(bandsInLine);
			
			// Count band occurrences to narrow down the search to bands that have at least 50 occurrences
			for (String band: bandsInLine) {
				
				// Increment the occurrence index or create a new band occurrence
				if (occurrenceCount.containsKey(band)) {
					occurrenceCount.put(band, occurrenceCount.get(band) +1 );
				}else { occurrenceCount.put(band, 1);
						uniqueBands.add(band);
				}
			}
		}
			
		// loop through each list removing bands that are unpopular picks and do not meet >= 50 pick requirement
		for (int i=0; i <userBandPicks.size(); i++) {
			String[] currentUserList = userBandPicks.get(i);
			ArrayList<String> updatedList = new ArrayList<String>(); 
			String [] updatedArray;

			// Update each user band list with only bands that fit the >= 50 occurrence count
			for (String band: currentUserList) {
					if (occurrenceCount.get(band) >= 50) {
						updatedList.add(band); // store to the end of the updated list
					}
				}
				updatedArray = updatedList.toArray(new String[updatedList.size()]);
				userBandPicks.set(i, updatedArray);
			}
			
		try {
			coupleFinding(OutputFileName);
		}catch (IOException ex) {
			System.out.print("Error occurred writing output list to file");
		}
		
	}
	
	// STEP 2:
	// Now that we have a list of potential candidates, we pick two items from the first list
	// and check that both items exist in all of the other lists 50 times. If they do we add the pair
	// to an arraylist of found lists. Now we pick another two items from the first list such that the pair
	// we select does not exist in an arraylist of failed couples. Until we are done with every
	// possible permutation of a list  
	public void coupleFinding(String outputFileName) throws IOException {
		String output = "";
		BufferedWriter writer = new BufferedWriter(new FileWriter(outputFileName, true));
		
		// find a couple in list a and compare through all the lists
		String[] currentCouple;
		
		// OPTIMIZATION: 
		// Iterate through the remaining user lists, but stop at 49 lists before the end
		// because at that point all of the couples with over 50 occurrences have been found
		for (int x=0; x< userBandPicks.size()-49; x++) { 
			String[] currentUsersList = userBandPicks.get(x);
			
			for (String item1 : currentUsersList) {
				for (String item2 : currentUsersList) {
					if (item1 != item2) {
						String[] option1 = {item1,item2};
						String option1txt = item1 + item2; // Stored as txt to facilitate checking the failed couples/ existing couples
						String option2txt = item2 + item1; // in order to avoid any duplicate prints
						
						// check if [item1, item2] or [item2, item1] exists in either couple or failed couple list
						if (coupleList.contains(option1txt) || failedCoupleList.contains(option1txt) || 
								coupleList.contains(option2txt) || failedCoupleList.contains(option2txt)) {
								continue;
						}else {// Now that a couple has been selected, find the number of occurrences in the other user lists
							// Check if the number of occurrences exceeds the 50 threshold
							if (findNumCoupleOccurrences(option1,x)> 50) {
								coupleList.add(option1txt);
								output = item1 + " , " + item2 + "\n";
								writer.write(output);// Write the result to a file
							}else {
								failedCoupleList.add(option1txt);
								failedCoupleList.add(option2txt);
							}
						}
					}
				}
			}
		}
		
		writer.close(); 
		
	}
	
	// Iterates through the list of user choices, to try to find if a couple exists
	// Additionally use an offset so we dont search from the start every time
	public int findNumCoupleOccurrences(String[] currentCouple, int offset) {
		int count = 0;
		
		
		for (int x=offset; x< userBandPicks.size(); x++) {
			String[] currentUserList = userBandPicks.get(offset);
			int stringCount =0;
			
			// Check if bands exist in all given user lists
			for (String band : currentUserList) {
				if (band == currentCouple[0] || band == currentCouple[1]) {
					stringCount += 1;
				}
			}
			if (stringCount == 2) { count+=1;} // Increase count if both strings found
		}
		
		return count;
	}
	

}
