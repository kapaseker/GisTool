package util;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import util.Interface.GerneralAnalysis;
/**
 * V1版本的距离计算分析
 * @author i-xiepenggang
 *
 */
public class V1DistanceCal extends GerneralAnalysis{

	@Override
	public String getResultState() {
		// TODO Auto-generated method stub
		if(mResultContent.equals("")){
			return RETR_BAD;
		}
		if(checkisXML() == false){
			return RETR_WAR;
		}
		Document doc;
		try {
			doc = DocumentHelper.parseText(mResultContent);
			Element rootElement =  doc.getRootElement();
			if(rootElement.getText().trim().equals("")){
				return RETR_BAD;
			}else{
				return RETR_YES;
			}
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			return RETR_BAD;
		}
	}

}
