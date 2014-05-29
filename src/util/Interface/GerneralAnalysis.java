package util.Interface;

import util.RequestUtil;

public abstract class GerneralAnalysis extends RequestUtil implements IGisAnalysis{
	protected String mID = "";
	protected static String RETR_YES = "YES";
	protected static String RETR_BAD ="NG";
	
	@Override
	public void setID(String str) {
		// TODO Auto-generated method stub
		this.mID = str;
	}
	
	public void init(){
		mUrl = null;
		mRqContent = null;
		mContentEncoding = "GBK";
		mResultContent="";
		mRequestDate = null;
		mHandleSpanTime = "";
		mID="";
	}
	
	@Override
	public String getRequestID() {
		// TODO Auto-generated method stub
		return mID;
	}

	@Override
	public String getRequestTime() {
		// TODO Auto-generated method stub
		return getRequestDate();
	}

	@Override
	public String getHandleTime() {
		// TODO Auto-generated method stub
		return getHandleSpanTime();
	}
	@Override
	public String getResultFile() {
		// TODO Auto-generated method stub
		return getResult();
	}
}
