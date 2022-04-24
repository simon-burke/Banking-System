import java.io.IOException;
import java.util.*;
public class Interface {
	public static void main (String args[]) throws InterruptedException, IOException
	{
		int end = 0;
		Scanner obj = new Scanner(System.in);
		int  Number = -1;
		System.out.println("Welcome to your virtual banking experience.");
		System.out.println("Type 1 to login or 2 to create a new account.");
		int log = obj.nextInt();
		Number = Login.log(log); //gets account number
		String type = ChooseType.input; //gets account type
		while (end == 0) 
		{
			String func = "";
			System.out.println("What would you like to do today? \n");	//Interface stuff
			System.out.println("1. Make a deposit");                  	//Interface stuff
			System.out.println("2. Make a withdrawal");               	//Interface stuff
			System.out.println("3. Transfer");                        	//Interface stuff
			System.out.println("4. Show account info");              	 //Interface stuff
			System.out.println("5. Change password");                	 //Interface stuff
			System.out.println("6. Change account info");				 //Interface stuff
			System.out.println("7. End session");   
			func = obj.next();
			int back = 0;
			while (back ==0) //Used for back command
			{
				switch(func)
				{
					case "1": //deposit
						System.out.println("Input deposit amount or type \"Back\" to return to the menu."); //Allows user to cancel transaction
						double amtD = 0;
						String input = obj.next();
						if (input.equals("Back"))
							back = 1;
						else
						{
							amtD = Double.valueOf(input); 
							AccTypes.deposit(type, Number, amtD);  //deposits for given account type
							back = 1;
						}
						break;
					
					case "2": //Withdraw
						System.out.println("Input withdrawal amount or type \"Back\" to return to the menu.");
						double amtW = 0;
						input = obj.next();
						if (input.equals("Back")) //Allows user to cancel transaction
							back = 1;
						else
						{
							amtW = Double.valueOf(input);
							AccTypes.withdraw(type, Number, amtW); //withdraws for given account type
							back = 1;
						}
						break;
					
					case "3": //Transfer
						System.out.println("What is the recipient account type?");
						String toType = obj.next();
						System.out.println("Input recipient account number or type \"Back\" to return to the menu.");
						int to = 0;
						input = obj.next();
						if (input.equals("Back")) //Allows user to cancel transaction
						{
							back = 1;
						}
						else
							to = Integer.valueOf(input);
							System.out.println("What is the transfer amount?");
							double amtT = 0;
							if (obj.hasNextDouble())
							{
								amtT = obj.nextDouble();
								AccTypes.transfer(type, Number, amtT, toType, to); //Withdraws from user account, deposits in recipient account
							}
							back = 1;
						break;
					
					case "4": //get info
						AccTypes.printInfo(type, Number); //prints account info
						back = 1;
						break;
					
					case "5": //change password
						Login.changeLog(); //allows user to change password
						back = 1;
						break;
					case "6": //edit account info
						System.out.println("What would you like to edit? \nIncome, Name, or PIN");
						String T = obj.next(); //allows user to update relevant account information
						AccTypes.editInfo(type, Number, T);
						
					case "7":
						end = 1;
						back = 1;
						System.out.println("Have a nice day!"); //ends program
						break;
					default:
						System.out.println("Invalid input."); //unexpected input
						back = 1;
						break;
				}
				
			}
		Account.incandint();
		}
	}
}