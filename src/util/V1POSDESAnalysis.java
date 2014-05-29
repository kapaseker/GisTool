package util;

import util.Interface.GerneralAnalysis;

public class V1POSDESAnalysis extends GerneralAnalysis {

	@Override
	public String getResultState() {
		// TODO Auto-generated method stub
		if(mResultContent.equals("")){
			return RETR_BAD;
		}
		return RETR_YES;
	}

}
