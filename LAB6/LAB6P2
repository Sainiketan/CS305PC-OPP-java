import java.util.Scanner;
enum Fruits{

	Apple('a'),Banana('b') ,Citrus('c') , DragonFruit('d') , Eggplant('e') ,Fig('f') ,Grapes('g');
	private final  char a;
	Fruits(char a){
		this.a=a;
	}
	public char get(){
		return a;
	}
	
}
public class EnumEx{
	public static void main(String...args){
		Scanner s= new Scanner(System.in);
		System.out.println("enter the value: ");
		String st = s.next();
		char ch = st.charAt(0);
		ch = Character.toLowerCase(ch);
		
		for(Fruits f : Fruits.values()){
			if(f.get() == ch)
			System.out.println(f);
		}
		
	}
}
