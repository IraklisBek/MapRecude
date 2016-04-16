package project;
/**
 * Class that will be used in GenerateCsvs class 
 * to construct the Arraylist of the transactions 
 * that will be inserted to csv file.
 * @author Iraklis Bekiaris
 *
 */
public class TransactionForCsv {
	
	private int id;
	private int userid;
	private char type;
	/**
	 * Constructor
	 * @param id		the transaction id
	 * @param userid	the user id
	 * @param type		the transaction type
	 */
	public TransactionForCsv(int id, int userid, char type){
		this.id = id;
		this.userid = userid;
		this.type = type;
	}
	/**
	 * 
	 * @return	the transaction id
	 */
	public int getTrID(){
		return id;
	}
	/**
	 * 
	 * @return	the user id that refers to the transaction
	 */
	public int getTrUsID(){
		return userid;
	}
	/**
	 * 
	 * @return	the transaction type
	 */
	public char getTrType(){
		return type;
	}
}
