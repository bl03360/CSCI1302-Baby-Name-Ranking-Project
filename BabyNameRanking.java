/**
 * 
 */
package cs1302.projects;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

/**
 * A class that scans the user for the year, gender, and name of a baby. Where it then reads an URL file of baby names and rankings of the year 
 * inputed and gives the user the ranking. While also making a query file for that information
 * @author Brandon Lewis
 *
 */
public class BabyNameRanking {

	/**
	 * Calls on the readUserInfo method
	 * @param args
	 */
	public static void main(String[] args) {
		readUserInfo();
	}
	
	/**
	 * This method prompts the user to enter year, gender and name. 
	 */
	public static void readUserInfo() {
		@SuppressWarnings("resource")
		Scanner scan = new Scanner(System.in);
		
		System.out.print("Enter the year: ");
		String year = scan.next();

		System.out.print("\nEnter the gender: ");
		char gender = scan.next().charAt(0);
		
		
		System.out.print("\nEnter the name: ");
		String name = scan.next();
		
		showRankingInfo(year, gender, name);
		
	}
		
	/**
	 * This method gets the user input, reads the appropriate file and displays the ranking information.
	 * @param year baby's year born
	 * @param gender sex of baby
	 * @param name babys' name
	 */
	public static void showRankingInfo(String year, char gender, String name) {

		ArrayList<String> rankofBaby = new ArrayList<>();
		
		System.out.println("\n======Ranking Information:======");
		
		Date date = new Date();
			
		try {
		URL website = new URL("http://liveexample.pearsoncmg.com/data/babynameranking" + year + ".txt");
		@SuppressWarnings("resource")
		Scanner inputStream = new Scanner(website.openStream());
		
		while (inputStream.hasNext()) {
			for (int i = 0; i < 5; i++) {
			     	rankofBaby.add(i, inputStream.next());
			    }//adds information in the url file to the arraylist for each line
			if (rankofBaby.get(gender(gender)).equals(name)) {
				System.out.println("\n" + name + " is ranked #" + rankofBaby.get(0) + " in year " + year);
				String filename = "Query Info: " + date + " - " + name + " is ranked #" + rankofBaby.get(0) + " in year " + year;
				logInfoIntoFile(filename);	
				toContinue();
				}//if name is in the file and matches userinputted name, logged and continued
			}
			if (!rankofBaby.get(gender(gender)).equals(name)) {
				System.out.println("\n" + name + " is not ranked in year " + year);
				toContinue();
			}//if name is not in the file and doesnt match, error message and continued
		}
		catch (java.io.FileNotFoundException e) {
			System.out.println("\nhttp://liveexample.pearsoncmg.com/data/babynameranking" + year + ".txt is not a proper URL");
			toContinue();
		}
		catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	
	/**
	 * This method returns a number of where to check in the arraylist for names
	 * @param gender Sex of child
	 * @return 1 If M/m for gender, 3 If F/f for gender, 0 If char is neither M/m or F/f Prints invalid message and 
	 */
	public static int gender(char gender) {
		
		if (gender == 'M' || gender == 'm') {
			return 1;
		}
		else if (gender == 'F' || gender == 'f') {
			return 3;
		}
		else System.out.println("You have entered an invalid gender");
		toContinue();
		return 0;
	}
	
	/**
	 * This method scans if the user wants to continue, exits system if no
	 */
	public static void toContinue() {
		@SuppressWarnings("resource")
		Scanner scan = new Scanner(System.in);
		
		System.out.print("\nDo you want to continue? (y/n): ");
		String cont = scan.next();
		
		if (cont.equals("y") || cont.equals("Y")) {
			System.out.println();
			readUserInfo();
		}
		else System.out.println("\nHave a nice time!"); 
			 System.exit(0);
	}
	
	/**
	 * This method creates searchResults.txt file and writes the ranking information into the file
	 * of if file already exist add new information to next line
	 * @param filename Query for file that includes the date, name, and rank
	 */
	public static void logInfoIntoFile(String filename) {
		
		try {
			File file = new File("searchResults.txt");
			FileWriter fw = new FileWriter(file, true);
			BufferedWriter bw = new BufferedWriter(fw);
			if (file.exists()) {
				bw.append(filename);
				bw.newLine();
				bw.close();
			}
			else if (!file.exists()) {
				bw.write(filename);
				bw.newLine();
			}
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
