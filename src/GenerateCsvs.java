package project;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

/**
 * Class to generate the files users.csv and transactions.csv.
 * @author Iraklis Bekiaris
 *
 */
public class GenerateCsvs {
	
	private static final String COMMA_DELIMITER = ",";
	private static final String NEW_LINE_SEPERATOR = "\n";
	private static final String FILE_HEADER_USERS = "user id, username";
	private static final String FILE_HEADER_TRANSACTIONS = "transaction id, user id, transaction type";
	/**
	 * 
	 * @param usersFileName the name of the file to generetae users.csv
	 */
	public static void generateUsersCsv(String usersFileName){
		
		
		ArrayList<UserForCsv> users = new ArrayList<UserForCsv>();
		UserForCsv[] user = new UserForCsv[1000];
		Random randomChar = new Random();
		String alphabet = "abcdefghijklmnopqrstuvwxyz";
		//Initialize the ArrayList of the users (check UsersForCsv class).
		for(int i=0; i<1000; i++){
			user[i] = new UserForCsv(i+1, alphabet.charAt(randomChar.nextInt(alphabet.length())));
			users.add(user[i]);
		}
		
		FileWriter fileWriter = null;
		//Generate the users.csv file .
		try {
			fileWriter = new FileWriter(usersFileName);
			
			fileWriter.append(FILE_HEADER_USERS.toString());
			
			fileWriter.append(NEW_LINE_SEPERATOR);
			
			for(UserForCsv userr : users){
				fileWriter.append(String.valueOf(userr.getUsID())); 
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append(String.valueOf(userr.getUserName()));
				fileWriter.append(NEW_LINE_SEPERATOR);
			}
			
			System.out.println("CSV user file generetaed");
		} catch (Exception e){
			System.out.println("error" + "   " + e.getMessage());
		} finally {
			
			try{
				fileWriter.flush();
				fileWriter.close();
			} catch (IOException e){
				System.out.println("error" + "   " + e.getMessage());
			}
		}
	}
	
	public static void genererateTransactionsCsv(String transactionsFileName){
		
		ArrayList<TransactionForCsv> transactions = new ArrayList<TransactionForCsv>(); 
		TransactionForCsv[] transaction = new TransactionForCsv[20000];
		Random randomInt = new Random();
		Random randomChar = new Random();
		String alphabet = "abcdefghijklmnopqrstuvwxyz";		
		//Initialize the ArrayList of the transactions (check TransactonsForCsv class).
		for(int i=0; i<20000; i++){
			transaction[i] = new TransactionForCsv(i+1,randomInt.nextInt(1000) + 1, alphabet.charAt(randomChar.nextInt(alphabet.length())));
			transactions.add(transaction[i]);
		}
		
		FileWriter fileWriter = null;
		//Generate transactions.csv file.
		try {
			fileWriter = new FileWriter(transactionsFileName);
			
			fileWriter.append(FILE_HEADER_TRANSACTIONS.toString());
			
			fileWriter.append(NEW_LINE_SEPERATOR);
			
			for(TransactionForCsv transactionn : transactions){
				fileWriter.append(String.valueOf(transactionn.getTrID())); 
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append(String.valueOf(transactionn.getTrUsID()));
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append(String.valueOf(transactionn.getTrType()));
				fileWriter.append(NEW_LINE_SEPERATOR);
			}
			
			System.out.println("CSV transaction file generetaed");
		} catch (Exception e){
			System.out.println("error" + "   " + e.getMessage());
		} finally {
			
			try{
				fileWriter.flush();
				fileWriter.close();
			} catch (IOException e){
				System.out.println("error" + "   " + e.getMessage());
			}
		}		
	}
	
	public static void main(String[] args){
		String usersFileName = System.getProperty("user.home")+"/input1/users.csv";
		String transactionsFileName = System.getProperty("user.home")+"/input2/transactions.csv";
        System.out.println("Write CSVs files:");
	    GenerateCsvs.generateUsersCsv(usersFileName);		
	    GenerateCsvs.genererateTransactionsCsv(transactionsFileName);	
	}
	
}
