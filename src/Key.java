package project;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.io.WritableComparable;
public  class Key implements Writable, WritableComparable<Key> {
	
	private IntWritable key = new IntWritable();
	private IntWritable kind = new IntWritable();
	/**
	 * Empty constructor. Hadoop needs it for Writables.
	 */
	public Key(){};
	/**
	 * Constructor.
	 * @param key	the key of the context for each Object either UserRecord or TransactionRecord.
	 * + In case of UserRecord the key is the user id. In case of TransactionRecord the key is the user id that refers to the transaction.
	 * @param kind	the kind of the key (UserRecord -->0, TransactionRecord -->1).
	 */
	public Key(int key, int kind){
		this.key.set(key);
		this.kind.set(kind);;
	}
	/**
	 * Getter.
	 * @return	the key.
	 */
	public IntWritable getKey(){
		return key;
	}
	/**
	 * Getter.
	 * @return	the kind of the key.
	 */
	public IntWritable getKind(){
		return kind;
	}
	//Implemented methods of Writable so the Mapper and Reducer will be able to 
	//read from the Key class and write in the context the fields of the class.
	@Override
	public void readFields(DataInput in) throws IOException {
		this.key.readFields(in);
		this.kind.readFields(in);	
	}

	@Override
	public void write(DataOutput out) throws IOException {
		this.key.write(out);
		this.kind.write(out);	
	}

	@Override
	public int compareTo(Key o) {
		
	    if (this.key.equals(o.key )) {
	        return this.kind.compareTo(o.kind);
	    } else {
	        return this.kind.compareTo(o.kind);
	    }
	}	
}

