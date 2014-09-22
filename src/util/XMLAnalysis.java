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

/**
 * 
 * @author i-xiepenggang
 * @category XML分析器
 */

@SuppressWarnings("serial")
public class XMLAnalysis extends ArrayList<Map<String, String>> {

	private String mXMLPath = null;

	// private ArrayList<Map<String, String>> mItemList = new
	// ArrayList<Map<String, String>>();
	
	/**
	 * 
	 * 默认构造函数,使用默认在cfg路径下面的配置文件
	 */
	public XMLAnalysis(){
		this(null);
	}
	/**
	 * 
	 * @param path 传递配置文件的参数
	 */
	public XMLAnalysis(String path) {
		if (path == null) {
			mXMLPath = "./cfg/GIS.xml";
		} else {
			mXMLPath = path;
		}

		init();
	}
	
	 /**
	  * 初始化...
	  */
	private void init() {

		SAXReader reader = new SAXReader();
		try {
			//这个就是读取xml文件的一个分析器吧
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
			e.printStackTrace();
		}

	}
}
