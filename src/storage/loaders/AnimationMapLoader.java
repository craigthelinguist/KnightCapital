package storage.loaders;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

import tools.Constants;
import tools.ImageLoader;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.DomDriver;


public class AnimationMapLoader {

	// use the static methods
	private AnimationMapLoader(){}

	public void test() throws IOException{
		String name = "ovelia_red_south";
	}

	public static void main(String[] args) throws IOException{
		new AnimationMapLoader().test();
	}

}
