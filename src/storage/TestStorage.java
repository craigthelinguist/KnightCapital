package storage;

import java.io.FileNotFoundException;
import java.io.FileReader;

import com.thoughtworks.xstream.XStream;

import tools.Constants;

public class TestStorage {

	public void test() throws Exception{

		Person p = new Person(69,"avi");

		String file = "knight.xml";
		FileReader fileReader = new FileReader(Constants.DATA_UNITS + file);
		XStream stream = new XStream();
		stream.alias("person", storage.Person.class);
		p = (Person) stream.fromXML(fileReader);
		System.out.println(p);

	}

	public static void main(String[] args) throws Exception{

		new TestStorage().test();

	}

}
