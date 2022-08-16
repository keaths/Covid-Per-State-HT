/**
 * 
 * This class is responsible for handling the hashtable. Methods include searching, deleting, locating,
 * and counting empty/collided cells within the linked lists. Each state is given a hash value based on
 * it's attributes, states with the same hash value are indexed into the same linked list. If no hash
 * value is given to an index, it's counted as empty.
 * 
 * @author Keath Sawdo
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class HashTable 
{
	public LinkedList[] hashArray;
	public int arraySize;
	public int stateCount;											

	
	public HashTable(int size)										
	{
		arraySize = size;
		hashArray = new LinkedList[arraySize];
	}
	
	/**
	 * 
	 * This method iterates through the array of linked lists, and displays them
	 * according to their contents. Populated indexes recieve a counter index,
	 * empty ones do not.
	 * 
	 */
	public void displayTable()								
	{														
		System.out.println("Table:\n");	
		int count = 0;
		for(int j = 0; j < arraySize; j++)
		{
			if(hashArray[j] != null)
			{
				System.out.print(j + ") ");					
				hashArray[j].display();
			}
			else
			{
				count++;								
				System.out.println("Empty");				
			}
		}
		System.out.println("");
	}
	
	/**
	 * 
	 * This method calculates the hash value of each state based on it's
	 * name, deaths, and population.
	 * 
	 * @param key
	 * @return
	 */
	public int hashFunc(int key)
	{
		return key % arraySize;
	}
	
	/**
	 * 
	 * This method is responsible for inserting nodes into their
	 * respective index (based on their hash value) in the array
	 * of linked lists.
	 * 
	 * @param name
	 * @param population
	 * @param death
	 */
	public void insert(String name, double population, double death)
	{		
		int hashval = hashFunc(keyMaker(name, population, death));			
		Node s = new Node (name, population, death);
		if(hashArray[hashval] == null)
		{
			LinkedList ten = new LinkedList();
			hashArray[hashval] = ten;
			stateCount++;
		}
		hashArray[hashval].insert(s);
	}
	
	/**
	 * 
	 * This method is responsible for producting a key to create
	 * a hash value for each state to be inserted into regarding 
	 * the array of linked lists.
	 * 
	 * @param name
	 * @param population
	 * @param death
	 * @return
	 */
	public int keyMaker(String name, double population, double death)
	{
		int i;
		int nameVal = 0;
		for(i = 0; i < name.length(); i++)
		{
			nameVal+= (int)(name.charAt(i));
		}
		int hashval = hashFunc(nameVal + (int)population + (int)death);
		return hashval;
	}
	
	/**
	 * 
	 * This method is responsible for iterating through the csv file to locate
	 * a state with the same name as the user's entry. If found, 1 is returned to 
	 * signify it was found, elsewise, -1 is returned.
	 * 
	 * @param stateName
	 * @return
	 * @throws FileNotFoundException
	 */
	public Node CSVsearch(String stateName) throws FileNotFoundException
	{
		Scanner inFile = new Scanner(new File("States5.csv"));
		inFile.useDelimiter(",|\n");
		inFile.nextLine();
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
			if(name.equals(stateName))
			{
				Node result = new Node(name, deaths, population);
				return result;
			}
		}
		return null;
	}
	
	/**
	 * 
	 * This method is responsible for locating the specific state node within
	 * the Linked list array at the designated hash value index, once it's been
	 * confirmed it exists within the CSV file that is. If the state is found, 1 is returned,
	 * else, if the state is not found (meaning it's already been deleted), -1 is returned.
	 * 
	 * @param name
	 * @param deaths
	 * @param pop
	 * @return
	 */
	public int Find(String name, Double deaths, Double pop)
	{		
		int index = hashFunc(keyMaker(name, deaths, pop));
		int searchResult = hashArray[index].find(name);
		if(searchResult == 1)
		{
			return 1;
		}
		else
		{
			return -1;
		}
	}
	
	/**
	 * 
	 * This method is responsible for deleting the designated node
	 * within the linked list array.
	 * 
	 * @param name
	 * @param deaths
	 * @param pop
	 */
	public void delete(String name, Double deaths, Double pop)
	{		
		int index = hashFunc(keyMaker(name, deaths, pop));
		hashArray[index].delete(name);
	}
	
	/**
	 * 
	 * This method is responsible for counting the total amount of empty and collided
	 * cells within the array of linked lists. The empty and collided cells are converted
	 * into a string, seperated by a comma, and delimited within main.
	 * 
	 * @return
	 */
	public String emptyAndCollisionCounter()
	{
		int emptyCount = 0;
		int collisionCount = 0;
		for(int j = 0; j < arraySize; j++)
		{
			if(hashArray[j] == null)
			{
				emptyCount++;
			}
			else
			{
				collisionCount += hashArray[j].collisionCounter();
			}
		}
		String fullCount = Integer.toString(emptyCount) + "," + Integer.toString(collisionCount);
		return fullCount;
	}
	
	
	
}
