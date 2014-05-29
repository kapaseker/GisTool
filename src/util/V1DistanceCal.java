package util;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import util.Interface.GerneralAnalysis;

public class V1DistanceCal extends GerneralAnalysis{

	@Override
	public String getResultState() {
		// TODO Auto-generated method stub
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
