package util;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import util.Interface.GerneralAnalysis;
/**
 * v1版本的一个特殊机构,累觉不爱啊...这么多特殊情况
 * @author i-xiepenggang
 *
 */
public class V1SPATIAL_RESPONSEAnalysis extends GerneralAnalysis {

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
			Element searchresultEle = rootElement.element("searchresult");

			if (searchresultEle != null) {
				Element countEle = searchresultEle.element("count");
				if (!countEle.getText().equals("0")) {
					return RETR_YES;
				} else {
					return RETR_BAD;
				}
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
