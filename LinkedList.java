/**
 * 
 * This class handles all of the linked lists within the array, including the
 * modification of their nodes. This class successfully deletes, inserts, finds,
 * and counts the amount of collided cells within a specified linked list.
 * 
 * @author Keath
 *
 */
public class LinkedList 
{
	public Node first;
	public Node last;
	public Node current;
	public Node previous;
	public HashTable h;
	
	public LinkedList()
	{
		first = null;
		last = null;
		current = null;
	}	
	
	/**
	 * 
	 * This method is responsible for inserting a node into a linked list
	 * based on it's hash value. If another node exists, it's simply placed
	 * continuously after last.
	 * 
	 * @param s
	 */
	public void insert(Node s)
	{
		if(isEmpty())
		{
			first = s;
		}
		else
		{
			last.nextNode = s;
		}	
		last = s;
	}
	
	/**
	 * 
	 * This method is responsible for locating and deleting the node
	 * within a linked last that contains the same name as the user's
	 * entered state name. Each possible case for a deletion is accounted for.
	 * 
	 * @param key
	 * @return
	 */
	public int delete(String key)
	{
		current = first;
		while(current != null && !current.name.equals(key))
		{
			if(current.nextNode == null)
			{
				return -1;
			}
			if(current == first)
			{
				previous = current;
			}
			else
			{
				previous = current;
			}
			current = current.nextNode;
		}
		if(current == first && current == last)
		{
			first = first.nextNode;
			last = last.nextNode;
			return 1;
		}
		if(current == first)
		{
			first = first.nextNode;
			return 1;
		}
		if(current == last)
		{
			previous.nextNode = last.nextNode;	
			last = previous;
			return 1;
		}
		else 
		{
			previous.nextNode = current.nextNode;
		}
		return 1;
	}
	
	/**
	 * 
	 * This method checks whether or not the linked list is empty.
	 * 
	 * @return
	 */
	public boolean isEmpty()
	{
		return (first == null);
	}
	
	/**
	 * 
	 * This method is responsible for locating the state within the linked list,
	 * based on the user's entered state name.
	 * 
	 * @param key
	 * @return
	 */
	public int find(String key)
	{
		Node current = first;
		while(current != null)
		{
			if(current.name.equals(key))
			{
				return 1;
			}
			current = current.nextNode;
		}
		return -1;
	}
	
	/**
	 * 
	 * This method displays each node (according to the node classes method) in a uniform
	 * manner, along with providing proper spacing to lists with multiple nodes for a better
	 * ease of reading.
	 * 
	 */
	public void display()
	{
		Node current = first;
		int count = 0;
		while(current != null)
		{
			if(current != first)
			{
				System.out.printf("    ");
				current.printNode();
				current = current.nextNode;
			}
			else
			{
				current.printNode();
				current = current.nextNode;
			}
		}
	}
	
	/**
	 * 
	 * This method counts every list within the array
	 * that contains more than a single node.
	 * 
	 * @return
	 */
	public int collisionCounter()
	{
		Node current = first;
		int count = 0;
		while(current != null)
		{
			current = current.nextNode;
			count++;
			if(count > 1)
			{
				return 1;
			}
		}
		return 0;
	}
	

}
