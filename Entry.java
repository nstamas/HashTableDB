///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Main Class File:  TestHash
// File:             Entry
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
/**
 * Entry class creates an entry to be added to a hash table. Entries consist of
 * a string name, long phone, and an int hash type. The entry class also
 * provides a hash code method which produces a hash key for each entry when 
 * called. The will vary depending on the hash type variable. Both the equals 
 * and two string methods are overrided. The to string will print a name ":"
 * and its phone number. The equals checks if entries have the same name and 
 * phone.
 * @author stamas hareng & CS 367 staff(for skeleton)
 *
 */
public class Entry {
	//instance variables for hashtype.
	public static final int STATIC = 0;
	public static final int STRING = 1;
	public static final int LONG = 2;
	public static final int BOTH = 3;
	
	//instance variables for name, phone, and hash type
	private String name;
	private long phone;
	private int hashType;
	
	/**
	 * Entry constructor sets the entries name, phone and hash type to the values
	 * provided.
	 * @param name name of entry
	 * @param phone phone number of entry
	 * @param hashType hashtype of entry
	 */
	public Entry(String name, long phone, int hashType) {
		this.name = name;
		this.phone = phone;
		this.hashType = hashType;
	}
	
	@Override
	/**
	 * To string method is overrided to print the name ":" and phone number 
	 * of an entry.
	 * @return the proper string to be returned.
	 */
	public String toString() {
		return name + ":" + phone;
	}
	
	@Override
	/**
	 * The equals method is overrided to check if an entries name and phone 
	 * number are the same
	 * @return boolean to determine if the compared entries are the same.
	 */
	public boolean equals(Object other) {
		if (other instanceof Entry) {
			Entry that = (Entry) other;
			if (that.name.equals(this.name) && that.phone == this.phone)
				return true;
		}
		
		return false;
	}
	
	/**
	 * Returns a hashCode for this object. You should complete the three
	 * different hash functions marked below.
	 * 
	 * Make note that when you write a hash function, it must always return
	 * the same value for the same object. In other words, you should not use
	 * any randomness to generate a hash code.
	 * @return The hash key to be returned depending on the hash key provided
	 */
	@Override
	public int hashCode() {
		if (hashType == STRING) {
			/* Hash on the String name only. Java has a built-in hashing 
			 * function for Strings; see 
			 * http://docs.oracle.com/javase/1.5.0/docs/api/java/lang/String.html#hashCode%28%29
			 * */
			int sum = 0;
			for(int i = 0; i < name.length(); i++){
				//Takes characters integer equivalent multiplies it by 31 then
				//raises it to the power of the length of the name minus the 
				//poistion and 1. This is to try to create a unique hash key.
				sum += name.charAt(i)*(31)^(name.length() - i - 1);
			}
			
			//Returns the sum
			return sum;
		}
		else if (hashType == LONG) {
			/* Hash on the phone number only. You may write whatever hash 
			 * function you like, as long as it involves the phone number 
			 * in some way. Mod and/or division both work well for this. */
			//takes the integer value of the phone number divided by 10.
			int sum = (int) phone / 10;
			
			//returns the sum
			return sum;
		}
		else if (hashType == BOTH) {
			/* Come up with your own hashing function. Your hashing function
			 * should have better performance than each of the other functions 
			 * on at least one of the given input files. 
			 * You may use the name, phone number, or both. */
			//combines takes previous name hash code method and then divides it
			//by the phone number provided with it.
			int phoneNum = (int) phone;
			int sum = 0;
			for(int i = 0; i < name.length(); i++){
				sum = name.charAt(i)*(31)^(name.length() - i - 1);
			}
			sum = phoneNum / sum;
			
			//Returns sum
			return sum;
		}
		else {
			//Fixed hash function
			return 11;
		}
	}
}
