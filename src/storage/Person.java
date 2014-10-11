package storage;

public class Person {

	private int age;
	private String name;

	public Person(int a, String s){
		age = a;
		name = s;
	}

	public String toString(){
		return name + ": " + age;
	}

}
