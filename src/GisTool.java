import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;



public class GisTool {
	public static void main(String[] args) throws DocumentException {
		for (String s : args)
			System.out.println(s);
		SAXReader reader = new SAXReader();
		Document docroot = reader.read("E:/readme.txt");
		
		System.out.println(docroot.getRootElement().getName());
		
		
	}
}
