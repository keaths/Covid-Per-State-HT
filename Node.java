/**
 * 
 * This class is responsible for assigning the values for each state within the linked list,
 * along with providing a uniform display for all the given states.
 * 
 * @author Keath
 *
 */
public class Node 
{
	String name;
	double population;
	double deaths;
	Node nextNode;
	
	public Node(String name, double population, double deaths)
	{
		this.name = name;
		this.population = population;
		this.deaths = deaths;
	}
	
	public void printNode() 
	{
		System.out.printf("%-30s %-20.2f\n", name, (double)deaths/population * 100000);
	}
}
