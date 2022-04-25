import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;
public class Bank {
	public static int nextNum(String type) throws FileNotFoundException //counts how many accounts are in file
	{
		int num = 1;
		if (type.equals("Checking"))
		{
			Scanner scanner = new Scanner(new File("//Users//simonburke//Desktop//CheckingAccounts.txt")); // path to file
	    		while (scanner.hasNextLine()) 
	    		{
	    			if (scanner.nextLine().contains("Name:")) num++; //searches through file for "name" and counts each one
	    		}	
	    		//scanner.close();
		}
		else if (type.equals("Savings")) //Same thing for Savings accounts
		{
			Scanner scanner = new Scanner(new File("//Users//simonburke//Desktop//SavingsAccounts.txt")); // path to file
			while (scanner.hasNextLine()) 
			{
				if (scanner.nextLine().contains("Name:")) num++;
			}
			//scanner.close();
		}
		return num;
	}
	
	public static int newAccount(File file, String user, String n, double bal, double inc, int pass, String Type) throws InterruptedException, IOException //Create new account
	{
		int num = nextNum(Type); //Takes in necessary information writes new account in file
		String Info = "\n\nUser:\n" + user + "\nAccount Number:\n" + Integer.toString(num) + "\nName:\n" + n + "\nBalance:\n" + Double.toString(bal) + "\nIncome:\n" + Double.toString(inc) + "\nPassword:\n" + Integer.toString(pass) + "\nAccount Type:\n" + Type + "\nTransactions:\n" + "0";
		FileWriter fr = new FileWriter(file, true);
		fr.write(Info);
		fr.close();
		System.out.println("Creating account...");
		TimeUnit.SECONDS.sleep(1);
		System.out.println("Account created.  Account Info: ");
		AccTypes.printInfo(Type, num);
		return num;
	}
}
