package project;


import java.io.IOException;
import java.util.ArrayList;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.MultipleInputs;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
/**
 * The map-reduce class.
 * @author Iraklis Bekiaris
 *
 */
public class MapReduceClass {

	/**
	 * Class to map the users where the key will be the user id 
	 * and value the UserRecord in Record form.
	 *
	 */
	public  static class UsersMap extends Mapper<Object, Text, Key, Record>{
		
		public void map(Object key, Text value, Context context) throws IOException, InterruptedException {			
			int userid ;
			String name;	
			String[] recordFields = value.toString().split(",");//skips the ",".
			if(!recordFields[0].equals("user id")){//skips the header.
				userid=Integer.parseInt(recordFields[0]);
				name = recordFields[1];
				UserRecord ur = new UserRecord(name);
				Key keyy = new Key(userid, 0);
				Record genericRecord = new Record(ur);				
				context.write(keyy, genericRecord);
			}
		}	
	}	
	/**
	 * Class to map the transactions where key will be the user id that refers to the transaction 
	 * and value the TransactionRecord in Record form. 
	 *
	 */
	public static class TransactionsMap extends Mapper<Object, Text, Key, Record>{
		
		public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
			
			int userid;
			int transactionid;
			String type;			
			String[] recordFields = value.toString().split(",");//skips the ",".
			if(!recordFields[0].equals("transaction id")){//skips the header.
				transactionid = Integer.parseInt(recordFields[0]);
				userid= Integer.parseInt(recordFields[1]);
				type = recordFields[2];
				TransactionRecord tr = new TransactionRecord(transactionid, type);
				Key keyy = new Key(userid, 1);
				Record genericRecord = new Record(tr);
				context.write(keyy, genericRecord);
			}
		}	
	}	
	/**
	 * Class for reduce. We get the values that had been mapped in the previous map classes
	 * and with the help of ArrayLists, where each one of the ArrayLists has the values we need,
	 * we compare the results as far as the user id and the transaction user id are concerned 
	 * to have the output we want.
	 *
	 */
	public static class TheReducer extends Reducer<Key, Record, NullWritable, Text> {
		private ArrayList<String> keyUserID = new ArrayList<String>();
		private ArrayList<String> keyTrUserID = new ArrayList<String>();
		private ArrayList<String> names = new ArrayList<String>();
		private ArrayList<String> transactionIDs = new ArrayList<String>();
		private ArrayList<String> transactionTypes = new ArrayList<String>();		
		public void reduce(Key key, Iterable<Record> values, Context context) throws IOException, InterruptedException {
			IntWritable zero = new IntWritable(0);
			int k=0;// counter of reduce calls (useful for line 101).
			for(Record v : values){
				Writable record = v.get(); 
				if(key.getKind().equals(zero)){//kind is user record
					UserRecord ur = (UserRecord) record;
					keyUserID.add(key.getKey().toString());
					names.add(ur.getName().toString());
				} else{//kind is transaction record
					TransactionRecord tr = (TransactionRecord) record;
					keyTrUserID.add(key.getKey().toString());
					transactionIDs.add(tr.getTransactionUserid().toString());
					transactionTypes.add(tr.getType().toString());
					k++;
				}
				if(k==20000){//the Reducer has ended his job.
					for(int i=0; i<1000; i++){
						for(int j=0; j<20000; j++){
							if(keyUserID.get(i).equals(keyTrUserID.get(j)))//a user id is the same as a user id that refers to a transaction according to the transactions.csv file.
								context.write(NullWritable.get(), new Text(keyUserID.get(i) + ", " + names.get(i) +", " 
													+ transactionIDs.get(j) + ", " + transactionTypes.get(j)));
							//later from the command line we transform the text output to csv file.
							//+ with the command: cat /path/to/output/we/get/from/hdfs> /path/to/output/we/get/from/hdfs/output.csv
												//ex: cat /tmp/part-r-00000> /tmp/output.csv 
						}
					}
				}		
			}			
		}
	}
	
	
	
	public static void main(String[] args)  throws Exception  {
        Configuration c=new Configuration();//Configuration for the job.      
        Job job = new Job(c,"multiple");//Initialization of the job
        
        job.setJarByClass(MapReduceClass.class);//Set the jar that refers to the job.
        
	    job.setInputFormatClass(TextInputFormat.class);//Set the input format.
	    job.setOutputFormatClass(TextOutputFormat.class); //Set the output format.
	    //Set the output key and value class.(Check line 32, 53).
	    job.setMapOutputKeyClass(Key.class);
	    job.setMapOutputValueClass(Record.class);
	    //Configure the input paths (input files, format class, map class).
	    MultipleInputs.addInputPath(job, new Path(args[0]), TextInputFormat.class, UsersMap.class);
	    MultipleInputs.addInputPath(job,new Path(args[1]), TextInputFormat.class, TransactionsMap.class);	    	
	    
	    job.setReducerClass(TheReducer.class);//Set the reducer class.
	    //Set the output key and value class. (Check line 79 parameters).
	    job.setOutputKeyClass(NullWritable.class);
	    job.setOutputValueClass(Text.class);	    	    	    	

	    FileOutputFormat.setOutputPath(job, new Path(args[2]));
	    
	    System.exit(job.waitForCompletion(true) ? 0:1);	
	}
	
}
