import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class ChooseType {
	public static String input;
	public static int choose(String user) throws InterruptedException, IOException //Takes in user ID, returns desired account number
	{
		int end = 1;
		int number = -1;
		ArrayList <Integer> accs = new ArrayList <Integer>();
		Scanner scan5 = new Scanner(System.in);
		boolean flag = false;
		all:
		while (end != 0) //repeats until account is found
		{
			System.out.println("Pick your account type:\nChecking or Savings");
			input = scan5.next();
				if (input.equals("Checking")) {}
				else if (input.equals("Savings")) {}
				else
					System.out.println("Invalid account type.");
				accs = getAccs(user, input); //gets accounts of user
				if (accs.get(0) != -1) //if user has account
				{
					int check = 0;
					repeat:
					while(check == 0) //repeat until valid account is chosen
					{
						System.out.println("Which account would you like to access?");
						System.out.println("0: Create new account");
						for (int i = 0;i < accs.size(); i++)
							System.out.println("Account number " + accs.get(i)); //print account choices
						int choice = scan5.nextInt();
						if (choice == 0)
						{
							flag = true;
							System.out.println("Account Holder Name:");
							String Holder = scan5.next();
							System.out.println("Starting balance:");
							double bal = scan5.nextDouble();
							System.out.println("Income:");
							double inc = scan5.nextDouble();
							System.out.println("Choose your PIN:");
							int PIN = scan5.nextInt();
							if (input.equals("Checking"))
							{
								number = CheckingAccount.newAccount(user, Holder, bal, inc, PIN); //create account with user input
								end = 0;
								choose(user);
							}
							else if(input.equals("Savings"))
							{
								number = SavingsAccount.newAccount(user, Holder, bal, inc, PIN);
								end = 0;
								choose(user);
							}
						}
						for (int i = 0;i < accs.size(); i++) //check if user inputed valid account
						{
							if (choice == accs.get(i)) //if valid account is chosen, return account number
							{
								flag = true;
								check = 1;
								number = choice;
								end = 0;
								break;
							}
						}
						if (flag == false) //invalid account choice repeats loop
						{
							System.out.println("Not a valid account choice.");
						}
					}
				}
				else //if no accounts found
				{
					String YN = "";
					while (YN.isEmpty()) //User either creates account or does not.
					{
						System.out.println("No accounts of that type found.  Would you like to make a new account?");
						YN = scan5.next();
						if (YN.equals("Yes")) //if user wants to create account
						{
							System.out.println("Account Holder Name:");
							String Holder = scan5.next();
							System.out.println("Starting balance:");
							double bal = scan5.nextDouble();
							System.out.println("Income:");
							double inc = scan5.nextDouble();
							System.out.println("Choose your PIN:");
							int PIN = scan5.nextInt();
							scan5.nextLine();
							if (input.equals("Checking"))
							{
								number = CheckingAccount.newAccount(user, Holder, bal, inc, PIN); //create account with user input
								end = 0;
							}
							else if(input.equals("Savings"))
							{
								number = SavingsAccount.newAccount(user, Holder, bal, inc, PIN);
								end = 0;
							}
						}
						else if (YN.equals("No")) 
						{
	
							break all;
						}
						else
						{
							System.out.println("Invalid choice.");
							YN = "";
						}
					
					}
			
				}
			}
		//scan5.close();
		return number;
		}
	
	public static ArrayList<Integer> getAccs(String user, String type) throws FileNotFoundException
	{ 
		//Searches through file to find user
		//Each time "User" is seen, it adds the account number to accs
		//Returns array list
		ArrayList <Integer> accs = new ArrayList <Integer>();
		if (type.equals("Checking"))
		{
			Scanner scan5 = new Scanner(new File("//Users//simonburke//Desktop//CheckingAccounts.txt")); // path to file
			boolean bool = false;
		    while (scan5.hasNextLine()) 
		    {
		    	String temp = scan5.next();
	    		bool = user.equals(temp);
		    	if (bool == true)
		    	{ 
			   		while (scan5.hasNextLine())
			   		{
			   			if (scan5.nextLine().contains("Account Number:"))
			   			{
			   				int Number = Integer.valueOf(scan5.nextLine());
		    				accs.add(Number);
		    				break;
			    		}
			    	}
			    }
			   	else
			   		if (scan5.hasNextLine())
			   		scan5.nextLine();
		    }
		  // scan5.close();
		}
		else if (type.equals("Savings")) //repeats for Saving accounts
		{
			//Could have created a method to be repeated, but was already in too deep
			Scanner scan5 = new Scanner(new File("//Users//simonburke//Desktop//SavingsAccounts.txt")); // path to file
			boolean bool = false;
		    while (scan5.hasNextLine()) 
		    {
		    	String temp = scan5.next();
	    		bool = user.equals(temp);
		    	if (bool == true)
		    	{ 
			   		while (scan5.hasNextLine())
			   		{
			   			if (scan5.nextLine().contains("Account Number:"))
			   			{
			   				int Number = Integer.valueOf(scan5.nextLine());
		    				accs.add(Number);
		    				//scan5.close();
		    				break;
			    		}
			    	}
			    }
			   	else
			   		if (scan5.hasNextLine())
			   		scan5.nextLine();

		    }
			
		}
		    if (accs.size() == 0)
		    	accs.add(-1);
			return accs;
	}
}
