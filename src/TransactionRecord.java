package project;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;
/**
 * Class that will be used in MapReduceClass 
 * in order to help the TransactionsMap class (check in MapReduceClass)
 * to have a better quality as far as the context.write method is concerned.
 * @author Iraklis Bekiaris
 *
 * Class is useful for TheReducer class (check in MapReduceClass) too 
 * where we can have the values we want
 * in order to generate the output we want.
 */
public class TransactionRecord implements Writable{
	
	private IntWritable transactionid = new IntWritable();
	private Text type = new Text();
	/**
	 * Empty constructor. Hadoop needs it for Writables.
	 */	
	public TransactionRecord(){};
	/**
	 * Constructor.
	 * @param transactionid	the transaction id.
	 * @param type			the transaction type.
	 */
	public TransactionRecord(int transactionid, String type){		
		this.transactionid.set(transactionid);
		this.type.set(type);
	}
	/**
	 * Getter.
	 * @return	the transaction id.
	 */
	public IntWritable getTransactionUserid(){
		return transactionid;
	}
	/**
	 * Getter.
	 * @return	the transaction type.
	 */
	public Text getType(){
		return type;
	}
	//Implemented methods of Writable so the Mapper and Reducer will be able to 
	//read from the TransactionRecord class and write in the context the fields of the class.
	@Override
	public void readFields(DataInput in) throws IOException {
		this.transactionid.readFields(in);
		this.type.readFields(in);
	}

	@Override
	public void write(DataOutput out) throws IOException {
		this.transactionid.write(out);
		this.type.write(out);
		
	}
}
