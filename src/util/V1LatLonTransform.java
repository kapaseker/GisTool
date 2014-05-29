package util;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import util.Interface.GerneralAnalysis;

public class V1LatLonTransform extends GerneralAnalysis {

	@Override
	public String getResultState() {
		// TODO Auto-generated method stub

		// TODO Auto-generated method stub

		try {
			Document doc = DocumentHelper.parseText(mResultContent);
			Element rootElement = doc.getRootElement();
			Element lstEle = rootElement.element("list");
			if (lstEle != null) {
				return (lstEle.elements().size() == 0) ? RETR_BAD : RETR_YES;

			}

			return RETR_BAD;
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			return RETR_BAD;
		}

	}

}
