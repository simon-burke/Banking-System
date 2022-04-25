import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class CheckingAccount extends Account{
		public CheckingAccount(int AccountNumber, String name, double balance, double income, int password,
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
		static void deposit(int account, double amount) throws InterruptedException, IOException
		{
			System.out.println("Input your PIN");
			Scanner scan = new Scanner(System.in);
			int PIN = scan.nextInt();
			if (checkPIN(account, PIN)) //Checks for correct PIN before making transaction
			{
				System.out.println("Depositing..."); 
				TimeUnit.SECONDS.sleep(1);
				double bal = getBal(account); //gets balance from file
				bal +=amount; //adds amount to balance
				editBal(account, bal); //changes balance in file
				System.out.println("$" + amount + " depositied.  New balance is $" + getBal(account));
			}
			else
				System.out.println("Incorrect PIN.  Try again."); //incorrect PIN returns to menu
			//scan.close();
		}
		static void withdraw(int account, double amount) throws InterruptedException, IOException
		{
			System.out.println("Input your PIN");
			Scanner scan = new Scanner(System.in);
			int PIN = scan.nextInt();
			if (checkPIN(account, PIN)) //Checks for correct PIN before making transaction
			{
				System.out.println("Withdrawing...");
				TimeUnit.SECONDS.sleep(1);
				double bal = getBal(account); //gets balance from file
				bal -= amount; //subtracts amount from balance
				editBal(account, bal);
				System.out.println("$" + amount + " withdrawn.  New balance is $" + getBal(account));
			}
			else
				System.out.println("Incorrect PIN.  Try again."); //Incorrect PIN returns to menu
			//scan.close();
		}
		static void transfer(int account, double amount, String toType, int to) throws InterruptedException, IOException
		{
			//Checking account has no transaction limit
			//Checking account can go into debt, so bal is not checked
			Scanner scan = new Scanner(System.in); 
			if (toType.equals("Checking")) //Checks recipient account type
			{
				System.out.println("Input your PIN");
				int PIN = scan.nextInt();
				if (checkPIN(account, PIN)) //Checks for correct PIN before making transaction
				{
					double bal = getBal(account); //Gets user balance
					System.out.println("transfering...");
					TimeUnit.SECONDS.sleep(1);
					bal = getBal(account);
					bal -=(amount); 
					double bal2 = getBal(to);
					bal2 +=amount;
					editBal(account, bal);
					editBal(to, bal2);
					System.out.println("$" + amount + " transfered to " + getName(to) + ".  New balance is $" + getBal(account));
				}
				else
					System.out.println("Incorrect PIN.  Try again.");
				//scan.close();
			}
			else if (toType.equals("Savings")) //Same but recipient is savings account
			{
				System.out.println("Input your PIN");
				int PIN = scan.nextInt();
				if (checkPIN(account, PIN))
				{
					double bal = getBal(account);
					System.out.println("transfering...");
					TimeUnit.SECONDS.sleep(1);
					bal = getBal(account);
					bal -=(amount);
					double bal2 = SavingsAccount.getBal(to);
					bal2 +=amount;
					editBal(account, bal);
					SavingsAccount.editBal(to, bal2);
					System.out.println("$" + amount + " transfered to " + SavingsAccount.getName(to) + ".  New balance is $" + getBal(account));
				}
				else
					System.out.println("Incorrect PIN.  Try again.");
			}
			else
			{
				System.out.println("Invalid account type.  Try again.");
			}
	}
		static void checkBalance(int account) throws InterruptedException, FileNotFoundException
		{
			System.out.println("Fetching balance...");
			TimeUnit.SECONDS.sleep(1);
			System.out.println("Account balance: $" + getBal(account));
		}
		
		static String getName(int number) throws FileNotFoundException 
		{
			Scanner scan = new Scanner(new File("//Users//simonburke//Desktop//CheckingAccounts.txt")); // path to file
			boolean bool = false;
			all:
		    while (scan.hasNextLine()) //Searches through file for account number 
		    {
		    	String temp = scan.next(); //sets Holder equal to line after Name:
	    		bool = Integer.toString(number).equals(temp);
		    	if (bool == true)
		    	{ 
		    		while (scan.hasNextLine())
		    		{
		    			if (scan.nextLine().contains("Name:"))
		    			{
		    				Holder = scan.nextLine();
		    				break all;
		    			}
		    		}
		    	}
		    	else
		    	{
		    		scan.nextLine();
		    	}
		    }
			//scan.close();
			return Holder;
		}
		
		static double getBal(int number) throws FileNotFoundException
		{
			Scanner scan = new Scanner(new File("//Users//simonburke//Desktop//CheckingAccounts.txt")); // path to file
			boolean bool = false;
			all:
		    while (scan.hasNextLine()) 
		    {
		    	String temp = scan.nextLine();
	    		bool = Integer.toString(number).equals(temp);
		    	if (bool == true)
		    	{ 
		    		while (scan.hasNextLine())
		    		{
		    			if (scan.nextLine().contains("Balance:"))
		    			{
		    				Balance = Double.valueOf(scan.nextLine());
		    				break all;
		    			}
		    			else
				    	{
				    		scan.nextLine();
				    	}
		    		}
		    	}
		    }
			Balance = (double)((double) Math.round((Balance * Math.pow(10, 2))) / Math.pow(10,2));
			return Balance;
		}
		static double getInc(int number) throws FileNotFoundException
		{
			Scanner scan = new Scanner(new File("//Users//simonburke//Desktop//CheckingAccounts.txt")); // path to file
			boolean bool = false;
			all:
		    while (scan.hasNextLine()) //Searches through file for account number 
		    {
		    	String temp = scan.nextLine(); //sets Holder equal to line after Name:
	    		bool = Integer.toString(number).equals(temp);
		    	if (bool == true)
		    	{ 
		    		while (scan.hasNextLine())
		    		{
		    			if (scan.nextLine().contains("Income:"))
		    			{
		    				Income = Double.valueOf(scan.nextLine());
		    				break all;
		    			}
		    			else
				    	{
				    		scan.nextLine();
				    	}
		    		}
		    		
		    	}
		    	
		    }
			return Income;
		}		
		
		static int getPIN(int number) throws FileNotFoundException
		{
			Scanner scan = new Scanner(new File("//Users//simonburke//Desktop//CheckingAccounts.txt")); // path to file
			boolean bool = false;
			all:
		    while (scan.hasNextLine()) //Searches through file for account number 
		    {
		    	String temp = scan.nextLine(); //sets Holder equal to line after Name:
	    		bool = Integer.toString(number).equals(temp);
		    	if (bool == true)
		    	{ 
		    		while (scan.hasNextLine())
		    		{
		    			if (scan.nextLine().contains("Password:"))
		    			{
		    				PIN = Integer.valueOf(scan.nextLine());
		    				break all;
		    			}
		    			else
				    	{
				    		scan.nextLine();
				    	}
		    		}
		    	}
		    	
		    }
			return PIN;
		}
		private static String getType(int number) throws FileNotFoundException
		{
			Scanner scan = new Scanner(new File("//Users//simonburke//Desktop//CheckingAccounts.txt")); // path to file
			boolean bool = false;
			all:
		    while (scan.hasNextLine()) //Searches through file for account number 
		    {
		    	String temp = scan.nextLine();
	    		bool = Integer.toString(number).equals(temp);
		    	if (bool == true)
		    	{ 
		    		while (scan.hasNextLine())//sets Holder equal to line after Name:
		    		{
		    			if (scan.nextLine().contains("Account Type:"))
		    			{
		    				Type = scan.nextLine();
		    				break all;
		    			}
		    			else
				    	{
				    		scan.nextLine();
				    	}
		    		}
		    	}
		    	
		    }
			return Type;
		}
		private static int getTrans(int number) throws FileNotFoundException
		{
			Scanner scan = new Scanner(new File("//Users//simonburke//Desktop//CheckingAccounts.txt")); // path to file
			boolean bool = false;
			all:
		    while (scan.hasNextLine()) 
		    {
		    	String temp = scan.nextLine();
	    		bool = Integer.toString(number).equals(temp);
		    	if (bool == true)
		    	{ 								  //Searches through file for account number 
		    		while (scan.hasNextLine()) //sets Holder equal to line after Name:
		    		{
		    			if (scan.nextLine().contains("Transactions:"))
		    			{
		    				numOfTransactions = Integer.valueOf(scan.nextLine());
		    				break all;
		    			}
		    			else
				    	{
				    		scan.nextLine();
				    	}
		    		}
		    	}
		    	
		    }
			return numOfTransactions;
		}
		static void printInfo(int number) throws FileNotFoundException, InterruptedException 
		{
			System.out.println("Fetching account info...");
			TimeUnit.SECONDS.sleep(1); //fetches all account info from file, prints
			System.out.println("Account Info: \n\n\nAccount number: " + number + 
					"\nAccount Holder: " + getName(number) + "\nBalance: $" + getBal(number) + "\nIncome: $" + getInc(number) + "\nAccount Type: " + getType(number) + "\nTransactions:\n" + getTrans(number));
		}
		 static void editBal(int account, double Balance) throws IOException
		{
			Path path = Paths.get(URI.create("file:/Users/simonburke/Desktop/CheckingAccounts.txt"));
			Account.editBal(path, account, Balance); //sends file to Accounts.editBal
		}
		 static void editName(int account, String Name) throws IOException
		{
			Path path = Paths.get(URI.create("file:/Users/simonburke/Desktop/CheckingAccounts.txt"));
			Account.editName(path, account, Name);//sends file to Accounts.editName
		}
		 static void editInc(int account, double Income) throws IOException
		{
			Path path = Paths.get(URI.create("file:/Users/simonburke/Desktop/CheckingAccounts.txt"));
			Account.editInc(path, account, Income);//sends file to Accounts.editInc
		}
		 static void editPIN(int account, int pass) throws IOException
		{
			Path path = Paths.get(URI.create("file:/Users/simonburke/Desktop/CheckingAccounts.txt"));
			Account.editPIN(path, account, pass);//sends file to Accounts.editPIN
		}
		static int newAccount(String user, String n, double bal, double inc, int pass) throws InterruptedException, IOException
		{
			File file = new File("//Users//simonburke//Desktop//CheckingAccounts.txt");
			return Bank.newAccount(file, user, n, bal, inc, pass, "Checking");//sends file to Accounts.newAccount
		}
		static void editTrans(int account, int T) throws IOException
		{
			Path path = Paths.get(URI.create("file:/Users/simonburke/Desktop/CheckingAccounts.txt"));
			Account.editTrans(path, account, T);//sends file to Accounts.editTrans
		}
		static boolean checkPIN(int account, int PIN) throws FileNotFoundException
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
