class Annop{
	@Deprecated
	public void depM(){
		System.out.println("Deprecated method..");
	}
	public void ovR(){
		System.out.println("Annop");
	}
}
class AnnoC extends Annop{
	@Override	
	public void ovR(){
		System.out.println("AnnoC");
	}
}
public class AnnoDemo{
	@SuppressWarnings("deprecation")
	public static void main(String...args){
		AnnoC ac = new AnnoC();
		ac.ovR();
		ac.depM();
	
	}



}
