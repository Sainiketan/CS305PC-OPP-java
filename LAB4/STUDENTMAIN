package Student;
import java.util.Scanner;
import java.io.*;
class StudentMain implements Operations{
	public static void main(String...args){
	 	static String name , id , branch , password;
	 	Scanner sc = new Scanner(System.in);
		public void register(Student std){
		System.out.println("Enter Student ID: ");
		id = s.nextLine();
		std.getID(id);
		
		System.out.println("Enter Student Branch: ");
		branch = s.nextLine();
		std.getBranch(branch);
		
		System.out.println("Enter Student Password: ");
		password = s.nextLine();
		std.getPassword(password);
		System.out.println(std.getDetails());
		try{
		BufferedWriter writer = new BufferedWriter(new FileWriter(fname, true));
		writer.write(std.getDetails());
		writer.close();
		}catch(IOException ie){
			System.out.println(ie.getMessage());
		}
		System.out.println("=========================\n Registration Done Successfully \n=========================");
		
	}

	public void logIn(String id, String password){
		boolean flag = false;
		try{
		BufferedReader reader = new BufferedReader(new FileReader(fname));
		String data;
			while((data = reader.readLine()) != null){
				String info[] = data.split(",");
				if(info[0].equals(id) && info[3].equals(password)){
					System.out.println("=========================\n LogIn Successfully \n=========================");
					flag = true;
					break;
				}
			}
			if(flag == false){
				System.out.println("=========================\n Wrong ID or Password \n=========================");
			}
			
		}catch(IOException ie){
			System.out.println(ie.getMessage());
		}		
	}
	
	public void exit(){
		System.exit(0);
	}
	
	public static void main(String...args){
		int i; 
		StudentMain sm = new StudentMain();
		
		System.out.println("=========================\nWelcome to SMS\n=========================");
		
		
		while(true){
		System.out.println("Select Menu:\n1. Registration\n2. LogIn\n3. Exit");
		i = s.nextInt();
		s.nextLine();
		
		switch(i){
			case 1: System.out.println("=========================\n Registration \n=========================");
				System.out.println("Enter Student Name: ");
				name = s.nextLine();
				Student st = new Student(name);
				sm.register(st);
				break;
			case 2:
				System.out.println("=========================\n LogIn \n=========================");
				System.out.print("Enter Student ID: ");
				id = s.nextLine();
		
				System.out.print("Enter Student Password: ");
				password = s.nextLine();
			
				sm.logIn(id, password);
				
				break;
			case 3: 
				sm.exit();
				break;
			default:
				System.out.println("Wrong Entry");
		}
		
		}
	}
}













	
