/**
 * 
 * This project tasks us with utilizing hashtables, along with assigning linked list values
 * into an array of linked lists based on the ASCII values of each state's name and death rate.
 * The program successfully catches user error when entering integers, and ensures proper notification
 * of any unintentional user errors(i.e. deleting a state that's already deleted, search for a deleted
 * state versus searching for a nonexistant state etc.).
 * 
 * @author Keath Sawdo
 * @version: 4/18/21
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) throws FileNotFoundException
	{
		System.out.println("COP3530 Project 5");
		System.out.println("Instructor: Xudong Liu\n");
		
		Scanner scan = new Scanner(System.in).useDelimiter("\n");
		System.out.print("Please enter the file name: ");
		String fileName = scan.nextLine();
		Scanner inFile = null;
		try
		{																					
			inFile = new Scanner(new File(fileName));
		}
		catch (FileNotFoundException e)
		{
			System.out.println("\nCould not open file!");
			System.exit(1);
		}
		
		inFile.useDelimiter(",|\n");
		inFile.nextLine();
		HashTable states = new HashTable(101);
		String menuChoice = null;
		while(inFile.hasNext())
		{
			String name = inFile.next();
			String capital = inFile.next();
			String region = inFile.next();
			int USHouse = inFile.nextInt();
			int population = inFile.nextInt();											
			int cases = inFile.nextInt();
			Double caseRate = (double) cases/population * 100000;		//unprovided attributes are directly calculated based off of related attribute values, no methods.
			int deaths = inFile.nextInt();
			Double deathRate = (double) deaths/population * 100000;
			Double caseFatalityRate = (double) deaths/cases;
			int medianHouseholdIncome = inFile.nextInt();
			double crimeRate = Double.parseDouble(inFile.next());
			states.insert(name, population, deaths);
		}
		
		do
		{
			System.out.println("\n1) Print hash table\n2) Delete a state of a given name\n"
					+ "3) Insert a state of a given name\n4) Search and print a state and its DR for a given name\n5)"
					+ " Print numbers of empty cells and collisions\n6) Exit");													//displaying menu of options for user
			System.out.print("Enter your choice: ");
			menuChoice = scan.nextLine();
			boolean isNum = menuChoice.matches("-?\\d+(\\.\\d+)?");
			while(isNum == false || Integer.parseInt(menuChoice) > 6 || Integer.parseInt(menuChoice) <= 0)						//loop to check whether the user's entry is an integer,
			{																													//along with it being within the menu option's bounds.
				System.out.print("Invalid choice enter 1-6: ");
				menuChoice = scan.nextLine();
				isNum = menuChoice.matches("-?\\d+(\\.\\d+)?");
			}
			System.out.println();

			switch(menuChoice)
			{
				case "1":
				{
					states.displayTable();																						//option 1 displays the hashtable
					break;
				}
				
				case "2":
				{
					System.out.print("\nEnter a state: ");
					String state = scan.nextLine();
					if(states.CSVsearch(state) == null)
					{
						System.out.println(state + " is not a state.");															//searches the csv file for a state with the given name
						break;
					}
					int result = states.Find(states.CSVsearch(state).name, states.CSVsearch(state).deaths, states.CSVsearch(state).population);				//the linked list array is addressed at the found states hash function index
					if(result != -1)
					{
						states.delete(states.CSVsearch(state).name, states.CSVsearch(state).population, states.CSVsearch(state).deaths);					//if found within the linked list, the state is successfully removed.
						System.out.println(state + " was deleted.");
					}
					if(result == -1)																													//if the state was found in the csv but not the linked list, it was already removed
					{																																	//, thus notifying the user with a different message.
						System.out.println(state + " is already deleted");
					}
					break;
				}
				
				case "3":
				{
					System.out.print("\nEnter a state: ");
					String state = scan.nextLine();
					if(states.CSVsearch(state) == null)																							//csv is searched for the state with the given name
					{
						System.out.println(state + " is not a state.");
						break;
					}
					int result = states.Find(states.CSVsearch(state).name, states.CSVsearch(state).deaths, states.CSVsearch(state).population);									//linked list is searched at the hash functions index
					if(result == -1)
					{	
						states.insert(states.CSVsearch(state).name, states.CSVsearch(state).deaths, states.CSVsearch(state).population);
						System.out.println(state + " was inserted.");
					}
					if(result == 1)
					{
						System.out.println(state + " is already inserted.");
					}
					break;
				}
				
				case "4":
				{
					System.out.print("\nEnter a state: ");
					String state = scan.nextLine();
					if(states.CSVsearch(state) == null)
					{
						System.out.println(state + " is not a state.");
						break;
					}
					int result = states.Find(states.CSVsearch(state).name, states.CSVsearch(state).deaths, states.CSVsearch(state).population);
					if(result != -1)																													//after determing that the state exists both within the csv file and linked list, it's location and deathrate are displayed.
					{
						System.out.println(state + " is found at index " + result + " with an DR of " + String.format("%.2f", ((states.CSVsearch(state).population / states.CSVsearch(state).deaths) * 100000)));
					}
					else
					{
						System.out.println(state + " could not be found.");																			//if the state exists within the csv file, but is deleted from the linked list, a different message is prompted.
					}
					break;
				}
				
				case "5":
				{	
					String[] count = states.emptyAndCollisionCounter().split(",");																//to allow a multi-valued return, the empty and collision counts are split in a string.
					System.out.println("There are " + count[0] + " empty cells and " + count[1] + " collisons in the hashtable");
					break;
				}
				
				case "6":
				{
					
					break;
				}
			}
		}while(!menuChoice.equals("6"));
	}
}