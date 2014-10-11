package storage;

public class Person {

	private final int age;
	private final String name;
	public final int AGE_DOUBLE;

	public Person(int a, String n){
		age = a;
		name = n;
		AGE_DOUBLE = age*age;
	}

	public String toString(){
		return name + ": " + age + " doubled is " + AGE_DOUBLE;
	}

}
