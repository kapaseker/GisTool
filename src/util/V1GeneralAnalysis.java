package util;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import util.Interface.GerneralAnalysis;

/**
 * 大多数V1版本的分析
 * 
 * @author i-xiepenggang
 * 
 */
public class V1GeneralAnalysis extends GerneralAnalysis {
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
			Element statusEle = rootElement.element("status");

			if (statusEle != null) {
				if (statusEle.getText().equals("E0")) {
					return RETR_YES;
				} else {
					return RETR_BAD;
				}
			} else {
				Element countEle = rootElement.element("count");
				if (countEle != null) {
					if (!countEle.getText().equals("0")) {
						return RETR_YES;
					} else {
						return RETR_BAD;
					}
				}
			}
			return RETR_BAD;

		} catch (Exception e) {
			return RETR_BAD;
		}
	}
}
