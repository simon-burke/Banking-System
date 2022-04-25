import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class SavingsAccount extends Account{
	public SavingsAccount(int AccountNumber, String name, double balance, double income, int password,
			String accountType) {
		super(AccountNumber, name, balance, income, password, accountType);
	}
	static int Number;
	static String Holder;
	static double Balance;
	static double Income;
	static int PIN;
	static String Type;
	static int numOfTransactions;
	double interest = (double)1.15;
	static void deposit(int account, double amount) throws InterruptedException, IOException //methods should be called from checking account 
	{																					//with changing inputs, but I ran out of time
	//Savings account uses a transaction limit of 15
		//Checks numOfTransactions against limit.  If limit is exceeded, there is a 10% tax on the transaction.
		//Savings account cannot go into debt so bal must be > amount
		System.out.println("Input your PIN");
		Scanner scan2 = new Scanner(System.in);
		int PIN = scan2.nextInt();
		if (checkPIN(account, PIN))
		{
			int trans = getTrans(account);
			if (trans < 15) 
			{
				System.out.println("Depositing...");
				TimeUnit.SECONDS.sleep(1);
				double bal = getBal(account);
				bal +=amount;
				editBal(account, bal);
				System.out.println("$" + amount + " depositied.  New balance is $" + getBal(account));
				trans++;
				editTrans(account, trans);
			}
			else
			{
				System.out.println("You have exceeded your transaction limit.  You will be taxed 10%.  \nAre you sure you want to continue?");
				String YN = scan2.next();
				if (YN.equals("Yes")) //If transaction limit is exceeded, asks user to confirm transaction and taxes 10% on deposit
				{
					System.out.println("Depositing...");
					TimeUnit.SECONDS.sleep(1);
					double bal = getBal(account);
					double newamount = amount * (double)0.9;
					bal +=newamount;
					editBal(account, bal);
					System.out.println("$" + amount + " depositied.  New balance is $" + getBal(account));
					trans++;
					editTrans(account, trans);
				}
				else
					System.out.println("Transaction cancelled.");
			}
		}
		else
			System.out.println("Incorrect PIN.  Try again.");
		//scan2.close();
	}
	static void withdraw(int account, double amount) throws InterruptedException, IOException
	{
		//Savings account uses a transaction limit of 15
		//Checks numOfTransactions against limit.  If limit is exceeded, there is a 10% tax on the transaction.
		//Savings account cannot go into debt so bal must be > amount
		System.out.println("Input your PIN");
		Scanner scan2 = new Scanner(System.in);
		int PIN = scan2.nextInt();
		if (checkPIN(account, PIN))
		{
			int trans = getTrans(account);
			double bal = getBal(account);
			if (bal > amount)
			{
				if (trans < 15) //transaction limit not reached so no tax
				{
					System.out.println("Withdrawing...");
					TimeUnit.SECONDS.sleep(1);
					bal = getBal(account);
					bal -=amount;
					editBal(account, bal);
					System.out.println("$" + amount + " withdrawn.  New balance is $" + getBal(account));
					trans++;
					editTrans(account, trans);
				}
				else //transaction limit reached, so 10% tax
				{
					System.out.println("You have exceeded your transaction limit.  You will be taxed 10%.  \nAre you sure you want to continue?");
					String YN = scan2.next();
					if (YN.equals("Yes"))
					{
						System.out.println("Withdrawing...");
						TimeUnit.SECONDS.sleep(1);
						bal = getBal(account);
						bal -=(amount * 1.1);
						editBal(account, bal);
						System.out.println("$" + amount + " withdrawn.  New balance is $" + getBal(account));
						trans++;
						editTrans(account, trans);
					}
					else
						System.out.println("Transaction cancelled."); //if user chooses to cancel transaction
				}
			}
			else
				System.out.println("Insufficient Funds.  Transaction cancelled."); //if user lacks sufficient funds
		}
		else
			System.out.println("Incorrect PIN.  Try again."); //incorrect PIN returns to menu
		//scan2.close();
	}
	static void transfer(int account, double amount, String toType, int to) throws InterruptedException, IOException
	{
		//Savings account uses a transaction limit of 15
		//Checks numOfTransactions against limit.  If limit is exceeded, there is a 10% tax on the transaction.
		//Savings account cannot go into debt so bal must be > amount
		Scanner scan2 = new Scanner(System.in);
		if (toType.equals("Savings")) //function for Savings recipient
		{
			System.out.println("Input your PIN");
			int PIN = scan2.nextInt();
			if (checkPIN(account, PIN)) //check PIN of user
			{
				int trans = getTrans(account);
				double bal = getBal(account);
				if (bal > amount)
				{
					if (trans < 15)  //if transaction limit not met
					{
						System.out.println("transfering...");
						TimeUnit.SECONDS.sleep(1);
						bal = getBal(account);
						bal -=(amount);
						double bal2 = getBal(to);
						bal2 +=amount;
						editBal(account, bal);
						editBal(to, bal2);
						System.out.println("$" + amount + " transfered to " + getName(to) + ".  New balance is $" + getBal(account));
						trans++;
						editTrans(account, trans);
					}
					else //if transaction limit met chargers user 10% more than transfer amount
					{
						System.out.println("You have exceeded your transaction limit.  You will be taxed 10%.  \nAre you sure you want to continue?");
						String YN = scan2.next();
						if (YN.equals("Yes"))
						{
							System.out.println("transfering...");
							TimeUnit.SECONDS.sleep(1);
							bal = getBal(account);
							bal -=(amount * 1.1);
							double bal2 = getBal(to);
							bal2 +=amount;
							editBal(account, bal);
							editBal(to, bal2);
							System.out.println("$" + amount + " transfered to " + getName(to) + ".  New balance is $" + getBal(account));
							trans++;
							editTrans(account, trans);
						}
						else
							System.out.println("Transaction cancelled."); //if user cancels transaction
					}
				}
				else
					System.out.println("Insufficient Funds.  Transaction cancelled."); //if user lacks funds
			}
			else
				System.out.println("Incorrect PIN.  Try again."); //Incorrect PIN
		}
		else if (toType.equals("Checking")) //same but for Checking recipient
		{
			System.out.println("Input your PIN");
			int PIN = scan2.nextInt();
			if (checkPIN(account, PIN))
			{
				int trans = getTrans(account);
				double bal = getBal(account);
				if (bal > amount)
				{
					if (trans < 15) 
					{
						System.out.println("transfering...");
						TimeUnit.SECONDS.sleep(1);
						bal = getBal(account);
						bal -=(amount);
						double bal2 = CheckingAccount.getBal(to);
						bal2 +=amount;
						editBal(account, bal);
						CheckingAccount.editBal(to, bal2);
						System.out.println("$" + amount + " transfered to " + CheckingAccount.getName(to) + ".  New balance is $" + getBal(account));
						trans++;
						editTrans(account, trans);
					}
					else
					{
						System.out.println("You have exceeded your transaction limit.  You will be taxed 10%.  \nAre you sure you want to continue?");
						String YN = scan2.next();
						if (YN.equals("Yes"))
						{
							System.out.println("transfering...");
							TimeUnit.SECONDS.sleep(1);
							bal = getBal(account);
							bal -=(amount * 1.1);
							double bal2 = CheckingAccount.getBal(to);
							bal2 +=amount;
							editBal(account, bal);
							CheckingAccount.editBal(to, bal2);
							System.out.println("$" + amount + " transfered to " + CheckingAccount.getName(to) + ".  New balance is $" + getBal(account));
							trans++;
							editTrans(account, trans);
						}
						else
							System.out.println("Transaction cancelled.");
					}
				}
				else
					System.out.println("Insufficient Funds.  Transaction cancelled.");
			}
			else
				System.out.println("Incorrect PIN.  Try again.");
		}
		else
		{
			System.out.println("Invalid account type.  Try again.");
		}
		//scan2.close();
	}
	static void checkBalance(int account) throws InterruptedException, FileNotFoundException
	{
		System.out.println("Fetching balance...");
		TimeUnit.SECONDS.sleep(1);
		System.out.println("Account balance: $" + getBal(account)); //gets balance of account
	}
	
	static String getName(int number) throws FileNotFoundException //gets account holder's name from file
	{
		Scanner scan2 = new Scanner(new File("//Users//simonburke//Desktop//SavingsAccounts.txt")); // path to file
		boolean bool = false;
		all:
	    while (scan2.hasNextLine()) //searches through file until correct account number is found
	    {
	    	String temp = scan2.nextLine();
    		bool = Integer.toString(number).equals(temp);
	    	if (bool == true)
	    	{ 
	    		while (scan2.hasNextLine())
	    		{
	    			if (scan2.nextLine().contains("Name:")) //after finding account number, returns the line after "Name:"
	    			{
	    				Holder = scan2.nextLine();
	    				break all;
	    			}
	    			else
	    	    	{
	    	    		scan2.nextLine();
	    	    	}
	    		}
	    	}
	    }
    	//scan2.close();
		return Holder;
	}
	
	static double getBal(int number) throws FileNotFoundException //gets balance from file
	{															  //all get commands function in same way
		Scanner scan2 = new Scanner(new File("//Users//simonburke//Desktop//SavingsAccounts.txt")); // path to file
		boolean bool = false;
		all:
	    while (scan2.hasNextLine()) 
	    {
	    	String temp = scan2.next();
    		bool = Integer.toString(number).equals(temp);
	    	if (bool == true)
	    	{ 
	    		while (scan2.hasNextLine())
	    		{
	    			if (scan2.nextLine().contains("Balance:"))
	    			{
	    				Balance = Double.valueOf(scan2.nextLine());
	    				break all;
	    			}
	    			else
	    	    	{
	    				if (scan2.hasNextLine())
	    	    		scan2.nextLine();
	    	    	}
	    		}
	    	}
	    }
		//scan2.close();
		Balance = (double)((double) Math.round((Balance * Math.pow(10, 2))) / Math.pow(10,2));
		return Balance;
	}
	static double getInc(int number) throws FileNotFoundException	//gets income from file
	{																//all get commands function in same way
		Scanner scan2 = new Scanner(new File("//Users//simonburke//Desktop//SavingsAccounts.txt")); // path to file
		boolean bool = false;
		all:
	    while (scan2.hasNextLine()) 
	    {
	    	String temp = scan2.nextLine();
    		bool = Integer.toString(number).equals(temp);
	    	if (bool == true)
	    	{ 
	    		while (scan2.hasNextLine())
	    		{
	    			if (scan2.nextLine().contains("Income:"))
	    			{
	    				Income = Double.valueOf(scan2.nextLine());
	    				break all;
	    			}
	    			else
	    	    	{
	    	    		scan2.nextLine();
	    	    	}
	    		}
	    	}
	    	
	    }
		//scan2.close();
		return Income;
	}		
	
	static int getPIN(int number) throws FileNotFoundException	//gets PIN from file
	{															//all get commands function in same way
		Scanner scan2 = new Scanner(new File("//Users//simonburke//Desktop//SavingsAccounts.txt")); // path to file
		boolean bool = false;
		all:
	    while (scan2.hasNextLine()) 
	    {
	    	String temp = scan2.nextLine();
    		bool = Integer.toString(number).equals(temp);
	    	if (bool == true)
	    	{ 
	    		while (scan2.hasNextLine())
	    		{
	    			if (scan2.nextLine().contains("Password:"))
	    			{
	    				PIN = Integer.valueOf(scan2.nextLine());
	    				break all;
	    			}
	    			else
	    	    	{
	    	    		scan2.nextLine();
	    	    	}
	    		}
	    	}
	    	
	    }
		//scan2.close();
		return PIN;
	}
	private static String getType(int number) throws FileNotFoundException //gets account type from file
	{																	   //all get commands function in same way
		Scanner scan2 = new Scanner(new File("//Users//simonburke//Desktop//SavingsAccounts.txt")); // path to file
		boolean bool = false;
		all:
	    while (scan2.hasNextLine()) 
	    {
	    	String temp = scan2.nextLine();
    		bool = Integer.toString(number).equals(temp);
	    	if (bool == true)
	    	{ 
	    		while (scan2.hasNextLine())
	    		{
	    			if (scan2.nextLine().contains("Account Type:"))
	    			{
	    				Type = scan2.nextLine();
	    				break all;
	    			}
	    			else
	    	    	{
	    	    		scan2.nextLine();
	    	    	}
	    		}
	    	}
	    }
		//scan2.close();
		return Type;
	}
	private static int getTrans(int number) throws FileNotFoundException //gets number of transactions from file
	{
		Scanner scan2 = new Scanner(new File("//Users//simonburke//Desktop//SavingsAccounts.txt")); // path to file
		boolean bool = false;
		all:
	    while (scan2.hasNextLine()) 
	    {
	    	String temp = scan2.nextLine();
    		bool = Integer.toString(number).equals(temp);
	    	if (bool == true)
	    	{ 
	    		while (scan2.hasNextLine())
	    		{
	    			if (scan2.nextLine().contains("Transactions:"))
	    			{
	    				numOfTransactions = Integer.valueOf(scan2.nextLine());
	    				break all;
	    			}
	    			else
	    	    	{
	    	    		scan2.nextLine();
	    	    	}
	    		}
	    	}
	    }
		//scan2.close();
		return numOfTransactions;
	}
	static void printInfo(int number) throws FileNotFoundException, InterruptedException //get all info from file and prints
	{
		System.out.println("Fetching account info...");
		TimeUnit.SECONDS.sleep(1);
		System.out.println("Account Info: \n\n\nAccount number: " + number + 
				"\nAccount Holder: " + getName(number) + "\nBalance: $" + getBal(number) + "\nIncome: $" + getInc(number) + "\nAccount Type: " + getType(number) + "\nTransactions:\n" + getTrans(number));
	}
	 static void editBal(int account, double Balance) throws IOException //sends path to Accounts.editBal
	{
		Path path = Paths.get(URI.create("file:/Users/simonburke/Desktop/SavingsAccounts.txt"));
		Account.editBal(path, account, Balance);
	}
	 static void editName(int account, String Name) throws IOException //sends path to Accounts.editName
	{
		Path path = Paths.get(URI.create("file:/Users/simonburke/Desktop/SavingsAccounts.txt"));
		Account.editName(path, account, Name);
	}
	 static void editInc(int account, double Income) throws IOException //sends path to Accounts.editInc
	{
		Path path = Paths.get(URI.create("file:/Users/simonburke/Desktop/SavingsAccounts.txt"));
		Account.editInc(path, account, Income);
	}
	 static void editPIN(int account, int pass) throws IOException //sends path to Accounts.editPIN
	{
		Path path = Paths.get(URI.create("file:/Users/simonburke/Desktop/SavingsAccounts.txt"));
		Account.editPIN(path, account, pass);
	}
	static int newAccount(String user, String n, double bal, double inc, int pass) throws InterruptedException, IOException //sends path to Accounts.newAccount
	{
		File file = new File("//Users//simonburke//Desktop//SavingsAccounts.txt");
		return Bank.newAccount(file, user, n, bal, inc, pass, "Savings");
	}
	static void editTrans(int account, int T) throws IOException //sends path to Accounts.editTrans
	{
		Path path = Paths.get(URI.create("file:/Users/simonburke/Desktop/SavingsAccounts.txt"));
		Account.editTrans(path, account, T);
	}
	static boolean checkPIN(int account, int PIN) throws FileNotFoundException //checks if input == PIN
	{
		int TPIN = getPIN(account);
		if (TPIN == PIN)
		{
			return true;
		}
		else
			return false;
	}

}
