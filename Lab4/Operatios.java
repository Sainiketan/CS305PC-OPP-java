package Student;

interface Operations{
	abstract void register(Student std);
	abstract void login(String id ,  String password);
	abstract void exit();
}
