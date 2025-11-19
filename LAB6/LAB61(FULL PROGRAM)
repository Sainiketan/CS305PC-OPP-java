
class Annop{
	@Deprecated
	public void depM(){
		System.out.println("Deprecated method..");
	}
	public void ovR(){
		System.out.println("Annop");
	}
}
@MyAnno("MY ANNO DEF")
class AnnoC extends Annop{
	@Override	
	public void ovR(){
		System.out.println("AnnoC");
	}
}
public class AnnoDemo1{
	@SuppressWarnings("deprecation")
	public static void main(String...args){
		Class<?> ct = AnnoC.class;
		AnnoC ac = new AnnoC();
		ac.ovR();
		ac.depM();
		System.out.println(ct.isAnnotationPresent(MyAnno.class));
		System.out.println(ct.getAnnotationType(MyAnno.class).usage());
	}



}
