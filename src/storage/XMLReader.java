package storage;

import java.io.File;
import java.io.FileInputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * this class is to read in the xml files that contain the different levels
 * @author Selemon Yitbarek
 * */
public class XMLReader {

	private String filePath;
	private String level;
	
	/**
	 * constructer
	 * @param String filepath that contains the file path and the 
	 * level the player wants to play at
	 */
	public XMLReader(String filePath, String level){
		this.filePath = filePath;
		this.level = level;
		
	}
	
	
	public void readLevel(){
		try{
			//grabs the file path of the xml file to read
			File file = new File(this.filePath);
			//FileInputStream stream = new FileInputStream(file);
			//creates a new factory instance
			DocumentBuilderFactory builder = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = builder.newDocumentBuilder();//creates a new instance of documentBuilder
			System.out.println("YOLO");
			Document document = dBuilder.parse(file);//Parses the given file as an XML document and return a new DOM document object
			
			document.getDocumentElement().normalize();//parent node
			System.out.println("Root element "+document.getDocumentElement().getNodeName());
			NodeList nodelist = document.getElementsByTagName(this.level);
			System.out.println(this.level+" attributes");
			
			for(int i = 0; i<nodelist.getLength(); i++){
				Node firstNode = nodelist.item(0);
				if(firstNode.getNodeType()==Node.ELEMENT_NODE){
					Element firstElement = (Element) firstNode;
					NodeList nameList = firstElement.getElementsByTagName("name");
					Element nameElement = (Element) nameList.item(0);
					NodeList n = nameElement.getChildNodes();
					System.out.println("Hero name : "+n.item(0).getNodeValue());
					NodeList health = firstElement.getElementsByTagName("health");
					Element healthElement = (Element) health.item(0);
					NodeList healthList = healthElement.getChildNodes();
					System.out.println("Health : "+healthList.item(0).getNodeValue());
					NodeList speed = firstElement.getElementsByTagName("speed");
					Element speedElement = (Element) speed.item(0);
					NodeList speedList = speedElement.getChildNodes();
					System.out.println("Speed : "+speedList.item(0).getNodeValue());
					NodeList damage = firstElement.getElementsByTagName("damage");
					Element damageElement = (Element) damage.item(0);
					NodeList damageList = damageElement.getChildNodes();
					System.out.println("Damage : "+damageList.item(0).getNodeValue());
					NodeList amour = firstElement.getElementsByTagName("amour");
					Element amourElement = (Element) amour.item(0);
					NodeList amourList = amourElement.getChildNodes();
					System.out.println("Amour : "+amourList.item(0).getNodeValue());
					NodeList movement = firstElement.getElementsByTagName("movement");
					Element movementElement = (Element) movement.item(0);
					NodeList movementList = movementElement.getChildNodes();
					System.out.println("Movement : "+movementList.item(0).getNodeValue());
					NodeList sight = firstElement.getElementsByTagName("sight");
					Element sightElement = (Element) sight.item(0);
					NodeList sightList = sightElement.getChildNodes();
					System.out.println("Sight : "+sightList.item(0).getNodeValue());
					
				
				
				}
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args){
		new XMLReader("C:\\Users\\mr\\workspace\\KnightCapital\\assets\\Levels.xml", "levelTwo").readLevel();
	}
	
}

