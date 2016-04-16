package project;
/**
 * Class that will be used in GenerateCsvs class 
 * to construct the Arraylist of the users 
 * that will be inserted to csv file.
 * @author Iraklis Bekiaris
 *
 */
public class UserForCsv {
	
	private int id;
	private char username;
	/**
	 * Constructor
	 * @param id		the user id.
	 * @param username	the user name.
	 */
	public UserForCsv(int id, char username){
		this.id = id;
		this.username = username;
	}
	/**
	 * 
	 * @return	the user id.
	 */
	public int getUsID(){
		return id;
	}
	/**
	 * 
	 * @return	the user name.
	 */
	public char getUserName(){
		return username;
	}

}
