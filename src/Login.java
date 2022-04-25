import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
public class Login {
	static String user = "";
	static String password = "";
	public static int login(String user, String password) throws IOException, InterruptedException
	{
		boolean checkUser;
		boolean checkPass;
		int Account = -1;
		Path path = Paths.get(URI.create("file:/Users/simonburke/Desktop/Logins.txt"));
		List<String> fileContent = new ArrayList<>(Files.readAllLines(path));
		while (Account == -1) //while no account is found
		{
			boolean check = false;
			for (int i = 0; i < fileContent.size()-2; i++) 
			{
				checkUser = fileContent.get(i).equals(user); //check if username and password are valid
				checkPass = fileContent.get(i+2).equals(password);
				if (checkUser && checkPass) 
				{
					check = true;
				}
			}
			if (check == true) //if so continue to ChooseType
				Account = ChooseType.choose(user);
			else //if not, return to login
			{
				Scanner scan4 = new Scanner(System.in);
				System.out.println("Incorrect login.  Try again.");
				System.out.println("Username:");
				user = scan4.next();
				System.out.println("Password:");
				password = scan4.next();
				//scan4.close();
			}
			
		}
		return Account;
	}
	public static int log(int log) throws IOException, InterruptedException
	{
		Path path = Paths.get(URI.create("file:/Users/simonburke/Desktop/Logins.txt"));
		List<String> fileContent = new ArrayList<>(Files.readAllLines(path));
		int Number = -1;
		Scanner scan4 = new Scanner(System.in);
		if (log == 1)
		{
			System.out.println("Please login.\nUsername:");
			user = scan4.next();
			System.out.println("Password:");
			password = scan4.next();
			
			Number = Login.login(user, password); //authenticates username and password, returns selected account number
			//scan4.close();
			return Number;
		}
		else //create new account with user input
		{
			boolean flag = false;
			while (flag == false)
			{
				boolean checkUser = false;
				System.out.println("Create your Username");
				String name = scan4.next();
				System.out.println("Create your password");
				String password = scan4.next();
				for (int i = 0; i <= fileContent.size()-1; i++)
				{
					checkUser = fileContent.get(i).equals(name);
					if (checkUser == true)
						break;
				}
				if (checkUser == true)
				{
					System.out.println("Username is taken.  Please try a different username.");
				}
				else
				{
					File file = new File("//Users//simonburke//Desktop//Logins.txt");
					String Info = "\n\nUser:\n" + name + "\nPassword:\n" + password;
					FileWriter fr = new FileWriter(file, true);
					fr.write(Info);
					fr.close();
					Number = Login.login(name, password);
					flag = true;
					//scan4.close();
				}
			}
			return Number;
		}
		
	}
	public static void changeLog() throws IOException //change account password
	{
		System.out.println("Please enter your current password or type back to return:");
		Scanner scan4 = new Scanner(System.in);
		String P = scan4.next();
		if (P.equals("Back")) {}
		else
		{
			boolean CheckP = false;
			Path path = Paths.get(URI.create("file:/Users/simonburke/Desktop/Logins.txt"));
			List<String> fileContent = new ArrayList<>(Files.readAllLines(path));
			for (int i = 0; i < fileContent.size(); i++) 
			{
				if (fileContent.get(i).equals(Login.user)) 
				{
					CheckP = fileContent.get(i+2).equals(P);
					if (CheckP)
					{
						System.out.println("Enter your new password or type \"Back\" to return:");
						String pass = scan4.next();
						if (pass.equals("Back"))
							break;
						else
						{
							fileContent.set(i+2, pass);
							break;
						}
					}
				}
			}
		//scan4.close();
		Files.write(path, fileContent);
		}
	}
}