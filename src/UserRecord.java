package project;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;
/**
 * Class that will be used in MapReduceClass 
 * in order to help the UsersMap class (check in MapReduceClass)
 * to have a better quality as far as the context.write method is concerned.
 * 
 * Class is useful for TheReducer class (check in MapReduceClass) too 
 * where we can have the values we want
 * in order to generate the output we want.
 * @author Iraklis Bekiaris
 *
 */
public class UserRecord implements Writable{
	
	private Text name = new Text();
	/**
	 * Empty constructor. Hadoop needs it for Writables.
	 */	
	public UserRecord(){};
	/**
	 * Constructor.
	 * @param name	the name of the user.
	 */
	public UserRecord(String name){
		this.name.set(name);
	}
	/**
	 * Getter.
	 * @return	the name of the user.	
	 */
	public Text getName(){
		return name;
	}
	//Implemented methods of Writable so the Mapper and Reducer will be able to
	//read from the UserRecord class and write in the context the fields of the class.
	@Override
	public void readFields(DataInput in) throws IOException {
		this.name.readFields(in);		
	}

	@Override
	public void write(DataOutput out) throws IOException {
		this.name.write(out);		
	}
}
