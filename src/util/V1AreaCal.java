package util;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import util.Interface.GerneralAnalysis;
/**
 * V1版本有个比较特殊的分析例子,是面积的那个
 * @author i-xiepenggang
 *	
 */
public class V1AreaCal extends GerneralAnalysis {

	@Override
	public String getResultState() {
		// TODO Auto-generated method stub
		if(mResultContent.equals("")){
			return RETR_BAD;
		}
		if(checkisXML() == false){
			return RETR_WAR;
		}
		try {
			Document doc = DocumentHelper.parseText(mResultContent);
			Element rootElement = doc.getRootElement();
			if (rootElement.element("result") != null) {
				return RETR_YES;
			} else {
				return RETR_BAD;
			}
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			return RETR_BAD;
		}
	}

}
