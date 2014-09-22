package util;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import util.Interface.GerneralAnalysis;

/**
 * V2版本都是这个东西!!!还是V2好啊
 * 
 * @author i-xiepenggang
 * 
 */
public class V2GeneralAnalysis extends GerneralAnalysis {

	@Override
	public String getResultState() {
		// TODO Auto-generated method stub
		if (mResultContent.equals("")) {
			return RETR_BAD;
		}
		if (checkisXML() == false) {
			return RETR_WAR;
		}
		try {
			Document doc = DocumentHelper.parseText(mResultContent);
			Element rootElement = doc.getRootElement();
			if (rootElement.elementText("status").equals("E0")) {
				return RETR_YES;
			} else {
				return RETR_BAD;
			}

		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			return RETR_BAD;
		} catch (Exception e) {
			return RETR_BAD;
		}
	}
}
