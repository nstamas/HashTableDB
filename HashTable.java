///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Main Class File:  TestHash
// File:             HashTable
// Semester:         CS367 Spring 2013
//
// Author:           Nick Stamas nstamas@wisc.edu
// CS Login:         stamas
// Lecturer's Name:  Jim Skrentny
// Lab Section:      N/A
//
//                   PAIR PROGRAMMERS COMPLETE THIS SECTION
// Pair Partner:     Calvin Hareng hareng@wisc.edu
// CS Login:         hareng
// Lecturer's Name:  Jim Skrentny
// Lab Section:      N/A
//
//                   STUDENTS WHO GET HELP FROM ANYONE OTHER THAN THEIR PARTNER
// Credits:          CS 367 staff provided skeletons
//////////////////////////// 80 columns wide //////////////////////////////////
import java.io.*;
import java.util.LinkedList;

/**
 * This class implements a hashtable that uses chaining for collision handling.
 * The chains are implemented using LinkedLists.  When a hashtable is created, 
 * its initial size and maximum load factor are specified. The hashtable can 
 * hold arbitrarily many items and resizes itself whenever it reaches 
 * its maximum load factor. Note that this hashtable allows duplicate entries.
 */
public class HashTable<T> {
	/* Add private variables here as needed */
	private LinkedList<T>[] items;
	private double loadFactor;
	double numItems;
	/**
	 * Constructs an empty hashtable with the given initial size and maximum '
	 * load factor. The load factor should be a real 
	 * number greater than 0.0 (not a percentage).  For example, to create a 
	 * hash table with an initial size of 10 and a load factor of 0.85, one 
	 * would use:
	 * <dir><tt>HashTable ht = new HashTable(10, 0.85);</tt></dir>
	 *
	 * @param initSize The initial size of the hashtable.  If the size is less
	 * than or equal to 0, an IllegalArgumentException is thrown.
	 * @param loadFactor The load factor expressed as a real number.  If the
	 * load factor is less than or equal to 0.0, an IllegalArgumentException is
	 * thrown.
	 **/

	public HashTable(int initSize, double loadFactor) {
		//Checks for a valid table size
		if(initSize <= 0){
			throw new IllegalArgumentException();
		}
		//Checks for a valid load factor
		if(loadFactor <= 0.0){
			throw new IllegalArgumentException();
		}

		//Creates a new array of linked lists with specified size
		items = (LinkedList<T>[]) (new LinkedList[initSize]);
		
		//Inserts new linked list in every cell in the array
		for(int i = 0; i < items.length; i++){
			items[i] = new LinkedList<T>();
		}
		
		//sets load factor
		this.loadFactor = loadFactor;
		
		//sets initial number of items
		numItems = 0;

	}


	/**
	 * Determines if the given item is in the hashtable and returns it if 
	 * present.  If more than one copy of the item is in the hashtable, the 
	 * first copy encountered is returned.
	 *
	 * @param item the item to search for in the hashtable
	 * @return the item if it is found and null if not found
	 **/
	public T lookup(T item) {

		//gets proper hash key
		int hashing = item.hashCode();
		
		//Mods hash key by table size. Then takes absolute to prevent negatives 
		hashing = Math.abs(hashing % items.length);

		//Finds specified list in hash and checks if item is in that list
		if(items[hashing].contains(item)){
			//returns the item that was looked up
			return item;
		}
		//returns null if item was not found
		return null;
	}


	/**
	 * Inserts the given item into the hash table.  
	 * 
	 * If the load factor of the hashtable after the insert would exceed 
	 * (not equal) the maximum load factor (given in the constructor), then the 
	 * hashtable is resized.
	 * 
	 * When resizing, to make sure the size of the table is good, the new size 
	 * is always 2 x <i>old size</i> + 1.  For example, size 101 would become 
	 * 203.  (This  guarantees that it will be an odd size.)
	 * 
	 * <p>Note that duplicates <b>are</b> allowed.</p>
	 *
	 * @param item the item to add to the hashtable
	 **/
	public void insert(T item) {

        //Gets proper hash key for an item
		int hashing = item.hashCode();
		//Mods the hash key by table size so it can be added to the table.
		//Absolute is taken in order to prevent a negative value from being
		//produced
		hashing = Math.abs(hashing % items.length);
		//Adds item to proper position in the hash using moded hash key
		items[hashing].add(item);
		//increments number of items
		numItems ++;
		
		//Checks if the table should be resized by comparing the current load
		//factor to the max load factor
		if((numItems / (double) items.length) > loadFactor){
			
			//Creates another array of doubled size to store hash table values
			LinkedList<T>[] items1 = (LinkedList<T>[]) 
						(new LinkedList[(int)items.length*2 + 1]);
			//initializes the linked list in the new hash table
			for(int i = 0; i < items1.length; i++){
				items1[i] = new LinkedList<T>();
			}

			// Rehashes everything into the re-sized hashtable with buckets
			for(int i = 0; i < items.length; i++){
				for(int j = 0; j < items[i].size(); j++){
					int hashing1 = items[i].get(j).hashCode();
					hashing1 = Math.abs(hashing1 % items1.length);

					items1[hashing1].add(items[i].get(j));
				}
			}
			//Sets items to point to items1
			items = items1;
		}
	}


	/**
	 * Removes and returns the given item from the hashtable.  If the item is 
	 * not in the hashtable, <tt>null</tt> is returned.  If more than one copy 
	 * of the item is in the hashtable, only the first copy encountered is 
	 * removed and returned.
	 *
	 * @param item the item to delete in the hashtable
	 * @return the removed item if it was found and null if not found
	 **/
	public T delete(T item) {
		
		//Gets proper hashing key of item
		int hashing = item.hashCode();
		//mods hash key so it will fit in the hash table. Absolute value is 
		//taken to prevent negative values from being produced
		hashing = Math.abs(hashing % items.length);

		//Checks item was present and removed from specified hash list
		if(items[hashing].remove(item)){
			//decrements item count
			numItems--;
			//returns the deleted item
			return item;
		}
		//returns null if item was not found in hash table
		return null;
	}


	/**
	 * Prints statistics about the hashtable to the PrintStream supplied.
	 * The statistics displayed are: 
	 * <ul>
	 * <li>the current table size
	 * <li>the number of items currently in the table 
	 * <li>the current load factor
	 * <li>the length of the largest chain
	 * <li>the number of chains of length 0
	 * <li>the average length of the chains of length > 0
	 * </ul>
	 *
	 * @param out the place to print all the output
	 **/
	public void displayStats(PrintStream out) {
		//prints statistics of hash table to the desired out printstream
		//Stats include current size of the table, # of items in the table,
		//current load factor, size of the longest chain, # of chains with a 
		//size of zero, and the average length of chains that aren't zer0.
		out.println("HashTable Statistics: ");
		out.println("  Current Table Size:       " + items.length);
		out.println("  # items in table:         " + (int)numItems);
		out.println("  Current load factor:      " + (numItems/items.length));
		out.println("  Longest chain length:     " + longestChain());
		out.println("  # 0-Length chains:        " + num0Chain());
		out.println("  Avg (non-0) chain length: " + avgChain());

	}
	/**
	 * Method moves through hash table and finds the longest chain/list
	 * @return the length of the longest chain/list
	 */
	private int longestChain(){
		//variable to store longest chain length.
		int longest = 0;
		//loops through hash table and finds longest chain by comparing the
		//length of every chain to current longest chain.
		for(int i = 0; i < items.length; i++){
			if(items[i].size() > longest){
				longest = items[i].size();
			}
		}
		//returns the longest chain.
		return longest;
	}

	/**
	 * Finds the number of chains that have a length of zero.
	 * @return the number of chains with a length of zero.
	 */
	private int num0Chain(){
		//variable to store number of chains with a length of zero.
		int num0 = 0;
		//Loops through hash table and if a chain has a length of zero the num0
		//variable is incremented.
		for(int i = 0; i < items.length; i++){
			if(items[i].size() == 0){
				num0++;
			}
		}
		//returns the number of chains that have a length of zero.
		return num0;
	}

	/**
	 * Finds the length of all chains in the hash table that aren't zero added 
	 * together. That value is then divided by the number of chains in the table
	 * that aren't zero.
	 * @return the average non-zero chain length.
	 */
	private double avgChain(){
		//creates instance variable to store the length of all non-zero chains
		//added together.
		double totalChainLenghts = 0;
		//the total number of chains in the hash table that aren't zero.
		double numChain = 0;
		//Loops through the hash table, and if a list has a size larger than zero
		//it's chain length is added to the total chain lengths variable. Then 
		//the number of non-zero chains variable is incremented.
		for(int i = 0; i < items.length; i++){
			if(items[i].size() > 0){
				totalChainLenghts += items[i].size();
				numChain ++;
			}
		}
		//prevents from division by zero.
		if(numChain == 0){
			return 0;
		}
		//returns the average non-zero chain length.
		return (totalChainLenghts/numChain);
	}
}
