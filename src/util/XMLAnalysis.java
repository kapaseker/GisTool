package util;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

@SuppressWarnings("serial")
public class XMLAnalysis extends ArrayList<Map<String, String>> {

	private String mXMLPath = null;

	// private ArrayList<Map<String, String>> mItemList = new
	// ArrayList<Map<String, String>>();

	public XMLAnalysis(String path) {
		if (path == null) {
			mXMLPath = "GIS.xml";
		} else {
			mXMLPath = path;
		}

		init();
	}

	private void init() {

		SAXReader reader = new SAXReader();

		try {

			Document gisDoc = reader.read(new File(mXMLPath));
			Element rootEle = gisDoc.getRootElement();
			Element serList = rootEle.element("ServiceList");

			List<Element> lst = serList.elements("ServiceItem");

			for (Element eleItem : lst) {
				Map<String, String> itemMap = new HashMap<String, String>();
				String SerialID = eleItem.element("SerialID").getText();
				String ServiceVersion = eleItem.element("ServiceVersion")
						.getText();
				String ServiceURL = eleItem.element("ServiceURL").getText();

				itemMap.put("SerialID", SerialID);
				itemMap.put("ServiceVersion", ServiceVersion);
				itemMap.put("ServiceURL", ServiceURL);

				this.add(itemMap);
			}

		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			System.out.println("没有找到文件");
		}

	}
}
