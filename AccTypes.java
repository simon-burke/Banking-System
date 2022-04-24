import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;

import java.util.*;
public class AccTypes {
	//Checks account type and uses account functions depending on type
	public static void printInfo(String type, int num) throws FileNotFoundException, InterruptedException
	{
		if (type.equals("Checking"))
			CheckingAccount.printInfo(num);
		else if(type.equals("Savings"))
			SavingsAccount.printInfo(num);
	}
	public static void deposit(String type, int account, double amount) throws InterruptedException, IOException
	{
		if (type.equals("Checking"))
			CheckingAccount.deposit(account, amount);
		else if(type.equals("Savings"))
			SavingsAccount.deposit(account, amount);
	}
	public static void withdraw(String type, int account, double amount) throws InterruptedException, IOException
	{
		if (type.equals("Checking"))
			CheckingAccount.withdraw(account, amount);
		else if(type.equals("Savings"))
			SavingsAccount.withdraw(account, amount);
	}
	public static void transfer(String type, int account, double amount, String toType, int to) throws InterruptedException, IOException
	{
		if (type.equals("Checking"))
			CheckingAccount.transfer(account, amount, toType, to);
		else if(type.equals("Savings"))
			SavingsAccount.transfer(account, amount, toType, to);
	}
	public static void checkBalance(String type, int account) throws InterruptedException, FileNotFoundException
	{
		if (type.equals("Checking"))
			CheckingAccount.checkBalance(account);
		else if(type.equals("Savings"))
			SavingsAccount.checkBalance(account);
	}
	public static String getName(String type, int number) throws FileNotFoundException 
	{
		String Name = "";
		if (type.equals("Checking"))
			Name = CheckingAccount.getName(number);
		else if(type.equals("Savings"))
			Name = SavingsAccount.getName(number);
		return Name;
	}
	public static double getBal(String type, int number) throws FileNotFoundException
	{
		double bal = 0;
		if (type.equals("Checking"))
			bal = CheckingAccount.getBal(number);
		else if(type.equals("Savings"))
			bal = SavingsAccount.getBal(number);
		return bal;
	}
	public static double getInc(String type, int number) throws FileNotFoundException
	{
		double inc = 0;
		if (type.equals("Checking"))
			inc = CheckingAccount.getInc(number);
		else if(type.equals("Savings"))
			inc = SavingsAccount.getInc(number);
		return inc;
	}
	public static int getPIN(String type, int number) throws FileNotFoundException
	{
		int PIN = 0;
		if (type.equals("Checking"))
			PIN = CheckingAccount.getPIN(number);
		else if(type.equals("Savings"))
			PIN = SavingsAccount.getPIN(number);
		return PIN;
	}
	public static String getType(String type) throws FileNotFoundException
	{
		return type;
	}
	public static void editBal(String type, int account, double Balance) throws IOException
	{
		if (type.equals("Checking"))
			CheckingAccount.editBal(account, Balance);
		else if(type.equals("Savings"))
			SavingsAccount.editBal(account, Balance);
	}
	public static void editName(String type, int account, String Name) throws IOException
	{
		if (type.equals("Checking"))
			CheckingAccount.editName(account, Name);
		else if(type.equals("Savings"))
			SavingsAccount.editName(account, Name);
	}
	public static void editInc(String type, int account, double Income) throws IOException
	{
		if (type.equals("Checking"))
			CheckingAccount.editInc(account, Income);
		else if(type.equals("Savings"))
			SavingsAccount.editInc(account, Income);
	}
	public static void editPIN(String type, int account, int pass) throws IOException
	{
		if (type.equals("Checking"))
			CheckingAccount.editPIN(account, pass);
		else if(type.equals("Savings"))
			SavingsAccount.editPIN(account, pass);
	}
	static int newAccount(String type, String user, String n, double bal, double inc, int pass) throws InterruptedException, IOException
	{
		int num = 0;
		if (type.equals("Checking"))
		{
			
			num = CheckingAccount.newAccount(user, n, bal, inc, pass);
		}
		else if(type.equals("Savings"))
		{
			num = SavingsAccount.newAccount(user, n, bal, inc, pass);
		}
		return num;
	}
	public static void editTrans(String type, Path path, int account, int T) throws IOException
	{
		if (type.equals("Checking"))
			CheckingAccount.editTrans(account, T);
		else if(type.equals("Savings"))
			SavingsAccount.editTrans(account, T);
	}
	public static void editInfo (String type, int account, String T) throws IOException
	{
		if (T.equals("Income"))
		{
			System.out.println("Input new income");
			Scanner scan3 = new Scanner(System.in);
			double Income = scan3.nextDouble();
			editInc(type, account, Income);
			//scan3.close();
		}
		else if (T.equals("Name"))
		{
			System.out.println("Input new name");
			Scanner scan3 = new Scanner(System.in);
			String name = scan3.next();
			editName(type, account, name);
			//scan3.close();
		}
		else if(T.equals("PIN"))
		{
			System.out.println("Input new PIN");
			Scanner scan3 = new Scanner(System.in);
			int PIN = scan3.nextInt();
			editPIN(type, account, PIN);
			//scan3.close();
		}
		System.out.println(T + " is being updated...");
		System.out.println(T + "updated.");
	}
}
