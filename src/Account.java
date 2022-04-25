
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


public class Account 
{
		static int Number;
		static String Holder;
		static double Balance;
		static double Income;
		static int PIN;
		static String Type;
		static int numOfTransactions;
		public Account(int AccountNumber, String name, double balance, double income, int password, String accountType)
		{
			Number = AccountNumber;
			Holder = name;
			Balance = balance;
			Income = income;
			PIN = password;
			Type = accountType;
		}

		static void editBal(Path path, int account, double Balance) throws IOException
		{
			List<String> fileContent = new ArrayList<>(Files.readAllLines(path)); //Adds entire file to ArrayList
			for (int i = 0; i < fileContent.size(); i++) {
			    if (fileContent.get(i).equals(Integer.toString(account))) { //iterates through arraylist until correct account number is found.
			        fileContent.set(i+4, Double.toString(Balance)); //sets balance of that account to new balance
			        break;
			    }
			}
			Files.write(path, fileContent);
		}
		static void editName(Path path, int account, String Name) throws IOException
		{
			List<String> fileContent = new ArrayList<>(Files.readAllLines(path)); //Adds entire file to ArrayList
			for (int i = 0; i < fileContent.size(); i++) {
			    if (fileContent.get(i).equals(Integer.toString(account))) { //sets name of that account to new name
			        fileContent.set(i+2, Name);
			        break;
			    }
			}
			Files.write(path, fileContent);
		}
		static void editInc(Path path, int account, double Income) throws IOException
		{
			List<String> fileContent = new ArrayList<>(Files.readAllLines(path)); //Adds entire file to ArrayList
			for (int i = 0; i < fileContent.size(); i++) {
			    if (fileContent.get(i).equals(Integer.toString(account))) { //iterates through arraylist until correct account number is found.
			        fileContent.set(i+6, Double.toString(Income)); //sets income of that account to new income
			        break;
			    }
			}
			Files.write(path, fileContent);
		}
		static void editPIN(Path path, int account, int pass) throws IOException
		{
			List<String> fileContent = new ArrayList<>(Files.readAllLines(path)); //Adds entire file to ArrayList
			for (int i = 0; i < fileContent.size(); i++) {
			    if (fileContent.get(i).equals(Integer.toString(account))) { //iterates through arraylist until correct account number is found.
			        fileContent.set(i+8, Integer.toString(pass)); //sets PIN of that account to new PIN
			        break;
			    }
			}
			Files.write(path, fileContent);
		}
		static void editTrans(Path path, int account, int num) throws IOException
		{
			List<String> fileContent = new ArrayList<>(Files.readAllLines(path)); //Adds entire file to ArrayList
			for (int i = 0; i < fileContent.size(); i++) 
			{
			    if (fileContent.get(i).equals(Integer.toString(account)))  //iterates through arraylist until correct account number is found.
			    {
			        fileContent.set(i+12, Integer.toString(num)); //sets number of transactions for that account to new number
			        break;
			    }
			}
			Files.write(path, fileContent);
		}
		static void incandint() throws InterruptedException, IOException // adds income and interest to balance for all accounts
		{
			Path path = Paths.get(URI.create("file:/Users/simonburke/Desktop/CheckingAccounts.txt"));
			double Balance;
			double inc;
			double newBal;
	    	List<String> fileContent = new ArrayList<>(Files.readAllLines(path));
	    	for (int i = 0; i < fileContent.size(); i++) 
	   		{
	   		    if (fileContent.get(i).equals("Name:")) 
	   		    {
	   		    	Balance = Double.valueOf(fileContent.get(i+3));
    		    	inc = Double.valueOf(fileContent.get(i+5));
	    		    newBal = (double) ((Balance + inc) * 1.05);
	    		    fileContent.set(i+3, Double.toString(newBal));
	    		}
	   		}
	    	Files.write(path, fileContent);
	    	path = Paths.get(URI.create("file:/Users/simonburke/Desktop/SavingsAccounts.txt"));
	    	fileContent = (Files.readAllLines(path));
		    for (int i = 0; i < fileContent.size(); i++) 
		    {
		        if (fileContent.get(i).equals("Name:")) 
		        {
		        	Balance = Double.valueOf(fileContent.get(i+5));
		        	inc = Double.valueOf(fileContent.get(i+7));
		        	newBal = (double) ((Balance + inc) * 1.15);
		            fileContent.set(i+3, Double.toString(newBal));
		        }
		    }
		Files.write(path, fileContent);
		}
}
