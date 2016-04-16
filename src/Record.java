package project;

import org.apache.hadoop.io.GenericWritable;
import org.apache.hadoop.io.Writable;

@SuppressWarnings("unchecked")
/**
 * Class that helps to input the user and transaction record classes into one class
 * plus it will be writable to context.
 *
 */
public class Record extends GenericWritable {
    private static Class<? extends Writable>[] CLASSES = null;
    /**
     * Definition of CLASSES. Useful for override method to return the suitable class 
     * + depending on what is inserted on Record constructor
     * + so hadoop would know the class the method refers to.  
     */
    static {
        CLASSES = (Class<? extends Writable>[]) new Class[] {
                UserRecord.class,
                TransactionRecord.class
        };
    }
	/**
	 * Empty constructor. Hadoop needs it for Writables.
	 */	   
    public Record() {}
   /**
    * Constructor
    * @param object	The writable object
    */
    public Record(Writable object) {
        set(object);
    }
    
	@Override
	protected Class<? extends Writable>[] getTypes() {
		return CLASSES;
	}

}
